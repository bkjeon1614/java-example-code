package com.example.bkjeon.entity.common.log;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
