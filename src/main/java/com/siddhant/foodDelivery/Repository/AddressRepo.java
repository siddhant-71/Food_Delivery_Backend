package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(long userId);
    boolean existsByUserId(long userId);
    void deleteByUserId(long userId);
    List<Address> findAllByCity(String city);
    List<Address> findAllByState(String state);
    List<Address> findAllByPincode(int pincode);
    List<Address> findAllByArea(String area);
}
