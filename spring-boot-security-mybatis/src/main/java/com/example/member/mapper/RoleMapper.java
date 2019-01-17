package com.example.member.mapper;

import com.example.member.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Mapper
public interface RoleMapper {
    @Transactional(readOnly = true)
    Role getRoleInfo(@Param("role") String role);
}