package com.lunchvoting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lunchvoting.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetByEmail() {
        assertThat(userService.getByEmail(USER1.getEmail())).isEqualToComparingFieldByField(USER1);
    }
}