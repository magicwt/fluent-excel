package com.magicwt.fluentexcel.annotation;

import com.magicwt.fluentexcel.converter.Converter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 与excel column相互转化的类属性注解
 *
 * @author magicwt
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelColumn {

  // 属性名称，必须在表格第一行
  String value() default "";

  // 属性是否必须
  boolean required() default true;

  // 属性值的格式，目前只用在日期格式，例如可填"yyyy-MM-dd hh:mm:ss"
  String format() default "";

  // 属性值自定义转化器
  Class<? extends Converter> converter() default Converter.class;

}
