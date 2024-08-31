package com.bkjeon.example.service;

import com.bkjeon.example.domain.user.UserPrincipal;
import com.bkjeon.example.entity.user.Role;
import com.bkjeon.example.entity.user.User;
import com.bkjeon.example.entity.user.UserRole;
import com.bkjeon.example.mapper.user.RoleMapper;
import com.bkjeon.example.mapper.user.UserMapper;
import com.bkjeon.example.mapper.user.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findUserByLoginId(username);
		return new UserPrincipal(user);
	}

}
