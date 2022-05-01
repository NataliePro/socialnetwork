package com.chensy.socialnetwork.controllers;


import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static com.chensy.socialnetwork.utils.ServerUtils.getUserFromSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/users")
    public String getUserList(@RequestParam(value = "search", required = false) String search,
                              @RequestParam(value = "page", required = false) Integer page,
                              Model model) {

        model.addAttribute("userList", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/search")
    public String getUsersByPrefix(@RequestParam(value = "firstprefix", required = false) String firstPrefix,
                                   @RequestParam(value = "lastprefix", required = false) String lastprefix,
                                   Model model) {

        model.addAttribute("userList", userService.getUserByFirstNameAndLastNamePrefix(firstPrefix, lastprefix));
        return "users";

    }
}
