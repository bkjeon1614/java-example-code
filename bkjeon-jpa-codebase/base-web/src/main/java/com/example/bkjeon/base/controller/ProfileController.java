package com.example.bkjeon.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("prod", "prod1", "prod2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(1);

        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }

}
