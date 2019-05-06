<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
<body class="login-bg">
    <div class="login layui-anim layui-anim-up">
        <div class="message">公众号后台管理登录</div>
        <div id="darkbannerwrap"></div>
        <form method="post" action = "${basePath}/admin/user/login" class="layui-form" >
            <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" value="${loginModel.username!}" >
            <hr class="hr15">
            <input name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input" value="">
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >
            <div>
                <span style="color: red">${loginModel.error!}</span>
            </div>
        </form>
    </div>
</@Body>