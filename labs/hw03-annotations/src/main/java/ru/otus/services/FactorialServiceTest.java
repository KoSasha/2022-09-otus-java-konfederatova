package ru.otus.services;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class FactorialServiceTest {

    private final static int VALUE = 2;
    private FactorialService appService;

    @Before
    public void setUp() {
        System.out.println("Before test");
        appService = new FactorialService(VALUE);
        throw new RuntimeException();
    }

    @Test
    void factorialSuccessfulTest() {
        System.out.println("Test");
        int expected = 2;
        int actual = appService.factorial();
        if (actual == expected) {
            System.out.println("equals");
        } else {
            throw new RuntimeException();
        }
    }

    @Test
    void factorialUnsuccessfulTest() {
        System.out.println("negative Test");
        int expected = 3;
        int actual = appService.factorial();
        if (actual == expected) {
            System.out.println("equals");
        } else {
            throw new RuntimeException();
        }
    }

    @After
    public void end() {
        System.out.println("After test");
    }

}
