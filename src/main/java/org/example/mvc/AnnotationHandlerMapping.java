package org.example.mvc;


import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.controller.RequestMethod;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 어노테이션 핸들러 매핑
 */
public class AnnotationHandlerMapping implements HandlerMapping{
    private final Object[] basePackage;
    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    /**
     *  Reflection 을 이용하여 맵 초기화하기
     */
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);

        // HomeController 가 넘어로 것
        Set<Class<?>> clazzesWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class);

        // method 에 붙어있는 value 값과, method 값(GET) 을 추출하는 작업
        clazzesWithControllerAnnotation.forEach(clazz ->
                        Arrays.stream(clazz.getDeclaredMethods()).forEach(declaredMethod -> {
                            RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(RequestMapping.class);

                            Arrays.stream(getRequestMethods(requestMapping))
                                    .forEach(requestMethod -> handlers.put(
                                            new HandlerKey(requestMethod, requestMapping.value()), new AnnotationHandler(clazz, declaredMethod)
                                    ));
                        })
                );

    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
