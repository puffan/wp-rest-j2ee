package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;


@ServletComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        final ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
