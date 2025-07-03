package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.Cart;
import com.siddhant.foodDelivery.Entities.Order;
import com.siddhant.foodDelivery.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //CRUD
    User registerUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(long Id);

    //FIND
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByPhone(String phone);
    List<User> getUsersByRole(String role);


    //AUTHENTICATION
    User authenticatedUser(String email, String password);
    void changePassword(String email, String oldPassword, String newPassword);
    void resetPassword(String email);
    boolean existsByEmail(String email);
    boolean deleteUserById(long id);



    //CART MANAGEMENT
    Cart getCartByUserId(long userId);
    void addItemToCart(Cart cart, long dishId);
    void removeItemFromCart(Cart cart, long dishId);
    void clearCart(Cart cart);
    List<Order> getUserOrders(long userId);
    void clearUserCart(long userId);



    //ADMIN
    long countUsers();
    long countOrders();
    long countRestaurants();
    long countDeliveryAgents();

}
