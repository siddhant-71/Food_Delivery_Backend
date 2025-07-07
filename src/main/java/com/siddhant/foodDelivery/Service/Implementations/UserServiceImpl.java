package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Enums.UserRole;
import com.siddhant.foodDelivery.Exceptions.CartExceptions.CartNotFoundException;
import com.siddhant.foodDelivery.Exceptions.DishExceptions.DishNotFoundException;
import com.siddhant.foodDelivery.Exceptions.UserException.PasswordMismatchException;
import com.siddhant.foodDelivery.Exceptions.UserException.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.siddhant.foodDelivery.Entities.Cart;
import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Repository.*;
import com.siddhant.foodDelivery.Service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private DeliveryAgentRepo deliveryAgentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DishRepo dishRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private CartRepo cartRepo;




    @Override
    public User registerUser(User user) {
        userRepo.save(user);
        return user;
    }

    @Override
    public User updateUser(long userId, User newUser) {
        User oldUser=userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User Not Found"));
            if(newUser.getEmail()!=null){
                oldUser.setEmail(newUser.getEmail());
            }
            if(newUser.getPhone()!=null){
                oldUser.setPhone(newUser.getPhone());
            }
            if(newUser.getName()!=null) {
                oldUser.setName(newUser.getName());
            }
        userRepo.save(oldUser);
        return oldUser;
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(long Id) {
        User user=userRepo.findById(Id).orElseThrow(()->new UserNotFoundException("User Not Found"));
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(()->new UserNotFoundException("User with the email "+email+" not found"));
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepo.findByPhone(phone).orElseThrow(()->new UserNotFoundException("User with the phone no "+phone+" not found"));
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        List<User> users=userRepo.findAllByRole(role);
        if(!users.isEmpty())return users;
        logger.info("No users found with role {}",role);
        return List.of();
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user=userRepo.findByEmail(email).orElseThrow(()->new UserNotFoundException("User with the email "+email+" not found"));
        if(user.getPassword().equals(password)){
            return user;
        }
        else{
            logger.warn("Incorrect password for user {}",email);
            throw new PasswordMismatchException("password mismatch");
        }
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        User user=userRepo.findByEmail(email).orElseThrow(()->new UserNotFoundException("User with the email "+email+" not found"));
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            userRepo.save(user);
        }
        else{
            logger.warn("Incorrect old password for user {}",email);
            throw new PasswordMismatchException("wrong password");
        }
    }

    @Override
    public void resetPassword(String email) {
        User user=userRepo.findByEmail(email).orElseThrow(()->new UserNotFoundException("User with the email "+email+" not found"));
        //Send THe EMail to reset the password;
        logger.info("Password reset link is sent to the email {}",email);
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<User> user=userRepo.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public boolean deleteUserById(long id) {
        User user=userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User with the id "+id+" not found"));
        userRepo.delete(user);
        return true;
    }

    @Override
    public Cart getCartByUserId(long userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User with the id "+userId+" not found"));
        if(user.getCart()==null){
            throw new CartNotFoundException("Cart not found for the user with id "+userId);
        }
        return user.getCart();
    }

    @Override
    public void addItemToCart(Cart cart, long dishId) {
        Dish dish=dishRepo.findById(dishId).orElseThrow(()->new DishNotFoundException("Dish with the id "+dishId+" not found"));
        List<Dish> dishes=cart.getDishes();
        dishes.add(dish);
        cart.setDishes(dishes);
        cartRepo.save(cart);
    }

    @Override
    public void removeItemFromCart(Cart cart, long dishId) {
        Dish dish=dishRepo.findById(dishId).orElseThrow(()->new DishNotFoundException("Dish with the id "+dishId+" not found"));
        List<Dish> dishes=cart.getDishes();
        dishes.remove(dish);
        cart.setDishes(dishes);
        cartRepo.save(cart);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.setDishes(List.of());
        cartRepo.save(cart);
    }

    @Override
    public List<Order> getUserOrders(long userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User with the id "+userId+" not found"));
        return orderRepo.findAllByUserId(userId);
    }

    @Override
    public void clearUserCart(long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User with the id " + userId + " not found"));
        user.getCart().setDishes(List.of());
        cartRepo.save(user.getCart());
    }

    @Override
    public long countUsers() {
        long ans=userRepo.count();
        if(ans==0)logger.info("No users found");
        return ans;
    }

    @Override
    public long countOrders() {
        long ans=orderRepo.count();
        if(ans==0)logger.info("No orders found");
        return ans;
    }

    @Override
    public long countRestaurants() {
        long ans=restaurantRepo.count();
        if(ans==0)logger.info("No restaurants found");
        return ans;
    }

    @Override
    public long countDeliveryAgents() {
        long ans=deliveryAgentRepo.count();
        if(ans==0)logger.info("No delivery agents found");
        return ans;
    }
}
