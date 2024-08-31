package com.example.bkjeon.enums.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * [데이터들간의 연관관계 표현]
 * -> 다른 테이블인데 같은 속성의 코드를 갖고있지만 저장되는 값이 어느 테이블은 Y/N 이고 또 다른 테이블은 1/0 일 경우
 *    새로운 타입이 추가되면 if 문을 포함한 메소드 단위로 코드가 증가하는것을 방지하기위해 Enum 으로 추출
 */
@Getter
@AllArgsConstructor
public enum EnumTableType {

	Y("1", true),
	N("0", false);

	private String table1Value;
	private boolean table2Value;

	public boolean isTable2Value() {
		return table2Value;
	}

}
