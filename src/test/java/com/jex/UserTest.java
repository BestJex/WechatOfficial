package com.jex;

import com.jex.official.MainApplication;
import com.jex.official.common.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class UserTest {

    @Test
    public void getPassword() {
        String password = "123456";
        System.out.println(SecurityUtils.SHA256WithKey(password));
    }
}
