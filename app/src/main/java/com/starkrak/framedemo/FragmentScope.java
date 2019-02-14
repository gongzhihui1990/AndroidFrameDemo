package com.starkrak.framedemo;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Scope 局部单例注解
 */
@Scope  //声明这是一个自定义@Scope注解
@Retention(RUNTIME)  //Fragment局部单例
public @interface FragmentScope {
}
