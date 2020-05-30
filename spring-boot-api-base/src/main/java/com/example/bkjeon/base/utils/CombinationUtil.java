package com.example.bkjeon.base.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CombinationUtil {

    private CombinationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) {
        String[] testArgs = {"001", "002", "003", "004"};

        List<List<String>> powerSet = new LinkedList<>();

        for (int i = 1; i <= testArgs.length; i++) {
            powerSet.addAll(combination(Arrays.asList(testArgs), i));
        }

        System.out.println(powerSet);
    }

    public static <T> List<List<T>> combination(List<T> values, int size) {
        if (0 == size) {
            return Collections.singletonList(Collections.emptyList());
        }

        if (values.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<T>> combination = new LinkedList<>();

        T actual = values.iterator().next();

        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = combination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(combination(subSet, size));

        return combination;
    }

}
