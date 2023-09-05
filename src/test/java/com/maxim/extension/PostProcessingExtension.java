package com.maxim.extension;

import java.lang.reflect.Field;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import com.maxim.service.UserService;

import lombok.Getter;

public class PostProcessingExtension implements TestInstancePostProcessor{

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        System.out.println("post processing extension");
        Field[] declaredFields = testInstance.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Getter.class)) {
                field.set(testInstance, new UserService(null));
            }
        }
    }
    
}
