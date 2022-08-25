package com.example.bkjeon.base.services.api.v1.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.enums.enums.EnumPayGroup;
import com.example.bkjeon.enums.enums.EnumPayType;
import com.example.bkjeon.enums.enums.EnumSingleton;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/data/enums")
public class EnumController {

	/**
	 * Enum 사용 예제
	 * - 데이터들간의 연관관계 표현
	 * - 상태와 행위를 한곳에서 관리
	 * - 데이터 그룹관리
	 */
	@GetMapping
	public void getEnumList() {
		log.info(">>>>>>>>>>>>>>>>>> ");
		log.info("TableType.java 참고");
		log.info(">>>>>>>>>>>>>>>>>> ");
		log.info("Code에게 직접 계산을 요청 -> EnumCalculatorType.calculate(); 참고");
		log.info(">>>>>>>>>>>>>>>>>> ");
		EnumPayGroup payGroup = EnumPayGroup.findByPayCode(EnumPayType.KAKAO_PAY);
		log.info("결제수단: KAKAO_PAY 코드값 payGroup.name(): {}", payGroup.name());
		log.info("결제수단: KAKAO_PAY 코드값 payGroup.getTitle(): {}", payGroup.getTitle());
		log.info(">>>>>>>>>>>>>>>>>> ");
	}

	/**
	 * Enum 을 사용한 싱글톤
	 */
	@GetMapping("singleton")
	public void getEnumSingleton() {
		String name = EnumSingleton.INSTANCE.getName();
		log.info(">>>>>>>>>>>>>>>>>> {}", name);
	}

}
