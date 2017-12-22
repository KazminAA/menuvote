package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Role;
import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.service.UserService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.junit.Assume;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static com.simplevoting.menuvoting.UserTestData.*;
import static com.simplevoting.menuvoting.UserTestData.assertMatch;
import static com.simplevoting.menuvoting.VoteTestData.*;
import static com.simplevoting.menuvoting.VoteTestData.assertMatch;

public class UserServiceImplTest extends AbstractServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void create() {
        User created = new User(null, "Admin2", "user3@ukr.net", "pass12", Role.ROLE_ADMIN, Role.ROLE_USER);
        service.create(created);
        assertMatch(service.getAll(), ADMIN1, created, USER1, USER2);
    }

    @Test
    public void duplicateMailCreate() throws Exception {
        thrown.expect(DataAccessException.class);
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void delete() {
        service.delete(USER1.getId());
        assertMatch(service.getAll(), ADMIN1, USER2);
    }

    @Test
    public void deleteNoFound() {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    public void get() {
        User user = service.get(USER1.getId());
        assertMatch(user, USER1);
    }

    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        service.get(1);
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail(USER1.getEmail());
        assertMatch(user, USER1);
    }

    @Test
    public void getByEmailNotFound() {
        thrown.expect(NotFoundException.class);
        service.getByEmail("m1@mail.ru");
    }

    @Test
    public void getAll() {
        List<User> users = service.getAll();
        assertMatch(users, ADMIN1, USER1, USER2);
    }

    @Test
    public void update() {
        User updated = new User(USER1);
        updated.setName("Updated");
        updated.setEmail("updated@mail.ru");
        service.update(updated);
        assertMatch(service.getAll(), ADMIN1, updated, USER2);
    }

    @Test
    public void testValidation() throws Exception {
        Assume.assumeFalse(Arrays.toString(environment.getActiveProfiles()).contains("jdbc"));
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", true, EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", true, EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", true, EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
    }

    @Test
    public void getWithVotes() {
        User user = service.getWithVotes(USER1.getId());
        assertMatch(user.getVotes(), VOTE1, VOTE4);
    }
}