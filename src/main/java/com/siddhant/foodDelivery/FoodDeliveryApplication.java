package com.siddhant.foodDelivery;

import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Service.Implementations.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryApplication.class, args);

		System.out.println("Hello Food Delivery App Has Been Started");
	}

}