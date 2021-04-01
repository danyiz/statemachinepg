package hu.restapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class SpringRestApplication {

    @Autowired
    private StateMachine<String, String> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

           stateMachine.start();
           stateMachine.sendEvent("E1");
           log.info("State: {}", stateMachine.getState());
           log.info("Let's inspect the beans provided by Spring Boot:");
            stateMachine.sendEvent("E2");
            log.info("State: {}", stateMachine.getState());
            stateMachine.sendEvent("E3");
            stateMachine.sendEvent("E4");
            stateMachine.sendEvent("E5");
            stateMachine.sendEvent("end");
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                log.info(beanName);
//            }
            log.info("End");

        };
    }
}

