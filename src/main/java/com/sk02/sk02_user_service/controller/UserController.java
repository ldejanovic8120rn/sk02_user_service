package com.sk02.sk02_user_service.controller;

import com.sk02.sk02_user_service.dto.user.*;
import com.sk02.sk02_user_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping ("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(PageRequest.of(0, 20)), HttpStatus.OK);
    }

    @GetMapping ("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping ("/client")
    public ResponseEntity<UserDto> createUserClient(@RequestBody @Valid UserClientCreateDto userClientCreateDto) {
        return new ResponseEntity<>(userService.createClient(userClientCreateDto), HttpStatus.CREATED);
    }

    @PutMapping ("client/{id}")
    public ResponseEntity<UserDto> updateUserClient(@PathVariable("id") Long id, @RequestBody UserClientUpdateDto userClientUpdateDto) {
        return new ResponseEntity<>(userService.updateUserClient(id, userClientUpdateDto), HttpStatus.OK);
    }

    @PostMapping ("/manager")
    public ResponseEntity<UserDto> createUserManager(@RequestBody @Valid UserManagerCreateDto userManagerCreateDto) {
        return new ResponseEntity<>(userService.createManager(userManagerCreateDto), HttpStatus.CREATED);
    }

    @PutMapping ("manager/{id}")
    public ResponseEntity<UserDto> updateUserManager(@PathVariable("id") Long id, @RequestBody UserManagerUpdateDto userManagerUpdateDto) {
        return new ResponseEntity<>(userService.updateUserManager(id, userManagerUpdateDto), HttpStatus.OK);
    }

    @PutMapping ("delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping ("activate/{id}")
    public ResponseEntity<HttpStatus> activateUser(@PathVariable("id") Long id) {
        userService.activateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
