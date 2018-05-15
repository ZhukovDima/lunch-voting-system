package com.lunchvoting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lunchvoting.UserTestData.*;

public class AuthenticationServiceTest extends AbstractServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void getByEmail() throws Exception {
        assertMatch(authenticationService.getByEmail("user1@mail.com"), USER1);
    }
}