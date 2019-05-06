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
        <button class="layui-btn" onclick="x_admin_show('添加活动','${basePath}/admin/activity/create')"><i
                class="layui-icon layui-icon-add-circle-fine"></i>添加
        </button>
    </xblock>
    <table class="layui-table" id="layui_table_id" lay-filter="dataTable">
    </table>

</div>
<script>
layui.use('table', function () {
    var table = layui.table;
    table.render({
        id: "dataTable",
        elem: '#layui_table_id',
        url: '${basePath}/admin/activity/list',
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
            field: 'officialName',
            with: 100,
            title: '公众号',
            align: 'center',
            sort: false
        },
            {
                field: 'name',
                with: 100,
                title: '活动名称',
                align: 'center',
                sort: false
            },
            {
                field: 'description',
                with: 100,
                title: '活动描述',
                align: 'center',
                sort: false
            },
            {
                field: 'createTime',
                with: 100,
                title: '创建时间',
                align: 'center',
                sort: false
            },
            {
                field: 'updateTime',
                with: 100,
                title: '更新时间',
                align: 'center',
                sort: false
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
    });
});
</script>
</@Body>