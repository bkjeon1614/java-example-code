package com.example.member.service;

import com.example.member.entity.Role;
import com.example.member.entity.User;
import com.example.member.entity.UserRole;
import com.example.member.mapper.RoleMapper;
import com.example.member.mapper.UserMapper;
import com.example.member.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
    private RoleMapper roleMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findUserByLoginId(String loginId) {
		return userMapper.findUserByLoginId(loginId);
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        userMapper.setUserInfo(user);

        Role role = roleMapper.getRoleInfo("ADMIN");

		UserRole userRole = new UserRole();
		userRole.setRoleId(role.getId());
		userRole.setUserId(user.getId());

		userRoleMapper.setUserRoleInfo(userRole);
	}

}
