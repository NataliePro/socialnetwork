package com.chensy.socialnetwork.controllers;


import com.chensy.socialnetwork.UserSearch;
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

    public static final int MAX_USERS_COUNT_PER_PAGE = 30;
    private final UserService userService;

    @GetMapping("/users")
    public String getUserList(Model model) {

        model.addAttribute("userList", userService.getRecentUsers(MAX_USERS_COUNT_PER_PAGE));
        model.addAttribute("userSearch", new UserSearch());
        return "users";
    }

    @GetMapping("/search")
    public String getUsersByPrefix(@RequestParam(value = "firstName", required = false) String firstName,
                                   @RequestParam(value = "lastName", required = false) String lastName,
                                   Model model) {

        model.addAttribute("userList", userService.getUserByFirstNameAndLastNamePrefix(firstName, lastName));
        model.addAttribute("userSearch", new UserSearch().setFirstName(firstName).setLastName(lastName));
        return "users";

    }
}
