package tn.sdf.pfesdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
//@ComponentScan(basePackages = {"tn.sdf.pfesdf.controllers"})
public class PfeSdfApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfeSdfApplication.class, args);
    }

}
