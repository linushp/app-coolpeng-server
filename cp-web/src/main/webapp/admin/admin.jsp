<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
<head>
    <meta charset="gb2312">
    <title>个人博客后台管理系统</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link rel="stylesheet" href="<%=application.getAttribute("ctx")%>/common/css/common.css"/>
    <link rel="stylesheet" href="<%=application.getAttribute("ctx")%>/admin/css/admin.css"/>
</head>
<body>

<div class="admin-header">
    个人博客后台管理系统
</div>
<div class="admin-left">
    <div class="admin-left-menu">
        <ul>
            <li><a href="#home">概览</a></li>
            <li><a href="#new-post">写文章</a></li>
            <li><a href="#manage-module">模块管理</a></li>
            <li><a href="#manage-post">文章管理</a></li>
            <li><a href="#setting">设置</a></li>
        </ul>
    </div>
</div>
<div class="admin-content">
    <div ng-app="adminModule">
        <div ng-view></div>
    </div>
</div>
<div class="clear"></div>

<div class="admin-footer"></div>




<div>${tms:getGlobalConstScript()}</div>

<script src="<%=application.getAttribute("ctx")%>/common/lib/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/common/lib/underscore.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/common/lib/angular.min.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/common/lib/angular-route.min.js" type="text/javascript"></script>

<script src="<%=application.getAttribute("ctx")%>/common/js/common.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/admin/js/config.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/admin/js/homeController.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/admin/js/manageModuleController.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/admin/js/managePostController.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/admin/js/newPostController.js" type="text/javascript"></script>
<script src="<%=application.getAttribute("ctx")%>/admin/js/settingController.js" type="text/javascript"></script>
</body>
</html>