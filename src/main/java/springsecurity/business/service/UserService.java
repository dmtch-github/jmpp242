package springsecurity.business.service;

import springsecurity.business.entities.User;

import java.util.List;

public interface UserService {

    public List<User> getUsers();

    public void saveUser(User user);

    public void deleteUser(int id);

    public User getUser(int id);

    public User getUser(String username);
}
