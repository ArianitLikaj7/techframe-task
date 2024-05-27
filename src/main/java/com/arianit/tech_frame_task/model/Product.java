package com.arianit.tech_frame_task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {

    private String name;
    private double price;
    private int quantity;
    private double vat;
    private double discount;
}
