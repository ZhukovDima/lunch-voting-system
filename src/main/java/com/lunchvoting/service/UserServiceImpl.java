package com.lunchvoting.service;

import com.lunchvoting.AuthorizedUser;
import com.lunchvoting.model.User;
import com.lunchvoting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return userRepository.getByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return new AuthorizedUser(user);
    }
}
