<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
    <div class="x-body">
        <form class="layui-form">
            <div class="layui-form-item" style="width: 350px">
                <label class="layui-form-label">公众号</label>
                <div class="layui-input-block">
                    <select name="officialId" id="officialId" lay-verify="required" >
                        <option value=""></option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="activity" class="layui-form-label">
                    活动名称
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="name" name="name" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
          <div class="layui-form-item layui-form-text">
              <label for="description" class="layui-form-label">
                  活动内容
              </label>
              <div class="layui-input-block">
                  <textarea placeholder="请输入活动内容" id="description" name="description" class="layui-textarea"></textarea>
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
        var form,layedit,layer,laydate;
        layui.use(['form', 'layedit','layer','laydate'], function () {
            $ = layui.jquery;
            form = layui.form;
            layer = layui.layer;
            layedit = layui.layedit;
            laydate = layui.laydate;

          selectOfficialName();
          //监听提交
          form.on('submit(add)', function(data){
              $.ajax({
                  url: "${basePath}/admin/activity/create",
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
        function selectOfficialName() {
            $.ajax({
                url: "${basePath}/admin/official/select",
                type: "GET",
                dataType: "json",
                success: function (result) {
                    var list = result.data;
                    var official = document.getElementById("officialId");
                    for (var i = 0; i < list.length; i++) {
                        var option = document.createElement("option");
                        option.setAttribute("value", list[i].id);
                        option.innerText = list[i].name;
                        official.appendChild(option);
                        form.render("select");
                    }
                }
            });
        }
    </script>

</@Body>