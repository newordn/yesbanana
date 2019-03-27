package com.derteuffel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by neword on 18/03/2019.
 */

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping("/form")
    public String getForm()
    {
        return "payment/paymentForm";
    }
    @GetMapping("/transactions")
    public String getTransactions()
    {
        return "payment/transactions";
    }
}
