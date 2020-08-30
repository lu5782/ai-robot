$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/financedailydownloadreport/list',
        datatype: "json",
        colModel: [
			{ label: '日期', name: 'statisticsDate', index: 'statistics_date', width: 70 },
			{ label: 'H5名称', name: 'hfiveName', index: 'hfive_name', width: 70 },
			{ label: 'H5页面UV', name: 'hfiveUv', index: 'hfive_uv', width: 80 },
			{ label: 'IOS下载页UV', name: 'downloadIosUv', index: 'download_ios_uv', width: 80 },
			{ label: 'IOS下载页点击立即下载UV', name: 'downloadClickIosUv', index: 'download_click_ios_uv', width: 100 },
			{ label: 'Android下载页UV', name: 'downloadArdUv', index: 'download_ard_uv', width: 100 },
			{ label: 'H5到IOS下载页转化率', name: 'downloadHfiveIosRate', index: 'download_hfive_ios_rate', width: 100 },
			{ label: 'H5到Android下载页转化率', name: 'downloadHfiveArdRate', index: 'download_hfive_ard_rate', width: 100 }
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

	queryDownloadNum();

});


function queryDownloadNum(){
	axios.get(baseURL + "report/financedailydownloadreport/queryDownloadNum")
		.then(function (response) {
			if (null != response.data) {
				vm.hfiveNo = response.data.data.hfiveNo;
				vm.hfiveYes = response.data.data.hfiveYes;
				vm.hfiveDetail = response.data.data.hfiveDetail;
				vm.downloadIos = response.data.data.downloadIos;
				vm.downloadArd = response.data.data.downloadArd;

				$("#hfiveNo").text(vm.hfiveNo);
				$("#hfiveYes").text(vm.hfiveYes);
				$("#hfiveDetail").text(vm.hfiveDetail);
				$("#downloadIos").text(vm.downloadIos);
				$("#downloadArd").text(vm.downloadArd);

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
		financeDailyDownloadReport: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.financeDailyDownloadReport = {};
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
			var url = vm.financeDailyDownloadReport.id == null ? "app/financedailydownloadreport/save" : "app/financedailydownloadreport/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeDailyDownloadReport),
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
				    url: baseURL + "app/financedailydownloadreport/delete",
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
			$.get(baseURL + "app/financedailydownloadreport/info/"+id, function(r){
                vm.financeDailyDownloadReport = r.financeDailyDownloadReport;
            });
		},
		reset: function (event) {
			$(':input', '#appForm').val('');
			vm.queryParams.registerBeginDate = '';
			vm.queryParams.registerEndDate = '';
			$("#hfiveName").val("");
		},
		exportReport: function () {
			window.location.href = baseURL + "report/financedailydownloadreport/exportReport?registerBeginDate="
				+ vm.queryParams.registerBeginDate + "&registerEndDate="
				+ vm.queryParams.registerEndDate+ "&hfiveName="
				+ $("#hfiveName").val();
		},
		reload: function (event) {
			vm.showList = true;
			queryDownloadNum();
			//传入查询条件参数
			$("#jqGrid").jqGrid("setGridParam", {
				postData: {
					registerBeginDate: vm.queryParams.registerBeginDate,
					registerEndDate: vm.queryParams.registerEndDate,
					//hfiveName: vm.queryParams.hfiveName,
					//registerBeginDate: $("#registerBeginDate").val(),
					//registerEndDate: $("#registerEndDate").val(),
					hfiveName: $("#hfiveName").val()
				}
			});

			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});