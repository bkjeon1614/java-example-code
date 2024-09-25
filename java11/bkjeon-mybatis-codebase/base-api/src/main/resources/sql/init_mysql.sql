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

CREATE TABLE `tb_cm_his_l` (
  `LOG_NO` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '로그번호',
  `CALL_URL` varchar(2000) NOT NULL COMMENT '호출URL',
  `CALL_MTHD_SP_VAL` varchar(4000) NOT NULL COMMENT '호출방법구분값',
  `CALL_PARA_VAL` mediumtext COMMENT '호출매개변수값',
  `SVC_CALL_NM` varchar(200) NOT NULL COMMENT '서비스 호출명',
  `SVC_MTHD_NM` varchar(200) NOT NULL COMMENT '서비스 호출명(메서드)',
  `EXEC_TME` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '실행시간 (단위:m/s)',
  `LOG_DESC` varchar(4000) DEFAULT NULL COMMENT '로그설명',
  `SYS_REGR_ID` varchar(20) NOT NULL COMMENT '시스템 등록일',
  `SYS_REG_DTIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '시스템 등록일자',
  `SYS_MODR_ID` varchar(20) NOT NULL COMMENT '시스템 수정자',
  `SYS_MOD_DTIME` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '시스템 수정일자',
  PRIMARY KEY (`LOG_NO`),
  KEY `del_log_idx` (`SYS_REG_DTIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='애플리케이션 공통 로그';
