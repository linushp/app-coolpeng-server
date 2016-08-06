package com.coolpeng.framework.db;

import com.coolpeng.framework.event.TMSEventBus;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.ServiceUtils;
import com.coolpeng.framework.utils.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;

//import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class SimpleDAO<T> {
    private static Logger logger = LoggerFactory.getLogger(SimpleDAO.class);
    private TemplateSQL sqlTemplate;
    private Class<T> clazz;


    public SimpleDAO(Class<T> clazz) {
        this.sqlTemplate = new TemplateSQL(clazz);
        this.clazz = clazz;
    }

    public List<T> findAll() {
        String sql = this.sqlTemplate.getSelectSQL();
        return queryForList(sql, null);
    }

    public List<T> findBy(String key,Object value) throws FieldNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put(key,value);
        return queryForList(params);
    }

    public List<T> findBy(String key1,Object value1,String key2,Object value2) throws FieldNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put(key1,value1);
        params.put(key2,value2);
        return queryForList(params);
    }


    public T queryForObject(String sql, Map<String, Object> params) {
        MapSqlParameterSource sps = new MapSqlParameterSource(params);
        try {
            return (T) getJdbcTemplate().queryForObject(sql, sps, new TMSBeanRowMapper<>(this.clazz));
        } catch (EmptyResultDataAccessException e1) {
            logger.info("no result , params is {}", params);
        } catch (Exception e) {
            logger.error("no result , params is {}", params, e);
        }
        return null;
    }

    public int count(Map<String, Object> params)
            throws FieldNotFoundException {
        MapSqlParameterSource sps = new MapSqlParameterSource(params);
        String sql = this.sqlTemplate.getCountSQL(params.keySet());
        Integer count = (Integer) getJdbcTemplate().queryForObject(sql, sps, Integer.class);
        return count.intValue();
    }

    public T queryForObject(Map<String, Object> params)
            throws FieldNotFoundException {
        String sql = null;
        if (params == null)
            sql = this.sqlTemplate.getSelectSQL();
        else {
            sql = this.sqlTemplate.getSelectSQL(params.keySet());
        }

        return queryForObject(sql, params);
    }


    public T queryById(String id)
            throws FieldNotFoundException, ParameterErrorException {
        if ((id == null) || (id.isEmpty())) {
            throw new ParameterErrorException("id 不能为空");
        }
        Map params = new HashMap();
        params.put("id", id);
        return (T) queryForObject(params);
    }

    public T queryForObject(String id) throws ParameterErrorException, FieldNotFoundException {
        return queryById(id);
    }

    public T queryForObject(String id, String[] leftJoinFieldNameArray)
            throws ClassNotFoundException, FieldNotFoundException {
        Map params = new HashMap();
        params.put("id", id);

        QueryCondition qc = new QueryCondition();
        qc.setParams(params);

        List<T> result = queryForList(qc, leftJoinFieldNameArray);
        if (CollectionUtil.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    public List<T> queryForList(String[] leftJoinFieldNameArray)
            throws FieldNotFoundException, ClassNotFoundException {
        return queryForList(null, leftJoinFieldNameArray);
    }

    public List<T> queryForList(QueryCondition qc, String[] leftJoinFieldNameArray)
            throws FieldNotFoundException, ClassNotFoundException {
        Set where = null;
        MapSqlParameterSource sps = null;
        if ((qc != null) && (qc.getParams() != null)) {
            where = qc.getParams().keySet();
            sps = new MapSqlParameterSource(qc.getParams());
        }

        Tuple t = this.sqlTemplate.getLeftJoinSelectSQL(where, qc, -1, -1, leftJoinFieldNameArray);
        String sql = (String) t.first();

        Map fieldAliasMap = (Map) t.third();

        List result = null;

        if (sps != null)
            result = getJdbcTemplate().query(sql, sps, new TMSBeanRowMapper(this.clazz, fieldAliasMap));
        else {
            result = getJdbcTemplate().query(sql, new TMSBeanRowMapper(this.clazz, fieldAliasMap));
        }

        logger.debug(sql);
        logger.debug(fieldAliasMap.toString());
        return result;
    }

    public List<T> queryForList(Map<String, Object> params)
            throws FieldNotFoundException {
        String sql = null;
        if (CollectionUtil.isEmpty(params))
            sql = this.sqlTemplate.getSelectSQL();
        else {
            sql = this.sqlTemplate.getSelectSQL(params.keySet());
        }

        return queryForList(sql, params);
    }

    public List<T> queryForList(String sql, Map<String, Object> params) {
        try {
            if ((params != null) && (!params.isEmpty())) {
                MapSqlParameterSource sps = new MapSqlParameterSource(params);
                return getJdbcTemplate().query(sql, sps, new TMSBeanRowMapper(this.clazz));
            }
            return getJdbcTemplate().query(sql, new TMSBeanRowMapper(this.clazz));
        } catch (EmptyResultDataAccessException e) {
            logger.info("no result , params is {}", params);
        }
        return new ArrayList<>();
    }

    public List<T> queryByNamingSQL(String namingSqlID, Map<String, Object> params) {
        String sql = NamingSQL.getNamingSqlById(namingSqlID);

        if (sql == null) {
            logger.info("error to get naming sql , id = {} ", namingSqlID);
            return null;
        }

        return queryForList(sql, params);
    }


    public PageResult<T> queryForPage(int pageNumber, int pageSize)
            throws ClassNotFoundException, FieldNotFoundException {
        QueryCondition qc = new QueryCondition();
        return queryForPage(qc, pageNumber, pageSize, new String[0]);
    }

    public PageResult<T> queryForPage(QueryCondition qc, int pageNumber, int pageSize, String[] leftJoinFieldNameArray)
            throws ClassNotFoundException, FieldNotFoundException {
        Set where = null;
        MapSqlParameterSource sps = null;
        if ((qc != null) && (qc.getParams() != null)) {
            where = qc.getParams().keySet();
            sps = new MapSqlParameterSource(qc.getParams());
        }

        Tuple t = this.sqlTemplate.getLeftJoinSelectSQL(where, qc, pageNumber, pageSize, leftJoinFieldNameArray);

        String countSql = (String) t.forth();
        Integer totalCount = (Integer) getJdbcTemplate().queryForObject(countSql, sps, Integer.class);

        String sql = (String) t.first();

        Map fieldAliasMap = (Map) t.third();

        List pageData = null;

        if (sps != null)
            pageData = getJdbcTemplate().query(sql, sps, new TMSBeanRowMapper(this.clazz, fieldAliasMap));
        else {
            pageData = getJdbcTemplate().query(sql, new TMSBeanRowMapper(this.clazz, fieldAliasMap));
        }

        logger.debug(sql);
        logger.debug(fieldAliasMap.toString());

        return new PageResult(totalCount.intValue(), pageSize, pageNumber, pageData);
    }

    public int delete(String id) {
        return deleteById(id);
    }

    public int deleteById(String id){
        String sql = this.sqlTemplate.getDeleteSQL();
        MapSqlParameterSource sps = new MapSqlParameterSource("id", id);
        return getJdbcTemplate().update(sql, sps);
    }

    public int delete(Map<String, Object> params)
            throws FieldNotFoundException {
        Set where = params.keySet();
        String[] whereArray = this.sqlTemplate.toStringArray(where);
        String sql = this.sqlTemplate.getDeleteSQL(whereArray);
        MapSqlParameterSource sps = new MapSqlParameterSource(params);
        return getJdbcTemplate().update(sql, sps);
    }

    public int save(T entity) {
        return insert(entity);
    }


    public int insertOrUpdate(T entity)
            throws UpdateErrorException {
        if ((entity instanceof BaseEntity)) {
            BaseEntity object1 = (BaseEntity) entity;
            String id = object1.getId();
            if ((id != null) && (id.length() > 0)) {
                int updateResult = update(entity);
                return updateResult;
            }
            return insert(entity);
        }

        logger.error("entity is not BaseEntity");

        return -1;
    }

    public int deleteAndInsert(T entity, Map<String, Object> params)
            throws FieldNotFoundException {
        delete(params);
        return insert(entity);
    }

    public int deleteAndInsert(T entity, String entityId)
            throws FieldNotFoundException {
        Map params = new HashMap();
        params.put("id", entityId);
        delete(params);
        return insert(entity);
    }


    //TODO 数据插入或更新时候需要将特殊字段转义
    public int insert(T entity) {
        if ((entity instanceof BaseEntity)) {
            BaseEntity entity1 = (BaseEntity) entity;
            entity1.setUpdateTime(DateUtil.currentTimeFormat());
            entity1.setCreateTime(DateUtil.currentTimeFormat());
            entity1.setId(null);
        }

        TMSEventBus.sendEvent(new SimpleDAOEvent(SimpleDAOEventEnum.beforeInsert, this.clazz, entity));

        String sql = this.sqlTemplate.getInsertSQL();
        SqlParameterSource sps = new TMSBeanPropertySqlParameterSource(entity);
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int insertResult = getJdbcTemplate().update(sql, sps, generatedKeyHolder);

        Number key = generatedKeyHolder.getKey();

        int id = key.intValue();
        BaseEntity entity1 = (BaseEntity) entity;
        entity1.setId("" + id);

        TMSEventBus.sendEvent(new SimpleDAOEvent(SimpleDAOEventEnum.afterInsert, this.clazz, entity));

        return insertResult;
    }


    public int update(T entity)
            throws UpdateErrorException {

        if ((entity instanceof BaseEntity)) {
            BaseEntity entity1 = (BaseEntity) entity;
            entity1.setUpdateTime(DateUtil.currentTimeFormat());
            String id = entity1.getId();
            if ((id == null) || (id.isEmpty())) {
                throw new UpdateErrorException(entity);
            }
        }

        TMSEventBus.sendEvent(new SimpleDAOEvent(SimpleDAOEventEnum.beforeUpdate, this.clazz, entity));

        String sql = this.sqlTemplate.getUpdateSQL();
        SqlParameterSource sps = new TMSBeanPropertySqlParameterSource(entity);
        int result = getJdbcTemplate().update(sql, sps);
        TMSEventBus.sendEvent(new SimpleDAOEvent(SimpleDAOEventEnum.afterUpdate, this.clazz, entity));
        return result;
    }

    public int updateFields(String entityId, Map<String, Object> fieldsAndValue)
            throws UpdateErrorException {
        fieldsAndValue.remove("id");

        if (fieldsAndValue.isEmpty()) {
            return -1;
        }

        TMSEventBus.sendEvent(new SimpleDAOEvent(SimpleDAOEventEnum.beforeUpdateFields, this.clazz, fieldsAndValue));

        String sql = this.sqlTemplate.getUpdateSQL(fieldsAndValue.keySet(), null);

        fieldsAndValue.put("id", entityId);

        SqlParameterSource sps = new TMSMapSqlParameterSource(fieldsAndValue, this.clazz);

        int result = getJdbcTemplate().update(sql, sps);

        TMSEventBus.sendEvent(new SimpleDAOEvent(SimpleDAOEventEnum.afterUpdateFields, this.clazz, fieldsAndValue));

        return result;
    }

    public int batchUpdateFields(String whereKey, String whereValue, Map<String, Object> fieldsAndValue)
            throws UpdateErrorException {
        fieldsAndValue.remove("id");

        if (fieldsAndValue.isEmpty()) {
            return -1;
        }

        String sql = this.sqlTemplate.getUpdateSQL(fieldsAndValue.keySet(), whereKey);

        fieldsAndValue.put(whereKey + "Where", whereValue);

        SqlParameterSource sps = new TMSMapSqlParameterSource(fieldsAndValue, this.clazz);

        return getJdbcTemplate().update(sql, sps);
    }


    private static NamedParameterJdbcTemplate getJdbcTemplate() {
        NamedParameterJdbcTemplate jdbcTemplate = ServiceUtils.getNamedParameterJdbcTemplate();
        return jdbcTemplate;
    }

}