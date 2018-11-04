package com.magicwt.fluentexcel.writer;

import static com.magicwt.fluentexcel.constant.Constant.XLSX_SUFFIX;

import com.magicwt.fluentexcel.exception.FileFormatNotSupportException;

/**
 * {@link ExcelWriter}工厂
 *
 * @author magicwt
 */
public class ExcelWriterFactory {

  public static ExcelWriter create(String filePath) throws FileFormatNotSupportException {
    if (filePath.endsWith(XLSX_SUFFIX)) {
      return new XlsxExcelWriter(filePath);
    } else {
      throw new FileFormatNotSupportException();
    }
  }
}
