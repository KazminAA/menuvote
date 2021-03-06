package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Role;
import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.utils.json.JsonUtils;

import java.util.Arrays;
import java.util.HashSet;

import static com.simplevoting.menuvoting.VoteTestData.*;
import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final User USER1 = new User(START_SEQ, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER2 = new User(START_SEQ + 1, "User2", "user2@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN1 = new User(START_SEQ + 2, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    static {
        fillSets();
    }

    public static void fillSets() {
        USER1.setVotes(new HashSet<>(Arrays.asList(VOTE1)));
        USER2.setVotes(new HashSet<>(Arrays.asList(VOTE2, VOTE4)));
        ADMIN1.setVotes(new HashSet<>(Arrays.asList(VOTE3, VOTE5)));
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes", "password").isEqualTo(expected);
    }

    public static String jsonWithPassword(User user, String passwd) {
        return JsonUtils.wrightAdditionProps(user, "password", passwd);
    }
}
