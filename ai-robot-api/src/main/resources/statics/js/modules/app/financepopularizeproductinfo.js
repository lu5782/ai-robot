$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financepopularizeproductinfo/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 50, key: true },
            { label: '产品名称', name: 'productName', index: 'productName', width: 80 },
            { label: '跳转Url', name: 'redirectUrl', index: 'redirect_url', width: 80 },
            { label: '顺序', name: 'displayOrder', index: 'display_order', width: 80 },
            { label: '状态', name: 'isHomeOnline', index: 'is_home_online', width: 80,
                formatter: function (value, options, row) {
                    return value == "Y" ? "上线" : "下线"
                }
            },
            { label: '上线时间', name: 'updateTime', index: 'update_time', width: 80 },
            { label: '备注', name: 'remark', index: 'remark', width: 80 }
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
        productName:"",
		showList: true,
		title: null,
		financePopularizeProductInfo: {}
	},
	methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financepopularizeproductinfo/getSelectList';
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
			vm.financePopularizeProductInfo = {};
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
			var url = vm.financePopularizeProductInfo.id == null ? "app/financepopularizeproductinfo/save" : "app/financepopularizeproductinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financePopularizeProductInfo),
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
				    url: baseURL + "app/financepopularizeproductinfo/delete",
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
                    url: baseURL + "app/financepopularizeproductinfo/tapeout",
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
                    url: baseURL + "app/financepopularizeproductinfo/tapebelow",
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
			$.get(baseURL + "app/financepopularizeproductinfo/info/"+id, function(r){
                vm.financePopularizeProductInfo = r.financePopularizeProductInfo;
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