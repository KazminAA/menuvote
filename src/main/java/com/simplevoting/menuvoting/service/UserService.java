package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.to.UserTo;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo userTo);

    List<User> getAll();

    void enable(int id, boolean enabled);

    User getWithVotes(int id);
}