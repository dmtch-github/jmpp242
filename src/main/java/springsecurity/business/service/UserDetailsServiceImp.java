package springsecurity.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.business.dao.UserDao;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.getUserByName(username);
    }

}
