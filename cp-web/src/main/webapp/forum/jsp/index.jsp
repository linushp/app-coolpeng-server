<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/jsp/taglibs.jsp" %>
<tms:view>

    <div class="mainContent">
        <c:forEach var="g" items="${groupList}">
            <div class="groupName">${g.groupName}</div>
            <div class="clear"></div>
            <c:forEach var="m" items="${g.moduleList}">
                <div class="forum-module-item">
                    <a href="<%=application.getAttribute("ctx")%>/forum/post-list.shtml?orderBy=time&moduleId=${m.id}">
                        <div style="height: 5px"></div>
                        <img src="${m.moduleIcon}">
                        <span class="forum-module-item-name-bg"></span>
                        <span class="forum-module-item-name">${m.moduleName}（${m.postCount}）</span>
                        <span class="forum-module-item-desc">${m.moduleDesc}</span>
                    </a>
                </div>
            </c:forEach>
            <div class="clear" ></div>
        </c:forEach>

    </div>

    <style>
        .groupName{
            position: relative;
            margin: 0;
            line-height: 20px;
            padding: 6px 20px;
            font-size: 15px;
            background-color: #F7F7F7;
            height: 20px;
            border-bottom: 1px solid #eaeaea;
            font-weight: normal;
        }
        .forum-module-item {
            width: 479px;
            float: left;
            border-right: 1px solid #eaeaea;
            border-bottom: 1px solid #eaeaea;
        }
        .forum-module-item  a{
            display: block;
            background-color: #fff;
            margin-right: 1px;
            padding: 5px 20px 5px 140px;
        }
        .forum-module-item img {
            float: left;
            margin: 8px 0 0 -120px;
            width: 100px;
            height: 70px;
        }



        .forum-module-item-name {
            font-size: 14px;
            margin: 0;
            font-weight: normal;
            height: 35px;
            overflow: hidden;
            color: rgb(66, 139, 209);
            cursor: auto;
            display: block;
        }

        .forum-module-item-desc {
            color: rgb(153, 153, 153);
            cursor: auto;
            display: block;
            font-family: 'Microsoft Yahei', 'Helvetica Neue', Helvetica, Arial, sans-serif;
            font-size: 12px;
            height: 40px;
            line-height: 20px;
            list-style-image: none;
            list-style-position: outside;
            list-style-type: none;
            margin-bottom: 10px;
            margin-left: 0px;
            margin-right: 0px;
            margin-top: 0px;
            overflow-x: hidden;
            overflow-y: hidden;
            text-align: left;
        }
    </style>

    <c:if test="${currentUser!=null}">
        <div style="clear: both" class="mainContent">

            <form class="layout-container" style="margin-left: 20px" action="<%=application.getAttribute("ctx")%>/forum/createModuleGroup.shtml" method="post" target="_self">
                <div class="layout-title">
                    创建板块Group
                </div>
                <div class="layout-row">
                    板块Group名称
                    <input type="text" name="groupName">
                </div>
                <div class="layout-row">
                    板块Group描述
                    <input type="text" name="groupDesc">
                </div>
                <input style="height: 30px;width: 120px" value="创建Group模块" type="submit">
            </form>


            <div class="clear" style="height: 30px"></div>

            <form class="layout-container" style="margin-left: 20px" action="<%=application.getAttribute("ctx")%>/forum/createModule.shtml" method="post" target="_self">
                <div class="layout-title">
                    创建板块
                </div>
                <div class="layout-row">
                    板块名称
                    <input type="text" name="moduleName">
                </div>
                <div class="layout-row">
                    板块描述
                    <input type="text" name="moduleDesc">
                </div>
                <div class="layout-row">
                    板块类型
                    <input type="text" name="moduleType">
                </div>
                <div class="layout-row">
                    forumGroupId
                    <input type="text" name="forumGroupId">
                </div>
                <div class="layout-row">ICON
                    <input type="text" name="moduleIcon">
                </div>
                <input style="height: 30px;width: 120px" value="创建模块" type="submit">
            </form>
            <div class="clear" style="height: 30px"></div>
        </div>
    </c:if>
</tms:view>