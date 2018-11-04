package com.magicwt.fluentexcel.demo.reader;

import com.magicwt.fluentexcel.context.ExcelContext;
import com.magicwt.fluentexcel.demo.model.Model;
import com.magicwt.fluentexcel.handler.RowHandler;
import com.magicwt.fluentexcel.reader.ExcelReader;
import com.magicwt.fluentexcel.reader.ExcelReaderFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author magicwt
 */
public class SimpleReader {

  static class SimpleRowHandler implements RowHandler {

    @Override
    public void beginSheet(ExcelContext excelContext) {

    }

    @Override
    public void process(List<Object> objectList, Class clazz, ExcelContext excelContext) {
      if (clazz == Model.class) {
        for (Object object : objectList) {
          Model model = (Model) object;
          System.out.println(model.toString());
        }
      }
    }

    @Override
    public void endSheet(ExcelContext excelContext) {

    }

  }

  public static void main(String[] args) {
    ExcelReader excelReader = ExcelReaderFactory.create("/Users/magicwt/test.xlsx");
    excelReader.read(new ExcelContext(), new SimpleRowHandler(), 1000, Arrays.asList(Model.class));
  }

}
