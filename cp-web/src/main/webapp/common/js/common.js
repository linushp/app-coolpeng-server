_.mixin({
    /**
     * 将 uri 的查询字符串参数映射成对象
     *
     * @method mapQuery
     * @memberOf string
     *
     * @param {String} uri 要映射的 uri
     * @return {Object} 按照 uri 映射成的对象
     *
     * @example
     *  var url = "http://tms.qihang.com/?hello=4765078&style=blue";
     *  // queryObj 则得到一个{hello:"4765078", style:"blue"}的对象。
     *  var queryObj = _.mapQuery(url);
     * };
     */
    mapQuery: function (uri) {
        //window.location.search
        uri = uri && uri.split('#')[0] || window.location.search; //remove hash
        var i,
            key,
            value,
            index = uri.indexOf("?"),
            pieces = uri.substring(index + 1).split("&"),
            params = {};
        if (index === -1) {//如果连?号都没有,直接返回,不再进行处理.
            return params;
        }
        for (i = 0; i < pieces.length; i++) {
            try {
                index = pieces[i].indexOf("=");
                key = pieces[i].substring(0, index);
                value = pieces[i].substring(index + 1);
                if (!(params[key] = (value))) {
                    throw new Error("uri has wrong query string when run mapQuery.");
                }
            }
            catch (e) {
                console.log("错误：[" + e.name + "] " + e.message + ", " + e.fileName + ", 行号:" + e.lineNumber + "; stack:" + typeof e.stack, 2);
            }
        }
        return params;
    },

    /**
     * 获取查询字符串
     * @param paramName
     * @param uri 可选参数
     * @returns {*}
     */
    getQueryParam: function (paramName, uri) {
        var queryMap = _.mapQuery(uri);
        return queryMap[paramName];
    },


    path:function(p){
        return window.APPLICATION_CONTEXT + p;
    },

    /**
     * 产生路径绝对路径的前缀
     * @param pathPrefix
     * @returns {Function}
     */
    withPath: function (pathPrefix) {

        ////如果不传此属性
        //if (!pathPrefix && !_.isString(pathPrefix)) {
        //    pathPrefix = window.TMS_VIEW_PAGE_BASE;
        //}

        return function (p) {
            return  window.APPLICATION_CONTEXT + pathPrefix + p;
        }
    },


});





/**
 * 字符串工具的集合
 * @private
 */
var _s = {

    /**
     * 判断一个字符串是不是空字符串
     * @param str
     * @returns {boolean}
     */
    isBlank: function (str) {
        return !str || ($.trim(str).length === 0);
    },

    /**
     * 获取一个字符串中去除前后空白之后的长度
     * @param str
     * @returns {*}
     */
    charSize:function(str){
        if(!str){
            return 0;
        }
        return $.trim(str).length;
    },
    startsWith: function (str, starts) {
        if (starts === '') return true;
        if (str == null || starts == null) return false;
        str = "" + str;
        starts = "" + starts;
        return str.length >= starts.length && str.slice(0, starts.length) === starts;
    },

    endsWith: function (str, ends) {
        if (ends === '') return true;
        if (str == null || ends == null) return false;
        str = "" + str;
        ends = "" + ends;
        return str.length >= ends.length && str.slice(str.length - ends.length) === ends;
    },

    camelize: function (str) {
        return str.replace(/[-_\s]+(.)?/g, function (match, c) {
            return c.toUpperCase();
        });
    }
};








var commonModule = angular.module("commonModule", []);


