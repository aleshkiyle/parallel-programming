package com.rsreu.rodin.cmo;

import com.rsreu.rodin.cmo.tasks.AddProductTask;
import com.rsreu.rodin.cmo.tasks.BuyTask;
import com.rsreu.rodin.cmo.tasks.CreateCustomerTask;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class OnlineShop {

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Product> goods = new ConcurrentHashMap<>();
    private final BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>();

    private Double balance = 0.0;

    public OnlineShop() {

        @AllArgsConstructor
        class TaskProcessor implements Runnable {
            private BlockingQueue<Object> queue;
            private OnlineShop onlineShop;

            @Override
            public void run() {
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
                    if (task instanceof AddProductTask) {
                        onlineShop.addProduct((AddProductTask) task);
                    }
            }
        }
        TaskProcessor taskProcessor = new TaskProcessor(blockingQueue, this);
        Thread thread = new Thread(taskProcessor);
        thread.start();
    }

    public void createCustomer(String username, Double balance) {
        try {
            blockingQueue.put(new CreateCustomerTask(username, balance));
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

    public void addProduct(String name, Integer quantity, Double price) {
        try {
            blockingQueue.put(AddProductTask.builder()
                    .name(name)
                    .price(price)
                    .quantity(quantity)
                    .build());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void addProduct(AddProductTask addProductTask) {
        Product product = goods.get(addProductTask.getName());
        if (product != null) {
            product.setQuantity(product.getQuantity() + addProductTask.getQuantity());
            product.setPrice(addProductTask.getPrice());
        } else {
            product = new Product(addProductTask.getPrice(),
                    addProductTask.getQuantity(), addProductTask.getName());
            goods.put(addProductTask.getName(), product);
        }
    }

    public void buy(String customerUserName, String goodName, Integer quantity) {
        try {
            blockingQueue.put(BuyTask.builder()
                    .customerUserName(customerUserName)
                    .goodProduct(goodName)
                    .quantity(quantity)
                    .build());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void buy(BuyTask buyTask) {
        Customer customer = findCustomer(buyTask.getCustomerUserName());
        Product product = findProduct(buyTask.getGoodProduct());
        if (product.getQuantity() < buyTask.getQuantity()) {
            throw new IllegalArgumentException("Недостаточное количество товара");
        }

        Double sum = product.getPrice() * buyTask.getQuantity();

        if (customer.getBalance() < sum) {
            throw new IllegalArgumentException("Недостаточно средств на счету");
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

    public List<Product> getGoodsList() {
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
            return this.customers;
        }

    }

    public double getBalance() {
        synchronized (balance) {
            return this.balance;
        }

    }

    public Map<String, Product> getGoods() {
        synchronized (goods) {
            return this.goods;
        }
    }

    public BlockingQueue<Object> getBlockingQueue() {
        return this.blockingQueue;
    }
}
