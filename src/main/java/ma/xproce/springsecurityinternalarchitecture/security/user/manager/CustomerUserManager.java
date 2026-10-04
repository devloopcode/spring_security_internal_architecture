package ma.xproce.springsecurityinternalarchitecture.security.user.manager;

public class CustomerUserManager{}

/*
import lombok.AllArgsConstructor;
import ma.xproce.springsecurityinternalarchitecture.dao.entities.Customer;
import ma.xproce.springsecurityinternalarchitecture.dao.repositories.UserRepository;
import ma.xproce.springsecurityinternalarchitecture.mapper.UserMapper;
import ma.xproce.springsecurityinternalarchitecture.security.config.CustomDatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import java.util.Collection;
import java.util.List;

@Configuration
@AllArgsConstructor
public class CustomerUserManager implements UserDetailsManager {

    CustomDatabaseAccess customDatabaseAccess;
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public void createUser(UserDetails user) {
        Customer customer = userMapper.fromUserToCustomer((User) user);
        userRepository.save(customer);
    }

    @Override
    public void updateUser(UserDetails user) {



    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByEmail(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {



    }

    @Override
    public boolean userExists(String username) {
        List<Customer>  customers = userRepository.findByEmail(username);
        if(customers.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customDatabaseAccess.loadUserByUsername(username);
    }
}
*/