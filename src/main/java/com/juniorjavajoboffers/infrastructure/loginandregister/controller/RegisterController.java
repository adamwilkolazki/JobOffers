package com.juniorjavajoboffers.infrastructure.loginandregister.controller;

import com.juniorjavajoboffers.domain.loginandregister.LoginAndRegisterFacade;
import com.juniorjavajoboffers.domain.loginandregister.RegisterUserDto;
import com.juniorjavajoboffers.domain.loginandregister.RegistrationResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> registerNewUser(@RequestBody RegisterUserDto newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.password());
        RegisterUserDto userToRegister = new RegisterUserDto(newUser.username(), encodedPassword);
        RegistrationResultDto registeredUser = loginAndRegisterFacade.registerUser(userToRegister);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

    }
}




