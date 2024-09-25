package com.example.bkjeon.config.bean;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.bkjeon.enums.enums.PdSortMapper;
import com.example.bkjeon.enums.enums.PdSortMapperValue;
import com.example.bkjeon.enums.enums.PdSortType;
import com.example.bkjeon.enums.enums.common.EnumMapperKey;

/**
 * 상품 정렬코드 Bean
 */
@Component
public class PdSortMapperBean {

	private final PdSortMapper pdSortMapper;

	public PdSortMapperBean(@Lazy PdSortMapper pdSortMapper) {
		this.pdSortMapper = pdSortMapper;
	}

	@Bean
	public PdSortMapper pdSortMapper() {
		PdSortMapper pdSortMapper = new PdSortMapper();
		pdSortMapper.put(EnumMapperKey.PD_SORT_FILTER.getName(), PdSortType.class);	// 팩토리에 enum 등록
		return pdSortMapper;
	}

	public List<PdSortMapperValue> getPdSortList() {
		return pdSortMapper.get(EnumMapperKey.PD_SORT_FILTER.getName());
	}

}
