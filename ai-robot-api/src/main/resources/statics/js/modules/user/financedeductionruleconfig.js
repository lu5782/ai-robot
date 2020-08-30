$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financedeductionruleconfig/list',
        datatype: "json",
        colModel: [			
			{ label: '规则内容', name: 'ruleContent', index: 'rule_content', width: 440 },
			{ label: '规则大类', name: 'ruleName', index: 'rule_name', width: 150 },
			{ label: '渠道代码(AMID)', name: 'channelCode', index: 'channel_code', width: 150 },
			{ label: '开始执行时间', name: 'executionTime', index: 'execution_time', width: 150 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 150 },
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 150 },
			{ label: '创建者', name: 'creator', index: 'creator', width: 150 },
			{ label: '更新者', name: 'updator', index: 'updator', width: 150 },
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
    vm.loadAll();
});

function Editor1(ruleName) {
}
var vm = new Vue({
	el:'#financeApp',
	data:{
        switch1:false,
		showList: true,
		title: null,
        channelCodeSearch: null,
        financeDeductionRuleConfig:{
            isVipCourse:1,
            t1:"前",
            t2:"个不扣量",
            t3:"每",
            t4:"个扣",
            t5:"个"
        }
	},
	methods: {
        loadAll: function () {
            //调用的后台接口
            let url1 = baseURL + 'app/financechannelconfiginfo/getSelectChannelCodeList';
            //从后台获取到对象数组
            axios.get(url1).then((response) => {
                //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
                if(null != response)
                {vm.restaurantsCode = response.data;}
            }).catch((error) => {
                    console.log(error);
            });
        },
        querySearchChannelCodeAsync(queryString, cb) {
            var restaurants = vm.restaurantsCode;
            var results = queryString ? restaurants.filter(vm.createStateFilter(queryString)) : restaurants;
            cb(results);
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelectChannelCode(item) {
            vm.channelCodeSearch = item.value;
        },
		query: function () {
			vm.reload();
		},
		add: function(){
            switch1:false,
			vm.showList = false;
			vm.title = "新增";
			vm.financeDeductionRuleConfig = {
                isVipCourse:1,
				t1:"前",
				t2:"个不扣量，之后每",
				t3:"每",
				t4:"个扣",
				t5:"个"
			};
            var count = $("#jqGrid").getGridParam("reccount");//当前有几行
            $.ajax({
                type: "GET",
                url: baseURL + "app/financedeductionruleconfig/querychannel",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        var data = r.list;
                        if (data.length > 0) {
                            $("#channelCode").html("");
                            var channelCodeHtml = "<option value=''> 渠道编码（AMID）</option>";
                            for (var i = 0; i < data.length; i++) {
                                var item = data[i];
                                channelCodeHtml += "<option     value=" + item.channelCode + ">" + item.channelCode + "</option>";

                            }
                        }
                        $('#channelCode').append(channelCodeHtml);
                        layui.use('form', function () {
                            var form = layui.form;
                            form.render();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
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
        details: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
		saveOrUpdate: function (event) {
			var url = vm.financeDeductionRuleConfig.id == null ? "app/financedeductionruleconfig/save" : "app/financedeductionruleconfig/update";
          vm.financeDeductionRuleConfig.executionTime=$("#executionTime").val();
            $.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeDeductionRuleConfig),
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
				    url: baseURL + "app/financedeductionruleconfig/delete",
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
            vm.financeDeductionRuleConfig.isVipCourse=1;
			$.get(baseURL + "app/financedeductionruleconfig/info/"+id, function(r){
                vm.financeDeductionRuleConfig = r.financeDeductionRuleConfig;
                vm.financeDeductionRuleConfig.t1="前";
                vm.financeDeductionRuleConfig.t2="个不扣量";
                vm.financeDeductionRuleConfig.t3="每";
                vm.financeDeductionRuleConfig.t4="个扣";
                vm.financeDeductionRuleConfig.t5="个";
                $("#executionTime").val(vm.financeDeductionRuleConfig.executionTime);
                $("#channelCode").html("");
                    var channelCodeHtml = "<option     value=" + vm.financeDeductionRuleConfig.channelCode + ">" + vm.financeDeductionRuleConfig.channelCode + "</option>";
            $('#channelCode').append(channelCodeHtml);
            });

        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    channelCode: vm.channelCodeSearch
                },
                page:1
            }).trigger("reloadGrid");
		}
	}
});