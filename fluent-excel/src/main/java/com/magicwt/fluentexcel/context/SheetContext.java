package com.magicwt.fluentexcel.context;

import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * excel sheet处理时的上下文
 *
 * @author magicwt
 */
public class SheetContext {

  // 当前sheet
  private Sheet sheet;

  // 当前sheet写入的行量
  private int rowNum;

  public SheetContext(Sheet sheet) {
    this.sheet = sheet;
    this.rowNum = 0;
  }

  /**
   * 向当前sheet写入一行数据
   */
  public void write(List<String> strList) {
    Row row = sheet.createRow(rowNum++);
    int column = 0;
    for (String str : strList) {
      Cell cell = ((Row) row).createCell(column++);
      ((Cell) cell).setCellValue(str);
    }
  }

}
