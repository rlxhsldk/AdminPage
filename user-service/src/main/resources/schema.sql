-- member 테이블 생성
CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nickname VARCHAR(100) NOT NULL,
                        email VARCHAR(255),  -- 이메일 필드 추가
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        campaign_applied BOOLEAN DEFAULT FALSE,
                        role VARCHAR(50)
);

-- business 테이블 생성
CREATE TABLE business (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,  -- 가게 이름
                          industry VARCHAR(50) NOT NULL,  -- 업종 (한식, 일식, 양식 등)
                          recruitment_period VARCHAR(100),  -- 모집 기간 (예: 2025-05-01 ~ 2025-05-30)
                          max_people INT,  -- 모집 인원
                          application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 사업체 신청 날짜, 기본값은 현재 시간
                          status VARCHAR(20) DEFAULT 'PENDING'  -- 상태 (대기, 승인, 취소 등), 기본값은 'PENDING'
);

-- experiences 테이블 생성 (member, business 테이블 아래에 추가)
CREATE TABLE experiences (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             user_id BIGINT       NOT NULL,  -- member.id 참조
                             campaign_id BIGINT   NOT NULL,  -- business.id 참조
                             apply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 신청 일시
                             status VARCHAR(20)    DEFAULT 'PENDING',          -- 상태 (PENDING, APPROVED, CANCELLED)
                             remark VARCHAR(255),                              -- 관리자 메모(승인/거부 사유)
                             FOREIGN KEY (user_id)     REFERENCES member(id),
                             FOREIGN KEY (campaign_id) REFERENCES business(id)
);

