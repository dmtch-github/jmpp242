package springsecurity.business.dao;

import org.springframework.stereotype.Repository;
import springsecurity.business.dao.UserDao;
import springsecurity.business.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("daoEntityManager")
public class UserDaoEmImp implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getUsers() {
        List<User> users = em.createQuery("FROM User")
                .getResultList();
        return users;
    }

    @Override
    public void saveUser(User user) {
        if(user.getId() == 0) {
            em.persist(user);
        } else {
            em.merge(user);
        }

    }

    @Override
    public void deleteUser(int id) {
        User user = em.find(User.class, id);
        em.remove(user);
        System.out.println("daoEntityManager: daleteUser");
    }

    @Override
    public User getUser(int id) {
        return em.find(User.class, id);
    }


    @Override
    public User getUserByName(String username) {
        return null;
    }


}
