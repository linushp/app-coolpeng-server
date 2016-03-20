<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<tms:view>

  <div class="mainContent">

    <form action="${ctx}/user/login/submit.shtml" method="post" style="display: block;margin-left: 20px">

      <c:if test="${error==true}">
        <div style="color: red;line-height: 30px;font-size: 13px">用户名或密码错误</div>
      </c:if>

      <div class="layout-row">
        <span class="layout-title">用户名：</span>
        <input class="layout-input" type="text"name="username">
      </div>

      <div class="layout-row">
        <span class="layout-title">密码：</span>
        <input class="layout-input" type="password" name="password">
      </div>

      <div class="layout-row">
        <span class="layout-title">&nbsp;</span>
        <input value="登录"  type="submit">
      </div>

      <div class="layout-row">
        <span class="layout-title">&nbsp;</span>
      </div>

      <div class="layout-row">
        <span class="layout-title">&nbsp;</span>
      </div>
  </form>
  </div>

</tms:view>