package com.example.bkjeon.feature.common.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonLogDTO {

    private String dbNm;
    private String callUrl;
    private String callMthdSpVal;
    private String callParaVal;
    private String svcCallNm;
    private String svcMthdNm;
    private Long execTme;
    private String logDesc;
    private String sysRegrId;
    private String sysModrId;

}