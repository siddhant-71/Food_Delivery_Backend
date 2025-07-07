package com.siddhant.foodDelivery.Controllers;


import com.siddhant.foodDelivery.Entities.Address;
import com.siddhant.foodDelivery.Service.Implementations.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address/")
public class AddressController {
    @Autowired
    AddressServiceImpl addressService;
    @PostMapping("{id}")
    void add(@RequestBody Address address,@PathVariable("id")long id){
        addressService.addAddressForUser(id,address);
    }
    @PutMapping("{id}")
    void update(@RequestBody Address address, @PathVariable("id")long id){
        addressService.updateAddress(id,address);
    }
    @DeleteMapping("{id}")
    void delete(@PathVariable("id")long id){
        addressService.deleteAddress(id);
    }
    @GetMapping("{id}")
    Address getById(@PathVariable("id")long id){
        return addressService.getAddressbyId(id);
    }
    @GetMapping((""))
    List<Address> getAll(){
        return addressService.getAllAddress();
    }
    @GetMapping("users/{id}")
    List<Address> getUserAdd(@PathVariable("id")long id){
        return addressService.getAllAddressOfUsers(id);
    }
    @GetMapping("primary/{id}")
    Address getPrimary(@PathVariable("id")long id){
        return addressService.getPrimaryAddressForUser(id);
    }
}
