$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/financechanneloperationalreport/list',
        datatype: "json",
        colModel: [
            {label: '日期', name: 'statisticsDate', index: 'statistics_date', width: 160},
            {label: '渠道大类', name: 'channelType', index: 'channel_type', width: 160},
            {label: '渠道编码', name: 'channelCode', index: 'channel_code', width: 160},
            {label: '渠道名称', name: 'channelDesc', index: 'channel_desc', width: 160},
            {label: '落地页PV总数', name: 'registerPagePvNum', index: 'register_page_pv_num', width: 160},
            {label: '落地页UV总数', name: 'registerPageUvNum', index: 'register_page_uv_num', width: 160},
            {label: '点击申请按钮UV总数', name: 'applyBuutonUvNum', index: 'apply_buuton_uv_num', width: 160},
            {label: '注册总数', name: 'registerNum', index: 'register_num', width: 160},
            {label: '注册转换率', name: 'registerConversionRate', index: 'register_conversion_rate', width: 160},
            {label: 'APP下载页UV数', name: 'appDownloanUvNum', index: 'app_downloan_uv_num', width: 160},
            {label: '注册登录用户数', name: 'registerLoginNum', index: 'register_login_num', width: 160},
            {label: '点击甲方UV总数', name: 'userClickUvNum', index: 'user_click_uv_num', width: 160},
            {label: '平均访问时长', name: 'averageAccessDuration', index: 'average_access_duration', width: 160},
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
        financeChannelOperationalReport: {}
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
            vm.financeChannelOperationalReport = {};
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
            var url = vm.financeChannelOperationalReport.id == null ? "report/financechanneloperationalreport/save" : "report/financechanneloperationalreport/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeChannelOperationalReport),
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
                    url: baseURL + "report/financechanneloperationalreport/delete",
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
            $.get(baseURL + "report/financechanneloperationalreport/info/" + id, function (r) {
                vm.financeChannelOperationalReport = r.financeChannelOperationalReport;
            });
        },
        reset: function (event) {
            $(':input', '#appForm').val('');
            vm.queryParams.registerBeginDate = '';
            vm.queryParams.registerEndDate = '';
            vm.queryParams.channelType = '';
            vm.channelCode = '';
        },
        exportReport: function () {
            window.location.href = baseURL + "report/financechanneloperationalreport/exportReport?registerBeginDate="
                + vm.queryParams.registerBeginDate + "&registerEndDate=" + vm.queryParams.registerEndDate
                + "&channelType=" + vm.queryParams.channelType + "&channelCode=" + vm.channelCode;
            ;
        },
        reload: function (event) {
            vm.showList = true;
            //传入查询条件参数
            $("#jqGrid").jqGrid("setGridParam", {
                postData: {
                    registerBeginDate: vm.queryParams.registerBeginDate,
                    registerEndDate: vm.queryParams.registerEndDate,
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