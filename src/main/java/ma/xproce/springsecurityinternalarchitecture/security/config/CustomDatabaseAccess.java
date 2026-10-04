package ma.xproce.springsecurityinternalarchitecture.security.config;


import lombok.AllArgsConstructor;
import ma.xproce.springsecurityinternalarchitecture.dao.entities.Customer;
import ma.xproce.springsecurityinternalarchitecture.dao.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User ;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomDatabaseAccess implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String login;
        String password;
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Customer> customers = userRepository.findByEmail(username);

        if (customers.size() == 0) {
            System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~User Not Found try different crudentials");
            throw new UsernameNotFoundException("User Not Found try different crudentials");
        } else {
            Customer customer = customers.get(0);
            login = customer.getEmail();
            password = customer.getPwd();
            authorities.add(new SimpleGrantedAuthority(customer.getRole()));
        }
        return new User(login, password, authorities);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
