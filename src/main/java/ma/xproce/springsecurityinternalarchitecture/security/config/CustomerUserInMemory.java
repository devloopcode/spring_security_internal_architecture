package ma.xproce.springsecurityinternalarchitecture.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class CustomerUserInMemory {

    //@Bean
    public InMemoryUserDetailsManager users() {
        UserDetails user = User.withUsername("xproce")
                .password("12345")
                .authorities("user", "update")
                .build();

        UserDetails admin = User.withUsername("manager")
                .password(encoder().encode("12345"))
                .authorities("admin", "delete")
                .roles()
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }


    //@Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
