package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.User;
import com.siddhant.foodDelivery.Enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAllByRole(UserRole role);
    List<User> findAllByName(String name);
    Optional<User> findByPhone(String phone);
    boolean existsByPhone(String phone);
}