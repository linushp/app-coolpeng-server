<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/jsp/taglibs.jsp" %>
<tms:view title="留言板">
    <link rel="stylesheet" href="${ctx}/forum/css/leave-msg.css"/>

    <div class="mainContent">

        <div class="leave-msg-list">
            <c:forEach var="d" items="${postList.pageData}">
                <div class="leave-msg-item" id="leave-msg-item-${d.id}">
                    <div class="leave-msg-item-avatar">
                        <img src="${d.createAvatar}">
                    </div>
                    <div class="leave-msg-item-right">
                        <div class="layout-row">
                            <span class="createNickname">${d.createNickname}</span>
                            <span class="createTime">${tms:prettyDate(d.createTime)}</span>
                        </div>
                        <div class="layout-row">
                            <span style="font-size: 13px;line-height: 24px;">${d.summary}</span>
                        </div>
                        <div style="height: 14px;margin-top: 20px">
                        <span class="likeCountBtn" data-id="${d.id}">
                            <span class="icon-like"></span><span class="text">（${d.likeCount}）</span>
                        </span>
                            <c:if test="${tms:isAdmin()}">
                            <span class="replyBtn" data-id="${d.id}">
                                <span class="icon-reply"></span> <span class="text"> 回复</span>
                            </span>
                            </c:if>

                        </div>


                        <div class="leave-msg-item-reply"></div>


                        <c:if test="${tms:isNotEmpty(d.lastReplyMsg)}">
                            <div class="leave-msg-item-reply1">
                                <div class="leave-msg-item-avatar">
                                    <img src="${tms:url(d.lastReplyAvatar)}">
                                </div>
                                <div class="leave-msg-item-right" style="width: 700px">
                                    <div class="layout-row">
                                        <span class="createNickname">${d.lastReplyNickname}</span>
                                        <span class="createTime">${tms:prettyDate(d.lastReplyTime)}</span>
                                    </div>
                                    <div class="layout-row">
                                        <span style="font-size: 13px;line-height: 24px;">${d.lastReplyMsg}</span>
                                    </div>
                                </div>
                                <div class="clear"></div>
                                </div>
                        </c:if>

                    </div>
                    <div class="clear"></div>
                </div>
            </c:forEach>


            <div style="margin-top: 10px">
                <tms:pagination
                        pageTotal="${postList.pageCount}"
                        pageNow="${postList.pageNumber}"
                        pageUrl="${ctx}/forum/post-list.shtml?orderBy=${orderBy}&moduleId=${belongModule.id}&pageNumber="
                        withFirstLast="false"></tms:pagination>
            </div>

        </div>

        <div class="leave-msg-form-box">
            <form id="leave-msg-form" action="${ctx}/forum/createLeaveMessage.shtml" method="post">

                <div class="layout-row">
                    <span class="layout-title">您的昵称：</span>
                    <input type="text" class="layout-input" name="nickname">
                </div>

                <div class="layout-row">
                    <span class="layout-title">选择头像：</span>

                    <div>
                        <c:forEach step="1" var="d" begin="0" end="9">
                            <label class="leave-msg-avatar">
                                <img src="${ctx}/forum/images/avatar/${d}.png"/>
                                <input type="radio" value="/forum/images/avatar/${d}.png" name="avatar">
                            </label>
                        </c:forEach>
                    </div>
                    <div class="clear" style="height: 10px"></div>
                </div>

                <div class="layout-row">
                    <span class="layout-title">留言内容：</span>
                    <textarea style="width: 500px;height: 80px" class="layout-textarea" name="postContent"></textarea>
                </div>
                <div class="layout-row">
                    <span class="layout-title">验证码：</span>
                    <input type="text" name="captcha" class="layout-input" style="float: left">
                    <img id="leave-msg-captcha" src="${ctx}/captcha.shtml?_${tms:randomInt()}">

                    <div style="height: 10px" class="clear"></div>
                </div>

                <div class="layout-row">
                    <span class="layout-title">&nbsp;</span>
                    <input type="submit" class="layout-submit" value="提交留言">
                </div>

                <input style="display: none" type="text" value="留言板" class="postTitle" name="postTitle">
                <input style="display: none" type="text" class="parentId" name="parentId" value="${belongModule.id}">
                <input style="display: none" type="text" class="method" name="method" value="leaveMsg">

                <div style="height: 10px"></div>
            </form>
        </div>
        <div style="height: 10px"></div>
    </div>


    <script src="${ctx}/forum/js/leave-msg.js" type="text/javascript"></script>
</tms:view>