package br.com.certificatevalid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class CertificateValidApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificateValidApplication.class, args);
    }

}
