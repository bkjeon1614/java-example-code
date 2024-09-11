package com.example.bkjeon.base.api.v1.controller.security.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 필요한 권한이 존재하지 않는 경우에 403 Forbidden 에러를 리턴하기 위한 클래스
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 필요한 권한이 없이 접근하려 할때 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
