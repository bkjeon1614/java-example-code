package com.bkjeon.codebase.application.command;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadAdCommand {

    private int size;

    public static ReadAdCommand from(int size) {
        return ReadAdCommand.builder().size(size).build();
    }

}