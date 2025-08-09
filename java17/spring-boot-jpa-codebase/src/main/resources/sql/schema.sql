CREATE TABLE advertisement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,                                       -- 광고 ID
    name VARCHAR(100) NOT NULL UNIQUE,                                          -- 광고명 (중복 불가)
    description TEXT NOT NULL,                                                  -- 광고 문구
    reward_amount INT NOT NULL,                                                 -- 광고 참여시 적립 액수
    participation_limit INT NOT NULL,                                           -- 광고 참여 가능 횟수
    image_url VARCHAR(500) NOT NULL,                                            -- 광고 이미지 URL
    start_date DATETIME NOT NULL,                                               -- 광고 노출 시작일
    end_date DATETIME NOT NULL,                                                 -- 광고 노출 종료일
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                             -- 생성 일시
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 마지막 수정 일시
);

CREATE TABLE advertisement_participation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,                   -- 광고 참여 ID
    user_id BIGINT NOT NULL,                                -- 참여한 유저 ID
    ad_id BIGINT NOT NULL,                                  -- 참여한 광고 ID
    participation_time DATETIME NOT NULL,                   -- 광고 참여 시각
    reward_amount INT NOT NULL,                             -- 적립 액수
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP          -- 생성 일시
);