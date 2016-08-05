package com.coolpeng.blog.controller;

import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostImage;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.utils.CollectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/19.
 */
@Controller
public class ImageController {



    @RequestMapping(value = {"/images"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView getPostContent() {
        ModelMap modelMap = new ModelMap();
        return new ModelAndView("forum/jsp/image-list", modelMap);
    }














    /**
     * 获取论坛所有的帖子
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/images/updateForumImages")
    public TMSResponse updateForumImages() {

        List<ForumPostImage> images = ForumPostImage.DAO.findAll();

        List<ForumPost> posts = ForumPost.DAO.findAll();

        List<ForumPostImage> insertImages = new ArrayList<>();

        try {
            Map<String, ForumPostImage> oldImages = CollectionUtil.toMap(images, "imagePath");

            for (ForumPost post:posts){
                List<String> imageList = post.createTempImageEntity(10000);

                for (String imagePath:imageList){
                    if (!oldImages.containsKey(imagePath)){
                        insertImages.add(createImageInfo(imagePath,post));
                    }
                }
            }

            for (ForumPostImage img:insertImages){
                ForumPostImage.DAO.insertOrUpdate(img);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ForumPostImage createImageInfo(String imagePath, ForumPost post) {
        ForumPostImage image = new ForumPostImage();
        image.setCreateTime(post.getCreateTime());
        image.setCreateUserId(post.getCreateUserId());
        image.setImageDesc(post.getSummary());
        image.setImageName(post.getPostTitle());
        image.setForumPostId(post.getId());
        image.setForumPostTitle(post.getPostTitle());
        image.setImagePath(imagePath);

        return image;
    }



}
