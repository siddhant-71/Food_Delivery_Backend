package com.siddhant.foodDelivery.Controllers;


import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Exceptions.UserException.UserNotFoundException;
import com.siddhant.foodDelivery.Repository.UserRepo;
import com.siddhant.foodDelivery.Service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;



}
