package com.homework.first.domain;

import com.homework.first.dao.PostRepository;
import com.homework.first.exception.NonexisitngEntityException;
import com.homework.first.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImlp implements PostService{

    @Autowired
    private PostRepository repository;

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public Post findById(String postId) {
        return repository.findById(postId).orElseThrow(() -> new NonexisitngEntityException(String.format("Post with ID '%s' does not exists.", postId)));
    }

    @Override
    public Post add(Post post) {
        return repository.insert(post);
    }

    @Override
    public Post update(Post post) {
        Optional<Post> old = repository.findById(post.getId());

        if(!old.isPresent()){
            throw new NonexisitngEntityException(String.format("Post with ID '%s' does not exists.", post.getId()));
        }

        post.setCreated(old.get().getCreated());
        return repository.save(post);
    }

    @Override
    public Post remove(String postId) {
        Optional<Post> old = repository.findById(postId);

        if(!old.isPresent()){
            throw new NonexisitngEntityException(String.format("Post with ID '%s' does not exists.", postId));
        }

        repository.deleteById(postId);
        return old.get();
    }
}
