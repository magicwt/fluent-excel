package com.magicwt.fluentexcel.element;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;
import com.magicwt.fluentexcel.converter.ConverterFactory;
import java.lang.reflect.Field;

/**
 * {@link ColumnElement}构建器
 *
 * @author magicwt
 */
public class ColumnElementBuilder {

  /**
   * 根据{@link Field}和{@link ExcelColumn}构建{@link ColumnElement}
   */
  public static ColumnElement build(Field field, ExcelColumn excelColumn) {
    ColumnElement columnElement = new ColumnElement();
    // 设置ExcelColumn
    columnElement.setExcelColumn(excelColumn);
    // 设置Field
    columnElement.setField(field);
    Converter converter;
    if (excelColumn.converter() == Converter.class) {
      // 如果未设置自定义转换器，则根据属性类型获取转换器
      converter = ConverterFactory.getByFieldType(field.getType());
    } else {
      // 如果设置自定义转换器，则采用自定义转换器
      converter = ConverterFactory.getByClass(excelColumn.converter());
    }
    if (converter == null) {
      throw new RuntimeException(field.getName() + "没有符合的converter");
    }
    columnElement.setConverter(converter);
    return columnElement;
  }

}
