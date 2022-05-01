package com.chensy.socialnetwork.controllers;

import com.chensy.socialnetwork.dto.PasswordChangeDTO;
import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.chensy.socialnetwork.utils.ServerUtils.getUserFromSession;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class SettingsController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/settings")
    public String getSettingsPage(Model model) {
        model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
        return "settings";
    }

    @PostMapping("/settings")
    public String updateSettings(HttpServletRequest request, @ModelAttribute("user") UserDTO user)  {
        userService.updateUser(user);

        request.getSession().setAttribute("user", userService.getUserByEmail(user.getEmail()));
        return "redirect:/user/settings";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute("passwordChangeDTO") PasswordChangeDTO passwordChangeDTO,
                                 BindingResult bindingResult, HttpServletRequest request, Model model) {
        if(bindingResult.hasErrors()) {
            return "settings";
        }
        UserDTO user = getUserFromSession(request);
        String password = user.getPassword();
        boolean passwordsMatch = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), password);
        if(!passwordsMatch) {
            model.addAttribute("passwordError", "Passwords doesn't match");
        } else {
            userService.updatePassword(passwordChangeDTO.getPassword(), user.getId());
            request.getSession().setAttribute("user", userService.getUserByEmail(user.getEmail()));
        }
        model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
        return "settings";
    }

}
