package com.magicwt.fluentexcel.reader;

import com.magicwt.fluentexcel.context.ExcelContext;
import com.magicwt.fluentexcel.handler.RowHandler;
import java.util.Collection;

/**
 * {@link ExcelReader}接口
 *
 * @author magicwt
 */
public interface ExcelReader {

  void read(ExcelContext excelContext, RowHandler rowHandler, int batchSize, Collection<Class<?>> classes);

}
