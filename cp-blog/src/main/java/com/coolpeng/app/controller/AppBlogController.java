package com.coolpeng.app.controller;

import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.service.ForumModuleService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.mvc.jsptag.TmsFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by 栾海鹏 on 2016/3/18.
 */
@Controller
@RequestMapping(value = "/app/blog", produces = "application/json; charset=UTF-8")
public class AppBlogController {


    @Autowired
    private ForumModuleService forumModuleService;

    @Autowired
    private ForumService forumService;


    /**
     * 获取所有话题列表
     *
     * @return
     * @throws FieldNotFoundException
     * @throws UpdateErrorException
     * @throws ParameterErrorException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @ResponseBody
    @RequestMapping({"/getAllModuleList"})
    public TMSResponse getAllModuleList()
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<ForumModule> moduleList = this.forumModuleService.getForumModuleList(true);
        return TMSResponse.success(moduleList);
    }


    /**
     * 获取所有文章列表
     *
     * @param moduleId
     * @param pageSize
     * @param pageNumber
     * @return
     * @throws ClassNotFoundException
     * @throws FieldNotFoundException
     */
    @ResponseBody
    @RequestMapping({"/getPostList"})
    public TMSResponse getPostList(String moduleId, int pageSize, int pageNumber) throws ClassNotFoundException, FieldNotFoundException {
        PageResult<ForumPost> postPageResult = forumService.getPostList(pageNumber, pageSize, moduleId);

        List<ForumPost> list = postPageResult.getPageData();
        for (ForumPost p : list) {
            p.createTempImageEntity(3);
            p.setPostContent(null);
        }

        return TMSResponse.success(postPageResult);
    }


    /**
     * 获取一篇文章及其回复
     *
     * @param postId
     * @param pageSize
     * @param pageNumber
     * @return
     * @throws ClassNotFoundException
     * @throws FieldNotFoundException
     * @throws ParameterErrorException
     * @throws UpdateErrorException
     */
    @ResponseBody
    @RequestMapping({"/getPostWithReply"})
    public TMSResponse getPostWithReply(String postId, int pageSize, int pageNumber) throws ClassNotFoundException, FieldNotFoundException, ParameterErrorException, UpdateErrorException {
        ForumPost postContent = this.forumService.getPostWithReply(postId, pageNumber, pageSize);
        String moduleId = postContent.getForumModuleId();


        String reg = "src=\"http://coolpeng\\.bj\\.bcebos\\.com/dragon_ball/upload.{25,35}\\..{3,4}\"";
        PageResult<ForumPostReply> replyPage = postContent.getReplyPageResult();
        List<ForumPostReply> replyList = replyPage.getPageData();
        for (ForumPostReply reply:replyList){

            String time = TmsFunctions.prettyDate(reply.getCreateTime());
            reply.setCreateTime(time);

//            String content = reply.getReplyContent();
//            if (StringUtils.isNotBlank(content)){
//                content.replaceAll(reg,)
//            }
        }

        return TMSResponse.success(postContent);
    }


}
