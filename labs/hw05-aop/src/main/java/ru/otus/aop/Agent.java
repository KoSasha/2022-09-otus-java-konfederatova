package ru.otus.aop;

import java.lang.instrument.Instrumentation;

//java -javaagent:"main.jar" -jar "main.jar"

public class Agent {

    public static void premain(String args, Instrumentation instrumentation) {
//        System.out.println("Classes loaded: " + instrumentation.getAllLoadedClasses().length);
//        instrumentation.addTransformer(new ClassTransformer());
        System.out.println("premain");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                if (className.equals("ru/otus/aop/instrumentation/proxy/MyClassImpl")) {
                    return addProxyMethod(classfileBuffer);
                }
                return classfileBuffer;
            }
        });
    }



}
