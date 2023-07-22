package br.ifsp.techmaps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TechMapsApplication {

    public static void main(String[] args) {SpringApplication.run(TechMapsApplication.class, args);}

}
