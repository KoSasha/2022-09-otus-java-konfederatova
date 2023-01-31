package ru.otus;

import ru.otus.services.FactorialServiceTest;
import ru.otus.services.TestService;

public class Main {

    public static void main(String[] args) throws Exception {
        TestService.startTests(FactorialServiceTest.class);
    }
}