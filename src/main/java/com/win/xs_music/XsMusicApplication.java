package com.win.xs_music;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@Slf4j //日志
@ServletComponentScan
public class XsMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(XsMusicApplication.class, args);
    }

}
