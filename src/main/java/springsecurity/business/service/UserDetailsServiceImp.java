package springsecurity.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.business.dao.UserDao;
import springsecurity.business.entities.Role;
import springsecurity.business.entities.Roles;
import springsecurity.business.entities.User;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImp(UserService userService) {
        this.userService = userService;
    }

    /**
     * Здесь происходит поиск пользователя по имени.
     * Далее система проверит пароль и авторизует по роли, имя больше не проверяется!
     * Проверяем в БД наличие пользователей, если их нет совсем,
     * то активируем пользователя admin с паролем admin и ролью ADMIN
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Запрос для юзера " + username);
        User user = userService.getUserByName(username);
        System.out.println("АВТОРИЗОВАЛ из БАЗЫ============================================");
        if(user == null && username.equals("admin")) {
            //здесь сначала проверить, что в БД нет ни одной записи User
            user = new User(1, "admin", "Admin", "Admin",
                    (byte) 32, "admin", Collections.singleton(new Role(Roles.ROLE_ADMIN_ID, Roles.ROLE_ADMIN)));
        }
        return user;
    }
}

