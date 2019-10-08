package com.derteuffel.restController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentRestController {

    @PostMapping(value = "/do_payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, Boolean > doPayment(@RequestBody Map<String,String> object)
    {
        System.out.println(object);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        Stripe.apiKey= "sk_test_XQayGouNu0oTFSRnX6lFzkru00GjI4eZlA";
        Map<String,Object> chargeMap = new HashMap<String,Object>();
        chargeMap.put("amount",object.get("amount"));
        chargeMap.put("currency",object.get("currency"));
        chargeMap.put("source",object.get("token"));
        chargeMap.put("description",object.get("description"));
        try{
            Charge charge = Charge.create(chargeMap);
            System.out.println(charge);
        }catch(StripeException e)
        {
            e.printStackTrace();
        }
        return map;
    }
}
