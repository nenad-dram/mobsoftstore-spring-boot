package com.endyary.mobsoftstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MobSoftStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobSoftStoreApplication.class, args);
    }

}
