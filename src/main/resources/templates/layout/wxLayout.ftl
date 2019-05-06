<#assign basePath = request.contextPath />
<!DOCTYPE html>
<#macro Head title charset="utf-8" lang="zh-CN">
<html lang="${lang}">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" >
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="${charset}"/>
    <link rel="shortcut icon" href="${basePath}/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${basePath}/statics/wx/css/site.css">
    <script type="text/javascript" src="${basePath}/statics/js/jquery3.2.1.min.js"></script>
    <title>${title}</title>
<#nested>
</head>
</#macro>
<#macro Body>
<body class="bg" ontouchstart="" onmouseover="">
<#nested>
</body>
</html>
</#macro>