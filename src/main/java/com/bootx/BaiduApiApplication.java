package com.bootx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author black
 */
@SpringBootApplication
@EnableScheduling
public class BaiduApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaiduApiApplication.class, args);
    }

}
