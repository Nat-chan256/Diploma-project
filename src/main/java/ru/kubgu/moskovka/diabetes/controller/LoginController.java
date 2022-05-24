package ru.kubgu.moskovka.diabetes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kubgu.moskovka.diabetes.dao.UserDAO;
import ru.kubgu.moskovka.diabetes.entity.User;

@Controller
public class LoginController {

    private UserDAO userDAO = new UserDAO();

    @GetMapping("/diabetes")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("error", "");
        return "login";
    }

    @PostMapping("/diabetes")
    public String login(@ModelAttribute User user, Model model){
        User userFromDB = userDAO.show(user.getLogin());
        if (userFromDB == null){
            model.addAttribute("error", "There is no user with login " + user.getLogin());
            return "redirect:/diabetes";
        }
        if (userFromDB.getPassword().equals(user.getPassword())){
            model.addAttribute("error", "The password is incorrect");
            return "redirect:/diabetes";
        }
        return "redirect:/diabetesTest";
    }
}
