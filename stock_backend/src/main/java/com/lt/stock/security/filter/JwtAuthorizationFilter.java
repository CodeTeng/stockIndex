package com.lt.stock.security.filter;

import com.lt.stock.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @description: 授权过滤器
 * @author: ~Teng~
 * @date: 2023/2/14 18:52
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 获取请求头中的 token 信息
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        // 2. 判断 token 是否存在
        if (StringUtils.isAnyBlank(token)) {
            // 用户未登录 放行 去登录拦截
            filterChain.doFilter(request, response);
            return;
        }
        // 3. 判断 token 是否已经过期
        Claims claims = JwtTokenUtil.checkJWT(token);
        if (claims == null) {
            // token 解析错误
            response.getWriter().write("Illegal Token!!!");
            return;
        }
        if (JwtTokenUtil.isExpiration(token)) {
            // token 已经过期 重新登录
            filterChain.doFilter(request, response);
            return;
        }
        // 3. 如果 token 存在，解析 token 获取用户名和权限信息
        String username = JwtTokenUtil.getUsername(token);
        // 格式：ROLE_ADMIN,P6
        String role = JwtTokenUtil.getUserRole(token);
        // 获取以,分隔的权限字符串
        String roleNames = StringUtils.strip(role, "[]");
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(roleNames);
        // 4. 将用户名和权限组装成 UsernamePasswordAuthenticationToken
        // 参数1：用户名 参数2：密码 参数3：权限集合
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorityList);
        // 5. 将组装的 token 对象存入 security 上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 6. 放行，其他过滤器会做后序操作
        filterChain.doFilter(request, response);
    }
}
