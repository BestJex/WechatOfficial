<#include "../layout/wxLayout.ftl"/>
<@Head title="支付成功">
</@Head>
<@Body>
<div class="wrapper">
    <div class="orderPanel-head">
        <div>
            <img src="${basePath}/statics/wx/image/right.png">
        </div>
        <div>
            <span>支付成功</span>
        </div>
    </div>
    <div class="orderPanel">
        <div class="orderPanel-order">
            <div>
                <div class="orderPanel-item clearfix">
                    <label>订单编号 ：</label>
                    <span>${order.orderId}</span>
                </div>
            </div>
            <div>
                <div class="orderPanel-item  clearfix">
                    <label>交易时间 ：</label>
                    <span>${order.paymentTime}</span>
                </div>
            </div>
        </div>
        <hr style="margin-left: 1em; margin-right:1em;">
        <div class="orderPanel-address">
            <div class="orderPanel-item clearfix">
                <label>收货人 ：</label>
                <span>${order.name}</span>
            </div>
            <div class="orderPanel-item clearfix">
                <label>手机号 ：</label>
                <span>${order.phone}</span>
            </div>
            <div class="orderPanel-item clearfix">
                <label>收货地址 ：</label>
                <span>${order.address}</span>
            </div>
        </div>
    </div>
</div>
</@Body>
