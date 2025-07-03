package com.siddhant.foodDelivery.Service.Interfaces;

import com.siddhant.foodDelivery.Entities.Address;

import java.util.List;

public interface AddressService {
    //CRUD
    Address addAddressForUser(long userId,Address address);
    Address updateAddress(long oldAddressId,Address address);
    void deleteAddress(long AddressId);
    Address getAddressbyId(long AddressId);
    List<Address> getAllAddress();


    //USER SPECIFIC
    void setPrimaryAddress(long userId,long AddressId);
    List<Address> getAllAddressOfUsers(long UserId);
    Address getPrimaryAddressForUser(long UserId);
}
