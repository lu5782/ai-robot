function queryOnLoad(id) {
    $.get("question/queryQuesttionById", {
        id : id
    }, function(result) {
        if (true) {
            $("#qaid").attr("value",id);
            if (result!=null && result.data!=null) {
                $("#title").attr("value", result.data.title);
                ue.setContent(result.data.content);
                $("#seqNo").attr("value", result.data.seqNo);
            }
        } else {
            alert("请求失败");
        }
    });


    /*$.ajax({
        url : "question/queryQuesttionById",
        async : true,
        type : "GET",
        data : {
            "id" : id
        },
        success : function(data) {
            $("#qaid").attr("value",id);
            if (data!=null && data.data!=null) {
                $("#title").attr("value", data.data.title);
                ue.setContent(data.data.content);
            }
        },
        error : function() {
            alert("请求失败");
        },
        dataType : "json"
    });*/



}


