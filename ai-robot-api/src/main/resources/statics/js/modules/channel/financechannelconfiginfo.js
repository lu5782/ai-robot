$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/financechannelconfiginfo/list',
        datatype: "json",
        colModel: [
            { label: '渠道大类', name: 'channelName', index: 'channel_name', width: 100 },
            { label: '渠道描述', name: 'channelDesc', index: 'channel_desc', width: 100 },
            { label: '渠道大类Id', name: 'channelType', index: 'channel_desc', width: 100 },
            { label: 'AMID', name: 'channelCode', index: 'channel_code', width: 100 },
            {label: '投放链接类型', name: 'servingLinkType', index: 'serving_link_type', width: 100,
                formatter: function (value) {
                    switch (value) {
                        case "phone":
                            return "无验证码";
                        case "sms":
                            return "有验证码";
                        case "apply":
                            return "现金贷";
                        default :
                            return ""
                    }
                }
            },
            { label: '长链接地址', name: 'linkUrl', index: 'link_url', width: 700 },
            { label: 'Approach1', name: 'approach1', index: 'approach1', width: 100 },
            { label: 'Approach2', name: 'approach2', index: 'approach2', width: 100 },
            { label: 'Approach3', name: 'approach3', index: 'approach3', width: 100 },
            { label: '短链接地址', name: 'shortLinkUrl', index: 'short_link_url', width: 100 },
            { label: 'APP是否跳转现金贷', name: 'isCashLoanProcess', index: 'is_cash_loan_process', width: 100 ,
                formatter: function (value) {
                    switch (value) {
                        case "Y":
                            return "是";
                        case "N":
                            return "否";
                        default :
                            return ""
                    }
                }
            },
            { label: '过期时间', name: 'expireTime', index: 'expire_time', width: 100 },
            { label: '使用人', name: 'userName', index: 'user_name', width: 100 },
            { label: '上线时间', name: 'update_time', index: 'user_name', width: 100 },
			{ label: '备注', name: 'remark', index: 'remark', width: 100 }
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
    vm.getConfigUrl();
});

var vm = new Vue({
	el:'#financeApp',
	data:{
        channelName:"",
		showList: true,
		title: null,
		financeChannelConfigInfo: {},
        UrlRequest:{},
        configUrl:""
	},
	methods: {
        loadAll: function () {
            //调用的后台接口
            let url = baseURL + 'app/financechannelconfiginfo/getSelectList';
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
        generateUrl:function(){
            if ($("#channelDesc").val()==null){
                alert("请选择渠道大类");
                return;
            }
            if (vm.financeChannelConfigInfo.channelCode==null){
                alert("请输入AMID");
                return;
            }
            var linkUrl=vm.configUrl+vm.financeChannelConfigInfo.pageUrl+"&am_id="+vm.financeChannelConfigInfo.channelCode;
            $("#search_cashbackType").val();
            if(!vm.financeChannelConfigInfo.approach1==''){
                linkUrl+="&approach1="+encodeURIComponent(
                    vm.financeChannelConfigInfo.approach1)
            }
            if(!vm.financeChannelConfigInfo.approach2==''){
                linkUrl+="&approach2="+encodeURIComponent(
                    vm.financeChannelConfigInfo.approach2)
            }
            if(!vm.financeChannelConfigInfo.approach3==''){
               linkUrl+="&approach3="+encodeURIComponent(
                vm.financeChannelConfigInfo.approach3)
            }
            vm.UrlRequest.linkUrl   =linkUrl;
            $("#linkUrl").val(vm.UrlRequest.linkUrl)
            $.ajax({
                type: "POST",
                url: baseURL + "createShortLink",
                data: JSON.stringify(vm.UrlRequest),
                contentType: "application/json",
                success: function (r) {

                    if (r.errorCode == 0000000) {
                        var data = r.data;
                        if (data.length > 0) {
                            alert('操作成功', function(index){
                            });
                            vm.financeChannelConfigInfo.shortLinkUrl=data;
                            $("#shortLinkUrl").val(vm.financeChannelConfigInfo.shortLinkUrl)
                            $("#linkUrl").val(linkUrl)
                        }
                    } else {
                        alert(r.errorMessage);
                    }
                }
            });
        },
        createStateFilter(queryString) {
            return (state) =>{return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);};
        },
        handleSelect(item) {
            vm.channelName = item.value;
        },
		query: function () {
			vm.reload();
		},
		add: function(){
            $("#expireTime").val("");
			vm.showList = false;
			vm.title = "新增";
			vm.financeChannelConfigInfo = {
                pageUrl:"reg?processType=phone"
            };
            $("#linkUrl").val("")
            $.ajax({
                type: "GET",
                url: baseURL + "app/financechannelconfiginfo/querychannel",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        var data = r.list;
                        if (data.length > 0) {
                            $("#channelDesc").html("");
                            var html = "<option value=''> 请选择渠道大类</option>";
                            for (var i = 0; i < data.length; i++) {
                                var item = data[i];
                                html += "<option     value=" + item.channelType + ">" + item.channelName + "</option>";
                            }
                        }
                        $('#channelDesc').append(html);
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
		saveOrUpdate: function (event) {
            if (vm.financeChannelConfigInfo.pageUrl == "reg?processType=phone") {

                vm.financeChannelConfigInfo.servingLinkType = "phone";
            } else if (vm.financeChannelConfigInfo.pageUrl == "regSms?processType=sms") {
                vm.financeChannelConfigInfo.servingLinkType = "sms";
            } else if (vm.financeChannelConfigInfo.pageUrl == "regSms?processType=apply") {
                vm.financeChannelConfigInfo.servingLinkType = "apply";
            }
			var url = vm.financeChannelConfigInfo.id == null ? "app/financechannelconfiginfo/save" : "app/financechannelconfiginfo/update";
            vm.financeChannelConfigInfo.linkUrl = $("#linkUrl").val();

            if (vm.financeChannelConfigInfo.userName==null||vm.financeChannelConfigInfo.channelCode==null||$("#channelDesc").val()==null){
                alert("必填数据不能为空")
                return;
            }
            vm.financeChannelConfigInfo.expireTime = $("#expireTime").val();

			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.financeChannelConfigInfo),
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
				    url: baseURL + "app/financechannelconfiginfo/delete",
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
			$.get(baseURL + "app/financechannelconfiginfo/info/"+id, function(r){
                vm.financeChannelConfigInfo = r.financeChannelConfigInfo;
                $("#channelDesc").html("");
                   var  html = "<option     value=" + vm.financeChannelConfigInfo.channelType + ">" + vm.financeChannelConfigInfo.channelName + "</option>";
            $('#channelDesc').append(html);
                $("#expireTime").val(vm.financeChannelConfigInfo.expireTime);
                $("#linkUrl").val(vm.financeChannelConfigInfo.linkUrl)
                if (vm.financeChannelConfigInfo.servingLinkType=="phone") {
                   vm. financeChannelConfigInfo.pageUrl="reg?processType=phone";
                }else  if (vm.financeChannelConfigInfo.servingLinkType=="sms") {
                   vm.financeChannelConfigInfo.pageUrl="regSms?processType=sms";
                }else  if (vm.financeChannelConfigInfo.servingLinkType=="apply"){
                    vm.financeChannelConfigInfo.pageUrl="regSms?processType=apply";
                }
            });

		},
        getConfigUrl: function(id){
            $.get(baseURL + "app/financechannelconfiginfo/getConfigUrl", function(r){
                vm.configUrl=r.configUrl;
            });

        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'channelName': vm.channelName
                },
                page:1
            }).trigger("reloadGrid");
		}
	}
});