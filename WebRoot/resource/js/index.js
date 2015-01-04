


//几个布局的对象
		 
        var layout, tab, accordion;
        //tabid计数器，保证tabid不会重复
        var tabidcounter = 0;
        //窗口改变时的处理函数
        function f_heightChanged(options) {
            if (tab)
                tab.addHeight(options.diff);
            if (accordion && options.middleHeight - 24 > 0)
                accordion.setHeight(options.middleHeight - 24);
        }
        //增加tab项的函数
        function f_addTab(tabid, text, url) {
            if (!tab) return;
            if (!tabid)
            {
                tabidcounter++;
                tabid = "tabid" + tabidcounter; 
            }
            tab.addTabItem({ tabid: tabid, text: text, url: url });
        }
        //登录
        function f_login()
        {
            LG.login();
        }
        //修改密码
        function f_changepassword()
        {
            LG.changepassword();
        }
        
        function f_changepassword2()
        {
            LG.changepassword2();
        }
        
        $(document).ready(function ()
        {
        	if(globalconfig.roleid=="5" || globalconfig.roleid=="6"){
        		$("#updatePwd").css("display","none");
        	}
        	
            //菜单初始化
            $("ul.menulist li").live('click', function ()
            {
                var jitem = $(this);
                var tabid = jitem.attr("tabid");
                var url = jitem.attr("url");
                if (!url) return;
                if (!tabid)
                {
                    tabidcounter++;
                    tabid = "tabid" + tabidcounter;
                    jitem.attr("tabid", tabid);

                    //给url附加menuno
                    if (url.indexOf('?') > -1) url += "&";
                    else url += "?";
                    
                    url += "MenuNo=" + jitem.attr("menuno");
                    jitem.attr("url", url);
                }
                f_addTab(tabid, $("span:first", jitem).html(), url);
            }).live('mouseover', function ()
            {
                var jitem = $(this);
                jitem.addClass("over");
            }).live('mouseout', function ()
            {
                var jitem = $(this);
                jitem.removeClass("over");
            });

            //布局初始化 
            //layout
            layout = $("#mainbody").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-10,space:4, onHeightChanged: f_heightChanged });
            var bodyHeight = $(".l-layout-center:first").height();
            //Tab
            tab = $("#framecenter").ligerTab({ height: bodyHeight, contextmenu: true });
            LG.prevDialogImage();
            var mainmenu = $("#mainmenu");
           // alert(basepath+'systemMenu/getMenus.do');
            $.getJSON(basepath+'systemMenu/getMenus.do?Math.random', function (menus)
            {
                $(menus).each(function (i, menu)
                {
                    var item = $('<div title="' + menu.menuname + '"><ul class="menulist"></ul></div>');

                    $(menu.children).each(function (j, submenu)
                    {
                        var subitem = $('<li><img/><span></span><div class="menuitem-l"></div><div class="menuitem-r"></div></li>');
                        subitem.attr({
                            url:basepath+submenu.menuurl+"?MenuNo="+submenu.menuno,
                            menuno: submenu.menuno,
                            tabid:submenu.menuid
                        });
                        $("img", subitem).attr("src", basepath+submenu.icon || basepath+submenu.icon);
                        $("span", subitem).html(submenu.menuname || submenu.text);
                        $("ul:first", item).append(subitem);
                    });
                    mainmenu.append(item);
                });
                accordion = $("#mainmenu").ligerAccordion({ height: bodyHeight - 24, speed: null });
            });
            
           
//            if(parseInt(globalconfig.roleid)>2){
//            	
//            	$.getJSON(basepath+'SystemWeb/getCurOrgname.do' ,function(resultdata){
//            		if(resultdata['orgname']){
//            			$("#curOrg").html(resultdata['orgname']).css("display","inline");
//            		}
//            	});
//            }else{
//            	$("#wrapspan").css("display","none");
//            }
//           
            
            //时间
            var now = new Date(), hour = now.getHours();
            if (hour > 4 && hour < 6) { $("#labelwelcome").html("凌晨好！"); }
            else if (hour < 9) { $("#labelwelcome").html("早上好！") ;}
            else if (hour < 12) { $("#labelwelcome").html("上午好！"); }
            else if (hour < 14) { $("#labelwelcome").html("中午好！") ;}
            else if (hour < 17) { $("#labelwelcome").html("下午好！");}
            else if (hour < 19) { $("#labelwelcome").html("傍晚好！"); }
            else if (hour < 22) { $("#labelwelcome").html("晚上好！"); }
            else { $("#labelwelcome").html("夜深了，注意休息！"); }
            
           // setTimeout('getCurrentUser()',2000);
          
            $("#pageloading").hide();
        });
        

 
 function loginout(){
	 $.ligerDialog.confirm("您确认要退出系统吗？",function(yes){
		 if(yes){
			 LG.ajax({
				 url : basepath+""+"SystemWeb/doLoginOut.do",
				 data: {},
				 success : function(data,message){
					 window.location.href= window.location.href;
//					 window.location.href=basepath+'index.html';
				 }
				 
			 });
		 }else{
			 return;
		 }
	 });
 }
 

 