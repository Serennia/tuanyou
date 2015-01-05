<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath %>resource/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var basepath = '<%=basePath%>';
</script>


