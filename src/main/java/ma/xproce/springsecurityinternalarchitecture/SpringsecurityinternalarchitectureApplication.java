package ma.xproce.springsecurityinternalarchitecture;

import ma.xproce.springsecurityinternalarchitecture.dao.entities.Customer;
import ma.xproce.springsecurityinternalarchitecture.dao.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringsecurityinternalarchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityinternalarchitectureApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserRepository userRepository) {
        return args -> {
            List<Customer> customers = List.of(
                    new Customer(null, "alice.smith@gmail.com", "password123", "USER"),
                    new Customer(null, "bob.johnson@yahoo.com", "securePass!", "USER"),
                    new Customer(null, "charlie.williams@outlook.com", "qwerty789", "USER"),
                    new Customer(null, "david.brown@gmail.com", "admin2024", "ADMIN"),
                    new Customer(null, "emma.jones@hotmail.com", "pass1234", "USER"),
                    new Customer(null, "frank.miller@gmail.com", "helloWorld!", "USER"),
                    new Customer(null, "grace.davis@gmail.com", "grace@2024", "USER"),
                    new Customer(null, "henry.wilson@company.com", "wilson@123", "MANAGER"),
                    new Customer(null, "isabella.moore@gmail.com", "bellaSecure!", "USER"),
                    new Customer(null, "jack.taylor@corp.com", "taylor@admin", "ADMIN"),
                    new Customer(null, "karen.anderson@gmail.com", "anderson2024", "USER"),
                    new Customer(null, "liam.thomas@yahoo.com", "liam#321", "USER"),
                    new Customer(null, "mia.jackson@outlook.com", "jackson@pass", "USER"),
                    new Customer(null, "noah.white@company.com", "whiteSecure!", "MANAGER"),
                    new Customer(null, "olivia.harris@gmail.com", "olivia2025", "USER"),
                    new Customer(null, "peter.martin@corp.com", "martin@admin", "ADMIN"),
                    new Customer(null, "quinn.lewis@gmail.com", "lewisPass!", "USER"),
                    new Customer(null, "rachel.walker@business.com", "rachel123", "USER"),
                    new Customer(null, "samuel.young@gmail.com", "young@secure", "USER"),
                    new Customer(null, "tina.king@yahoo.com", "king2025", "USER"),
                    new Customer(null, "xproce", "12345", "ADMIN"));
            userRepository.saveAll(customers);
        };
    }
}
