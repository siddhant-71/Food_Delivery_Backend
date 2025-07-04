package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Entities.Cart;
import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Exceptions.CartExceptions.CartNotFoundException;
import com.siddhant.foodDelivery.Exceptions.DishExceptions.DishNotFoundException;
import com.siddhant.foodDelivery.Exceptions.UserException.UserNotFoundException;
import com.siddhant.foodDelivery.Repository.CartRepo;
import com.siddhant.foodDelivery.Repository.DishRepo;
import com.siddhant.foodDelivery.Repository.UserRepo;
import com.siddhant.foodDelivery.Service.Interfaces.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger= LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private DishRepo dishRepo;

    @Override
    public Cart createCartForUser(long userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User with the id "+userId+" not found"));
        if(user.getCart()==null){
            Cart cart=new Cart();
            user.setCart(cart);
            cart.setUser(user);
            cartRepo.save(cart);
            userRepo.save(user);
            logger.info("Cart created for user with id {}",userId);
            return cart;
        }
        return user.getCart();
    }

    @Override
    public Cart getCartByUserId(long UserId) {
        User user=userRepo.findById(UserId).orElseThrow(()->new UserNotFoundException("User with the id "+UserId+" not found"));
        if(user.getCart()==null){
            throw new CartNotFoundException("Cart not found for the user with id "+UserId);
        }
        return user.getCart();
    }

    @Override
    public void clearCart(long cartId) {
        Cart cart=cartRepo.findById(cartId).orElseThrow(()->new CartNotFoundException("Cart with the id "+cartId+" not found"));
        cart.setDishes(List.of());
        cartRepo.save(cart);
    }

    @Override
    public Cart addDishToCart(long userId, long dishId, int quantity) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User with the id "+userId+" not found"));
        Cart cart=user.getCart();
        Dish dish=dishRepo.findById(dishId).orElseThrow(()->new DishNotFoundException("Dish with the id "+dishId+" not found"));
        if(cart==null){
            cart=new Cart();
            List<Dish>dishes=new ArrayList<>();
            for(int i=0;i<quantity;i++)dishes.add(dish);
            cart.setDishes(dishes);
            cart.setUser(user);
            user.setCart(cart);
            userRepo.save(user);
            return cart;
        }
        else{
            for(int i=0;i<quantity;i++)cart.getDishes().add(dish);
            cartRepo.save(cart);
            return cart;
        }
    }

    @Override
    public Cart removeDishFromCart(long userID, long DishId) {
        User user=userRepo.findById(userID).orElseThrow(()->new UserNotFoundException("User with the id "+userID+" not found"));
        Cart cart=user.getCart();
        if(cart==null){
            throw new CartNotFoundException("Cart is Empty of User with UserId "+userID);
        }
        else{
            Dish dish=dishRepo.findById(DishId).orElseThrow(()->new DishNotFoundException("Dish with the id "+DishId+" not found"));
            cart.getDishes().remove(dish);
            logger.info("Dish with name {} removed from cart of user {}",dish.getName(),user.getName());
            cartRepo.save(cart);
            user.setCart(cart);
        }
        return cart;
    }

    @Override
    public double calculateTotalCartValueByUser(long UserId) {
        User user=userRepo.findById(UserId).orElseThrow(()->new UserNotFoundException("User with the id "+UserId+" not found"));
        if(user.getCart()==null){
            logger.info("Cart is Empty ");
            return 0.00;
        }
        Cart cart=user.getCart();
        double total=0.00;
        for(Dish dish:cart.getDishes()){
            total+=dish.getPrice();
        }
        logger.info("Total Cart Value is {}",total);
        return total;
    }

    @Override
    public int totalCartItemsByUserid(long userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User with the id "+userId+" not found"));
        if(user.getCart()==null){
            logger.info("Cart is Empty No Cart Items Found");
            return 0;
        }
        logger.info("Total Items in Cart are {}",user.getCart().getDishes().size());
        return user.getCart().getDishes().size();
    }
}
