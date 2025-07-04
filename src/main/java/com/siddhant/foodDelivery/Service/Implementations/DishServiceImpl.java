package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Entities.Dish;
import com.siddhant.foodDelivery.Exceptions.DishExceptions.DishNotFoundException;
import com.siddhant.foodDelivery.Repository.DishRepo;
import com.siddhant.foodDelivery.Service.Interfaces.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {

    private static final Logger logger= LoggerFactory.getLogger(DishServiceImpl.class);
    @Autowired
    private DishRepo dishRepo;
    @Override
    public Dish addDish(Dish dish) {
        dishRepo.save(dish);
        logger.info("Dish with name {} added successfully",dish.getName());
        return dish;
    }

    @Override
    public Dish updateDish(long dishId, Dish updatedDish) {
        Dish dish=dishRepo.findById(dishId).orElseThrow(()->new DishNotFoundException("Dish with id "+dishId+" not found"));
        if(updatedDish.getName()!=null)dish.setName(updatedDish.getName());
        if(updatedDish.getPrice()!=0.00)dish.setPrice(updatedDish.getPrice());
        if(updatedDish.getDescription()!=null)dish.setDescription(updatedDish.getDescription());
        if(updatedDish.getImage()!=null)dish.setImage(updatedDish.getImage());
        dishRepo.save(dish);
        logger.info("Dish with name {} and dishId {} updated successfully",dish.getName(),dish.getId());
        return dish;
    }

    @Override
    public void deleteDish(long dishId) {
        Dish dish=dishRepo.findById(dishId).orElseThrow(()->new DishNotFoundException("dish with dishId "+dishId+" was not found"));
        dishRepo.delete(dish);
        logger.info("Dish with name {} and dishId {} deleted successfully",dish.getName(),dish.getId());
    }

    @Override
    public Dish getDishById(long DishId) {
        return dishRepo.findById(DishId).orElseThrow(()->new DishNotFoundException("Dish with id "+DishId+" not found"));
    }
}
