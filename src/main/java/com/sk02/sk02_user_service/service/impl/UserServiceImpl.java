package com.sk02.sk02_user_service.service.impl;

import com.sk02.sk02_user_service.domain.ClientAttributes;
import com.sk02.sk02_user_service.domain.ManagerAttributes;
import com.sk02.sk02_user_service.domain.User;
import com.sk02.sk02_user_service.domain.enums.Rank;
import com.sk02.sk02_user_service.dto.user.*;
import com.sk02.sk02_user_service.mapper.UserMapper;
import com.sk02.sk02_user_service.repository.ClientAttributesRepository;
import com.sk02.sk02_user_service.repository.ManagerAttributesRepository;
import com.sk02.sk02_user_service.repository.UserRepository;
import com.sk02.sk02_user_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private ClientAttributesRepository clientAttributesRepository;
    private ManagerAttributesRepository managerAttributesRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, ClientAttributesRepository clientAttributesRepository, ManagerAttributesRepository managerAttributesRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.clientAttributesRepository = clientAttributesRepository;
        this.managerAttributesRepository = managerAttributesRepository;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper :: userToUserDto);
    }

    @Override
    public UserDto createClient(UserClientCreateDto userClientCreateDto) {
        User user = userMapper.userClientCreateDtoToUser(userClientCreateDto);
        ClientAttributes clientAttributes = new ClientAttributes();

        clientAttributes.setRank(Rank.BRONZE);
        clientAttributes.setPassportNumber(userClientCreateDto.getPassportNumber());
        clientAttributes.setReservationNumber(0);

        user.setClientAttributes(clientAttributes);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto createManager(UserManagerCreateDto userManagerCreateDto) {
        User user = userMapper.userManagerCreateDtoToUser(userManagerCreateDto);
        ManagerAttributes managerAttributes = new ManagerAttributes();

        managerAttributes.setHotelName(userManagerCreateDto.getHotelName());
        managerAttributes.setEmploymentDate(new Date());

        user.setManagerAttributes(managerAttributes);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUser(String username) {
        return null;
    }

    @Override
    public UserDto updateUserClient(Long id, UserClientUpdateDto userClientUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(null);
        userMapper.updateClientUser(userClientUpdateDto, user);

        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUserManager(Long id, UserManagerUpdateDto userManagerUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(null);
        userMapper.updateManagerUser(userManagerUpdateDto, user);

        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
