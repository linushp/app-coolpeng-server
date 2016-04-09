<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tms" uri="/WEB-INF/tld/tms-view.tld" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="com.coolpeng.blog.utils.HeaderUtils" %>
<%
    HeaderUtils.setHeaderMenuGroup(request);
    application.setAttribute("ctx", request.getScheme() + "://" + request.getHeader("host") + request.getContextPath());
%>

<link rel="stylesheet" href="<%=application.getAttribute("ctx")%>/common/css/common.css"/>
<link rel="stylesheet" href="<%=application.getAttribute("ctx")%>/common/css/widget.css"/>
<script src="<%=application.getAttribute("ctx")%>/common/lib/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/common/lib/underscore.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/common/lib/angular.min.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/common/js/common.js" type="text/javascript"></script>

<div class="header_blog">
    <div style="height: 60px"></div>
    <div class="quotes">
        <p>初遇时，她的微笑，她往日的深情、承诺和傻劲，两个人共度的美丽时刻，一一印在回忆里，今天的感情已经比不上从前，但是我爱着恋着往日的她，舍不得离开。</p>

        <div class="text5">Coding・记录</div>
        <div class="flower"><img src="${ctx}/home/images/flower.jpg"></div>
    </div>
    <!--nav begin-->
    <div id="nav">
        <ul>
            <li class="header_blog_nav_li"><a href="${ctx}/home.shtml">首页</a></li>
            <c:forEach var="g" items="${headerMenuGroup}">

                <c:if test="${g.status==0}">

                    <li class="header_blog_nav_li">
                        <c:if test="${tms:isSizeEqual(g.moduleList,1)}">
                            <c:forEach var="m" items="${g.moduleList}">
                                <c:if test="${m.status==0}">
                                    <a href="${ctx}/forum/post-list.shtml?orderBy=time&moduleId=${m.id}">${m.moduleName}</a>
                                </c:if>
                            </c:forEach>
                        </c:if>

                        <c:if test="${!tms:isSizeEqual(g.moduleList,1)}">
                            <span style="color: #FFFFFF;cursor: default;"> ${g.groupName}</span>
                            <ul>
                                <div class="clear20"></div>
                                <c:forEach var="m" items="${g.moduleList}">
                                    <c:if test="${m.status==0}">
                                        <li>
                                            <a href="${ctx}/forum/post-list.shtml?orderBy=time&moduleId=${m.id}">${m.moduleName}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <div class="clear20"></div>
                            </ul>
                        </c:if>
                    </li>

                </c:if>

            </c:forEach>
        </ul>
    </div>
    <!--nav end-->
</div>