package com.example.bkjeon.entity.common.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonLog {

    /**
     * logNo: 로그번호
     * svcCallNm: 서비스 호출명(컨트롤러 + 메서드)
     * callUrl: 호출URL
     * callMthdSpVal: 호출방법구분값
     * callParaVal: 호출매개변수값
     * execTme: 실행시간 (단위:m/s)
     * logDesc: 로그설명
     */
    private Long logNo;
    private String callUrl;
    private String callMthdSpVal;
    private String callParaVal;
    private String svcCallNm;
    private String svcMthdNm;
    private Long execTme;
    private String logDesc;

    private String sysRegrId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sysRegDtime;

    private String sysModrId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sysModDtime;

}
