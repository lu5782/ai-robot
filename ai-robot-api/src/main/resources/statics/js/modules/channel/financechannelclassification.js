$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financechannelclassification/list',
        datatype: "json",
        colModel: [			
			{ label: '渠道大类id', name: 'channelType', index: 'channel_type', width: 200 },
			{ label: '渠道大类', name: 'channelName', index: 'channel_name', width: 200 },
			{ label: '状态', name: 'isDelete', index: 'is_delete', width: 200,
                formatter: function (value) {
                    return value == "0" ? "上线" : "下线"
                }
			},
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 200 },
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 200 }			
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
        }
    });
});

var vm = new Vue({
	el:'#financeApp',
	data:{
		showList: true,
		title: null,
		financeChannelClassification: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.financeChannelClassification = {};
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
			var url = vm.financeChannelClassification.id == null ? "app/financechannelclassification/save" : "app/financechannelclassification/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeChannelClassification),
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
				    url: baseURL + "app/financechannelclassification/delete",
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
			$.get(baseURL + "app/financechannelclassification/info/"+id, function(r){
                vm.financeChannelClassification = r.financeChannelClassification;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:1
            }).trigger("reloadGrid");
		}
	}
});