package com.siddhant.foodDelivery.Repository;

import com.siddhant.foodDelivery.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAllByRole(String role);
    List<User> findAllByName(String name);
    Optional<User> findByPhone(String phone);
    boolean existsByPhone(String phone);
}


/*
save(S entity)

saveAll(Iterable<S> entities)

findById(ID id)

existsById(ID id)

findAll()

findAllById(Iterable<ID> ids)

count()

deleteById(ID id)

delete(T entity)

deleteAllById(Iterable<? extends ID> ids)

deleteAll(Iterable<? extends T> entities)

deleteAll()
 */