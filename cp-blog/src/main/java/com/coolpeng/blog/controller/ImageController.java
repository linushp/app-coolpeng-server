package com.coolpeng.blog.controller;

import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ImageKeyword;
import com.coolpeng.framework.db.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/19.
 */
@Controller
public class ImageController {

    /**
     * 获取论坛所有的帖子
     *
     * @return
     */
    @RequestMapping(value = "/forum/post-list", method = RequestMethod.POST)
    public ModelAndView getPostList(@RequestParam(value = "imgSrc", required = false, defaultValue = "") String imgSrc,
                                    @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                    @RequestParam(value = "desc", required = false, defaultValue = "") String desc) {


//        Map<String, Object> params = new HashMap<>();
//        params.put("")
//
//        ImageKeyword.DAO.queryForObject(params);
//


        return null;
    }

    ;

}
