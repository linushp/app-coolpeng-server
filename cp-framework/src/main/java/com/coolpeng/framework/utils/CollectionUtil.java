package com.coolpeng.framework.utils;

import com.coolpeng.framework.exception.FieldNotFoundException;
import org.apache.commons.beanutils.PropertyUtils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CollectionUtil {

	public static boolean isEmpty(Collection<?> collections) {
		return collections == null || collections.isEmpty();
	}

	public static boolean isEmpty(Object[] collections) {
		return collections == null || collections.length == 0;
	}

	public static boolean isEmpty(Map<?, ?> collections) {
		return collections == null || collections.isEmpty();
	}

	public static int indexOf(Object[] collections, Object object) {
		if (!isEmpty(collections)) {
			for (int i = 0; i < collections.length; i++) {
				Object obj = collections[i];
				if (object.equals(obj)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static <T> Map<Object, List<T>> groupBy(Collection<T> collections, String fieldName) throws FieldNotFoundException, IllegalArgumentException, IllegalAccessException {

		Map<Object, List<T>> map = new HashMap<Object, List<T>>();

		if (isEmpty(collections)) {
			return map;
		}

		Field field = ReflectUtils.getObjectFieldByName(collections, fieldName);
		for (T t : collections) {
			Object fieldValue = field.get(t);
			List<T> list = map.get(fieldValue);
			if (list == null) {
				list = new ArrayList<T>();
				map.put(fieldValue, list);
			}
			list.add(t);
		}

		return map;
	}


	/**
	 * 将list<Object> 对象转换为map对象.
	 *
	 * @param collection
	 * @param propName 对象属性名称,值对应map中key
	 * @return map key --> propName对应属性的值
	 *             value --> object
	 */
	public static Map<String, ?> toMap(Collection collection, String propName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Map<String, Object> results = new HashMap<>();
		if (null!=collection){
			for(Object obj : collection){
				try {
					Object propObj = PropertyUtils.getProperty(obj, propName);
					if (propObj!=null){
						results.put(propObj.toString(), obj);
					}
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return results;
	}
	

}
