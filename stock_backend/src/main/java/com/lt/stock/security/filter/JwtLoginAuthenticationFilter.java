package com.lt.stock.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lt.stock.common.Response;
import com.lt.stock.pojo.entity.SysUser;
import com.lt.stock.pojo.vo.LoginResponseVo;
import com.lt.stock.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 自定义登录过滤器
 * @author: ~Teng~
 * @date: 2023/2/14 18:26
 */
public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String usernameParameter = "username";
    private String passwordParameter = "password";
    private String codeParameter = "code";
    private String rKeyParameter = "rkey";

    public JwtLoginAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username;
        String password;
        String checkCode;
        String rKey;
        // 1. 判断当前请求是否是 post 方式请求
        if (!request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())) {
            // 不是 post 方式提交 直接抛异常
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 2. 判断方式方式是 ajax 方式提交还是 form 表单方式提交
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            // 3. 获取请求流中的用户信息
            // 3.1 从 request 对象中获取流对象
            ServletInputStream inputStream = request.getInputStream();
            // 3.2 解析流对象，获取用户相关信息
            HashMap<String, String> hashMap = new ObjectMapper().readValue(inputStream, HashMap.class);
            // 3.3 获取用户名和密码、验证码信息
            username = hashMap.get(usernameParameter);
            password = hashMap.get(passwordParameter);
            checkCode = hashMap.get(codeParameter);
            rKey = hashMap.get(rKeyParameter);
        } else {
            // 3. form 表单方式提交
            username = request.getParameter(usernameParameter);
            password = request.getParameter(passwordParameter);
            checkCode = request.getParameter(codeParameter);
            rKey = request.getParameter(rKeyParameter);
        }
        String rKeyValue = (String) redisTemplate.opsForValue().get(rKey);
        if (rKeyValue == null || !rKeyValue.equals(checkCode)) {
            throw new RuntimeException("验证码错误");
        }
        // 删除验证码
        redisTemplate.delete(rKey);
        // 防止空字符串
        username = StringUtils.isBlank(username) ? "" : username;
        password = StringUtils.isBlank(password) ? "" : password;
        // 4. 将用户信息组装称 token 对象 -> UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 5. 将组装的 token 对象委托给认证管理器做后序的认证处理
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * 认证成功方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser sysUser = (SysUser) authResult.getPrincipal();
        // 从 sysUser 中获取权限信息
        List<GrantedAuthority> authorityList = sysUser.getAuthorities();
        // 创建 token
        String username = sysUser.getUsername();
        String token = JwtTokenUtil.createToken(username, authorityList.toString());
        // 设置响应编码格式
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 组装响应结果
        LoginResponseVo loginResult = LoginResponseVo.builder().id(sysUser.getId())
                .nickName(sysUser.getNickName())
                .username(sysUser.getUsername())
                .phone(sysUser.getPhone())
                .menus(sysUser.getMenus()) // 侧边栏
                .permissions(sysUser.getPermissions()) // 按钮权限
                .accessToken(token)
                .build();
        Response<LoginResponseVo> ok = Response.ok(loginResult);
        String result = new Gson().toJson(ok);
        // 响应数据
        response.getWriter().write(result);
    }

    /**
     * 认证失败后的处理方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String returnData = "";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            returnData = "账号过期";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            returnData = "密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            returnData = "密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            returnData = "账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            returnData = "账号锁定";
        }
        // 用户不存在
        else if (failed instanceof InternalAuthenticationServiceException) {
            returnData = "用户不存在";
        }
        // 其他错误
        else {
            returnData = "未知异常";
        }
        // 处理编码方式 防止中文乱码
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 将反馈塞到HttpServletResponse中返回给前台
        Response result = Response.error(returnData);
        response.getWriter().write(new Gson().toJson(result));
    }
}
