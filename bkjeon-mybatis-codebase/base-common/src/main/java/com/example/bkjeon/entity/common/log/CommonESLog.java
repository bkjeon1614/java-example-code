package com.example.bkjeon.entity.common.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CommonESLog {

    /**
     * logId: 로그ID (logType + svcCallNm + loginId + sysRegDtime) => sha256 encoding
     * logType: 로그타입(action)
     * callUrl: 호출URL
     * callMthdSpVal: 호출방법구분값
     * callParaVal: 호출매개변수값
     * svcCallNm: 서비스명(omt: omt(admin), oyez: 올영EZ, lounge: 올리브라운지)
     * svcMthdNm: 서비스 메서드명(컨트롤러 + 메서드)
     * execTme: 실행시간 (단위:m/s)
     * logDesc: 로그설명
     * loginId: 로그인된 ID
     * sysRegDtime: 저장시간(yyyyMMddhhmmss)
     */
    @JsonProperty("log_id")
    private String logId;

    @JsonProperty("log_type")
    private String logType;

    @JsonProperty("call_url")
    private String callUrl;

    @JsonProperty("call_mthd_sp_val")
    private String callMthdSpVal;

    @JsonProperty("call_para_val")
    private String callParaVal;

    @JsonProperty("svc_call_nm")
    private String svcCallNm;

    @JsonProperty("svc_mthd_nm")
    private String svcMthdNm;

    @JsonProperty("exec_tme")
    private Long execTme;

    @JsonProperty("log_desc")
    private String logDesc;

    @JsonProperty("login_id")
    private String loginId;

    @JsonProperty("sys_reg_date_time")
    private String sysRegDateTime;

    @Builder
    public CommonESLog(
        String logId,
        String logType,
        String callUrl,
        String callMthdSpVal,
        String callParaVal,
        String svcCallNm,
        String svcMthdNm,
        Long execTme,
        String logDesc,
        String loginId,
        String sysRegDateTime
    ) {
        this.logId = logId;
        this.logType = logType;
        this.callUrl = callUrl;
        this.callMthdSpVal = callMthdSpVal;
        this.callParaVal = callParaVal;
        this.svcCallNm = svcCallNm;
        this.svcMthdNm = svcMthdNm;
        this.execTme = execTme;
        this.logDesc = logDesc;
        this.loginId = loginId;
        this.sysRegDateTime = sysRegDateTime;
    }

    public void logUpdate(String logType, String logDesc, String loginId) {
        this.logType = logType;
        this.logDesc = logDesc;
        this.loginId = loginId;
    }

}
