package com.example.bkjeon.dto.validation;

import com.example.bkjeon.enums.validation.EnumTypeValid;
import com.example.bkjeon.enums.validation.ExampleEnumCustomType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class PostEnumCustomValidDTO {

    @EnumTypeValid(target = ExampleEnumCustomType.class, message = "올바른 exampleEnumAssertTrueType 값을 입력하여 주시길 바랍니다.")
    @ApiModelProperty(value = "예제타입(EXAMPLE_CREATE: 생성, EXAMPLE_UPDATE: 수정, EXAMPLE_DELETE: 삭제)")
    @NotEmpty(message = "exampleEnumAssertTrueType 를 입력하여 주시길 바랍니다.")
    private String exampleEnumAssertTrueType;

    @ApiModelProperty(value = "이름")
    @NotEmpty(message = "exampleName 를 입력하여 주시길 바랍니다.")
    private String exampleName;

    @Builder
    public PostEnumCustomValidDTO(String exampleEnumAssertTrueType, String exampleName) {
        this.exampleEnumAssertTrueType = exampleEnumAssertTrueType;
        this.exampleName = exampleName;
    }

}
