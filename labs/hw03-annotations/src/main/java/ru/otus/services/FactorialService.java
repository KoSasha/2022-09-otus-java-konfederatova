package ru.otus.services;

import com.google.common.math.IntMath;

// тестируемый класс
public class FactorialService {

    private final int value;

    public FactorialService(int value) {
        this.value = value;
    }

    public int factorial() {
        return IntMath.factorial(value);
    }

}
