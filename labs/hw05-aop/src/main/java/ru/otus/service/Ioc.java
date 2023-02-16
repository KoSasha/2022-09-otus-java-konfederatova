package ru.otus.service;

import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    public static TestLogging createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;

        DemoInvocationHandler(TestLoggingImpl testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method methodImpl = TestLoggingImpl.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (methodImpl.isAnnotationPresent(Log.class)) {
                System.out.println("executed method: " + method.getName() + ", param: " + Arrays.toString(args));
            }
            return method.invoke(testLogging, args);
        }

    }

}
