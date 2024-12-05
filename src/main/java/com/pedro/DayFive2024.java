package com.pedro;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * From the problem statement found here: https://adventofcode.com/2024/day/5
 */
public class DayFive2024 {

    public static void main(String[] args) {

        AtomicBoolean isRule = new AtomicBoolean(true);
        HashMap<String, List<String>> rules = new HashMap<>();
        List<String[]> updates = new ArrayList<>();

        try (Stream<String> lines =
                     Files.lines(Path.of(ClassLoader.getSystemResource("input/input_05_2024.txt").toURI()))) {
            lines.forEach(line -> {

                if (line.isEmpty()) {
                    isRule.set(false);
                    return;
                }

                if (isRule.get()) {

                    String[] rule = line.split("\\|");
                    rules.merge(rule[0], List.of(rule[1].trim()), (a, b) -> {
                        Set<String> set = new TreeSet<>(a);
                        set.addAll(b);
                        return new ArrayList<>(set);
                    });
                } else {

                    var update = line.split(",");
                    //var result = extractForPart1(update, rules);

                    var result = extractForPart2(update, rules);
                    if (result.length > 0) {
                        updates.add(result);
                    }
                }
            });

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Rules: " + rules.size());
        System.out.println("Updates: " + updates.size());

        int sum = 0;

        for (String[] update : updates) {
            var middleIndex = update.length / 2;
            sum += Integer.parseInt(update[middleIndex]);
        }

        System.out.println("Sum: " + sum);

    }

    public static String[] extractForPart1(String[] update, HashMap<String, List<String>> rules) {
        var isValid = true;

        for (int i = update.length - 1; i > 0; i--) {
            var rulesList = rules.get(update[i]);
            if (rulesList == null) {
                System.out.println("No rule found for " + Arrays.toString(update));
                continue;
            }

            for (int j = i; j >= 0; j--) {
                if (rulesList.contains(update[j])) {
                    isValid = false;
                    break;
                }
            }
        }

        if (isValid) {
            return update;
        }
        return new String[0];

    }

    private static String[] extractForPart2(String[] update, HashMap<String, List<String>> rules) {
        var isValid = true;
        var isIncorrect = false;

        while (isValid) {
            isValid = false;
            for (int i = update.length - 1; i > 0; i--) {

                var rulesList = rules.get(update[i]);
                if (rulesList == null) {
                    System.out.println("No rule found for " + update);
                    continue;
                }

                for (int j = i; j >= 0; j--) {
                    if (rulesList.contains(update[j])) {
                        var holder = update[i];
                        update[i] = update[j];
                        update[j] = holder;

                        isIncorrect = true;
                        isValid = true;
                    }
                }
            }
        }

        if (isIncorrect) {
            return update;
        }
        return new String[0];
    }
}
