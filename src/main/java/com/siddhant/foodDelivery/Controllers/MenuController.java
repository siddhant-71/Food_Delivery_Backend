package com.siddhant.foodDelivery.Controllers;

import com.siddhant.foodDelivery.Entities.Menu;
import com.siddhant.foodDelivery.Service.Implementations.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuServiceImpl menuService;
    
    @GetMapping("/restaurant/{id}")
    List<Menu> getAll(@PathVariable("id")long id){
        return menuService.getAllMenusOfRestaurant(id);
    }
    @GetMapping("/{id}")
    Menu getById(@PathVariable("id") long id){
        return menuService.getMenuById(id);
    }
    @DeleteMapping("/{id}")
    void Delete(@PathVariable("id")long id){
        menuService.deleteMenu(id);
    }
    @PutMapping("/{id}")
    void update(@PathVariable("id")long id,@RequestBody Menu menu){
        menuService.updateMenu(id,menu);
    }
    @PostMapping("/")
    void add(@RequestBody Menu menu){
        menuService.addd(menu);
    }
    
    
    
}
