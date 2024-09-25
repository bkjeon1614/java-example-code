package com.example.bkjeon.base.services.api.v1.data;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.config.bean.PdSortMapperBean;
import com.example.bkjeon.enums.enums.EnumPayGroup;
import com.example.bkjeon.enums.enums.EnumPayType;
import com.example.bkjeon.enums.enums.EnumSingleton;
import com.example.bkjeon.enums.enums.PdSortMapperValue;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/data/enum")
@RequiredArgsConstructor
public class EnumController {

	private final PdSortMapperBean pdSortMapperBean;

	@ApiOperation("DB로 관리하기 애매한 enum 값들을 코드 기반으로 관리할 때")
	@GetMapping("enums")
	public List<PdSortMapperValue> getEnumList() {
		// Bean 사용 O
		return pdSortMapperBean.getPdSortList();

		// Bean 사용 X
		/*
		return Arrays.stream(PdSortType.values()).sorted(Comparator.comparing(PdSortType::getPdSortOrder)).map(
			PdSortMapperValue::new).collect(Collectors.toList());
		 */
	}

	/**
	 * Enum 사용 예제
	 * - 데이터들간의 연관관계 표현
	 * - 상태와 행위를 한곳에서 관리
	 * - 데이터 그룹관리
	 */
	@ApiOperation("Enum 사용예제")
	@GetMapping("enums/findOne")
	public void findEnum() {
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

	@ApiOperation("Enum 을 사용한 싱글톤")
	@GetMapping("enums/singleton")
	public void getEnumSingleton() {
		String name = EnumSingleton.INSTANCE.getName();
		log.info(">>>>>>>>>>>>>>>>>> {}", name);
	}

}
