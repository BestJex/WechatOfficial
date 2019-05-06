<#include "../../layout/layout.ftl"/>
<@Head title="关键字回复"></@Head>
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
            <div class="layui-input-inline">
                <input id="search_keyword" class="layui-input" placeholder="关键词">
            </div>
            <button id="btnSearch" class="layui-btn layui-btn-normal"><i class="layui-icon layui-icon-search"></i>搜索</button>
            <button id="btnCreate" class="layui-btn"><i class="layui-icon layui-icon-add-circle-fine"></i>添加</button>
        </div>
    </xblock>
    <table class="layui-table" id="layui_table_id" lay-filter="tbKeywordReply"></table>
</div>
<script type="text/html" id="optBar">
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<div id="editFrom" style="display:none;">
    <div style="margin-top:20px;margin-right:15px;">
        <form class="layui-form layui-from-pane" action="" >
            <input id="id" name="id" type="hidden" >
            <div class="layui-form-item layui-form-text">
                <label for="official" class="layui-form-label">公众号</label>
                <div class="layui-input-block">
                    <input type="text" id="official" lay-verify="required" autocomplete="off" class="layui-input" disabled />
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label for="content" class="layui-form-label">关键字</label>
                <div class="layui-input-block">
                    <input type="text" id="keyword" name="keyword" required="" lay-verify="required" autocomplete="off" class="layui-input" />
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label for="content" class="layui-form-label">回复内容</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入回复内容" style="height: 200px" id="content" name="content" class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item" style="margin-top:40px">
                <div class="layui-input-block">
                    <button class="layui-btn  layui-btn-submit " lay-submit="" lay-filter="edit">确认修改</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
var tableRef;
var officialId;
var keyword;
$('#btnCreate').on('click', function(){
    x_admin_show('添加','${basePath}/admin/keywordReply/create');
});
$('#btnSearch').on('click', function(){
    officialId = $('#search_official').val();
    keyword = $.trim($('#search_keyword').val());
    if(officialId == ''){
        officialId = 0;
    }
    tableRef.reload({
        where: {officialId: officialId, keyword: keyword},
        page: {curr: 1}
    });
});
layui.use(['table','layer','form'], function(){
    var table = layui.table;
    layer = layui.layer;
    form = layui.form;
    layedit = layui.layedit;
    var editIndex;
    tableRef = table.render({
        id: 'tbKeywordReply',
        elem: '#layui_table_id',
        url: '${basePath}/admin/keywordReply/list',
        cellMinWidth: 20,
        even: true,
        page: true,
        limits: [20, 50],
        limit: 20,
        method: 'get',
        cols: [[
            {field:'id', width:40, align:'center',title:'ID'},
            {field:'officialName', width:150, align: 'center',title:'公众号'},
            {field:'keyword',width:150, align: 'center', title:'关键字'},
            {field:'content',title:'回复内容'},
            {field:'createTime',width:150, align: 'center',title:'创建时间'},
            {fixed:'right',toolbar:'#optBar',width:150,align: 'center',title:'操作'}
        ]],
        parseData: function(res){
            data = res.data;
            return {
                'code': res.code,
                'msg': res.msg,
                'count': data.count,
                'data': data.items
            }
        }
    });
    table.on('tool(tbKeywordReply)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            if(confirm('确认删除')){
                $.post('${basePath}/admin/keywordReply/delete', {id: data.id}, function(response){
                    if(response.code == 0){
                        layer.alert("删除成功", {icon: 6},function () {
                            layer.close(layer.index);
                            tableRef.reload();
                        });
                    }else{
                        layer.msg(response.msg);
                    }
                },'json');
            }
        }else if(obj.event === 'edit'){
            $('#id').val(data.id);
            $('#official').val(data.officialName);
            $('#keyword').val(data.keyword);
            $('#content').val(data.content);
            var editIndex = layer.open({
                type: 1,
                title: "修改回复",
                area: ['600px', '600px'],
                content: $("#editFrom") //引用的弹出层的页面层的方式加载修改界面表单
            });
        }
    });
    form.on('submit(edit)', function(data) {
        $.ajax({
            url:"${basePath}/admin/keywordReply/edit",
            type:'POST',
            data: {id:data.field.id, keyword:data.field.keyword, content:data.field.content},
            success:function (res) {
                if (res.code === 0) {
                    var index = layer.alert("修改成功", {icon: 6},function () {
                        layer.closeAll();
                        tableRef.reload();
                    });
                } else {
                    layer.msg(data.msg);
                }
            }
        });
        return false;
    })
});
</script>
</@Body>