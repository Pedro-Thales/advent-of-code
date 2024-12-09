package com.pedro.year2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class Day08 {
    public static void main(String[] args) {

        var start = System.currentTimeMillis();
        Set<Node> antinodes = new HashSet<>();
        char[][] matrix = new char[50][50];
        AtomicInteger lineNumber = new AtomicInteger();
        HashMap<Character, ArrayList<Node>> positions = new HashMap<>();

        try (Stream<String> lines =
                     Files.lines(Path.of(ClassLoader.getSystemResource("input/input_08_2024.txt").toURI()))) {
            lines.forEach(line -> {
                var chars = line.toCharArray();

                matrix[lineNumber.get()] = chars;

                for (int i = 0; i < chars.length; i++) {
                    var node = new Node(lineNumber.get(), i);
                    var poslist = new ArrayList<Node>();
                    poslist.add(node);
                    if (chars[i] != '.') {
                        positions.merge(chars[i], poslist, (a, b) -> {
                            a.add(node);
                            return a;
                        });
                    }
                }
                lineNumber.getAndIncrement();


            });


            for (Character c : positions.keySet()) {
                var poslist = positions.get(c);
                //get every pair of nodes with the same character and find the antinode
                for (int i = 0; i < poslist.size(); i++) {
                    for (int j = 0; j < poslist.size(); j++) {
                        if (i == j) {
                            continue;
                        }
                        var nodeA = poslist.get(i);
                        var nodeB = poslist.get(j);
                        var antinode = findAntinode(nodeA, nodeB);
                        if (antinode != null) {
                            antinodes.add(antinode);
                        }
                    }
                }
            }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Antinodes Size: " + antinodes.size());
        var end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");

    }

    public static Node findAntinode(Node nodeA, Node nodeB) {
        var x = 2 * nodeA.x - nodeB.x;
        var y = 2 * nodeA.y - nodeB.y;
        if (x < 0 || x > 49 || y < 0 || y > 49) {
            return null;
        }
        return new Node(x, y);
    }

    static class Node {

        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{x:" + x + ", y:" + y + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
