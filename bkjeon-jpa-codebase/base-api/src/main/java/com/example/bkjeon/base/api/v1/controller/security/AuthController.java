package com.example.bkjeon.base.api.v1.controller.security;

import com.example.bkjeon.base.api.v1.controller.security.jwt.JwtFilter;
import com.example.bkjeon.base.api.v1.controller.security.jwt.TokenProvider;
import com.example.bkjeon.base.api.v1.controller.security.jwt.dto.LoginDTO;
import com.example.bkjeon.base.api.v1.controller.security.jwt.dto.TokenDTO;
import com.example.bkjeon.base.api.v1.controller.security.jwt.dto.UserDTO;
import com.example.bkjeon.base.domain.jwt.User;
import com.example.bkjeon.base.api.v1.service.security.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/security/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @ApiOperation("토큰발급")
    @PostMapping("login")
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginDTO loginDto) {
        // username, password 을 이용하여 token 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // token을 이용해서 .authenticate 메소드가 실행이 될 때 CustomUserDetailsService 에서 loadUserByUsername 이 실행
        // 결과값을 SecurityContext에 저장
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // TokenProvider의 createToken 메소드를 실행하여 token 생성
        String jwt = tokenProvider.createToken(authentication);

        // token을 Response Header와 Body에도 넣어서 리턴한다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);

        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation("회원가입")
    @PostMapping("signUp")
    public ResponseEntity<User> signUp(
        @Valid @RequestBody UserDTO userDto
    ) {
        return ResponseEntity.ok(userService.signUp(userDto));
    }

}
