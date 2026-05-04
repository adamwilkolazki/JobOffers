package com.juniorjavajoboffers.domain.loginandregister;

import com.juniorjavajoboffers.domain.loginandregister.InMemoryLoginRepository;
import com.juniorjavajoboffers.domain.loginandregister.LoginAndRegisterFacade;
import com.juniorjavajoboffers.domain.loginandregister.RegisterUserDto;
import com.juniorjavajoboffers.domain.loginandregister.RegistrationResultDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class LoginAndRegisterFacadeTest {


    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(new InMemoryLoginRepository());

    @Test
    public void should_register_new_user(){
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");

        //when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.registerUser(registerUserDto);

        //then
        assertThat(registrationResultDto.created()).isEqualTo(true);
        assertThat(registrationResultDto.username()).isEqualTo("username");
    }
    @Test
    public void should_find_user_by_username(){
        //given
        String username = "username";
        RegisterUserDto registerUserDto = new RegisterUserDto(username, "password");
        loginAndRegisterFacade.registerUser(registerUserDto);
        //when
        UserDto userByUsername = loginAndRegisterFacade.findUserByUsername(username);

        //then
        assertThat(userByUsername.username()).isEqualTo("username");
        assertThat(userByUsername.password()).isEqualTo("password");
    }
    @Test
    public void should_throw_user_not_found_exception_when_user_not_exist_in_database(){
        //given
        String username = "username";
        //when
        Throwable throwable = catchThrowable(()->loginAndRegisterFacade.findUserByUsername(username));
        //then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("user not found");
    }
}