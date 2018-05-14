package com.lunchvoting.service;

import com.lunchvoting.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lunchvoting.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetByEmail() {
        assertThat(userRepository.getByEmail(USER1.getEmail())).isEqualToComparingFieldByField(USER1);
    }
}