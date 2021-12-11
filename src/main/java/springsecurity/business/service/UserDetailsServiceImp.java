package springsecurity.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.business.entities.Role;
import springsecurity.business.entities.Roles;
import springsecurity.business.entities.User;

import java.util.Collections;

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
     * то активируем временного пользователя admin с паролем admin и ролью ADMIN
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        if(user == null) {
            if(username.equals("admin") && (userService.getCountUsers() == 0)) {
                //создаем динамического пользователя для работы с системой
                return new User(1, Roles.SUPER_USER_NAME, "Admin", "Admin",
                        (byte) 32, Roles.SUPER_USER_PASSWORD, Collections.singleton(new Role(Roles.ROLE_ADMIN)));
            }

            throw new UsernameNotFoundException("Пользователь " + username + " не найден");
        }
        return user;
    }
}

