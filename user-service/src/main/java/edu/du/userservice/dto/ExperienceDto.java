package edu.du.userservice.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * 체험단 신청 정보를 클라이언트에 전달하기 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceDto {
    private Long id;               // 신청 ID
    private Long userId;           // 신청자 회원 ID
    private String username;       // 신청자 닉네임
    private Long campaignId;       // 캠페인(사업체) ID
    private String campaignName;   // 캠페인 이름
    private LocalDateTime applyDate; // 신청 일시
    private String status;         // 상태 (PENDING, APPROVED, CANCELLED)
    private String remark;         // 관리자 메모(승인/거부 사유)
}