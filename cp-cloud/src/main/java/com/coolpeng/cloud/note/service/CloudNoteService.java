package com.coolpeng.cloud.note.service;

import com.coolpeng.blog.entity.ForumGroup;
import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.service.ForumModuleService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.cloud.note.vo.CategoryVO;
import com.coolpeng.cloud.note.vo.NoteVO;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.QueryCondition;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luanhaipeng on 16/8/17.
 */
@Service
public class CloudNoteService {

    @Autowired
    private ForumModuleService forumModuleService;
    @Autowired
    private ForumService forumService;

    private static final String LEVEL_GROUP = "group";
    private static final String LEVEL_MODULE = "module";


    /******************getNoteCategory********************/
    public List<CategoryVO> getNoteCategory(String ownerId) {

        List<CategoryVO> result = new ArrayList<>();
        List<ForumGroup> groupList = findByCreateUserId(forumModuleService.getForumGroupList(true), ownerId);
        for (ForumGroup g : groupList) {
            CategoryVO c = new CategoryVO(g.getId(), "blog", g.getGroupName(), g.getGroupDesc());
            c.setLevel(LEVEL_GROUP);
            c.setChildren(toCategoryVOList(g.getModuleList()));
            result.add(c);
        }
        return result;
    }

    private List<CategoryVO> toCategoryVOList(List<ForumModule> moduleList) {
        List<CategoryVO> result = new ArrayList<>();
        for (ForumModule m : moduleList) {
            CategoryVO c = new CategoryVO(m.getId(), "blog_" + m.getModuleType(), m.getModuleName(), m.getModuleDesc());
            c.setLevel(LEVEL_MODULE);
            result.add(c);
        }
        return result;
    }

    private List<ForumGroup> findByCreateUserId(List<ForumGroup> groupList, String ownerId) {
        List<ForumGroup> result = new ArrayList<>();
        if (groupList == null) {
            return result;
        }
        for (ForumGroup g : groupList) {
            if (ownerId.equals(g.getCreateUserId())) {
                result.add(g);
            }
        }
        return result;
    }





    /******************getNoteListByCategory********************/
    public PageResult<NoteVO>  getNoteListByCategory(String level,String categoryId, int pageSize, int pageNumber) throws ClassNotFoundException, FieldNotFoundException {

        PageResult<ForumPost> postPage = null;
        if (LEVEL_MODULE.equals(level)){
            postPage = forumService.getPostListByModuleId(categoryId, pageNumber, pageSize, null);
        }else {
            postPage = forumService.getPostListByGroupId(categoryId, pageNumber, pageSize, null);
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
    public NoteVO getNoteById(String id) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        ForumPost post = forumService.getPostById(id, true);
        return new NoteVO(post);
    }




    /******************saveOrUpdateNote********************/
    public NoteVO saveOrUpdateNote(NoteVO noteVO) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {

        String moduleId = noteVO.getForumModuleId();
        String postTitle = noteVO.getPostTitle();
        String postContent = noteVO.getPostContent();
        String summary = noteVO.getSummary();
        String id = noteVO.getId();
        List <String> imageList = noteVO.getImageList();

        if (StringUtils.isNotBlank(id)){
            ForumPost post = forumService.updatePost(id,moduleId, postTitle, postContent, summary,imageList);
            return new NoteVO(post);
        }else {
            ForumPost post = forumService.createPost(moduleId, postTitle, postContent, summary,imageList);
            return new NoteVO(post);
        }
    }


    /******************deleteNote********************/
    public void deleteNote(String noteId) {
        ForumPost.DAO.deleteById(noteId);
    }
}
