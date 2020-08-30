$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/financemgcspblacklistreport/list',
        datatype: "json",
        colModel: [			

			{ label: '日期', name: 'statisticsDate', index: 'statistics_date', width: 180 },
			{ label: '入口名称', name: 'entryName', index: 'entry_name', width: 180 },
			{ label: '点击UV', name: 'clickUvNum', index: 'click_uv_num', width: 180 },
			{ label: '实际支付UV', name: 'paymentUvNum', index: 'payment_uv_num', width: 180 },
			{ label: '支付转化率', name: 'paymentConversionRate', index: 'payment_conversion_rate', width: 180 },

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
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

	queryBlackNum();
});

 function queryBlackNum(){
	axios.get(baseURL + "report/financemgcspblacklistreport/queryBlackNum")
		.then(function (response) {
			if (null != response.data) {
				vm.homeBlack = response.data.data.homeBlack;
				vm.aloneBlack = response.data.data.aloneBlack;
				vm.payUv = response.data.data.payUv;
				vm.payNum = response.data.data.payNum;
                $("#homeBlack").text(vm.homeBlack);
				$("#aloneBlack").text(vm.aloneBlack);
				$("#payUv").text(vm.payUv);
				$("#payNum").text(vm.payNum);

			}
		})
		.catch(function (error) {
			console.log(error);
		});
};



layui.use(['table', 'laydate', 'element'], function () {

	var laydate = layui.laydate;
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
			console.log(value); //得到日期生成的值，如：2017-08-18
			vm.queryParams.registerEndDate = value;
		}
	});
});

var vm = new Vue({
	el:'#financeApp',
	data:{
		showList: true,
		title: null,
		queryParams: {},
		financeMgcspBlacklistReport: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.financeMgcspBlacklistReport = {};
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
			var url = vm.financeMgcspBlacklistReport.id == null ? "app/financemgcspblacklistreport/save" : "app/financemgcspblacklistreport/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeMgcspBlacklistReport),
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
				    url: baseURL + "app/financemgcspblacklistreport/delete",
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
			$.get(baseURL + "app/financemgcspblacklistreport/info/"+id, function(r){
                vm.financeMgcspBlacklistReport = r.financeMgcspBlacklistReport;
            });
		},
		reset: function (event) {
			$(':input', '#appForm').val('');
			vm.queryParams.registerBeginDate = '';
			vm.queryParams.registerEndDate = '';
			$("#entryName").val("");
		},
		exportReport: function () {
			window.location.href = baseURL + "report/financemgcspblacklistreport/exportReport?registerBeginDate="
				+ vm.queryParams.registerBeginDate + "&registerEndDate="
				+ vm.queryParams.registerEndDate+ "&entryName="
				+ $("#entryName").val();
		},
		reload: function (event) {
			vm.showList = true;
			queryBlackNum();
			//传入查询条件参数
			$("#jqGrid").jqGrid("setGridParam", {
				postData: {
					registerBeginDate: vm.queryParams.registerBeginDate,
					registerEndDate: vm.queryParams.registerEndDate,
					//entryName: vm.queryParams.entryName
					entryName: $("#entryName").val()
				}
			});

			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});