package com.sk02.sk02_user_service.service.impl;

import com.sk02.sk02_user_service.domain.ClientAttributes;
import com.sk02.sk02_user_service.domain.ManagerAttributes;
import com.sk02.sk02_user_service.domain.User;
import com.sk02.sk02_user_service.dto.token.TokenRequestDto;
import com.sk02.sk02_user_service.dto.token.TokenResponseDto;
import com.sk02.sk02_user_service.dto.user.*;
import com.sk02.sk02_user_service.exception.NotFoundException;
import com.sk02.sk02_user_service.mapper.UserMapper;
import com.sk02.sk02_user_service.repository.UserRepository;
import com.sk02.sk02_user_service.security.service.TokenService;
import com.sk02.sk02_user_service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String notFoundMessageId = "User with given ID not found!";
    private static final String notFoundMessageEmailAndPassword = "User with given email and password not found!";

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, TokenService tokenService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository.findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword()).orElseThrow(() -> new NotFoundException(notFoundMessageEmailAndPassword));

        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().name());

        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper :: userToUserDto);
    }

    @Override
    public UserDto createClient(UserClientCreateDto userClientCreateDto) {
        User user = userMapper.userClientCreateDtoToUser(userClientCreateDto);
        ClientAttributes clientAttributes = new ClientAttributes();

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
    public UserDto getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("User with given username - \"" + username + "\" not found!"));
    }

    @Override
    public UserDto updateUserClient(String authorization, UserClientUpdateDto userClientUpdateDto) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = claims.get("id", Long.class);

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(notFoundMessageId));
        userMapper.updateClientUser(userClientUpdateDto, user);

        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUserManager(String authorization, UserManagerUpdateDto userManagerUpdateDto) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = claims.get("id", Long.class);

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(notFoundMessageId));
        userMapper.updateManagerUser(userManagerUpdateDto, user);

        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(notFoundMessageId));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void activateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(notFoundMessageId));
        user.setEnabled(true);
        userRepository.save(user);
    }

}
