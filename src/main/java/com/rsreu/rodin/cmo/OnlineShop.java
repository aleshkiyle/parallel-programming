package com.rsreu.rodin.cmo;

import com.rsreu.rodin.cmo.task.AddGoodTask;
import com.rsreu.rodin.cmo.task.BuyTask;
import com.rsreu.rodin.cmo.task.CreateCustomerTask;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class OnlineShop {
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Product> products = new ConcurrentHashMap<>();
    private final BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>();

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
        TaskProcessor taskProcessor = new TaskProcessor(blockingQueue, this);
        Thread thread = new Thread(taskProcessor);
        thread.start();
    }

    public void createCustomer(String username, Double balance) {
        try {
            blockingQueue.put(CreateCustomerTask.builder()
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
            blockingQueue.put(AddGoodTask.builder()
                    .name(name)
                    .price(price)
                    .quantity(quantity)
                    .build());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void addProduct(AddGoodTask addGoodTask) {
        Product product = products.get(addGoodTask.getName());
        if (product != null) {
            product.setQuantity(product.getQuantity() + addGoodTask.getQuantity());
            product.setPrice(addGoodTask.getPrice());
        } else {
            product = new Product(addGoodTask.getPrice(), addGoodTask.getQuantity(), addGoodTask.getName());
            products.put(addGoodTask.getName(), product);
        }
    }

    public void buy(String customerUserName, String productName, Integer quantity) {
        try {
            blockingQueue.put(BuyTask.builder()
                    .customerUserName(customerUserName)
                    .productName(productName)
                    .quantity(quantity)
                    .build());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
            throw new IllegalArgumentException("");
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
        synchronized (products) {
            return products;
        }
    }

    public BlockingQueue<Object> getBlockingQueue() {
        return blockingQueue;
    }
}
