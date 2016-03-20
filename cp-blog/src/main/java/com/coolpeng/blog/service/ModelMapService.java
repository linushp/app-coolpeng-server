package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumGroup;
import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class ModelMapService {

    @Autowired
    private ForumModuleService forumModuleService;

    public ForumModule addBelongModuleAndGroup(ModelMap modelMap, String moduleId)
            throws FieldNotFoundException, ParameterErrorException {
        ForumModule module = this.forumModuleService.getForumModule(moduleId, true);
        ForumGroup group = module.getForumGroup();

        List moduleList = this.forumModuleService.getForumModuleListByGroupId(module.getForumGroupId());
        group.setModuleList(moduleList);

        modelMap.put("belongModule", module);
        modelMap.put("belongGroup", group);

        return module;
    }
}