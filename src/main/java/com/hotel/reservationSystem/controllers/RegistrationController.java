package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.controllers.validators.UserValidator;
import com.hotel.reservationSystem.models.dto.UserDto;
import com.hotel.reservationSystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private UserService userService;
    private UserValidator userValidator;


    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator){
         this.userService = userService;
         this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserDto userDto,
            BindingResult bindingResult) {

        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/registration";
        }

        userService.registerNewUserAccount(userDto);

        return "successRegister";
    }

}
