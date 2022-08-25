package com.example.bkjeon.enums.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제수단
 */
@Getter
@AllArgsConstructor
public enum EnumPayType {

	REALTIME_ACCOUNT_TRANSFER("실시간계좌이체"),
	EASY_ACCOUNT_TRANSFER("간편계좌이체"),
	KAKAO_PAY("카카오페이"),
	TOSS("토스"),
	PAYCO("페이코"),
	KAKAO_BANK("카카오뱅크"),
	K_BANK("케이뱅크"),
	SHINHAN_CARD("신한카드"),
	HYUNDAI_CARD("현대카드"),
	CITY_BANK("시티뱅크"),
	POINT("포인트"),
	COUPON("쿠폰"),
	EMPTY("없음");

	private String title;

}
