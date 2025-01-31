package ru.cft.template.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.core.model.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByIdAndActiveIsTrue(UUID id);
    Optional<Session> findById(UUID id);
}
