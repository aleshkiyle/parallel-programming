package com.rsreu.rodin.cmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OnlineShop {

    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Product> products = new HashMap<>();
    private Double balance = 0.0;

    private final Integer quantity = 0;

    private final Lock lock = new ReentrantLock();

    public Customer createCustomer(String username, Double balance) {
        synchronized (customers) {
            Customer customer = customers.get(username);
            if (customer == null) {
                customer = new Customer(username, balance);
                customers.put(customer.getUsername(), customer);
            } else {
                customer.setBalance(balance);
            }
            return customer;
        }
    }

    public void addProduct(String name, int quantity, Double price) {
        synchronized (products) {
            Product product = products.get(name);
            if (product != null) {
                product.setQuantity(product.getQuantity() + quantity);
                product.setPrice(price);
            } else {
                product = new Product(price, quantity, name);
                products.put(name, product);
            }
        }
    }

    public void buy(String customerUserName, String goodName, Integer quantity) {
        synchronized (customerUserName) {
            synchronized (goodName) {
                synchronized (quantity) {
                    Customer customer = findCustomer(customerUserName);
                    Product product = findGood(goodName);
                    if (product.getQuantity() < quantity) {
                        throw new IllegalArgumentException("Недостаточное количество товара");
                    }
                    Double sum = product.getPrice() * quantity;

                    if (customer.getBalance() < sum) {
                        throw new IllegalArgumentException("Недостаточно средств на счету");
                    }

                    product.setQuantity(product.getQuantity() - quantity);
                    customer.setSpent(customer.getSpent() + sum);
                    customer.setBalance(customer.getBalance() - sum);
                    this.balance = this.balance + sum;
                }
            }
        }
    }

    public Double getSpent(String customerUsername) {
        synchronized (customers) {
            Customer customer = findCustomer(customerUsername);
            return customer.getSpent();
        }
    }

    public List<Product> getGoodsList() {
        synchronized (products) {
            return new ArrayList<>(products.values());
        }
    }

    private Product findGood(String goodName) {
        Product product = products.get(goodName);
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

    public Map<String, Product> getProducts() {
        synchronized (products) {
            return this.products;
        }
    }
}
