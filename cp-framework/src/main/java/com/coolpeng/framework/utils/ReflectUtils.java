package com.coolpeng.framework.utils;

import com.coolpeng.framework.db.BaseEntity;
import com.coolpeng.framework.exception.FieldNotFoundException;
import org.apache.commons.collections.map.MultiKeyMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectUtils {

    /**
     * 只过滤出基本数据类型的字
     */
    public static final int FIELD_FILTER_ONLY_BASIC = 1;

    /**
     * 只过滤出是实体类的字
     */
    public static final int FIELD_FILTER_ONLY_ENTITY = 2;

    /**
     * 不进行过过滤
     */
    public static final int FIELD_FILTER_ALL = 3;

    private static final String[] BASIC_FIELD_TYPE_ARRAY = new String[]{"java.lang.String", "int", "long", "char", "boolean", "java.util.Date", "float", "double", "byte", "short",
            "java.lang.Integer", "java.lang.Long", "java.lang.Character", "java.lang.Boolean", "java.lang.Float", "java.lang.Double", "java.lang.Byte", "java.lang.Short"};

    private static final MultiKeyMap getObjectFieldsCache = new MultiKeyMap();
    /**
     * 获取�?个类中具有get方法的字�?
     *
     * @param clazz
     * @param fieldFilter 是否只显示基本属性字�?
     * @return
     */
    public static Field[] getObjectFields(Class<?> clazz,final int fieldFilter) {

        //先从缓存中查询
        Object cacheField = getObjectFieldsCache.get(clazz, fieldFilter);
        if (cacheField != null) {
            return (Field[]) cacheField;
        }

        //没有缓存的查询一遍
        Field[] fieldArray = getObjectFieldsNoCache(clazz,fieldFilter);

        //放在缓存里
        getObjectFieldsCache.put(clazz, fieldFilter, fieldArray);

        return fieldArray;
    }

    private static Field[] getObjectFieldsNoCache(Class<?> clazz,final int fieldFilter){
        Method[] method = clazz.getMethods();
        List<Field> list = new ArrayList<Field>();

        // 获取有get方法的字段
        Field[] fields = toArray(getClassFields(clazz, true));// clazz.getDeclaredFields();
        Map<String, Field> staticFields = getStaticFields(clazz);
        for (int j = 0; j < fields.length; j++) {
            Field f = fields[j];
            f.setAccessible(true);

            // 当只显示基本属性时，过滤掉非基本属性
            if (FIELD_FILTER_ONLY_BASIC == fieldFilter && !isBasicField(f)) {
                continue;
            }

            // 当只要实体字段属性时，过滤掉非实体字段的属性
            if (FIELD_FILTER_ONLY_ENTITY == fieldFilter && !isEntityField(f)) {
                continue;
            }

            String fieldName = f.getName();

            // 过滤掉静态属性
            if (staticFields.containsKey(fieldName)) {
                continue;
            }

            // 找到有get方法的属性
            String m = "get" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
            for (int i = 0; i < method.length; i++) {
                if (method[i].getName().endsWith(m)) {
                    list.add(f);
                }
            }
        }

        // 转换成数组
        Field[] fieldArray = toArray(list);

        return fieldArray;
    }

    public static Field[] toArray(Collection<Field> list) {
        Field[] fieldArray = new Field[list.size()];
        int i = 0;
        for (Field f : list) {
            fieldArray[i] = f;
            i++;
        }
        return fieldArray;
    }

    /**
     * 获取�?个类的所有静态属�?
     *
     * @param clazz
     * @return key为属性名
     */
    private static Map<String, Field> getStaticFields(Class<?> clazz) {
        Field[] staticFields = clazz.getFields();
        return toMappedFields(staticFields);
    }

    /**
     * 判断�?个属性的数据类型是不是BaseEntity的子�?
     *
     * @param field
     * @return
     */
    private static boolean isEntityField(Field field) {
        Class<?> fieldType = field.getType();
        return BaseEntity.class.isAssignableFrom(fieldType);
    }

//    public static Class<?> getFieldType(Field field) throws ClassNotFoundException {
//        Type fieldType = field.getGenericType();
//        String typeName = fieldType.getTypeName();
//        Class<?> fieldClazz = Class.forName(typeName);
//        return fieldClazz;
//    }

    /**
     * 判断�?个属性是不是基本数据类型 。除了八种基本数据类型外，String和Date也被认为是基本数据类�?
     *
     * @param field
     * @return
     */
    private static boolean isBasicField(Field field) {

        Class<?> fieldType = field.getType();
        String fieldTypeName = fieldType.getName();
        for (String basicType : BASIC_FIELD_TYPE_ARRAY) {
            if (basicType.equals(fieldTypeName)) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, Field> getMappedObjectFields(Class<?> clazz) {
        // 不过�?
        Field[] fields = getObjectFields(clazz, FIELD_FILTER_ALL);
        return toMappedFields(fields);
    }

    private static Map<String, Field> toMappedFields(Field[] fields) {

        Map<String, Field> map = new HashMap<String, Field>();

        if (fields == null) {
            return map;
        }

        for (Field f : fields) {
            map.put(f.getName(), f);
        }

        return map;
    }

    public static <T> Field getObjectFieldByName(Collection<T> collections, String fieldName) throws FieldNotFoundException {

        // 虽然是个集合但只�?要使用其中一个元�?
        for (Object t : collections) {

            Class<?> clazz = t.getClass();

            return getObjectFieldByName(clazz, fieldName);

        }
        return null;
    }

    public static <T> Field getObjectFieldByName(Class<?> clazz, String fieldName) throws FieldNotFoundException {

        Map<String, Field> fields = ReflectUtils.getMappedObjectFields(clazz);
        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName, clazz);
        }
        return field;

    }

    /**
     * 获取类实例的属�?��??
     *
     * @param clazz              类名
     * @param includeParentClass 是否包括父类的属性�??
     * @return 类名.属�?�名=属�?�类�?
     */
    public static List<Field> getClassFields(Class<?> clazz, boolean includeParentClass) {
        List<Field> fieldList = new ArrayList<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldList.add(field);
        }
        if (includeParentClass) {
            Class superClazz0 = clazz.getSuperclass();
            String className = superClazz0.getName();
            if (BaseEntity.class.getName().equals(className)) {
                fieldList.addAll(getClassFields(superClazz0, false));
            } else {
                fieldList.addAll(getClassFields(superClazz0, true));
            }

        }

        return fieldList;
    }

    public static boolean isTypeString(Field f) {
        return "java.lang.String".equals(f.getType().getName());
    }

    public static <T extends Annotation>  List<Field> getClassFieldsWithAnnotation(Class<?> clazz, Class<T> annotationClazz) {
        Field[] fields = ReflectUtils.getObjectFieldsNoCache(clazz, FIELD_FILTER_ALL);
        List<Field> result = new ArrayList<>();
        if (fields != null) {
            for (Field field : fields) {
                T annotation = field.getAnnotation(annotationClazz);
                field.setAccessible(true);
                if (annotation != null) {
                    result.add(field);
                }
            }
        }
        return result;

    }
}
