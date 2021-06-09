package com.example.bkjeon.base.controller.security;

import com.example.bkjeon.base.entity.security.jwt.User;
import com.example.bkjeon.base.service.security.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/security")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // @PreAuthorize를 통하여 USER, ADMIN 두가지 권한 모두 허용
    @ApiOperation("USER, ADMIN 권한만 호출 가능")
    @GetMapping("user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    // @PreAuthorize를 통하여 ADMIN 권한만 허용
    @ApiOperation("ADMIN 권한만 호출 가능")
    @GetMapping("user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }

}
