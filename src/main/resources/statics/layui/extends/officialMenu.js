layui.define(['jquery', 'layer'], function(exports){
    var MOD_NAME = 'officialMenu';
    var $ = layui.jquery;
    var template = '';
    parents = function(type, name){
        this.type = type;
        this.name = name;
        this.sub_button = [];
    }
    subs = function(type, name){
        this.type = type;
        this.name = name;
    }
    var obj = function(config){
        // this.config = config;
        this.button = [];
        this.data = null;
    }
    obj.prototype.render = function(data){
        var button = this.button = data.menu.button;
        var ix = button.length;//一级菜单数量
        var menu='<div class="custom-menu-view__menu"><div class="text-ellipsis"></div></div>';
        var customBtns = $('.custom-menu-view__footer__right');
        if(button.length > 0){
            showMenu();
            $('.cm-edit-after').hide();
        }else{
            addMenu();
            $('.cm-edit-before').siblings().hide();
        }
        var customEl='<div class="custom-menu-view__menu"><div class="text-ellipsis">新建菜单</div></div>';
        var customUl='<ul class="custom-menu-view__menu__sub"><li class="custom-menu-view__menu__sub__add"><div class="text-ellipsis"><i class="glyphicon glyphicon-plus text-info"></i></div></li></ul>';
        var customLi='<li class="custom-menu-view__menu__sub__item"><div class="text-ellipsis">新建子菜单</div></li>';
        // 添加一级菜单事件
        customBtns.on('click', '.custom-menu-view__menu__add', function(){
            ix = button.length + 1;
            if(ix < 4){
                $(this).before(customEl);
                $(this).prev().append(customUl);
                $('.custom-menu-view__footer__right').find('.subbutton__actived').removeClass('subbutton__actived');
                $(this).prev().addClass('subbutton__actived');
                var buttonIndex = $(this).prev().index();
                $('.custom-menu-view__menu').eq(buttonIndex).on('click', (function(buttonIndex){
                    var txt=$('.custom-menu-view__menu').eq(buttonIndex).text();
                    setMenuText(txt);
                })(buttonIndex));
                button.push(new parents('click', '新建菜单'));
            }
            if(ix==3){
                $(this).remove();
            }
            $('.cm-edit-after').show().siblings().hide();
        });
        // 添加二级菜单事件
        var rowIndex;
        customBtns.on('click','li>.text-ellipsis>i',function(){
            rowIndex = $(this).parents('.custom-menu-view__menu').index();
            var liNum = $(this).parents('.custom-menu-view__menu').find('li').length;
            if(liNum <= 1){
                $('#reminderModal').modal('show');
            }else{
                if(liNum < 6){
                    $(this).parent().parent().before(customLi);
                    button[rowIndex].sub_button.push(new subs('click', '新建子菜单'));
                    $('input[name="keyWord"]').val('');
                    setSubButtonActived(rowIndex);
                }
                if(liNum == 5){
                    $(this).parents('li').remove();
                }
            }
            $('#radioGroup').show();
            setSubText()
        });
        // 确定添加子菜单事件
        $('.reminder').on('click', function(){
            var ul = $('.custom-menu-view__menu')[rowIndex].getElementsByTagName('ul')[0];
            var li = $(customLi);
            ul.insertBefore(li[0], ul.childNodes[0]);
            button[rowIndex].sub_button.push(new subs('click', '新建子菜单'));
            $('input[name="keyWord"]').val('');
            setSubButtonActived(rowIndex);
            delete button[rowIndex].type;
            delete button[rowIndex].key;
            delete button[rowIndex].url;
            $('#reminderModal').modal('hide');
            setSubText()
        });
        // 点击一级或二级菜单事件
        customBtns.on('click','.text-ellipsis',function(){
            $('.cm-edit-after').show().siblings().hide();
            if($(this).parent().hasClass('custom-menu-view__menu__sub__item') || $(this).parent().hasClass('custom-menu-view__menu')){
                $(this).parents('.custom-menu-view__footer__right').find('.subbutton__actived').removeClass('subbutton__actived');
                $(this).parent().addClass('subbutton__actived');
            }
            //一级菜单列数
            if($('.msg-context__item').is(':hidden')){
                $('.msg-template').show();
            }else if($('.msg-context__item').is(':visible')){
                $('.msg-template').hide();
            }
            //点击在一级菜单上
            if($(this).parent().hasClass('custom-menu-view__menu')){
                if($('.custom-menu-view__menu').hasClass('subbutton__actived')){
                    var current=$('.subbutton__actived');
                    var buttonIndex=current.index();
                    var lis=current.find('ul>li');
                    setInput(button[buttonIndex].name);
                    updateTit(button[buttonIndex].name);
                    if(lis.length > 1){
                        $('#editTextMsg').hide();
                        $('#editPage').hide();
                        $('#radioGroup').hide();
                    }else{
                        if(button[buttonIndex].type == 'click'){
                            $('input[name="keyWord"]').val(button[buttonIndex].key);
                            radios[0].checked = true;
                            $('#editTextMsg').show();
                            $('#editPage').hide();
                            $('#radioGroup').show();
                        }
                        else if(button[buttonIndex].type=='view'){
                            $('input[name="url"]').val(button[buttonIndex].url);
                            radios[1].checked=true;
                            $('#editTextMsg').hide();
                            $('#editPage').show();
                            $('#radioGroup').show();
                        }
                        else if(!button[buttonIndex].type){
                            radios[0].checked=true;
                            $('#editTextMsg').show();
                            $('#editPage').hide();
                            $('#radioGroup').show();
                        }
                        if(button[buttonIndex].key){
                            $('.msg-context__item').hide();
                            $('.msg-template').show();
                        }else{
                            $('.msg-context__item').show();
                            $('.msg-template').hide();
                        }
                    }
                }

            }
            //点击在二级菜单上
            if($(this).parent().hasClass('custom-menu-view__menu__sub__item')){
                var buttonIndex = $(this).parents('.custom-menu-view__menu').index();
                var subIndex = $(this).parent().index();
                var subText = button[buttonIndex].sub_button[subIndex].name;
                var subUrl = button[buttonIndex].sub_button[subIndex].url;
                var subType = button[buttonIndex].sub_button[subIndex].type;
                var subKey = button[buttonIndex].sub_button[subIndex].key;

                if($('.custom-menu-view__menu__sub__item').hasClass('subbutton__actived')){
                    setInput(subText);
                    updateTit(subText);
                    $('#radioGroup').show();
                    if(subType=='click'){
                        radios[0].checked=true;
                        $('#editTextMsg').show();
                        $('#editPage').hide();
                        $('input[name="keyWord"]').val(subKey);
                    }else if(subType=='view'){
                        radios[1].checked=true;
                        $('#editTextMsg').hide();
                        $('#editPage').show();
                        $('input[name="url"]').val(subUrl);
                    }else if(!subType){
                        radios[0].checked=true;
                        $('#editTextMsg').show();
                        $('#editPage').hide();
                        $('input[name="url"]').val('');
                    }
                    if(subKey){
                        $('.msg-context__item').hide();
                        $('.msg-template').show();
                    }else{
                        $('.msg-context__item').show();
                        $('.msg-template').hide();
                    }
                }
            }
        });
        // 保存右侧菜单名称
        $('input[name="custom_input_title"]').keyup(function(){
            var val=$(this).val();
            var current=$('.subbutton__actived');
            if($('.custom-menu-view__menu__sub__item').hasClass('subbutton__actived')){
                var rowIndex = current.parents('.custom-menu-view__menu').index();
                var colIndex = current.index();
                button[rowIndex].sub_button[colIndex].name=val;
                current.find('.text-ellipsis').text(val);
                updateTit(val);
            }else if($('.custom-menu-view__menu').hasClass('subbutton__actived')){
                var rowIndex = current.index();
                button[rowIndex].name=val;
                current.children('.text-ellipsis').text(val);
                updateTit(val)
            }
        });
        // 保存右侧跳转页面的url
        $('input[name="url"]').keyup(function(){
            var val = $(this).val();
            var current = $('.subbutton__actived');
            if($('.custom-menu-view__menu__sub__item').hasClass('subbutton__actived')){
                var rowIndex = current.parents('.custom-menu-view__menu').index();
                var colIndex = current.index();
                button[rowIndex].sub_button[colIndex].url = val;
                button[rowIndex].sub_button[colIndex].type = 'view';
                if(button[rowIndex].sub_button[colIndex].url == ''){
                    delete button[rowIndex].sub_button[colIndex].url;
                }
            }else if($('.custom-menu-view__menu').hasClass('subbutton__actived')){
                var rowIndex = current.index();
                button[rowIndex].url=val;
                button[rowIndex].type='view';
                if(button[rowIndex].url==''){
                    delete button[rowIndex].url;
                }
            }
        });
        // 保存右侧的关键词
        $('input[name="keyWord"]').keyup(function(){
            var val = $(this).val();
            var current = $('.subbutton__actived');
            if($('.custom-menu-view__menu__sub__item').hasClass('subbutton__actived')){
                var rowIndex = current.parents('.custom-menu-view__menu').index();
                var colIndex = current.index();
                button[rowIndex].sub_button[colIndex].key = val;
                button[rowIndex].sub_button[colIndex].type = 'click';
                if(button[rowIndex].sub_button[colIndex].key == ''){
                    delete button[rowIndex].sub_button[colIndex].key;
                }
            }else if($('.custom-menu-view__menu').hasClass('subbutton__actived')){
                var rowIndex = current.index();
                button[rowIndex].key=val;
                button[rowIndex].type='click';
                if(button[rowIndex].key==''){
                    delete button[rowIndex].key;
                }
            }
        });
        // 菜单内容跳转
        var radios = document.getElementsByName("radioBtn");
        for ( var n = 0; n < radios.length; n++) {
            radios[n].index = n;
            radios[n].onchange = function(){
                if (radios[this.index].checked == true) {
                    if(radios[this.index].value == 'textMsg'){
                        $('#editTextMsg').show();
                        $('input[name="keyWord"]').val('');
                        $('#editPage').hide();
                    }else if(radios[this.index].value == 'link'){
                        $('#editTextMsg').hide();
                        $('input[name="url"]').val('');
                        $('#editPage').show();
                    }
                }
            };
        }
        // 删除菜单按钮
        $('#delMenu').on('click', function(){
            var is_rootMenu = $('.custom-menu-view__menu').hasClass('subbutton__actived');//一级菜单选择项
            var is_subMenu = $('.custom-menu-view__menu__sub__item').hasClass('subbutton__actived');//二级菜单选中项
            if(is_rootMenu){
                var rootMenu = $('.custom-menu-view__menu.subbutton__actived');
                var rootMenuSiblings = rootMenu.siblings();
                var rowIndex = rootMenu.index();
                rootMenu.remove();
                button.splice(rowIndex, 1);
                if(rootMenuSiblings.last().hasClass('custom-menu-view__menu__add') == false){
                    enabledAddMenu(customBtns);
                }
            }else if(is_subMenu){
                var subMenuItem = $('.custom-menu-view__menu__sub__item.subbutton__actived');
                var subMenuItemSiblings = subMenuItem.siblings();
                var rowIndex = subMenuItem.parents('div').index();
                var colIndex = subMenuItem.index();
                subMenuItem.remove();
                button[rowIndex].sub_button.splice(colIndex, 1);
                if(subMenuItemSiblings.last().hasClass('custom-menu-view__menu__sub__add') == false){
                    enabledAddSubMenu($('.custom-menu-view__menu__sub')[rowIndex]);
                }
            }
            //清除右边数据
            $('.cm-edit-before').show().siblings().hide();
            setInput('');
            updateTit('');
            $('input[name="url"]').val('');
            $('.msg-template').children().remove();
            $('.msg-context__item').show();
        });
        // 显示第一级菜单
        function showMenu(){
            if(button.length==1){
                appendMenu(button.length);
                showBtn();
                $('.custom-menu-view__menu').css({
                    width:'50%',
                });
            }
            if(button.length==2){
                appendMenu(button.length);
                showBtn();
                $('.custom-menu-view__menu').css({
                    width:'33.3333%',
                });
            }
            if(button.length==3){
                appendMenu(button.length);
                showBtn();
                $('.custom-menu-view__menu').css({
                    width:'33.3333%',
                });
            }
        }
        // 显示子菜单
        function showBtn(){
            for(var i=0;i < button.length;i++){
                var text = button[i].name;
                var list = document.createElement('ul');
                list.className="custom-menu-view__menu__sub";
                $('.custom-menu-view__menu')[i].childNodes[0].innerHTML=text;
                $('.custom-menu-view__menu')[i].appendChild(list);
                for(var j=0;j<button[i].sub_button.length;j++){
                    var text=button[i].sub_button[j].name;
                    var li=document.createElement("li");
                    var tt=document.createTextNode(text);
                    var div=document.createElement('div');
                    li.className='custom-menu-view__menu__sub__item';
                    // li.id='sub_'+i+'_'+j;//设置二级菜单id
                    div.className="text-ellipsis";
                    div.appendChild(tt);
                    li.appendChild(div);
                    $('.custom-menu-view__menu__sub')[i].appendChild(li);
                }
                var ulBtnL = button[i].sub_button.length;

                if(ulBtnL<5){
                    enabledAddSubMenu($('.custom-menu-view__menu__sub')[i]);
                }
            }
        }
        // 显示添加的菜单
        function appendMenu(num){
            var menuDiv=document.createElement('div');
            var mDiv=document.createElement('div');
            var mi=document.createElement('i');
            mi.className='glyphicon glyphicon-plus text-info';
            mDiv.className='text-ellipsis';
            menuDiv.className='custom-menu-view__menu__add';
            mDiv.appendChild(mi);
            menuDiv.appendChild(mDiv)
            switch(num){
                case 1:
                    customBtns.append(menu);
                    customBtns.append(menuDiv);
                    break;
                case 2:
                    customBtns.append(menu);
                    customBtns.append(menu);
                    customBtns.append(menuDiv);
                    break;
                case 3:
                    customBtns.append(menu);
                    customBtns.append(menu);
                    customBtns.append(menu);
                    break;
            }
        }
        // 初始化菜单按钮
        function addMenu(){
            var menuI = '<div class="custom-menu-view__menu__add"><div class="text-ellipsis"><i class="glyphicon glyphicon-plus text-info"></i></div></div>';
            customBtns.append(menuI);
        }
        // 添加一级菜单按钮
        function enabledAddMenu(menuParentElement){
            var menuDiv=document.createElement('div');
            var mDiv=document.createElement('div');
            var mi=document.createElement('i');
            mi.className='glyphicon glyphicon-plus text-info';
            mDiv.className='text-ellipsis';
            menuDiv.className='custom-menu-view__menu__add';
            mDiv.appendChild(mi);
            menuDiv.appendChild(mDiv)
            menuParentElement.append(menuDiv);
        }
        // 添加二级菜单按钮
        function enabledAddSubMenu(subMenuParentElement){
            var iLi = document.createElement("li");
            var ii = document.createElement('i');
            var iDiv = document.createElement("div");
            ii.className = "glyphicon glyphicon-plus text-info";
            iDiv.className = "text-ellipsis";
            iLi.className='custom-menu-view__menu__sub__add';
            iDiv.appendChild(ii);
            iLi.appendChild(iDiv);
            // $('.custom-menu-view__menu__sub')[i].appendChild(iLi);
            subMenuParentElement.appendChild(iLi);
        }
        var setMenuText = function(value){
            setInput(value);
            updateTit(value);
            radios[0].checked=true;
            $('#editTextMsg').show();
            $('#editPage').hide();
            $('.msg-context__item').show();
            $('.msg-template').hide();
        }
        function setSubText(){
            var actived=$('.custom-menu-view__menu__sub__item').hasClass('subbutton__actived');
            var activedTxt=$('.subbutton__actived').text();
            if(actived){
                setInput(activedTxt);
                updateTit(activedTxt);
                radios[0].checked=true;
                $('#editTextMsg').show();
                $('#editPage').hide();
                $('.msg-context__item').show();
                $('.msg-template').hide();
            }
        }
        //将指定序列的二级菜单
        function setSubButtonActived(rowIndex){
            $('.custom-menu-view__footer__right').find('.subbutton__actived').removeClass('subbutton__actived');
            $('.custom-menu-view__menu').eq(rowIndex).find('i').parent().parent().prev().addClass('subbutton__actived');
        }
        //设置右边input的value
        function setInput(val){
            $('input[name="custom_input_title"]').val(val);
        }
        function updateTit(text){
            $('#cm-tit').html(text);
        }
    }
    exports(MOD_NAME, function(config){
        var _this = new obj(config);

        return _this;
    });
});