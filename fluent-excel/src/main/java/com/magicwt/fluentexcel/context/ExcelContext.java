package com.magicwt.fluentexcel.context;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * 对excel文件处理时的上下文
 *
 * @author magicwt
 */
public class ExcelContext {

  private Map<String, Object> values = Maps.newHashMap();

  public Object get(String key) {
    return values.get(key);
  }

  public void put(String key, Object value) {
    values.put(key, value);
  }

}
