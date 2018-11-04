package com.magicwt.fluentexcel.converter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.magicwt.fluentexcel.converter.impl.BigDecimalConverter;
import com.magicwt.fluentexcel.converter.impl.DateConverter;
import com.magicwt.fluentexcel.converter.impl.FloatConverter;
import com.magicwt.fluentexcel.converter.impl.IntegerConverter;
import com.magicwt.fluentexcel.converter.impl.LongConverter;
import com.magicwt.fluentexcel.converter.impl.StringConverter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 单元格和类属性转换器工厂
 *
 * @author magicwt
 */
public class ConverterFactory {

  // 属性类型和转换器的映射
  private static Map<Class<?>, Converter> fieldTypeConverterMap = Maps.newConcurrentMap();

  // 转换器类和转换器的映射
  private static Map<Class<?>, Converter> classConverterMap = Maps.newConcurrentMap();

  // 转换器类和转换器的缓存
  private static LoadingCache<Class<? extends Converter>, Converter> cache = CacheBuilder
      .newBuilder().build(new CacheLoader<Class<? extends Converter>, Converter>() {
        @Override
        public Converter load(Class<? extends Converter> converterClass) throws Exception {
          return converterClass.newInstance();
        }
      });

  static {
    // 初始化基础转换器，并放入映射中
    Converter integerConverter = new IntegerConverter();
    fieldTypeConverterMap.put(Integer.TYPE, integerConverter);
    fieldTypeConverterMap.put(Integer.class, integerConverter);
    classConverterMap.put(IntegerConverter.class, integerConverter);
    Converter longConverter = new LongConverter();
    fieldTypeConverterMap.put(Long.TYPE, longConverter);
    fieldTypeConverterMap.put(Long.class, longConverter);
    classConverterMap.put(LongConverter.class, longConverter);
    Converter floatConverter = new FloatConverter();
    fieldTypeConverterMap.put(Float.TYPE, floatConverter);
    fieldTypeConverterMap.put(Float.class, floatConverter);
    classConverterMap.put(FloatConverter.class, floatConverter);
    Converter bigDecimalConverter = new BigDecimalConverter();
    fieldTypeConverterMap.put(BigDecimal.class, bigDecimalConverter);
    classConverterMap.put(BigDecimalConverter.class, bigDecimalConverter);
    Converter dateConverter = new DateConverter();
    fieldTypeConverterMap.put(Date.class, dateConverter);
    classConverterMap.put(DateConverter.class, dateConverter);
    Converter stringConverter = new StringConverter();
    fieldTypeConverterMap.put(String.class, stringConverter);
    classConverterMap.put(StringConverter.class, stringConverter);
  }

  /**
   * 根据属性类型获取转换器
   */
  public static Converter getByFieldType(Class<?> fieldType) {
    return fieldTypeConverterMap.get(fieldType);
  }

  /**
   * 根据转换器类获取转换器
   */
  public static Converter getByClass(Class<? extends Converter> converterClass) {
    if (classConverterMap.containsKey(converterClass)) {
      return classConverterMap.get(converterClass);
    }
    try {
      return cache.get(converterClass);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

}
