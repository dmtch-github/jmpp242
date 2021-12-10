package springsecurity.business.dao;

import org.springframework.stereotype.Repository;
import springsecurity.business.dao.UserDao;
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
        List<User> users = em.createQuery("FROM User")
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
                .getResultList(); //getSingleResult выдает ошибку.
        return list.size() > 0 ? list.get(0) : null;
    }


    @Override
    public void saveUser(User user) {
        System.out.println("UserDao.saveUser Работаю со строкой ролей = " + user.getTextRoles());
        String[] names = user.getTextRoles().split(" ");
        System.out.println("UserDao.saveUser Получил такой список = " + names);

        String[] namesRole = Arrays.stream(names)
            .map(String::toUpperCase)
            .filter(x -> x.equals(Roles.ADMIN) || x.equals(Roles.USER))
            .distinct()
            .map(x -> Roles.ROLE_PREFIX+x)
            .toArray(String[]::new);

        //если роли не определены, то назначаем роль USER
        if(namesRole.length == 0) {
            namesRole = new String[]{Roles.ROLE_USER};
        }

        System.out.println("UserDao.saveUser Обработал # Получил такой namesRole = " + namesRole);

        Set<Role> roles = new HashSet<>();
        for(String name : namesRole) {
            Role role = getRoleByName(name);
            if(role == null) {
                role = new Role(name); //создание роли, когда её нет в БД
            }
            roles.add(role);
        }

        System.out.println("UserDao.saveUser Юзер имеет такие роли = " + user.getRoles());
        System.out.println("UserDao.saveUser Назначаю Юзеру такие роли = " + roles);

        user.setRoles(roles);

        if(user.getId() == 0) {
            System.out.println("Буду сохранять юзера айди=0. Роль = " + user.getRoles());
            em.persist(user);
        } else {
            System.out.println("Буду сохранять юзера айди=" + user.getId() +"  Роль = " + user.getRoles());
            em.merge(user);
        }
    }

    @Override
    public void deleteUser(int id) {
        User user = em.find(User.class, id);
        //удаление юзера должно происходить без удаления роли
        em.remove(user);
        System.out.println("daoEntityManager: daleteUser");
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
        System.out.println("Посылаю запрос \"" + hqlRequest + "\"");

        List<User> list = em.createQuery(hqlRequest, User.class)
                .setParameter("email", username)
                .getResultList();
//        .getSingleResult();
        System.out.println("Получил список длиной " + list.size());
//
        User user = (list.size()>0 ? list.get(0) : null);
        System.out.println("Получил Юзер " + user);
        if(user != null) {
            user.rolesToText();
        }
        return user;
    }


}
