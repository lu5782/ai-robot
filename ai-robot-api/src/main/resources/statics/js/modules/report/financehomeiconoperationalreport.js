$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/financehomeiconoperationalreport/list',
        datatype: "json",
        colModel: [
			{ label: '日期', name: 'statisticsDate', index: 'statistics_date', width: 80 }, 			
			{ label: '图标名称', name: 'title', index: 'title', width: 80 }, 			
			{ label: '图标点击UV', name: 'totalUvNum', index: 'total_uv_num', width: 80 }, 			
			{ label: '图标跳转链接', name: 'redirectUrl', index: 'redirect_url', width: 80 }, 			
			{ label: '位置', name: 'displayOrder', index: 'display_order', width: 80 }
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
        iconTitle: null,
        queryParams: {},
		financeHomeIconOperationalReport: {}
	},
	methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'report/financehomeiconoperationalreport/getSelectList';
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
            vm.iconTitle = item.value;
        },
		query: function () {
			vm.reload();
		},
		add: function(){
            $("#expireTime").val();
			vm.showList = false;
			vm.title = "新增";
			vm.financeHomeIconOperationalReport = {};
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
			var url = vm.financeHomeIconOperationalReport.id == null ? "report/financehomeiconoperationalreport/save" : "report/financehomeiconoperationalreport/update";
            vm.financeTrainingCourseInfo.startTime = $("#registerEndDate").val();
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeHomeIconOperationalReport),
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
				    url: baseURL + "app.hotloan/financehomeiconoperationalreport/delete",
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
			$.get(baseURL + "report/financehomeiconoperationalreport/info/"+id, function(r){
                vm.financeHomeIconOperationalReport = r.financeHomeIconOperationalReport;
                $("#expireTime").val(vm.financeHomeIconOperationalReport.expireTime);
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    registerBeginDate: vm.queryParams.registerBeginDate,
                    registerEndDate: vm.queryParams.registerEndDate,
                    title: vm.iconTitle
                }
            }).trigger("reloadGrid");
		},
        reset: function (event) {
            $(':input', '#appForm').val('');
            vm.queryParams.registerBeginDate = '';
            vm.queryParams.registerEndDate = '';
            vm.iconTitle = '';
        },
        exportReport: function () {
            window.location.href = baseURL + "report/financehomeiconoperationalreport/exportReport?registerBeginDate="
                + vm.queryParams.registerBeginDate + "&registerEndDate=" + vm.queryParams.registerEndDate
                + "&title=" + vm.iconTitle;
        }
	}
});