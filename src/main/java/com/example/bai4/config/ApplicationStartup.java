package com.example.bai4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

    @Autowired
    private Environment environment;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String port = environment.getProperty("server.port");
        // Default to 8080 if the port is not configured
        if (port == null) {
            port = "8080";
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Application is ready!");
        System.out.println("Access the product list here: http://localhost:" + port + "/products");
        System.out.println("--------------------------------------------------");
    }
}
