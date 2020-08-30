$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financedeductionregisterchannelinfo/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 },
			{ label: '手机号码', name: 'mobileNum', index: 'mobile_num', width: 80 }, 			
			{ label: '渠道编码AMID', name: 'channelCode', index: 'channel_code', width: 80 },
			{ label: '渠道详情', name: 'channelName', index: 'channel_name', width: 80 }, 			
			{ label: '注册平台类型', name: 'platformType', index: 'platform_type', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '扣量规则', name: 'ruleId', index: 'rule_id', width: 80 }, 			
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
var vm = new Vue({
	el:'#financeApp',
	data:{
		showList: true,
		title: null,
		financeDeductionRegisterChannelInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
        reset: function () {
            $(':input', '#appForm3').val('');
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    channelCode:$("#channelCode").val(),
                    registerBeginDate: $("#registerBeginDate").val(),
                    registerEndDate:  $("#registerEndDate").val()
                },
                page:1
            }).trigger("reloadGrid");
		}
	}
});