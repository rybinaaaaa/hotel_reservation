package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.util.validators.UserValidator;
import com.hotel.reservationSystem.dto.UserDTO;
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
public class AuthController {

    private final UserService userService;
    private final UserValidator userValidator;


    @Autowired
    public AuthController(UserService userService, UserValidator userValidator){
         this.userService = userService;
         this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/registration2")
    public String newRegistration() {
        return "successRegister";
    }

    @PostMapping("/registration")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDto,
            BindingResult bindingResult) {

        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/registration";
        }

        userService.registerNewUserAccount(userDto);

        return "successRegister";
    }

}
