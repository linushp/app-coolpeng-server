package com.coolpeng.framework.db;

import com.coolpeng.framework.utils.ReflectUtils;
import com.coolpeng.framework.utils.StringUtils;

import java.lang.reflect.Field;

public class EntityTableUtil {

	public static String toCreateTable(Class<?> clazz) {

		TemplateSQL t = new TemplateSQL(clazz);

		String tableName = t.getTableName(clazz);

		System.out.println("DROP TABLE IF EXISTS `" + tableName + "`;");

		System.out.println("CREATE TABLE `" + tableName + "` (");

		Field[] fields = ReflectUtils.getObjectFields(clazz, ReflectUtils.FIELD_FILTER_ALL);

		System.out.println("  `id` int(11) NOT NULL AUTO_INCREMENT,");
		for (Field f : fields) {

			String name = f.getName();
			String dnFieldName = StringUtils.camelToUnderline(name);
			Class<?> type = f.getType();
			String dataType = type.getSimpleName();

			if ("id".equals(dnFieldName)) {
				// do nothing
			} else if ("String".equals(dataType)) {

				f.setAccessible(true);
	
//				FieldDef fieldDef = f.getDeclaredAnnotation(FieldDef.class);
//				if (fieldDef != null && FieldDef.DBTYPE_LONGTEXT.equals(fieldDef.dbType())) {
//					System.out.println("  `" + dnFieldName + "` longtext,");
//				} else {
//					System.out.println("  `" + dnFieldName + "` varchar(256) DEFAULT NULL,");
//				}
				
			} else if ("int".equals(dataType)) {
				System.out.println("  `" + dnFieldName + "` int(11) DEFAULT NULL,");
			}

		}

		System.out.println("  PRIMARY KEY (`id`)");
		System.out.println(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
		
		System.out.println("\n\n");
		
		return null;
	}
}
