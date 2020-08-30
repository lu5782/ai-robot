$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financebankinfo/list',
        datatype: "json",
        colModel: [
            { label: '银行名称', name: 'bankName', index: 'bank_name', width: 200 },
            { label: 'logo url', name: 'logoUrl', index: 'logo_url', width: 200 },
            { label: '最高额度', name: 'maxAmount', index: 'max_amount', width: 200 },
            { label: '跳转url', name: 'redirectUrl', index: 'redirect_url', width: 200 },
            { label: '预计通过率', name: 'predictPassingRate', index: 'predict_passing_rate', width: 200 },
            { label: '顺序', name: 'displayOrder', index: 'display_order', width: 200 },
            { label: '更新时间', name: 'updateTime', index: 'update_time', width: 200 },
            { label: '状态', name: 'isDelete', index: 'is_delete', width: 200,
                formatter: function (value, options, row) {
                    return value == "0" ? "上线" : "下线"
                }
            },
            { label: '备注', name: 'remark', index: 'remark', width: 200 },
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
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
        }
    });
    vm.loadAll();
});

var vm = new Vue({
    el:'#financeApp',
    data:{

        bankName:"",
        showList: true,
        title: null,
        financeBankInfo: {
            productTags:""
        }
    },
    methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financebankinfo/getSelectList';
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
            var restaurants = vm.restaurants;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelect(item) {
            vm.bankName = item.value;
        },
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.financeBankInfo = {};
            $("#div_showLogo img").remove();
            var inputText = "<img class=\'layui-upload-img\' id=\'showLogo\' style=\'height:200px;width:200px;\'>";
            $('#div_showLogo').append(inputText);
            layui.use('form', function () {
                var form = layui.form;
                form.render();
            });
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.financeBankInfo.id == null ? "app/financebankinfo/save" : "app/financebankinfo/update";
            vm.financeBankInfo.logoUrl = $("#search_popularBannerUrl").val();//获取图片路径
            vm.financeBankInfo.productTags= $("#search_productFeatures").val();//获取图片路径
            var arrBox = new Array();
            $("input:checkbox[name='like']:checked").each(function (i) {
                arrBox[i] = $(this).val();
            });
            var value = arrBox.join("|");//将数组合并成字符串
            $("#search_productFeatures").val(value);
            vm.financeBankInfo.product_tags = $("#search_cardType").val()
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeBankInfo),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financebankinfo/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        tapebelow: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要上线选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financebankinfo/tapeout",
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
        below: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要下线选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financebankinfo/tapebelow",
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
        getInfo: function(id){
            $.get(baseURL + "app/financebankinfo/info/"+id, function(r){
                vm.financeBankInfo = r.financeBankInfo;
                $("#search_popularBannerUrl").val(vm.financeBankInfo.logoUrl);//得到图片路径
                $('#showLogo').attr('src', vm.financeBankInfo.logoUrl);//得到图片显示
                if (vm.financeBankInfo.productTags==null){
                    return;
                }
                layui.use('form', function () {
                    var form = layui.form;

                    $("#search_productFeatures").html("");
                    var boxHtml = "<input type=\'checkbox\' name=\'like\' value=\'白金卡\' title=\'白金卡\' lay-skin=\'primary\'>";
                    boxHtml += "<input type=\'checkbox\' name=\'like\' value=\'免年费\' title=\'免年费\' lay-skin=\'primary\'>";
                    $('#search_productFeatures').append(boxHtml);

                    var arr = vm.financeBankInfo.productTags.split("|");
                    for (var i = 0; i < arr.length; i++) {
                        var item = arr[i];
                        $("input:checkbox[value=" + item + "]").attr('checked', 'checked');
                    }

                    form.render();
                });
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'bankName': vm.bankName
                },
                page:1
            }).trigger("reloadGrid");
        }
    }
});