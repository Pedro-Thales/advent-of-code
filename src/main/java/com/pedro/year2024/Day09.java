package com.pedro.year2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;


public class Day09 {
    public static void main(String[] args) {

        var start = System.currentTimeMillis();
        char[] line;

        try (Stream<String> lines =
                     Files.lines(Path.of(ClassLoader.getSystemResource("input/input_09_2024.txt").toURI()))) {
            String lineStr = lines.findFirst().get();
            line = lineStr.toCharArray();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        List<String> blockLine = new ArrayList<>();
        int value = 0;
        for (int i = 0; i < line.length; i++) {
            if (i % 2 == 0) {
                blockLine.addAll(addItemTimes(value, line[i]));
                value++;
            } else {
                blockLine.addAll(addBlankTimes(line[i]));
            }
        }

        Long sum = 0L;
        int lastEmptyPosition = blockLine.size() - 1;

        for (int i = 0; i < blockLine.size(); i++) {
            if (blockLine.get(i).equals(".")) {
                for (int j = lastEmptyPosition; j > i; j--) {
                    if (!blockLine.get(j).equals(".")) {
                        Collections.swap(blockLine, j, i);
                        break;
                    }
                }
            }
            if (!blockLine.get(i).equals(".")) {
                sum += Long.parseLong(blockLine.get(i)) * i;
            }
        }

        System.out.println("Sum: " + sum);

        var end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");

    }

    private static List<String> addItemTimes(int item, char timesValue) {
        List<String> list = new ArrayList<>();
        int count = 0;
        int times = Character.getNumericValue(timesValue);
        while (count < times) {
            list.add(String.valueOf(item));
            count++;
        }

        return list;

    }

    private static List<String> addBlankTimes(char timesValue) {
        List<String> list = new ArrayList<>();
        int count = 0;
        int times = Character.getNumericValue(timesValue);
        while (count < times) {
            list.add(".");
            count++;
        }

        return list;

    }

}
