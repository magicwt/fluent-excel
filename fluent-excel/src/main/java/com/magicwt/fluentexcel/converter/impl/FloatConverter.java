package com.magicwt.fluentexcel.converter.impl;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * 类属性类型为{@link Float}的转换器
 *
 * @author magicwt
 */
public class FloatConverter implements Converter<Float> {

  @Override
  public Float from(String str, ExcelColumn excelColumn) {
    if (StringUtils.isBlank(str)) {
      return null;
    }
    return Float.valueOf(str);
  }

  @Override
  public String to(Float object, ExcelColumn excelColumn) {
    return object.toString();
  }

}
