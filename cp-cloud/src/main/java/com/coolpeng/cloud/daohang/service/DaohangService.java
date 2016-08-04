package com.coolpeng.cloud.daohang.service;

import com.coolpeng.cloud.daohang.entity.DhCategory;
import com.coolpeng.cloud.daohang.entity.DhItem;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by luanhaipeng on 16/8/3.
 */
@Service
public class DaohangService {

    private static final String UN_CATEGORY_ID = "-1";

    private static final String FIELD_CATEGORY_ID = "categoryId";

    private static final String FIELD_TYPE = "type";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public DhCategory insertOrUpdateDhCategory(DhCategory category) throws UpdateErrorException {
        DhCategory.DAO.insertOrUpdate(category);
        return category;
    }


    public DhItem insertOrUpdateDhItem(DhItem dhItem) throws UpdateErrorException {
        DhItem.DAO.insertOrUpdate(dhItem);
        return dhItem;
    }


    public void deleteDhCategory(String categoryId) throws UpdateErrorException {
        DhCategory.DAO.deleteById(categoryId);
        Map<String, Object> fieldsAndValue = new HashMap<>();
        fieldsAndValue.put(FIELD_CATEGORY_ID, UN_CATEGORY_ID);
        DhItem.DAO.batchUpdateFields(FIELD_CATEGORY_ID, categoryId, fieldsAndValue);
    }


    public void deleteDhItem(String id) {
        DhItem.DAO.deleteById(id);
    }


    public List<DhCategory> getCategoryList(Integer type) throws IllegalAccessException, FieldNotFoundException {

        List<DhCategory> categoryList = DhCategory.DAO.findBy(FIELD_TYPE, type);
        List<DhItem> itemList = DhItem.DAO.findBy(FIELD_TYPE, type);


        Map<Object, List<DhItem>> itemMap = CollectionUtil.groupBy(itemList, FIELD_CATEGORY_ID);

        for (DhCategory dhCategory : categoryList) {
            String id = dhCategory.getId();
            List<DhItem> items = itemMap.get(id);
            items = sortDnItemByOrder(items);
            dhCategory.setItems(items);
        }


        //未分类
        List<DhItem> others = itemMap.get(UN_CATEGORY_ID);
        if (!CollectionUtil.isEmpty(others)) {
            DhCategory otherCategory = new DhCategory();
            otherCategory.setText("未分类");
            otherCategory.setItems(others);
            otherCategory.setOrder(0);
            otherCategory.setType(type);
            otherCategory.setId(UN_CATEGORY_ID);
            categoryList.add(otherCategory);
        }

        return sortCategoryByOrder(categoryList);

    }


    private List<DhCategory> sortCategoryByOrder(List<DhCategory> items) {
        if (items == null) {
            return new ArrayList<>();
        }

        Collections.sort(items, new Comparator<DhCategory>() {
            @Override
            public int compare(DhCategory o1, DhCategory o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });

        return items;
    }

    private List<DhItem> sortDnItemByOrder(List<DhItem> items) {
        if (items == null) {
            return new ArrayList<>();
        }

        Collections.sort(items, new Comparator<DhItem>() {
            @Override
            public int compare(DhItem o1, DhItem o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });

        return items;
    }


}
