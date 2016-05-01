<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp" %>
<div class="footer">
    <footer>
        <p>
            <span>栾海鹏的个人博客</span>

            <c:if test="${!tms:isLogin()}">
                <span id="loginButton">登录</span>
            </c:if>
            <c:if test="${tms:isLogin()}">
                欢迎您：${tms:getCurrentUser().nickname}
                <span id="logoutButton">退出</span>
            </c:if>
        </p>
    </footer>

    <script>


        jQuery(document).ready(function($){

            $(document).on("click","#loginButton",function(){

                if(!window.tmsLoginBox){
                    var url = _.path("/common/js/loginbox.js");
                    _.jsLoader(url,function(){
                        window.tmsLoginBox.popWindow();
                    });
                }else{
                    window.tmsLoginBox.popWindow();
                }

            });

            $(document).on("click","#logoutButton",function(){
                var url = _.path("/blog/user/rest/logout.shtml");
                $.post(url,{},function(d){
                    _.layer(function(layer){
                        layer.alert("成功退出",function(){
                            window.location.reload();
                        });
                    });
                },"json");
            });

        });

    </script>
</div>
