$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financechannleaccountinfo/list',
        datatype: "json",
        colModel: [
            { label: '渠道大类Id', name: 'channelType', index: 'channel_type', width: 200 },
            { label: '渠道代码AMID', name: 'channelCode', index: 'channel_code', width: 80 },
            { label: '渠道大类', name: 'channelName', index: 'channel_name', width: 80 },
            { label: '手机号码', name: 'mobileNum', index: 'mobile_num', width: 80 },
            { label: '备注', name: 'reamrk', index: 'reamrk', width: 80 },
            { label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
            { label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vm.loadAll();

    $("#channelType").change(function () {
        var channelType= $("#channelType").val();
       vm.financeChannleAccountInfo.channelName = $("#channelType").find("option:selected").text();
        $.ajax({
            type: "GET",
            data: {channelType:channelType},
            url: baseURL + "app/financechannleaccountinfo/querychannel",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    var data = r.list;
                    $("#channelCode").html("");
                    if (data.length > 0) {
                        var channelCodeHtml = "<option value=''> 请选择AMID</option>";
                        for (var i = 0; i < data.length; i++) {
                            var item = data[i];
                            channelCodeHtml += "<option     value=" + item.channelCode + ">" + item.channelCode + "</option>";
                        }
                    }

                    $('#channelCode').append(channelCodeHtml);
                    layui.use('form', function () {
                        var form = layui.form;
                        form.render();
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    })
});

var vm = new Vue({
    el:'#financeApp',
    data:{
        showList: true,
        title: null,
        financeChannleAccountInfo: {},
        channelCodeSearch: null,
        channelName:""
    },
    methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financechannelconfiginfo/getSelectList';
            //从后台获取到对象数组
            axios.get(url).then((response) => {
                //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
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
        querySearchAsync(queryString, cb) {
            var restaurants = vm.restaurants;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelect(item) {
            vm.channelName = item.value;
        },
        querySearchChannelCodeAsync(queryString, cb) {
            var restaurants = vm.restaurantsCode;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        handleSelectChannelCode(item) {
            vm.channelCodeSearch = item.value;
        },
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.financeChannleAccountInfo = {};
            $.ajax({
                type: "GET",
                url: baseURL + "app/financechannleaccountinfo/querychannelType",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        var data = r.list;
                        if (data.length > 0) {
                            $("#channelType").html("");
                            var channelTypeHtml = "<option value=''> 请选择渠道大类</option>";
                            for (var i = 0; i < data.length; i++) {
                                var item = data[i];
                                channelTypeHtml += "<option     value=" + item.channelType + ">" + item.channelName + "</option>";
                            }
                        }
                        $('#channelType').append(channelTypeHtml);
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
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.financeChannleAccountInfo.id == null ? "app/financechannleaccountinfo/save" : "app/financechannleaccountinfo/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.financeChannleAccountInfo),
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
                    url: baseURL + "app/financechannleaccountinfo/delete",
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
        getInfo: function(id){
            $.get(baseURL + "app/financechannleaccountinfo/info/"+id, function(r){
                vm.financeChannleAccountInfo = r.financeChannleAccountInfo;
                $('#channelType').html('');//把dom节点清空或者赋值
                //添加一个下拉框并且把值给给下拉框
                var channelTypeHtml = "<option value=" + vm.financeChannleAccountInfo.channelType + ">" + vm.financeChannleAccountInfo.channelType + "</option>";
                $('#channelType').append(channelTypeHtml);
                $('#channelCode').html('');//把dom节点清空或者赋值
                //添加一个下拉框并且把值给给下拉框
                var channelCodeHtml = "<option value=" + vm.financeChannleAccountInfo.channelCode + ">" + vm.financeChannleAccountInfo.channelCode + "</option>";
                $('#channelCode').append(channelCodeHtml);
                $('#channelName').html('');//把dom节点清空或者赋值
                //添加一个下拉框并且把值给给下拉框
                var channelNameHtml = "<option value=" + vm.financeChannleAccountInfo.channelName + ">" + vm.financeChannleAccountInfo.channelName + "</option>";
                $('#channelName').append(channelNameHtml);
            });

        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'channelName': vm.channelName,
                    'channelCode': vm.channelCodeSearch,
                    'mobileNum':$("#mobileNum").val()
                },
                page:1
            }).trigger("reloadGrid");
        }
    }
});