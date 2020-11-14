package com.fundamental.compiler;

import com.fundamental.compiler.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class FundamentalCompilerApplication {

    public static void results() {
        try {
            String location = "G:\\tailieuREstore\\java-Spring-framework\\implementation\\fundamental-compiler\\result-dir";
            File dir = new File(location);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("gcc de1bai3.c", null, dir);
            Process processRS = runtime.exec(location+"\\a.exe", null, dir);
            BufferedReader reader = new BufferedReader(new InputStreamReader(processRS.getInputStream()));
            String rs = "";
            while ((rs = reader.readLine()) != null) {
                System.out.println(rs);
            }
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(FundamentalCompilerApplication.class, args);
        results();
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
