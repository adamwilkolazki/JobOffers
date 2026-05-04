package com.juniorjavajoboffers.domain.loginandregister;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;



public class InMemoryLoginRepository implements LoginRepository {

    private Map<String, User> database = new HashMap<>();


    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(database.get(username));
    }

    @Override
    public User save(User entity) {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id.toString())
                .username(entity.username())
                .password(entity.password()).build();
        database.put(user.username(), user);
        return user;
    }
}
