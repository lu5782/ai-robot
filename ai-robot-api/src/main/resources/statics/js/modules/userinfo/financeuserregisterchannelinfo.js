$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financeuserregisterchannelinfo/getMemberInfos',
        datatype: "json",
        colModel: [
            {label: '用户编号', name: 'userId', index: 'user_id', width: 80},
            {label: '手机号码', name: 'mobileNum', index: 'mobile_num', width: 80},
            {label: '注册平台', name: 'platformName', index: 'platform_name', width: 80},
            {label: '注册渠道', name: 'channelCode', index: 'channel_code', width: 80},
            {label: '真实姓名', name: 'realName', index: 'real_name', width: 80},
            {label: '身份证号', name: 'idCardNo', index: 'id_card_no', width: 80},
            {label: '注册时间', name: 'createTime', index: 'create_time', width: 80},
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
    // vm.getQueryConditions();
    vm.queryUsersNum();
    vm.loadAll();
});

var vm = new Vue({
    el: '#financeApp',
    data: {
        showList: true,
        title: null,
        channelCode: null,
        financeUserRegisterChannelInfo: {},
        yesterdayNewNum: 0,
        ninetyDaysActiveNum: 0,
        yesterdayActiveNum: 0
    },
    methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financeuserregisterchannelinfo/getUsersSelectList';
            //从后台获取到对象数组
            axios.get(url).then((response) => {
                //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示x
            if(null != response)
                {vm.restaurants = response.data;}
            }).catch((error) => {
                    console.log(error);
            });
            //调用的后台接口
            let url1 = baseURL + 'app/financechannelconfiginfo/getSelectChannelCodeList';
            //从后台获取到对象数组
            axios.get(url1).then((response) => {
                //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
                if(null != response)
            {vm.restaurantsCode = response.data;}
            }).catch((error) => {
                    console.log(error);
            });
        },
        getQueryConditions: function () {

            $.ajax({
                type: "GET",
                url: baseURL + "app/financeuserregisterchannelinfo/getQueryConditions",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        var data = r.list;
                        if (data.length > 0) {
                            $("#platformName").html("");
                            $("#channelName").html("");
                            var channelHtml = "<option value=''></option>";
                            var platformHtml = "<option value=''></option>";
                            for (var i = 0; i < data.length; i++) {
                                var item = data[i];
                                channelHtml += "<option     value=" + item.channelCode + ">" + item.channelName + "</option>";
                                platformHtml += "<option     value=" + item.platformCode + ">" + item.platformName + "</option>";

                            }
                        }
                        $('#channelName').append(channelHtml);
                        $('#platformName').append(platformHtml);
                        layui.use('form', function () {
                            var form = layui.form;
                            form.render();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        querySearchAsync(queryString, cb) {
            var restaurants = vm.restaurants;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        querySearchChannelCodeAsync(queryString, cb) {
            var restaurants = vm.restaurantsCode;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelect(item) {
            vm.channelType = item.value;
        },
        handleSelectChannelCode(item) {
            vm.channelCode = item.value;
        },
        query: function () {
            vm.reload();
        },
        exportReport: function () {
            window.location.href = baseURL + "app/financeuserregisterchannelinfo/exportReport?registerBeginDate="
                + $("#registerBeginDate").val() +
                "&registerEndDate=" + $("#registerEndDate").val()
                + "&channelCode=" + $("#channelCode").val()
                + "&mobileNum=" + $("#mobileNum").val()
                + "&exportNum=" + $("#exportNum").val();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    channelType: vm.channelType,
                    channelCode: vm.channelCode,
                    registerBeginDate: $("#registerBeginDate").val(),
                    registerEndDate: $("#registerEndDate").val(),
                    mobileNum: $("#mobileNum").val()
                },
                page:1
            }).trigger("reloadGrid");
        },
        queryUsersNum: function () {
            axios.get(baseURL + "app/financeuserregisterchannelinfo/queryUsersNum")
                .then(function (response) {
                    if (null != response.data) {
                        vm.yesterdayNewNum = response.data.data.yesterdayNewNum;
                        vm.ninetyDaysActiveNum = response.data.data.ninetyDaysActiveNum;
                        vm.yesterdayActiveNum = response.data.data.yesterdayActiveNum;

                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
});