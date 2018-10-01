package com.sample.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class RouteController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

}
