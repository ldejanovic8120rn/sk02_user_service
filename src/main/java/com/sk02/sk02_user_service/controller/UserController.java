package com.sk02.sk02_user_service.controller;

import com.sk02.sk02_user_service.dto.user.UserClientCreateDto;
import com.sk02.sk02_user_service.dto.user.UserDto;
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

    @PostMapping ("/user/client")
    public ResponseEntity<UserDto> createUserClient(@RequestBody @Valid UserClientCreateDto userClientCreateDto) {
        return new ResponseEntity<>(userService.createClient(userClientCreateDto), HttpStatus.OK);
    }

}
