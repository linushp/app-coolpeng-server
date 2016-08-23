package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.entity.enums.AccessControl;
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
import com.coolpeng.framework.utils.ipaddr.IPAddrCallback;
import com.coolpeng.framework.utils.ipaddr.IPAddrParse;
import com.coolpeng.framework.utils.ipaddr.IPAddrResult;
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
    private ForumCategoryService forumCategoryService;

    @Autowired
    private ForumImageService forumImageService;



    public ForumPost createPost(String moduleId, String postTitle, String postContent,List<String> imageList,AccessControl accessControl)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        return createPost(moduleId,postTitle,postContent,null,imageList,accessControl);
    }


    public ForumPost updatePost(String postId,String moduleId,String myModuleId, String postTitle, String postContent, String summary, List<String> imageList,String accessControl) throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        ForumPost post = getPostById(postId,null, false);

        post.setCategoryId(moduleId);
        post.setPostTitle(postTitle);
        post.setPostContent(postContent);
        post.setSummary(summary);
        post.setAccessControl(accessControl);
        post.setMyCategoryId(myModuleId);
        post.setImageList(imageList);

        if (TmsCurrentRequest.isLogin()) {
            TmsUserEntity user = TmsCurrentRequest.getCurrentUser();
            post.setUpdateUserId(user.getId());
        }


        ForumPost.DAO.update(post);

        return post;
    }




    /**
     *
     * @param moduleId
     * @param postTitle
     * @param postContent
     * @param summary  可以为null
     * @param images   可以为null
     * @return
     * @throws FieldNotFoundException
     * @throws UpdateErrorException
     * @throws ParameterErrorException
     */
    public ForumPost createPost(String moduleId, String postTitle, String postContent,String summary,List<String> images,AccessControl accessControl)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        final ForumPost forumPost = toForumPost(moduleId, postTitle, postContent, summary, accessControl);

        if (forumPost == null) {
            this.logger.error("创建帖子失败");
            return null;
        }

        ForumCategory module = forumPost.getMyCategory();

        forumPost.setImageList(images);

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

        this.forumCategoryService.incrementPostCount(module);

        this.forumImageService.saveForumPostImageByNewPost(forumPost, images);

        //3、IP地址解析
        IPAddrParse.parseIpAddr(forumPost.getCreateIpAddr(), new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult, String resultStr) {
                if (ipAddrResult.isOk()) {
                    forumPost.setCreateIpStr(ipAddrResult.toDisplayString());
                    try {
                        ForumPost.DAO.update(forumPost);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
        });

        return forumPost;
    }

    public ForumPostReply createPostReply(String postId, String postContent,List<String> images)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        ForumPost p = ForumPost.DAO.queryForObject(postId);
        int replyCount = p.getReplyCount();
        p.setReplyCount(replyCount + 1);
        p.appendToAllImageList(images);

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
            reply.setCreateMail(user.getMail());
            reply.setCreateUserId(user.getId());
        }

        reply.setCreateIpAddr(TmsCurrentRequest.getClientIpAddr());

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



    public PageResult<ForumPost> getPostList(int pageNumber, int pageSize, String moduleId, String orderBy,AccessControl accessControl)
            throws FieldNotFoundException, ClassNotFoundException {
        return getPostList(pageNumber, pageSize, moduleId, orderBy, null,accessControl);
    }

    public PageResult<ForumPost> getPostList(int pageNumber, int pageSize, String moduleId, String orderBy, ModuleTypeEnum moduleType,AccessControl accessControl)
            throws ClassNotFoundException, FieldNotFoundException {
        QueryCondition qc = toQueryCondition(moduleId, orderBy, moduleType,accessControl);
        return getPostList(qc, pageNumber, pageSize);
    }

    public PageResult<ForumPost> getPostList(QueryCondition qc, int pageNumber, int pageSize)
            throws FieldNotFoundException, ClassNotFoundException {
        PageResult p = ForumPost.DAO.queryForPage(qc, pageNumber, pageSize, new String[]{"createUser"});

        EntityUtils.addDefaultUser(p, TmsCurrentRequest.getContext());
        EntityUtils.setAvatarUrl(p, TmsCurrentRequest.getContext());

        return p;
    }




    /**
     *
     * @param categoryId   可以为null
     * @param pageNumber
     * @param pageSize
     * @param titleLike 可以为null
     * @return
     * @throws FieldNotFoundException
     * @throws ClassNotFoundException
     */
    public PageResult<ForumPost> getPostListByMyCategoryId(String categoryId, int pageNumber, int pageSize, String titleLike)
            throws FieldNotFoundException, ClassNotFoundException {

        Map<String, String> params = new HashMap<>();
        String sql = " FROM  `t_forum_post` p  where 1=1 ";
        if (StringUtils.isNotBlank(categoryId)){
            params.put("category_id", categoryId);
            sql +=" and p.category_id =:category_id  ";
        }

        if (StringUtils.isNotBlank(titleLike)){
            sql +=" and p.post_title like CONCAT('%',:titleLike,'%')  ";
            params.put("titleLike",titleLike);
        }

        int pageBegin = (pageNumber - 1) * pageSize;
        String listSQL = "SELECT p.* " + sql + "  limit " + pageBegin + "," + pageSize + "  ";
        String countSQL = "Select count(0)  " + sql;
        PageResult<ForumPost> p = ForumPost.DAO.queryForPageBySQL(listSQL, countSQL, params);
        p.setPageSize(pageSize);
        p.setPageNumber(pageNumber);


        EntityUtils.addDefaultUser(p, TmsCurrentRequest.getContext());
        EntityUtils.setAvatarUrl(p, TmsCurrentRequest.getContext());

        return p;
    }




    private QueryCondition toQueryCondition(String moduleId, String orderBy, ModuleTypeEnum moduleType,AccessControl accessControl) {
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

        AccessControl.appendQueryCondition(accessControl,qc);

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


    /**
     * 可以为null
     * @param postId
     * @param accessControl
     * @param updateViewCount
     * @return
     * @throws UpdateErrorException
     * @throws ParameterErrorException
     * @throws FieldNotFoundException
     */
    public ForumPost getPostById(String postId,AccessControl accessControl,boolean updateViewCount) throws UpdateErrorException, ParameterErrorException, FieldNotFoundException {
        ForumPost p = ForumPost.DAO.queryForObject(postId);

        if (p == null) {
            return null;
        }

        if (accessControl!=null){
            //我想查公共的，你有不是公共的。
            if (accessControl.isPublic() && !AccessControl.parse(p.getAccessControl()).isPublic()){
                return null;
            }
        }

        if (updateViewCount) {
            int viewCount = p.getViewCount();
            p.setViewCount(viewCount + 1);
            ForumPost.DAO.update(p);
        }
        return p;
    }


    public ForumPost getPostWithReply(String postId, int pageNumber, int pageSize,AccessControl accessControl)
            throws ParameterErrorException, FieldNotFoundException, UpdateErrorException, ClassNotFoundException {

        ForumPost p = getPostById(postId,accessControl, true);

        if (p == null) {
            return null;
        }

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



    private ForumPost toForumPost(String moduleId, String postTitle, String postContent,String summary,AccessControl accessControl)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        ForumCategory module = ForumCategory.DAO.queryById(moduleId);

        if (module != null) {
            ForumPost forumPost = new ForumPost();

            if(accessControl.isPublic()){
                forumPost.setCategoryId(moduleId);
                forumPost.setCategory(module);
                forumPost.setCategoryType(module.getType());
            }

            forumPost.setMyCategoryId(moduleId);
            forumPost.setMyCategory(module);
            forumPost.setAccessControl(accessControl.toString());

            forumPost.setPostContent(postContent);
            forumPost.setLastReplyTime(DateUtil.currentTimeFormat());
            forumPost.setUpdateTime(DateUtil.currentTimeFormat());
            forumPost.setCreateTime(DateUtil.currentTimeFormat());
            if (postTitle.length() > 41) {
                postTitle = postTitle.substring(0, 40) + "...";
            }

            forumPost.setPostTitle(postTitle);

            if (StringUtils.isBlank(summary)){
                summary = StringUtils.maxSize(postContent, 500);
                summary = HtmlUtil.getTextFromHtml2(summary);
                if ((!summary.isEmpty()) && (summary.length() > 151)) {
                    summary = summary.substring(0, 150);
                    summary = summary + "......";
                }
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


}