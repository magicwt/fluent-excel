package com.magicwt.fluentexcel.converter.impl;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * 类属性类型为{@link Long}的转换器
 *
 * @author magicwt
 */
public class LongConverter implements Converter<Long> {

  @Override
  public Long from(String str, ExcelColumn excelColumn) {
    if (StringUtils.isBlank(str)) {
      return null;
    }
    return Long.valueOf(str);
  }

  @Override
  public String to(Long object, ExcelColumn excelColumn) {
    return object.toString();
  }

}
