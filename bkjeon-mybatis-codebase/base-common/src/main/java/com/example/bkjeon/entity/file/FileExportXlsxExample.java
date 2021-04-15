package com.example.bkjeon.entity.file;

import com.example.bkjeon.model.file.ToExcel;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileExportXlsxExample {

    @ToExcel("상품아이디")
    private String productId;

    @ToExcel("상품명")
    private String productName;

}
