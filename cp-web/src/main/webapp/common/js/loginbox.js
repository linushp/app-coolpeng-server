(function(window){

    var $ = window.jQuery;


    var template='' +
        '<div style="width: 260px;margin: 20px auto">' +
        '   <table>' +
        '       <tr style="height: 45px">' +
        '           <td>用户名：</td>' +
        '           <td><input class="layout-input username" type="text"></td>' +
        '       </tr>' +
        '       <tr>' +
        '           <td>密 码：</td>' +
        '           <td><input class="layout-input password" type="password"></td>' +
        '       </tr>' +
        '   </table>' +
        '</div>';



    var config={
        type: 1,
        title: "登录",
        area: ['510px', '230px'],
        shadeClose: true, //点击遮罩关闭
        content: template,
        btn: ['确定', "取消"],
        yes: function (index, $content) {

            var username = $content.find(".username").val();
            var password = $content.find(".password").val();

            var url = _.path("/blog/user/rest/login.shtml");
            $.post(url,{
                username:username,
                password:password
            },function(d){

                if(d.responseCode!==0){
                    layer.alert(d.responseText)
                }else{
                    layer.close(index);
                    window.location.reload();
                }
            },"json");

        }
    };




    function popWindow(){
        _.layer(function(layer){
            layer.open(config);
        });
    }


    window.tmsLoginBox = {
        popWindow:popWindow
    };


})(window);