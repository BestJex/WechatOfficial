<#include "../layout/layout.ftl"/>
<@Head title="session失效"></@Head>
<@Body>
<div>
    <span>登录已失效，请重新登录！</span>
</div>
<script type="text/javascript">
    setTimeout(function(){
        if(parent.gotoLogin) {
            parent.gotoLogin();
        }else{
            window.location.href = "${basePath}/admin/user/login";
        }
    }, 2000);
</script>
</@Body>