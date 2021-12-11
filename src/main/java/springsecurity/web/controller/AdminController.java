package springsecurity.web.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springsecurity.business.entities.Role;
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

    public static final String URL_ROOT = "/admin";
    private static final String NAME_URL_ROOT = "urlRoot";
    private static final String COMMAND_REDIRECT = "redirect:";
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
     *      выхода из системы
     * При наличии роли User показывает ссылку на личный кабинет
     */
    @GetMapping("")
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.addAttribute("isuser", roles.contains("ROLE_USER"));
        model.addAttribute("listUsers", userService.getUsers());
        model.addAttribute(NAME_URL_ROOT, URL_ROOT);
        return "admin";
    }

    /**
     * Удаляет пользователя из БД и
     * перенаправляет на главную страницу
     */
    @PostMapping(value = "/{id}", params = "delete")
    public String deleteUser(ModelMap model,
                             @PathVariable("id") int id) {
        userService.deleteUser(id);
        return COMMAND_REDIRECT + URL_ROOT;
    }

    /**
     * Открывает форму для редактирования
     * данных пользователя с id
     */
    @PostMapping(value = "/{id}", params = "update")
    public String updateUser(@PathVariable("id") int id,
                             Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute(NAME_URL_ROOT, URL_ROOT);
        return "edit-user";
    }

    /**
     * Открывает форму для редактирования
     * данных нового пользователя
     */
    @PostMapping(value = "", params = "add")
    public String addUser(ModelMap model) {
        User user = new User();
        user.setTextRoles(Roles.USER);
        model.addAttribute("user", user);
        model.addAttribute(NAME_URL_ROOT, URL_ROOT);
        return "edit-user";
    }

    /**
     * Сохраняет данные пользователя в БД,
     * динамически меняет права авторизованного администратора
     * и перенаправляет на главную страницу
     */
    @PostMapping(value = "", params = "save")
    public String saveUser(User user) {
        userService.saveUser(user);
        //для текущего пользователя делаем динамическую авторизацию: смена прав
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(((UserDetails) auth.getPrincipal()).getUsername().equals(user.getUsername())) {
            List<String> newRoles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            List<GrantedAuthority> auths = getAuthorities(newRoles);
            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getCredentials(),auths);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        return COMMAND_REDIRECT + URL_ROOT;
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> auths = new ArrayList<>();
        if(!roles.isEmpty()) {
            for (String s : roles) {
                auths.add(new SimpleGrantedAuthority(s));
            }
        }
        return auths;
    }

    /**
     * Перенаправляет на главную страницу.
     * Используется при отмене операций: Сохранить, Изменить
     */
    @PostMapping(value = "", params = "redirect")
    public String redirect(ModelMap model) {
        return COMMAND_REDIRECT + URL_ROOT;
    }
}
