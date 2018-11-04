package com.magicwt.fluentexcel.converter.impl;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * 类属性类型为{@link Date}的转换器
 *
 * @author magicwt
 */
public class DateConverter implements Converter<Date> {

  @Override
  public Date from(String str, ExcelColumn excelColumn) {
    if (StringUtils.isBlank(str)) {
      return null;
    }
    return DateTime.parse(str, DateTimeFormat.forPattern(excelColumn.format())).toDate();
  }

  @Override
  public String to(Date object, ExcelColumn excelColumn) {
    return new DateTime(object).toString(excelColumn.format());
  }

}
