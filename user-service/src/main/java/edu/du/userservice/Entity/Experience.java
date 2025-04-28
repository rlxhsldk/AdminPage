package edu.du.userservice.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "experiences")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Business campaign;

    private LocalDateTime applyDate;

    @Enumerated(EnumType.STRING)
    private ExperienceStatus status;

    private String remark;

    public enum ExperienceStatus { PENDING, APPROVED, CANCELLED }

    @PrePersist
    public void prePersist() {
        if (applyDate == null) applyDate = LocalDateTime.now();
        if (status == null) status = ExperienceStatus.PENDING;
    }
}