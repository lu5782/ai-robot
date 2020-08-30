$(function () {

    vm.queryChannelNum();
});
function deductionRules(value, options, rowObject) {
	var result = value;
	if (result==null){
		result="请选择规则"
	}
    return '<button class="layui-btn layui-btn-sm" type="button" style="text-decoration: none;"target="_bank"onclick="Editor()">'+result+'</button>';
};
function details(value, options, rowObject) {
    return '<button class="layui-btn layui-btn-sm" type="button" style="text-decoration: none;"target="_bank"onclick="Editor1()">查看详情</button>';
};
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
            vm.financeUserRegisterChannelInfo.registerBeginDate = value;
        }
    });
    laydate.render({
        elem: '#registerEndDate'
        , format: 'yyyy-MM-dd'
        //, type: 'datetime'
        , theme: 'molv'
        , showBottom: false
        , done: function (value, date, endDate) {
            vm.financeUserRegisterChannelInfo.registerEndDate = value;
        }
    });
});
function Editor1() {
vm.showList=false;
    vm.showBonusList=true;
    var channelCode=$('#jqGrid').jqGrid('getGridParam','selrow');
    if (channelCode == null) {
        return;
    }
    vm.financeUserRegisterChannelInfo.channelCode = channelCode;
    vm.reload2(channelCode);
    $("#jqGrid2").setGridWidth($(window).width());

}
var vm = new Vue({
	el:'#financeApp',
	data:{
        showBonusList:false,
		showList: true,
		title: null,
		financeUserRegisterChannelInfo: {},
        channelNum:0,
        nowadaysNum:0,
        yesterdayNum:0

	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.financeUserRegisterChannelInfo = {};
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
			var url = vm.financeUserRegisterChannelInfo.id == null ? "app/financeuserregisterchannelinfo/save" : "app/financeuserregisterchannelinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeUserRegisterChannelInfo),
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
        queryChannelNum: function () {
            axios.get(baseURL + "app/financeuserregisterchannelinfo/queryChannelNum")
                .then(function (response) {
                    if (null != response.data) {
                        vm.channelNum = response.data.data.channelNum;
                        vm.nowadaysNum = response.data.data.nowadaysNum;
                        vm.yesterdayNum = response.data.data.yesterdayNum;

                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        reload2: function (channelCode) {
		    var channelCode1 = channelCode;
            $("#jqGrid2").jqGrid({
                url: baseURL + 'app/financeuserregisterchannelinfo/list',
                datatype: "json",
                postData: {
                    channelCode: vm.financeUserRegisterChannelInfo.channelCode
                },
                shrinkToFit: false,
                autoScroll: true,
                colModel: [
                    {
                        label: '手机号码',
                        name: 'mobileNum',
                        index: 'mobile_num',
                        width: 200,

                    },
                    {
                        label: '渠道编码（AMID)',
                        name: 'channelCode',
                        index: 'channel_code',
                        width: 220,
                    },
                    {label: '渠道名称', name: 'channelName', index: 'channel_name', width: 220},
                    {
                        label: '注册时间',
                        name: 'createTime',
                        index: 'create_time',
                        width: 220
                    },

                    {label: '备注', name: 'remark', index: 'remark', width: 280}
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: false,
                pager: "#jqGridPager2",
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
                    $("#jqGrid2").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });




            var page2 = $("#jqGrid2").jqGrid('getGridParam','page');
            $("#jqGrid2").jqGrid('setGridParam',{

                postData: {
                    "channelCode":vm.financeUserRegisterChannelInfo.channelCode,
                    mobileNum:$("#mobileNum").val(),
                    registerBeginDate: $("#registerBeginDate").val(),
                    registerEndDate:  $("#registerEndDate").val()
                },
                page:1
            }).trigger("reloadGrid");
        },
        reset: function () {
            $(':input', '#appForm3').val('');
        },
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "app/financeuserregisterchannelinfo/delete",
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
			$.get(baseURL + "app/financeuserregisterchannelinfo/info/"+id, function(r){
                vm.financeUserRegisterChannelInfo = r.financeUserRegisterChannelInfo;
            });
		},
        reload: function (event) {
		    vm.reset();
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#mobileNum").val("")
            $("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'channelCode':$("#channelCode").val()
                },
                page:1
            }).trigger("reloadGrid");
        }
	}
});