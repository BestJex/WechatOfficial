<#include "../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
<body>
  <!-- 顶部开始 -->
    <div class="container">
      <div class="logo"><a href="${basePath}/admin/index">公众号后台管理系统</a></div>
      <div class="left_open">
        <i title="展开左侧栏" class="iconfont">&#xe699;</i>
      </div>
      <ul class="layui-nav left fast-add" lay-filter="">
        <li class="layui-nav-item"></li>
      </ul>
      <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
          <a href="javascript:;">${adminUser.username}</a>
          <dl class="layui-nav-child"> <!-- 二级菜单 -->
            <dd><a href="${basePath}/admin/user/loginOut">退出</a></dd>
          </dl>
        </li>
      </ul>
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
    <!-- 左侧菜单开始 -->
    <div class="left-nav">
      <div id="side-nav">
        <ul id="nav">
          <li>
            <a href="javascript:;">
              <i class="iconfont">&#xe723;</i>
              <cite>公众号管理</cite>
              <i class="iconfont nav_right">&#xe697;</i>
            </a>
            <ul class="sub-menu">
              <li>
                <a _href="${basePath}/admin/official/index">
                  <i class="iconfont">&#xe6a7;</i>
                  <cite>公众号列表</cite>
                </a>
              </li>
                <li>
                    <a _href="${basePath}/admin/payment/index">
                        <i class="iconfont">&#xe6a7;</i>
                        <cite>支付配置</cite>
                    </a>
                </li>
              <li>
                <a _href="${basePath}/admin/subscribeReply/index">
                  <i class="iconfont">&#xe6a7;</i>
                  <cite>关注回复</cite>
                </a>
              </li>
              <li>
                <a _href="${basePath}/admin/keywordReply/index">
                  <i class="iconfont">&#xe6a7;</i>
                  <cite>关键词回复</cite>
                </a>
              </li>
            </ul>
          </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe723;</i>
                    <cite>用户管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${basePath}/admin/officialUser/index">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>用户列表</cite>
                        </a>
                    </li >
                </ul>
            </li>
          <li>
            <a href="javascript:;">
              <i class="iconfont">&#xe723;</i>
              <cite>活动管理</cite>
              <i class="iconfont nav_right">&#xe697;</i>
            </a>
              <ul class="sub-menu">
                <li>
                  <a _href="${basePath}/admin/activity/index">
                    <i class="iconfont">&#xe6a7;</i>
                    <cite>活动列表</cite>
                  </a>
                </li >
              </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe723;</i>
                    <cite>订单管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${basePath}/admin/order/index">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>订单列表</cite>
                        </a>
                    </li >
                </ul>
            </li>
            <#--<li>-->
                <#--<a href="javascript:;">-->
                    <#--<i class="iconfont">&#xe726;</i>-->
                    <#--<cite>管理员管理</cite>-->
                    <#--<i class="iconfont nav_right">&#xe697;</i>-->
                <#--</a>-->
                <#--<ul class="sub-menu">-->
                    <#--<li>-->
                        <#--<a _href="admin-list.html">-->
                            <#--<i class="iconfont">&#xe6a7;</i>-->
                            <#--<cite>管理员列表</cite>-->
                        <#--</a>-->
                    <#--</li >-->
                <#--</ul>-->
            <#--</li>-->

            </li>
        </ul>
      </div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
          <ul class="layui-tab-title">
            <li class="home"><i class="layui-icon">&#xe68e;</i>我的桌面</li>
          </ul>
          <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='${basePath}/admin/welcome' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
<script type="text/javascript">
function gotoLogin(){
    window.location.href = "${basePath}/admin/user/login";
}
</script>
</@Body>
