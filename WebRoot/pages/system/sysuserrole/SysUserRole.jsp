<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp" %>
<head>

<script type="text/javascript"
	src="<%=basePath%>pages/system/sysuserrole/SysUserRole.js"></script>

<style type="text/css">
.cell-label {
	width: 80px;
}

#tabcontainer .l-tab-links {
	border-top: 1px solid #D0D0D0;
	border-left: 1px solid #D0D0D0;
	border-right: 1px solid #D0D0D0;
}

.projectgrid .l-selected .l-grid-row-cell,.projectgrid .l-selected {
	background: none;
}

.access-icon {
	background: url(<%=basePath%>/resource/uilib/skins/Aqua/images/controls/checkbox.gif)
		0px 0px;
	height: 13px;
	line-height: 13px;
	width: 13px;
	margin: 4px 20px;
	display: block;
	cursor: pointer;
}

.access-icon-selected {
	background-position: 0px -13px;
}

.l-panel td.l-grid-row-cell-editing {
	padding-bottom: 2px;
	padding-top: 2px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工角色分配</title>
</head>
<body style="overflow: hidden;">
	<div id="layout" style="margin: 2px; margin-right: 3px;">
		<div position="center" id="mainmenu" title="用户">
			<div id="usergrid" style="margin: 2px auto;"></div>
		</div>
		<div position="bottom" title="角色">
			<div id="userrolegrid" style="margin: 2px auto;"></div>
		</div>
		<div id="userroleedit" style="display:none;">
		<form id="mainform" method="post"></form>
		</div>
	</div>
</body>
</html>