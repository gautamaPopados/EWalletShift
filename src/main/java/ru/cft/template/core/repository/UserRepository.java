package ru.cft.template.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.core.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<User> findById(Long id);
}
