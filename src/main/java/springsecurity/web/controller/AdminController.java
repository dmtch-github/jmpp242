package springsecurity.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springsecurity.business.entities.Roles;
import springsecurity.business.entities.User;
import springsecurity.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String URL_ROOT = "/admin";
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Главная страница Администратора.
     * Отображает данные о всех пользователях системы.
     * Предоставляет инструменты для:
     *      добавления нового пользователя
     *      редактирования данных пользователя
     *      удаления пользователя
     */
    @GetMapping("")
    public String admin(Model model) {
        System.out.println("Метод admin");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.addAttribute("isuser", roles.contains("ROLE_USER"));
        System.out.println("admin: Передал в модель " + roles.contains("ROLE_USER"));

        model.addAttribute("listUsers", userService.getUsers()); //new ArrayList<User>()); //
        model.addAttribute("urlRoot", URL_ROOT);
        return "admin";
    }

    /**
     * Удаляет пользователя из БД и
     * перенаправляет на главную страницу
     */
    @PostMapping(value = "/{id}", params = "delete")
    public String deleteUser(ModelMap model,
                             @PathVariable("id") int id) {
        System.out.println("Нажата кнопка УДАЛИТЬ с id=" + id);
        userService.deleteUser(id);
        return "redirect:" + URL_ROOT;
    }

    /**
     * Открывает форму для редактирования
     * данных пользователя с id
     */
    @PostMapping(value = "/{id}", params = "update")
    public String updateUser(@PathVariable("id") int id,
                             Model model) {
        System.out.println("Нажата кнопка ИЗМЕНИТЬ с id=" + id);
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("urlRoot", URL_ROOT);
        return "edit-user";
    }

    /**
     * Открывает форму для редактирования
     * данных нового пользователя
     */
    @PostMapping(value = "", params = "add")
    public String addUser(ModelMap model) {
        System.out.println("Метод addUser");
        User user = new User();
        user.setTextRoles(Roles.USER);
        model.addAttribute("user", user);
        model.addAttribute("urlRoot", URL_ROOT);
        return "edit-user";
    }

    /**
     * Сохраняет данные пользователя в БД и
     * перенаправляет на главную страницу
     */
    @PostMapping(value = "", params = "save")
    public String saveUser(User user) {
        System.out.println("Метод saveUser");
        userService.saveUser(user);
        return "redirect:" + URL_ROOT;
    }

    /**
     * Перенаправляет на главную страницу.
     * Используется при отмене операций: Сохранить, Изменить
     */
    @PostMapping(value = "", params = "redirect")
    public String redirect(ModelMap model) {
        System.out.println("Метод redirect");
        return "redirect:" + URL_ROOT;
    }

    @PostMapping(value = "", params = "logout")
    public String logout(ModelMap model) {
        System.out.println("Метод logout");
        return "redirect:/login";
    }

}
