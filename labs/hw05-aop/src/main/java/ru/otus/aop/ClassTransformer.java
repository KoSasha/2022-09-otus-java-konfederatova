package ru.otus.aop;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import ru.otus.annotations.Log;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class ClassTransformer implements ClassFileTransformer {

    private ClassPool pool= ClassPool.getDefault();

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        try {
            pool.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));
            CtClass cclass = pool.get(className.replaceAll("/", "."));
            if (!className.startsWith("ru/otus/")) {
                return null;
            }
            for (CtMethod currentMethod : cclass.getDeclaredMethods()) {
                Log annotation = (Log) currentMethod.getAnnotation(Log.class);
                if (annotation != null) {
                    currentMethod.insertBefore("{System.out.println(\"executed method: \"" + currentMethod.getName() + "\", param: \"" + Arrays.toString(currentMethod.getParameterTypes()));
                }
            }
            return cclass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
