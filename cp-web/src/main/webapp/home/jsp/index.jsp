<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/jsp/taglibs.jsp" %>
<!doctype html>
<html>
<head>
<title>朱凤珠和栾海鹏</title>
<meta name="Keywords" content="朱凤珠栾海鹏的技术博客,C语言" >
<meta name="Description" content="朱凤珠栾海鹏的技术博客" >
  <meta http-equiv='X-UA-Compatible' content='IE=EDGE'>
<!--[if lt IE 9]>
<script src="${ctx}/home/js/modernizr.js"></script>
<![endif]-->
 <script type='text/javascript'>
 var APPLICATION_CONTEXT='${ctx}';
</script>
  

</head>
<body>
<link href="${ctx}/home/css/index.css" rel="stylesheet">
<%@ include file="/common/jsp/header.jsp" %>




<div class="photowall">
  <ul class="wall_a">
    <li><a href="/"><img src="${ctx}/home/images/p01.jpg" style="width: 297px;height: 158px">
      <figcaption>
        <h2>不再因为别人过得好而焦虑，在没有人看得到你的时候依旧能保持节奏 </h2>
      </figcaption>
      </a></li>
    <li><a href="/"><img src="${ctx}/home/images/p02.jpg" style="width: 204px;height: 158px">
      <figcaption>
        <h2>不再因为别人过得好而焦虑，在没有人看得到你的时候依旧能保持节奏 </h2>
      </figcaption>
      </a></li>
    <li><a href="/"><img src="${ctx}/home/images/p03.jpg" style="width: 239px;height: 158px">
      <figcaption>
        <h2>不再因为别人过得好而焦虑，在没有人看得到你的时候依旧能保持节奏 </h2>
      </figcaption>
      </a></li>
    <li>
      <p class="text_a"><a href="/">一个人最好的模样大概是平静一点，坦然接受自己所有的弱点，不再因为别人过得好而焦虑，在没有人看得到你的时候依旧能保持节奏......</a></p>
    </li>
    <li><a href="/"><img src="${ctx}/home/images/p04.jpg" style="width: 297px;height: 195px">
      <figcaption>
        <h2>不再因为别人过得好而焦虑，在没有人看得到你的时候依旧能保持节奏 </h2>
      </figcaption>
      </a></li>
    <li>
      <p class="text_b"><a href="/">逃避自己的人，最终只能导致自己世界的崩塌，而变得越来越没有安全感。</a></p>
    </li>
    <li><a href="/"><img src="${ctx}/home/images/p05.jpg" style="width: 239px;height: 195px">
      <figcaption>
        <h2>不再因为别人过得好而焦虑，在没有人看得到你的时候依旧能保持节奏 </h2>
      </figcaption>
      </a></li>
    <li><a href="/"><img src="${ctx}/home/images/p06.jpg" style="width: 260px;height: 195px">
      <figcaption>
        <h2>不再因为别人过得好而焦虑，在没有人看得到你的时候依旧能保持节奏 </h2>
      </figcaption>
      </a></li>
  </ul>
</div>







<div class="full-title1">
  <div class="mainContent1">
    <span>精彩内容</span>
  </div>
</div>
<div style="background: #FFFFFF">
    <div class="mainContent1">
      <div class="clear30"></div>
      <div class="layout-column31-left post-list-wrapper">
          <h2 class="title">
            <span>最新博文</span>
          </h2>
          <blog:blogList pageResult="${postList}"></blog:blogList>
      </div>

      <div class="layout-column31-right">
        <h2 class="title">
          <span>最新回复</span>
        </h2>
        <blog:lastReply replyList="${lastReplyList}"></blog:lastReply>
      </div>
      <div class="clear"></div>
    </div>
</div>








<div class="full-title1">
  <div class="mainContent1">
    <span>话题广场</span>
  </div>
</div>
<div style="background: #ededef">
  <div class="mainContent">
    <div class="module-list-wrapper">

      <div class="clear10"></div>
      <blog:moduleList moduleList="${categoryList}"></blog:moduleList>
      <div class="clear10"></div>
    </div>
    <div class="clear"></div>
  </div>
</div>


