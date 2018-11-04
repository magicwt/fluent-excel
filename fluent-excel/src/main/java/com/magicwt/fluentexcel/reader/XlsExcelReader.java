package com.magicwt.fluentexcel.reader;


import com.google.common.base.Preconditions;
import com.magicwt.fluentexcel.context.ExcelContext;
import com.magicwt.fluentexcel.handler.RowHandler;
import com.magicwt.fluentexcel.poi.BaseHSSFListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * excel03的流式读取
 *
 * 参考: https://poi.apache.org/spreadsheet/how-to.html#record_aware_event_api
 * http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/hssf/eventusermodel/examples/XLS2CSVmra.java
 *
 * @author magicwt
 */
public class XlsExcelReader extends AbstractExcelReader {

  protected XlsExcelReader(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void read(ExcelContext excelContext, RowHandler rowHandler, int batchSize,
      Collection<Class<?>> classes) {
    Preconditions.checkNotNull(classes);
    Preconditions.checkArgument(classes.size() > 0);
    InputStream inputStream = null;
    POIFSFileSystem fileSystem = null;
    try {
      inputStream = new FileInputStream(filePath);
      fileSystem = new POIFSFileSystem(inputStream);
      // 初始化BaseHSSFListener
      BaseHSSFListener hssfListener = new BaseHSSFListener(excelContext, rowHandler, batchSize,
          classes);
      // 初始化MissingRecordAwareHSSFListener，将BaseHSSFListener作为MissingRecordAwareHSSFListener的子listener
      // MissingRecordAwareHSSFListener填补了dummy record，比如缺失行、缺失列、行尾等record，便于自定义listener的操作
      MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(hssfListener);
      // 初始化FormatTrackingHSSFListener，将MissingRecordAwareHSSFListener作为FormatTrackingHSSFListener的子listener
      // FormatTrackingHSSFListener记录格式化信息，并提供了方法根据单元格id查询格式化值
      FormatTrackingHSSFListener formatListener = new FormatTrackingHSSFListener(listener);
      // 将FormatTrackingHSSFListener注入自定义listener，便于自定义listener回调其中的方法
      hssfListener.setFormatListener(formatListener);
      // 初始化事件工厂
      HSSFEventFactory factory = new HSSFEventFactory();
      HSSFRequest request = new HSSFRequest();
      // 为所有的记录添加listener
      request.addListenerForAllRecords(formatListener);
      // 流式读取excel并处理记录事件
      factory.processWorkbookEvents(request, fileSystem);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      // 关闭
      IOUtils.closeQuietly(fileSystem);
      IOUtils.closeQuietly(inputStream);
    }
  }

}
