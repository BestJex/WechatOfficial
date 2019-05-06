<#include "../layout/wxLayout.ftl"/>
<@Head title="填写订单">
</@Head>
<@Body>
<div class="wrapper">
    <div class="addressPanel">
        <form method="post" action="${basePath}/pay/wechat/${orderId}" onsubmit="return sub();">
            <div>
                <div class="payTitle">需支付费用</div>
                <hr style="margin-left: 1em; margin-right:1em;">
                <div class="payMoney">￥${price}元</div>
            </div>
            <div class="address">
                <div class="group clearfix">
                    <label>收货人</label>
                    <div><input type="text" id="name" name="name" value=""></div>
                </div>
                <div class="group clearfix">
                    <label>手机号</label>
                    <div  style="display: inline-block;">
                        <input type="text" id="phone" name="phone" value="">
                    </div>
                </div>
                <div class="group clearfix">
                    <label>收货地址</label>
                    <div style="display: inline-block;">
                        <input type="text" id="address" name="address" value="">
                    </div>
                </div>
            </div>
            <div>
                <button type="submit" class="btnPay"></button>
            </div>
        </form>
    </div>
</div>
</body>
<script>
    function verifyPhone(phone){
        var reg=/^[1][3,4,5,7,8][0-9]{9}$/;
        return reg.test(phone);
    }

    function sub() {
        if(!document.getElementById('name').value){
            alert('请输入姓名');
            return false;
        }
        if(!verifyPhone(document.getElementById('phone').value)){
            alert('请输入正确的手机号');
            return false;
        }
        if(document.getElementById('address').value.length < 10){
            alert('请输入正确的收货地址');
            return false;
        }
        return true;
    }
<#if payMap??>
    //调用微信JS api 支付
    function jsApiCall()
    {
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest',
                {
                    "appId": '${payMap["appId"]}',
                    "timeStamp":  '${payMap["timeStamp"]}',
                    "nonceStr": '${payMap["nonceStr"]}',
                    "package":  '${payMap["package"]}',
                    "signType":  '${payMap["signType"]}',
                    "paySign":  '${payMap["paySign"]}'
                },
                function(res){
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        window.location.href = '${basePath}/order/write/${orderId}'
                    }
                }
        );
    }

    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', jsApiCall);
            document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
        }
    }else{
        jsApiCall();
    }
</#if>
</script>
</@Body>