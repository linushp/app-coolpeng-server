package com.coolpeng.framework.db;

import com.alibaba.fastjson.JSON;
import com.coolpeng.framework.db.annotation.FieldDef;
import com.coolpeng.framework.utils.ReflectUtils;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 栾海鹏 on 2016/3/26.
 */
public class TMSMapSqlParameterSource extends AbstractSqlParameterSource {

    private Map<String, Object> fieldsAndValue;

    private  Class clazz;

    private List<Field> fieldDefList;

    public TMSMapSqlParameterSource(Map<String, Object> fieldsAndValue, Class clazz) {
        this.fieldsAndValue = fieldsAndValue;
        if (this.fieldsAndValue==null){
            this.fieldsAndValue = new HashMap<>();
        }
        this.clazz = clazz;
        this.fieldDefList = ReflectUtils.getClassFieldsWithAnnotation(this.clazz, FieldDef.class);

    }


    @Override
    public boolean hasValue(String paramName) {
        Object value = this.fieldsAndValue.get(paramName);
        if (value == null) {
            return false;
        }
        return true;
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        Object value = this.fieldsAndValue.get(paramName);
        if (value == null) {
            return null;
        }

        if (value instanceof String){
            return value;
        }


        if (value instanceof Integer || value instanceof Boolean || value instanceof Long || value instanceof Character || value instanceof Double || value instanceof Float){
            return value;
        }


        Field field = getFieldByParamName(paramName);
        if (ReflectUtils.isJSONColumn(field)){
             return JSON.toJSONString(value);
        }else {
            return value;
        }
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

}
