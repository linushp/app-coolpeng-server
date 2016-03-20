package com.coolpeng.framework.exception;

/**
 * 字段在类中没有找�?
 * 
 * @author luan
 *
 */
public class FieldNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private Class<? extends Object> clazz;

	public FieldNotFoundException(String fieldName,
			Class<? extends Object> clazz) {
		this.fieldName = fieldName;
		this.clazz = clazz;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Object> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return "FieldNotFoundException [fieldName=" + fieldName + ", clazz="
				+ clazz.getName() + "]";
	}

}
