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
@RequestMapping("/admin")
public class AdminController {

    private static final String URL_ROOT = "/admin";
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

/*

POST это добавить, PATCH или PUT это обновить.

    <form th:method="GET" th:action="@{/admin/{id}/edit(id=${user.getId()})}">
        <input type="submit" value="EDIT">
    </form>

<form th:method="GET" th:action="@{/admin/new}">
    <input type="submit" value="Create new User">
</form>
    это к контроллерам чтоб запрос отправиить

        <form th:method="PATCH" th:action="@{/admin/{id}(id=${user.getId()})}" th:object="${user}">
    Обновление

<form th:method="POST" th:action="@{/admin}" th:object="${user}">
    Создание


    так ты в цикле же это делаешь, у тебя разница будет только в id запроса

th:action="@{/admin/{id}(id=${user.getId()})}"
 */



    @GetMapping("")
    public String admin(Model model) {
        System.out.println("Метод admin");
        model.addAttribute("listUsers", userService.getUsers());
        model.addAttribute("urlRoot", URL_ROOT);
        return "admin";
    }

    @PostMapping(value = "", params = "delete")
    public String deleteUser(ModelMap model, HttpServletRequest req) {
        System.out.println("Нажата кнопка Delete");
//        int id = Integer.parseInt(req.getParameter("idDelete"));
//        userService.deleteUser(id);
        return "redirect:" + URL_ROOT;
    }

//    @PostMapping(value = "", params = "update")
//    public String updateUser(ModelMap model, HttpServletRequest req) {
//        System.out.println("Нажата кнопка Edit");
//        return "redirect:" + URL_ROOT;
//    }

    @PostMapping(value = "", params = {"add", "update"})
    public String addUser(ModelMap model,
                          HttpServletRequest req,
                          @RequestParam(value="add", required = false) String strAdd,
                          @RequestParam(value="update", required = false) String strUpdate) {
        System.out.println("Метод addUser atrAdd=\"" + strAdd + "\" strUpdate=\"" +strUpdate+"\"");
        model.addAttribute("user", new User());
        model.addAttribute("urlRoot", URL_ROOT);
        return "edit-user";
    }





//    // Когда запрошенный URI / method7 / 123, значение id равно 123
//    @RequestMapping(value = "/method7/{id}")
//    public String method7(
//            @PathVariable("id") Integer id
//    ) {
//        return "method7 with id=" + id;
//    }

    /**
     * Выдает данные пользователя на форму редактирования.
     * Используется в операциях: Сохранить, Изменить
     */
//    @PostMapping(value = "", params = "add") //,"update"})
//    //TODO здесь не могу взять 2 параметра почему?
//    public String editUser(ModelMap model, HttpServletRequest req) {
//        System.out.println("Метод editUser");
//        String num = req.getParameter("update");
//        if (num == null) {
//            model.addAttribute("user", new User());
//        } else {
//            try {
//                int id = Integer.parseInt(num);
//                model.addAttribute("user", userService.getUser(id));
//            } catch(NumberFormatException e) {
//                //TODO как обработать эту ошибку, что выдать на форму?
//            }
//        }
//        model.addAttribute("urlRoot", URL_ROOT);
//        return "edit-user";
//    }

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
    public String adminRedirect(ModelMap model) {
        System.out.println("Метод adminRedirect");
        return "redirect:" + URL_ROOT;
    }




//    public String deleteUser(ModelMap model, HttpServletRequest req) {
//        int id = Integer.parseInt(req.getParameter("idDelete"));
//        userService.deleteUser(id);
//        return "redirect:/";
//    }

/***********************************************************************************************/
    /**
     * Главная страница Администратора.
     * Отображает данные о всех пользователях системы.
     * Предоставляет инструменты для:
     *      добавления нового пользователя
     *      редактирования данных пользователя
     *      удаления пользователя
     */

    /**
     * Удаляет пользователя из БД и
     * перенаправляет на главную страницу
     */



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
