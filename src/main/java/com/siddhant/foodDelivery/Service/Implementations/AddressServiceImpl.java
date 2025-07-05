package com.siddhant.foodDelivery.Service.Implementations;

import com.siddhant.foodDelivery.Entities.Address;
import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Exceptions.AddressExceptions.AddressNotFoundException;
import com.siddhant.foodDelivery.Exceptions.UserException.UserNotFoundException;
import com.siddhant.foodDelivery.Repository.AddressRepo;
import com.siddhant.foodDelivery.Repository.UserRepo;
import com.siddhant.foodDelivery.Service.Interfaces.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {
    private static final Logger logger= LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Override
    public Address addAddressForUser(long userId, Address address) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        if(address==null)throw new NullPointerException("Address is null");
        address.setUser(user);
        addressRepo.save(address);
        if(user.getAddresses()==null){
            user.setAddresses(List.of(address));
            if(user.getAddresses().size()==1)user.setPrimaryAddress(address);
            return address;
        }
        user.getAddresses().add(address);
        if(user.getAddresses().size()==1)user.setPrimaryAddress(address);
        userRepo.save(user);
        return address;
    }
    @Override
    public Address updateAddress(long oldAddressId, Address address) {
        Address oldAddress=addressRepo.findById(oldAddressId).orElseThrow(()->new AddressNotFoundException("Address Not Found"));
        if(address.getArea()!=null)oldAddress.setArea(address.getArea());
        if(address.getCity()!=null)oldAddress.setCity(address.getCity());
        if(address.getPincode()!=0)oldAddress.setPincode(address.getPincode());
        if(address.getLandmark()!=null)oldAddress.setLandmark(address.getLandmark());
        if(address.getHouseNo()!=null)oldAddress.setHouseNo(address.getHouseNo());
        if(address.getStreet()!=null)oldAddress.setStreet(address.getStreet());
        addressRepo.save(oldAddress);
        logger.info("Address with id {} updated successfully ",oldAddressId);
        return oldAddress;
    }

    @Override
    public void deleteAddress(long AddressId) {
        Address address=addressRepo.findById(AddressId).orElseThrow(()->new AddressNotFoundException("Address Not Found"));
        addressRepo.deleteById(AddressId);
        logger.info("Address with id {} deleted successfully " ,AddressId);
    }

    @Override
    public Address getAddressbyId(long AddressId) {
        return addressRepo.findById(AddressId).orElseThrow(()->new AddressNotFoundException("Address Not Found"));
    }

    @Override
    public List<Address> getAllAddress() {
        if(addressRepo.count()==0)logger.info("No Addresses found");
        return addressRepo.findAll();
    }

    @Override
    public void setPrimaryAddress(long userId, long AddressId) {
        User user=userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Address address=addressRepo.findById(AddressId).orElseThrow(()->new AddressNotFoundException("Address Not Found"));
        user.setPrimaryAddress(address);
        userRepo.save(user);
        logger.info("Address with id {} set as primary address for user with id {}",AddressId,userId);
    }

    @Override
    public List<Address> getAllAddressOfUsers(long UserId) {
        User user=userRepo.findById(UserId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        if(user.getAddresses().isEmpty())logger.warn("No Addresses found for user with id {} ",UserId);
        return user.getAddresses();
    }

    @Override
    public Address getPrimaryAddressForUser(long UserId) {
        User user=userRepo.findById(UserId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        if(user.getPrimaryAddress()==null){
            throw new AddressNotFoundException("Primary Address Not Found");
        }
        return user.getPrimaryAddress();
    }
}
