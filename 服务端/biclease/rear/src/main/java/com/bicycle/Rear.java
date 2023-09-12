package com.bicycle;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bicycle.mapper")
public class Rear {
    public static void main(String[] args) {
        SpringApplication.run(Rear.class, args);
        System.out.println("后台服务器启动成功！");
    }
}
