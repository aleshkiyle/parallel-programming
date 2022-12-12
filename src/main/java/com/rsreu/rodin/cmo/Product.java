package com.rsreu.rodin.cmo;

import lombok.Data;
import lombok.ToString;

@Data
public class Product {
    private Double price;
    private int quantity;
    private String name;

    public Product(Double price, int quantity, String name) {
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }
}
