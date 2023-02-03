package ru.otus.services;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

// класс-запускалка тестов
public class TestService {

    private static final String REPORT_FILE = "report.txt";
    private static final String REPORT_FORMAT = "%s:\nSuccessful tests: %d\nUnsuccessful Tests: %d\nAll tests: %d";
    private static final String SUCCESSFUL_TESTS = "successfulTests";
    private static final String UNSUCCESSFUL_TESTS = "unSuccessfulTests";
    private int successfulTests;
    private int unSuccessfulTests;

    public static void startTests(Class clazz) throws IOException {
        Method[] methods = clazz.getDeclaredMethods();
        Map<String, List<Method>> map = Arrays.stream(methods).collect(Collectors.groupingBy(TestService::getAnnotation));
        TestService testService = new TestService();

        for (Method method: map.get(Test.class.getName())) {
            Object testClass = ReflectionHelper.instantiate(clazz);
            map.get(Before.class.getName()).forEach(m -> executeMethod(m, testClass, Before.class, testService));
            executeMethod(method, testClass, Test.class, testService);
            map.get(After.class.getName()).forEach(m -> executeMethod(m, testClass, After.class, testService));
        }
        writeReportStatistic(clazz, testService);
        printStatisticsReport(clazz);
    }

    private static String getAnnotation(Method method) {
        List<Class> testAnnotations = List.of(Test.class, Before.class, After.class);
        String annotation = null;
        for (Class ta : testAnnotations) {
            Annotation a = method.getAnnotation(ta);
            if (a != null) {
                annotation = a.annotationType().getName();
            }
        }
        return annotation;
    }

    private static void executeMethod(Method method, Object testClass, Class annotation, TestService testService) {
        try {
            ReflectionHelper.callMethod(testClass, method.getName());
            if (annotation.equals(Test.class)) {
                ReflectionHelper.setFieldValue(testService, SUCCESSFUL_TESTS, getStatisticCountTests(testService, SUCCESSFUL_TESTS) + 1);
            }
        } catch (Exception e) {
            if (annotation.equals(Test.class)) {
                ReflectionHelper.setFieldValue(testService, UNSUCCESSFUL_TESTS, getStatisticCountTests(testService, UNSUCCESSFUL_TESTS) + 1);
                System.out.println(e);
            }
        }
    }

    private static int getStatisticCountTests(Object testService, String name) {
        return (int) ReflectionHelper.getFieldValue(testService, name);
    }

    private static void writeReportStatistic(Class clazz, TestService testService) throws IOException {
        String reportPath = Objects.requireNonNull(clazz.getClassLoader().getResource(REPORT_FILE)).getPath();
        int successfulTests = getStatisticCountTests(testService, SUCCESSFUL_TESTS);
        int unSuccessfulTests = getStatisticCountTests(testService, UNSUCCESSFUL_TESTS);
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
