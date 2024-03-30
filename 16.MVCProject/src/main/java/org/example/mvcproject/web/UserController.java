package org.example.mvcproject.web;

import org.example.mvcproject.models.dto.user.UserLoginDto;
import org.example.mvcproject.models.dto.user.UserRegisterDto;
import org.example.mvcproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/users/register")
    public String doRegister(@Valid UserRegisterDto userRegisterDto) {
        boolean success = userService.register(userRegisterDto);

        if (success) {
            return "redirect:/";
        }

        return "user/register";
    }

    @GetMapping("/users/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/users/login")
    public ModelAndView doLogin(@Valid UserLoginDto userLoginDto, BindingResult bindingResult) {
        if (userLoginDto.getUsername().equals("asdfgh") &&
                userLoginDto.getPassword().equals("123456")) {

            ModelAndView result = new ModelAndView();
            result.setViewName("redirect:/home");

            return result;
        }

        List<String> errors = bindingResult
                .getAllErrors()
                .stream()
                .map(e -> e.getObjectName() + " " + e.getDefaultMessage())
                .toList();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");
        modelAndView.addObject("errors", errors);

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout() {

        return "home";
    }

}
