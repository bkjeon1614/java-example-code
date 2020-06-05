CREATE TABLE tb_board (
    BOARD_NO int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '게시판 고유 값',
    GROUP_NO int(11) unsigned NOT NULL COMMENT '게시판 그룹번호 (원글은 자신의 값)',
    SORT_SEQ int(2) unsigned NOT NULL COMMENT '게시글 정렬 순번',
    BOARD_LVL int(2) unsigned NOT NULL COMMENT '게시글 레벨(depth)',
    BOARD_TITLE varchar(45) COLLATE utf8_bin NOT NULL COMMENT '게시글 제목',
    BOARD_CONTENTS text COLLATE utf8_bin DEFAULT NULL COMMENT '게시글 내용',
    SYS_REGR_ID varchar(20) COLLATE utf8_bin NOT NULL COMMENT '시스템 등록자',
    SYS_REG_DTIME datetime DEFAULT current_timestamp() COMMENT '시스템 등록일자',
    SYS_MODR_ID varchar(20) COLLATE utf8_bin NOT NULL COMMENT '시스템 수정자',
    SYS_MOD_DTIME datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '시스템 수정일자',
    PRIMARY KEY (BOARD_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='계층형 게시판';