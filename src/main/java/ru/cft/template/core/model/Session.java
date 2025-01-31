package ru.cft.template.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sessions")
public class Session {
    @Id
    @GeneratedValue
    @Column(name = "id", length=16)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;
}
