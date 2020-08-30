$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financehiseductionruleconfig/list',
        datatype: "json",
        colModel: [			
			{ label: '规则内容', name: 'ruleContent', index: 'rule_content', width: 200 },
			{ label: '规则名称', name: 'ruleName', index: 'rule_name', width: 200 }, 			
			{ label: '渠道代码', name: 'channelCode', index: 'channel_code', width: 200 }, 			
			{ label: '开始执行时间', name: 'executionTime', index: 'execution_time', width: 200 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 200 },
            { label: '操作人', name: 'operator', index: 'operator', width: 200 },
            { label: '操作类型', name: 'operateType', index: 'operate_type', width: 200 },
            { label: '操作时间', name: 'operateTime', index: 'operate_time', width: 200 },
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
            vm.financeHisEductionRuleConfig.registerBeginDate = value;
        }
    });
    laydate.render({
        elem: '#registerEndDate'
        , format: 'yyyy-MM-dd'
        //, type: 'datetime'
        , theme: 'molv'
        , showBottom: false
        , done: function (value, date, endDate) {
            vm.financeHisEductionRuleConfig.registerEndDate = value;
        }
    });
});

var vm = new Vue({
	el:'#financeApp',
	data:{
		showList: true,
		title: null,
		financeHisEductionRuleConfig: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
        reset: function () {
            $(':input', '#appForm').val('');
        },
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.financeHisEductionRuleConfig = {};
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
			var url = vm.financeHisEductionRuleConfig.id == null ? "app/financehiseductionruleconfig/save" : "app/financehiseductionruleconfig/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeHisEductionRuleConfig),
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
				    url: baseURL + "app/financehiseductionruleconfig/delete",
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
			$.get(baseURL + "app/financehiseductionruleconfig/info/"+id, function(r){
                vm.financeHisEductionRuleConfig = r.financeHisEductionRuleConfig;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    channelCode:$("#channelCode").val(),
                    registerBeginDate: vm.financeHisEductionRuleConfig.registerBeginDate,
                    registerEndDate: vm.financeHisEductionRuleConfig.registerEndDate
                },
                page:1
            }).trigger("reloadGrid");
		}
	}
});