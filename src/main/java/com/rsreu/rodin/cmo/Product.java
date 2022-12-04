package com.rsreu.rodin.cmo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
