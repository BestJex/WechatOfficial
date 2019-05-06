<#include "../../layout/layout.ftl"/>
<@Head title="后台管理系统"></@Head>
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
        <button class="layui-btn" onclick="x_admin_show('添加关注回复','${basePath}/admin/subscribeReply/create')"><i
                class="layui-icon layui-icon-add-circle-fine"></i>添加
        </button>
    </xblock>
    <table class="layui-table" id="layui_table_id" lay-filter="dataTable">
    </table>

</div>

<div class="layui-row" id="editFrom" style="display:none;">
    <div class="layui-col-md10">
        <form class="layui-form layui-from-pane" action="" style="margin-top:20px" >
            <div class="layui-form-item layui-form-text" style="padding-right:20px">
                <label for="description" class="layui-form-label">
                    回复内容
                </label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入关注回复内容" style="height: 200px" id="content" name="content" class="layui-textarea"></textarea>
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
<script type="text/html" id="bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
</script>
<script>
    var form,table,layedit,layer,laydate;
    layui.use(['form', 'table','layedit','layer','laydate'], function () {
        $ = layui.jquery;
        form = layui.form;
        table = layui.table;
        layer = layui.layer;
        layedit = layui.layedit;
        laydate = layui.laydate;

        table.render({
            id: "dataTable",
            elem: '#layui_table_id',
            url: '${basePath}/admin/subscribeReply/list',
            cellMinWidth: 20,
            even: true,
            page: true,
            limits: [20, 50, 100],
            limit: 20,
            method: 'get',
            cols: [[
                {field: 'id',hide : true},
                {field: 'officialId',hide : true},
                {field: 'officialName',with: 100,title: '公众号',align: 'center',sort: false},
                {field: 'content',title: '关注回复内容',align: 'center'},
                {field: 'createTime',title: '创建时间',align: 'center'},
                {field: 'updateTime',title: '修改时间',align: 'center'},
                {fixed: 'right',  align: 'center',title:'操作', toolbar: '#bar', width:150}
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
        table.on('tool(dataTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                layer.open({
                    //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    type: 1,
                    title: "修改关注回复",
                    area: ['600px', '600px'],
                    content: $("#editFrom") //引用的弹出层的页面层的方式加载修改界面表单
            });
                //动态向表传递赋值可以参看文章进行修改界面的更新前数据的显示，当然也是异步请求的要数据的修改数据的获取
                setFormValue(data);
            }
        });

        //监听弹出框表单提交 ,massage是修改界面的表单数据
        function setFormValue(data){
            $("#content").val(data.content);
            form.on('submit(edit)', function(massage) {
                $.ajax({
                    url:"${basePath}/admin/subscribeReply/edit",
                    type:'POST',
                    data: {officialId:data.officialId,content:massage.field.content},
                    success:function (res) {
                        if (res.code === 0) {
                            layer.alert("修改成功", {icon: 6},function () {
                                layer.close(layer.index);
                                location.reload();
                            });
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
                return false;
            })
        }
    });

</script>
</@Body>