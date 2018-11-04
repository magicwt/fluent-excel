package com.magicwt.fluentexcel.converter.impl;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * 类属性类型为{@link BigDecimal}的转换器
 *
 * @author magicwt
 */
public class BigDecimalConverter implements Converter<BigDecimal> {

  @Override
  public BigDecimal from(String str, ExcelColumn excelColumn) {
    if (StringUtils.isBlank(str)) {
      return null;
    }
    return new BigDecimal(str);
  }

  @Override
  public String to(BigDecimal object, ExcelColumn excelColumn) {
    return object.toString();
  }

}
