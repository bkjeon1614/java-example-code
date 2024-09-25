package com.example.bkjeon.dto.actuator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibarayInfo {

    private String name;
    private String version;

}
