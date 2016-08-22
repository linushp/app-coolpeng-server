package com.coolpeng.cloud.note.service;

import com.coolpeng.blog.entity.*;
import com.coolpeng.blog.entity.enums.AccessControl;
import com.coolpeng.blog.service.ForumModuleService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.cloud.note.vo.CategoryVO;
import com.coolpeng.cloud.note.vo.NoteVO;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.QueryCondition;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/8/17.
 */
@Service
public class CloudNoteService {

    @Autowired
    private ForumService forumService;

    @Autowired
    private CloudPrivateModuleService cloudPrivateModuleService;



    /**
     * 创建一个分类目录
     * @param categoryVO {[id],name,desc,level,parentId}
     * @param currentUser
     * @return
     */
    public CategoryVO saveOrUpdateNoteCategory(CategoryVO categoryVO, UserEntity currentUser) throws ParameterErrorException, FieldNotFoundException, UpdateErrorException {

        if (CloudConst.LEVEL_GROUP.equalsIgnoreCase(categoryVO.getLevel())){
            if (StringUtils.isBlank(categoryVO.getId())){
                return cloudPrivateModuleService.createGroup(categoryVO,currentUser);
            }
            return cloudPrivateModuleService.updateGroup(categoryVO,currentUser);
        }

        if (CloudConst.LEVEL_MODULE.equalsIgnoreCase(categoryVO.getLevel())){
            if (StringUtils.isBlank(categoryVO.getId())){
                return cloudPrivateModuleService.createModule(categoryVO,currentUser);
            }
            return cloudPrivateModuleService.updateModule(categoryVO, currentUser);
        }

        return null;
    }


    /**
     * 删除分类
     * @param categoryVO
     * @param user
     * @return
     */
    public CategoryVO deleteNoteCategory(CategoryVO categoryVO, UserEntity user) {
        if (CloudConst.LEVEL_GROUP.equalsIgnoreCase(categoryVO.getLevel())){
            ForumGroup.DAO.deleteById(categoryVO.getId());
        }
        if (CloudConst.LEVEL_MODULE.equalsIgnoreCase(categoryVO.getLevel())){
            ForumModule.DAO.deleteById(categoryVO.getId());
        }
        return categoryVO;
    }


    /******************getNoteCategory********************/
    public List<CategoryVO> getNoteCategory(String ownerId) throws FieldNotFoundException {

        Map<String,Object> groupQueryParams = new HashMap<>();
        groupQueryParams.put("createUserId",ownerId);

        List<ForumGroup> allGroupList = ForumGroup.DAO.queryForList(groupQueryParams);
        List<ForumModule> allModuleList = ForumModule.DAO.queryForList(groupQueryParams);

        List<CategoryVO> result = new ArrayList<>();
        List<ForumGroup> groupList = allGroupList;
        for (ForumGroup g : groupList) {
            CategoryVO c = new CategoryVO(g.getId(), "g_", g.getGroupName(), g.getGroupDesc());
            c.setLevel(CloudConst.LEVEL_GROUP);
            c.setChildren(toCategoryVOList(g.getId(),allModuleList));
            result.add(c);
        }
        return result;
    }

    private List<CategoryVO> toCategoryVOList(String groupId ,List<ForumModule> moduleList) {
        List<CategoryVO> result = new ArrayList<>();
        for (ForumModule m : moduleList) {
            if (groupId.equals(m.getForumGroupId())){
                CategoryVO c = new CategoryVO(m.getId(), "m_" + m.getModuleType(), m.getModuleName(), m.getModuleDesc());
                c.setLevel(CloudConst.LEVEL_MODULE);
                c.setParentId(groupId);
                //TODO 无限层级树
                c.setParentLevel(CloudConst.LEVEL_GROUP);
                result.add(c);
            }
        }
        return result;
    }




    /******************getNoteListByCategory********************/
    public PageResult<NoteVO>  getNoteListByCategory(String level,String categoryId, int pageSize, int pageNumber,String titleLike) throws ClassNotFoundException, FieldNotFoundException {

        PageResult<ForumPost> postPage = null;

        if (CloudConst.LEVEL_MODULE.equals(level)){
            postPage = forumService.getPostListByModuleId(categoryId, pageNumber, pageSize, titleLike);
        }else if (CloudConst.LEVEL_GROUP.equals(level)){
            postPage = forumService.getPostListByGroupId(categoryId, pageNumber, pageSize, titleLike);
        }else {
            postPage = forumService.getPostListByModuleId(null,pageNumber, pageSize, titleLike);
        }

        List<ForumPost> postList = postPage.getPageData();
        List<NoteVO> noteList = new ArrayList<>();
        for (ForumPost p:postList){
            p.setPostContent(null);//不返回详细内容
            noteList.add(new NoteVO(p));
        }


        PageResult<NoteVO> notePage = new PageResult<>(postPage.getTotalCount(), pageSize, pageNumber, noteList);
        return notePage;
    }



    /******************getNoteListByCategory********************/
    public NoteVO getNoteByIdWithReply(String postId, int pageNumber, int pageSize) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, ClassNotFoundException {
//        ForumPost post = forumService.getPostById(id,null, true);

        ForumPost post = forumService.getPostWithReply(postId, pageNumber, pageSize, null);

        return new NoteVO(post);
    }




    /******************saveOrUpdateNote********************/
    public NoteVO saveOrUpdateNote(NoteVO noteVO) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {

        String moduleId = noteVO.getForumModuleId();
        String myModuleId = noteVO.getMyModuleId();
        String postTitle = noteVO.getPostTitle();
        String postContent = noteVO.getPostContent();
        String summary = noteVO.getSummary();
        String accessControl = noteVO.getAccessControl();

        String id = noteVO.getId();
        List <String> imageList = noteVO.getImageList();

        if (StringUtils.isNotBlank(id)){
            //修改自己的note的之前，moduleId,myModuleId的属性都是有的
            ForumPost post = forumService.updatePost(id,moduleId,myModuleId, postTitle, postContent, summary,imageList,accessControl);
            return new NoteVO(post);
        }else {
            ForumPost post = forumService.createPost(moduleId, postTitle, postContent, summary, imageList, AccessControl.PUBLIC);
            return new NoteVO(post);
        }
    }


    /******************deleteNote********************/
    public void deleteNote(String noteId) {
        ForumPost.DAO.deleteById(noteId);
    }


    /**
     * 新建或保存
     * @param forumPostReply
     * @return
     */
    public ForumPostReply saveOrUpdateNoteReply(ForumPostReply forumPostReply,UserEntity user) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        String postId = forumPostReply.getForumPostId();
        String replyId = forumPostReply.getId();
        if (StringUtils.isBlank(replyId)){
            return forumService.createPostReply(postId,forumPostReply.getReplyContent());
        }
        else {
            forumPostReply.setUpdateTime(DateUtil.currentTimeFormat());
            forumPostReply.setUpdateUserId(user.getId());
            ForumPostReply.DAO.updateEntityFields(forumPostReply,new String[]{"replyContent","updateTime","updateUserId"});
            return forumPostReply;
        }
    }

    /**
     * 删除
     * @param replyId
     */
    public void deleteNoteReply(String replyId) {
        ForumPostReply.DAO.deleteById(replyId);
    }

}
