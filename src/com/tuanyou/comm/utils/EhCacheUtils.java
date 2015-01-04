package com.tuanyou.comm.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
/**
 * 
* @ClassName: EhCacheUtils
* @Description: TODO(EhCache缓存实现类)
* @author xf 
* @date 2014年8月19日 下午5:48:38
*
 */
public class EhCacheUtils {
	
	/**
	 * 获取EhCache中值
	 * 
	 * @param fileName
	 * @param cacheName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String fileName, String cacheName, String key) {
		CacheManager cacheManager = CacheManager.create(fileName);
		Cache cache = cacheManager.getCache(cacheName);
		Element element = cache.get(key);
		return (T) element.getValue();
	}
	
	/**
	 * 向EhCache中放值
	 * 
	 * @param fileName
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static <T> void setValue(String fileName, String cacheName, String key, T value) {
		CacheManager cacheManager = CacheManager.create(fileName);
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}
	
	
	/**
	 * 向EhCache中放值
	 * 
	 * @param fileName
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static  void remove(String fileName, String cacheName, String key) {
		CacheManager cacheManager = CacheManager.create(fileName);
		cacheManager.removeCache(cacheName);
		Cache cache = cacheManager.getCache(cacheName);
		cache.remove(key);
	}

}
