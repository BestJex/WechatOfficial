<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
<div class="x-nav">
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon layui-icon-refresh-1" style="line-height:30px"></i></a>
</div>
<div class="x-body">
    <xblock>
        <div class="layui-form">
            <div class="layui-inline">
                <select id="search_official">
                    <option value="">公众号</option>
                    <#if officials??>
                        <#list officials as official>
                    <option value="${official.id}">${official.name}</option>
                        </#list>
                    </#if>
                </select>
            </div>
            <div class="layui-inline">
                <select id="search_subscribeStatus">
                    <option value="">关注状态</option>
                    <option value="1">已关注</option>
                    <option value="2">未关注</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <input id="search_name" class="layui-input" placeholder="昵称">
            </div>
            <button id="btnSearch" class="layui-btn"><i class="layui-icon layui-icon-search"></i>搜索</button>
        </div>
    </xblock>
    <table class="layui-table" id="layui_table_id" lay-filter="dataTable">
    </table>

</div>

<script>
    var tableUser;
    var officialId;
    var name;
    var status;
    $('#btnSearch').on('click', function(){
        officialId = $('#search_official').val();
        status = $('#search_subscribeStatus').val();
        name = $.trim($('#search_name').val());
        if(officialId == ''){
            officialId = 0;
        }
        if(status == ''){
            status = 0;
        }
        tableUser.reload({
            where: {officialId: officialId, name: name, status: status},
            page: {curr: 1}
        });
    });
    layui.use('table', function () {
        var table = layui.table;
        tableUser = table.render({
            id: "dataTable",
            elem: '#layui_table_id',
            url: '${basePath}/admin/officialUser/list',
            cellMinWidth: 20,
            even: true,
            page: true,
            limits: [20, 50, 100],
            limit: 20,
            method: 'get',
            cols: [[
                {
                    field: 'id',
                    hide: true
                },
                {
                    field: 'officialName',
                    with: 100,
                    title: '公众号',
                    align: 'center',
                    sort: false
                },
                {
                    field: 'headImgUrl',
                    title: '用户头像',
                    align: 'center',
                    templet: '<div><img style="height:100%" src="{{ d.headImgUrl}}"></div>'
                },
                {
                    field: 'nickName',
                    title: '昵称',
                    align: 'center'
                },
                {
                    field: 'sex',
                    title: '性别',
                    align: 'center'
                },
                {
                    field: 'country',
                    title: '所在国家',
                    align: 'center'
                },
                {
                    field: 'province',
                    title: '所在省份',
                    align: 'center'
                },
                {
                    field: 'city',
                    title: '所在城市',
                    align: 'center'
                },
                {
                    field: 'language',
                    title: '用户语言',
                    align: 'center'
                },
                {
                    field: 'subscribeTime',
                    title: '关注时间',
                    align: 'center'
                },
                {
                    field: 'subscribeScene',
                    title: '关注来源',
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '用户状态',
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
                }
            ]],
            parseData: function (res) {
                data = res.data;
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": data.count, //解析数据长度
                    "data": data.items //解析数据列表
                };
            },
            done: function () {
                $("[data-field='sex']").children().each(function () {
                    if ($(this).text() == '0') {
                        $(this).text("未知")
                    } else if ($(this).text() == '1') {
                        $(this).text("男")
                    } else if ($(this).text() == '2') {
                        $(this).text("女")
                    }
                });
                $("[data-field='status']").children().each(function () {
                    if ($(this).text() == '1') {
                        $(this).text("已关注")
                    } else if ($(this).text() == '2') {
                        $(this).text("未关注")
                    }
                });
            }
        });
    });
</script>
</@Body>