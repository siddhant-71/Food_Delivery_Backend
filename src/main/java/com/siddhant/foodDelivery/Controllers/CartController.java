package com.siddhant.foodDelivery.Controllers;

import com.siddhant.foodDelivery.Entities.Cart;
import com.siddhant.foodDelivery.Service.Implementations.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartServiceImpl cartService;

    @GetMapping("/user/{id}")
    Cart getByUserId(@PathVariable("id")long id){
        return cartService.getCartByUserId(id);
    }

}
