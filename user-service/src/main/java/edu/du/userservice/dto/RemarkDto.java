package edu.du.userservice.dto;

import lombok.Data;

/**
 * 승인/거부 사유를 전달받기 위한 DTO
 */
@Data
public class RemarkDto {
    private String remark;         // 사유 텍스트
}