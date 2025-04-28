INSERT INTO member (nickname, email, campaign_applied, role) VALUES
                                                                 ('홍길동', 'hong@test.com', false, 'ADMIN'),
                                                                 ('김철수', 'kim@test.com', true, 'USER'),
                                                                 ('이영희', 'lee@test.com', false, 'USER');

INSERT INTO business (name, industry, recruitment_period, max_people, status) VALUES
                                                                                  ('제주도 풀빌라', '한식', '2025-05-01 ~ 2025-05-30', 10, 'PENDING'),
                                                                                  ('강남 일식집', '일식', '2025-06-01 ~ 2025-06-30', 15, 'PENDING'),
                                                                                  ('부산 양식 레스토랑', '양식', '2025-07-01 ~ 2025-07-31', 20, 'PENDING');
-- src/main/resources/data.sql 에 아래 INSERT 문을 추가하세요.

-- 체험단 신청 샘플 데이터 삽입
INSERT INTO experiences (user_id, campaign_id, apply_date, status, remark) VALUES
                                                                               (1, 1, CURRENT_TIMESTAMP, 'PENDING', NULL),
                                                                               (2, 2, CURRENT_TIMESTAMP, 'PENDING', NULL),
                                                                               (3, 3, CURRENT_TIMESTAMP, 'PENDING', NULL),
                                                                               (1, 2, CURRENT_TIMESTAMP, 'APPROVED', '모든 요건 충족'),
                                                                               (2, 1, CURRENT_TIMESTAMP, 'CANCELLED', '신청자 요청에 의한 취소');


-- 샘플 리뷰 데이터 삽입
INSERT INTO reviews (username, rating, content, created_at) VALUES
                                                                ('alice', 5, '제품이 정말 마음에 들어요! 강력 추천합니다.', CURRENT_TIMESTAMP),
                                                                ('bob',   3, '배송은 빨랐지만 포장이 아쉬웠어요.', CURRENT_TIMESTAMP),
                                                                ('carol', 4, '가격 대비 성능이 준수합니다. 전체적으로 만족해요.', CURRENT_TIMESTAMP),
                                                                ('dave',  2, '기능이 기대 이하였고, 사용법이 복잡해요.', CURRENT_TIMESTAMP),
                                                                ('eve',   5, '재구매 의사 100%! 친구들에게도 추천했어요!', CURRENT_TIMESTAMP);