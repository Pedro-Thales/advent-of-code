package com.pedro;


import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

/**
 * From the problem statement found here: https://adventofcode.com/2024/day/6
 */

public class DaySix2024 {

    public static void main(String[] args) throws URISyntaxException, IOException {


        var path = ClassLoader.getSystemResource("input/input_06_2024.txt").toURI();

        var mapped = new HashMap<Integer, String>();
        var obstacles = new HashMap<Integer, List<Integer>>();

        var maxLine = 129;
        var maxPosition = 129;

        var guardLine = 0;
        var guardPosition = 0;

        var direction = "UP";

        var positionsPassed = new HashSet<String>();

        FileInputStream fis = new FileInputStream(new File(path));
        try(BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            var lineNumber = 0;
            while ((line = br.readLine()) != null) {

                mapped.put(lineNumber, line);
                var positions = line.toCharArray();
                if (line.contains("^")) {
                    guardLine = lineNumber;
                    guardPosition = line.indexOf("^");
                }
                if (line.contains("#")) {
                    for (int i = 1; i < positions.length - 1; i++) {
                        if (positions[i] == '#') {
                            obstacles.merge(lineNumber, List.of(i), (a, b) -> {
                                Set<Integer> set = new TreeSet<>(a);
                                set.addAll(b);
                                return new ArrayList<>(set);
                            });
                        }
                    }
                }
                lineNumber++;
            }
        }

        while ((guardLine < maxLine && guardLine >= 0) && (guardPosition < maxPosition && guardPosition >= 0)) {
            System.out.println("Guard position: " + guardLine + "," + guardPosition);

            switch (direction) {
                case "UP":
                    if(guardLine - 1 < 0){
                        guardLine--;
                        break;
                    }

                    if (mapped.get(guardLine - 1).charAt(guardPosition) != '#'){
                        positionsPassed.add(guardLine + "," + guardPosition);
                        guardLine--;
                    } else {
                        direction = "RIGHT";
                    }
                    break;
                case "RIGHT":
                    if (mapped.get(guardLine).charAt(guardPosition + 1) != '#'){
                        positionsPassed.add(guardLine + "," + guardPosition);
                        guardPosition++;
                    } else {
                        direction = "DOWN";
                    }
                    break;
                case "DOWN":
                    if (mapped.get(guardLine + 1).charAt(guardPosition) != '#'){
                        positionsPassed.add(guardLine + "," + guardPosition);
                        guardLine++;
                    }
                    else {
                        direction = "LEFT";
                    }
                    break;
                case "LEFT":
                    if((guardPosition - 1) < 0){
                        guardPosition--;
                        break;
                    }
                    if (mapped.get(guardLine).charAt(guardPosition - 1) != '#'){
                        positionsPassed.add(guardLine + "," + guardPosition);
                        guardPosition--;
                    } else {
                        direction = "UP";
                    }
                    break;
            }

        }

        System.out.println("Positions passed by the guard: " + positionsPassed.size());

    }
}
