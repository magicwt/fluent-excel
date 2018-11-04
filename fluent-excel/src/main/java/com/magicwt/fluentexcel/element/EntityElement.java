package com.magicwt.fluentexcel.element;

import com.google.common.collect.Lists;
import com.magicwt.fluentexcel.annotation.ExcelEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * entity元素
 *
 * @author magicwt
 */
public class EntityElement {

  // 类
  private Class<?> entityClass;

  // 类上的ExcelEntity注解
  private ExcelEntity excelEntity;

  // ColumnElement value和ColumnElement的映射
  private Map<String, ColumnElement> columnElementMap;

  // 表头
  private List<String> header;

  // 必填属性集合
  private Set<String> requiredColumn;

  /**
   * 将行记录转换为对象
   *
   * @param row 行记录
   * @param columnElementMap 列序号和{@link ColumnElement}的映射
   */
  public Object from(List<String> row, Map<Integer, ColumnElement> columnElementMap) {
    try {
      Object object = entityClass.newInstance();
      for (Map.Entry<Integer, ColumnElement> entry : columnElementMap.entrySet()) {
        int index = entry.getKey();
        ColumnElement columnElement = entry.getValue();
        if (index < row.size()) {
          columnElement.convertAndSet(row.get(index), object);
        }
      }
      return object;
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将对象转换为行记录
   */
  public List<String> to(Object object) {
    List<String> row = Lists.newArrayList();
    for (ColumnElement columnElement : columnElementMap.values()) {
      row.add(columnElement.getAndConvert(object));
    }
    return row;
  }

  public String getName() {
    if (excelEntity != null && StringUtils.isNotBlank(excelEntity.value())) {
      return excelEntity.value();
    } else {
      return entityClass.getName();
    }
  }

  public Class<?> getEntityClass() {
    return entityClass;
  }

  public void setEntityClass(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  public ExcelEntity getExcelEntity() {
    return excelEntity;
  }

  public void setExcelEntity(ExcelEntity excelEntity) {
    this.excelEntity = excelEntity;
  }

  public Map<String, ColumnElement> getColumnElementMap() {
    return columnElementMap;
  }

  public void setColumnElementMap(
      Map<String, ColumnElement> columnElementMap) {
    this.columnElementMap = columnElementMap;
  }

  public List<String> getHeader() {
    return header;
  }

  public void setHeader(List<String> header) {
    this.header = header;
  }

  public Set<String> getRequiredColumn() {
    return requiredColumn;
  }

  public void setRequiredColumn(Set<String> requiredColumn) {
    this.requiredColumn = requiredColumn;
  }

}
