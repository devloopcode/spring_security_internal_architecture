package ma.xproce.springsecurityinternalarchitecture.dao.repositories;

import ma.xproce.springsecurityinternalarchitecture.dao.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByEmail(String email);
    void deleteByEmail(String email);
}
