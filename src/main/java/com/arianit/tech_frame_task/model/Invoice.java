package com.arianit.tech_frame_task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class Invoice {
    private List<Product> products;
    private double subtotal;
    private double vat;
    private double total;
}
