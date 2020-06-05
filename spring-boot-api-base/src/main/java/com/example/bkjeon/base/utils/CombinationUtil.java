package com.example.bkjeon.base.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Combination Utils
 * @author Bong Keun - Jeon
 * @version 1.0
 */
@Slf4j
public class CombinationUtil {

    private CombinationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) {
        String[] testArgs = {"001", "002", "003", "004"};

        List<List<String>> powerSet = new LinkedList<>();

        for (int i = 1; i <= testArgs.length; i++) {
            powerSet.addAll(getStrArrCombination(Arrays.asList(testArgs), i));
        }

        log.info(powerSet.toString());
    }

    private static <T> List<List<T>> getStrArrCombination(List<T> values, int size) {
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

        List<List<T>> subSetCombination = getStrArrCombination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(getStrArrCombination(subSet, size));

        return combination;
    }

}
