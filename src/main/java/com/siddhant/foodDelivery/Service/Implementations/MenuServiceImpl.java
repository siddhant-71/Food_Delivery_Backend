package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Entities.Menu;
import com.siddhant.foodDelivery.Entities.Restaurant;
import com.siddhant.foodDelivery.Exceptions.MenuExceptions.MenuNotFoundException;
import com.siddhant.foodDelivery.Exceptions.RestaurantExceptions.RestaurantNotFoundException;
import com.siddhant.foodDelivery.Repository.DishRepo;
import com.siddhant.foodDelivery.Repository.MenuRepo;
import com.siddhant.foodDelivery.Repository.RestaurantRepo;
import com.siddhant.foodDelivery.Service.Interfaces.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private DishRepo dishRepo;

    @Override
    public Menu addMenuForRestaurant(long restaurantId, Menu menu) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+restaurantId+" not found"));
        restaurant.getMenu().add(menu);
        restaurantRepo.save(restaurant);
        logger.info("Menu has been added for restaurant with id {}",restaurantId);
        return menu;
    }

    public void addd(Menu menu){
        menuRepo.save(menu);
    }
    @Override
    public Menu updateMenu(long menuId, Menu menu) {
        Menu oldMenu=menuRepo.findById(menuId).orElseThrow(()->new MenuNotFoundException("Menu with id "+menuId+" not found"));
        if(menu.getCategory()!=null)oldMenu.setCategory(menu.getCategory());
        if(menu.getDescription()!=null)oldMenu.setDescription(menu.getDescription());
        menuRepo.save(oldMenu);
        logger.info("Menu with id {} updated successfully",menuId);
        return oldMenu;
    }

    @Override
    public void deleteMenu(long menuId) {
        menuRepo.deleteById(menuId);
        logger.info("Menu with id {} deleted successfully",menuId);
    }

    @Override
    public List<Menu> getAllMenusOfRestaurant(long restaurantId) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+restaurantId+" not found"));
        if(restaurant.getMenu()==null)logger.error("No menus found for restaurant with id {}",restaurantId);
        logger.info("All menus of restaurant with id {} found successfully",restaurantId);
        return restaurant.getMenu();
    }

    @Override
    public Menu getMenuById(long menuId) {
        return menuRepo.findById(menuId).orElseThrow(()->new MenuNotFoundException("Menu with id "+menuId+" not found"));
    }

    @Override
    public Dish addDishToMenu(long menuId, Dish dish) {
        Menu menu=menuRepo.findById(menuId).orElseThrow(()->new MenuNotFoundException("Menu with id "+menuId+" not found"));
        dish.setMenu(menu);
        dishRepo.save(dish);
        menu.getDishes().add(dish);
        menuRepo.save(menu);
        logger.info("Dish with name {} added to menu with id {}",dish.getName(),menuId);
        return dish;
    }

    @Override
    public void deleteDishFromMenu(long menuId, Dish dish) {
        Menu menu=menuRepo.findById(menuId).orElseThrow(()->new MenuNotFoundException("Menu with id "+menuId+" not found"));
        menu.getDishes().remove(dish);
        menuRepo.save(menu);
        logger.info("Dish with name {} deleted from menu with id {}",dish.getName(),menuId);
    }

    @Override
    public Dish updateDishFromMenu(long menuId, Dish newDish,long oldDishId) {
        Menu menu=menuRepo.findById(menuId).orElseThrow(()->new MenuNotFoundException("Menu with id "+menuId+" not found"));
        for(Dish dish:menu.getDishes()){
            if(dish.getId()==oldDishId){
                if(newDish.getName()!=null)dish.setName(newDish.getName());
                if(newDish.getPrice()!=0.00)dish.setPrice(newDish.getPrice());
                if(newDish.getDescription()!=null)dish.setDescription(newDish.getDescription());
                if(newDish.getImage()!=null)dish.setImage(newDish.getImage());
                dishRepo.save(dish);
                logger.info("Dish with name {} updated in menu with id {}",dish.getName(),menuId);
                return dish;
            }
        }
        logger.warn("Dish with id {} not found in menu with id {}",oldDishId,menuId);
        return null;
    }

    @Override
    public List<Dish> getDishesByMenuId(long menuId) {
        Menu menu=menuRepo.findById(menuId).orElseThrow(()->new MenuNotFoundException("Menu with id "+menuId+" not found"));
        if(menu.getDishes()==null)logger.error("No dishes found for menu with id {}",menuId);
        else logger.info("All dishes of menu with id {} found successfully",menuId);
        return menu.getDishes();
    }
}
