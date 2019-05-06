<#include "../../layout/layout.ftl"/>
<@Head title="公众号后台管理系统"></@Head>
<@Body>
<div class="x-nav">
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon layui-icon-refresh-1" style="line-height:30px"></i></a>
</div>
<div class="x-body">
    <#--<div class="layui-row">-->
        <#--<form class="layui-form layui-col-md12 x-so">-->
            <#--<input type="text" name="username" placeholder="请输入订单号" autocomplete="off" class="layui-input">-->
            <#--<button class="layui-btn" lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>-->
        <#--</form>-->
    <#--</div>-->
    <xblock>
        <button class="layui-btn" onclick="x_admin_show('添加订单','${basePath}/admin/order/create')"><i
                class="layui-icon layui-icon-add-circle-fine"></i>添加
        </button>
    </xblock>
    <table class="layui-table" id="layui_table_id" lay-filter="dataTable">
    </table>

</div>
<script type="text/html" id="bar">
    <a class="layui-btn layui-btn-xs" lay-event="generate">生成链接</a>
</script>
<script>
    function copyUrl(orderId){
        var baseUrl = '${baseUrl}/order/write/';
        var generateUrl = baseUrl + orderId;
        var oInput = document.createElement('input');
        oInput.value = generateUrl;
        document.body.appendChild(oInput);
        oInput.select(); // 选择对象
        document.execCommand("Copy"); // 执行浏览器复制命令
        oInput.className = 'oInput';
        oInput.style.display='none';
        layer.msg("链接已复制到剪切板");
    }
layui.use('table', function () {  // 引入 table模块
    var table = layui.table;
    table.render({
        id: "dataTable",//
        elem: '#layui_table_id',//指定表格元素
        url: '${basePath}/admin/order/list',  //请求路径
        cellMinWidth: 20,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
        even: true,   //隔行换色
        page: true, //开启分页
        limits: [20, 50, 100],  //每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。
        limit: 20,//每页默认显示的数量
        method: 'get',  //提交方式
        cols: [[
            {
                field: 'id',
                hide : true
            },
            {
                field: 'orderId', //json对应的key
                with: 100,
                title: '订单编号',   //列名
                align: 'center',
                sort: false   // 默认为 false,true为开启排序
            },
            {
                field: 'openId',
                title: '用户编号',
                align: 'center'
            },
            {
                field: 'name',
                title: '收件人',
                align: 'center'
            },
            {
                field: 'phone',
                title: '收件人电话',
                align: 'center'
            },
            {
                field: 'price',
                title: '金额（元）',
                align: 'center'
            },
            {
                field: 'address',
                title: '收货地址',
                align: 'center'
            },
            {
                field: 'activity',
                with: 50,
                title: '活动',
                align: 'center'
            },
            // {
            //     field: 'shippingCode',
            //     title: '快递单号',
            //     align: 'center'
            // },
            {
                field: 'status',
                title: '订单状态',
                align: 'center'
            },
            {
                field: 'payType',
                title: '支付方式',
                align: 'center'
            },
            {
                field: 'tradeNo',
                title: '流水单号',
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
            },
            {
                field: 'paymentTime',
                title: '交易时间',
                align: 'center'
            },
            {
                field: 'remark',
                title: '备注',
                align: 'center'
            },
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
        },
        done: function(){
            $("[data-field='status']").children().each(function(){
                if($(this).text()=='0'){
                    $(this).text("未付款")
                }else if($(this).text()=='1'){
                    $(this).text("已付款")
                }
            });
            $("[data-field='payType']").children().each(function(){
                if($(this).text()=='0'){
                    $(this).text("未知")
                }else if($(this).text()=='1'){
                    $(this).text("微信")
                }else if($(this).text()=='2'){
                    $(this).text("支付宝")
                }
            });
        }
    });
    table.on('tool(dataTable)', function(obj){
        var data = obj.data;
        if(obj.event === 'generate'){
            copyUrl( data.orderId);
        }
    });
});
</script>
</@Body>