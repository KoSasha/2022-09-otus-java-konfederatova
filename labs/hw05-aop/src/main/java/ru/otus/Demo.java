package ru.otus;

import ru.otus.service.Ioc;
import ru.otus.service.TestLogging;

public class Demo {

    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLogging();
        testLogging.calculation(1);
        testLogging.calculation(2, 3);
    }

}