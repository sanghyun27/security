package com.study.security_sanghyun.handler.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)			// 언제 적용시킬 것인지(실행중에)
@Target({ TYPE, METHOD })	// 어디에 적용시킬 것(매개변수타입 앞, 메서드 위)
public @interface Timer {

}
