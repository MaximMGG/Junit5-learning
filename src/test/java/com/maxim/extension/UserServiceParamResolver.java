package com.maxim.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.maxim.service.UserService;

public class UserServiceParamResolver implements ParameterResolver{

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType() == UserService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Store store = extensionContext.getStore(ExtensionContext.Namespace.create(UserService.class));
        return store.getOrComputeIfAbsent(UserService.class, it -> new UserService());
    }

    
}
