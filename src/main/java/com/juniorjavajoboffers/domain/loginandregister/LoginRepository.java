package com.juniorjavajoboffers.domain.loginandregister;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends MongoRepository<User, String> {

    Optional<User> findUserByUsername(String username);


    <S extends User> S save(S entity);
}
