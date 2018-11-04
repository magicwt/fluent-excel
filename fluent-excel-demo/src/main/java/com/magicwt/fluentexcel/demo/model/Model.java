package com.magicwt.fluentexcel.demo.model;

import com.magicwt.fluentexcel.annotation.ExcelColumn;
import com.magicwt.fluentexcel.annotation.ExcelEntity;

/**
 * @author magicwt
 */
@ExcelEntity("测试sheet")
public class Model extends BaseModel {

  @ExcelColumn("值1")
  private int value1;

  public Model() {
  }

  public Model(int value1, String value2) {
    this.value1 = value1;
    this.value2 = value2;
  }

  public int getValue1() {
    return value1;
  }

  public void setValue1(int value1) {
    this.value1 = value1;
  }

  @Override
  public String toString() {
    return "Model{" +
        "value1=" + value1 +
        ", value2='" + value2 + '\'' +
        '}';
  }

}
