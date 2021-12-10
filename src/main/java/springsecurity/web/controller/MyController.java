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


//    @GetMapping("/login")
//    public String getLogin(Model model) {
//        model.addAttribute("title", "Форма входа");
//        return "login";
//    }


/* От Сергея

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

@PatchMapping("/{id}")
public String update(@ModelAttribute("users") User user, @PathVariable("id") Long id) {
    System.out.println("_______________________________"+user.getUserName()+user.getEmail()+id);
    userService.update(user, id);
    return "redirect:/admin";
}


 */



}
