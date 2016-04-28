package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostImage;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.HtmlUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 栾海鹏 on 2016/4/9.
 */
@Service
public class ForumImageService {

    public void saveForumPostImageByNewReply(ForumPostReply reply, List<String> images) {
        if (CollectionUtil.isEmpty(images)){
            return;
        }

        for (String imgPath :images){
            ForumPostImage image = new ForumPostImage();
            image.setImagePath(imgPath);


            String summary = reply.getReplySummary();
            if (StringUtils.isBlank(summary)){
                summary = HtmlUtil.getTextFromHtml2(reply.getReplyContent());
                summary = StringUtils.maxSize(summary, 200);
                if (StringUtils.isBlank(summary)){
                    summary = reply.getForumPostTitle();
                }
            }
            image.setImageDesc(summary);
            image.setImageName(summary);

            image.setForumPostTitle(reply.getForumPostTitle());
            image.setForumPostId(reply.getForumPostId());

            image.copyBaseEntity(reply);


            ForumPostImage.DAO.save(image);
        }


    }

    public void saveForumPostImageByNewPost(ForumPost post, List<String> images) {
        if (CollectionUtil.isEmpty(images)){
            return;
        }

        for (String imgPath :images){
            ForumPostImage image = new ForumPostImage();
            image.setImagePath(imgPath);


            String summary = post.getSummary();
            if (StringUtils.isBlank(summary)){
                summary = HtmlUtil.getTextFromHtml2(post.getPostContent());
                summary = StringUtils.maxSize(summary, 200);
                if (StringUtils.isBlank(summary)){
                    summary = post.getPostTitle();
                }
            }

            image.setImageDesc(summary);
            image.setImageName(summary);

            image.setForumPostTitle(post.getPostTitle());
            image.setForumPostId(post.getId());

            image.copyBaseEntity(post);

            ForumPostImage.DAO.save(image);
        }

    }

}
