package com.magicwt.fluentexcel.element;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;

/**
 * {@link EntityElement}缓存
 *
 * @author magicwt
 */
public class EntityElementCache {

  private static LoadingCache<Class<?>, EntityElement> cache = CacheBuilder.newBuilder().build(
      new CacheLoader<Class<?>, EntityElement>() {
        @Override
        public EntityElement load(Class<?> entityClass) throws Exception {
          return EntityElementBuilder.build(entityClass);
        }
      });

  /**
   * 根据{@link Class<?>}获取{@link EntityElement}
   */
  public static EntityElement getByClass(Class<?> entityClass) {
    try {
      return cache.get(entityClass);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

}
