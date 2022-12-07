package com.example.bkjeon.enums.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 상품 정렬코드 Enum Value 팩토리 클래스
 */
public class PdSortMapper {

	private Map<String, List<PdSortMapperValue>> factory = new LinkedHashMap<>();

	public PdSortMapper() {}

	public void put(String key, Class<? extends PdSortMapperType> e) {
		factory.put(key, toEnumValues(e));
	}

	/**
	 * PdSortMapperType 인터페이스 구현체만 오도록 제한
	 * @param e
	 * @return
	 */
	private List<PdSortMapperValue> toEnumValues(Class<? extends PdSortMapperType> e) {
		return Arrays.stream(e.getEnumConstants()).map(PdSortMapperValue::new).collect(Collectors.toList());
	}

	public List<PdSortMapperValue> get(String key) {
		return factory.get(key);
	}

	public Map<String, List<PdSortMapperValue>> get(List<String> keys) {
		if (keys == null || keys.size() == 0) {
			return new LinkedHashMap<>();
		}

		return keys.stream().collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
	}

	public Map<String, List<PdSortMapperValue>> getAll() {
		return factory;
	}

}
