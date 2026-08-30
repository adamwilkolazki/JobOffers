package com.juniorjavajoboffers.infrastructure.sercurity.configuration;

import com.juniorjavajoboffers.domain.loginandregister.LoginAndRegisterFacade;
import com.juniorjavajoboffers.domain.loginandregister.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@AllArgsConstructor

public class LoginUserDetailsService implements UserDetailsService {

    private final LoginAndRegisterFacade loginFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        UserDto userByUsername = loginFacade.findUserByUsername(username);

        return getUser(userByUsername);
    }

    private org.springframework.security.core.userdetails.User getUser(UserDto userDto) {
        return new User(userDto.username(), userDto.password(), Collections.emptyList());
    }
}
