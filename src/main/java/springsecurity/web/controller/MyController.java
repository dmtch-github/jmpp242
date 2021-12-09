package springsecurity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springsecurity.business.service.UserService;

@Controller
public class MyController {

    private final UserService userService;

    @Autowired
    public MyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String get(Model model)
    {
        model.addAttribute("get", "GET");
        model.addAttribute("put", "POST");
        System.out.println("Обработан метод GET");
        return "test";
    }

    @PostMapping("/")
    public String post(Model model)
    {
        model.addAttribute("get", "POST");
        model.addAttribute("put", "PUT");
        System.out.println("Обработан метод POST");
        return "test";
    }

    @PutMapping("/")
    public String put(Model model)
    {
        model.addAttribute("get", "PUT");
        model.addAttribute("put", "DELETE");
        System.out.println("Обработан метод PUT");
        return "test";
    }

    @DeleteMapping("/")
    public String delete(Model model)
    {
        model.addAttribute("get", "DELETE");
        model.addAttribute("put", "PATCH");
        System.out.println("Обработан метод DELETE");
        return "test";
    }

    @PatchMapping("/")
    public String patch(Model model)
    {
        model.addAttribute("get", "PATCH");
        model.addAttribute("put", "GET");
        System.out.println("Обработан метод PATCH");
        return "test";
    }


//    GET
//    POST
//    PUT
//    DELETE
//    PATCH


    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("title", "Форма входа");
        return "login";
    }




}
