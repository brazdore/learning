package com.github.brazdore.hr.problemsolving.algorithms.basic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class GradingStudents {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int gradesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> grades = IntStream.range(0, gradesCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        List<Integer> result = gradingStudents(grades);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static List<Integer> gradingStudents(List<Integer> grades) {
        final List<Integer> roundedGrades = new ArrayList<>();

        grades.forEach(g -> {
            if (g < 38) {
                roundedGrades.add(g);
            } else if ((g + 1) % 5 == 0) {
                roundedGrades.add(g + 1);
            } else if ((g + 2) % 5 == 0) {
                roundedGrades.add(g + 2);
            } else roundedGrades.add(g);
        });
        return roundedGrades;
    }
}