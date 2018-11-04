package com.magicwt.fluentexcel.reader;

import com.google.common.base.Preconditions;
import com.magicwt.fluentexcel.context.ExcelContext;
import com.magicwt.fluentexcel.handler.RowHandler;
import com.magicwt.fluentexcel.poi.BaseSheetContentsHandler;
import com.magicwt.fluentexcel.poi.XSSFSheetXMLHandler;
import com.magicwt.fluentexcel.poi.XSSFSheetXMLHandler.SheetContentsHandler;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * excel07的流式读取
 *
 * 参考: https://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
 * http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xssf/eventusermodel/examples/FromHowTo.java
 *
 * @author magicwt
 */
public class XlsxExcelReader extends AbstractExcelReader {

  protected XlsxExcelReader(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void read(ExcelContext excelContext, RowHandler rowHandler, int batchSize,
      Collection<Class<?>> classes) {
    Preconditions.checkNotNull(classes);
    Preconditions.checkArgument(classes.size() > 0);
    OPCPackage xlsxPackage = null;
    try {
      xlsxPackage = OPCPackage.open(new File(filePath), PackageAccess.READ);
      // 初始化XSSFReader，XSSFReader基于OOXML，读取文件中的一部分
      XSSFReader xssfReader = new XSSFReader(xlsxPackage);
      // 获取SharedStringsTable，对应OOXML中的sharedStrings.xml，记录excel文件中的字符串常量
      SharedStringsTable strings = xssfReader.getSharedStringsTable();
      // 获取StylesTable，对应OOXML中的styles.xml，记录excel文件中的样式
      StylesTable styles = xssfReader.getStylesTable();
      // 获取sheet迭代
      SheetIterator iterator = (SheetIterator) xssfReader.getSheetsData();
      while (iterator.hasNext()) {
        InputStream stream = iterator.next();
        try {
          // 对每个sheet进行处理
          processSheet(styles, strings,
              new BaseSheetContentsHandler(excelContext, rowHandler, batchSize, classes), stream);
        } catch (Exception e) {
          throw new RuntimeException(e);
        } finally {
          IOUtils.closeQuietly(stream);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      // 关闭
      IOUtils.closeQuietly(xlsxPackage);
    }
  }

  private void processSheet(StylesTable styles, SharedStringsTable strings,
      SheetContentsHandler sheetHandler, InputStream sheetInputStream) throws Exception {
    DataFormatter formatter = new DataFormatter();
    InputSource sheetSource = new InputSource(sheetInputStream);
    XMLReader sheetParser = SAXHelper.newXMLReader();
    ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter,
        false);
    sheetParser.setContentHandler(handler);
    sheetParser.parse(sheetSource);
  }
}
