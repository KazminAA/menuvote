package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.AuthorizedUser;
import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.repository.UserRepository;
import com.simplevoting.menuvoting.service.UserService;
import com.simplevoting.menuvoting.to.UserTo;
import com.simplevoting.menuvoting.utils.MessageUtil;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.simplevoting.menuvoting.utils.UserUtil.prepareToSave;
import static com.simplevoting.menuvoting.utils.UserUtil.updateFromTo;
import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.checkNotFound;
import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.checkNotFoundWithId;

@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final MessageUtil messageUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, MessageUtil messageUtil, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.messageUtil = messageUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(allEntries = true)
    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    @CacheEvict(allEntries = true)
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

    @Cacheable
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @CacheEvict(allEntries = true)
    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        repository.save(prepareToSave(updateFromTo(user, userTo), passwordEncoder));
    }

    @Transactional
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

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) throw new UsernameNotFoundException(messageUtil.getMessage("exception.user.notFound", email));
        return new AuthorizedUser(user);
    }
}