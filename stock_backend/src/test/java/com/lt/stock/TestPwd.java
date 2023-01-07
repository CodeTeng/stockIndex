package com.lt.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 19:06
 */
@SpringBootTest
public class TestAll {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPwd() {
        String pwd="1234";
        String encode = passwordEncoder.encode(pwd);
        // $2a$10$W864RVedOz/eRc7U2KQozOSK3m9fgnwaYnnC/Cj0QEaYq.qDgs/nO
        System.out.println(encode);
        boolean flag = passwordEncoder.matches(pwd, "$2a$10$W864RVedOz/eRc7U2KQozOSK3m9fgnwaYnnC/Cj0QEaYq.qDgs/nO");
        System.out.println(flag);
    }
}
