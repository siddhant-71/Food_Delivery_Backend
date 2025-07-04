package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Entities.Menu;

import java.util.List;
public interface MenuService {
    Menu addMenuForRestaurant(long restaurantId, Menu menu);
    Menu updateMenu(long menuId, Menu menu);
    void deleteMenu(long menuId);
    List<Menu> getAllMenusOfRestaurant(long restaurantId);
    Menu getMenuById(long menuId);

    Dish addDishToMenu(long menuId, Dish dish);
    void deleteDishFromMenu(long menuId, Dish dish);
    Dish updateDishFromMenu(long menuId, Dish updatedDish,long dishId);
    List<Dish> getDishesByMenuId(long menuId);
}
