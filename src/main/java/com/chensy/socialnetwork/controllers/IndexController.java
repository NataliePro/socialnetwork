package com.chensy.socialnetwork.controllers;


import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.service.CountryService;
import com.chensy.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final CountryService countryService;

    @GetMapping("/")
    public String indexPage(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/user/profile");
            return null;
        }
        model.addAttribute("user", new UserDTO());
        model.addAttribute("countryList", countryService.getAllCountries());
        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "service/access-denied";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        String email = userDTO.getEmail();
        UserDTO userByEmail = userService.getUserByEmail(email);
        if (userByEmail != null) {
            model.addAttribute("registrationError", true);
            model.addAttribute("user", userDTO);
            return "index";
        }
        userService.createUser(userDTO);
        return "service/registration-confirmation";
    }

}
