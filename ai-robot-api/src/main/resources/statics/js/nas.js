$(function () {
    vm.getList();
});

const vm = new Vue({
    el: '#financeApp',
    data: {
        name: "",
        type: "",
        showList: true,
        title: "标题-nas盘",
        financeHisIncrementRule: {
            firstText: "每",
            lastText: "个刷1个"
        }
    },
    methods: {
        uploadFile: function () {
            //调用的后台接口
            let url = baseURL + '/nas/upload';
            //从后台获取到对象数组
            axios.get(url).then((response) => {
                //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
                if (null != response) {
                    vm.restaurants = response.data;
                }
            }).catch((error) => {
                console.log(error);
            });
        },
        getFileLists() {
            console.log("this window.onload");
            $.ajax(
                {
                    url: '/nas/getChild',
                    data: JSON.stringify({filePath: "/"}),   // 需要传送的参数
                    type: 'POST',   // 请求方式
                    dataType: 'json', // 返回数据的格式, 通常为JSON
                    contentType: 'application/json',
                    success: function (response) { // 请求成功后的回调函数
                        console.log("response=" + response);
                        let list = []
                        let i = {"reamrk": response[0], "creator": response[0], "value": response[0]}
                        console.log("i=" + i);
                        list.push(i)
                        console.log("list=" + list.length);
                        console.log("list=" + JSON.stringify(i));
                        vm.restaurants = list;
                    },
                    error: function () {
                        console.log("response=" + response);
                        console.log('Send Request Fail..'); // 请求失败时的回调函数
                    }
                }
            );
        },
        getList() {
            $("#jqGrid").jqGrid({
                url: baseURL + 'nas/getChild',
                mtype: "POST",
                ajaxGridOptions: {
                    contentType: "application/json",
                },
                // ---不需要传分页信息时使用---
                // postData: JSON.stringify({
                //     "filePath": "/",
                // }),
                // ---不需要传分页信息时使用---

                // ---需要传分页信息时使用---
                prmNames: {
                    page: "page",
                    rows: "rows",
                    order: "sort",
                },
                postData: {
                    "filePath": "/",
                },
                // ---需要传分页信息时使用---
                page: 1,
                rowNum: 4,                     //在grid上显示记录条数，这个参数是要被传递到后台
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: true,
                caption: "操作数组数据",          //相当于标题
                // colNames: ['名称','类型','修改日期','大小','最后修改人','备注'],
                colModel: [
                    {label: '名称', name: 'name', index: 'rule_name', width: 80},
                    {label: '类型', name: 'type', index: 'product_name', width: 80},
                    {label: '修改日期', name: 'updateDate', index: 'create_time', width: 80},
                    {label: '大小', name: 'size', index: 'rule_content', width: 80},
                    {label: '最后修改人', name: 'updateBy', index: 'creator', width: 80},
                    {label: '备注', name: 'remark', index: 'remark', width: 80},
                ],
                viewrecords: true,
                height: 385,

                pager: "#jqGridPager",
                datatype: "json",           // 返回值的数据格式
                jsonReader: {
                    root: "list",           // json中代表实际模型数据的入口
                    page: "currPage",       // json中代表当前页码的数据
                    total: "totalPage",     // json中代表页码总数的数据
                    records: "totalCount",  // json中代表数据行总数的数据
                    rows: "list",
                },
                onSelectRow: function (id) {
                    //当选择行时触发此事件。rowid：当前行id；status：选择状态，当multiselect 为true时此参数才可用
                    console.log('rowid=' + id);
                },
                gridComplete: function () {
                    // 当表格所有数据都加载完成而且其他的处理也都完成时触发此事件，排序，翻页同样也会触发此事件
                    // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
                    // vm.refresh();
                    var ids = $("#jqGrid").jqGrid("getDataIDs");//获取所有行的id
                    var rowData = $("#jqGrid").getRowData(ids[0]);
                    console.log("第一行数据=" + rowData)

                    //隐藏grid底部滚动条
                    // $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            }).trigger("reloadGrid") //trigger(“reloadGrid”);为重新载入jqGrid表格。
        },
        refresh: function () {
            vm.showList = true;
            // const page = $("#jqGrid").jqGrid('getGridParam', 'page');
            const page = $('#jqGrid').getGridParam('page'); // current page
            const rows = $('#jqGrid').getGridParam('rows'); // rows
            const sort = $('#jqGrid').getGridParam('sort'); // sort

            $("#jqGrid").jqGrid('setGridParam', {
                url: url,
                page: page,
                rows: rows,
                sort: sort
            }).trigger("reloadGrid");
        },
        onPaging:function(pageBtn){ //翻页实现

            var records = $("#jqGrid").getGridParam('page');//获取返回的记录数
            console.log("records page=" + records)

            var page = $("#jqGrid").getGridParam('page');//获取返回的当前页
            var rowNum = $("#jqGrid").getGridParam('rowNum'); //获取显示配置记录数量
            var total = Math.ceil(records/rowNum); //$("#jqGrid").getGridParam('total');//获取总页数
            if(pageBtn==="next" && page<total){
                page = parseInt(page)+1;
            }
            if(pageBtn==="prev" && page>1){
                page = parseInt(page)-1;
            }
            if(pageBtn==="last"){
                page = total;
            }
            if(pageBtn==="first"){
                page = 1;
            }
            //这里的 loadLoalData( );是自定义的一个js方法。重复利用
        }

    }
});