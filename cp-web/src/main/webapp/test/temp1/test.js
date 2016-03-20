var testApp = angular.module("testApp", []);
testApp.controller("testController",["$scope",function($scope){

    function init () {
        $scope.data = [
            [1,2,3],
            [4,5,6],
            [7,8,9]
        ];
        $scope.path = [];
    }




    //逆时针
    function createR(x,y,name){
        return function (){
            var d = $scope.data;
            var temp = d[x][y];
            d[x][y] = d[x][y+1];
            d[x][y+1] = d[x+1][y+1];
            d[x+1][y+1] = d[x+1][y+0];
            d[x+1][y+0] = temp;
            $scope.data  = angular.copy(d);
            $scope.path.push(name);
            return name;
        };
    }


    //顺时针
    function create_R(x,y,name){
        return function (){
            var d = $scope.data;
            var temp = d[x][y];
            d[x][y] = d[x+1][y+0];
            d[x+1][y+0] = d[x+1][y+1];
            d[x+1][y+1] = d[x+0][y+1];
            d[x+0][y+1] = temp;
            $scope.data  = angular.copy(d);
            $scope.path.push(name);
            return name;
        };
    }



    $scope.R1 = createR(0,0,"R1");

    $scope.R2 = createR(0,1,"R2");

    $scope.R3 = createR(1,0,"R3");

    $scope.R4 = createR(1,1,"R4");

    $scope.r1 = create_R(0,0,"r1");

    $scope.r2 = create_R(0,1,"r2");

    $scope.r3 = create_R(1,0,"r3");

    $scope.r4 = create_R(1,1,"r4");

    init();







    $scope.parseStr = function () {

        init();

        var str = $scope.str;
        var c_arr = [];
        for (var i = 0; i < str.length; i++) {
            var c= str[i];
            c_arr.push(c);
            if (i%2==1) {
                c_arr.push("_");
            };
        };
        
        var operArray = c_arr.join("").split("_");
        for (var i = 0; i < operArray.length; i++) {
            var operName = operArray[i]
            var oper = $scope[operName];
            oper&&oper();
        };


    }



}]);