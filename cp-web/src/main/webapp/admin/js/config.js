var adminModule = angular.module("adminModule",["ngRoute","commonModule"]);

adminModule.factory('interceptorService', function ($q) {
    var interceptor = {
        'response': function (response) {
            var data = response.data;
            if (!!data.errorCode) {
                alert(data.errorText);
                return $q.reject(response)
            }
            return response;
        },
        'responseError': function (rejection) {
            if(rejection.status > 0){
                alert('未知错误，请稍后再试！');
                console.log(rejection.data);
            }
            return $q.reject(rejection)
        }
    };
    return interceptor;
});

adminModule.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {

        //定位到template文件目录
        var templatePath = function(p){
            return _.path("/admin/template/"+ p);
        };

        var route = $routeProvider.when('/home', {
            controller: 'homeController',
            templateUrl: templatePath("home.html")
        });
        route.when('/manage-module', {

            controller: 'manageModuleController',
            templateUrl: templatePath("manage-module.html")
        });
        route.when('/new-post', {
            controller: 'newPostController',
            templateUrl: templatePath("new-post.html")
        });

        route.when('/manage-post', {
            //参数项列表
            controller: 'managePostController',
            templateUrl: templatePath("manage-post.html")
        });

        route.when('/setting', {
            //参数项列表
            controller: 'settingController',
            templateUrl: templatePath("setting.html")
        });


        route.otherwise({ redirectTo: '/home'});

        $httpProvider.interceptors.push('interceptorService');
    }
]);


/**
 *     FORUM(1),
 ASK(2),
 BLOG(3),
 GOSSIP(4),//留言板
 WEIBO(5); //类似于微博的个人说说
 */

adminModule.filter("moduleType", function() {

    return function(type) {
        if(type==1){
            return "论坛";
        }
        if(type==2){
            return "问答";
        }
        if(type==3){
            return "博客";
        }
        if(type==4){
            return "留言板";
        }
        if(type==5){
            return "微博";
        }

        return '';
    };
})