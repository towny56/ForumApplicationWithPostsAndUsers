package com.homework.first.domain;

import com.homework.first.model.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post findById(String postId);
    Post add(Post post);
    Post update(Post post);
    Post remove(String postId);
}
