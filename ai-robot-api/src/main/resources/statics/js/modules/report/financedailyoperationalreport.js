$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/financedailyoperationalreport/list',
        datatype: "json",
        colModel: [
            {label: '日期', name: 'statisticsDate', index: 'statistics_date', width: 180},
            {label: 'H5访问UV', name: 'totalUvNum', index: 'total_uv_num', width: 180},
            {label: '注册数', name: 'registerNum', index: 'register_num', width: 180},
            {label: '注册转换率', name: 'registerConversionRate', index: 'register_conversion_rate', width: 180},
            {label: '新用户首页UV数', name: 'homeUvNewNum', index: 'home_uv_new_num', width: 180},
            {label: '老用户首页UV数', name: 'homeUvOldNum', index: 'home_uv_new_num', width: 180},
            {label: '首页UV数', name: 'homeUvNum', index: 'home_uv_num', width: 180},
            {label: '新用户首页UV率', name: 'homeUvRate', index: 'home_uv_rate', width: 180},
            {label: '老用户首页UV率', name: 'homeUvOldRate', index: 'home_uv_rate', width: 180},
            {label: '点击贷款数量UV', name: 'loanUvNum', index: 'loan_uv_num', width: 180},
            {label: '新用户点击贷款数量UV', name: 'loanNewUvNum', index: 'loan_new_uv_num', width: 180},
            {label: '点击信用卡数量UV', name: 'creditCardUvNum', index: 'credit_card_uv_num', width: 180},
            {label: '新用户点击信用卡数量UV', name: 'creditCardNewUvNum', index: 'credit_card_new_uv_num', width: 180}
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
            console.log(value); //得到日期生成的值，如：2017-08-18
            vm.queryParams.registerEndDate = value;
        }
    });
});
var vm = new Vue({
    el: '#financeApp',
    data: {
        showList: true,
        title: null,
        queryParams: {},
        financeDailyOperationalReport: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.financeDailyOperationalReport = {};
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
            var url = vm.financeDailyOperationalReport.id == null ? "report/financedailyoperationalreport/save" : "report/financedailyoperationalreport/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeDailyOperationalReport),
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
                    url: baseURL + "report/financedailyoperationalreport/delete",
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
            $.get(baseURL + "report/financedailyoperationalreport/info/" + id, function (r) {
                vm.financeDailyOperationalReport = r.financeDailyOperationalReport;
            });
        },
        reset: function (event) {
            $(':input', '#appForm').val('');
            vm.queryParams.registerBeginDate = '';
            vm.queryParams.registerEndDate = '';
        },
        exportReport: function () {
            window.location.href = baseURL + "report/financedailyoperationalreport/exportReport?registerBeginDate="
                + vm.queryParams.registerBeginDate + "&registerEndDate=" + vm.queryParams.registerEndDate;
        },
        reload: function (event) {
            vm.showList = true;
            //传入查询条件参数
            $("#jqGrid").jqGrid("setGridParam", {
                postData: {
                    registerBeginDate: vm.queryParams.registerBeginDate,
                    registerEndDate: vm.queryParams.registerEndDate,
                }
            });
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page:1
            }).trigger("reloadGrid");
        }
    }
});