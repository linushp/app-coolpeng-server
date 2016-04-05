package com.coolpeng.framework.db;

import com.coolpeng.framework.db.annotation.FieldDef;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TemplateSQL
{
	private static Logger logger = LoggerFactory.getLogger(TemplateSQL.class);
	public static final String TABLE_PREFIX = "t";
	private String tableName = "";

	private String schemaPrefix = "";
	private Class<?> clazz;
	private Field[] basicFields;
	private String[] basicFieldNames;
	private String[] dbBasicFieldNames;
	private Field[] entityFields;
	private String[] entityFieldNames;

	public TemplateSQL(Class<?> clazz)
	{
		this.clazz = clazz;

		initBasicField(clazz);

		initJoinEntityField(clazz);

		this.tableName = getTableName(clazz);
	}

	private void initJoinEntityField(Class<?> clazz)
	{
		this.entityFields = ReflectUtils.getObjectFields(clazz, 2);

		if (!CollectionUtil.isEmpty(this.entityFields)) {
			int fieldsLength = this.entityFields.length;
			this.entityFieldNames = new String[fieldsLength];
			for (int i = 0; i < fieldsLength; i++) {
				Field f = this.entityFields[i];
				this.entityFieldNames[i] = f.getName();
			}
		}
	}

	private void initBasicField(Class<?> clazz)
	{

		//TODO 在这里JSON类型的字段也被看做是基本数据类型。
		List<Field> basicFieldsList = CollectionUtil.toList(ReflectUtils.getObjectFields(clazz, ReflectUtils.FIELD_FILTER_ONLY_BASIC));
		List<Field> annoList = ReflectUtils.getClassFieldsWithAnnotation(clazz, FieldDef.class);
		for (Field field:annoList){
			if (ReflectUtils.isJSONColumn(field)){
				basicFieldsList.add(field);
			}
		}
		this.basicFields = CollectionUtil.toArray(basicFieldsList);


		if (!CollectionUtil.isEmpty(this.basicFields))
		{
			int fieldsLength = this.basicFields.length;
			this.basicFieldNames = new String[fieldsLength];
			this.dbBasicFieldNames = new String[fieldsLength];
			for (int i = 0; i < fieldsLength; i++) {
				Field f = this.basicFields[i];
				String fieldName = f.getName();
				this.basicFieldNames[i] = fieldName;
				this.dbBasicFieldNames[i] = StringUtils.camelToUnderline(fieldName);
			}
		}
	}

	/**
	 * 获取表名称：就是根据类名称
	 * @param clazz
	 * @return
	 */
	public String getTableName(Class<?> clazz)
	{
		String clazzName = clazz.getSimpleName();
		return this.schemaPrefix + StringUtils.camelToUnderline(new StringBuilder().append("t").append(clazzName).toString());
	}

	public String getInsertSQL()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("" + this.tableName + "");
		sql.append(" (");
		for (int i = 0; i < this.dbBasicFieldNames.length; i++) {
			sql.append("" + this.dbBasicFieldNames[i] + "");
			if (i < this.dbBasicFieldNames.length - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		sql.append(" VALUES(");
		for (int i = 0; i < this.basicFieldNames.length; i++) {
			String fieldName = this.basicFieldNames[i];
			sql.append(":" + fieldName);
			if (i < this.basicFieldNames.length - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		return sql.toString();
	}

	public String getUpdateSQL()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ");
		sql.append("" + this.tableName + "");
		sql.append(" SET ");
		for (int i = 0; i < this.dbBasicFieldNames.length; i++) {
			String dbFieldName = this.dbBasicFieldNames[i];
			String fieldName = this.basicFieldNames[i];
			if (!"id".equals(fieldName)) {
				sql.append(dbFieldName);
				sql.append("=:" + fieldName);

				if (i < this.dbBasicFieldNames.length - 1) {
					sql.append(",");
				}
			}
		}
		sql.append(" WHERE ");
		sql.append(" id ");
		sql.append("=:id");
		return sql.toString();
	}

	public String getUpdateSQL(Collection<String> fieldNames, String whereKey)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ");
		sql.append("" + this.tableName + "");
		sql.append(" SET ");

		String[] fieldNameArray = toStringArray(fieldNames);
		int length = fieldNameArray.length;
		for (int i = 0; i < length; i++) {
			String fieldName = fieldNameArray[i];
			String dbFieldName = StringUtils.camelToUnderline(fieldName);
			if (!"id".equals(fieldName)) {
				sql.append(dbFieldName);
				sql.append("=:" + fieldName);
				if (i < length - 1) {
					sql.append(",");
				}
			}
		}

		sql.append(" WHERE ");
		if ((whereKey != null) && (!whereKey.isEmpty())) {
			sql.append(" " + StringUtils.camelToUnderline(whereKey) + " ");
			sql.append("=:" + whereKey);
		} else {
			sql.append(" id ");
			sql.append("=:id");
		}
		return sql.toString();
	}

	public String getCountSQL(Collection<String> where) throws FieldNotFoundException
	{
		String[] whereArray = toStringArray(where);
		return getCountSQL(whereArray);
	}

	public String getCountSQL(String[] where) throws FieldNotFoundException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(0) FROM ");
		sql.append("" + this.tableName + "");
		sql.append(toWhereSQL(where));
		return sql.toString();
	}

	public String getSelectSQL()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ");
		sql.append("" + this.tableName + "");
		return sql.toString();
	}

	public String getSelectSQL(Collection<String> where) throws FieldNotFoundException
	{
		if ((where != null) && (!where.isEmpty()))
		{
			String[] whereArray = toStringArray(where);

			return getSelectSQL(whereArray);
		}
		return getSelectSQL();
	}

	public String getSelectSQL(String[] where)
			throws FieldNotFoundException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ");
		sql.append("" + this.tableName + "");

		sql.append(toWhereSQL(where));

		return sql.toString();
	}

	public Tuple getLeftJoinSelectSQL(Collection<String> where, QueryCondition qc, int pageNumber, int pageSize, String[] leftJoinFieldNameArray) throws FieldNotFoundException, ClassNotFoundException
	{
		AtomicInteger atomicInteger = new AtomicInteger(0);
		Map tableAliasMap = new HashMap();
		Map fieldAliasMap = new HashMap();

		LStringBuffer sql = new LStringBuffer(" ");

		String tableAlias = getTableAlias(tableAliasMap, atomicInteger, "");

		LStringBuffer selectFields = new LStringBuffer(" , ");

		sql.append("SELECT ");
		sql.append(selectFields, "count(0)");
		sql.append(" FROM");
		sql.append(this.tableName);
		sql.append(tableAlias);

		selectFields.append(tableAlias + ".*");

		if (!CollectionUtil.isEmpty(leftJoinFieldNameArray))
		{
			for (String leftJoinFieldName : leftJoinFieldNameArray) {
				Field field = getEntityField(leftJoinFieldName);
				String tableDbFieldName = getDbFieldName(leftJoinFieldName + "Id");

				Class fieldClazz = field.getType();

				String fieldTableName = getTableName(fieldClazz);
				String fieldTableAlias = getTableAlias(tableAliasMap, atomicInteger, leftJoinFieldName);

				sql.append("left join");
				sql.append(fieldTableName);
				sql.append(fieldTableAlias);
				sql.append("on");
				sql.append(fieldTableAlias + ".id=" + tableAlias + "." + tableDbFieldName);

				Tuple t = getSubDbFields(fieldClazz, leftJoinFieldName, fieldTableAlias, atomicInteger);
				selectFields.append((String)t.first());
				fieldAliasMap.putAll((Map)t.second());
			}

		}

		sql.append(toWhereSQL(toStringArray(where), tableAliasMap));

		String totalCountSQL = sql.join2();

		if ((qc != null) && (qc.getOrderField() != null)) {
			String field = qc.getOrderField();
			String dbField = StringUtils.camelToUnderline(field);
			sql.append("order by  " + dbField + " " + qc.getOrderType() + "");
		}

		if ((pageNumber > 0) && (pageSize > 0)) {
			int pageBegin = (pageNumber - 1) * pageSize;
			sql.append("limit " + pageBegin + "," + pageSize);
		}

		return new Tuple(new Object[] { sql.join1(), tableAliasMap, fieldAliasMap, totalCountSQL });
	}

	private Tuple getSubDbFields(Class<?> fieldClazz, String leftJoinFieldName, String fieldTableAlias, AtomicInteger atomicInteger)
	{
		Map fieldAliasMap = new HashMap();

		Field[] fieldSubFields = ReflectUtils.getObjectFields(fieldClazz, 1);
		LStringBuffer selectFields = new LStringBuffer(",");
		for (Field f : fieldSubFields) {
			String fieldName = f.getName();
			String dbFieldName = StringUtils.camelToUnderline(fieldName);

			String fieldAlias = getFieldAlias(fieldAliasMap, atomicInteger, leftJoinFieldName + "." + fieldName);

			selectFields.append(fieldTableAlias + "." + dbFieldName + " as " + fieldAlias);
		}

		return new Tuple(new Object[] { selectFields.join1(), fieldAliasMap });
	}

	private String getTableAlias(Map<String, String> tableAliasMap, AtomicInteger atomicInteger, String tableName)
	{
		String alias = (String)tableAliasMap.get(tableName);
		if (alias == null) {
			int number = atomicInteger.getAndIncrement();
			alias = "t" + number;
			tableAliasMap.put(tableName, alias);
		}
		return alias;
	}

	private String getFieldAlias(Map<String, String> fieldAliasMap, AtomicInteger atomicInteger, String fieldName) {
		String alias = (String)fieldAliasMap.get(fieldName);
		if (alias == null) {
			int number = atomicInteger.getAndIncrement();
			alias = "f" + number;
			fieldAliasMap.put(fieldName, alias);
		}
		return alias;
	}

	public String getDeleteSQL()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ");
		sql.append("" + this.tableName + "");
		sql.append(" WHERE ");
		sql.append(" id ");
		sql.append("=:id");
		return sql.toString();
	}

	public String getDeleteSQL(String[] where) throws FieldNotFoundException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ");
		sql.append("" + this.tableName + "");

		if ((where != null) && (where.length > 0)) {
			sql.append(toWhereSQL(where));
		} else {
			sql.append(" WHERE ");
			sql.append(" id ");
			sql.append("=:id");
		}

		return sql.toString();
	}

	private Field getEntityField(String fieldName) throws FieldNotFoundException
	{
		if ((!CollectionUtil.isEmpty(this.entityFieldNames)) && (fieldName != null)) {
			int index = CollectionUtil.indexOf(this.entityFieldNames, fieldName);
			if (index != -1) {
				return this.entityFields[index];
			}
		}
		throw new FieldNotFoundException(fieldName, this.clazz);
	}

	private String getDbFieldName(String w, Map<String, String> tableAliasMap) throws FieldNotFoundException {
		String[] sepArr = w.split("\\.");
		if (sepArr.length == 0) {
			throw new FieldNotFoundException(w, this.clazz);
		}
		if (sepArr.length == 1) {
			String fieldName = sepArr[0];
			return getDbFieldName(fieldName);
		}
		if (sepArr.length == 2)
		{
			String entityFieldName = sepArr[0];
			String entitySubFieldName = sepArr[1];
			Field entityField = getEntityField(entityFieldName);

			Class fieldClazz = entityField.getType();
			Field[] fieldSubFields = ReflectUtils.getObjectFields(fieldClazz, 1);

			String dbSubFieldName = getDbFieldName(entitySubFieldName, fieldSubFields);

			String tableAlias = (String)tableAliasMap.get(entityFieldName);
			return tableAlias + "." + dbSubFieldName;
		}

		throw new FieldNotFoundException(w, this.clazz);
	}

	private String getDbFieldName(String fieldName, Field[] fieldSubFields) throws FieldNotFoundException
	{
		if (!CollectionUtil.isEmpty(fieldSubFields)) {
			for (Field fieldSub : fieldSubFields) {
				String subFieldName = fieldSub.getName();
				if (subFieldName.equals(fieldName)) {
					return StringUtils.camelToUnderline(subFieldName);
				}
			}
		}
		throw new FieldNotFoundException(fieldName, this.clazz);
	}

	private String getDbFieldName(String fieldName) throws FieldNotFoundException {
		for (int i = 0; i < this.basicFieldNames.length; i++) {
			String fName = this.basicFieldNames[i];
			if (fieldName.equals(fName)) {
				return this.dbBasicFieldNames[i];
			}
		}
		throw new FieldNotFoundException(fieldName, this.clazz);
	}

	private String toWhereSQL(String[] where, Map<String, String> tableAliasMap) throws FieldNotFoundException {
		StringBuffer sql = new StringBuffer();

		if ((where != null) && (where.length > 0))
		{
			sql.append(" WHERE ");

			for (int i = 0; i < where.length; i++) {
				String w = where[i];
				String dbFieldName = getDbFieldName(w, tableAliasMap);

				if (dbFieldName == null) {
					logger.error("can not get the dbFieldName of {}", w);
					return null;
				}

				sql.append(" " + dbFieldName + " ");
				sql.append("=:" + w);

				if (i < where.length - 1) {
					sql.append(" and ");
				}
			}
		}

		return sql.toString();
	}

	public String toWhereSQL(String[] where) throws FieldNotFoundException
	{
		StringBuffer sql = new StringBuffer();

		if ((where != null) && (where.length > 0))
		{
			sql.append(" WHERE ");

			for (int i = 0; i < where.length; i++) {
				String w = where[i];
				String dbFieldName = getDbFieldName(w);

				if (dbFieldName == null) {
					logger.error("can not get the dbFieldName of {}", w);
					return null;
				}

				sql.append(" " + dbFieldName + " ");
				sql.append("=:" + w);

				if (i < where.length - 1) {
					sql.append(" and ");
				}
			}
		}

		return sql.toString();
	}

	public String[] toStringArray(Collection<String> stringCollection)
	{
		if ((stringCollection != null) && (!stringCollection.isEmpty())) {
			String[] whereArray = new String[stringCollection.size()];
			int i = 0;
			for (String s : stringCollection) {
				whereArray[i] = s;
				i++;
			}
			return whereArray;
		}
		return null;
	}

	public void setSchemaPrefix(String schemaPrefix) {
		this.schemaPrefix = schemaPrefix;
	}
	
}