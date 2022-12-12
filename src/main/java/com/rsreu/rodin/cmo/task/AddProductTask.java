package com.rsreu.rodin.cmo.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AddProductTask {

    private String name;

    private int quantity;

    private Double price;

}
