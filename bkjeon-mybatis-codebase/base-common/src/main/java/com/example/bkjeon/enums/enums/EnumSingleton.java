package com.example.bkjeon.enums.enums;

import lombok.Getter;

/**
 * Enum 으로 싱글톤 구현시에 장점
 * - Enum 타입은 기본적으로 직렬화가 가능하므로 Serializable 인터페이스를 구현할 필요가 없다.
 * - 리플렉션 문제도 발생하지 않음
 * - Enum 의 인스턴스 생성은 기본적으로 Thread safe 하다. (단, 열거형 클래스에 추가하는 메서드는 스레드 안정성을 보장하지 않으므로 배보다 배꼽이 더 큰 상황이되는 경우도 있으므로 주의해서 사용필요)
 */
@Getter
public enum EnumSingleton {

	INSTANCE;
	private String name;

}
