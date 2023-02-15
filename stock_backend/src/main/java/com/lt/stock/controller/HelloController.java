package com.lt.stock.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class HelloController {
    @PreAuthorize("hasAuthority('sys:user:list')") // .antMatchers("/hello").hasAuthority("ROLE_ADMIN")
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!!";
    }

    @PreAuthorize("hasAuthority('P6')") // .antMatchers("/say").hasAuthority("P6")
    @GetMapping("/say")
    public String say() {
        return "say Security!";
    }

    @PermitAll // .antMatchers("/register").permitAll()
    @GetMapping("/register")
    public String register(){
        return "register security";
    }
}
