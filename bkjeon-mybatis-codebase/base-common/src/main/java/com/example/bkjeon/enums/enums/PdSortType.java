package com.example.bkjeon.enums.enums;

import lombok.Getter;

@Getter
public enum PdSortType implements PdSortMapperType {

	SALES_RATE_SORT("2", "판매순", 1),
	POPULARITY_SORT("1", "인기순", 2),
	REVIEW_SORT("3", "리뷰 많은 순", 3),
	REG_DESC_SORT("4", "최근 등록순", 4),
	PRICE_DESC_SORT("6", "높은 가격순", 5),
	PRICE_ASC_SORT("5", "낮은 가격순", 6);

	private String pdSortCd;
	private String pdSortNm;
	private Integer pdSortOrder;

	PdSortType(String pdSortCd, String pdSortNm, Integer pdSortOrder) {
		this.pdSortCd = pdSortCd;
		this.pdSortNm = pdSortNm;
		this.pdSortOrder = pdSortOrder;
	}

}
