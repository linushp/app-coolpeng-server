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
import com.coolpeng.framework.utils.StringUtils;
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
    public TMSResponse getNoteCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException {
//        String ownerId = jsonObject.getString("ownerUserId");  //用户ID,

        /******************************/
        UserEntity user = getCurrentUserIfToken(jsonObject);
        List<CategoryVO> categoryList = cloudNoteService.getNoteCategory(user.getId());
        return TMSResponse.success(categoryList);
    }


    @ResponseBody
    @RequestMapping({"/saveOrUpdateNoteCategory"})
    public TMSResponse saveOrUpdateNoteCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException {
        CategoryVO categoryVO = jsonObject.getObject("CategoryVO", CategoryVO.class);  //用户ID,

        /******************************/
        UserEntity user = getCurrentUserIfToken(jsonObject);
        categoryVO = cloudNoteService.saveOrUpdateNoteCategory(categoryVO,user);
        return TMSResponse.success(categoryVO);
    }




    @ResponseBody
    @RequestMapping({"/getNoteListByCategory"})
    public TMSResponse getNoteListByCategory(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ClassNotFoundException {
        CategoryVO categoryVO = null;//jsonObject.getObject("CategoryVO", CategoryVO.class);
        JSONObject pathParams = jsonObject.getJSONObject("pathParams");//

        int pageSize = jsonObject.getInteger("pageSize");
        int pageNumber = jsonObject.getInteger("pageNumber");

        String titleLike = jsonObject.getString("titleLike");

        /******************************/

        String level = null;
        String categoryId = null;

        if (categoryVO != null) {
            level = categoryVO.getLevel();
            categoryId = categoryVO.getId();
        } else if (pathParams != null) {

            String g = pathParams.getString("g");
            if (StringUtils.isNotBlank(g)) {
                level = "group";
                categoryId = g;
            }

            String m = pathParams.getString("m");
            if (StringUtils.isNotBlank(m)) {
                level = "module";
                categoryId = m;
            }

        }


        PageResult<NoteVO> pageResult = cloudNoteService.getNoteListByCategory(level, categoryId, pageSize, pageNumber,titleLike);
        return TMSResponse.successPage(pageResult);
    }


    @ResponseBody
    @RequestMapping({"/getNoteById"})
    public TMSResponse getNoteById(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ClassNotFoundException, UpdateErrorException, ParameterErrorException {
        String id = jsonObject.getString("id");

        /******************************/
        if(StringUtils.isNotBlank(id)){
            NoteVO note = cloudNoteService.getNoteById(id);
            return TMSResponse.success(note);
        }else {
            return TMSResponse.success(null);
        }

    }


    @ResponseBody
    @RequestMapping({"/saveOrUpdateNote"})
    public TMSResponse saveOrUpdateNote(@RequestBody JSONObject jsonObject)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, TMSMsgException {

        NoteVO noteVO = jsonObject.getObject("NoteVO", NoteVO.class);

        /******************************/
        //判断用户有没有登录，只有登录用户才能发布
        assertIsUserLoginIfToken(jsonObject);


        if (StringUtils.isNotBlank(noteVO.getId())){
            //只有admin,或者 帖子作者 可以修改
            UserEntity user = getCurrentUserIfToken(jsonObject);
            if (!user.isAdmin() && !user.getId().equals(noteVO.getCreateUserId())){
                throw new TMSMsgException("只有管理员和原作者可以修改");
            }
        }

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
            throw new TMSMsgException("只有管理员和原作者可以删除");
        }

        String noteId = noteVO.getId();
        cloudNoteService.deleteNote(noteId);

        return TMSResponse.success(noteId);
    }



}
