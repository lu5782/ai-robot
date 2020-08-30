$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financeloaninfo/list',
        datatype: "json",
        colModel: [
            { label: '产品logo', name: 'logoUrl', index: 'logo_url', width: 180 },
            { label: '产品名称', name: 'productName', index: 'product_name', width: 180 },
            { label: '放款人数', name: 'productDesc', index: 'product_desc', width: 180 },
            { label: '额度范围', name: 'loanAmount', index: 'loan_amount', width: 180 },
            { label: '贷款产品期限', name: 'loanTerm', index: 'loan_term', width: 180 },
            { label: '产品标签', name: 'productTags', index: 'product_tags', width: 180 },
			{ label: '日利率', name: 'dailyInterestRate', index: 'daily_interest_rate', width: 180 },
			{ label: '角标', name: 'subscript', index: 'subscript', width: 180 },
            { label: '跳转url', name: 'redirectUrl', index: 'redirect_url', width: 180 },
			{ label: 'uv预警值', name: 'uvAlarmValue', index: 'uv_alarm_value', width: 180 }, 			
			{ label: '顺序', name: 'displayOrder', index: 'display_order', width: 180 },
            { label: '状态', name: 'isDelete', index: 'is_delete', width: 180,
                formatter: function (value, options, row) {
                    return value == "0" ? "上线" : "下线"
                }
            }	,
			{ label: '上线时间', name: 'onlineTime', index: 'online_time', width: 180 },
            { label: '备注', name: 'remark', index: 'remark', width: 180 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
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
        }
    });
    vm.loadAll();
});

var vm = new Vue({
	el:'#financeApp',
	data:{
        productName:"",
		showList: true,
		title: null,
		financeLoanInfo: {}
	},
	methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financeloaninfo/getSelectList';
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
            vm.productName = item.value;
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.financeLoanInfo = {};
            $("#div_showLogo img").remove();
            var inputText = "<img class=\'layui-upload-img\' id=\'showLogo\' style=\'height:180px;width:180px;\'>";
            $('#div_showLogo').append(inputText);
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
			var url = vm.financeLoanInfo.id == null ? "app/financeloaninfo/save" : "app/financeloaninfo/update";
            vm.financeLoanInfo.logoUrl = $("#search_popularBannerUrl").val();//获取图片路径
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeLoanInfo),
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
        tapebelow: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要上线选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "app/financeloaninfo/tapeout",
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
                    url: baseURL + "app/financeloaninfo/tapebelow",
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
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "app/financeloaninfo/delete",
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
			$.get(baseURL + "app/financeloaninfo/info/"+id, function(r){
                vm.financeLoanInfo = r.financeLoanInfo;
                $("#search_popularBannerUrl").val(vm.financeLoanInfo.logoUrl);//得到图片路径
                $('#showLogo').attr('src', vm.financeLoanInfo.logoUrl);//得到图片显示
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'productName': vm.productName
                },
                page:1
            }).trigger("reloadGrid");
		}
	}
});