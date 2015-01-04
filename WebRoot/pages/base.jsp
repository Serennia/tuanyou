<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link href="<%=basePath %>resource/uilib/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>resource/uilib/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css" />
<script src="<%=basePath %>resource/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/uilib/js/ligerui.all.js" type="text/javascript"></script>
<link href="<%=basePath %>resource/css/common.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath %>resource/js/common.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/LG.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/ligerui.expand.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/json2.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/iconselector.js" type="text/javascript"></script>
<link href="<%=basePath %>resource/uilib/skins/ligerui-icons.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath %>resource/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery.form.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/validator.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/validator.expand.js" type="text/javascript"></script>

<script src="<%=basePath %>resource/js/ajaxfileupload.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath %>resource/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="<%=basePath %>resource/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="<%=basePath %>resource/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
	var basepath = '<%=basePath%>';
</script>


