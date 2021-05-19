package com.example.bkjeon.dto.common.log;

import com.example.bkjeon.enums.log.LogType;
import com.example.bkjeon.enums.log.SvcCallNm;
import com.example.bkjeon.enums.validation.EnumTypeValid;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonESLogSaveRequestDTO {

    @EnumTypeValid(target = LogType.class, message = "올바른 로그타입 값을 입력하여 주시길 바랍니다.")
    @ApiModelProperty(value = "로그타입(select: 조회, create: 생성, update: 수정, delete: 삭제)")
    @NotEmpty(message = "logType 를 입력하여 주시길 바랍니다.")
    private String logType;

    @ApiModelProperty(value = "호출URL")
    @NotEmpty(message = "callUrl 를 입력하여 주시길 바랍니다.")
    private String callUrl;

    @ApiModelProperty(value = "호출방법구분값")
    @NotEmpty(message = "callMthdSpVal 를 입력하여 주시길 바랍니다.")
    private String callMthdSpVal;

    @ApiModelProperty(value = "호출매개변수값")
    private String callParaVal;

    @EnumTypeValid(target = SvcCallNm.class, message = "올바른 서비스명 값을 입력하여 주시길 바랍니다.")
    @ApiModelProperty(value = "서비스명(omt: omt(admin), oyez: 올영EZ, lounge: 올리브라운지)")
    @NotEmpty(message = "svcCallNm 를 입력하여 주시길 바랍니다.")
    private String svcCallNm;

    @ApiModelProperty(value = "서비스 메서드명(컨트롤러 + 메서드)")
    private String svcMthdNm;

    @ApiModelProperty(value = "실행시간 (단위:m/s)")
    private Long execTme;

    @ApiModelProperty(value = "로그설명")
    private String logDesc;

    @ApiModelProperty(value = "로그인된 ID")
    @NotEmpty(message = "loginId 를 입력하여 주시길 바랍니다.")
    private String loginId;

    @Builder
    public CommonESLogSaveRequestDTO(
        String logType,
        String callUrl,
        String callMthdSpVal,
        String callParaVal,
        String svcCallNm,
        String svcMthdNm,
        Long execTme,
        String logDesc,
        String loginId
    ) {
        this.logType = logType;
        this.callUrl = callUrl;
        this.callMthdSpVal = callMthdSpVal;
        this.callParaVal = callParaVal;
        this.svcCallNm = svcCallNm;
        this.svcMthdNm = svcMthdNm;
        this.execTme = execTme;
        this.logDesc = logDesc;
        this.loginId = loginId;
    }

}
