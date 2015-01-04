<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>resource/css/index.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>resource/poshytip/tip-yellow/tip-yellow.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>resource/js/index.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/js/validator.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/js/changepassword.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/js/login.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/poshytip/poshytip.min.js" type="text/javascript"></script>


<script type="text/javascript">
var basepath = '<%=basePath %>';

var globalconfig = {	
		"isAdmin" : '${sysUserInfo.adminFlag}',
		"dataorg" : '${sysUserInfo.dataOrgid}',
		"roleid"  : '${sysUserInfo.roleid}'
};

</script>
<title>管理系统</title>
</head>


<body style="text-align: center; background: #F0F0F0; overflow: hidden;">
	<div id="pageloading" style="display: block;"></div>
	<div id="topmenu" class="l-topmenu">
		<div id="login_bar">
			<strong id="labelwelcome"></strong>
			[<a href="javascript:;" id="usernametip">${sysUserInfo.usercode}</a>]
			<span id="wrapspan">[<a href="javascript:;"><span id="curOrg">${sysUserInfo.orgidname}</span></a>]</span>
			[<a href="javascript:;" id="usernametip">当前角色:${sysUserInfo.roleName }</a>]
			[<a onclick="loginout()" href="javascript:;" title="点击退出系统">退出</a>]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</div>
	<div id="mainbody" class="l-mainbody"
		style="width: 99.2%; margin: 0 auto; margin-top: 3px;">
		<div position="left" title="菜单" id="mainmenu"></div>
		<div position="center" id="framecenter">
			<div tabid="home" title="我的主页">
				<iframe frameborder="0" name="home" id="home" src="<%=basePath%>welcome.jsp"></iframe>
			</div>
		</div>
	</div>
</body>
</html>
