package com.repository;

import com.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

//public interface UserRepository extends JpaRepository<User,Integer> {
public interface UserRepository extends MongoRepository<User,Integer> {

    @Override
    Optional<User> findById(Integer integer);

    Optional<User> findByName(String name);

    List<User> findAll();

}
