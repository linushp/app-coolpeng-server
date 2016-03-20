package com.coolpeng.framework.db;

import java.util.HashMap;
import java.util.Map;

public class QueryCondition
{
    private Map<String, Object> params = new HashMap();
    private String orderField;
    private String orderType;

    public QueryCondition()
    {
    }

    public QueryCondition(String k, Object v)
    {
        addEqualCondition(k, v);
    }

    public void addEqualCondition(String k, Object v) {
        this.params.put(k, v);
    }

    public void setOrderBy(String orderField, String orderType) {
        this.orderField = orderField;
        this.orderType = orderType;
    }

    public void setOrderDesc(String orderField) {
        setOrderBy(orderField, "desc");
    }

    public void setOrderAsc(String orderField) {
        setOrderBy(orderField, "asc");
    }

    public Map<String, Object> getParams()
    {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getOrderField() {
        return this.orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}