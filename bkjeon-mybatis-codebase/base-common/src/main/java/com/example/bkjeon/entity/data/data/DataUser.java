package com.example.bkjeon.entity.data.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;

@ToString
@Getter
@AllArgsConstructor
public class DataUser {

    private String name;
    private int age;

    public DiffResult diff(DataUser other) {
        return new DiffBuilder(this, other, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("name", this.name, other.name)
            .append("age", this.age, other.age)
            .build();
    }

}
