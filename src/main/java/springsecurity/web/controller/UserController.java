package springsecurity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springsecurity.business.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String URL_ROOT = "/user";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String admin(Model model) {
        System.out.println("Метод admin");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.addAttribute("isadmin", roles.contains("ROLE_ADMIN"));
        model.addAttribute("user", userService.getUserByName(auth.getName()));
        model.addAttribute("urlRoot", URL_ROOT);
        return "user";
    }

    @PostMapping(value = "", params = "logout")
    public String logout(ModelMap model) {
        System.out.println("Метод logout");
        return "redirect:/login";
    }



}
