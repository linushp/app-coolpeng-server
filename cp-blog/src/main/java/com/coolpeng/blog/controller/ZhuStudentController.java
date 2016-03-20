package com.coolpeng.blog.controller;

import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ZhuStuScore;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/19.
 */
@Controller
public class ZhuStudentController {


    @RequestMapping("/score")
    public ModelAndView getPostList(@RequestParam(value = "stuNumber", required = false, defaultValue = "") String stuNumber,
                                    @RequestParam(value = "stuName", required = false, defaultValue = "") String stuName) throws FieldNotFoundException {

        String status = "no_params";


        Map<String, Object> modelMap = new HashMap<>();

        if (StringUtils.isBlank(stuName) && StringUtils.isBlank(stuNumber)) {

        } else {
            ZhuStuScore score = null;
            if (StringUtils.isNotBlank(stuNumber)) {
                Map<String, Object> params = new HashMap<>();
                params.put("column1", stuNumber.trim());
                score = ZhuStuScore.DAO.queryForObject(params);
            }

            if (score == null && StringUtils.isNotBlank(stuName)) {
                Map<String, Object> params = new HashMap<>();
                params.put("column2", stuName.trim());
                score = ZhuStuScore.DAO.queryForObject(params);
            }

            if (score == null) {
                status = "no_score";
            } else {
                status = "ok";
                modelMap.put("score", score);
                //
                modelMap.put("stuNumber", stuNumber);
                modelMap.put("stuName", stuName);

                int sum = 0;
                for (int i = 3; i <= 20; i++) {
                    int score0 = 0;
                    try {
                        Object propObj = PropertyUtils.getProperty(score, "column" + i);
                        String value = propObj.toString().replace("%", "");
                        score0 = NumberUtils.toInt(value, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sum += score0;
                }
                modelMap.put("scoreSum", sum);
            }

        }


        modelMap.put("status", status);

        return new ModelAndView("score/index", modelMap);
    }

}
