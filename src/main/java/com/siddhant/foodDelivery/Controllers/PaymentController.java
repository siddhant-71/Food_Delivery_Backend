package com.siddhant.foodDelivery.Controllers;


import com.siddhant.foodDelivery.Entities.Payment;
import com.siddhant.foodDelivery.Service.Implementations.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentServiceImpl paymentServiceImpl;


    @GetMapping("/{id}")
    public Payment getById(@PathVariable("id")long id){
        return paymentServiceImpl.getPaymentByPaymentId(id);
    }
    @GetMapping("/OrderId/{id}")
    public Payment getByOrderId(@PathVariable("id")long id){
        return paymentServiceImpl.getPaymentByOrderId(id);
    }
    @GetMapping("/Trans/{id}")
    public Payment getByTransId(@PathVariable("id")String id){
        return paymentServiceImpl.getPaymentByTransactionId(id);
    }
    @GetMapping("/")
    public List<Payment> getByStatus(){
        return paymentServiceImpl.getAllPayments();
    }


}
