package com.juniorjavajoboffers.domain.loginandregister;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {
private static final String USER_NOT_FOUND = "user not found";

    private  final LoginRepository repository;

    public RegistrationResultDto registerUser(RegisterUserDto user){
        final User newUser = User.builder()
                .username(user.username())
                .password(user.password())
                .build();
        User savedUser = repository.save(newUser);
        return new RegistrationResultDto(savedUser.id(),savedUser.username(),true);
    }

    public UserDto findUserByUsername(String username){
        return repository.findUserByUsername(username)
                .map(user -> new UserDto(user.id(),user.username(),user.password()))
                .orElseThrow(()-> new BadCredentialsException(USER_NOT_FOUND));
     }

}
