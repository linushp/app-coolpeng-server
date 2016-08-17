package com.coolpeng.cloud.note.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.cloud.common.base.RestBaseController;
import com.coolpeng.cloud.note.service.CloudNoteService;
import com.coolpeng.cloud.note.vo.CategoryVO;
import com.coolpeng.cloud.note.vo.NoteVO;
import com.coolpeng.cloud.reply.entity.CloudReply;
import com.coolpeng.cloud.reply.entity.CloudReplyPage;
import com.coolpeng.cloud.reply.service.CloudReplyService;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public TMSResponse getNoteCategory(@RequestBody JSONObject jsonObject) {
        String ownerId = jsonObject.getString("ownerUserId");  //用户ID,


        /******************************/
        List<CategoryVO> categoryList = cloudNoteService.getNoteCategory(ownerId);
        return TMSResponse.success(categoryList);
    }


    @ResponseBody
    @RequestMapping({"/getNoteListByCategory"})
    public TMSResponse getNoteListByCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ClassNotFoundException {
        CategoryVO categoryVO = jsonObject.getObject("CategoryVO", CategoryVO.class);
        int pageSize = jsonObject.getInteger("pageSize");
        int pageNumber = jsonObject.getInteger("pageNumber");

        /******************************/
        PageResult<NoteVO> pageResult = cloudNoteService.getNoteListByCategory(categoryVO, pageSize, pageNumber);
        return TMSResponse.successPage(pageResult);
    }


    @ResponseBody
    @RequestMapping({"/getNoteById"})
    public TMSResponse getNoteById(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ClassNotFoundException, UpdateErrorException, ParameterErrorException {
        String id = jsonObject.getString("id");

        /******************************/
        NoteVO note = cloudNoteService.getNoteById(id);
        return TMSResponse.success(note);
    }


    @ResponseBody
    @RequestMapping({"/saveOrUpdateNote"})
    public TMSResponse saveOrUpdateNote(@RequestBody JSONObject jsonObject)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, TMSMsgException {

        NoteVO noteVO = jsonObject.getObject("NoteVO", NoteVO.class);

        /******************************/
        //判断用户有没有登录，只有登录用户才能发布
        assertIsUserLoginIfToken(jsonObject);

        noteVO = cloudNoteService.saveOrUpdateNote(noteVO);

        return TMSResponse.success(noteVO);
    }


    /**
     * 只有登录用户（admin,或者 帖子作者 可以删除）
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
        if (!user.isAdmin() && !user.getId().equals(noteVO.getCreateUserId())){
            throw new TMSMsgException("只有Admin用户和帖子作者可以删除");
        }

        String noteId = noteVO.getId();
        cloudNoteService.deleteNote(noteId);

        return TMSResponse.success(noteId);
    }



}
