package com.example.bkjeon.enums.enums.common;

import lombok.Getter;

@Getter
public enum EnumMapperKey {

	PD_SORT_FILTER("PdSortType");

	private String name;

	EnumMapperKey(String name) {
		this.name = name;
	}

}
