$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financeAppShelfUserInfo/list',
        datatype: "json",
        colModel: [
            { label: '用户编号', name: 'userId', index: 'user_id', width: 200 ,key: true},
            { label: '手机号码', name: 'mobileNum', index: 'mobile_num', width: 200 },
            { label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
            { label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 },
            { label: '创建者', name: 'creator', index: 'creator', width: 80 },
            { label: '更新者', name: 'updator', index: 'updator', width: 80 }
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
});

var vm = new Vue({
    el:'#financeApp',
    data:{
        showList: true,
        title: null,
        AppShelfUserInfo: {},
        confirmPassword:"",
        captcha: ''
    },
    methods: {

        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.AppShelfUserInfo = {};
        },
        update: function (event) {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(userId)
        },
        saveOrUpdate: function (event) {
            if (!(vm.AppShelfUserInfo.password==vm.confirmPassword)){
                alert("两次密码不一致")
                return;
            }
            if (vm.AppShelfUserInfo.userId==null){
                vm.save();
            }else {
                vm.updateData();
            }
        },
        save: function (event) {
            axios.get('../../sys/getSecret')
                .then(function (response) {
                    let secret = CryptoJS.enc.Utf8.parse(response.data);
                    let loginData = {
                        mobileNum: vm.AppShelfUserInfo.mobileNum,
                        password: vm.AppShelfUserInfo.password
                    };
                    let loginJson = encrypt(JSON.stringify(loginData), secret);
                    axios.post('../../app/financeAppShelfUserInfo/save', loginJson, {headers: {'Content-Type': 'application/json'}}).then(function (res) {
                        let resData = res;
                        if (resData.data.code == 0) {//登录成功
                            alert('操作成功', function(index){
                                                vm.reload();
                                            });
                        } else {
                            alert(resData.data.msg)
                        }
                    })
                }).catch(function (error) {
            });

        },
        updateData: function (event) {
            axios.get('../../sys/getSecret')
                .then(function (response) {
                    let secret = CryptoJS.enc.Utf8.parse(response.data);
                    let loginData = {
                        mobileNum: vm.AppShelfUserInfo.mobileNum,
                        password: vm.AppShelfUserInfo.password
                    };
                    let loginJson = encrypt(JSON.stringify(loginData), secret);
                    axios.post('../../app/financeAppShelfUserInfo/update', loginJson, {headers: {'Content-Type': 'application/json'}}).then(function (res) {
                        let resData = res;
                        if (resData.data.code == 0) {//登录成功
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        } else {
                            alert(resData.data.msg)
                        }
                    })
                }).catch(function (error) {
            });

        },
        del: function (event) {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financeAppShelfUserInfo/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
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
        getInfo: function(userId){
            vm.confirmPassword=""
            $.get(baseURL + "app/financeAppShelfUserInfo/info/"+userId, function(r){
                vm.AppShelfUserInfo = r.AppShelfUserInfo;
            });

        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'channelName': vm.channelName,
                    'mobileNum':$("#mobileNum").val()
                },
                page:1
            }).trigger("reloadGrid");
        }
    }
});