commonModule.factory('cmUtil', ["$http",function ($http) {
    return {
        /**
         * 获取常量应用程序上下文，如http://127.0.0.1:8080/qh-tms
         * 使用此函数必须引入head.jsp文件
         * @returns {*}
         */
        getApplicationContext: function () {
            return window.APPLICATION_CONTEXT;
        },

        /**
         * 产生文件在系统中的绝对路径
         * @param p
         * @returns {*}
         */
        path: function (p) {
            return _.path(p);
        },

        /**
         * getJsonService的一个快捷方式
         * @param uri
         * @returns {*}
         */
        getJsonService1:function(uri){
            if (_s.endsWith(uri, ".shtml")) {
                uri = uri.replace(/\.shtml/, "")
            }
            var service = this.getJsonService("", [
                {name: "theJsonService", path: uri}
            ]);
            return service["theJsonService"];
        },


        /**
         * 产生Service服务类的集合
         * @param pathPrefix Rest服务的路径前缀，例如 pathPrefix:"/salary/plan/"
         * @param pathNameArray [可选] 路径名称集合，必须是数组，例如：
         *      pathNameArray = [
         *          "getModel",
         *          "getHisTempItem",
         *          {"name":"getModelXXX","path":"getModel/XXX"}
         *      ];
         * @returns {{}} 服务的集合
         * @demo
         *      var jsonService = cmUtil.getJsonService("/salary/plan/");
         *      jsonService("getModel")({id:1},data).success(function(d){
         *           var data=d.data;
         *      });
         */
        getJsonService: function (pathPrefix, pathNameArray) {

            var path = _.withPath(pathPrefix);

            //loading区域计数
            var loadingAreaCount = {};

            //对undefined进行重命名，压缩后体积更小
            var _undefined = undefined;

            /**
             * 函数参数降阶
             * @param p1
             * @param p2
             * @param p3
             * @returns {{p1: *, p2: *, loadingArea: *}}
             */
            function reduceRequestParam(p1, p2, p3) {

                var loadingArea = _undefined;

                if (_.isString(p1)) {
                    //如果第一个参数就是字符串，没有参数生效
                    loadingArea = p1;
                    p1 = _undefined;
                    p2 = _undefined;
                }
                else if (_.isString(p2)) {
                    //如果第二个参数是字符串，只有第一个参数生效
                    loadingArea = p2;
                    p2 = _undefined;
                }
                else if (_.isString(p3)) {
                    //如果第三个参数是字符串，前两个参数都有效
                    loadingArea = p3;
                }

                //发送请求之前，如果需要显示loading样式
                if (loadingArea) {
                    loadingAreaCount[loadingArea] = loadingAreaCount[loadingArea] || 0;
                    loadingAreaCount[loadingArea]++;
                    console.log("reduceRequestParam:before setLoadingBar", loadingArea, loadingAreaCount[loadingArea]);
                    if (loadingAreaCount[loadingArea] === 1) {
                        //显示loading
                        //cmLoadingBar.setLoadingBar(loadingArea);
                    }
                }

                //HTTP请求的相应处理
                var responseHandler = function (res) {
                    loadingAreaCount[loadingArea]--;
                    console.log("reduceRequestParam:before cleanLoadingBar", loadingArea, loadingAreaCount[loadingArea]);
                    if (loadingAreaCount[loadingArea] === 0) {
                        //消除掉loading
                        //cmLoadingBar.cleanLoadingBar(loadingArea);
                    }
                };

                return {
                    p1: p1,
                    p2: p2,
                    loadingArea: loadingArea,
                    responseHandler: responseHandler
                };
            }


            /**
             *
             * @param serviceSubPath
             * @returns {Function}
             */
            function createService(serviceSubPath) {

                /**
                 * 产生一个Post请求
                 * 参数：( [param],[data],[loadingArea] )
                 * 函数使用组合demo：
                 *
                 *      1、foo() 没有参数
                 *      2、foo(data) 只有data，没有param
                 *      3、foo(param,null) 只有param，没有data
                 *      4、foo(param,data) 两个参数都有。
                 *
                 *      5、foo("loadingArea") 没有参数，只有loading区域
                 *      6、foo(data，"loadingArea") 只有data，没有param，只有loading区域
                 *      7、foo(param,null，"loadingArea") 只有param，没有data，只有loading区域
                 *      8、foo(param,data，"loadingArea") 两个参数都有。只有loading区域
                 *
                 * 两个参数都是可选参数
                 */
                return function (p1, p2, p3) {

                    //参数降阶,消掉第三个参数，并创建工具函数
                    var p = reduceRequestParam(p1, p2, p3);
                    p1 = p.p1;
                    p2 = p.p2;


                    //此页面的业务会话ID
                    var bid = _.getQueryParam("bid");
                    var data = p1;
                    var param = "";

                    //如果此页面具有业务会话ID
                    if (!!bid) {
                        param = _.toQueryParam({bid: bid});
                    }

                    //如果有第二个参数，严格比较
                    //如果只需要param，不需要data，可以将第二个参数传null过来
                    if (!_.isUndefined(p2)) {
                        data = p2;
                        if (!!bid) {
                            p1 = _.extend({bid: bid}, p1);
                        }
                        param = _.toQueryParam(p1);
                    }

                    //请求的实际路径
                    var realRestPath = path(serviceSubPath) + ".shtml" + param;

                    //HTTP请求的句柄
                    var requestHandler;
                    if (data) {
                        //如果有data参数
                        requestHandler = $http.post(realRestPath, data);
                    } else {
                        requestHandler = $http.post(realRestPath);
                    }

                    //如果需要显示loading样式
                    if (p.loadingArea) {
                        //responseHandler会对loading样式进行销毁
                        return requestHandler.success(p.responseHandler).error(p.responseHandler);
                    }

                    return requestHandler;
                };
            }


            var jsonService = function (funcName) {
                if (_.isString(funcName)) {
                    return createService(funcName);
                }
            };

            //如果有第二个参数,并且第二个参数是个数组
            if (_.isArray(pathNameArray) && pathNameArray.length > 0) {

                var services = {};
                for (var i = 0; i < pathNameArray.length; i++) {
                    var obj = pathNameArray[i];
                    if (_.isString(obj)) {
                        //当为字符串时候，直接以字符串的名字作为路径名和服务名
                        services[obj] = createService(obj);
                    } else if (_.isObject(obj)) {
                        var name = obj["name"];
                        var serviceSubPath = obj["path"];
                        //当为对象的时候，路径名和服务名拆开
                        services[name] = createService(serviceSubPath);
                    }
                }

                _.extend(jsonService, services);
            }

            return jsonService;
        },


        /**
         * 使用一个隐藏的form表单提交数据，主要用于文件下载
         * @param actionURL 要提交的地址
         * @param dataObject 一个KeyValue的结构，value必须为字符串
         */
        formPost: function (actionURL, dataObject) {
            var template = '' +
                '   <form action="' + actionURL + '" method="post" target="_self" > <% for(var i=0;i<keys.length;i++){var k=keys[i]; %>' +
                '       <input class="cm-form-d-<%=k%>" name="<%=k%>" type="input" />' +
                '   <%}%>' +
                '   </form>';

            var $body = $("body");
            var $area = $body.find(".cm-form-hidden");
            if (!$area || $area.length === 0) {
                $body.append('<div class="cm-form-hidden" style="display:none; "></div>');
                $area = $body.find(".cm-form-hidden");
            }
            var keys = _.keys(dataObject);
            var formTemplate = _.template(template)({keys: keys });
            $area.html(formTemplate);
            var $form = $area.find("form");

            //将数据设置到表单中
            _.map(dataObject, function (value, key) {
                $form.find(".cm-form-d-" + key).val(value);
            });
            $form.submit();
        }
    };
}]);



