<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
    <div class="x-body">
        <form class="layui-form">
            <div class="layui-form-item">
                <label for="appId" class="layui-form-label">
                    公众账号ID
                </label>
                <div class="layui-input-inline" style="display: none">
                    <input type="text" id="id" name="id" value="${payment.id!}" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="appId" name="appId" value="${payment.appId!}" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="appSecret" class="layui-form-label">
                    开发者密码
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="appSecret" name="appSecret" value="${payment.appSecret!}" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="mchId" class="layui-form-label">
                    商户号
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="mchId" name="mchId" value="${payment.mchId!}" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="payKey" class="layui-form-label">
                    支付密钥
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="payKey" name="payKey" value="${payment.payKey!}" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="notifyUrl" class="layui-form-label">
                    回调地址
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="notifyUrl" name="notifyUrl" value="${payment.notifyUrl!}" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                </label>
                <button  class="layui-btn" lay-filter="modify" lay-submit="">
                    确定
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
            form.on('submit(modify)', function(data){
                $.ajax({
                    url: "${basePath}/admin/payment/modify",
                    type:'post',
                    dataType:'json',
                    data:data.field,
                    success: function (data) {
                        if (data.code === 0) {
                            layer.alert("修改成功", {icon: 6},function () {
                                layer.close(layer.index);
                                location.replace(location.href)
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