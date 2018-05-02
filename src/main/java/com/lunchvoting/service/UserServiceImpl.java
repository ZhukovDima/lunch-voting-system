package com.lunchvoting.service;

import com.lunchvoting.model.User;
import com.lunchvoting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return userRepository.getByEmail(email);
    }
}
