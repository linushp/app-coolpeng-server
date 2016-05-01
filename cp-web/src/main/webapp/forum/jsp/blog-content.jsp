<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<tms:view require="ueditor" title="${postContent.postTitle}" name="blog-content">

  <link rel="stylesheet" href="<%=application.getAttribute("ctx")%>/forum/css/forum.css" />

  <div class="mainContent1">
    <blog:breadWidget pageMainObject="${postContent}"></blog:breadWidget>
  </div>

  <div class="mainContent forum-post">

    <c:if test="${tms:isAdmin()}">
      <div style="margin-left: 10px">
        <span class="cm-button blog-content-edit" data-id="${postContent.id}">编辑</span><span class="cm-button blog-content-delete" data-id="${postContent.id}" data-module="${postContent.forumModuleId}">删除</span>
      </div>
    </c:if>

    <div class="forum-post-content">
      <div class="forum-post-title">
          ${postContent.postTitle}
      </div>
      <div class="forum-post-create">
        <img class="forum-post-avatar" src="<%=application.getAttribute("ctx")%>${postContent.createUser.avatar}">
        <div class="forum-post-user">
          <span class="forum-post-nickname"><span>楼主：</span>${postContent.createUser.nickname}</span>
          <span class="forum-post-time" title="${postContent.createTime}">发布于：${tms:prettyDate(postContent.createTime)}</span>
          <span class="forum-post-view-count">查看：${postContent.viewCount}次 &nbsp;  跟帖：${postContent.replyCount}贴 </span>
        </div>
      </div>
      <div class="forum-post-text">${postContent.postContent}</div>
    </div>
    <c:forEach var="d" items="${postContent.replyPageResult.pageData}" >
      <div class="forum-post-content">
        <div class="forum-post-create">
          <img class="forum-post-avatar" src="<%=application.getAttribute("ctx")%>${d.createUser.avatar}">
          <div class="forum-post-user">
            <span class="forum-post-nickname">${d.createUser.nickname}</span>
            <span class="forum-post-time" title="${d.createTime}">发布于：${tms:prettyDate(d.createTime)}</span>

            <c:if test="${tms:isAdmin()}">
              <span class="blog-reply-delete" style="cursor: pointer" data-id="${d.id}">删除</span>
            </c:if>

          </div>
        </div>
        <div class="forum-post-text">${d.replyContent}</div>
      </div>
    </c:forEach>

    <div class="forum-post-pagination">
      <tms:pagination
              pageTotal="${postContent.replyPageResult.pageCount}"
              pageNow="${postContent.replyPageResult.pageNumber}"
              pageUrl="${ctx}/forum/post-content.shtml?postId=${postContent.id}&pageNumber="
              withFirstLast="false"></tms:pagination>
    </div>


    <%--<c:if test="${tms:isAdmin()}">--%>


      <div style="margin:0 auto;width: 95%">
        <div class="forum-new-tips">
          <span class="forum-new-tips-icon"></span>
          <span style="font-weight: 700;color: #000;font-size: 13px;">发表回复</span>
        </div>
          <%--<input type="text" placeholder="请输入标题" class="forum-new-title">--%>
        <div id="forum-editor" style="height: 250px"></div>
        <div style="height: 20px"></div>
        <span class="forum-new-button">发布新帖</span>
        <div style="height: 20px"></div>
        <form style="display: none" id="forum-new-form" action="${ctx}/forum/createPost.shtml" method="post">
          <input type="text" class="postTitle" name="postTitle">
          <input type="text" class="postContent" name="postContent">
          <input type="text" class="parentId" name="parentId" value="${postContent.id}">
          <input type="text" class="method" name="method" value="reply">
        </form>
      </div>
      <script src="<%=application.getAttribute("ctx")%>/forum/js/forum.js" type="text/javascript"></script>

    <%--</c:if>--%>





  </div>


</tms:view>