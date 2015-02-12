<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>幻灯片管理</title>
<script type="text/javascript" src="<%=basePath %>pages/houtai/homepage/huandengpian.js"></script>
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
	
	
	   	     <div id="AppendBill_Div" style="display:none;">    <%-- 上传 - 单 --%>  
         <table style="height:100%;width:100%">  
               <tr style="height:40px">  
                   <td style="width:20%">  
                       图标:  
                   </td>  
                   <td><input type="file"  style="width:200px" id="fileupload" name="fileupload"/>  
                   </td>  
               </tr>  
         </table>  
      
    </div> 
</body>
</html>