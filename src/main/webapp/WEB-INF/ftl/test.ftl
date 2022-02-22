<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/resources/wangEditor.min.js"></script>
</head>
<body>
    <button id="btnRead">读取内容</button>
    <button id="btnWrite">写入内容</button>
    <div id="divEditor" style="width:800px;height:600px"></div>
    <script>
        // create Web rich editor
        var E = window.wangEditor;
        var editor = new E("#divEditor");
        editor.create();

        document.getElementById("btnRead").onclick = function(){
            var content = editor.txt.html(); //Get the editor content
            alert(content);
        }
        document.getElementById("btnWrite").onclick = function(){
            var content = "<li style='color:red'>我是<b>新内容</b></li>"
            editor.txt.html(content); //Write to the editor
        }
    </script>
</body>
</html>