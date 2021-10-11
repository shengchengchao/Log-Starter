package com.xixi.log.loghelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xixi.log.loghelper"})
public class LogHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogHelperApplication.class, args);
    }

}
