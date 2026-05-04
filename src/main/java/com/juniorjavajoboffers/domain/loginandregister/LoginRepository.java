package com.juniorjavajoboffers.domain.loginandregister;

import java.util.Optional;

public interface LoginRepository {

    Optional<User> findUserByUsername(String username);

    User save(User user);
}