<div class="full-title1">
  <div class="mainContent1">
    <span>寄语</span>
  </div>
</div>



<div class="about">
  <ul>
    <div id="fountainG">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </div>
    <div class="about_girl"><span><a href="/"><img src="${ctx}/home/images/girl.jpg" style="width: 130px;height: 130px"></a></span>
      <p>初遇时，他的幽默，他往日的深情、承诺和傻劲儿，两个人共度的美丽时刻，一一印在我的回忆里....</p>
    </div>
    <div class="about_boy"><span><a href="/"><img src="${ctx}/home/images/boy.jpg"  style="width: 130px;height: 130px"></a></span>
      <p>初遇时，她的热情，她腼腆的微笑、可爱和气质，两个人共度的愉快时刻，一一印在我的回忆里...</p>
    </div>

    <div class="about_girl">
      <span><a href="https://github.com/luanhaipeng">
      <img src="${ctx}/home/images/github.png"  style="width: 130px;height: 130px"></a></span>
      <p>喜欢编程的人就要有一个自己的Github账号。分享的内容虽然不多，但是我一直在努力。嘻嘻嘻~~~~ &nbsp;&nbsp;&nbsp;
        去看一看 <a style="color: #C5C5C5" href="https://github.com/luanhaipeng" target="_blank">栾海鹏的GitHub</a></p>
    </div>

    <div class="about_boy"><span><a href="http://www.cnblogs.com/lhp2012/"><img src="${ctx}/home/images/222.png"  style="width: 130px;height: 130px"></a></span>
      <p>我也有一个自己的cnblog账号，自己太懒了，很少写东西，希望以后能好好写博客。。囧囧囧~~~ &nbsp;&nbsp;&nbsp;
        去看一看 <a style="color: #78964C" href="http://www.cnblogs.com/lhp2012/" target="_blank">栾海鹏的cnblog</a></p>
    </div>

  </ul>
</div>
<div class="blank"></div>
<div class="blog">

<c:forEach var="d" items="${newBlogPostList.pageData}" >
  <figure>
    <a href="${ctx}/forum/post-content.shtml?postId=${d.id}">
      <c:forEach var="img" items="${d.imageList}" >
        <img class="forum-list-img" src="${img}" >
      </c:forEach>
      <c:if test="${d.imageList==null || d.imageList.isEmpty()}">
        <img src="${ctx}/home/images/t02.jpg" style="width: 300px;height: 256px;">
      </c:if>
    </a>
    <p><a href="${ctx}/forum/post-content.shtml?postId=${d.id}" >${d.postTitle}</a></p>
    <figcaption>${d.summary}</figcaption>
  </figure>
</c:forEach>

</div>
<div class="blank"></div>
<div class="text6">Happy・相守</div>
<div class="hope">
  <ul>
    <div class="visitors">
      <dl>
        <dt><img src="${ctx}/home/images/s6.jpg"> </dt>
        <dd><a href="/">DanceSmile</a> </dd>
        <dd>你们本就是天生一对，地造一双，而今共偕连理，今后更需彼此宽容、互相照顾，祝福你们！</dd>
      </dl>
      <dl>
        <dt><img src="${ctx}/home/images/s7.jpg"> </dt>
        <dd><a href="/">骄傲的小甜甜</a> </dd>
        <dd>十年修得同船渡，百年修得共枕眠。于茫茫人海中找到她，分明是千年前的一段缘，祝你俩幸福美满，共谐连理。</dd>
      </dl>
      <dl>
        <dt><img src="${ctx}/home/images/s8.jpg"> </dt>
        <dd><a href="/">执子之手</a> </dd>
        <dd>托清风捎去衷心的祝福，让流云奉上真挚的情意；今夕何夕，空气里都充满了醉人的甜蜜。谨祝我最亲爱的朋友，从今后，爱河永浴！</dd>
      </dl>
    </div>
  </ul>
</div>
<%--<footer>--%>
  <%--<p>栾海鹏的个人博客 <span id="loginButton">登录</span></p>--%>
<%--</footer>--%>
<%@ include file="/common/jsp/footer.jsp" %>
</body>
</html>
