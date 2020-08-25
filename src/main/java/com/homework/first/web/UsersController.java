package com.homework.first.web;

import com.homework.first.domain.UsersService;
import com.homework.first.exception.InvalidEntityException;
import com.homework.first.model.User;
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
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping
    public List<User> getUsers(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") String userId){
        return service.findById(userId);
    }

    /*
    @PostMapping
    public ResponseEntity addUser(@RequestBody User user){
        User created = service.add(user);
        return ResponseEntity.created(MvcUriComponentsBuilder.fromMethodName(UsersController.class, "addUser", User.class).pathSegment("{id}").build(created.getId())).body(created);
    }
    */

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            String message = bindingResult.getFieldErrors().stream().map(error -> error.toString()).reduce("", (acc, errStr) -> (acc + "\n" + errStr));
            throw new InvalidEntityException(message);
        }

        User created = service.add(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId())).body(created);
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") String id, @RequestBody User user){
        if(!id.equals(user.getId())){
            throw new InvalidEntityException(String.format("Entity with id '%s' is different from URL resource id '%s'", user.getId(), id));
        }

        return service.update(user);
    }

    @DeleteMapping("{id}")
    public User remove(@PathVariable("id") String id){
        return service.remove(id);
    }
}
