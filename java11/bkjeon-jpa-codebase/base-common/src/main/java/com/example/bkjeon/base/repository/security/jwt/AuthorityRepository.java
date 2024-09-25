package com.example.bkjeon.base.repository.security.jwt;

import com.example.bkjeon.base.domain.jwt.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {}
