package com.arianit.tech_frame_task.controller;
import com.arianit.tech_frame_task.model.Invoice;
import com.arianit.tech_frame_task.model.Product;
import com.arianit.tech_frame_task.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public List<Invoice> createInvoices(@RequestBody List<Product> products) {
        return invoiceService.generateInvoices(products);
    }
}
