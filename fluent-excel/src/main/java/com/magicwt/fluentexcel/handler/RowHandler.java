package com.magicwt.fluentexcel.handler;

import com.magicwt.fluentexcel.context.ExcelContext;
import java.util.List;

/**
 * 行处理回调接口
 *
 * @author magicwt
 */
public interface RowHandler {

  /**
   * sheet开始
   */
  void beginSheet(ExcelContext excelContext);

  /**
   * 行处理
   *
   * @param objectList 返回的一批对象
   * @param clazz 对象的类
   * @param excelContext 上下文
   */
  void process(List<Object> objectList, Class clazz, ExcelContext excelContext);

  /**
   * sheet结束
   */
  void endSheet(ExcelContext excelContext);

}
