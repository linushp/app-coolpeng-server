adminModule.controller('manageModuleController', ['$scope',"cmUtil",
    function($scope,cmUtil) {

        $scope.showType=0;

        function initModuleList(){
            cmUtil.getJsonService1("/admin/forum/getAllModuleList.shtml")().success(function(d){
                var data= d.data;
                $scope.moduleList = data.moduleList;
                $scope.groupList = data.groupList;
            });
        }

        $scope.addNewGroup = function(){
            $scope.moduleGroupData = {};
            $scope.showType=11;
        };

        $scope.addNewModule = function(){
            $scope.modifyModuleData= {};
            $scope.showType=22;
        };


        $scope.listModule = function(g){
            $scope.showType=0;
        };


        $scope.modifyGroup = function(g){
            $scope.moduleGroupData = g;
            $scope.showType=1;
        };


        $scope.doModifyGroup = function(){
            var forumGroup = $scope.moduleGroupData;
            var x= cmUtil.getJsonService1("/admin/forum/saveModuleGroup.shtml");
            var m=x(forumGroup);
            m.success(function(d){
                alert("修改成功");
                initModuleList();
            });
        };

        $scope.modifyModule = function(m){
            $scope.modifyModuleData= m;
            $scope.showType=2;
        };

        $scope.doModifyModule = function(){
            var forumModule = $scope.modifyModuleData;
            cmUtil.getJsonService1("/admin/forum/saveModule.shtml")(forumModule).success(function(d){
                alert("修改成功");
                initModuleList();
            });
        };


        initModuleList();
    }
]);