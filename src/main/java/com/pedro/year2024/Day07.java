package com.pedro.year2024;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


public class Day07 {

    public static void main(String[] args) {

        var start = System.currentTimeMillis();

        AtomicReference<Long> actualSum = new AtomicReference<>(0L);

        try (Stream<String> lines =
                     Files.lines(Path.of(ClassLoader.getSystemResource("input/input_07_2024.txt").toURI()))) {
            lines.forEach(line -> {
                var tree = new Tree();

                var split = line.split(":");
                var key = Long.parseLong(split[0].trim());
                String[] values = split[1].trim().split(" ");

                for (int i = 0; i < values.length; i++) {
                    if (!values[i].isBlank()) {
                        var parsedValue = Long.parseLong(values[i]);
                        if (i == 0) {
                            tree.root = new Node(parsedValue);
                        } else {
                            tree.addNewValueToLevel(parsedValue, i - 1);
                        }
                    }
                }

                var contains = tree.containsNode(key);
                if (contains) {
                    actualSum.updateAndGet(v -> v + key);
                }

            });

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Sum: " + actualSum);
        var end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");

    }

    static class Node {
        Long value;
        Node left;
        Node right;

        public Node(Long value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

    }

    static class Tree {
        Day07.Node root;

        public void addNewValueToLevel(Long value, int level) {
            //SUM and multiply the current node with the value

            for (Node node : nodesAtDepth(level)) {
                node.left = new Day07.Node(node.value + value);
                node.right = new Day07.Node(node.value * value);
            }

        }

        public boolean containsNode(Long value) {
            return containsNodeRecursiveAndIsLastLevel(root, value);
        }

        private boolean containsNodeRecursiveAndIsLastLevel(Node current, Long value) {
            if (current == null) {
                return false;
            }
            if (Objects.equals(value, current.value) && current.right == null && current.left == null) {
                return true;
            }
            return containsNodeRecursiveAndIsLastLevel(current.left, value) || containsNodeRecursiveAndIsLastLevel(current.right, value);
        }

        public LinkedList<Node> nodesAtDepth(int depth) {
            LinkedList<Node> list = new LinkedList<>();
            return getGivenLevel(depth, root, list);
        }

        private LinkedList<Node> getGivenLevel(int depth, Node root, LinkedList<Node> list) {

            if (root == null) {
                return list;
            }

            if (depth == 0) {
                list.add(root);
            } else if (depth > 0) {
                getGivenLevel(depth - 1, root.left, list);
                getGivenLevel(depth - 1, root.right, list);
            }

            return list;
        }

    }

}
