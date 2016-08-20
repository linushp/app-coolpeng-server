package com.coolpeng.cloud.note.service;

import com.coolpeng.blog.entity.ForumGroup;
import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.entity.enums.AccessControl;
import com.coolpeng.cloud.note.vo.CategoryVO;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/8/20.
 */
@Service
public class CloudPrivateModuleService {

    private CategoryVO toCategoryVO( ForumGroup g ){
        CategoryVO cc = new CategoryVO(g.getId(), "g_", g.getGroupName(), g.getGroupDesc());
        cc.setLevel(CloudConst.LEVEL_GROUP);
        return cc;
    }

    private CategoryVO toCategoryVO( ForumModule m ){
        CategoryVO c = new CategoryVO(m.getId(), "m_" + m.getModuleType(), m.getModuleName(), m.getModuleDesc());
        c.setLevel(CloudConst.LEVEL_MODULE);
        c.setParentId(m.getForumGroupId());
        c.setParentLevel(CloudConst.LEVEL_GROUP);
        return c;
    }

    public CategoryVO createGroup(CategoryVO categoryVO, UserEntity currentUser) {
        ForumGroup g = new ForumGroup();
        g.setAccessControl(AccessControl.PRIVATE.getValue()); //在这里创建的只能是私有的
        g.setModuleList(null);
        g.setGroupName(categoryVO.getName());
        g.setGroupDesc(categoryVO.getDesc());
        g.setStatus(0);
        g.setCreateUserId(currentUser.getId());
        g.setUpdateUserId(currentUser.getId());
        ForumGroup.DAO.save(g);
        return toCategoryVO(g);
    }

    public CategoryVO createModule(CategoryVO categoryVO, UserEntity currentUser) {

        ForumModule m = new ForumModule();

        m.setAccessControl(AccessControl.PRIVATE.getValue()); //在这里创建的只能是私有的
        m.setModuleName(categoryVO.getName());
        m.setModuleDesc(categoryVO.getDesc());
        m.setModuleType(3);
        m.setStatus(0);
        m.setCreateUserId(currentUser.getId());
        m.setUpdateUserId(currentUser.getId());
        //TODO 无限层级
        m.setForumGroupId(categoryVO.getParentId());

        ForumModule.DAO.save(m);
        return toCategoryVO(m);
    }



    public CategoryVO updateGroup(CategoryVO categoryVO, UserEntity currentUser) throws ParameterErrorException, FieldNotFoundException, UpdateErrorException {

        ForumGroup group = ForumGroup.DAO.queryById(categoryVO.getId());

        group.setGroupDesc(categoryVO.getDesc());
        group.setGroupName(categoryVO.getName());

        group.setUpdateUserId(currentUser.getId());
        ForumGroup.DAO.update(group);
        return toCategoryVO(group);
    }

    public CategoryVO updateModule(CategoryVO categoryVO, UserEntity currentUser) throws ParameterErrorException, FieldNotFoundException, UpdateErrorException {

        ForumModule module = ForumModule.DAO.queryById(categoryVO.getId());

        module.setModuleName(categoryVO.getName());
        module.setModuleDesc(categoryVO.getDesc());
        module.setForumGroupId(categoryVO.getParentId());

        module.setUpdateUserId(currentUser.getId());

        ForumModule.DAO.update(module);
        return toCategoryVO(module);
    }
}
