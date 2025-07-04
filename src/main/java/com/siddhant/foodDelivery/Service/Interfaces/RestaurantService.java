package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Entities.Menu;
import com.siddhant.foodDelivery.Entities.Restaurant;

import java.util.List;

public interface RestaurantService {

    //CRUD
    Restaurant registerRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(long Id,Restaurant restaurant);
    void deleteRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(long id);

    //SEARCH
    List<Restaurant> searchRestaurantsByName(String name);
    List<Restaurant> searchRestaurantsByCity(String city);
    List<Restaurant> searchRestaurantsByState(String state);
    List<Restaurant> searchRestaurantsByRatingRange(double start, double end);
    List<Restaurant> searchRestaurantsByPincode(int pincode);
    List<Restaurant> searchRestaurantsByArea(String area);

    void updateRestaurantRating(long restaurantId, double rating);

    //MENU
    Menu addMenuToRestaurant(long restaurantId,Menu menu);
    void deleteMenuFromRestaurant(long restaurantId,long menuId);
    List<Dish> getDishesFromRestaurant(long restaurantId);
    List<Menu> getMenusFromRestaurant(long restaurantId);

}
