package springsecurity.business.dao;

import org.springframework.stereotype.Repository;
import springsecurity.business.entities.Role;
import springsecurity.business.entities.Roles;
import springsecurity.business.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository("daoEntityManager")
public class UserDaoEmImp implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getUsers() {
        List<User> users = em.createQuery("FROM User",User.class)
                .getResultList();
        for (User u: users) {
            u.rolesToText(); //преобразуем роли в текстовое описание
        }
        return users;
    }

    public Role getRoleByName(String rolename) {
        String hqlRequest = "FROM Role WHERE role = :role";
        List<Role> list = em.createQuery(hqlRequest, Role.class)
                .setParameter("role", rolename)
                .getResultList();
        return list.isEmpty() ? null: list.get(0);
    }

    @Override
    public void saveUser(User user) {
        String[] namesRole = Arrays.stream(user.getTextRoles().split(" "))
            .map(String::toUpperCase)
            .filter(x -> x.equals(Roles.ADMIN) || x.equals(Roles.USER))
            .distinct()
            .map(x -> Roles.ROLE_PREFIX+x)
            .toArray(String[]::new);

        //если ролей нет, то назначаем роль USER
        if(namesRole.length == 0) {
            namesRole = new String[]{Roles.ROLE_USER};
        }

        Set<Role> roles = new HashSet<>();
        for(String name : namesRole) {
            Role role = getRoleByName(name); //получаем роль из БД
            if(role == null) {
                role = new Role(name); //создаем роль, когда её нет в БД
            }
            roles.add(role);
        }

        user.setRoles(roles);

        if(user.getId() == 0) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    public void deleteUser(int id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public User getUser(int id) {
        User user = em.find(User.class, id);
        if(user != null) {
            user.rolesToText();
        }
        return user;
    }

    @Override
    public User getUserByName(String username) {
        String hqlRequest = "FROM User WHERE email = :email";
        List<User> list = em.createQuery(hqlRequest, User.class)
                .setParameter("email", username)
                .getResultList();
        User user = list.isEmpty() ? null: list.get(0);
        if(user != null) {
            user.rolesToText();
        }
        return user;
    }

    /**
     * Проверяет наличие записей в БД пользователей
     * Используется при создании временного администратора
     */
    @Override
    public int getCountUsers() {
        String hqlRequest = "SELECT COUNT(u) FROM User u";
        List<Long> list = em.createQuery(hqlRequest,Long.class).getResultList();
        return (int) (list.isEmpty()?0:list.get(0));
    }
}
