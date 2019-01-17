package com.example.member.mapper;

import com.example.member.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Mapper
public interface UserMapper {
    @Transactional(readOnly = true)
    User findUserByLoginId(@Param("loginId") String loginId);

    @Transactional
    int setUserInfo(@Param("param") User param);
}
