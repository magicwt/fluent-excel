package com.magicwt.fluentexcel.element;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.annotation.ExcelEntity;
import com.magicwt.fluentexcel.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link EntityElement}构建器
 *
 * @author magicwt
 */
public class EntityElementBuilder {

  /**
   * 根据{@link Class<?>}构建{@link EntityElement}
   */
  public static EntityElement build(Class<?> entityClass) {
    EntityElement entityElement = new EntityElement();
    // 设置EntityClass
    entityElement.setEntityClass(entityClass);
    ExcelEntity excelEntity = entityClass.getAnnotation(ExcelEntity.class);
    // 设置ExcelEntity
    entityElement.setExcelEntity(excelEntity);
    // 获取所有带ExcelColumn注解的属性
    Field[] fields = ReflectionUtil.getAnnotationFields(entityClass, ExcelColumn.class);
    Map<String, ColumnElement> columnElementMap = Maps.newLinkedHashMap();
    List<String> header = Lists.newArrayList();
    Set<String> requiredColumn = Sets.newHashSet();
    for (Field field : fields) {
      // 对于每个属性
      ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
      // 判断ExcelColumn是否有重复
      if (columnElementMap.containsKey(excelColumn.value())) {
        throw new RuntimeException(
            entityClass.getCanonicalName() + "已存在ExcelColumn value为" + excelColumn.value() + "的字段");
      }
      // 构建ColumnElement
      ColumnElement columnElement = ColumnElementBuilder.build(field, excelColumn);
      columnElementMap.put(excelColumn.value(), columnElement);
      header.add(excelColumn.value());
      if (excelColumn.required()) {
        requiredColumn.add(excelColumn.value());
      }
    }
    // 设置ColumnElementMap
    entityElement.setColumnElementMap(columnElementMap);
    // 设置Header
    entityElement.setHeader(header);
    // 设置RequiredColumn
    entityElement.setRequiredColumn(requiredColumn);
    return entityElement;
  }

}
