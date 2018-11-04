package com.magicwt.fluentexcel.converter.impl;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * 类属性类型为{@link Integer}的转换器
 *
 * @author magicwt
 */
public class IntegerConverter implements Converter<Integer> {

  @Override
  public Integer from(String str, ExcelColumn excelColumn) {
    if (StringUtils.isBlank(str)) {
      return null;
    }
    return Integer.valueOf(str);
  }

  @Override
  public String to(Integer object, ExcelColumn excelColumn) {
    return object.toString();
  }

}
