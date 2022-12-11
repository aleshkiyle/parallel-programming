package com.rsreu.rodin.cmo.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BuyTask {
    private String customerUserName;
    private String goodName;
    private Integer quantity;
}
