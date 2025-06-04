package com.app.vpk.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.FIELD }) // Apply to parameters and fields
public @interface MinLength {
	int value() default 2; // Minimum length, default is 2

	String message() default "String must be at least 2 characters long";
}
