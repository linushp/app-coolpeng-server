<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>Login Form</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="${ctx}/admin/css/login.css" rel='stylesheet' type='text/css' />
</head>
<body>
<div class="main">
  <div class="login">
    <h1>管理系统</h1>
    <div class="inset">
      <!--start-main-->
      <form action="${ctx}/admin/login/submit.shtml" method="post" >
        <c:if test="${error}">
          <div style="color: red;font-size: 12px;">用户名或密码错误</div>
        </c:if>
        <div>
          <h2>管理登录</h2>
          <span><label>用户名</label></span>
          <span><input type="text" class="textbox" name="username" ></span>
        </div>
        <div>
          <span><label>密码</label></span>
          <span><input type="password" class="password" name="password"></span>
        </div>
        <div class="sign">
          <input type="submit" value="登录" class="submit" />
        </div>
      </form>
    </div>
  </div>
  <!--//end-main-->
</div>

<div class="copy-right">
  <p>&copy; 2015 栾海鹏. All Rights Reserved</p>

</div>

</body>
</html>