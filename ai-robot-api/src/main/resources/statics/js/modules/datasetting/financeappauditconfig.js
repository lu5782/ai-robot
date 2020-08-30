$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financeappauditconfig/list',
        datatype: "json",
        colModel: [
            {label: 'app代码', name: 'appCode', index: 'app_code', width: 80},
            {label: 'app名称', name: 'appName', index: 'app_name', width: 80},
            {label: '应用市场代码', name: 'marketNo', index: 'market_no', width: 80},
            {label: '版本号', name: 'versionNo', index: 'version_no', width: 80},
            {label: 'app类型', name: 'appType', index: 'app_type', width: 80},
            {
                label: '是否审核中', name: 'isAudit', index: 'is_audit', width: 80,
                formatter: function (value, options, row) {
                    return value == "N" ? "否" : "是"
                }
            },
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
            {label: '更新时间', name: 'updateTime', index: 'update_time', width: 80},
            {label: '创建者', name: 'creator', index: 'creator', width: 80},
            {label: '更新者', name: 'updator', index: 'creator', width: 80}
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
});

var vm = new Vue({
    el: '#financeApp',
    data: {
        showList: true,
        title: null,
        financeAppAuditConfig: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.financeAppAuditConfig = {
                appType: 'android',
                isAudit: 'N'
            };
            $.ajax({
                type: "GET",
                url: baseURL + "app/financeappauditconfig/getAppMarketInfo",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        var data = r.list;
                        if (data.length > 0) {
                            $("#placeholder").html("");
                            var placeholderHtml = "<option value=''>应用市场名称</option>";
                            for (var i = 0; i < data.length; i++) {
                                var item = data[i];
                                placeholderHtml += "<option     value=" + item.marketNo + ">" + item.marketName + "</option>";
                            }
                        }
                        $('#placeholder').append(placeholderHtml);
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
            var url = vm.financeAppAuditConfig.id == null ? "app/financeappauditconfig/save" : "app/financeappauditconfig/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeAppAuditConfig),
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
                    url: baseURL + "app/financeappauditconfig/delete",
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
            $.get(baseURL + "app/financeappauditconfig/info/" + id, function (r) {
                vm.financeAppAuditConfig = r.financeAppAuditConfig;
                $("#placeholder").html("");
                var  html = "<option     value=" + vm.financeAppAuditConfig.marketNo + ">" + vm.financeAppAuditConfig.marketName + "</option>";
                $('#placeholder').append(html);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    appName: $("#appName").val()
                },
                page: 1
            }).trigger("reloadGrid");
        }
    }
});