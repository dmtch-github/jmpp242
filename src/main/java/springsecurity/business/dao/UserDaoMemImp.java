package springsecurity.business.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import springsecurity.business.entities.Role;
import springsecurity.business.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDaoMemImp implements UserDao {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final Map<Integer, User> users = new HashMap<>();

    @Autowired
    public UserDaoMemImp(InMemoryUserDetailsManager inMemoryUserDetailsManager) {

        User user = new User(0,"dima", "Dima", "Dmitriev",
                (byte)23, "dima@ya.ru", "dima",
                Collections.singleton(new Role(0, "ROLE_USER")));
        users.put(user.getId(),user);
        inMemoryUserDetailsManager.createUser(user);
        user = new User(1,"admin", "Admin", "Adminov",
                (byte)32, "admin@yandex.ru", "admin",
                Collections.singleton(new Role(1, "ROLE_ADMIN")));
        users.put(user.getId(),user);
        inMemoryUserDetailsManager.createUser(user);
        user = new User(2,"dimax", "Dimax", "Adminov",
                (byte)28, "dimax@yandex.ru", "dimax",
                new HashSet<Role>(Arrays.asList(new Role(0, "ROLE_USER"), new Role(1, "ROLE_ADMIN"))));
        users.put(user.getId(),user);
        inMemoryUserDetailsManager.createUser(user);

        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList(users.values());
    }

    @Override
    public void saveUser(User user) {
//        inMemoryUserDetailsManager.createUser(new User(username, password, new ArrayList<GrantedAuthority>()));
//        inMemoryUserDetailsManager.createUser(User.withUsername(username).password(password).roles("USER").build());
        if (user.getId() == 0) {
            Integer max = users.keySet().stream().max(Integer::compare).get();
            user.setId(max+1);
            inMemoryUserDetailsManager.createUser(user);
        } else {
            inMemoryUserDetailsManager.updateUser(user);
        }
        users.put(user.getId(),user);
    }

    @Override
    public void deleteUser(int id) {
        User user = users.get(id);
        if(user != null) {
            inMemoryUserDetailsManager.deleteUser(user.getUsername());
            users.remove(id);
        }
    }

    @Override
    public User getUser(int id) {
        User user = users.get(id);
        if((user != null) && inMemoryUserDetailsManager.userExists(user.getUsername())) {
                return user;
        }
        Logger.getLogger("UserDaoMemImp").log(Level.WARNING,"Пользователь с идентификатором {0} не найден", id);
        return null;
    }
}
