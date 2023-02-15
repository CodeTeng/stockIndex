package com.lt.stock.security.handler;

import com.google.gson.Gson;
import com.lt.stock.common.Response;
import com.lt.stock.common.enums.ResponseCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 匿名用户(即未登录时访问资源为匿名访问)无权限处理器
 * @author: ~Teng~
 * @date: 2023/2/14 18:53
 */
public class AccessAnonymousEntryPoint implements AuthenticationEntryPoint {
    /**
     * 当用户请求了一个受保护的资源，但是用户没有通过认证，那么抛出异常，
     * AuthenticationEntryPoint. Commence(..)就会被调用。
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //设置响应数据格式
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //构建结果
        Response result = Response.error(ResponseCode.NOT_PERMISSION.getCode(), ResponseCode.NOT_PERMISSION.getMessage());
        //将对象序列化为json字符串响应前台
        response.getWriter().write(new Gson().toJson(result));
    }
}
