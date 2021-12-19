package com.sk02.sk02_user_service.service;

import com.sk02.sk02_user_service.dto.user.*;
import org.springframework.data.domain.Page;

public interface UserService {

    //TODO login

    Page<UserDto> findAll();

    UserDto createClient(UserClientCreateDto userClientCreateDto);
    UserDto createManager(UserManagerCreateDto userManagerCreateDto);

    UserDto getUser(String username);

    UserDto updateUserClient(Long id, UserClientUpdateDto userClientUpdateDto);
    UserDto updateUserManager(Long id, UserManagerUpdateDto userManagerUpdateDto);

    UserDto deleteUser(Long id);

}
