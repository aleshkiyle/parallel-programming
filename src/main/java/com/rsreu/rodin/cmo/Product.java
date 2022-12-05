package com.rsreu.rodin.cmo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private Double price;
    private Integer quantity;
    private String name;
}
