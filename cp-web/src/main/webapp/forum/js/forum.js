function initBaiduEditor() {

    window.UMEDITOR_CONFIG.toolbar = [
        'undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
        'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
        '| justifyleft justifycenter justifyright justifyjustify |',
        'link unlink | emotion image video  | map',
        '| horizontal fullscreen'
    ];

    window.baiduEditorHandler = UM.getEditor('forum-editor');

}

jQuery(document).ready(function () {
    var $ = jQuery;
    initBaiduEditor();

    var $body = $("body");

    //发布新帖，发布回复
    $body.on("click",".forum-new-button",function () {
        var title = $(".forum-new-title").val();

        var um = window.baiduEditorHandler;
        var content = um.getContent();

        var $form = $("#forum-new-form");
        $form.find(".postTitle").val(title);
        $form.find(".postContent").val(content);
        $form.submit();
    });


    //点击小图片
    $body.on("click",".forum-list-img",function () {
        var $target = jQuery(this);
        //var src = $target.attr("src");
        var container = $target.closest(".forum-list-images");
        var imgDiv = container.find(".forum-list-img-middle1");

        //显示大图
        var img = imgDiv.find("img");

        var imgxx = $target.attr("imgxx");

        //var src="http://image.coolpeng.cn/"+imgxx+"@s_0,w_600,q_90";
        var src = imgxx;

        img.attr("src", src);
        imgDiv.show();

        //小图状态改变
        container.find(".forum-list-img-small").addClass("forum-list-img-small2");

        //
        container.find(".forum-list-img").removeClass("current");
        $target.addClass("current");
    });


    //点击大图
    $body.on("click",".forum-list-img-middle2",function(){
        var $target = jQuery(this);
        var imgDiv = $target.closest(".forum-list-img-middle1");
        imgDiv.hide();

        var container = $target.closest(".forum-list-images");

        //小图状态改变
        container.find(".forum-list-img-small").removeClass("forum-list-img-small2");
        container.find(".forum-list-img").removeClass("current");
    });

    //点击添加注释
    $body.on("click",".forum-list-img-oper .add-comment",function(){

        var $this=$(this);
        var mm=$this.closest(".forum-list-img-middle1");
        var mm2=mm.find(".forum-list-img-middle2");
        var imgSrc = mm2.attr("src");




        layer.open({
            type: 1,
            title:"添加图片描述信息",
            area: ['600px', '360px'],
            btn: ['确定', '取消'],
            yes: function(index, $layero){
                var category = $layero.find(".img-category").val();
                var keyword = $layero.find(".img-keyword").val();
                var desc = $layero.find(".img-desc").val();



            },
            content: '' +
            '<div class="layout-container">' +
            '   <div class="layout-row">' +
            '      <span class="layout-title">注释分类：</span>' +
            '      <select class="layout-select img-category">' +
            '          <option value="people">人物</option>' +
            '          <option value="skill">技能</option>' +
            '      </select>' +
            '   </div>' +
            '   <div class="layout-row">' +
            '       <span class="layout-title">注释关键词：</span>' +
            '       <input class="layout-input img-keyword" type="text">' +
            '   </div>' +
            '   <div class="layout-row">' +
            '       <span class="layout-title">注释详细内容：</span>' +
            '       <textarea style="width: 400px;height: 150px;" class="img-desc"></textarea>' +
            '   </div>' +
            '</div>'
        });
    });




    $body.on("click",".blog-content-edit",function(){
        var id = $(this).data("id")
    });


    $body.on("click",".blog-content-delete",function(){
        var id = $(this).data("id");
        var moduleId = $(this).data("module");
        $.get(_.path("/admin/forum/deletePostContent.shtml?postId="+id),function(d){
            _.layer(function(layer){
                layer.msg('删除成功',function(){
                    window.location.href = _.path("/forum/post-list.shtml?orderBy=time&moduleId="+moduleId);
                });
            });
        });
    });

    $body.on("click",".blog-reply-delete",function(){
        var id = $(this).data("id");

        $.get(_.path("/admin/forum/deletePostReply.shtml?replyId="+id),function(d){
            _.layer(function(layer){
                layer.msg('删除成功',function(){
                    window.location.reload();
                });
            });
        });

    });


});

