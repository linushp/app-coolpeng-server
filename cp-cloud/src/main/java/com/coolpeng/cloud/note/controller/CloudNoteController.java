package com.coolpeng.cloud.note.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.cloud.common.base.RestBaseController;
import com.coolpeng.cloud.note.service.CloudNoteService;
import com.coolpeng.cloud.note.vo.NoteVO;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by luanhaipeng on 16/8/17.
 */
@Controller
@RequestMapping(value = "/cloud/note", produces = "application/json; charset=UTF-8")
public class CloudNoteController extends RestBaseController {

    @Autowired
    private CloudNoteService cloudNoteService;

    @ResponseBody
    @RequestMapping({"/getNoteCategory"})
    public TMSResponse getNoteCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, TMSMsgException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        String ownerId = jsonObject.getString("ownerUserId");  //用户ID,

        /******************************/
        //判断用户有没有登录，只有登录用户才删除
        assertIsUserLoginIfToken(jsonObject);

        UserEntity user = getCurrentUserIfToken(jsonObject);
        List<ForumCategory> categoryList = cloudNoteService.getNoteCategory(user.getId());
        return TMSResponse.success(categoryList);
    }


    @ResponseBody
    @RequestMapping({"/saveOrUpdateNoteCategory"})
    public TMSResponse saveOrUpdateNoteCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, TMSMsgException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ForumCategory categoryVO = jsonObject.getObject("CategoryVO", ForumCategory.class);  //用户ID,

        /******************************/
        UserEntity user = getCurrentUserIfToken(jsonObject);
        cloudNoteService.saveOrUpdateNoteCategory(categoryVO, user);
        return getNoteCategory(jsonObject);

    }


    @ResponseBody
    @RequestMapping({"/deleteNoteCategory"})
    public TMSResponse deleteNoteCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, TMSMsgException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ForumCategory categoryVO = jsonObject.getObject("CategoryVO", ForumCategory.class);  //用户ID,

        /******************************/
        UserEntity user = getCurrentUserIfToken(jsonObject);
        cloudNoteService.deleteNoteCategory(categoryVO, user);
        return getNoteCategory(jsonObject);
    }





    @ResponseBody
    @RequestMapping({"/getNoteListByCategory"})
    public TMSResponse getNoteListByCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ClassNotFoundException {
        ForumCategory categoryVO = null;//jsonObject.getObject("CategoryVO", CategoryVO.class);

        String categoryId = jsonObject.getString("categoryId");//c101
        int pageSize = jsonObject.getInteger("pageSize");
        int pageNumber = jsonObject.getInteger("pageNumber");
        String titleLike = jsonObject.getString("titleLike");
        /******************************/

        UserEntity user = getCurrentUserIfToken(jsonObject);

        PageResult<NoteVO> pageResult = cloudNoteService.getNoteListByCategory(categoryId, pageSize, pageNumber, titleLike);
        return TMSResponse.successPage(pageResult);
    }


    @ResponseBody
    @RequestMapping({"/getNoteByIdWithReply"})
    public TMSResponse getNoteByIdWithReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ClassNotFoundException, UpdateErrorException, ParameterErrorException {
        String id = jsonObject.getString("id");
        Integer pageNumber = jsonObject.getInteger("pageNumber");
        Integer pageSize = jsonObject.getInteger("pageSize");

        /******************************/
        if (StringUtils.isNotBlank(id)) {
            pageNumber = pageNumber == null ? 1 : pageNumber;
            pageSize = pageSize == null ? 10 : pageSize;
            NoteVO note = cloudNoteService.getNoteByIdWithReply(id, pageNumber, pageSize);
            return TMSResponse.success(note);
        } else {
            return TMSResponse.success(null);
        }
    }


    @ResponseBody
    @RequestMapping({"/saveOrUpdateNote"})
    public TMSResponse saveOrUpdateNote(@RequestBody JSONObject jsonObject)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, TMSMsgException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        NoteVO noteVO = jsonObject.getObject("NoteVO", NoteVO.class);

        /******************************/
        //判断用户有没有登录，只有登录用户才能发布
        assertIsUserLoginIfToken(jsonObject);


        if (StringUtils.isNotBlank(noteVO.getId())) {
            //只有admin,或者 帖子作者 可以修改
            UserEntity user = getCurrentUserIfToken(jsonObject);
            if (!user.isAdmin() && !user.getId().equals(noteVO.getCreateUserId())) {
                throw new TMSMsgException("只有管理员和原作者可以修改");
            }
        }

        noteVO = cloudNoteService.saveOrUpdateNote(noteVO);

        return TMSResponse.success(noteVO);
    }


    /**
     * 只有登录用户（admin,或者 帖子作者 可以删除）
     *
     * @param jsonObject
     * @return
     * @throws TMSMsgException
     */
    @ResponseBody
    @RequestMapping({"/deleteNote"})
    public TMSResponse deleteNote(@RequestBody JSONObject jsonObject)
            throws TMSMsgException {

        NoteVO noteVO = jsonObject.getObject("NoteVO", NoteVO.class);

        /******************************/
        //判断用户有没有登录，只有登录用户才删除
        assertIsUserLoginIfToken(jsonObject);
        UserEntity user = getCurrentUserIfToken(jsonObject);

        //只有admin,或者 帖子作者 可以删除
        if (!user.isAdmin() && !user.getId().equals(noteVO.getCreateUserId())) {
            throw new TMSMsgException("只有管理员和原作者可以删除");
        }

        String noteId = noteVO.getId();
        cloudNoteService.deleteNote(noteId);

        return TMSResponse.success(noteId);
    }







    @ResponseBody
    @RequestMapping({"/saveOrUpdateNoteReply"})
    public TMSResponse saveOrUpdateNoteReply(@RequestBody JSONObject jsonObject)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, TMSMsgException {

        ForumPostReply noteVO = jsonObject.getObject("ForumPostReply", ForumPostReply.class);

        /******************************/
        //判断用户有没有登录，只有登录用户才能发布
        assertIsUserLoginIfToken(jsonObject);

        UserEntity user = getCurrentUserIfToken(jsonObject);

        if (StringUtils.isNotBlank(noteVO.getId())) {
            //只有admin,或者 帖子作者 可以修改
            if (!user.isAdmin() && !user.getId().equals(noteVO.getCreateUserId())) {
                throw new TMSMsgException("只有管理员和原作者可以修改");
            }
        }

        noteVO = cloudNoteService.saveOrUpdateNoteReply(noteVO,user);

        return TMSResponse.success(noteVO);
    }


    /**
     * 只有登录用户（admin,或者 帖子作者 可以删除）
     *
     * @param jsonObject
     * @return
     * @throws TMSMsgException
     */
    @ResponseBody
    @RequestMapping({"/deleteNoteReply"})
    public TMSResponse deleteNoteReply(@RequestBody JSONObject jsonObject)
            throws TMSMsgException {

        ForumPostReply noteVO = jsonObject.getObject("ForumPostReply", ForumPostReply.class);

        /******************************/
        //判断用户有没有登录，只有登录用户才删除
        assertIsUserLoginIfToken(jsonObject);
        UserEntity user = getCurrentUserIfToken(jsonObject);

        //只有admin,或者 帖子作者 可以删除
        if (!user.isAdmin() && !user.getId().equals(noteVO.getCreateUserId())) {
            throw new TMSMsgException("只有管理员和原作者可以删除");
        }

        String noteId = noteVO.getId();
        cloudNoteService.deleteNoteReply(noteId);

        return TMSResponse.success(noteId);
    }















}
