package ru.otus.services;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Scanner;

// класс-запускалка тестов
public class TestService {

    private static final String REPORT_FILE = "report.txt";
    private static final String REPORT_FORMAT = "%s:\nSuccessful tests: %d\nUnsuccessful Tests: %d\nAll tests: %d";

    public static void startTests(Class clazz) throws IOException {
        Object testClass = ReflectionHelper.instantiate(clazz);
        Method[] methods = clazz.getDeclaredMethods();
        executeMethods(methods, testClass, Before.class);
        executeMethods(methods, testClass, Test.class);
        executeMethods(methods, testClass, After.class);
        printStatisticsReport(clazz);
    }

    private static void executeMethods(Method[] methods, Object testClass, Class clazz) throws IOException {
        int successfulTests = 0;
        int unSuccessfulTests = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(clazz)) {
                try {
                    ReflectionHelper.callMethod(testClass, method.getName());
                    successfulTests++;
                } catch (Exception e) {
                    unSuccessfulTests++;
                    System.out.println(e);
                }
            }
        }
        if (clazz.equals(Test.class)) {
            writeReportStatistic(testClass.getClass(), successfulTests, unSuccessfulTests);
        }
    }

    private static void writeReportStatistic(Class clazz, int successfulTests, int unSuccessfulTests) throws IOException {
        String reportPath = Objects.requireNonNull(clazz.getClassLoader().getResource(REPORT_FILE)).getPath();
        FileWriter fw = new FileWriter(reportPath);
        fw.write(String.format(REPORT_FORMAT, clazz.getName(), successfulTests, unSuccessfulTests, successfulTests + unSuccessfulTests));
        fw.flush();
        fw.close();
    }

    private static void printStatisticsReport(Class clazz) throws FileNotFoundException {
        String reportPath = Objects.requireNonNull(clazz.getClassLoader().getResource(REPORT_FILE)).getPath();
        Scanner sc = new Scanner(new FileReader(reportPath));
        System.out.println();
        while (sc.hasNext()) {
            String source = sc.nextLine();
            System.out.println(source);
        }
        sc.close();
    }

}
