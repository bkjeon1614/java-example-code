package com.example.bkjeon.enums.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * [데이터 그룹관리]
 * - 결제종류를 기준으로 다른 기능이 필요할 때 수행되는 메소드가 추가될때마다 그에 따른 결제종류의 분기처리가 중복으로 많이 발생하게 된다.
 *   그래서 결제종류, 결제수단등의 관계를 명확하되 각 타입은 본인이 수행해야할 기능과 책임만 가질 수 있게 하려고 Enum 을 생성
 */
@Getter
@AllArgsConstructor
public enum EnumPayGroup {

	CASH("현금", Arrays.asList(EnumPayType.REALTIME_ACCOUNT_TRANSFER, EnumPayType.EASY_ACCOUNT_TRANSFER)),
	APP("앱", Arrays.asList(EnumPayType.KAKAO_PAY, EnumPayType.TOSS, EnumPayType.PAYCO)),
	CARD("카드", Arrays.asList(EnumPayType.KAKAO_BANK, EnumPayType.K_BANK, EnumPayType.SHINHAN_CARD, EnumPayType.HYUNDAI_CARD, EnumPayType.CITY_BANK)),
	ETC("기타", Arrays.asList(EnumPayType.POINT, EnumPayType.COUPON)),
	EMPTY("없음", Collections.EMPTY_LIST);

	private String title;
	private List<EnumPayType> payList;

	public static EnumPayGroup findByPayCode(EnumPayType payType) {
		return Arrays.stream(EnumPayGroup.values())
			.filter(enumPayGroup -> enumPayGroup.hashPayCode(payType))
			.findAny()
			.orElse(EMPTY);
	}

	public boolean hashPayCode(EnumPayType payType) {
		return payList.stream()
			.anyMatch(pay -> pay.equals(payType));
	}

}
