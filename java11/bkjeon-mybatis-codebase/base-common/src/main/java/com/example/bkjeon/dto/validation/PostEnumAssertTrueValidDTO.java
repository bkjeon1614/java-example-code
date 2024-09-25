package com.example.bkjeon.dto.validation;

import com.example.bkjeon.enums.validation.ExampleEnumAssertTrueType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

@Getter
@NoArgsConstructor
public class PostEnumAssertTrueValidDTO {

    @ApiModelProperty(value = "예제타입(EXAMPLE_CREATE: 생성, EXAMPLE_UPDATE: 수정, EXAMPLE_DELETE: 삭제)")
    @NotEmpty(message = "exampleEnumAssertTrueType 를 입력하여 주시길 바랍니다.")
    private String exampleEnumAssertTrueType;

    @ApiModelProperty(value = "이름")
    @NotEmpty(message = "exampleName 를 입력하여 주시길 바랍니다.")
    private String exampleName;

    @JsonIgnore
    @AssertTrue(message = "올바른 예제타입 값을 입력하여 주시길 바랍니다.")
    private boolean isExampleEnumAssertTrueTypeValid() {
        return Arrays.stream(
            ExampleEnumAssertTrueType.values())
                .anyMatch(type -> type.getExampleEnumAssertTrueType().matches(exampleEnumAssertTrueType)
        );
    }

}
