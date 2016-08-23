package com.coolpeng.cloud.note.service;

import com.coolpeng.blog.entity.*;
import com.coolpeng.blog.entity.enums.AccessControl;
import com.coolpeng.blog.service.ForumCategoryService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.blog.vo.ForumCategoryTree;
import com.coolpeng.cloud.note.vo.NoteVO;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luanhaipeng on 16/8/17.
 */
@Service
public class CloudNoteService {

    @Autowired
    private ForumService forumService;
//
//    @Autowired
//    private CloudPrivateModuleService cloudPrivateModuleService;


    @Autowired
    private ForumCategoryService forumCategoryService;



    /**
     * 创建一个分类目录
     * @param categoryVO {[id],name,desc,level,parentId}
     * @param currentUser
     * @return
     */
    public ForumCategory saveOrUpdateNoteCategory(ForumCategory categoryVO, UserEntity currentUser) throws ParameterErrorException, FieldNotFoundException, UpdateErrorException {

        ForumCategory forumCategory = new ForumCategory();
        if (StringUtils.isNotBlank(categoryVO.getId())){
            forumCategory = ForumCategory.DAO.queryById(categoryVO.getId());
        }
        forumCategory.setName(categoryVO.getName());
        forumCategory.setDesc(categoryVO.getDesc());
        forumCategory.setParentId(categoryVO.getParentId());
        forumCategory.setAccessControl(AccessControl.PRIVATE.getValue());
        forumCategoryService.saveOrUpdate(forumCategory);
        return categoryVO;
    }


    /**
     * 删除分类
     * @param categoryVO
     * @param user
     * @return
     */
    public ForumCategory deleteNoteCategory(ForumCategory categoryVO, UserEntity user) throws UpdateErrorException {
        forumCategoryService.deleteEntity(categoryVO.getId());
        return categoryVO;
    }


    /******************getNoteCategory********************/
    public List<ForumCategory> getNoteCategory(String ownerId) throws FieldNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ForumCategoryTree categoryTree = forumCategoryService.getForumCategoryByCreateUserId(ownerId);
        return categoryTree.getTreeNodeList();
    }






    /******************getNoteListByCategory********************/
    public PageResult<NoteVO>  getNoteListByCategory(String categoryId, int pageSize, int pageNumber,String titleLike) throws ClassNotFoundException, FieldNotFoundException {

        PageResult<ForumPost> postPage = forumService.getPostListByMyCategoryId(categoryId, pageNumber, pageSize, titleLike);

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

        String moduleId = noteVO.getCategoryId();
        String myModuleId = noteVO.getMyCategoryId();
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
        List<String> imageList = forumPostReply.getImageList();

        if (StringUtils.isBlank(replyId)){
            return forumService.createPostReply(postId,forumPostReply.getReplyContent(),imageList);
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
