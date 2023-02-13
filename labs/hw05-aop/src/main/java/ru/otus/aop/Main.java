package ru.otus.aop;

import ru.otus.service.Ioc;
import ru.otus.service.TestLogging;
import ru.otus.service.TestLoggingImpl;

public class Main {

    public static void main(String[] args) {
//        TestLogging testLogging = Ioc.createTestLogging();
//        testLogging.calculation(1);
//        testLogging.calculation(2, 3);
        new TestLoggingImpl().calculation(1);
    }

}