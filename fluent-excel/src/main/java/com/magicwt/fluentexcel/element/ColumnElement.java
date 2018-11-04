package com.magicwt.fluentexcel.element;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.converter.Converter;
import java.lang.reflect.Field;
import org.apache.commons.lang3.StringUtils;

/**
 * column元素，即与excel column相互转换的类属性信息
 *
 * @author magicwt
 */
public class ColumnElement {

  // 类属性
  private Field field;

  // 类属性上的ExcelColumn注解
  private ExcelColumn excelColumn;

  // 单元格和类属性转换器
  private Converter converter;

  /**
   * 对单元格值进行转换并赋值到对象属性中
   */
  public void convertAndSet(String str, Object object) {
    try {
      Object value = converter.from(str, excelColumn);
      if (value != null || !field.getType().isPrimitive()) {
        // 如果转换后值不为空或值为空但属性类型不是基础数据类型，则赋值到对象属性中
        field.set(object, value);
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 对对象属性值进行转换并返回值
   */
  public String getAndConvert(Object object) {
    try {
      // 获取对象属性值
      Object value = field.get(object);
      if (value == null) {
        // 如果对象属性值为空，则返回空
        return StringUtils.EMPTY;
      }
      // 转换对象属性值并返回值
      return converter.to(value, excelColumn);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public ExcelColumn getExcelColumn() {
    return excelColumn;
  }

  public void setExcelColumn(ExcelColumn excelColumn) {
    this.excelColumn = excelColumn;
  }

  public Converter getConverter() {
    return converter;
  }

  public void setConverter(Converter converter) {
    this.converter = converter;
  }

}
