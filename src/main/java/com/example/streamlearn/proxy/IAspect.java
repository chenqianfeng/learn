package com.example.streamlearn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @program streamLearn
 * @ClassName Aspect
 * @description
 * @create 2023-01-31 18:23
 **/
public interface IAspect {

    static <T> T getProxy(Class<T> clazzs, String... aspects) throws Exception {
        var aspectInsts = Arrays.stream(aspects).map(name -> {
            Class<?> clazz;
            try {
                clazz = Class.forName(name);
                return (IAspect)clazz.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        var inst = clazzs.getConstructor().newInstance();
        return (T) Proxy.newProxyInstance(
                clazzs.getClassLoader(),
                clazzs.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        for (var aspect : aspectInsts) {
                            aspect.before();
                        }
                        Object invoke = method.invoke(inst);
                        for (var aspect : aspectInsts) {
                            aspect.after();
                        }
                        return invoke;
                    }
                }
        );
    }

    void before();
    void after();
}
