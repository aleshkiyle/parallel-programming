package com.rsreu.rodin.cmo;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.rsreu.rodin.cmo.task.AddProductTask;
import com.rsreu.rodin.cmo.task.BuyTask;
import com.rsreu.rodin.cmo.task.CreateCustomerTask;
import com.rsreu.rodin.cmo.task.TaskEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineShop {
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Product> products = new ConcurrentHashMap<>();

    private Double balance = 0.0;

    int bufferSize = 1024 * 128;

    Disruptor<TaskEvent> disruptor =
            new Disruptor<>(TaskEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

    RingBuffer<TaskEvent> ringBuffer;


    public OnlineShop() {

        class TaskEventHandler implements EventHandler<TaskEvent> {

            private final OnlineShop onlineShop;

            TaskEventHandler(OnlineShop onlineShop) {
                this.onlineShop = onlineShop;
            }

            @Override
            public void onEvent(TaskEvent taskEvent, long l, boolean b) {
                Object task = taskEvent.getTask();
                try {
                    if (task instanceof BuyTask) {
                        onlineShop.buy((BuyTask) task);
                    }
                    if (task instanceof CreateCustomerTask) {
                        onlineShop.createCustomer((CreateCustomerTask) task);
                    }
                    if (task instanceof AddProductTask) {
                        onlineShop.addProduct((AddProductTask) task);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
        final EventHandler<TaskEvent> eventHandler = new TaskEventHandler(this);
        disruptor.handleEventsWith(eventHandler);
        ringBuffer = disruptor.start();
    }

    private void publish(Object task) {
        long sequence = ringBuffer.next();
        TaskEvent taskEvent = ringBuffer.get(sequence);
        taskEvent.setTask(task);
        ringBuffer.publish(sequence);
    }

    public void createCustomer(String username, Double balance) {
        publish(CreateCustomerTask.builder()
                .username(username)
                .balance(balance)
                .build());
    }

    private void createCustomer(CreateCustomerTask createCustomerTask) {
        Customer customer = customers.get(createCustomerTask.getUsername());
        if (customer == null) {
            customer = new Customer(createCustomerTask.getUsername(), createCustomerTask.getBalance());
            customers.put(customer.getUsername(), customer);
        } else {
            customer.setBalance(createCustomerTask.getBalance());
        }
    }

    public void addProduct(String name, int quantity, Double price) {
        publish(AddProductTask.builder()
                .name(name)
                .quantity(quantity)
                .price(price)
                .build());
    }

    private void addProduct(AddProductTask addProductTask) {
        Product product = products.get(addProductTask.getName());
        if (product != null) {
            product.setQuantity(product.getQuantity() + addProductTask.getQuantity());
            product.setPrice(addProductTask.getPrice());
        } else {
            product = new Product(addProductTask.getPrice(), addProductTask.getQuantity(), addProductTask.getName());
            products.put(addProductTask.getName(), product);
        }
    }

    public void buy(String customerUserName, String productName, Integer quantity) {
        publish(BuyTask.builder()
                .customerUserName(customerUserName)
                .productName(productName)
                .quantity(quantity)
                .build());
    }

    private void buy(BuyTask buyTask) {
        Customer customer = findCustomer(buyTask.getCustomerUserName());
        Product product = findProduct(buyTask.getProductName());
        if (product.getQuantity() < buyTask.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity of products");
        }

        Double sum = product.getPrice() * buyTask.getQuantity();

        if (customer.getBalance() < sum) {
            throw new IllegalArgumentException("Insufficient funds on the account");
        }

        product.setQuantity(product.getQuantity() - buyTask.getQuantity());
        customer.setSpent(customer.getSpent() + sum);
        customer.setBalance(customer.getBalance() - sum);
        this.balance = this.balance + sum;

    }

    public Double getSpent(String customerUsername) {
        synchronized (customers) {
            Customer customer = findCustomer(customerUsername);
            return customer.getSpent();
        }
    }

    public List<Product> getProductsList() {
        synchronized (products) {
            return new ArrayList<>(products.values());
        }
    }

    private Product findProduct(String goodName) {
        Product product = products.get(goodName);
        if (product == null) {
            throw new IllegalArgumentException("This product does not exist");
        }
        return product;
    }

    private Customer findCustomer(String customerUserName) {
        Customer customer = customers.get(customerUserName);
        if (customer == null) {
            throw new IllegalArgumentException("This client does not exist");
        }
        return customer;
    }

    public Map<String, Customer> getCustomers() {
        synchronized (customers) {
            return customers;
        }
    }

    public double getBalance() {
        synchronized (balance) {
            return balance;
        }
    }

    public Map<String, Product> getProducts() {
        synchronized (products) {
            return products;
        }
    }
}
