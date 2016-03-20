jQuery(document).ready(function($){



    $(".forum-module-modify-link").click(function(){

        var $this=$(this);
        var json=$this.find(".forum-module-json").html();
        var m=JSON.parse($.trim(json));
        var $from=$("#saveModuleForm");
        $from.find("input[name=moduleId]").val(m.id);
        $from.find("input[name=moduleName]").val(m.moduleName);
        $from.find("input[name=moduleDesc]").val(m.moduleDesc);
        $from.find("input[name=moduleType]").val(""+m.moduleType);
        $from.find("input[name=forumGroupId]").val(""+m.forumGroupId);
        $from.find("input[name=moduleIcon]").val(m.moduleIcon);
    });

});