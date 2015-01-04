<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String param = request.getParameter("param");
	if(param==null){
		param = "index";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<link href="<%=basePath %>resource/uilib/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath %>resource/uilib/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath %>resource/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>resource/uilib/js/core/base.js" type="text/javascript"></script>
	<script src="<%=basePath %>resource/uilib/js/plugins/ligerDrag.js" type="text/javascript"></script> 
	<script src="<%=basePath %>resource/uilib/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			var url ='<%=basePath%>'+ '<%=param%>'+'.jsp';
			var manager = $.ligerDialog.waitting('服务已断开,请重新登录...');
			var i = 0;
			setInterval(function (){
				
				if(i>=1){
					manager.close();
					top.location.href = url;
				}
				i++;
			}, 500);
		});
	
	</script>
	
<title>服务已断开</title>
</head>
<body>
</body>
</html>