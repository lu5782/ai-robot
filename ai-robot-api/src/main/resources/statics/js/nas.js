$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/nas/get',
        datatype: "json",
        colModel: [
            // {label: '规则名称', name: 'ruleName', index: 'rule_name', width: 80},
            // {label: '产品名称', name: 'productName', index: 'product_name', width: 80},
            // {label: '规则内容', name: 'ruleContent', index: 'rule_content', width: 80},
            // {label: '开始执行时间', name: 'executionTime', index: 'execution_time', width: 80},
            // {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
            {label: '创建者', name: 'creator', index: 'creator', width: 80},
            {label: '备注', name: 'reamrk', index: 'reamrk', width: 80},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    vm.getFileLists();
});

var vm = new Vue({
    el: '#financeApp',
    data: {
        productCode: "",
        productName: "",
        showList: true,
        title: "标题-nas盘",
        financeHisIncrementRule: {
            firstText: "每",
            lastText: "个刷1个"
        }
    },
    methods: {
        uploadFile: function () {
            //调用的后台接口
            let url = baseURL + '/nas/upload';
            //从后台获取到对象数组
            axios.get(url).then((response) => {
                //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
                if (null != response) {
                    vm.restaurants = response.data;
                }
            }).catch((error) => {
                console.log(error);
            });
        },
        getFileLists() {
            console.log("this window.onload");
            $.ajax(
                {
                    url: '/nas/getFileLists',
                    data: JSON.stringify({filePath: "/"}),   // 需要传送的参数
                    type: 'POST',   // 请求方式
                    dataType: 'json', // 返回数据的格式, 通常为JSON
                    contentType: 'application/json',
                    success: function (response) { // 请求成功后的回调函数
                        console.log(response);
                        let list = []
                        let i = {"reamrk": response[0], "creator": response[0], "value": response[0]}
                        console.log("i=" + i);
                        list.push(i)
                        console.log("list=" + list.length);
                        console.log("list=" + JSON.stringify(i));
                        vm.restaurants = list;
                    },
                    error: function () {
                        console.log('Send Request Fail..'); // 请求失败时的回调函数
                    }
                }
            );
        },
        querySearchAsync(queryString, cb) {
            vm.productCode = ""
            var restaurants = vm.restaurants;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    productName: vm.productName
                },
                page: 1
            }).trigger("reloadGrid");
        }
    }
});