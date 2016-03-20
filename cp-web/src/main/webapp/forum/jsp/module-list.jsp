<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/jsp/taglibs.jsp" %>
<tms:view>
    <link rel="stylesheet" href="<%=application.getAttribute("ctx")%>/forum/css/forum-index.css" />
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
                    <c:if test="${tms:isAdmin()}">

                        <span class="link forum-module-modify-link"  style="position: absolute;right: 10px;bottom: 10px;background: #000;z-index: 100;display: block;width: 50px;height: 30px;text-align: center;line-height: 30px">
                            修改
                            <span style="display: none" class="forum-module-json">${tms:toJSONString(m)}</span>
                        </span>
                    </c:if>
                </div>
            </c:forEach>
            <div class="clear" ></div>
        </c:forEach>

    </div>







    <c:if test="${tms:isAdmin()}">
        <div style="clear: both" class="mainContent">
            <form class="layout-container" style="margin-left: 20px" action="<%=application.getAttribute("ctx")%>/forum/createModuleGroup.shtml" method="post" target="_self">
                <div class="layout-row">
                    <h3>创建板块Group</h3>
                </div>
                <div class="layout-row">
                    <span class="layout-title">板块Group名称</span>
                    <input type="text" name="groupName" class="layout-input">
                </div>
                <div class="layout-row">
                    <span class="layout-title">板块Group描述</span>
                    <input type="text" name="groupDesc" class="layout-input">
                </div>
                <input style="height: 30px;width: 120px" value="创建Group模块" type="submit">
            </form>

            <div class="clear" style="height: 30px"></div>

            <form id="saveModuleForm" class="layout-container" style="margin-left: 20px" action="<%=application.getAttribute("ctx")%>/forum/saveModule.shtml" method="post" target="_self">
                <div class="layout-row">
                     <h3>保存板块</h3>
                </div>
                <div class="layout-row">
                    <span class="layout-title">板块ID</span>
                    <input type="text" name="moduleId" class="layout-input ">
                </div>
                <div class="layout-row">
                    <span class="layout-title">板块名称</span>
                    <input type="text" name="moduleName" class="layout-input">
                </div>
                <div class="layout-row">
                     <span class="layout-title">板块描述</span>
                    <input type="text" name="moduleDesc" class="layout-input">
                </div>
                <div class="layout-row">
                     <span class="layout-title">板块类型</span>
                    <%--<input type="text" name="moduleType">--%>
                    <select name="moduleType" class="layout-select">
                        <%--论坛（1），问答（2），博客类型（3），留言板（4）--%>
                            <option value="1">论坛</option>
                            <option value="2">问答</option>
                            <option value="3">博客</option>
                            <option value="4">留言板</option>
                    </select>
                </div>
                <div class="layout-row">
                    <span class="layout-title">所属Group</span>
                    <%--<input type="text" name="forumGroupId" class="layout-input">--%>
                    <select name="forumGroupId" class="layout-select">
                        <c:forEach var="g" items="${groupList}">
                            <option value="${g.id}">${g.groupName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="layout-row">
                    <span class="layout-title">icon URL</span>
                    <input type="text" name="moduleIcon"  class="layout-input">
                </div>
                <input style="height: 30px;width: 120px" value="保存模块" type="submit">
            </form>
            <div class="clear" style="height: 30px"></div>
        </div>


        <script src="${ctx}/forum/js/forum-index.js" type="text/javascript"></script>

    </c:if>



</tms:view>