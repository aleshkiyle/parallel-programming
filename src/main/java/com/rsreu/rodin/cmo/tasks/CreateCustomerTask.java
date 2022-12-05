package com.rsreu.rodin.cmo.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateCustomerTask {

    private String username;
    private Double balance;

}
