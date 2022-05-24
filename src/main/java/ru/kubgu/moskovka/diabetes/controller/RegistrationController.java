package ru.kubgu.moskovka.diabetes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kubgu.moskovka.diabetes.dao.UserDAO;
import ru.kubgu.moskovka.diabetes.entity.User;

@Controller
public class RegistrationController {

    private UserDAO userDAO = new UserDAO();

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user){
        userDAO.save(user);
        return "redirect:/diabetesTest";
    }
}
