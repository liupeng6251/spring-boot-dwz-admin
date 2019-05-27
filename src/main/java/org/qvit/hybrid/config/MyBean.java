package org.qvit.hybrid.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements CommandLineRunner {

    public void run(String... args) {
        System.err.println(args);
        System.err.println(args.length);
        for(String str:args){
            System.err.println(str);
        }
    }

}