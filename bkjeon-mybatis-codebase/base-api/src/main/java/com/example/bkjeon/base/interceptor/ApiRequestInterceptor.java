package com.example.bkjeon.base.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiRequestInterceptor extends HandlerInterceptorAdapter {

    /**
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String apiKey = request.getHeader("ACCESS-TOKEN");
        String path = request.getRequestURI();

        if (StringUtils.isNotBlank(apiKey)) {
            String[] availablePaths = ApiKeyAuthPaths.getAvailablePaths(apiKey);
            for (String availablePath : availablePaths) {
                Pattern pattern = Pattern.compile(availablePath);
                Matcher matcher = pattern.matcher(path);
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        response.setStatus(401);
        return false;
    }
    */

}
