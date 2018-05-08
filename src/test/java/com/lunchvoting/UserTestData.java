package com.lunchvoting;

import com.lunchvoting.model.Role;
import com.lunchvoting.model.User;

public class UserTestData {

    public static final int USER_ID = 1;

    public static final User USER1 = new User(USER_ID, "User1", "user1@mail.com", "password1", Role.ROLE_USER);
    public static final User ADMIN = new User(USER_ID, "Admin", "admin@mail.com", "admin", Role.ROLE_USER);
}
