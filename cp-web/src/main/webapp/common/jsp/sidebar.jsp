<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<div class="sidebar">
  <ul class="sidebar-menu">
    <li class="menu-item" data-name="instance-list">
        <a href="<%=application.getAttribute("ctx")%>/manage-node/index.jsp">实例管理</a>
    </li>
    <li class="menu-item" data-name="node-list">
      <a href="<%=application.getAttribute("ctx")%>/manage-node/node-list.jsp">Node管理</a>
    </li>
  </ul>
</div>