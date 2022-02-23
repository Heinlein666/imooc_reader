<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>短评管理</title>
    <style>
        #dlgBook{
            padding: 10px
        }
    </style>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">


</head>
<body>


<div class="layui-container">
    <blockquote class="layui-elem-quote">近期短评列表</blockquote>
    <table id="grdEvaluation" lay-filter="grdEvaluation"></table>
</div>

<script src="/resources/layui/layui.all.js"></script>
<script>

    var table = layui.table;
    var $ = layui.$;
    var editor = null;
    //第一个实例
    table.render({
        elem: '#grdEvaluation'
        , id : "evaluationList"
        , url: "/management/evaluation/list" //数据接口
        , page: true //开启分页
        , limit :20
        , cols: [[ //表头
            {field:"createTime" , title: '发布时间', width: '200'}
            , {field: 'content', title: '短评', width: '400'}
            , {type:"space" ,title: '图书', width: '200', templet: function (d) {
                    return d.book.bookName;
            }}
            , {type: "space" , title: '用户名', width: '100', templet: function (d) {
                    console.info(d);
                    return d.member.username;
            }}
            , {type: 'space', title: '操作', width: '100' , templet : function(d){
                    if(d.state=="enable") {
                        return "<button class='layui-btn layui-btn-sm '  data-id='" + d.evaluationId + "' onclick='disableEvaluation(this)'>禁用</button>";
                    }else if(d.state =="disable"){
                        return "已禁用";
                    }
                }
            }
        ]]
    });
    function disableEvaluation(obj){
        var evaluationId = $(obj).data("id");
        layui.layer.prompt({
            title: '请输入禁用原因',
        },function(value, index, elem){
            $.post("/management/evaluation/disable" , {
                evaluationId : evaluationId , disableReason:value
            },function(json){
                if(json.code=="0"){
                    layui.layer.close(index);
                    layui.layer.msg("短评已禁用")
                    table.reload('evaluationList');
                }
            } , "json")

        });
    }
</script>
</body>
</html>