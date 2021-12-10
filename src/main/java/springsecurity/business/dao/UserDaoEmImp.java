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

    @Override
    public void saveUser(User user) {
        System.out.println("UserDao.saveUser ROLES_TEXT=" + user.getTextRoles());
//        String[] names = (String[]) Arrays.stream(user.getTextRoles().split(" ")).distinct().toArray();
        String[] names = user.getTextRoles().split(" ");
        System.out.println("Получил такой names=" + names);

String[] list = Arrays.stream(names)
            .map(String::toUpperCase)
            .filter(x -> x.equals(Roles.ADMIN) || x.equals(Roles.USER))
            .distinct()
                .toArray(String[]::new);
            //.collect(ArrayList::new, ArrayList.add, ArrayList::addAll);

        System.out.println("Получил такой lis=" + list);


        System.out.println("UserDao.saveUser names=" + names);
        Set<Role> roles = new HashSet<>();
        Role role = null;
        for(String s : names) {
            switch (s) {
                case Roles.ADMIN:
                    System.out.println("UserDao.saveUser извлекаю ADMIN");
                    role = em.find(Role.class, Roles.ROLE_ADMIN_ID);
                    System.out.println("UserDao.saveUser извлек ADMIN = " + role);
                    break;
                case Roles.USER:
                    System.out.println("UserDao.saveUser извлекаю USER");
                    role = em.find(Role.class, Roles.ROLE_USER_ID);
                    System.out.println("UserDao.saveUser извлек USER = " + role);
                    break;
            }
            if(role != null){
                roles.add(role);
                role = null;
            }
        }
        if(roles.size() == 0) { //по умолчанию назначаем роль USER
            System.out.println("UserDao.saveUser список ролей путь назначаем USER");
            role = em.find(Role.class, Roles.ROLE_USER_ID);
            if(role != null) {
                user.setRoles(Collections.singleton(role));
            }
        } else {
            System.out.println("UserDao.saveUser список ролей не пуст " + roles);
            user.setRoles(roles);
        }
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
        User user = (User) em.createQuery(hqlRequest)
                .setParameter("email", username)
                .getSingleResult();
        if(user != null) {
            user.rolesToText();
        }
        return user;
    }


}
