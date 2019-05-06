<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
<div class="x-nav">
      <span class="layui-breadcrumb">
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon layui-icon-refresh-1" style="line-height:30px"></i></a>
</div>
<div class="x-body">
    <xblock>
        <button class="layui-btn" onclick="x_admin_show('添加公众号','${basePath}/admin/official/create')"><i
                class="layui-icon layui-icon-add-circle-fine"></i>添加
        </button>
    </xblock>
    <table class="layui-table" id="layui_table_id" lay-filter="dataTable">
    </table>
</div>
<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn-xs" lay-event="editMenu">自定义菜单</a>
</script>
<script>
    layui.use('table', function () {  // 引入 table模块
        var table = layui.table;
        table.render({
            id: "dataTable",//
            elem: '#layui_table_id',
            url: '${basePath}/admin/official/list',
            cellMinWidth: 20,
            even: true,
            page: true,
            limits: [20, 50, 100],
            limit: 20,
            method: 'get',
            cols: [[
                {
                    field: 'id',
                    hide : true
                },
                {
                    field: 'name',
                    title: '名字',
                    align: 'center'
                },
                {
                    field: 'account',
                    with: 100,
                    title: '原始ID',
                    align: 'center',
                    sort: false
                },
                {
                    field: 'appId',
                    title: '应用ID',
                    align: 'center'
                },
                {
                    field: 'appSecret',
                    title: '开发者密码',
                    align: 'center'
                },
                {
                    field: 'token',
                    title: '令牌',
                    align: 'center'
                },
                {
                    field: 'aesKey',
                    title: '消息加解密密钥',
                    align: 'center'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    align: 'center'
                },
                {
                    field: 'updateTime',
                    title: '修改时间',
                    align: 'center'
                },
                {
                    fixed:'right',
                    title:'操作',
                    toolbar:'#toolBar',
                    width:150,
                    align: 'center'

                }
            ]],
            parseData: function(res){
                data = res.data;
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": data.count, //解析数据长度
                    "data": data.items //解析数据列表
                };
            }
        })
        table.on('tool(dataTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'editMenu'){
            var appId =  data.appId;
            x_admin_show('自定义菜单','${basePath}/admin/official/menu/'+appId);
            }
        });
        })
</script>
</@Body>