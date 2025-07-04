package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.Cart;

public interface CartService {
    //CRUd
    Cart createCartForUser(long UserId);
    Cart getCartByUserId(long UserId);
    void clearCart(long cartId);

    Cart addDishToCart(long userId,long DishId,int quantity);
    Cart removeDishFromCart(long userID,long DishId);

    double calculateTotalCartValueByUser(long UserId);
    int totalCartItemsByUserid(long userId);


}
