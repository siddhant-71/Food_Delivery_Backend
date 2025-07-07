package com.siddhant.foodDelivery.Controllers;


import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Enums.UserRole;
import com.siddhant.foodDelivery.Service.Implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping("/")
    public List<User> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }
    @GetMapping("/id/{UserId}")
    public User getUserWithId(@PathVariable("UserId") long UserId){
        return userServiceImpl.getUserById(UserId);
    }
    @GetMapping("/email/{userEmail}")
    public User getUserWithEmail(@PathVariable("userEmail") String userEmail){
        User user=userServiceImpl.getUserByEmail(userEmail);
        return user;
    }
    @GetMapping("/phone/{userPhone}")
    public User getUserWithPhone(@PathVariable("userPhone") String userPhone){
        return userServiceImpl.getUserByPhone(userPhone);
    }
    @GetMapping("/role/{role}")
    public List<User> getUserByRole(@PathVariable("role") UserRole role){
        return userServiceImpl.getUsersByRole(role);
    }



    @DeleteMapping("/id/{userId}")
    public void deleteUserWithId(@PathVariable("userId") long userId){
        userServiceImpl.deleteUserById(userId);
    }
    @DeleteMapping("/email/{userEmail}")
    public void deleteUserWithEmail(@PathVariable("userEmail") String userEmail){
        User user=userServiceImpl.getUserByEmail(userEmail);
        userServiceImpl.deleteUser(user);
    }
    @DeleteMapping("/phone/{userPhone}")
    public void deleteUserWithPhone(@PathVariable("userPhone") String userPhone){
        User user=userServiceImpl.getUserByPhone(userPhone);
        userServiceImpl.deleteUser(user);
    }


    @PostMapping("/addUser")
    public void addUser(@RequestBody User user){
        userServiceImpl.registerUser(user);
    }


    @PutMapping("/{userId}")
    public void updateUser(@RequestBody User user,@PathVariable("userId") long userId){
        userServiceImpl.updateUser(userId,user);
    }

}
