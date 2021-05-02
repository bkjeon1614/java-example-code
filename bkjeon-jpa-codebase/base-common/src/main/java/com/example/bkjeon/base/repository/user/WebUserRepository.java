package com.example.bkjeon.base.repository.user;

import com.example.bkjeon.base.domain.user.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    Optional<WebUser> findByEmail(String email);
}
