<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<tms:view require="ueditor" title="${belongModule.moduleName}">
    <link rel="stylesheet" href="${ctx}/forum/css/forum.css" />
    <script src="${ctx}/common/lib/layer/layer.js"></script>
    <div class="mainContent1">
        <blog:breadWidget pageMainObject="${belongCategory}"></blog:breadWidget>
    </div>
    <div class="mainContent1">
        <div class="forum-list-topic-desc">
            <h3>${belongCategory.name}</h3>
            <p>${belongCategory.desc}</p>
        </div>
        <div class="">
            <div class="forum-list-menu1">
                <div style="height: 10px"></div>
                <ul>
                    <li class='${orderBy!="hot"?"on":""}'><a href="${ctx}/forum/post-list.shtml?orderBy=time&moduleId=${belongCategory.id}">最新</a></li>
                    <li class='${orderBy=="hot"?"on":""}'><a href="${ctx}/forum/post-list.shtml?orderBy=hot&moduleId=${belongCategory.id}">最热</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="mainContent forum-list" >
        <c:forEach var="d" items="${postList.pageData}" >
            <div class="forum-list-item">
                <img class="forum-list-avatar" src="${d.createAvatar}">
                <div class="forum-list-msg">
                    <a class="forum-list-title" href="${ctx}/forum/post-content.shtml?postId=${d.id}">${d.postTitle}</a>
                    <span class="forum-list-summary">${d.summary}</span>
                </div>
                <div class="forum-list-item-right">
                        <span class="col1">${d.createUser.nickname}</span>
                        <span class="col2">${d.viewCount} 次</span>
                        <span class="col1">${tms:prettyDate(d.createTime)}</span>
                        <span class="col2">${d.replyCount} 贴</span>
                </div>
                <div class="clear"></div>

                <div class="forum-list-images">
                    <div class="forum-list-img-middle1">
                        <div class="forum-list-img-oper">
                            <span class="view-big"><span class="icon"></span>查看大图</span>
                            <span class="add-comment"><span class="icon"></span>添加注释</span>
                        </div>
                        <div class="forum-list-img-middle3">
                            <img class="forum-list-img-middle2">
                        </div>
                        <div class="forum-list-img-middle4"></div>
                    </div>
                    <div class="forum-list-img-small">
                        <c:forEach var="img" items="${d.imageList}" >
                            <div class="forum-list-img2">
                                <img class="forum-list-img" imgxx="${tms:toImageThumb(img,600,90)}" src="${tms:toImageThumb(img,80,90)}" >
                            </div>
                        </c:forEach>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </c:forEach>
        <div class="forum-list-pagination">
            <tms:pagination
                    pageTotal="${postList.pageCount}"
                    pageNow="${postList.pageNumber}"
                    pageUrl="${ctx}/forum/post-list.shtml?orderBy=${orderBy}&moduleId=${belongModule.id}&pageNumber="
                    withFirstLast="false"></tms:pagination>
        </div>

        <div style="margin:0 auto;width: 95%">
            <div class="forum-new-tips">
                <span class="forum-new-tips-icon"></span>
                <span style="font-weight: 700;color: #000;font-size: 13px;">发表帖子</span>
            </div>
            <input type="text" placeholder="请输入标题" class="forum-new-title">
            <div id="forum-editor" style="height: 250px"></div>
            <div style="height: 20px"></div>
            <span class="forum-new-button">发布新帖</span>
            <div style="height: 20px"></div>
            <form style="display: none" id="forum-new-form" action="${ctx}/forum/createPost.shtml" method="post">
                <input type="text" class="postTitle" name="postTitle">
                <input type="text" class="postContent" name="postContent">
                <input type="text" class="parentId" name="parentId" value="${belongCategory.id}">
                <input type="text" class="orderBy" name="orderBy" value="${orderBy}">
                <input type="text" class="method" name="method" value="post">
            </form>
        </div>

        <script src="${ctx}/forum/js/forum.js" type="text/javascript"></script>
    </div>


</tms:view>