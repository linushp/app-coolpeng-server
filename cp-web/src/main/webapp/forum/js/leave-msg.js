jQuery(document).ready(function($){


    $("#leave-msg-captcha").click(function(){
        var src=APPLICATION_CONTEXT + "/captcha.shtml?_"+(new Date().getTime());
        $("#leave-msg-captcha").attr("src",src);
    });




    $(".likeCountBtn").click(function(){
        var $this = $(this);
        var postId = $this.data("id");

        jQuery.ajax({
            url: APPLICATION_CONTEXT + '/forum/ajax/setPostLikeCount.shtml',// 跳转到 action
            data:{
                postId:postId
            },
            type:'post',
            cache:false,
            dataType:'text',
            success:function(data) {
                if(data!="error"){
                    $this.find(".text").html(" （"+data+"）");
                }
            },
            error : function() {
            }
        });
    });




    $(".replyBtn").click(function(){

        $(".leave-msg-item-reply").hide();

        var $this = $(this);
        var postId = $this.data("id");
        var $item = $("#leave-msg-item-"+postId);

        var t='' +
            '<div class="leave-msg-item-reply-form">' +
            '   <textarea></textarea>' +
            '   <div></div>' +
            '   <input type="button" class="layout-submit" value="提交回复" data-id="{{postId}}" >' +
            '</div>';

        t= t.replace("{{postId}}",postId);

        var $reply=$item.find(".leave-msg-item-reply");
        $reply.html(t);
        $reply.show(300);
    });






    $(".leave-msg-item-reply").on("click",".layout-submit",function(){
        var $this=$(this);
        var postId = $this.data("id");
        var $item = $("#leave-msg-item-"+postId);
        var $reply=$item.find(".leave-msg-item-reply");
        var content=$reply.find("textarea").val();


        jQuery.ajax({
            url: APPLICATION_CONTEXT + '/forum/ajax/createPost.shtml',// 跳转到 action
            data:{
                method:"reply",
                parentId:postId,
                postTitle:"",
                postContent:content
            },
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                window.location.reload();
            },
            error : function() {
            }
        });


    });

});