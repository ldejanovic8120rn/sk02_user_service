package com.sk02.sk02_user_service.controller;

import com.sk02.sk02_user_service.domain.enums.Role;
import com.sk02.sk02_user_service.dto.token.TokenRequestDto;
import com.sk02.sk02_user_service.dto.token.TokenResponseDto;
import com.sk02.sk02_user_service.dto.user.*;
import com.sk02.sk02_user_service.security.CheckSecurity;
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

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @GetMapping
    @CheckSecurity (roles = {"ADMIN"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(userService.findAll(PageRequest.of(0, 20)), HttpStatus.OK);
    }

    @GetMapping ("/{username}")
    @CheckSecurity (roles = {"ADMIN"})
    public ResponseEntity<UserDto> getUserByUsername(@RequestHeader("Authorization") String authorization, @PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping ("/client")
    public ResponseEntity<UserDto> createUserClient(@RequestBody @Valid UserClientCreateDto userClientCreateDto) {
        return new ResponseEntity<>(userService.createClient(userClientCreateDto), HttpStatus.CREATED);
    }

    @PutMapping ("/client")
    public ResponseEntity<UserDto> updateUserClient(@RequestHeader("Authorization") String authorization, @RequestBody UserClientUpdateDto userClientUpdateDto) {

        Long id = Long.valueOf(0);
        return new ResponseEntity<>(userService.updateUserClient(id, userClientUpdateDto), HttpStatus.OK);
    }

    @PutMapping ("client/{id}")
    @CheckSecurity (roles = {"ADMIN"})
    public ResponseEntity<UserDto> updateUserClientByAdmin(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @RequestBody UserClientUpdateDto userClientUpdateDto) {
        return new ResponseEntity<>(userService.updateUserClient(id, userClientUpdateDto), HttpStatus.OK);
    }

    @PostMapping ("/manager")
    public ResponseEntity<UserDto> createUserManager(@RequestBody @Valid UserManagerCreateDto userManagerCreateDto) {
        return new ResponseEntity<>(userService.createManager(userManagerCreateDto), HttpStatus.CREATED);
    }

    @PutMapping ("manager/{id}")
    @CheckSecurity (roles = {"ADMIN"})
    public ResponseEntity<UserDto> updateUserManagerByAdmin(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @RequestBody UserManagerUpdateDto userManagerUpdateDto) {
        return new ResponseEntity<>(userService.updateUserManager(id, userManagerUpdateDto), HttpStatus.OK);
    }

    @PutMapping ("delete/{id}")
    @CheckSecurity (roles = {"ADMIN"})
    public ResponseEntity<HttpStatus> deleteUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping ("activate/{id}")
    @CheckSecurity (roles = {"ADMIN"})
    public ResponseEntity<HttpStatus> activateUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        userService.activateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
