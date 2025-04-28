package edu.du.userservice.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 가게 이름

    private String industry;  // 업종 (한식, 일식, 양식 등)

    @Column(name = "recruitment_period")
    private String recruitmentPeriod;  // 모집 기간 (예: 2025-05-01 ~ 2025-05-30)

    @Column(name = "max_people")
    private int maxPeople;  // 모집 인원

    @Column(name = "application_date", updatable = false)
    private LocalDateTime applicationDate = LocalDateTime.now();  // 사업체 신청 날짜, 기본값은 현재 시간

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private Status status = Status.PENDING;  // 상태, 기본값은 '대기(PENDING)'

    public enum Status {
        PENDING, APPROVED, CANCELLED  // 대기, 승인, 취소 상태
    }
}
