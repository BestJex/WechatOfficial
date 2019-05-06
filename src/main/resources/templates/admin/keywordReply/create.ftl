<#include "../../layout/layout.ftl" />
<@Head title="添加关键词回复"></@Head>
<@Body>
<div class=x-body>
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">公众号</label>
            <div class="layui-input-block" id="officialIds"></div>
        </div>
        <div class="layui-form-item">
            <label for="keyword" class="layui-form-label">关键词</label>
            <div class="layui-input-block">
                <input type="text" id="keyword" name="keyword" required="" lay-verify="required" autocomplete="off" class="layui-input" />
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label for="content" class="layui-form-label">回复内容</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入回复内容" id="content" name="content" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label for="L_repass" class="layui-form-label"></label>
            <button id="btnAdd" class="layui-btn" lay-filter="add" lay-submit="">添加</button>
      </div>
    </form>
</div>
<script>
var selectMRef;
layui.extend({selectM : '${basePath}/statics/layui/extends/selectM'})
    .use(['selectM', 'form', 'layer'], function(){
    selectM = layui.selectM;
    layer = layui.layer
    form = layui.form;
    selectOfficialName();
    function selectOfficialName() {
        $.get('${basePath}/admin/official/select', function(response){
            if(response.code == 0){
                selectMRef = selectM({
                    elem: '#officialIds',
                    data: response.data,
                    max: 0,
                    verify:'required'
                });
            }
        }, 'json');
    }
    form.on('submit(add)', function(data){
        params = {
            officialIds: selectMRef.values,
            keyword: data.field.keyword,
            content: data.field.content
        };
        $.ajax({
            url:'${basePath}/admin/keywordReply/create',
            data: params,
            traditional: true,
            method: 'POST',
            dataType: 'json',
            success: function(response){
                if(response.code == 0){
                    layer.alert("增加成功", {icon: 6},function () {
                        layer.close(layer.index);
                        window.parent.location.reload();
                    });
                }else{
                    layer.msg(response.msg);
                }
            }
        });
        return false;
    });
});
</script>
</@Body>