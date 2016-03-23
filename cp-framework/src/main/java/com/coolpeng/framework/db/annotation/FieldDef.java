package com.coolpeng.framework.db.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldDef {
	
	public static String DBTYPE_LONGTEXT = "longtext";

	/**
	 * 为了生存建表语句
	 * @return
	 */
	public String dbType() default "";


	/**
	 * 配置了此字段
	 * @return
	 */
	Class<?>[] jsonColumn() default {};
}
