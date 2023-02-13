package ru.otus.service;

import com.google.common.math.IntMath;
import ru.otus.annotations.Log;

public class TestLoggingImpl implements TestLogging {

    public TestLoggingImpl() {

    }

    @Log
    @Override
    public void calculation(int param) {
        System.out.println(IntMath.factorial(param));
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println(IntMath.factorial(param1 + param2));
    }

}
