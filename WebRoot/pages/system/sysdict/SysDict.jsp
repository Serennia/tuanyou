<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
<script type="text/javascript" src="<%=basePath %>resource/uilib/js/plugins/ligerGrid.js"></script>
<script type="text/javascript" src="<%=basePath %>pages/system/sysdict/SysDict.js"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<input type="hidden" id="MenuNo"
		value="<%=request.getParameter("MenuNo")%>" />
	<div id="mainsearch" style="width: 98%">
		<div class="searchtitle">
			<span>搜索</span><img src="<%=basePath %>resource/icons/32X32/searchtool.gif" />
			<div class="togglebtn"></div>
		</div>
		<div class="navline" style="margin-bottom: 4px; margin-top: 4px;"></div>
		<div class="searchbox">
			<form id="formsearch" class="l-form"></form>
		</div>
	</div>
	<div id="detail" style="display: none;">
		<form id="mainform" method="post"></form>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>