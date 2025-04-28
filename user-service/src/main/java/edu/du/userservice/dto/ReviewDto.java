package edu.du.userservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String username;
    private int rating;
    private String content;
    private LocalDateTime createdAt;
}