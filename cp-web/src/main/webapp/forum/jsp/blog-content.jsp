<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<tms:view require="ueditor">

  <link rel="stylesheet" href="<%=application.getAttribute("ctx")%>/forum/css/forum.css" />

  <div class="mainContent1">
    <blog:breadWidget pageMainObject="${postContent}"></blog:breadWidget>
  </div>

  <div class="mainContent forum-post">
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


    <%--<div style="margin:0 auto;width: 95%">--%>
      <%--<div class="forum-new-tips">--%>
        <%--<span class="forum-new-tips-icon"></span>--%>
        <%--<span style="font-weight: 700;color: #000;font-size: 13px;">发表回复</span>--%>
      <%--</div>--%>
      <%--<form id="forum-new-form" action="${ctx}/forum/createPost.shtml" method="post">--%>
        <%--<div style="height: 20px"></div>--%>
        <%--<input class="forum-new-button" type="submit" value="回复">--%>
        <%--<div style="height: 20px"></div>--%>

        <%--<div style="display: none;">--%>
          <%--<input type="text" class="postTitle" name="postTitle">--%>
          <%--<input type="text" class="postContent" name="postContent">--%>
          <%--<input type="text" class="parentId" name="parentId" value="${postContent.id}">--%>
          <%--<input type="text" class="method" name="method" value="reply">--%>
        <%--</div>--%>

      <%--</form>--%>
    <%--</div>--%>





  </div>


</tms:view>