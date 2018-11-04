package com.magicwt.fluentexcel.converter;

import com.magicwt.fluentexcel.annotation.ExcelColumn;

/**
 * 单元格和类属性转换器接口
 *
 * @author magicwt
 */
public interface Converter<T> {

  /**
   * 单元格值转换为类属性
   */
  T from(String str, ExcelColumn excelColumn);

  /**
   * 类属性转换为单元格
   */
  String to(T object, ExcelColumn excelColumn);

}
