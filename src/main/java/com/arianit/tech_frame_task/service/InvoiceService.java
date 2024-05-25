package com.arianit.tech_frame_task.service;

import com.arianit.tech_frame_task.model.Invoice;
import com.arianit.tech_frame_task.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    
    private Invoice createInvoice(List<Product> products){
        double subtotal = calculateSubtotal(products);
        double vat = calculateVat(products);
        double total = subtotal + vat;
        return new Invoice(products,subtotal,vat,total);
    }

    private double calculateVat(List<Product> products) {
        double vat = 0;
        for (Product product : products) {
            vat += product.getQuantity() * (product.getPrice() - product.getDiscount()) * product.getVat() / 100;
        }
        return vat;
    }

    private double calculateSubtotal(List<Product> products) {
        double subtotal = 0;
        for(Product product: products){
            subtotal += product.getQuantity() * (product.getPrice() - product.getDiscount());
        }
        return subtotal;
    }
}
