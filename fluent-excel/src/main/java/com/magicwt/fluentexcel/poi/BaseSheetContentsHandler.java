package com.magicwt.fluentexcel.poi;

import com.google.common.collect.Lists;
import com.magicwt.fluentexcel.context.ExcelContext;
import com.magicwt.fluentexcel.handler.RowHandler;
import java.util.Collection;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * {@link XSSFSheetXMLHandler.SheetContentsHandler}实现
 *
 * @author magicwt
 */
public class BaseSheetContentsHandler extends FluentReadSupport implements
    XSSFSheetXMLHandler.SheetContentsHandler {

  @Override
  public void startRow(int rowNum) {
    currentRow = rowNum;
    currentCol = -1;
  }

  @Override
  public void endRow(int rowNum) {
    processRow();
  }

  @Override
  public void cell(String cellReference, String formattedValue, XSSFComment comment) {
    if (cellReference == null) {
      cellReference = new CellAddress(currentRow, currentCol).formatAsString();
    }
    currentCol = new CellReference(cellReference).getCol();
    setColumnValue(formattedValue);
  }

  @Override
  public void beginSheet() {
    doBeginSheet();
  }

  @Override
  public void endSheet() {
    doEndSheet();
  }

  @Override
  public void headerFooter(String text, boolean isHeader, String tagName) {
    // do nothing
  }

  public BaseSheetContentsHandler(ExcelContext excelContext, RowHandler rowHandler, int batchSize,
      Collection<Class<?>> classes) {
    this.excelContext = excelContext;
    this.rowHandler = rowHandler;
    this.batchSize = batchSize;
    this.classes = Lists.newArrayList(classes);
  }

}
