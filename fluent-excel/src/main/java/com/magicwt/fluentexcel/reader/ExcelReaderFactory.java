package com.magicwt.fluentexcel.reader;

import static com.magicwt.fluentexcel.constant.Constant.XLSX_SUFFIX;
import static com.magicwt.fluentexcel.constant.Constant.XLS_SUFFIX;

import com.magicwt.fluentexcel.exception.FileFormatNotSupportException;

/**
 * {@link ExcelReader}工厂
 *
 * @author magicwt
 */
public class ExcelReaderFactory {

  public static ExcelReader create(String filePath) throws FileFormatNotSupportException {
    if (filePath.endsWith(XLSX_SUFFIX)) {
      return new XlsxExcelReader(filePath);
    } else if (filePath.endsWith(XLS_SUFFIX)) {
      return new XlsExcelReader(filePath);
    } else {
      throw new FileFormatNotSupportException();
    }
  }

}
