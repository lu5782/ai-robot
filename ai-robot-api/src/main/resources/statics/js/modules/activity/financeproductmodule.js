$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financeproductmodule/list',
        datatype: "json",
        colModel: [
            {label: '产品模块名称', name: 'productModuleName', index: 'product_module_name', width: 80},
            {label: '产品模块代码', name: 'productModuleCode', index: 'product_module_code', width: 80},
            {label: '模块顺序', name: 'displayOrder', index: 'display_order', width: 80},
            {
                label: '产品状态', name: 'isDelete', index: 'is_delete', width: 80,
                formatter: function (value) {
                    return value == "0" ? "上线" : "下线"
                }
            },
            {label: '创建人', name: 'creator', index: 'creator', width: 80},
            {label: '更新人', name: 'updator', index: 'updator', width: 80},
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
            {label: '更新时间', name: 'updateTime', index: 'update_time', width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
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
    vm.loadAll();
});

var vm = new Vue({
    el: '#financeApp',
    data: {
        productModuleName:"",
        productModuleCode:"",
        showList: true,
        title: null,
        financeProductModule: {}
    },
    methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financeproductmodule/getSelectList';
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
            vm.productModuleCode=""
            var restaurants = vm.restaurants;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelect(item) {
            vm.productModuleName = item.value;
            vm.productModuleCode =  item.label;
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.financeProductModule = {};
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
            if (vm.financeProductModule.productModuleName==null){
                alert("产品模块名称不能为空")
                return;
            }
            if (vm.financeProductModule.displayOrder==null){
                alert("模块顺序不能为空");
                return;
            }
            var url = vm.financeProductModule.id == null ? "app/financeproductmodule/save" : "app/financeproductmodule/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeProductModule),
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
        online: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要上线选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financeproductmodule/online",
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
                    url: baseURL + "app/financeproductmodule/offline",
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
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financeproductmodule/delete",
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
            $.get(baseURL + "app/financeproductmodule/info/" + id, function (r) {
                vm.financeProductModule = r.financeProductModule;

            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    productModuleName: vm.productModuleName
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});