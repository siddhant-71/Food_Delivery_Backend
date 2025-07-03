package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.Dish;

import java.util.List;

public interface DishService {
    Dish addDish(Dish dish);
    Dish updateDish(long DishId,Dish UpdatedDish);
    void deleteDish(long DishId);
    Dish getDishById(long DishId);
}
