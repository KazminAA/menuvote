package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.repository.UserRepository;
import com.simplevoting.menuvoting.service.UserService;
import com.simplevoting.menuvoting.to.UserTo;
import com.simplevoting.menuvoting.utils.UserUtil;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.checkNotFound;
import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        repository.save(UserUtil.updateFromTo(user, userTo));
    }

    @Override
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }

    @Override
    public User getWithVotes(int id) {
        return checkNotFoundWithId(repository.getWithVotes(id), id);
    }
}