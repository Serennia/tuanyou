<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <script src="<%=basePath %>resource/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
    <link href="<%=basePath %>resource/uilib/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath %>resource/uilib/skins/Gray/css/dialog.css" rel="stylesheet" type="text/css" />
   <link href="<%=basePath %>resource/tuanyou/login/login.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath %>resource/uilib/js/core/base.js" type="text/javascript"></script>
    <script src="<%=basePath %>resource/uilib/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="<%=basePath %>resource/js/common.js" type="text/javascript"></script>
	<script src="<%=basePath %>resource/js/LG.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录我的团游</title>
<script type="text/javascript">
var FromUrl = getQueryStringByName("FromUrl");
if (!FromUrl)
{
    FromUrl = encodeURIComponent("index.jsp");
}
$(function ()
{
	$("#txtUsername").focus();
	$("#random").bind("click",function(){
		var timenow = new Date().getTime();
		$(this).attr("src","random.jsp?d="+timenow);
	});
	$("#random").css("cursor","pointer");
	
    $(".login-text").focus(function ()
    {
        $(this).addClass("login-text-focus");
    }).blur(function ()
    {
        $(this).removeClass("login-text-focus");
    });

    $(document).keydown(function (e)
    {
        if (e.keyCode == 13)
        {
            dologin();
        }
    });

    $("#btnLogin").click(function ()
    {
        dologin();
    });
    
    
    function dologin()
    {
   		
    	var url='<%=basePath%>sysLogin/doLogin.do';
        var username = $.trim($("#txtUsername").val());
        var password = $.trim($("#txtPassword").val());
        if (username == "")
        {
            alert('账号不能为空!');
            $("#txtUsername").focus();
            return;
        }
        if (password == "")
        {
            alert('密码不能为空!');
            $("#txtPassword").focus();
            return;
        }
        $.ajax({
            type: 'post', cache: false, dataType: 'json',
            timeout:4000,
            url: url,
            data: {username:username,password:password},
            success: function (result)
            {
         		 if(result=='0'){
            		 alert('用户名或者密码错误!');
                     return;
            	}else if(result=='1'){
            		location.href = decodeURIComponent(FromUrl);
            	}else{
            		alert('发送系统错误,请与系统管理员联系!');
            		return;
            	}
            },
            
            error: function ()
            {                	
          		alert('请求时间超时,请重新登录!');
                window.location.href =  window.location.href; 
            },
            beforeSend: function ()
            {
                $.ligerDialog.waitting("正在登录中,请稍后...");
                $("#btnLogin").attr("disabled", true);
            },
            complete: function ()
            {
                $.ligerDialog.closeWaitting();
                $("#btnLogin").attr("disabled", false);
            }
        });
    }
});


</script>
</head>
<body>
<div class="container">
<div class="lgContainer">
    <div class="slogan"><img src="<%=basePath %>resource/tuanyou/login/slogan.png"></div>
    <p id="synlogin"></p>  
    <div class="inputRibbon mail mt40 js_box_email">          
        <p class="inputText"><input name="txtUsername" id="txtUsername" maxlength="40" placeholder="邮箱/用户名" tabindex="1" type="text"></p>
    </div>
    <span id="js_qylerror_span" style="display:none;" class="hint js_box_email error"></span>
<!--     <a href="javascript:void(0);" data-flag="1" class="signupLink js_box_email js_pup_signupLink" tabindex="4">手机号码登录</a> -->
    <div class="inputRibbon clearfix phone mt40 js_box_mobile" style="display:none;">
        <div style="width: 62px;" id="code" class="qui-select code" data-ui-width="64px" data-ui-menuwidth="300px">
            <strong style="width: 62px;" class="titles"><span class="title-text js_country_code">86</span></strong>
        </div>
        <p class="inputText"><input name="mobile" id="mobile" maxlength="40" placeholder="手机号码" tabindex="1" type="text"></p>
    </div>
    <span id="js_qylerror_span_mobile" style="display:none;" class="hint js_box_mobile error"></span>
    <a href="javascript:void(0);" data-flag="2" style="display:none;" class="signupLink js_box_mobile js_pup_signupLink" tabindex="4">邮箱/用户名登录</a>
    <div class="inputRibbon pwd js_qylerror_pwd">
        <p class="inputText">
            <input id="txtPassword" name="txtPassword" placeholder="密码" tabindex="2" autocomplete="off" type="password">
        </p>
    </div>
    <span id="passwordtips" style="display:none;" class="hint error"></span>
<!--     <a onclick="location.href='getpass.php'" href="javascript:void(0);" tabindex="6" class="forgetPwd fr">忘记密码</a> -->
    <p class="btns mt40">
       <input class="ui_button captchaBtn" id="btnLogin" value="登  录" tabindex="3" type="submit"></p>
    <p class="commentBox clearfix">          
        <span class="loginLink">还没有团游帐号？立即<a  href="#" tabindex="5">注册</a></span> 
        <span class="remember">
            <label for="tagpass">
               <input class="vm" id="tagpass" checked="checked" name="rememberpass" type="checkbox">
               <em class="vm">记住我</em>
            </label>
        </span>
    </p>            
</div>
</div>
</body>
</html>