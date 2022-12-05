package com.rsreu.rodin.cmo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private String username;
    private Double balance;

    private Double spent = 0.0;

    public Customer(String username, Double balance) {
        this.username = username;
        this.balance = balance;
    }
}
