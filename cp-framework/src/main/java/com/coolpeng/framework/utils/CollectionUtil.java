package com.coolpeng.framework.utils;

import com.coolpeng.framework.exception.FieldNotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class CollectionUtil {

	private static Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

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
	public static Map<String, Object> toMap(Collection collection, String propName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
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

	/**
	 * 获取参数中第一个不为空的字符串，如果返回空字符串，说明获取失败
	 * @param args
	 * @return
	 */
	public static String getNotBlank(String ...args) {

		if (args == null) {
			return "";
		}

		for (String s : args) {
			if (StringUtils.isNotBlank(s)) {
				return s;
			}
		}

		return "";

	}


	/**
	 * 求两个集合的差集
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<Object> difference(List list1,List list2){
		List<Object> result = new ArrayList<>();

		for (Object object:list1){
			if (object!=null){
				result.add(object);
			}
		}

		result.removeAll(list2);
		return result;
	}



	/**
	 * 获取List中第一个元素的Field
	 * @param list
	 * @param fieldName
	 * @param <T>
	 * @return
	 */
	private static <T> Field getFirstElementField(List<T> list,String fieldName){

		if (list == null || list.isEmpty()) {
			return null;
		}


		Class<? extends Object> clazz = list.get(0).getClass();
		Class<? extends Object> clazz0 = clazz;
		int count = 0;
		while (clazz != Object.class && count < 20) {
			Field field = null;
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
			if (field != null) {
				field.setAccessible(true);
				return field;
			}
			clazz = clazz.getSuperclass();
			count++;
		}

		logger.error("{}没有此属性{}", clazz0.getName(), fieldName);

		return null;
	}


	/**
	 * 查找或排除集合中的元素，私有方法以供reject和find调用
	 * @param list
	 * @param fieldName
	 * @param fieldValue
	 * @param isReject
	 * @param <T>
	 * @return
	 */
	private static <T> List<T> findOrReject(List<T> list,String fieldName,Object fieldValue,boolean isReject){

		Field field = getFirstElementField(list, fieldName);

		if (field == null) {
			return list;
		}


		Set<Object> targets = new HashSet<>();

		if (fieldValue instanceof Collection){
			targets.addAll((Collection)fieldValue);
		}else {
			targets.add(fieldValue);
		}



		List<T> result = new ArrayList<>();

		for (T object : list) {
			try {
				Object value = field.get(object);

				if (isReject) {

					if(!targets.contains(value)){
						result.add(object);
					}

				} else {
					if(targets.contains(value)){
						result.add(object);
					}
				}

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 从list中过滤掉指定属性指定值的元素
	 * @param list
	 * @param fieldName
	 * @param fieldValue
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> reject(List<T> list,String fieldName,Object fieldValue){
		return findOrReject(list,fieldName,fieldValue,true);
	}


	/**
	 * 从list中过滤掉指定属性指定值的元素
	 * @param list
	 * @param fieldName
	 * @param fieldValue
	 * @param <T>
	 * @return
	 */
	public static <T> Object findOne(List<T> list,String fieldName,Object fieldValue){
		List<T> list2 = findOrReject(list, fieldName, fieldValue, false);
		if (isEmpty(list2)){
			return null;
		}
		return list2.get(0);
	}


	/**
	 * 从list中过滤掉指定属性指定值的元素
	 * @param list
	 * @param fieldName
	 * @param fieldValue
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> find(List<T> list,String fieldName,Object fieldValue){
		return findOrReject(list,fieldName,fieldValue,false);
	}


	/**
	 * 模仿underscore的pluck函数
	 * var stooges = [{name: 'moe', age: 40}, {name: 'larry', age: 50}, {name: 'curly', age: 60}];
	 * _.pluck(stooges, 'name'); => ["moe", "larry", "curly"]
	 * @param list
	 * @param fieldName
	 * @return
	 */
	public static <T> List pluck(List<T> list,String fieldName){
		Field field = getFirstElementField(list, fieldName);

		if (field == null) {
			return new ArrayList<>();
		}

		List<Object> result = new ArrayList<>();

		for (T object : list) {
			try {
				Object value = field.get(object);
				if (value != null) {
					result.add(value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	/**
	 * 判断对象是否为空
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}

		if (object.getClass().isArray()) {
			Object[] arr = (Object[]) object;
			return arr.length == 0;
		}

		if (object instanceof Map) {
			Map m = (Map) object;
			return m.isEmpty();
		}

		if (object instanceof Collection) {
			Collection c = (Collection) object;
			return c.isEmpty();
		}

		if (object instanceof String) {
			String s = (String) object;
			s = s.trim();
			return s.isEmpty();
		}

		logger.error("只能判断数组,Map,Collection,String");

		return false;
	}


	public static String encodeHtml(String str){
		if (str != null) {
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
		}
		return str;
	}


	/**
	 * 求两个集合的差集
	 * @param list1
	 * @param list2
	 * @param fieldName
	 * @return
	 */
	public static List<Object> differenceByKey(List<Object> list1,List<Object> list2,String fieldName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Field field = getFirstElementField(list1, fieldName);

		Map<String,Object> map2 = toMap(list2,fieldName);

		List<Object> result = new ArrayList<>();
		for (Object object : list1){
			Object filedValue = field.get(object);
			if (!map2.containsKey(filedValue)){
				result.add(object);
			}
		}

		return result;
	}


}
