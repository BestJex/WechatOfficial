<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
<link rel="stylesheet" href="${basePath}/statics/css/bootstrap.min.css">
<link rel="stylesheet" href="${basePath}/statics/css/font-awesome.min.css">
<link rel="stylesheet" href="${basePath}/statics/css/wx-custom.css">
<script type="text/javascript" src="${basePath}/statics/js/bootstrap.min.js"></script>
<#--<script type="text/javascript" src="${basePath}/statics/js/Sortable.js"></script>-->
<#--<script type="text/javascript" src="${basePath}/statics/js/menu.js"></script>-->
<div>
    <!-- 自定义菜单 -->
    <div class="custom-menu-edit-con">
        <div class="hbox">
            <div class="inner-left">
                <div class="custom-menu-view-con">
                    <div class="custom-menu-view">
                        <div class="custom-menu-view__title">${official.name}</div>
                        <div class="custom-menu-view__body">
                            <div class="weixin-msg-list"><ul class="msg-con"></ul></div>
                        </div>
                        <div id="menuMain" class="custom-menu-view__footer">
                            <div class="custom-menu-view__footer__left"></div>
                            <div class="custom-menu-view__footer__right" ></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="inner-right">
                <div class="cm-edit-after">
                    <div class="cm-edit-right-header b-b"><span id="cm-tit"></span> <a id="delMenu" class="pull-right" href="javascript:;">删除菜单</a></div>
                    <form class="form-horizontal wrapper-md" name="custom_form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">菜单名称:</label><div class="col-sm-5">
                            <input name="custom_input_title" type="text" class="form-control" ng-model="menuname" value="" placeholder="" ng-maxlength="5"></div><div class="col-sm-5 help-block">
                            <div ng-show="custom_form.custom_input_title.$dirty&&custom_form.custom_input_title.$invalid-maxlength">字数不超过5个汉字或16个字符</div>
                            <div class="font_sml" style="display: none;">若无二级菜单，可输入20个汉字或60个字符</div>
                        </div>
                        </div>
                        <div class="form-group" id="radioGroup">
                            <label class="col-sm-2 control-label">菜单内容:</label>
                            <div class="col-sm-10 LebelRadio">
                                <label class="checkbox-inline"><input type="radio" name="radioBtn" value="textMsg" checked> 文本消息</label>
                                <label class="checkbox-inline"><input type="radio" name="radioBtn" value="link"> 跳转网页</label>
                            </div>
                        </div>
                    </form>
                    <div class="cm-edit-content-con" id="editTextMsg">
                        <div class="cm-edit-page">
                            <div class="row">
                                <label class="col-sm-6 control-label" style="text-align: left;">公众号管理后台关键词回复:
                                </label>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label" style="text-align: left;">关键词:
                                </label>
                                <div class="col-sm-5">
                                    <input type="text" name="keyWord" class="form-control" placeholder="请填写关键词">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cm-edit-content-con" id="editPage">
                        <div class="cm-edit-page">
                            <div class="row">
                                <label class="col-sm-6 control-label" style="text-align: left;">粉丝点击该菜单会跳转到以下链接:
                                </label>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label" style="text-align: left;">页面地址:
                                </label>
                                <div class="col-sm-5">
                                    <input type="text" name="url" class="form-control" placeholder="认证号才可手动输入地址">
                                    <span class="help-block">必填,必须是正确的URL格式</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cm-edit-before"><h5>点击左侧菜单进行操作</h5></div>
            </div>
        </div>
    </div>
    <div class="cm-edit-footer">
        <#--<button id="sortBtn" type="button" class="btn btn-default">菜单排序</button>-->
        <#--<button id="sortBtnc" type="button" class="btn btn-default">完成排序</button>-->
        <button id="saveBtns" type="button" class="btn btn-info1">保存</button>
    </div>
</div>
<div id="reminderModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span>×</span></button>
                <h4 class="modal-title">
                    温馨提示
                </h4>
            </div>
            <div class="modal-body">
                <h5>添加子菜单后，一级菜单的内容将被清除。确定添加子菜单？</h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info reminder">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<div id="abnormalModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span>×</span></button>
                <h4 class="modal-title">
                    温馨提示
                </h4>
            </div>
            <div class="modal-body">
                <h5>数据异常</h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info reminder">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<script>
var data = ${menu};
var officialMenuRef;
layui.extend({officialMenu : '${basePath}/statics/layui/extends/officialMenu'})
    .use(['officialMenu'], function(){
    var officialMenu = layui.officialMenu;
    officialMenuRef = officialMenu();
    officialMenuRef.render(data);
});

$('#saveBtns').on('click',function () {
    saveMenu(officialMenuRef.button);
})
function saveMenu(menu){
    $.ajax({
        type: "POST",
        url: "${basePath}/admin/official/menu/save/${official.appId}",
        contentType: "application/json; charset=utf-8",
        data : JSON.stringify({button:menu}),
        dataType : "json",
        success : function(data) {
            if (data.code === 0) {
                layer.alert("保存成功", {icon: 6},function () {
                    layer.close(layer.index);
                    window.parent.location.reload();
                });
            } else {
                layer.msg(data.msg);
            }
        }
    });
}
</script>
</@Body>