package com.coolpeng.framework.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coolpeng.framework.db.annotation.FieldDef;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.ObjectUtils;
import com.coolpeng.framework.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TMSBeanRowMapper<T> implements RowMapper<T> {

	private static Logger logger = LoggerFactory.getLogger(TMSBeanRowMapper.class);

	private Class<T> clazz;

	private Map<String, String> fieldAliasMap;

	public TMSBeanRowMapper(Class<T> clazz) {
		this.clazz = clazz;
		PropertyDescriptor[] properDesc = BeanUtils.getPropertyDescriptors(clazz);
		System.out.println();
	}

	public TMSBeanRowMapper(Class<T> clazz, Map<String, String> fieldAliasMap) {
		this.clazz = clazz;

		//有别名的字段肯定是关联字段的查询，只有关联字段的查询才会用到，简单对象，不需要。
		this.fieldAliasMap = fieldAliasMap;
	}

	@Override
	public T mapRow(ResultSet rs, int arg1) {

		try {

			ObjectUtils<T> util = new ObjectUtils<T>(clazz);

			//1、 组装基本数据类型
			T instance = util.toObject(rs, null);

			//2、 组装关联的实体类
			Field[] entityFields = ReflectUtils.getObjectFields(this.clazz, ReflectUtils.FIELD_FILTER_ONLY_ENTITY);
			if (!CollectionUtil.isEmpty(entityFields)) {
				//只处理关联查询出来的字段
				if (this.fieldAliasMap != null) {
					for (Field entityField : entityFields) {
						// 组装关联的实体类
						Object fieldObject = toRelationFieldObject(entityField, rs);
						if (fieldObject!=null){
							entityField.setAccessible(true);
							entityField.set(instance, fieldObject);
						}
					}
				}
			}



			//3、组装所有的标注的特殊字段
			List<Field> fieldDefList = ReflectUtils.getClassFieldsWithAnnotation(this.clazz, FieldDef.class);
			if (!CollectionUtil.isEmpty(fieldDefList)) {
				for (Field field : fieldDefList) {

					//3.1 如果是一个JSONColumn字段
					if (isJSONColumn(field)) {
						Object jsonObject = toJSONColumnObject(field, rs);
						if (jsonObject!=null){
							field.setAccessible(true);
							field.set(instance, jsonObject);
						}
					}

					//3.2
					//...

				}
			}

			return instance;

		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("", e);
		}

		return null;
	}


	/**
	 * 找出JSONColumn对象中的数据
	 * @param field
	 * @param rs
	 * @return
	 */
	private Object toJSONColumnObject(Field field, ResultSet rs){
		FieldDef anno = field.getAnnotation(FieldDef.class);
		Class<?>[] jsonColumns = anno.jsonColumn();
		if (CollectionUtil.isEmpty(jsonColumns)){
			return null;
		}


		//1、获取字符串数据
		String valueText = null;
		try {
			valueText = rs.getString(com.coolpeng.framework.utils.StringUtils.camelToUnderline(field.getName()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (org.apache.commons.lang.StringUtils.isBlank(valueText)){
			return null;
		}



		//2、将字符串数据转成对象
		List<Class> clazzlist = CollectionUtil.toList(jsonColumns);
		clazzlist.add(null);

		Class clazz0 = clazzlist.get(0);
		Class clazz1 = clazzlist.get(1);

		if (clazz0.equals(List.class)){
			List list = JSON.parseArray(valueText, clazz1);
			return list;
		}
		else if (clazz0.equals(Map.class)){
			JSONObject jsonObject = JSON.parseObject(valueText);
			Map<String,Object> map = new HashMap<>();
			Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
			for (Map.Entry<String, Object> entry : entrySet){
				String key = entry.getKey();
				Object value1 = jsonObject.getObject(key, clazz1);
				map.put(key,value1);
			}
			return map;
		}
		else if (clazz0.equals(Set.class)){
			List list = JSON.parseArray(valueText, clazz1);
			return new HashSet<>(list);
		}else {
			return JSON.parseObject(valueText,clazz0);
		}
	}


	/**
	 * 判断一个字段是否是JSON字段
	 * @param entityField
	 * @return
	 */
	private boolean isJSONColumn(Field entityField){
		FieldDef anno = entityField.getAnnotation(FieldDef.class);
		Class<?>[] jsonColumns = anno.jsonColumn();
		if (jsonColumns!=null && jsonColumns.length > 0){
			return true;
		}
		return false;
	}



	/**
	 * 只处理关联查询出来的字段
	 * @param entityField
	 * @param rs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Object toRelationFieldObject(Field entityField, ResultSet rs) throws InstantiationException, IllegalAccessException {

		//不处理JSON字段
		if (isJSONColumn(entityField)){
			return null;
		}


		int valueCount = 0;
		Class<?> fieldType = entityField.getType();
		Object instance = fieldType.newInstance();
		String fieldName = entityField.getName();

		Field[] basicFields = ReflectUtils.getObjectFields(fieldType, ReflectUtils.FIELD_FILTER_ONLY_BASIC);
		if (!CollectionUtil.isEmpty(basicFields)) {
			for (Field f : basicFields) {

				String objectPath = fieldName + "." + f.getName();
				String dbAlias = this.fieldAliasMap.get(objectPath);
				if (!StringUtils.isEmpty(dbAlias)) {
					Object value = null;
					try {
						value = rs.getObject(dbAlias);
					} catch (SQLException e) {
						logger.info(e.getMessage());
					}

					if (value != null) {
						// 如果实体类的数据类型是字符串
						if (ReflectUtils.isTypeString(f)) {
							value = "" + value;
						}
						valueCount++;
						f.setAccessible(true);
						f.set(instance, value);
					}
				}

			}
		}

		// 如果�?个�?�都没有
		if (valueCount == 0) {
			return null;
		}
		return instance;
	}

	// public Map<String, Object> toMap(ResultSet rs) throws SQLException {
	// ResultSetMetaData md = rs.getMetaData();
	//
	// Map<String, Object> map = new HashMap<String, Object>();
	// int columnCount = md.getColumnCount();
	// for (int i = 1; i <= columnCount; i++) {
	// int getScale = md.getScale(i);
	// String getSchemaName = md.getSchemaName(i);
	// String getCatalogName = md.getCatalogName(i);
	// String getTableName = md.getTableName(i);
	// String getColumnClassName = md.getColumnClassName(i);
	// int getColumnDisplaySize = md.getColumnDisplaySize(i);
	// String getColumnLabel = md.getColumnLabel(i);
	// String getColumnName = md.getColumnName(i);
	// int getColumnType = md.getColumnType(i);
	// String getColumnTypeName = md.getColumnTypeName(i);
	//
	// System.out.println(getColumnTypeName);
	// }
	//
	// return map;
	// }

}
