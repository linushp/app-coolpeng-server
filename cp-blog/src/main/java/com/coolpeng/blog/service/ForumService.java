package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.entity.enums.ModuleTypeEnum;
import com.coolpeng.blog.utils.EntityUtils;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.QueryCondition;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.mvc.TmsUserEntity;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.HtmlUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ForumService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ForumHomeService forumHomeService;

    @Autowired
    private ForumModuleService forumModuleService;

    @Autowired
    private ForumImageService forumImageService;


    /**
     * 创建一篇新的帖子
     *
     * @param moduleId
     * @param postTitle
     * @param postContent
     * @return
     * @throws FieldNotFoundException
     * @throws UpdateErrorException
     * @throws ParameterErrorException
     */
    public ForumPost createPost(String moduleId, String postTitle, String postContent)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        ForumPost forumPost = toForumPost(moduleId, postTitle, postContent);

        if (forumPost == null) {
            this.logger.error("创建帖子失败");
            return null;
        }

        ForumModule module = forumPost.getForumModule();

        String content = forumPost.getPostContent();
        List<String> images = getImageUrlListFromContent(content);
        forumPost.addImageList(images);

        if (TmsCurrentRequest.isLogin()) {
            TmsUserEntity user = TmsCurrentRequest.getCurrentUser();
            forumPost.setCreateUserId(user.getId());
            forumPost.setCreateAvatar(user.getAvatar());
            forumPost.setCreateMail(user.getMail());
            forumPost.setCreateNickname(user.getNickname());
            forumPost.setLikeCount(0);
            forumPost.setLastReplyMsg("");
        }

        ForumPost.DAO.save(forumPost);

        this.forumModuleService.updatePostCount(module);

        this.forumHomeService.updateForumHome(forumPost, images);

        this.forumImageService.saveForumPostImageByNewPost(forumPost, images);

        return forumPost;
    }

    public ForumPostReply createPostReply(String postId, String postContent)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        ForumPost p = ForumPost.DAO.queryForObject(postId);
        int replyCount = p.getReplyCount();
        p.setReplyCount(replyCount + 1);
        List<String> images = getImageUrlListFromContent(postContent);
        p.addImageList(images);

        String summary = HtmlUtil.getTextFromHtml2(postContent);
        summary = StringUtils.maxSize(summary, 200);
        if (StringUtils.isNotBlank(summary)) {
            p.setLastReplyMsg(summary);
            p.setLastReplyTime(DateUtil.currentTimeFormat());

            if (TmsCurrentRequest.isLogin()) {
                TmsUserEntity user = TmsCurrentRequest.getCurrentUser();
                p.setLastReplyUserId(user.getId());
                p.setLastReplyAvatar(user.getAvatar());
                p.setLastReplyMail(user.getMail());
                p.setLastReplyNickname(user.getNickname());
            }
        }

        ForumPost.DAO.update(p);


        ForumPostReply reply = new ForumPostReply();
        reply.setForumPostId(postId);
        reply.setForumPostTitle(p.getPostTitle());
        reply.setFloorNumber("" + (replyCount + 1));
        reply.setReplyContent(postContent);
        reply.setReplySummary(summary);

        if (TmsCurrentRequest.isLogin()) {
            TmsUserEntity user = TmsCurrentRequest.getCurrentUser();
            reply.setCreateAvatar(user.getAvatar());
            reply.setCreateNickname(user.getNickname());
            reply.setCreateIpAddr(TmsCurrentRequest.getClientIpAddr());
            reply.setCreateMail(user.getMail());
            reply.setCreateUserId(user.getId());
        }

        ForumPostReply.DAO.insert(reply);

        this.forumImageService.saveForumPostImageByNewReply(reply, images);

        return reply;
    }

    public void updatePostLikeCount(String postId, int likeCount)
            throws UpdateErrorException {
        Map updateFields = new HashMap();

        updateFields.put("likeCount", Integer.valueOf(likeCount));

        ForumPost.DAO.updateFields(postId, updateFields);
    }

    public void updatePostRecommend(String postId, boolean isRecommend)
            throws UpdateErrorException {
        Map updateFields = new HashMap();
        if (isRecommend)
            updateFields.put("recommend", Integer.valueOf(1));
        else {
            updateFields.put("recommend", Integer.valueOf(0));
        }

        updateFields.put("updateTime", DateUtil.currentTimeFormat());

        ForumPost.DAO.updateFields(postId, updateFields);
    }

    public PageResult<ForumPost> getPostListRecommended(int pageNumber, int pageSize, ModuleTypeEnum moduleType, String moduleId)
            throws ClassNotFoundException, FieldNotFoundException {
        QueryCondition qc = toQueryCondition(moduleId, null, moduleType);

        qc.setOrderDesc("updateTime");
        qc.addEqualCondition("recommend", Integer.valueOf(1));

        return getPostList(qc, pageNumber, pageSize);
    }

    public PageResult<ForumPost> getPostListByModuleType(int pageNumber, int pageSize, ModuleTypeEnum moduleType) throws ClassNotFoundException, FieldNotFoundException {
        return getPostListByModuleType(pageNumber, pageSize, moduleType, null);
    }

    public PageResult<ForumPost> getPostListByModuleType(int pageNumber, int pageSize, ModuleTypeEnum moduleType, String orderBy) throws FieldNotFoundException, ClassNotFoundException {
        return getPostList(pageNumber, pageSize, null, orderBy, moduleType);
    }

    public PageResult<ForumPost> getPostList(int pageNumber, int pageSize, String moduleId)
            throws FieldNotFoundException, ClassNotFoundException {
        return getPostList(pageNumber, pageSize, moduleId, null, null);
    }

    public PageResult<ForumPost> getPostList(int pageNumber, int pageSize, String moduleId, String orderBy)
            throws FieldNotFoundException, ClassNotFoundException {
        return getPostList(pageNumber, pageSize, moduleId, orderBy, null);
    }

    public PageResult<ForumPost> getPostList(int pageNumber, int pageSize, String moduleId, String orderBy, ModuleTypeEnum moduleType)
            throws ClassNotFoundException, FieldNotFoundException {
        QueryCondition qc = toQueryCondition(moduleId, orderBy, moduleType);
        return getPostList(qc, pageNumber, pageSize);
    }

    public PageResult<ForumPost> getPostList(QueryCondition qc, int pageNumber, int pageSize)
            throws FieldNotFoundException, ClassNotFoundException {
        PageResult p = ForumPost.DAO.queryForPage(qc, pageNumber, pageSize, new String[]{"createUser"});

        EntityUtils.addDefaultUser(p, TmsCurrentRequest.getContext());

        EntityUtils.setAvatarUrl(p, TmsCurrentRequest.getContext());

        return p;
    }

    private QueryCondition toQueryCondition(String moduleId, String orderBy, ModuleTypeEnum moduleType) {
        QueryCondition qc = new QueryCondition();

        if ("hot".equals(orderBy)) {
            qc.setOrderDesc("viewCount");
        } else {
            qc.setOrderDesc("lastReplyTime");
        }

        if (moduleId != null) {
            qc.addEqualCondition("forumModuleId", moduleId);
        }

        if (moduleType != null) {
            qc.addEqualCondition("moduleType", Integer.valueOf(moduleType.getValue()));
        }

        return qc;
    }

    public List<ForumPostReply> getLastPostReplyByModuleId(int pageCount, String moduleId) {

        Map<String, Object> params = new HashMap<>();
        params.put("moduleId", moduleId);

        List<ForumPost> posts = ForumPost.DAO.queryForList("SELECT * FROM t_forum_post where last_reply_msg !='' and forum_module_id=:moduleId order by last_reply_time desc limit 0,100;", params);

        List<ForumPostReply> replyList = new ArrayList<>();
        for (ForumPost post : posts) {
            //过滤掉空回复
            if (StringUtils.isNotBlank(post.getLastReplyMsg())) {
                replyList.add(post.extractLastReply());
            }
        }

        if (replyList.size() > pageCount) {
            replyList = replyList.subList(0, pageCount);
        }

        EntityUtils.addDefaultUser(replyList, TmsCurrentRequest.getContext());
        EntityUtils.setReplyAvatarUrl(replyList, TmsCurrentRequest.getContext());

        return replyList;
    }

    public List<ForumPostReply> getLastPostReply(int pageCount) throws FieldNotFoundException, ClassNotFoundException {


        List<ForumPost> posts = ForumPost.DAO.queryForList("SELECT * FROM t_forum_post where last_reply_msg !='' order by last_reply_time desc limit 0,100", null);

        List<ForumPostReply> replyList = new ArrayList<>();
        for (ForumPost post : posts) {
            //过滤掉空回复
            if (StringUtils.isNotBlank(post.getLastReplyMsg())) {
                replyList.add(post.extractLastReply());
            }
        }

        if (replyList.size() > pageCount) {
            replyList = replyList.subList(0, pageCount);
        }

        EntityUtils.addDefaultUser(replyList, TmsCurrentRequest.getContext());
        EntityUtils.setReplyAvatarUrl(replyList, TmsCurrentRequest.getContext());

        return replyList;

    }

    public ForumPost getPostWithReply(String postId, int pageNumber, int pageSize)
            throws ParameterErrorException, FieldNotFoundException, UpdateErrorException, ClassNotFoundException {
        ForumPost p = (ForumPost) ForumPost.DAO.queryForObject(postId);
        int viewCount = p.getViewCount();
        p.setViewCount(viewCount + 1);
        ForumPost.DAO.update(p);

        String createUserId = p.getCreateUserId();
        UserEntity createUser = null;
        if (createUserId != null) {
            createUser = (UserEntity) UserEntity.DAO.queryForObject(createUserId);
        }
        if (createUser == null) {
            createUser = EntityUtils.getDefaultUser(TmsCurrentRequest.getContext());
        }
        p.setCreateUser(createUser);

        QueryCondition qc = new QueryCondition();
        qc.addEqualCondition("forumPostId", postId);

        PageResult<ForumPostReply> replyPageResult = ForumPostReply.DAO.queryForPage(qc, pageNumber, pageSize, new String[]{"createUser"});

        EntityUtils.addDefaultUser(replyPageResult, TmsCurrentRequest.getContext());
        EntityUtils.setReplyAvatarUrl(replyPageResult, TmsCurrentRequest.getContext());

        p.setReplyPageResult(replyPageResult);

        return p;
    }

    private List<String> getImageUrlListFromContent(String content) {
        List urlList = new ArrayList();
        if (StringUtils.isBlank(content)) {
            return urlList;
        }

        String reg = "img src=\"http://coolpeng\\.bj\\.bcebos\\.com/dragon_ball/upload.{25,35}\\..{3,4}\"";
        Pattern p = Pattern.compile(reg, 2);
        Matcher matcher = p.matcher(content);

        while (matcher.find()) {
            String x = matcher.group();
            x = x.replaceFirst("img src=\"", "");
            x = x.replace("\"", "");
            x = x.replace("http://coolpeng.bj.bcebos.com/", "");
            urlList.add(x);
        }
        return urlList;
    }

    private ForumPost toForumPost(String moduleId, String postTitle, String postContent)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        ForumModule module = this.forumModuleService.getForumModule(moduleId);

        if (module != null) {
            ForumPost forumPost = new ForumPost();
            forumPost.setPostContent(postContent);
            forumPost.setForumModuleId(moduleId);
            forumPost.setForumModule(module);
            forumPost.setModuleType(module.getModuleType());
            forumPost.setLastReplyTime(DateUtil.currentTimeFormat());
            forumPost.setUpdateTime(DateUtil.currentTimeFormat());
            forumPost.setCreateTime(DateUtil.currentTimeFormat());
            if (postTitle.length() > 41) {
                postTitle = postTitle.substring(0, 40) + "...";
            }

            forumPost.setPostTitle(postTitle);

            String summary = StringUtils.maxSize(postContent, 500);
            summary = HtmlUtil.getTextFromHtml2(summary);
            if ((!summary.isEmpty()) && (summary.length() > 151)) {
                summary = summary.substring(0, 150);
                summary = summary + "......";
            }

            forumPost.setSummary(summary);

            String ip = TmsCurrentRequest.getClientIpAddr();
            forumPost.setCreateIpAddr(ip);

            return forumPost;
        }
        this.logger.error("找不到ForumModule，moduleId={}", moduleId);

        return null;
    }

    public ForumPost getForumPost(String postId) throws ParameterErrorException, FieldNotFoundException {
        return (ForumPost) ForumPost.DAO.queryForObject(postId);
    }

    public void batchUpdatePost(ForumModule forumModule) throws UpdateErrorException {
        String moduleId = forumModule.getId();

        Map updateFields = new HashMap();

        updateFields.put("moduleType", Integer.valueOf(forumModule.getModuleType()));

        ForumPost.DAO.batchUpdateFields("forumModuleId", moduleId, updateFields);
    }
}