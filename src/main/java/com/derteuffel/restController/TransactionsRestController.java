package com.derteuffel.restController;

import com.derteuffel.data.Payment;
import com.derteuffel.repository.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionsRestController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping(value = "/mobile/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public List<Payment> transactions(@RequestBody Map<String,String> object)
    {
       return  userRepository.findById(Long.parseLong(object.get("id"))).get().getPayments();

    }
}
