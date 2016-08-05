package com.coolpeng.cloud.daohang.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.cloud.common.base.RestBaseController;
import com.coolpeng.cloud.daohang.entity.DhCategory;
import com.coolpeng.cloud.daohang.entity.DhItem;
import com.coolpeng.cloud.daohang.service.DaohangService;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/8/3.
 */
@Controller
@RequestMapping(value = "/cloud/daohang", produces = "application/json; charset=UTF-8")
public class DaohangController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DaohangService daohangService;


    @ResponseBody
    @RequestMapping({"/insertOrUpdateDhCategory"})
    public TMSResponse insertOrUpdateDhCategory(@RequestBody JSONObject jsonObject) throws UpdateErrorException, TMSMsgException {
        assertIsAdmin(jsonObject);
        DhCategory category = jsonObject.getObject("DhCategory", DhCategory.class);
        daohangService.insertOrUpdateDhCategory(category);
        return TMSResponse.success(category);

    }

    @ResponseBody
    @RequestMapping({"/insertOrUpdateDhItem"})
    public TMSResponse insertOrUpdateDhItem(@RequestBody JSONObject jsonObject) throws UpdateErrorException, TMSMsgException {
        assertIsAdmin(jsonObject);
        DhItem item = jsonObject.getObject("DhItem", DhItem.class);

        daohangService.insertOrUpdateDhItem(item);
        return TMSResponse.success(item);
    }

    @ResponseBody
    @RequestMapping({"/insertOrUpdateDhItem1"})
    public TMSResponse insertOrUpdateDhItem1() throws UpdateErrorException, TMSMsgException {

        DhItem item = new DhItem();
        item.setCategoryId("1");

        daohangService.insertOrUpdateDhItem(item);
        return TMSResponse.success(item);
    }


    @ResponseBody
    @RequestMapping({"/deleteDhCategory"})
    public TMSResponse deleteDhCategory(@RequestBody JSONObject jsonObject) throws UpdateErrorException {
        String id = jsonObject.getString("id");
        daohangService.deleteDhCategory(id);
        return TMSResponse.success();
    }


    @ResponseBody
    @RequestMapping({"/deleteDhItem"})
    public TMSResponse deleteDhItem(@RequestBody JSONObject jsonObject){
        String id = jsonObject.getString("id");
        daohangService.deleteDhItem(id);
        return TMSResponse.success();
    }


    @ResponseBody
    @RequestMapping({"/getCategoryList"})
    public TMSResponse getCategoryList(@RequestBody JSONObject jsonObject) throws IllegalAccessException, FieldNotFoundException {

        Integer type = jsonObject.getInteger("type");

        List<DhCategory> list = daohangService.getCategoryList(type);
        return TMSResponse.success(list);
    }







}
