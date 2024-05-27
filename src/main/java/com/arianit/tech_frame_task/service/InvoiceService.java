package com.arianit.tech_frame_task.service;

import com.arianit.tech_frame_task.model.Invoice;
import com.arianit.tech_frame_task.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private static final double MAX_INVOICE_AMOUNT = 500;
    private static final int MAX_UNITS_PER_PRODUCT = 50;

    public List<Invoice> generateInvoices(List<Product> products) {
        List<Invoice> invoices = new ArrayList<>();
        List<Product> remainingProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getPrice() > MAX_INVOICE_AMOUNT) {
                invoices.add(createInvoice(List.of(product)));
            } else {
                remainingProducts.add(product);
            }
        }

        distributeProductsAcrossInvoices(invoices, remainingProducts);
        return invoices;
    }


    private void distributeProductsAcrossInvoices(List<Invoice> invoices, List<Product> products) {
        List<Product> currentInvoiceProducts = new ArrayList<>();
        double currentTotal = 0;

        for (Product product : products) {
            int quantity = product.getQuantity();
            double unitPrice = product.getPrice() - product.getDiscount();
            double unitPriceWithVAT = unitPrice * (1 + product.getVat() / 100);

            while (quantity > 0) {
                int unitsForGroup = Math.min(quantity, MAX_UNITS_PER_PRODUCT);
                double groupValue = unitsForGroup * unitPriceWithVAT;

                if (currentTotal + groupValue > MAX_INVOICE_AMOUNT) {
                    invoices.add(createInvoice(currentInvoiceProducts));
                    currentInvoiceProducts = new ArrayList<>();
                    currentTotal = 0;
                }

                if (groupValue > MAX_INVOICE_AMOUNT) {
                    int unitsToAdd = (int) Math.floor(MAX_INVOICE_AMOUNT / unitPriceWithVAT);
                    double valueToAdd = unitsToAdd * unitPriceWithVAT;

                    currentInvoiceProducts.add(new Product(product.getName(), product.getPrice(), unitsToAdd, product.getVat(), product.getDiscount()));
                    invoices.add(createInvoice(currentInvoiceProducts));
                    currentInvoiceProducts = new ArrayList<>();
                    currentTotal = 0;

                    quantity -= unitsToAdd;
                } else {
                    currentInvoiceProducts.add(new Product(product.getName(), product.getPrice(), unitsForGroup, product.getVat(), product.getDiscount()));
                    currentTotal += groupValue;
                    quantity -= unitsForGroup;
                }
            }
        }

        if (!currentInvoiceProducts.isEmpty()) {
            invoices.add(createInvoice(currentInvoiceProducts));
        }
    }

    private Invoice createInvoice(List<Product> products) {
        double subtotal = calculateSubtotal(products);
        double vatAmount = calculateVAT(products);
        double total = subtotal + vatAmount;
        return new Invoice(products, subtotal, vatAmount, total);
    }

    private double calculateSubtotal(List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.getQuantity() * (product.getPrice() - product.getDiscount()))
                .sum();
    }

    private double calculateVAT(List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.getQuantity() * (product.getPrice() - product.getDiscount()) * product.getVat() / 100)
                .sum();
    }

}
