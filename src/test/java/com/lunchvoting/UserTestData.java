package com.lunchvoting;

import com.lunchvoting.model.Role;
import com.lunchvoting.model.User;
import com.lunchvoting.web.json.JsonUtil;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {

    public static final int USER_1_ID = 1;
    public static final int USER_2_ID = 2;
    public static final int ADMIN_ID = 3;

    public static final User USER1 = new User(USER_1_ID, "User1", "user1@mail.com", "password1", Role.ROLE_USER);
    public static final User USER2 = new User(USER_2_ID , "User2", "user2@mail.com", "password2", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@mail.com", "admin", Role.ROLE_ADMIN);

    public static User getNew() {
        return new User(null, "New user", "newuser@mail.com", "newPass", Role.ROLE_USER);
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password").isEqualTo(expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
