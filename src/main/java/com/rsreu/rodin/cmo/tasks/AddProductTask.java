package com.rsreu.rodin.cmo.tasks;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AddProductTask {

    private String name;
    private Integer quantity;
    private Double price;

}
