package com.homework.first.domain;

import com.homework.first.dao.UsersRepository;
import com.homework.first.exception.NonexisitngEntityException;
import com.homework.first.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(String userId) {
        return repository.findById(userId).orElseThrow(() -> new NonexisitngEntityException(String.format("User with ID '%s' does not exists.", userId)));
    }

    @Override
    public User add(User user) {
        return repository.insert(user);
    }

    @Override
    public User update(User user) {
        Optional<User> old = repository.findById(user.getId());

        if(!old.isPresent()){
            throw new NonexisitngEntityException(String.format("User with ID '%s' does not exists.", user.getId()));
        }

        return repository.save(user);
    }

    @Override
    public User remove(String userId) {
        Optional<User> old = repository.findById(userId);

        if(!old.isPresent()){
            throw new NonexisitngEntityException(String.format("User with ID '%s' does not exists.", userId));
        }

        repository.deleteById(userId);
        return old.get();
    }
}
