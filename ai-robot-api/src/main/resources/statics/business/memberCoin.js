function queryOnLoad(userId) {

    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 315
            ,url: 'members/queryMemberCoinDetail' //数据接口
            ,method:'post'
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'seqNo', title: '序号', width:60,type:'numbers'}
                ,{field: 'createDate', title: '时间'}
                ,{field: 'eventName', title: '事件'}
                ,{field: 'eventType', title: '增减'}
                ,{field: 'eventCoinNum', title: '金币数'}
            ]]
            ,where : {
                userId : userId
            }
        });

    });
}


