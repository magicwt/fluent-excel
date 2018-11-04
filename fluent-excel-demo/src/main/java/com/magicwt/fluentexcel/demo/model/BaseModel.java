package com.magicwt.fluentexcel.demo.model;

import com.magicwt.fluentexcel.annotation.ExcelColumn;

/**
 * @author magicwt
 */
public class BaseModel {

  @ExcelColumn("值2")
  protected String value2;

  public String getValue2() {
    return value2;
  }

  public void setValue2(String value2) {
    this.value2 = value2;
  }

}
