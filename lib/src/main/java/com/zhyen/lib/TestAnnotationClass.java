package com.zhyen.lib;


import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.zhyen.com.TestAnnotation")
public class TestAnnotationClass extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<String, String> map = new HashMap<>();
        for (TypeElement element : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(element)) {

            }
        }
        return false;
    }
}
