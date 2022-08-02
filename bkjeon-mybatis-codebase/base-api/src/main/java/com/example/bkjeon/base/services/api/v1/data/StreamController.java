package com.example.bkjeon.base.services.api.v1.data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.entity.data.stream.StreamUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/data/stream")
public class StreamController {

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
        List<StreamUser> streamUserArr = Arrays.asList(
            new StreamUser("C", 30),
            new StreamUser("D", 40),
            new StreamUser("전봉근", 10),
            new StreamUser("홍길동", 20),
            new StreamUser("E", 50)
        );

        List<StreamUser> sortedList = streamUserArr.stream()
            .sorted(Comparator.comparingInt(StreamUser::getAge))
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @ApiOperation("나이 내림차순으로 배열 정렬")
    @GetMapping("listObjectReverseSort")
    public List<StreamUser> listObjectReverseSortData() {
        List<StreamUser> streamUserArr = Arrays.asList(
            new StreamUser("전봉근", 30),
            new StreamUser("D", 40),
            new StreamUser("홍길동", 10),
            new StreamUser("B", 20),
            new StreamUser("E", 50)
        );

        List<StreamUser> sortedList = streamUserArr.stream()
            .sorted(Comparator.comparingInt(StreamUser::getAge).reversed())
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

}
