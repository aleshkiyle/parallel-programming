package com.rsreu.rodin.cmo;

import com.rsreu.rodin.cmo.task.AddGoodTask;
import com.rsreu.rodin.cmo.task.BuyTask;
import com.rsreu.rodin.cmo.task.CreateCustomerTask;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class OnlineShop {
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Product> goods = new ConcurrentHashMap<>();
    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();

    private Double balance = 0.0;

    public OnlineShop() {

        @AllArgsConstructor
        class TaskProcessor implements Runnable {
            private BlockingQueue<Object> queue;
            private OnlineShop onlineShop;

            @Override
            public void run() {
                while (true) {
                    Object task;
                    try {
                        task = queue.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (task instanceof BuyTask) {
                        onlineShop.buy((BuyTask) task);
                    }
                    if (task instanceof CreateCustomerTask) {
                        onlineShop.createCustomer((CreateCustomerTask) task);
                    }
                    if (task instanceof AddGoodTask) {
                        onlineShop.addProduct((AddGoodTask) task);
                    }
                }
            }
        }
        TaskProcessor taskProcessor = new TaskProcessor(queue, this);
        Thread thread = new Thread(taskProcessor);
        thread.start();
    }

    public void createCustomer(String username, Double balance) {
        try {
            queue.put(CreateCustomerTask.builder()
                    .username(username)
                    .balance(balance)
                    .build());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
        try {
            queue.put(AddGoodTask.builder()
                    .name(name)
                    .price(price)
                    .quantity(quantity)
                    .build());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void addProduct(AddGoodTask addGoodTask) {
        Product product = goods.get(addGoodTask.getName());
        if (product != null) {
            product.setQuantity(product.getQuantity() + addGoodTask.getQuantity());
            product.setPrice(addGoodTask.getPrice());
        } else {
            product = new Product(addGoodTask.getPrice(), addGoodTask.getQuantity(), addGoodTask.getName());
            goods.put(addGoodTask.getName(), product);
        }
    }

    public void buy(String customerUserName, String goodName, Integer quantity) {
        try {
            queue.put(BuyTask.builder()
                    .customerUserName(customerUserName)
                    .goodName(goodName)
                    .quantity(quantity)
                    .build());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void buy(BuyTask buyTask) {
        Customer customer = findCustomer(buyTask.getCustomerUserName());
        Product product = findProduct(buyTask.getGoodName());
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
        synchronized (goods) {
            return new ArrayList<>(goods.values());
        }
    }

    private Product findProduct(String goodName) {
        Product product = goods.get(goodName);
        if (product == null) {
            throw new IllegalArgumentException("Такого товара не существует");
        }
        return product;
    }

    private Customer findCustomer(String customerUserName) {
        Customer customer = customers.get(customerUserName);
        if (customer == null) {
            throw new IllegalArgumentException("Такого клиента не существует");
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
        synchronized (goods) {
            return goods;
        }
    }

    public BlockingQueue<Object> getQueue() {
        return queue;
    }
}
