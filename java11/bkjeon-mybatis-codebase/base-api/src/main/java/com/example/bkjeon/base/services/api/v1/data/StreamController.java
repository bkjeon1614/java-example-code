package com.example.bkjeon.base.services.api.v1.data;

import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.stream.Stream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.entity.data.stream.StreamUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/data/stream")
public class StreamController {

    // 1~5 이내의 숫자만 가능
    private static final String REG_EXP = "^[0-9]{1,5}$";

    @ApiOperation("Stream List Merge(=concat)")
    @GetMapping("listConcat")
    public void isListConcat() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("51021614", 20),
            new StreamUser("5102", 10)
        );
        List<StreamUser> streamUserList2 = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("51022BB", 10)
        );

        // merge
        List<StreamUser> mergeList = Stream.concat(streamUserList.stream(), streamUserList2.stream())
            .distinct()
            .collect(Collectors.toList());
        System.out.println(mergeList.size());   // 5
    }

    @ApiOperation("Stream List to Map 변환")
    @GetMapping("listToMap")
    public void isListToMap() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("51021614", 20),
            new StreamUser("5102", 10)
        );
        Map<String, Integer> streamUserMap = streamUserList.stream()
            .collect(Collectors.toMap(StreamUser::getName, StreamUser::getAge));
        System.out.println(streamUserMap.get("5102"));  // 10

        // 키 중복 제외
        List<StreamUser> streamUserList2 = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("51021614", 20),
            new StreamUser("5102", 10),
            new StreamUser("5102", 20)
        );
        // 1. 기존 값을 유지할 경우
        Map<String, Integer> streamUserMap2 = streamUserList2.stream()
            .collect(Collectors.toMap(StreamUser::getName, StreamUser::getAge, (oldValue, newValue) -> oldValue));
        System.out.println(streamUserMap2.get("5102")); // 10
        // 2. 새로운 값을 유지할 경우
        Map<String, Integer> streamUserMap3 = streamUserList2.stream()
            .collect(Collectors.toMap(StreamUser::getName, StreamUser::getAge, (oldValue, newValue) -> newValue));
        System.out.println(streamUserMap3.get("5102")); // 20
    }

    @ApiOperation("정규식 체크")
    @GetMapping("regExp")
    public void getRegExpList() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("51021614", 20),
            new StreamUser("5102", 10)
        );
        List<String> streamUserRegExpList = streamUserList.stream()
            .map(StreamUser::getName)
            .filter(o -> o.matches(REG_EXP))
            .collect(Collectors.toList());
        System.out.println(streamUserRegExpList.toString());
    }

    @ApiOperation("전체 데이터에서 skip / limit 를 활용한 페이지네이션 처리")
    @GetMapping("skipAndLimit")
    public List<StreamUser> getSkipAndLimit(
        @ApiParam(
            value = "page 번호를 설정할 수 있으며 설정 값은 1-N까지 입니다.",
            name = "page",
            defaultValue = "1",
            required = true
        ) @RequestParam Integer page,
        @ApiParam(
            value = "페이지 별 레코드 갯수를 설정 할 수 있습니다.",
            name = "limit",
            defaultValue = "10",
            required = true
        ) @RequestParam Integer limit
    ) {
        int offset = (page - 1) * limit;
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("BB", 20),
            new StreamUser("C", 10),
            new StreamUser("DD", 20),
            new StreamUser("E", 20),
            new StreamUser("A2", 30),
            new StreamUser("BB3", 20),
            new StreamUser("C24", 10),
            new StreamUser("D234D", 20),
            new StreamUser("E234", 20),
            new StreamUser("Asdfa", 30),
            new StreamUser("BadscB", 20),
            new StreamUser("C23fsd", 10)
        );
        return streamUserList.stream().skip(offset).limit(limit).collect(Collectors.toList());
    }

    @ApiOperation("List Map 형태의 데이터에서 stream 을 통한 sum 값 추출")
    @GetMapping("listToSum")
    public Long getListToSum() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("BB", 20),
            new StreamUser("C", 10),
            new StreamUser("DD", 20),
            new StreamUser("E", 20)
        );
        return streamUserList.stream().mapToLong(StreamUser::getAge).sum();
    }

    @ApiOperation("List Map 형태의 데이터에서 stream 을 통한 max 값 추출(min 도 같은 개념으로 활용 -> Ex) stream().min()...)")
    @GetMapping("listToMaxValue")
    public int getListToMaxValue() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("BB", 20),
            new StreamUser("C", 10),
            new StreamUser("DD", 20),
            new StreamUser("E", 20)
        );
        Integer maxAge = 0;
        Optional<StreamUser> maxStreamUserObj = streamUserList.stream().max(Comparator.comparing(StreamUser::getAge));
        if (maxStreamUserObj.isPresent()) {
            maxAge = maxStreamUserObj.get().getAge();
        }

        return maxAge;
    }

    @ApiOperation("Total Count 개수 얻기")
    @GetMapping("listToTotalCount")
    public Long getListToTotalCount() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("BB", 20),
            new StreamUser("C", 10),
            new StreamUser("DD", 20),
            new StreamUser("E", 20)
        );
        return streamUserList.stream().collect(Collectors.counting());
    }

    @ApiOperation("기존 생성된 List<Object> 에 stream 내부에서 새로운 List<Object> 에 대입")
    @GetMapping("listObjectToListObject")
    public List<StreamUser> getListObjectToListObject() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("BB", 20),
            new StreamUser("C", 10),
            new StreamUser("DD", 20),
            new StreamUser("E", 20)
        );

        List<StreamUser> newStreamUserList = new ArrayList<>();
        streamUserList.stream()
            .filter(obj -> obj.getAge() == 20)
            .sorted(Comparator.comparingInt(StreamUser::getAge))
            .map(StreamUser::new)
            .collect(Collectors.toCollection(() -> newStreamUserList));
        return newStreamUserList;
    }

    @ApiOperation("List<Object> 에서 List<String> 으로 구현")
    @GetMapping("listObjectToListString")
    public List<String> getListObjectToListString() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("전봉근BB", 20),
            new StreamUser("전봉근CC", 10),
            new StreamUser("전봉근DD", 40),
            new StreamUser("E", 50)
        );
        List<String> users = streamUserList.stream()
            .map(o -> o.getName())
            .collect(Collectors.toCollection(ArrayList::new));

        return users;
    }

    @ApiOperation("Stream.toArray() 을 사용하여 Stream 을 배열로 변환 (List -> array)")
    @GetMapping("listToArray")
    public String[] getListToArray() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 같은방식으로 변경하여 사용할 수 있다. String[] -> Integer[]
        String[] result = list.stream().map(String::toUpperCase).toArray(String[]::new);
        return result;
    }

    @ApiOperation("List 에서 조건에 맞는 하나의 객체만 추출")
    @GetMapping("listToFindOne")
    public StreamUser getListToFindOne() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("C", 30),
            new StreamUser("전봉근", 20),
            new StreamUser("전봉근1", 10),
            new StreamUser("전봉근2", 40),
            new StreamUser("E", 50)
        );
        StreamUser streamUser = streamUserList.stream()
            .filter(o -> o.getAge() == 10)
            .findAny()
            .orElse(null);
        return streamUser;
    }

    @ApiOperation("List를 HashMap형태로 변경")
    @GetMapping("listToHashMap")
    public Integer getListToHashMap() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("C", 30),
            new StreamUser("전봉근", 20),
            new StreamUser("전봉근1", 10),
            new StreamUser("전봉근2", 40),
            new StreamUser("E", 50)
        );
        Map<String, Integer> streamUserMap = streamUserList
                .stream()
                .collect(Collectors.toMap(StreamUser::getName, StreamUser::getAge));
        return streamUserMap.get("전봉근");
    }

    @ApiOperation("filter 후 Ascending 정렬로 1개의 데이터만 추출")
    @GetMapping("selectOneOrderAsc")
    public StreamUser getSelectOneOrderAsc() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("C", 30),
            new StreamUser("전봉근", 20),
            new StreamUser("전봉근", 10),
            new StreamUser("전봉근", 40),
            new StreamUser("E", 50)
        );
        StreamUser streamUser = streamUserList.stream()
            .filter(streamUserObj -> streamUserObj.getName().equals("전봉근"))
            .sorted(Comparator.comparingInt(StreamUser::getAge))
            .limit(1)
            .findAny()
            .get();

        return streamUser;
    }

    @ApiOperation("숫자, 영어대소문자 오름차순으로 정렬")
    @GetMapping("sort")
    public List<String> sortData() {
        List<String> list = Arrays.asList("9", "A", "Z", "1", "B", "Y", "4", "a", "c");
        List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @ApiOperation("숫자, 영어대소문자 내림차순으로 정렬")
    @GetMapping("reverseSort")
    public List<String> reverseSortData() {
        List<String> list = Arrays.asList("9", "A", "Z", "1", "B", "Y", "4", "a", "c");
        List<String> sortedList = list.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @ApiOperation("나이 오름차순으로 배열 정렬")
    @GetMapping("listObjectSort")
    public List<StreamUser> listObjectSortData() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("C", 30),
            new StreamUser("D", 40),
            new StreamUser("전봉근", 10),
            new StreamUser("홍길동", 20),
            new StreamUser("E", 50)
        );

        List<StreamUser> sortedList = streamUserList.stream()
            .sorted(Comparator.comparingInt(StreamUser::getAge))
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @ApiOperation("나이 내림차순으로 배열 정렬")
    @GetMapping("listObjectReverseSort")
    public List<StreamUser> listObjectReverseSortData() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("전봉근", 30),
            new StreamUser("D", 40),
            new StreamUser("홍길동", 10),
            new StreamUser("B", 20),
            new StreamUser("E", 50)
        );

        List<StreamUser> sortedList = streamUserList.stream()
            .sorted(Comparator.comparingInt(StreamUser::getAge).reversed())
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @ApiOperation("List Map 데이터 구분자 String 으로 변환")
    @GetMapping("isListMapToSplitStr")
    public String isListMapToSplitStr() {
        List<StreamUser> streamUserList = Arrays.asList(
            new StreamUser("전봉근", 30),
            new StreamUser("D", 40),
            new StreamUser("홍길동", 10),
            new StreamUser("B", 20),
            new StreamUser("E", 50)
        );

        // Objects.requireNonNull() 을 활용한 가독성 증가 및 명시적인 코드 작성
        return Objects.requireNonNull(streamUserList, "데이터가 존재하지 않습니다.").stream()
            .map(item -> item.getName())
            .collect(Collectors.joining(","));
    }

}
