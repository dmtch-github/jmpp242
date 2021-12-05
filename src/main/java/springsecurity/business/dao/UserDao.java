package springsecurity.business.dao;

import springsecurity.business.entities.User;

import java.util.List;

public interface UserDao {

    public List<User> getUsers();

    public void saveUser(User user);

    public void deleteUser(int id);

    public User getUser(int id);
}
