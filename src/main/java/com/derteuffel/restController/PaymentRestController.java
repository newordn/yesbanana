package com.derteuffel.restController;

import com.derteuffel.data.Payment;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.repository.PaymentRepository;
import com.derteuffel.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentRestController {
    @Autowired
    private StripeService paymentsService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping(value = "/mobile/do_payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, String > doPayment(@RequestBody Map<String,String> object)
    {
        System.out.println("Payment object" + object);
        Map<String,String> map = new HashMap<String,String>();
        Map<String,Object> chargeMap = new HashMap<String,Object>();
        chargeMap.put("amount",object.get("amount"));
        chargeMap.put("currency",object.get("currency"));
        chargeMap.put("source",object.get("token"));
        chargeMap.put("description",object.get("description"));
        try{
            Charge charge = paymentsService.charge(chargeMap);
            Payment payment= new Payment(Double.parseDouble(object.get("amount")),object.get("title"),object.get("description"),userRepository.findById(Long.parseLong(object.get("id"))).get());
            paymentRepository.save(payment);
            map.put("status","true");
            System.out.println(charge);
        }catch(StripeException e)
        {
            map.put("status","false");
            e.printStackTrace();
        }
        return map;
    }
}
