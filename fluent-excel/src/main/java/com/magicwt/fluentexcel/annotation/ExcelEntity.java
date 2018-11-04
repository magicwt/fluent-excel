package com.magicwt.fluentexcel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 与excel sheet相互转化的类注解
 *
 * @author magicwt
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelEntity {

  // 转化为excel sheet名称，若不设置，则sheet名称采用类名
  String value() default "";

}
