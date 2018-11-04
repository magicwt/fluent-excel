package com.magicwt.fluentexcel.converter.impl;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;

/**
 * 类属性类型为{@link String}的转换器
 *
 * @author magicwt
 */
public class StringConverter implements Converter<String> {

  @Override
  public String from(String str, ExcelColumn excelColumn) {
    return str;
  }

  @Override
  public String to(String object, ExcelColumn excelColumn) {
    return object;
  }

}
