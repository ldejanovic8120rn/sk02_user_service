package com.sk02.sk02_user_service.repository;

import com.sk02.sk02_user_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
