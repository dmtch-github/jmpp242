package springsecurity.business.dao;

import org.springframework.stereotype.Repository;
import springsecurity.business.entities.User;

import java.util.List;

@Repository
public class UserDaoSetImp implements UserDao {




    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public User getUser(int id) {
        return null;
    }
}
