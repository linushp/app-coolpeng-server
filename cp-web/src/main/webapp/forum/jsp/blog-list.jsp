<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/jsp/taglibs.jsp" %>
<tms:view require="ueditor" title="${belongModule.moduleName}">
    <link rel="stylesheet" href="${ctx}/forum/css/forum.css"/>
    <script src="${ctx}/common/lib/layer/layer.js"></script>
    <style>
        #mainContent {
            background-color: #FFF;
        }

        .layout-column31-left {
            float: left;
            width: 745px;
            border: 1px solid #DDDDDD;
        }

        .blog-list-wrapper1 {
            margin: 0 auto;
            width: 705px;
        }

        .layout-column31-right {
            width: 256px;
        }

        .blog-list-bg {
            background: url("${ctx}/forum/images/shubiao.jpg") no-repeat center 0px;
            display: block;
            position: relative;
            width: 100%;
            height: 300px;
        }
    </style>
    <div class="blog-list-bg"></div>
    <div class="mainContent1">
        <blog:breadWidget pageMainObject="${belongModule}"></blog:breadWidget>
    </div>
    <%--<div class="mainContent1">--%>
    <%--<div class="forum-list-topic-desc">--%>
    <%--<h3>${belongModule.moduleName}</h3>--%>
    <%--<p>${belongModule.moduleDesc}</p>--%>
    <%--</div>--%>
    <%--</div>--%>
    <div class="mainContent1">


        <div class="mainContent1">
            <div class="layout-column31-left post-list-wrapper">
                <div class="blog-list-wrapper1">
                    <h2 class="title">
            <span>${belongModule.moduleName}
              <span style="color: blueviolet;font-size: 13px;font-weight: normal;">
                [最新文章]
              </span></span>
                    </h2>

                    <blog:blogList pageResult="${postList}"></blog:blogList>


                    <div class="forum-list-pagination">
                        <tms:pagination
                                pageTotal="${postList.pageCount}"
                                pageNow="${postList.pageNumber}"
                                pageUrl="${ctx}/forum/post-list.shtml?orderBy=${orderBy}&moduleId=${belongModule.id}&pageNumber="
                                withFirstLast="false"></tms:pagination>
                    </div>


                </div>

            </div>

            <div class="layout-column31-right">
                <h2 class="title">
                    <span>最新回复</span>
                </h2>
            </div>
            <div class="clear"></div>
        </div>


        <div class="clear30"></div>


    </div>


    <c:if test="${tms:isAdmin()}">
        <div class="mainContent">
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
                    <input type="text" class="parentId" name="parentId" value="${belongModule.id}">
                    <input type="text" class="orderBy" name="orderBy" value="${orderBy}">
                    <input type="text" class="method" name="method" value="post">
                </form>
            </div>
        </div>
        <script src="${ctx}/forum/js/forum.js" type="text/javascript"></script>
    </c:if>

</tms:view>