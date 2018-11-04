package com.magicwt.fluentexcel.poi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magicwt.fluentexcel.context.ExcelContext;
import com.magicwt.fluentexcel.element.ColumnElement;
import com.magicwt.fluentexcel.element.EntityElement;
import com.magicwt.fluentexcel.element.EntityElementCache;
import com.magicwt.fluentexcel.handler.RowHandler;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 流式读取excel辅助抽象类
 *
 * @author magicwt
 */
public abstract class FluentReadSupport {

  // 上下文
  protected ExcelContext excelContext;

  // 行处理回调
  protected RowHandler rowHandler;

  // 批量读取行数
  protected int batchSize;

  // 当前行
  protected int currentRow = -1;

  // 当前列
  protected int currentCol = -1;

  // 行记录
  protected List<String> row = Lists.newArrayList();

  // 已读取并转换的对象列表
  protected List<Object> objectList = Lists.newArrayList();

  // 可能转换的类列表
  protected Collection<Class<?>> classes;

  // 当前EntityElement对象
  protected EntityElement currentEntityElement;

  // 当前列序号和ColumnElement对象的映射
  protected Map<Integer, ColumnElement> currentColumnElementMap;

  /**
   * sheet开始
   */
  protected void doBeginSheet() {
    // 执行RowHandler的beginSheet方法
    rowHandler.beginSheet(excelContext);
  }

  /**
   * sheet结束
   */
  protected void doEndSheet() {
    if (CollectionUtils.isNotEmpty(objectList)) {
      // 如果对象列表不为空，则执行RowHandler的process方法，并清空对象列表
      rowHandler.process(objectList, currentEntityElement.getEntityClass(), excelContext);
      objectList.clear();
    }
    // 执行RowHandler的endSheet方法
    rowHandler.endSheet(excelContext);
  }

  /**
   * 行处理
   */
  protected void processRow() {
    if (currentRow == 0) {
      // 第一行是表头，根据表头获取EntityElement对象
      buildElement();
    } else {
      if (CollectionUtils.isNotEmpty(row) && currentEntityElement != null) {
        // 如果当前行不为空且存在EntityElement，则将行记录转换为对象并加入对象列表
        objectList.add(currentEntityElement.from(row, currentColumnElementMap));
        if (objectList.size() >= batchSize) {
          // 如果对象列表大小达到批量读取行数，则执行RowHandler的process方法，并清空对象列表
          rowHandler.process(objectList, currentEntityElement.getEntityClass(), excelContext);
          objectList.clear();
        }
      }
    }
    // 清空行
    row.clear();
  }

  /**
   * 设置列值
   *
   * @param value
   */
  protected void setColumnValue(String value) {
    if (row.size() <= currentCol) {
      for (int col = row.size(); col < currentCol; col++) {
        row.add(col, StringUtils.EMPTY);
      }
      row.add(currentCol, value);
    } else {
      row.set(currentCol, value);
    }
  }

  /**
   * 根据表头获取{@link EntityElement}对象
   */
  private void buildElement() {
    currentEntityElement = null;
    for (Class entityClass : classes) {
      EntityElement entityElement = EntityElementCache.getByClass(entityClass);
      if (CollectionUtils.containsAll(row, entityElement.getRequiredColumn())) {
        currentEntityElement = entityElement;
        break;
      }
    }
    if (currentEntityElement == null) {
      return;
    }
    currentColumnElementMap = Maps.newHashMap();
    for (int index = 0; index < row.size(); index++) {
      String str = row.get(index);
      if (currentEntityElement.getColumnElementMap().containsKey(str)) {
        currentColumnElementMap.put(index, currentEntityElement.getColumnElementMap().get(str));
      }
    }
  }

}
