package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumGroup;
import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.enums.AccessControl;
import com.coolpeng.blog.utils.EntityUtils;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForumModuleService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static List<ForumGroup> GROUP_LIST = null;
    private static List<ForumModule> MODULE_LIST = null;
    private static Map<String, ForumGroup> GROUP_MAP = new HashMap();
    private static Map<String, ForumModule> MODULE_MAP = new HashMap();

    public void updateCache() {
        Map<String,Object> params = new HashMap<>();
        //只查询被公开的
        AccessControl.appendQueryCondition(AccessControl.PUBLIC,params);
        try {
            GROUP_LIST = ForumGroup.DAO.queryForList(params);
            MODULE_LIST = ForumModule.DAO.queryForList(params);
        } catch (FieldNotFoundException e) {
            logger.error("",e);
        }

        MODULE_MAP.clear();
        GROUP_MAP.clear();

        for (ForumModule m : MODULE_LIST) {
            MODULE_MAP.put(m.getId(), m);
        }

        for (ForumGroup g : GROUP_LIST) {
            GROUP_MAP.put(g.getId(), g);
        }

        EntityUtils.setModuleDefaultIcon(MODULE_LIST, TmsCurrentRequest.getContext());
    }

    private void makeSureNotNull() {
        if ((MODULE_LIST == null) || (GROUP_LIST == null)) {
            updateCache();
        }
    }

    public List<ForumModule> getForumModuleList(boolean hasGroup) {
        makeSureNotNull();
        List result = new ArrayList();
        for (ForumModule module : MODULE_LIST) {
            module = new ForumModule(module);
            if (hasGroup) {
                ForumGroup group = (ForumGroup) GROUP_MAP.get(module.getForumGroupId());
                if (group != null) {
                    group = new ForumGroup(group);
                    group.setModuleList(null);
                    module.setForumGroup(group);
                }
            }
            result.add(module);
        }

        return result;
    }

    public List<ForumGroup> getForumGroupList(boolean hasModuleList) {

        makeSureNotNull();

        List<ForumModule> allModuleList = getForumModuleList(false);

        List result = new ArrayList();

        for (ForumGroup group : GROUP_LIST) {
            group = new ForumGroup(group);
            if (hasModuleList) {
                List<ForumModule> moduleList = new ArrayList();
                for (ForumModule module : allModuleList) {
                    String groupId = group.getId();
                    String moduleGroupId = module.getForumGroupId();
                    if (groupId.equals(moduleGroupId)) {
                        moduleList.add(module);
                    }
                }
                group.setModuleList(moduleList);
            }

            result.add(group);
        }

        return result;
    }

    public List<ForumModule> getForumModuleList() {
        return getForumModuleList(false);
    }

    public List<ForumGroup> getForumGroupList() {
        return getForumGroupList(false);
    }

    public List<ForumModule> getForumModuleListByGroupId(String groupId) {
        List<ForumModule> result = new ArrayList();
        List<ForumModule> allModuleList = getForumModuleList(true);
        if (!CollectionUtil.isEmpty(allModuleList)) {
            for (ForumModule module : allModuleList) {
                if (groupId.equals(module.getForumGroupId())) {
                    result.add(module);
                }
            }
        }
        return result;
    }

    public ForumModule getForumModule(String moduleId) {
        return getForumModule(moduleId, false);
    }

    public ForumGroup getForumGroup(String forumGroupId) {
        if (forumGroupId == null) {
            return null;
        }
        List<ForumGroup> groupList = getForumGroupList(false);
        for (ForumGroup group : groupList) {
            if (forumGroupId.equals(group.getId())) {
                return group;
            }
        }
        return null;
    }

    public ForumModule getForumModule(String moduleId, boolean hasGroup) {
        if (moduleId != null) {
            List<ForumModule> moduleList = getForumModuleList(hasGroup);
            for (ForumModule module : moduleList) {
                if (moduleId.equals(module.getId())) {
                    return module;
                }
            }
        }
        return null;
    }

    public void updatePostCount(ForumModule module)
            throws FieldNotFoundException, UpdateErrorException {
        String moduleId = module.getId();

        Map params = new HashMap();
        params.put("forumModuleId", moduleId);
        int postCount = ForumPost.DAO.count(params);
        module.setPostCount(postCount + 1);
        ForumModule.DAO.update(module);
    }

    public void saveOrUpdateModule(String moduleId, String moduleName, String moduleDesc, String moduleIcon, String forumGroupId, int moduleType, int status) throws UpdateErrorException, ParameterErrorException, FieldNotFoundException {
        if (StringUtils.isBlank(moduleId)) {
            ForumModule forumModule = new ForumModule();
            forumModule.setModuleDesc(moduleDesc);
            forumModule.setModuleName(moduleName);
            forumModule.setPostCount(0);
            forumModule.setModuleType(moduleType);
            forumModule.setForumGroupId(forumGroupId);
            forumModule.setModuleIcon(moduleIcon);
            forumModule.setStatus(status);
            ForumModule.DAO.save(forumModule);
        } else {
            ForumModule forumModule = (ForumModule) ForumModule.DAO.queryForObject(moduleId);
            forumModule.setModuleDesc(moduleDesc);
            forumModule.setModuleName(moduleName);
            forumModule.setModuleType(moduleType);
            forumModule.setForumGroupId(forumGroupId);
            forumModule.setModuleIcon(moduleIcon);
            forumModule.setStatus(status);
            ForumModule.DAO.update(forumModule);
        }
        updateCache();
    }

    public void saveOrUpdateGroup(String groupId, String groupName, String groupDesc, int status) throws ParameterErrorException, FieldNotFoundException, UpdateErrorException {
        if (StringUtils.isBlank(groupId)) {
            ForumGroup forumGroup = new ForumGroup();
            forumGroup.setGroupDesc(groupDesc);
            forumGroup.setGroupName(groupName);
            forumGroup.setStatus(status);
            ForumGroup.DAO.insert(forumGroup);
        } else {
            ForumGroup forumGroup = (ForumGroup) ForumGroup.DAO.queryForObject(groupId);
            forumGroup.setGroupDesc(groupDesc);
            forumGroup.setGroupName(groupName);
            forumGroup.setStatus(status);
            ForumGroup.DAO.update(forumGroup);
        }

        updateCache();
    }
}