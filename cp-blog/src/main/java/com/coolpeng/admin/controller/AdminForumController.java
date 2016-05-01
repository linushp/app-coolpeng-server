package com.coolpeng.admin.controller;

import com.coolpeng.blog.entity.ForumGroup;
import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.service.ForumModuleService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminForumController {

    @Autowired
    private ForumModuleService forumModuleService;

    @Autowired
    private ForumService forumService;

    @ResponseBody
    @RequestMapping({"/admin/forum/getAllModuleList"})
    public TMSResponse getAllModuleList()
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List moduleList = this.forumModuleService.getForumModuleList(true);
        List groupList = this.forumModuleService.getForumGroupList(true);

        HashMap map = new HashMap();
        map.put("moduleList", moduleList);
        map.put("groupList", groupList);

        return TMSResponse.success(map);
    }

    @ResponseBody
    @RequestMapping({"/admin/forum/saveModule"})
    public TMSResponse saveModule(@RequestBody ForumModule forumModule)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String moduleId = forumModule.getId();

        this.forumModuleService.saveOrUpdateModule(forumModule.getId(), forumModule.getModuleName(), forumModule.getModuleDesc(), forumModule.getModuleIcon(), forumModule.getForumGroupId(), forumModule.getModuleType(), forumModule.getStatus());

        if (StringUtils.isNotBlank(moduleId)) {
            this.forumService.batchUpdatePost(forumModule);
        }

        return TMSResponse.success();
    }

    @ResponseBody
    @RequestMapping({"/admin/forum/saveModuleGroup"})
    public TMSResponse saveModuleGroup(@RequestBody ForumGroup forumGroup) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.forumModuleService.saveOrUpdateGroup(forumGroup.getId(), forumGroup.getGroupName(), forumGroup.getGroupDesc(), forumGroup.getStatus());
        return TMSResponse.success();
    }


    /**
     * 删除一篇帖子
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
    @RequestMapping({"/admin/forum/deletePostContent"})
    public TMSResponse deletePostContent(String postId) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        ForumPost.DAO.delete(postId);


        return TMSResponse.success();
    }


    /**
     * 删除一篇帖子的回复
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
    @RequestMapping({"/admin/forum/deletePostReply"})
    public TMSResponse deletePostReply(String replyId) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        ForumPostReply.DAO.delete(replyId);

        return TMSResponse.success();
    }



}