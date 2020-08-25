package com.homework.first.web;

import com.homework.first.domain.PostService;
import com.homework.first.exception.InvalidEntityException;
import com.homework.first.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    private PostService service;

    @GetMapping
    public List<Post> getPosts(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public Post getPostsById(@PathVariable("id") String postId){
        return service.findById(postId);
    }

    /*
    @PostMapping
    public ResponseEntity addPost(@RequestBody Post post){
        Post created = service.add(post);
        return ResponseEntity.created(MvcUriComponentsBuilder.fromMethodName(PostsController.class, "addPost", Post.class).pathSegment("{id}").build(created.getId())).body(created);
    }
    */

    @PostMapping
    public ResponseEntity addPost(@RequestBody Post post, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            String message = bindingResult.getFieldErrors().stream().map(error -> error.toString()).reduce("", (acc, errStr) -> (acc + "\n" + errStr));
            throw new InvalidEntityException(message);
        }

        Post created = service.add(post);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId())).body(created);
    }

    @PutMapping("{id}")
    public Post update(@PathVariable("id") String id, @RequestBody Post post){
        if(!id.equals(post.getId())){
            throw new InvalidEntityException(String.format("Entity with id '%s' is different from URL resource id '%s'", post.getId(), id));
        }

        return service.update(post);
    }

    @DeleteMapping("{id}")
    public Post remove(@PathVariable("id") String id){
        return service.remove(id);
    }
}
