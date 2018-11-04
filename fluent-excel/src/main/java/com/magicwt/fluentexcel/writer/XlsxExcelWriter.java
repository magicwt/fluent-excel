package com.magicwt.fluentexcel.writer;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.magicwt.fluentexcel.constant.Constant;
import com.magicwt.fluentexcel.context.SheetContext;
import com.magicwt.fluentexcel.element.EntityElement;
import com.magicwt.fluentexcel.element.EntityElementCache;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicwt
 */
public class XlsxExcelWriter implements ExcelWriter {

  private static Logger LOGGER = LoggerFactory.getLogger(XlsxExcelWriter.class);

  private String filePath;
  private Workbook workbook;
  private Map<EntityElement, SheetContext> sheetContextMap;

  protected XlsxExcelWriter(String filePath) {
    init(filePath, Constant.WINDOW_SIZE);
  }

  protected XlsxExcelWriter(String filePath, int windowSize) {
    init(filePath, windowSize);
  }

  private void init(String filePath, int windowSize) {
    this.filePath = filePath;
    workbook = new SXSSFWorkbook(windowSize);
    sheetContextMap = Maps.newHashMap();
  }

  @Override
  public <T> void write(List<T> dataList) {
    if (CollectionUtils.isEmpty(dataList)) {
      return;
    }
    write(dataList, (Class<T>) dataList.get(0).getClass());
  }

  @Override
  public <T> void write(List<T> dataList, Class<T> clazz) {
    Preconditions.checkNotNull(clazz);
    EntityElement entityElement = EntityElementCache.getByClass(clazz);
    SheetContext sheetContext = sheetContextMap.get(entityElement);
    if (sheetContext == null) {
      Sheet sheet = workbook.createSheet(entityElement.getName());
      sheetContext = new SheetContext(sheet);
      sheetContext.write(entityElement.getHeader());
      sheetContextMap.put(entityElement, sheetContext);
    }
    if (CollectionUtils.isEmpty(dataList)) {
      return;
    }
    for (T data : dataList) {
      sheetContext.write(entityElement.to(data));
    }
  }

  @Override
  public void close() {
    OutputStream outputStream = null;
    try {
      File file = new File(filePath);
      file.getParentFile().mkdirs();
      outputStream = new FileOutputStream(file);
      workbook.write(outputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        workbook.close();
      } catch (IOException e) {
        LOGGER.error("close workbook error", e);
      }
      IOUtils.closeQuietly(outputStream);
    }
  }

}
