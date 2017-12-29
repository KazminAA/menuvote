package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;

import static com.simplevoting.menuvoting.MenuTestData.*;
import static com.simplevoting.menuvoting.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {
    public static final Vote VOTE1 = new Vote(LocalDate.of(2017, 11, 1), USER1, MENU2);
    public static final Vote VOTE2 = new Vote(LocalDate.of(2017, 11, 1), USER2, MENU2);
    public static final Vote VOTE3 = new Vote(LocalDate.of(2017, 11, 1), ADMIN1, MENU1);
    public static final Vote VOTE4 = new Vote(LocalDate.of(2017, 11, 2), USER2, MENU5);
    public static final Vote VOTE5 = new Vote(LocalDate.of(2017, 11, 2), ADMIN1, MENU6);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual)
                .usingComparator(VoteTestData::compare)
                .isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparator(VoteTestData::compare).isEqualTo(expected);
    }

    private static int compare(Vote vote1, Vote vote2) {
        return vote1.getDate().compareTo(vote2.getDate()) + vote1.getUserId() - vote2.getUserId();
    }
}
