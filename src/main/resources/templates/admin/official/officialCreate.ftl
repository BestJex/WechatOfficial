<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
    <div class="x-body">
        <form class="layui-form">
            <div class="layui-form-item">
                <label for="account" class="layui-form-label">
                    原始ID
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="account" name="account" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    名字
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="name" name="name" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="appId" class="layui-form-label">
                    应用ID
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="appId" name="appId" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="appSecret" class="layui-form-label">
                    开发者密码
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="appSecret" name="appSecret" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="token" class="layui-form-label">
                    令牌
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="token" name="token" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="aesKey" class="layui-form-label">
                    消息加解密密钥
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="aesKey" name="aesKey" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                </label>
                <button  class="layui-btn" lay-filter="add" lay-submit="">
                    添加
                </button>
            </div>
        </form>
    </div>

    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
            var form = layui.form
                    ,layer = layui.layer;

            //监听提交
            form.on('submit(add)', function(data){
                $.ajax({
                    url: "${basePath}/admin/official/create",
                    type:'post',
                    dataType:'json',
                    data:data.field,
                    success: function (data) {
                        if (data.code === 0) {
                            layer.alert("增加成功", {icon: 6},function () {
                                layer.close(layer.index);
                                window.parent.location.reload();
                            });
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
                return false;
            });
        });
    </script>

</@Body>