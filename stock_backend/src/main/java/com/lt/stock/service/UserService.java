package com.lt.stock.service;

import com.lt.stock.common.Response;
import com.lt.stock.pojo.dto.LoginRequestDto;
import com.lt.stock.pojo.vo.LoginResponseVo;

import java.util.Map;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 19:11
 */
public interface UserService {
    Response<LoginResponseVo> login(LoginRequestDto loginRequestDto);

    Response<Map<String, String>> generateCaptcha();
}
