package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.utils.ForumUrlUtils;
import com.coolpeng.blog.utils.SysSettingUtil;
import com.coolpeng.blog.vo.ForumHomeVO;
import com.coolpeng.blog.vo.PostTitle;
import com.coolpeng.blog.vo.SlideImage;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/9/30.
 */
public class ForumHomeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //跟新论坛的首页
    public void updateForumHome(ForumPost forumPost, List<String> images) throws FieldNotFoundException {

        String title = forumPost.getPostTitle();
        String postLink = ForumUrlUtils.toPostContentURL(forumPost.getId());
        ForumHomeVO m = SysSettingUtil.getSysSetting(ForumHomeVO.class);
        if (m == null) {
            m = new ForumHomeVO();
        }

        //将图片信息添加至首页
        if (!CollectionUtil.isEmpty(images)) {
            m.getSlideImageList().add(new SlideImage(title, images.get(0), postLink));
        }

        //讲新帖子添加到首页
        m.getNewPostList().add(new PostTitle(title, postLink, forumPost.getCreateTime()));
        SysSettingUtil.saveOrUpdate(m);
    }
}
