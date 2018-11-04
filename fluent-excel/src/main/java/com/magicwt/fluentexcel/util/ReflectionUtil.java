package com.magicwt.fluentexcel.util;

import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 反射工具类
 */
public class ReflectionUtil {

  /**
   * 获取所有包含指定Annotation的Field数组
   */
  public static Field[] getAnnotationFields(Class<?> clazz,
      Class<? extends Annotation> annotationClass) {
    if (clazz == null || annotationClass == null) {
      return null;
    }
    List<Field> fields = getAllFieldsOfClass0(clazz);
    if (CollectionUtils.isEmpty(fields)) {
      return null;
    }
    List<Field> list = Lists.newArrayList();
    for (Field field : fields) {
      if (null != field.getAnnotation(annotationClass)) {
        list.add(field);
        field.setAccessible(true);
      }
    }
    return list.toArray(new Field[0]);
  }

  /**
   * 获取类及父类的所有Field
   */
  public static List<Field> getAllFieldsOfClass0(Class<?> clazz) {
    List<Field> fields = Lists.newArrayList();
    List<Class<?>> clazzs = Lists.newArrayList();
    for (Class<?> itr = clazz; hasSuperClass(itr); ) {
      clazzs.add(itr);
      itr = itr.getSuperclass();
    }
    for (int i = clazzs.size() - 1; i >= 0; i--) {
      fields.addAll(Arrays.asList(clazzs.get(i).getDeclaredFields()));
    }
    return fields;
  }

  /**
   * 判断是否有超类
   */
  public static boolean hasSuperClass(Class<?> clazz) {
    return (clazz != null) && !clazz.equals(Object.class);
  }

}
