package de.antrophos.spring.demo.old;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class XmlSpringExampleApplication {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(XmlSpringExampleApplication.class, args);
    }
}
