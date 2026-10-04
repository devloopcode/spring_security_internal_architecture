package ma.xproce.springsecurityinternalarchitecture.mapper;

import ma.xproce.springsecurityinternalarchitecture.dao.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    ModelMapper mapper = new ModelMapper();


    public Customer fromUserToCustomer(User user) {
        String role = user.getAuthorities().parallelStream().toList().get(0).toString();
        Customer customer = mapper.map(user, Customer.class);
        customer.setPwd(user.getPassword());
        customer.setRole(role);
        return customer;
    }
}
