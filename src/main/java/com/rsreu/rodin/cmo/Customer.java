package com.rsreu.rodin.cmo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {

    private String username;
    private Double balance;

    private Double spent = 0.0;

    public Customer(String username, Double balance) {
        this.username = username;
        this.balance = balance;
    }
}
