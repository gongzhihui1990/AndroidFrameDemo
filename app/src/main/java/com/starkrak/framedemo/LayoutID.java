package com.starkrak.framedemo;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.LayoutRes;

/**
 * 用于设置activity的fragment的layout
 * @author 龚志辉
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutID {
    @LayoutRes int value();
}
