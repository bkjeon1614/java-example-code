package com.example.bkjeon.base.services.api.v1.data;

import com.example.bkjeon.feature.data.stream.StreamUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/data/stream")
public class StreamController {

    @GetMapping("sort")
    public List<String> sortData() {
        List<String> list = Arrays.asList("9", "A", "Z", "1", "B", "Y", "4", "a", "c");
        List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @GetMapping("reverseSort")
    public List<String> reverseSortData() {
        List<String> list = Arrays.asList("9", "A", "Z", "1", "B", "Y", "4", "a", "c");
        List<String> sortedList = list.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @GetMapping("listObjectSort")
    public List<StreamUser> listObjectSortData() {
        List<StreamUser> streamUsers = Arrays.asList(
            new StreamUser("C", 30),
            new StreamUser("D", 40),
            new StreamUser("A", 10),
            new StreamUser("B", 20),
            new StreamUser("E", 50)
        );

        List<StreamUser> sortedList = streamUsers.stream()
            .sorted(Comparator.comparingInt(StreamUser::getAge))
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

    @GetMapping("listObjectReverseSort")
    public List<StreamUser> listObjectReverseSortData() {
        List<StreamUser> streamUsers = Arrays.asList(
            new StreamUser("C", 30),
            new StreamUser("D", 40),
            new StreamUser("A", 10),
            new StreamUser("B", 20),
            new StreamUser("E", 50)
        );

        List<StreamUser> sortedList = streamUsers.stream()
            .sorted(Comparator.comparingInt(StreamUser::getAge).reversed())
            .collect(Collectors.toList());
        sortedList.forEach(System.out::println);
        return sortedList;
    }

}
