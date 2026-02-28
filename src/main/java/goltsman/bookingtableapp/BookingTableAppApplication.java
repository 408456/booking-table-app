package goltsman.bookingtableapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BookingTableAppApplication {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder(4).encode("admin123"));
        SpringApplication.run(BookingTableAppApplication.class, args);
    }
}
