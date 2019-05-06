<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
    <div class="x-body">
        <form class="layui-form">
            <div class="layui-form-item" style="width: 350px">
                <label class="layui-form-label">请选择活动</label>
                <div class="layui-input-block">
                    <select name="activityId" id="activityId" lay-verify="required" >
                        <option value=""></option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="price" class="layui-form-label">
                    价格
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="price" name="price" onkeyup="checknum(this);"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label for="remark" class="layui-form-label">
                    备注
                </label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入备注内容" id="remark" name="remark" class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                </label>
                <button class="layui-btn" lay-filter="add" lay-submit="">
                    添加
                </button>
            </div>
        </form>
    </div>
    <script>
        var form,layedit,layer,laydate;
        layui.use(['form', 'layedit','layer','laydate'], function () {
            $ = layui.jquery;
            form = layui.form;
            layer = layui.layer;
            layedit = layui.layedit;
            laydate = layui.laydate;

            selectActivityName();
            //监听提交
            form.on('submit(add)', function (data) {

                $.ajax({
                    url: "${basePath}/admin/order/create",
                    type: 'post',
                    dataType: 'json',
                    data: data.field,
                    success: function (data) {
                        if (data.code === 0) {
                            layer.alert("增加成功", {icon: 6}, function () {
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
        })

        function selectActivityName() {
            $.ajax({
                url: "${basePath}/admin/activity/select",
                type: "GET",
                dataType: "json",
                success: function (result) {
                    var list = result.data;
                    var activity = document.getElementById("activityId");
                    for (var i = 0; i < list.length; i++) {
                        var option = document.createElement("option");
                        option.setAttribute("value", list[i].id);
                        option.innerText = list[i].name;
                        activity.appendChild(option);
                        form.render("select");
                    }
                }
            });
        }
        function checknum(obj)
        {
            obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
            obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
            obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
            if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
                obj.value= parseFloat(obj.value);
            }
        }

    </script>

</@Body>