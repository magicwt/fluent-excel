package com.magicwt.fluentexcel.writer;

import java.util.List;

/**
 * {@link ExcelWriter}接口
 *
 * @author magicwt
 */
public interface ExcelWriter {

  <T> void write(List<T> dataList);

  <T> void write(List<T> dataList, Class<T> clazz);

  void close();

}
