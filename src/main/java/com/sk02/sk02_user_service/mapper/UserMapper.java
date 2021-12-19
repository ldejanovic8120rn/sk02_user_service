package com.sk02.sk02_user_service.mapper;

import com.sk02.sk02_user_service.domain.ClientAttributes;
import com.sk02.sk02_user_service.domain.User;
import com.sk02.sk02_user_service.domain.enums.Rank;
import com.sk02.sk02_user_service.domain.enums.Role;
import com.sk02.sk02_user_service.dto.user.*;

public class UserMapper {

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public User userClientCreateDtoToUser(UserClientCreateDto userClientCreateDto) {
        User user = new User();

        user.setRole(Role.CLIENT);
        user.setFirstName(userClientCreateDto.getFirstName());
        user.setLastName(userClientCreateDto.getLastName());
        user.setUsername(userClientCreateDto.getUsername());
        user.setPassword(userClientCreateDto.getPassword());
        user.setEmail(userClientCreateDto.getEmail());
        user.setPhone(userClientCreateDto.getPhone());
        user.setBirthday(userClientCreateDto.getBirthday());

        ClientAttributes clientAttributes = new ClientAttributes();
        clientAttributes.setRank(Rank.BRONZE);
        clientAttributes.setPassportNumber(userClientCreateDto.getPassportNumber());

        return user;
    }

    public User userManagerCreateDtoToUser(UserManagerCreateDto userManagerCreateDto) {

        return null;
    }

    public User userClientUpdateDtoToUser(UserClientUpdateDto userClientUpdateDto) {

        return null;
    }

    public User userManagerUpdateDtoToUser(UserManagerUpdateDto userManagerUpdateDto) {

        return null;
    }
}
