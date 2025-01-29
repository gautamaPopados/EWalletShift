package ru.cft.template.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.core.model.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByToken(String token);
    Optional<Session> findById(UUID id);
    boolean existsByToken(String token);
    boolean existsByUserIdAndIsActiveTrue(Long userId);
    List<Session> findAllByUserId(Long userId);
}
