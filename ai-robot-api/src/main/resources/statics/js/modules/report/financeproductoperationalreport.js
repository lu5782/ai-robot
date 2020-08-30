$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/financeproductoperationalreport/list',
        datatype: "json",
        colModel: [
            {label: '日期', name: 'statisticsDate', index: 'statistics_date', width: 80},
            {label: '合作产品', name: 'productName', index: 'product_name', width: 80},
            {label: '用户点击数', name: 'totalClickNum', index: 'total_click_num', width: 80},
            {label: '产品位置（排序）', name: 'productOrder', index: 'product_order', width: 80}],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
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
        queryParams: {},
        financeProductOperationalReport: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.financeProductOperationalReport = {};
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
            var url = vm.financeProductOperationalReport.id == null ? "report/financeproductoperationalreport/save" : "report/financeproductoperationalreport/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeProductOperationalReport),
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
                    url: baseURL + "report/financeproductoperationalreport/delete",
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
            $.get(baseURL + "report/financeproductoperationalreport/info/" + id, function (r) {
                vm.financeProductOperationalReport = r.financeProductOperationalReport;
            });
        },
        reset: function (event) {
            $(':input', '#appForm').val('');
            vm.queryParams.registerBeginDate = '';
            vm.queryParams.registerEndDate = '';
            vm.queryParams.productName = '';
        },
        exportReport: function () {
            window.location.href = baseURL + "report/financeproductoperationalreport/exportReport?registerBeginDate="
                + vm.queryParams.registerBeginDate + "&registerEndDate=" + vm.queryParams.registerEndDate
                + "&productName=" + vm.queryParams.productName;
        },
        reload: function (event) {
            vm.showList = true;
            //传入查询条件参数
            $("#jqGrid").jqGrid("setGridParam", {
                postData: {
                    registerBeginDate: vm.queryParams.registerBeginDate,
                    registerEndDate: vm.queryParams.registerEndDate,
                    productName: vm.queryParams.productName
                }
            });
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page:1
            }).trigger("reloadGrid");
        }
    }
});