package com.rsreu.rodin.monitors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Phone {

    private String name;
    private Integer price;
}

class MainPhone {

    public static void main(String[] args) {
        List<Phone> phones = List.of(
                new Phone("Iphone 8", 30000), new Phone("Meizu m5 Note", 12000)
        );
        int sum = phones.stream()
                .mapToInt(Phone::getPrice)
                .sum();
        System.out.println(sum);
    }
}