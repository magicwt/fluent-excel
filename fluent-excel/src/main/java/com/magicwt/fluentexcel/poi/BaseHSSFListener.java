package com.magicwt.fluentexcel.poi;

import com.google.common.collect.Lists;
import com.magicwt.fluentexcel.context.ExcelContext;
import com.magicwt.fluentexcel.exception.FunctionNotSupportException;
import com.magicwt.fluentexcel.handler.RowHandler;
import java.util.Collection;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;

/**
 * 流式读取excel03文件
 *
 * <p>实现{@link HSSFListener}接口</p>
 *
 * @author magicwt
 */
public class BaseHSSFListener extends FluentReadSupport implements HSSFListener {

  private boolean beginSheet = false;
  private SSTRecord sstRecord = null;
  private FormatTrackingHSSFListener formatListener;

  public BaseHSSFListener(ExcelContext excelContext, RowHandler rowHandler, int batchSize,
      Collection<Class<?>> classes) {
    this.excelContext = excelContext;
    this.rowHandler = rowHandler;
    this.batchSize = batchSize;
    this.classes = Lists.newArrayList(classes);
  }

  @Override
  public void processRecord(Record record) {
    switch (record.getSid()) {
      case RowRecord.sid:
        // 对于RowRecord，获取当前行
        RowRecord rowRecord = (RowRecord) record;
        currentRow = rowRecord.getRowNumber();
        break;
      case NumberRecord.sid:
        // 对于NumberRecord，获取当前行、当前列，并设置列值
        NumberRecord numberRecord = (NumberRecord) record;
        currentRow = numberRecord.getRow();
        currentCol = numberRecord.getColumn();
        setColumnValue(formatListener.formatNumberDateCell(numberRecord));
        break;
      case SSTRecord.sid:
        // 对于SSTRecord，暂存
        sstRecord = (SSTRecord) record;
        break;
      case LabelSSTRecord.sid:
        // 对于LabelSSTRecord，获取当前行、当前列，并从SSTRecord获取列值
        LabelSSTRecord labelSSTRecord = (LabelSSTRecord) record;
        currentRow = labelSSTRecord.getRow();
        currentCol = labelSSTRecord.getColumn();
        String value = null;
        if (sstRecord != null) {
          value = sstRecord.getString(labelSSTRecord.getSSTIndex()).toString();
        }
        setColumnValue(value);
        break;
      case BOFRecord.sid:
        // 对于BOFRecord，且类型为sheet，执行doBeginSheet方法
        BOFRecord bofRecord = (BOFRecord) record;
        if (bofRecord.getType() == BOFRecord.TYPE_WORKSHEET) {
          doBeginSheet();
          beginSheet = true;
        }
        break;
      case EOFRecord.sid:
        // 对于EOFRecord，且beginSheet为true，执行doEndSheet方法
        if (beginSheet) {
          doEndSheet();
          beginSheet = false;
        }
        break;
      case FormulaRecord.sid:
        // 对于FormulaRecord，抛出公式不支持异常
        throw new FunctionNotSupportException();
      default:
        break;
    }
    if (record instanceof LastCellOfRowDummyRecord) {
      // 对于LastCellOfRowDummyRecord，执行processRow方法
      processRow();
    }
  }

  public void setFormatListener(
      FormatTrackingHSSFListener formatListener) {
    this.formatListener = formatListener;
  }

}
