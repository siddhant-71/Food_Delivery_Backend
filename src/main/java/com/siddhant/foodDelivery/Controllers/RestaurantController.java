package com.siddhant.foodDelivery.Controllers;

import com.siddhant.foodDelivery.Entities.Restaurant;
import com.siddhant.foodDelivery.Service.Implementations.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantServiceImpl;

    @GetMapping("/")
    public List<Restaurant> all(){
        return restaurantServiceImpl.getAllRestaurants();
    }
    @GetMapping("/{id}")
    public Restaurant findByid(@PathVariable("id")long id){
        return restaurantServiceImpl.getRestaurantById(id);
    }
    @GetMapping("/name/{name}")
    public List<Restaurant> findByName(@PathVariable("name")String name){
        return restaurantServiceImpl.searchRestaurantsByName(name);
    }
    @GetMapping("/area/{area}")
    public List<Restaurant> findByArea(@PathVariable("area")String area){
        return restaurantServiceImpl.searchRestaurantsByArea(area);
    }
    @GetMapping("/city/{city}")
    public List<Restaurant> findByCity(@PathVariable("city")String city){
        return restaurantServiceImpl.searchRestaurantsByCity(city);
    }
    @GetMapping("/state/{state}")
    public List<Restaurant> findByState(@PathVariable("state")String state){
        return restaurantServiceImpl.searchRestaurantsByState(state);
    }
    @GetMapping("/rating/{rating}")
    public List<Restaurant> findByRating(@PathVariable("rating") double rating){
        return restaurantServiceImpl.searchRestaurantsByRatingRange(rating,5.00);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")long id){
        Restaurant rest=restaurantServiceImpl.getRestaurantById(id);
        restaurantServiceImpl.deleteRestaurant(rest);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id")long id,@RequestBody Restaurant restaurant){
        restaurantServiceImpl.updateRestaurant(id,restaurant);
    }

    @PostMapping("/add")
    public void add(@RequestBody Restaurant restaurant){
        restaurantServiceImpl.registerRestaurant(restaurant);
    }


}
