<#assign basePath = request.contextPath />
<!DOCTYPE html>
<#macro Head title charset="utf-8" lang="zh-CN">
<html lang="${lang}">
<head>
    <meta name="viewport” content=”width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="${charset}"/>
    <link rel="shortcut icon" href="${basePath}/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${basePath}/statics/css/font.css">
    <link rel="stylesheet" href="${basePath}/statics/css/xadmin.css">
    <script type="text/javascript" src="${basePath}/statics/js/jquery3.2.1.min.js"></script>
    <script src="${basePath}/statics/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${basePath}/statics/js/xadmin.js"></script>
    <title>${title}</title>
<#nested>
</head>
</#macro>
<#macro Body>
<body>
<#nested>
</body>
</html>
</#macro>