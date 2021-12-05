package springsecurity.web.controller;

import springsecurity.business.entities.User;
import springsecurity.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({"/", "/saveUser"})
    public String showAllUsers(ModelMap model) {
        model.addAttribute("listUsers", userService.getUsers());
        return "show-all-users";
    }

    @RequestMapping(value = "/idUser", params = {"idDelete"})
    public String deleteUser(ModelMap model, HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("idDelete"));
        userService.deleteUser(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/idUser", params = {"idUpdate"})
    public String updateUser(ModelMap model, HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("idUpdate"));
        model.addAttribute("user", userService.getUser(id));
        return "show-user";
    }

    @RequestMapping("/addUser")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "show-user";
    }

    @RequestMapping(value = "/saveUser", params = {"save"})
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

}
