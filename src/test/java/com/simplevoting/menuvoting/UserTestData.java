package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Role;
import com.simplevoting.menuvoting.model.User;

import java.util.Arrays;

import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final User USER1 = new User(START_SEQ, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER2 = new User(START_SEQ + 1, "User2", "user2@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN1 = new User(START_SEQ + 2, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes").isEqualTo(expected);
    }
}
