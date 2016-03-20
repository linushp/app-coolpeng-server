package com.coolpeng.framework.db;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.ObjectUtils;
import com.coolpeng.framework.utils.ReflectUtils;

public class BeanRowMapper<T> implements RowMapper<T> {

	private static Logger logger = LoggerFactory.getLogger(BeanRowMapper.class);

	private Class<T> clazz;

	private Map<String, String> fieldAliasMap;

	public BeanRowMapper(Class<T> clazz, Map<String, String> fieldAliasMap) {
		this.clazz = clazz;
		this.fieldAliasMap = fieldAliasMap;
	}

	@Override
	public T mapRow(ResultSet rs, int arg1) {

		try {

			ObjectUtils<T> util = new ObjectUtils<T>(clazz);

			// 组装基本数据类型
			T instance = util.toObject(rs, null);

			// 组装�?关联的实体类
			Field[] entityFields = ReflectUtils.getObjectFields(this.clazz, ReflectUtils.FIELD_FILTER_ONLY_ENTITY);
			if (!CollectionUtil.isEmpty(entityFields)) {
				for (Field entityField : entityFields) {

					// 组装�?关联的实体类
					Object fieldObject = toFieldObject(entityField, rs);
					if (fieldObject != null) {
						entityField.setAccessible(true);
						entityField.set(instance, fieldObject);
					}
				}
			}

			return instance;

		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("", e);
		}

		return null;
	}

	private Object toFieldObject(Field entityField, ResultSet rs) throws InstantiationException, IllegalAccessException {

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
