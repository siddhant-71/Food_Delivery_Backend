package com.siddhant.foodDelivery.Controllers;

import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Service.Implementations.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishServiceImpl dishService;

    @GetMapping("/{id}")
    Dish getById(@PathVariable("id")long id){
        return dishService.getDishById(id);
    }
    @PostMapping("/")
    void addDish(@RequestBody Dish dish){
        dishService.addDish(dish);
    }
    @DeleteMapping("/{id}")
    void delete(@PathVariable("id")long id){
        dishService.deleteDish(id);
    }
    @PutMapping("/{id}")
    void update(@PathVariable("id")long id, @RequestBody Dish dish){
        dishService.updateDish(id,dish);
    }
}
