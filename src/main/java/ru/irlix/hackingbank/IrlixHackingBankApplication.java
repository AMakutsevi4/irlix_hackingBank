package ru.irlix.hackingbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IrlixHackingBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrlixHackingBankApplication.class, args);
        System.out.println("Go to: http://localhost:8080/clients");
        System.out.println("Go to: http://localhost:8080/payments");
    }

}
