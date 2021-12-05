package springsecurity.business.dao;

import springsecurity.business.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserEmDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getUsers() {
        return em.createQuery("FROM User")
                .getResultList();
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
        em.remove(getUser(id));
    }

    @Override
    public User getUser(int id) {
        return em.find(User.class, id);
    }
}
