package com.derteuffel.service;

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("sk_test_XQayGouNu0oTFSRnX6lFzkru00GjI4eZlA")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
    public Charge charge(Map chargeParams) throws StripeException {
           return Charge.create(chargeParams);

    }
}