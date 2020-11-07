package com.example.bkjeon.feature.validation;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostValidDTO {

    @NotNull
    private String name;

    @Email
    private String email;

}
