package com.hotel.reservationSystem.controllers.validators;

import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.models.dto.UserDto;
import com.hotel.reservationSystem.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return  User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto person = (UserDto) o;

        if (userService.findByEmail(person.getEmail()).isEmpty()) {
            return;
        }

        errors.rejectValue("email", "", "User with this email already exists");
    }
}