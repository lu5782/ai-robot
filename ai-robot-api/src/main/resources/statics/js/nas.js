$(function () {
    getFiles("/")
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
    methods: {}
})

function getFileList(filePath) {
    $.ajax(
        {
            url: '/nas/getChild',
            data: {filePath: filePath},   // 需要传送的参数
            type: 'POST',   // 请求方式
            dataType: 'json', // 返回数据的格式, 通常为JSON
            contentType: 'application/json',
            success: function (response) { // 请求成功后的回调函数
                console.log("Send Request success response=  " + JSON.stringify(response));
            },
            error: function (response) {
                console.log('Send Request Fail..' + response); // 请求失败时的回调函数
            }
        }
    );

    $("#jqGrid").trigger("reloadGrid") //trigger(“reloadGrid”);为重新载入jqGrid表格。
}

function getFiles(filePath) {
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
            "filePath": filePath,
        },
        // ---需要传分页信息时使用---
        page: 1,
        rowNum: 10,                     //在grid上显示记录条数，这个参数是要被传递到后台
        rowList: [10, 30, 50],
        rownumbers: false,               //显示序列号
        rownumWidth: 25,　　               //序号列宽
        autowidth: true,
        multiselect: true,             //是否显示每一行第一列的复选框
        caption: "数据列表",             //相当于标题
        // colNames: ['名称','类型','修改日期','大小','最后修改人','备注'],
        colModel: [
            {label: '名称', name: 'name', index: 'rule_name', width: 80},
            {label: '类型', name: 'type', index: 'product_name', width: 80},
            {label: '修改日期', name: 'updateDate', index: 'create_time', width: 80},
            {label: '大小', name: 'size', index: 'rule_content', width: 80},
            {label: '最后修改人', name: 'updateBy', index: 'creator', width: 80},
            {label: '备注', name: 'remark', index: 'remark', width: 80},
        ],
        viewrecords: true,             //是否显示总的记录条数
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
            const rowData = $("#jqGrid").getRowData(id);
            console.log('rowData=' + rowData.name);
            getChildFiles(rowData.name);
        },
        gridComplete: function () {
            // 当表格所有数据都加载完成而且其他的处理也都完成时触发此事件，排序，翻页同样也会触发此事件
            // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
            // vm.refresh();
            // const ids = $("#jqGrid").jqGrid("getDataIDs");//获取所有行的id
            // const rowData = $("#jqGrid").getRowData(ids[0]);
            //隐藏grid底部滚动条
            // $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    })
        .trigger("reloadGrid") //trigger(“reloadGrid”);为重新载入jqGrid表格。
}

function getChildFiles(filePath) {
    // const page = $("#jqGrid").jqGrid('getGridParam', 'page');
    const page = $('#jqGrid').getGridParam('page'); // current page
    const rows = $('#jqGrid').getGridParam('rows'); // rows
    const sort = $('#jqGrid').getGridParam('sort'); // sort
    console.log('refresh filePath=' + filePath);
    // $("#jqGrid").jqGrid('clearGridData');// 清空数据
    $("#jqGrid").jqGrid('setGridParam', {
        url: baseURL + 'nas/getChild',
        page: page,
        rows: rows,
        order: sort,
        postData: {             //新的查询参数
            "filePath": filePath,
        }
    }).trigger("reloadGrid");
}

// function uploadFile() {
//     //调用的后台接口
//     let url = baseURL + '/nas/upload';
//     console.log("uploadFile..." + url)
//
//     //从后台获取到对象数组
//     axios.get(url).then((response) => {
//         //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
//         if (null != response) {
//             console.log(response);
//         }
//     }).catch((error) => {
//         console.log(error);
//     });
//
//     // upload.render({
//     //     elem: '#test3',
//     //     url: url,     //此处配置你自己的上传接口即可
//     //     accept: 'file', //普通文件
//     //     done: function (res) {
//     //         layer.msg('上传成功');
//     //         console.log(res);
//     //     }
//     // });
// }


function clickButton() {
    console.log('clickButton');
}