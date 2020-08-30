$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financeactivityconfig/list',
        datatype: "json",
        colModel: [
            {label: '产品名称', name: 'productName', index: 'product_name', width: 80},
            {label: '产品顺序', name: 'displayOrder', index: 'display_order', width: 80},
            {label: '上首页时间', name: 'onlineTime', index: 'online_time', width: 80},
            {label: '操作', name: 'isDelete', index: 'is_delete', width: 80,
                formatter: function (value) {
                    return value == "0" ? "上线" : "下线"
                }},
            {label: '备注', name: 'remark', index: 'remark', width: 80},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        loadComplete: function () {
            //debugger;
            //在表格加载完成后执行
            var ids = $("#jqGrid").jqGrid("getDataIDs");//获取所有行的id
            for (var i = 0; i < ids.length; i++) {
                var rowData = $("#jqGrid").getRowData(ids[i]);
                if (rowData.isDelete=="下线") {
                    $("#"+ids[i]+ " td").css("background-color","#C0C0C0");

                }
            }
        },
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
    vm.loadAll();
});

var vm = new Vue({
    el: '#financeApp',
    data: {
        showList: true,
        title: null,
        financeActivityConfig: {},
        productName:"",
        productCode:""
    },
    methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financeactivityconfig/getSelectList';
            //从后台获取到对象数组
            axios.get(url).then((response) => {
                //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
                if(null != response)
            {vm.restaurants = response.data;}
        }).catch((error) => {
                console.log(error);
        });
        },
        querySearchAsync(queryString, cb) {
            vm.productCode=""
            var restaurants = vm.restaurants;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelect(item) {
            vm.productName = item.value;
            vm.productCode =  item.label;
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.financeActivityConfig = {};
            $.ajax({
                type: "GET",
                url: baseURL + "app/financeactivityconfig/getLoanList",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        var data = r.list;
                        if (data.length > 0) {
                            $("#productCode").html("");
                            var productCodeHtml = "<option value=''>产品名称</option>";
                            for (var i = 0; i < data.length; i++) {
                                var item = data[i];
                                productCodeHtml += "<option     value=" + item.productCode + ">" + item.productName + "</option>";

                            }
                        }
                        $('#productCode').append(productCodeHtml);
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
            var url = vm.financeActivityConfig.id == null ? "app/financeactivityconfig/save" : "app/financeactivityconfig/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeActivityConfig),
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
                    url: baseURL + "app/financeactivityconfig/delete",
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
        online: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要上线选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financeactivityconfig/online",
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
        offline: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要下线选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financeactivityconfig/offline",
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
            $.get(baseURL + "app/financeactivityconfig/info/" + id, function (r) {
                vm.financeActivityConfig = r.financeActivityConfig;
                $('#productCode').html('');//获取下拉框的值，再把获得的值显示在修改页面
                var codeHtml = "<option     value=" + vm.financeActivityConfig.productCode + ">" + vm.financeActivityConfig.productName + "</option>";
                $('#productCode').append(codeHtml);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    productCode: vm.productCode
                },
                page:1
            }).trigger("reloadGrid");

        }
    }
});