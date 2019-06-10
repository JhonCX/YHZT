package com.cc.yhzt;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableCaching
@EnableTransactionManagement//开启事物支持，启用注解事物管理
public class YhztApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhztApplication.class, args);
    }

}
