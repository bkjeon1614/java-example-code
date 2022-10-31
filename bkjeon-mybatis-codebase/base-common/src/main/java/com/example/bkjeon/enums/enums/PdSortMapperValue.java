package com.example.bkjeon.enums.enums;

import lombok.Getter;

@Getter
public class PdSortMapperValue {

	private String pdSortCd;
	private String pdSortNm;
	private Integer pdSortOrder;

	/**
	 * PdSortMapperType 인터페이스를 인자로 받는다.
	 * @param pdSortMapperType
	 */
	public PdSortMapperValue(PdSortMapperType pdSortMapperType) {
		this.pdSortCd = pdSortMapperType.getPdSortCd();
		this.pdSortNm = pdSortMapperType.getPdSortNm();
		this.pdSortOrder = pdSortMapperType.getPdSortOrder();
	}

}
