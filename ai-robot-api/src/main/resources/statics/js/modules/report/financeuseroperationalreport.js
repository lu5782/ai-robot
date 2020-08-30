$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/financeuseroperationalreport/list',
        datatype: "json",
        colModel: [
            {label: '日期', name: 'statisticsDate', index: 'statistics_date', width: 160},
            {label: '渠道大类', name: 'channelType', index: 'channel_type', width: 160},
            {label: '渠道编码', name: 'channelCode', index: 'channel_code', width: 160},
            {label: '手机号', name: 'mobileNum', index: 'mobile_num', width: 160},
            {label: '点击甲方UV总数', name: 'totalClickUvNum', index: 'total_click_uv_num', width: 160},
            {label: 'IP地址', name: 'ipAddress', index: 'ip_address', width: 160},
            {label: '下载APP终端', name: 'platformType', index: 'platform_type', width: 160},
            {label: '访问时长', name: 'accessDuration', index: 'access_duration', width: 160},
            {label: '姓名', name: 'realName', index: 'real_name', width: 160},
            {label: '身份证号码', name: 'idCardNo', index: 'id_card_no', width: 160},
            {label: '地区/省份', name: 'region', index: 'region', width: 160},
            {label: '备注', name: 'remark', index: 'remark', width: 160}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        shrinkToFit:false,
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
        }
    });
    vm.loadAll();
});
layui.use(['table', 'laydate', 'element'], function () {
    //var table = layui.table;
    var laydate = layui.laydate;
    //var element = layui.element;
    // 搜索注册时间
    laydate.render({
        elem: '#registerBeginDate'
        , format: 'yyyy-MM-dd'
        //, type: 'datetime'
        , theme: 'molv'
        , showBottom: false
        , done: function (value, date, endDate) {
            vm.queryParams.registerBeginDate = value;
        }
    });
    laydate.render({
        elem: '#registerEndDate'
        , format: 'yyyy-MM-dd'
        //, type: 'datetime'
        , theme: 'molv'
        , showBottom: false
        , done: function (value, date, endDate) {
            vm.queryParams.registerEndDate = value;
        }
    });
});
var vm = new Vue({
    el: '#financeApp',
    data: {
        showList: true,
        title: null,
        channelCode: null,
        queryParams: {},
        financeUserOperationalReport: {}
    },
    methods: {
        loadAll: function () {
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
        querySearchChannelCodeAsync(queryString, cb) {
            var restaurants = vm.restaurantsCode;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelectChannelCode(item) {
            vm.channelCode = item.value;
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.financeUserOperationalReport = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.financeUserOperationalReport.id == null ? "report/financeuseroperationalreport/save" : "report/financeuseroperationalreport/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeUserOperationalReport),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "report/financeuseroperationalreport/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "report/financeuseroperationalreport/info/" + id, function (r) {
                vm.financeUserOperationalReport = r.financeUserOperationalReport;
            });
        },
        reset: function (event) {
            $(':input', '#appForm').val('');
            vm.queryParams.registerBeginDate = '';
            vm.queryParams.registerEndDate = '';
            vm.queryParams.channelType = '';
            vm.channelCode = '';
            vm.queryParams.mobileNum = '';
        },
        exportReport: function () {
            window.location.href = baseURL + "report/financeuseroperationalreport/exportReport?registerBeginDate="
                + vm.queryParams.registerBeginDate + "&registerEndDate=" + vm.queryParams.registerEndDate
                + "&channelType=" + vm.queryParams.channelType + "&channelCode=" + vm.channelCode
                + "&mobileNum=" + vm.queryParams.mobileNum;
        },
        reload: function (event) {
            vm.showList = true;
            //传入查询条件参数
            $("#jqGrid").jqGrid("setGridParam", {
                postData: {
                    registerBeginDate: vm.queryParams.registerBeginDate,
                    registerEndDate: vm.queryParams.registerEndDate,
                    mobileNum: vm.queryParams.mobileNum,
                    channelType: vm.queryParams.channelType,
                    channelCode: vm.channelCode
                }
            });
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page:1
            }).trigger("reloadGrid");
        }
    }
});