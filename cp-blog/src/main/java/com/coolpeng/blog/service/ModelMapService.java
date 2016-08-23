package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumGroup;
import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class ModelMapService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ForumModuleService forumModuleService;

    public ForumModule addBelongModuleAndGroup(ModelMap modelMap, String moduleId)
            throws FieldNotFoundException, ParameterErrorException {
//        try {
            ForumModule module = this.forumModuleService.getForumModule(moduleId, true);
            ForumGroup group = module.getForumGroup();

            List moduleList = this.forumModuleService.getForumModuleListByGroupId(module.getForumGroupId());
            group.setModuleList(moduleList);

            modelMap.put("belongModule", module);
            modelMap.put("belongGroup", group);
            return module;
//        }catch (Throwable e){
//
//            logger.error("",e);
//
//            modelMap.put("belongModule", new ForumModule());
//            modelMap.put("belongGroup", new ForumGroup());
//            return new ForumModule();
//        }

    }
}