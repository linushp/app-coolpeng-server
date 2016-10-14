package com.coolpeng.framework.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ObjectUtils<T> {

	private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

	private Class<T> clazz;

	private Field[] fields;

	public ObjectUtils(Class<T> clazz) {
		this.clazz = clazz;
		this.fields = ReflectUtils.getObjectFields(clazz, ReflectUtils.FIELD_FILTER_ONLY_BASIC);
	}

	/**
	 * 将Map转成java对象
	 *
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public List<T> toObjectList(List<Map<String, Object>> mapList, String entityKeyPrefix) throws InstantiationException, IllegalAccessException {
		List<T> objectList = new ArrayList<T>();
		for (Map<String, Object> map : mapList) {
			T instance = clazz.newInstance();
			instance = merge(instance, map, entityKeyPrefix);
			objectList.add(instance);
		}
		return objectList;
	}

	/**
	 * 将Map转成java对象
	 * 
	 * @param map
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T toObject(Map<String, Object> map, String entityKeyPrefix) throws InstantiationException, IllegalAccessException {

		T instance = clazz.newInstance();

		return merge(instance, map, entityKeyPrefix);
	}

	/**
	 * 将Map转成java对象
	 * 
	 * @param instance
	 * @param map
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T merge(T instance, Map<String, Object> map, String entityKeyPrefix) throws InstantiationException, IllegalAccessException {

		if (entityKeyPrefix == null) {
			entityKeyPrefix = "";
		}

		for (Field f : fields) {

			f.setAccessible(true);

			String fieldName = f.getName();

			Object value = map.get(entityKeyPrefix + fieldName);
			if (value != null) {
				f.set(instance, value);
			}
		}

		return instance;
	}

	/**
	 * 将ResultSet数据集转换成Java对象
	 * 
	 * @param rs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T toObject(ResultSet rs, String entityKeyPrefix) throws InstantiationException, IllegalAccessException {

		T instance = clazz.newInstance();

		return merge(instance, rs, entityKeyPrefix);
	}

	/**
	 * 将ResultSet数据集转换成Java对象
	 * 
	 * @param rs
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T merge(T instance, ResultSet rs, String entityKeyPrefix) throws IllegalArgumentException, IllegalAccessException {

		if (entityKeyPrefix == null) {
			entityKeyPrefix = "";
		} else {
			entityKeyPrefix = entityKeyPrefix + "_";
		}

		for (Field f : fields) {

			f.setAccessible(true);

			String fieldName = f.getName();
			String dbFieldName = StringUtils.camelToUnderline(fieldName);

			Object value = null;
			try {
				value = rs.getObject(entityKeyPrefix + dbFieldName);
			} catch (SQLException e) {

				String tableName = "";
				try {
					ResultSetMetaData rsmd = rs.getMetaData();
					tableName = rsmd.getTableName(1);
				} catch (SQLException e1) {
				}
				logger.info(tableName + "  " + e.getMessage());
			}

			if (value != null) {
				if (ReflectUtils.isTypeString(f)) {
					value = "" + value;
				}
				f.set(instance, value);
			}
		}

		return instance;
	}

	/**
	 * 将JSONArray转成java对象
	 *
	 * @return
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public List<T> toObjectList(JSONArray jsonArray) throws InstantiationException, IllegalAccessException {
		List<T> objectList = new ArrayList<T>();

		Iterator<Object> it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();
			T instance = toObject(jsonObject);
			objectList.add(instance);
		}

		return objectList;
	}

	/**
	 * 将JSON对象转换成对象实�?
	 *
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T toObject(JSONObject jsonObject) throws InstantiationException, IllegalAccessException {

		T instance = clazz.newInstance();

		return merge(instance, jsonObject);
	}

	/**
	 * 将JSON对象转换成对象实�?
	 * 
	 * @param instance
	 * @param jsonObject
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public T merge(T instance, JSONObject jsonObject) throws IllegalArgumentException, IllegalAccessException {

		for (Field f : fields) {

			f.setAccessible(true);

			String fieldName = f.getName();

			Object value = jsonObject.get(fieldName);

			if (value != null) {
				f.set(instance, value);
			}
		}

		return instance;
	}

	/**
	 * 合并两个对象，将对象2的不为空的属性复制到对象1中，并返回对�?1
	 * 
	 * @param object1
	 * @param object2
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public T merge(T object1, T object2) {
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				Object value = f.get(object2);
				if (value != null) {
					f.set(object1, value);
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return object1;
	}

}
