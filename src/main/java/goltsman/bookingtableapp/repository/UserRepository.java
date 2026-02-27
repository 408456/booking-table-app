package goltsman.bookingtableapp.repository;

import goltsman.bookingtableapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}
