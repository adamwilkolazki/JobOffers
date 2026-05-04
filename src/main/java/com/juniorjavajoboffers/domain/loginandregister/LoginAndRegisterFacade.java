package com.juniorjavajoboffers.domain.loginandregister;


import lombok.AllArgsConstructor;

@AllArgsConstructor
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
                .orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));
     }

}
