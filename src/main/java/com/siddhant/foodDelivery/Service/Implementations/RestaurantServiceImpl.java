package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Entities.Menu;
import com.siddhant.foodDelivery.Entities.Restaurant;
import com.siddhant.foodDelivery.Exceptions.MenuExceptions.MenuNotFoundException;
import com.siddhant.foodDelivery.Exceptions.RestaurantExceptions.RestaurantNotFoundException;
import com.siddhant.foodDelivery.Repository.MenuRepo;
import com.siddhant.foodDelivery.Repository.RestaurantRepo;
import com.siddhant.foodDelivery.Service.Interfaces.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final Logger logger= LoggerFactory.getLogger(RestaurantServiceImpl.class);
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Override
    public Restaurant registerRestaurant(Restaurant restaurant) {
        restaurantRepo.save(restaurant);
        logger.info("Restaurant with name {} registered successfully",restaurant.getName());
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(long id,Restaurant restaurant) {
        Restaurant oldRestaurant=restaurantRepo.findById(id).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+id+" not found"));
        if(restaurant.getName()!=null)oldRestaurant.setName(restaurant.getName());
        if(restaurant.getArea()!=null)oldRestaurant.setArea(restaurant.getArea());
        if(restaurant.getCity()!=null)oldRestaurant.setCity(restaurant.getCity());
        if(restaurant.getState()!=null)oldRestaurant.setState(restaurant.getState());
        if(restaurant.getImage()!=null)oldRestaurant.setImage(restaurant.getImage());
        if(restaurant.getPincode()!=null)oldRestaurant.setPincode(restaurant.getPincode());
        if(restaurant.getDescription()!=null)oldRestaurant.setDescription(restaurant.getDescription());
        restaurantRepo.save(oldRestaurant);
        logger.info("Restaurant with id {} updated successfully",id);
        return oldRestaurant;
    }

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepo.delete(restaurant);
        logger.info("Restaurant with name {} deleted successfully",restaurant.getName());
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @Override
    public Restaurant getRestaurantById(long id) {
        return restaurantRepo.findById(id).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+id+" not found"));
    }

    @Override
    public List<Restaurant> searchRestaurantsByName(String name) {
        List<Restaurant> restaurants=restaurantRepo.findAllByName(name);
        if(restaurants.isEmpty())logger.info("No restaurants found with name {}",name);
        else logger.info("Found {} restaurants with name {}",restaurants.size(),name);
        return restaurants;
    }

    @Override
    public List<Restaurant> searchRestaurantsByCity(String city) {
        List<Restaurant> restaurants=restaurantRepo.findAllByCity(city);
        if(restaurants.isEmpty())logger.info("No restaurants found with city {}",city);
        else logger.info("Found {} restaurants with city {}",restaurants.size(),city);
        return restaurants;
    }

    @Override
    public List<Restaurant> searchRestaurantsByState(String state) {
        List<Restaurant> restaurants=restaurantRepo.findAllByState(state);
        if(restaurants.isEmpty())logger.info("No restaurants found with state {}",state);
        else logger.info("Found {} restaurants with state {}",restaurants.size(),state);
        return restaurants;
    }

    @Override
    public List<Restaurant> searchRestaurantsByRatingRange(double start, double end) {
        List<Restaurant> restaurants=restaurantRepo.findAllByRatingBetween(start,end);
        if(restaurants.isEmpty())logger.info("No restaurants found with rating between {} and {}",start,end);
        else logger.info("Found {} restaurants with rating between {} and {}",restaurants.size(),start,end);
        return restaurants;
    }

    @Override
    public List<Restaurant> searchRestaurantsByPincode(int pincode) {
        List<Restaurant> restaurants=restaurantRepo.findAllByPincode(pincode);
        if(restaurants.isEmpty())logger.info("No restaurants found with pincode {}",pincode);
        else logger.info("Found {} restaurants with pincode {}",restaurants.size(),pincode);
        return restaurants;
    }

    @Override
    public List<Restaurant> searchRestaurantsByArea(String area) {
        List<Restaurant> restaurants=restaurantRepo.findAllByArea(area);
        if(restaurants.isEmpty())logger.info("No restaurants found with area {}",area);
        else logger.info("Found {} restaurants with area {}",restaurants.size(),area);
        return restaurants;
    }

    @Override
    @Transactional
    public void updateRestaurantRating(long restaurantId, double rating) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+restaurantId+" not found"));
        double oldRating=restaurant.getRating()*restaurant.getRatingCount();
        oldRating=oldRating+rating;
        restaurant.setRatingCount(restaurant.getRatingCount()+1);
        restaurant.setRating(oldRating/restaurant.getRatingCount());
        restaurantRepo.saveAndFlush(restaurant);
        logger.info("Rating of Restaurant {} - {}  updated to {} successfully",restaurantId,restaurant.getName(),restaurant.getRating() );
    }

    @Override
    public Menu addMenuToRestaurant(long restaurantId, Menu menu) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+restaurantId+" not found"));
        if(restaurant.getMenu()==null){
            restaurant.setMenu(new ArrayList<>());
        }
        restaurant.getMenu().add(menu);
        menu.setRestaurant(restaurant);
        restaurantRepo.save(restaurant);
        logger.info("Menu has been added for restaurant with id {}",restaurantId);
        menuRepo.save(menu);
        return menu;
    }

    @Override
    public void deleteMenuFromRestaurant(long restaurantId, long menuId) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+restaurantId+" not found"));
        Menu menu=menuRepo.findById(menuId).orElseThrow(()->new MenuNotFoundException(("Menu with id "+menuId+" not found")));
        restaurant.getMenu().remove(menu);
        menu.setRestaurant(null);
        restaurantRepo.save(restaurant);
        menuRepo.delete(menu);
        logger.info("Menu with id {} deleted from restaurant with id {}",menuId,restaurantId);
    }

    @Override
    public List<Dish> getDishesFromRestaurant(long restaurantId) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+restaurantId+" not found"));
        List<Menu> menus=restaurant.getMenu();
        if(menus==null || menus.isEmpty()){
            logger.warn("No dishes found for restaurant with id {} ",restaurantId);
            return List.of();
        }
        else logger.info("All dishes of restaurant with id {} found successfully",restaurantId);
        List<Dish> ans=new ArrayList<>();
        for(Menu menu:menus){
            ans.addAll(menu.getDishes());
        }
        return ans;
    }

    @Override
    public List<Menu> getMenusFromRestaurant(long restaurantId) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+restaurantId+" not found"));
        List<Menu> menus=restaurant.getMenu();
        if(menus==null || menus.isEmpty())logger.warn("No menus found for restaurant with id {}",restaurantId);
        else logger.info("All menus of restaurant with id {} found successfully",restaurantId);
        return menus;
    }
}
