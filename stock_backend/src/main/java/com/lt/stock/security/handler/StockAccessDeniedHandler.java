package com.lt.stock.security.handler;

import com.google.gson.Gson;
import com.lt.stock.common.Response;
import com.lt.stock.common.enums.ResponseCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 认证用户无权限访问资源时处理器
 * @author: ~Teng~
 * @date: 2023/2/14 18:52
 */
public class StockAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //设置响应数据格式
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //构建结果
        Response result = Response.error(ResponseCode.NOT_PERMISSION.getCode(), ResponseCode.NOT_PERMISSION.getMessage());
        //将对象序列化为json字符串响应前台
        response.getWriter().write(new Gson().toJson(result));
    }
}
