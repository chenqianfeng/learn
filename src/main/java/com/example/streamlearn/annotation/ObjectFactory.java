package com.example.streamlearn.annotation;

import com.example.streamlearn.proxy.IAspect;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * @program streamLearn
 * @ClassName ObjectFactory
 * @description
 * @create 2023-02-01 13:17
 **/
public class ObjectFactory {

    public static <T> T newInstance(Class<T> clazz) throws Exception {
        var annotations = clazz.getAnnotations();
        var aspects = new LinkedList<IAspect>();
        for (var annotation : annotations){
            if (annotation instanceof Aspect){
                var type = ((Aspect) annotation).type();
                var aspect = (IAspect)type.getConstructor().newInstance();
                aspects.push(aspect);
            }
        }
        T t = clazz.getConstructor().newInstance();
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        aspects.forEach(aspect->aspect.before());
                        Object invoke = method.invoke(t);
                        aspects.forEach(aspect->aspect.after());
                        return invoke;
                    }
                }
        );
    }

}
