package com.lt.stock.controller;

import com.lt.stock.common.Response;
import com.lt.stock.pojo.dto.LoginRequestDto;
import com.lt.stock.pojo.vo.LoginResponseVo;
import com.lt.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 18:10
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
//    @PostMapping("/login")
//    public Response<LoginResponseVo> login(@RequestBody LoginRequestDto loginRequestDto) {
//        return userService.login(loginRequestDto);
//    }

    /**
     * 生成验证码
     *
     * @return map:{code: xxx, rkey: xxx}
     */
    @GetMapping("/captcha")
    public Response<Map<String, String>> generateCaptcha() {
        return userService.generateCaptcha();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Response<String> getUser() {
        return Response.ok("hello");
    }
}
