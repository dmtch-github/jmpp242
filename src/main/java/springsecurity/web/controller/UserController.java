package springsecurity.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springsecurity.business.entities.Roles;
import springsecurity.business.entities.User;
import springsecurity.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
//@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
    public String user(Model model) {

        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("listUsers", userService.getUsers());
        model.addAttribute("urlRoot", "/admin");
        return "admin";
    }

        //удаление одного работника
    @PostMapping(value = "/admin", params = "delete")
    public String deleteUser(ModelMap model, HttpServletRequest req) {
        //TODO в delete взять номер для удаления пользователя
        //а вот через RequestParam не могу взять
                             //@RequestParam("id") String num) {
//        System.out.println("num=" + num);
        System.out.println("delete=" + req.getParameter("delete"));
//        int id = Integer.parseInt(req.getParameter("idDelete"));
//        userService.deleteUser(id);
        return "redirect:/admin";
    }



//    // Когда запрошенный URI / method7 / 123, значение id равно 123
//    @RequestMapping(value = "/method7/{id}")
//    public String method7(
//            @PathVariable("id") Integer id
//    ) {
//        return "method7 with id=" + id;
//    }

    @PostMapping(value = "/admin", params = "add") //,"update"})
    //TODO здесь не могу взять 2 параметра почему?
    public String editUser(ModelMap model, HttpServletRequest req) {
        System.out.println("addUser сработал");
        String num = req.getParameter("update");
        if (num == null) {
            model.addAttribute("user", new User());
        } else {
            try {
                int id = Integer.parseInt(num);
                model.addAttribute("user", userService.getUser(id));
            } catch(NumberFormatException e) {
                //TODO как обработать эту ошибку, что выдать на форму?
            }
        }
        model.addAttribute("urlRoot", "/admin");
        return "edit-user";
    }



    @PostMapping(value = "/admin", params = "save")
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin", params = "redirect")
    public String adminRedirect(ModelMap model) {
        return "redirect:/admin";
    }




//    public String deleteUser(ModelMap model, HttpServletRequest req) {
//        int id = Integer.parseInt(req.getParameter("idDelete"));
//        userService.deleteUser(id);
//        return "redirect:/";
//    }



    @GetMapping("/login")
    public String get(Model model) {
        model.addAttribute("title", "Форма входа");
        return "login";
    }



/*
    //получение и отображение всех работников
//    @GetMapping({"/", "/saveUser"})
//    public String showAllUsers(ModelMap model) {
//        model.addAttribute("listUsers", userService.getUsers());
//        return "show-all-users";
//    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }


    @GetMapping("/")
    public String showAllUsers(ModelMap model, HttpServletRequest request) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(request.isUserInRole(Roles.ROLE_ADMIN)) {
            model.addAttribute("listUsers", userService.getUsers());
            return "show-all-users";
        } else if (request.isUserInRole(Roles.ROLE_USER)) {
            model.addAttribute("user", userService.getUser(3));
            return "show-user";
        } else {

        }
        return "redirect:/login";

    }

*/

//
//
//    //получение и отображение ОДНОГО работника
//    @GetMapping(value = "/idUser", params = {"idUser"})
//    public String showOneUser(ModelMap model, HttpServletRequest req) {
//        int id = Integer.parseInt(req.getParameter("idUser"));
//        model.addAttribute("user", userService.getUser(id));
//        return "show-user";
//    }
//
//    //удаление одного работника
//    @DeleteMapping(value = "/idUser", params = {"idDelete"})
//    public String deleteUser(ModelMap model, HttpServletRequest req) {
//        int id = Integer.parseInt(req.getParameter("idDelete"));
//        userService.deleteUser(id);
//        return "redirect:/";
//    }
//
//    @RequestMapping(value = "/idUser", params = {"idUpdate"})
//    public String updateUser(ModelMap model, HttpServletRequest req) {
//        int id = Integer.parseInt(req.getParameter("idUpdate"));
//        model.addAttribute("user", userService.getUser(id));
//        return "show-user";
//    }
//
//    @RequestMapping("/addUser")
//    public String addUser(ModelMap model) {
//        model.addAttribute("user", new User());
//        return "show-user";
//    }
//
//    @RequestMapping(value = "/saveUser", params = {"save"})
//    public String saveUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/";
//    }

}
