package com.lt.stock.service.impl;

import com.lt.stock.common.Response;
import com.lt.stock.common.enums.Number;
import com.lt.stock.common.enums.ResponseCode;
import com.lt.stock.mapper.SysUserMapper;
import com.lt.stock.pojo.entity.SysUser;
import com.lt.stock.pojo.dto.LoginRequestDto;
import com.lt.stock.pojo.vo.LoginResponseVo;
import com.lt.stock.service.UserService;
import com.lt.stock.utils.IdWorker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 19:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Response<LoginResponseVo> login(LoginRequestDto loginRequestDto) {
        if (loginRequestDto == null) {
            return Response.error(ResponseCode.DATA_ERROR.getMessage());
        }
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        String code = loginRequestDto.getCode();
        String rKey = loginRequestDto.getRkey();
        if (StringUtils.isBlank(code)) {
            return Response.error(ResponseCode.SYSTEM_VERIFY_CODE_NOT_EMPTY.getMessage());
        }
        if (StringUtils.isAnyBlank(username, password, rKey)) {
            return Response.error(ResponseCode.DATA_ERROR.getMessage());
        }
        // 从 Redis 中获取验证码
        String rCode = stringRedisTemplate.opsForValue().get(rKey);
        // 验证码过期
        if (StringUtils.isBlank(rCode)) {
            return Response.error(ResponseCode.SYSTEM_VERIFY_CODE_EXPIRED.getMessage());
        }
        // 验证码错误
        if (!code.equals(rCode)) {
            return Response.error(ResponseCode.SYSTEM_VERIFY_CODE_ERROR.getMessage());
        }
        // redis 清除 key
        stringRedisTemplate.delete(rKey);
        // 根据用户名查询用户信息
        SysUser user = sysUserMapper.findByUserName(username);
        // 判断用户是否存在，存在则进行密码校验对比
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return Response.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
        }
        // 判断用户状态
        Integer status = user.getStatus();
        if (status == Number.Two.getNumber()) {
            return Response.error(ResponseCode.SYSTEM_USERNAME_LOCKED.getMessage());
        }
        LoginResponseVo loginResponseVo = new LoginResponseVo();
        BeanUtils.copyProperties(user, loginResponseVo);
        return Response.ok(loginResponseVo);
    }

    @Override
    public Response<Map<String, String>> generateCaptcha() {
        // 生成4位数字验证码
        String code = RandomStringUtils.randomNumeric(4);
        // 生成唯一id
        String rkey = String.valueOf(idWorker.nextId());
        // 验证码存入 redis 中，有效期为1分钟
        stringRedisTemplate.opsForValue().set(rkey, code, 60, TimeUnit.SECONDS);
        // 返回数据
        Map<String, String> map = new HashMap<>(2);
        map.put("rkey", rkey);
        map.put("code", code);
        return Response.ok(map);
    }
}
