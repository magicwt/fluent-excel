package com.magicwt.fluentexcel.demo.writer;

import com.magicwt.fluentexcel.demo.model.Model;
import com.magicwt.fluentexcel.writer.ExcelWriter;
import com.magicwt.fluentexcel.writer.ExcelWriterFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author magicwt
 */
public class SimpleWriter {

  public static void main(String[] args) {
    List<Model> dataList = Arrays.asList(new Model(1, "s1"), new Model(2, "s2"));
    ExcelWriter excelWriter = ExcelWriterFactory.create("/Users/magicwt/test.xlsx");
    excelWriter.write(dataList);
    excelWriter.close();
  }
}
