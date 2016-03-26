package com.coolpeng.framework.db;

import com.alibaba.fastjson.JSON;
import com.coolpeng.framework.db.annotation.FieldDef;
import com.coolpeng.framework.utils.ReflectUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 栾海鹏 on 2016/3/26.
 */
public class TMSBeanPropertySqlParameterSource extends AbstractSqlParameterSource {

    private final BeanWrapper beanWrapper;

    private String[] propertyNames;

    private Class clazz ;

    private List<Field> fieldDefList;
    /**
     * Create a new BeanPropertySqlParameterSource for the given bean.
     * @param object the bean instance to wrap
     */
    public TMSBeanPropertySqlParameterSource(Object object) {
        this.clazz = object.getClass();
        this.beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        //3、组装所有的标注的特殊字段
        this.fieldDefList = ReflectUtils.getClassFieldsWithAnnotation(this.clazz, FieldDef.class);
    }


    @Override
    public boolean hasValue(String paramName) {

        Field field = getFieldByParamName(paramName);
        //如果是JSON字段
        if (ReflectUtils.isJSONColumn(field)){
            return true;
        }

        boolean isOk =  this.beanWrapper.isReadableProperty(paramName);
        return isOk;
    }

    private Field getFieldByParamName(String paramName){
        List<Field> list = this.fieldDefList;
        for (Field field:list){
            if (paramName.equals(field.getName())){
                return field;
            }
        }
        return null;
    }


    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        try {

            Object value = this.beanWrapper.getPropertyValue(paramName);

            Field field = getFieldByParamName(paramName);
            //如果是JSON字段
            if (ReflectUtils.isJSONColumn(field)){
                return JSON.toJSONString(value);
            }

            return value;
        }
        catch (NotReadablePropertyException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Provide access to the property names of the wrapped bean.
     * Uses support provided in the {@link org.springframework.beans.PropertyAccessor} interface.
     * @return an array containing all the known property names
     */
    public String[] getReadablePropertyNames() {
        if (this.propertyNames == null) {
            List<String> names = new ArrayList<String>();
            PropertyDescriptor[] props = this.beanWrapper.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if (this.beanWrapper.isReadableProperty(pd.getName())) {
                    names.add(pd.getName());
                }
            }
            this.propertyNames = names.toArray(new String[names.size()]);
        }
        return this.propertyNames;
    }

    /**
     * Derives a default SQL type from the corresponding property type.
     * @see org.springframework.jdbc.core.StatementCreatorUtils#javaTypeToSqlParameterType
     */
    @Override
    public int getSqlType(String paramName) {


        Field field = getFieldByParamName(paramName);
        //如果是JSON字段
        if (ReflectUtils.isJSONColumn(field)){
            return Types.VARCHAR;

        }




        int sqlType = super.getSqlType(paramName);
        if (sqlType != TYPE_UNKNOWN) {
            return sqlType;
        }
        Class<?> propType = this.beanWrapper.getPropertyType(paramName);
        return StatementCreatorUtils.javaTypeToSqlParameterType(propType);
    }

}
