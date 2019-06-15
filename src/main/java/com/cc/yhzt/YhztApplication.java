package com.cc.yhzt;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.cc.yhzt.common.config.MyExceptionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//开启事物支持，启用注解事物管理
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableCaching
public class YhztApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhztApplication.class, args);
    }


    // 注册统一异常处理bean
    @Bean
    public MyExceptionResolver myExceptionResolver() {
        return new MyExceptionResolver();
    }
}
