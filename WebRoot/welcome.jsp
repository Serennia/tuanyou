<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script src="<%=basePath%>resource/jquery/jquery-1.5.2.min.js"
	type="text/javascript"></script>
<link href="<%=basePath%>resource/uilib/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=basePath%>resource/uilib/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css" />
<script src="<%=basePath%>resource/jquery/jquery-1.5.2.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>resource/uilib/js/ligerui.min.js"
	type="text/javascript"></script>
<link href="<%=basePath%>resource/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>resource/css/welcome.css" rel="stylesheet"
	type="text/css" />
<script
	src="<%=basePath%>resource/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script
	src="<%=basePath%>resource/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="<%=basePath%>resource/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
<script src="<%=basePath%>resource/jquery.form.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/json2.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/js/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/js/LG.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/js/addfavorite.js" type="text/javascript"></script>

<script type="text/javascript">
	var basepath = '<%=basePath%>';
</script>
<style type="text/css">
	table{
		width: 100%;
		height: 100%;
		margin: 0px;
		padding: 0px;
	}
	table tr{
		border-bottom: 1px black dashed;
	}
	.trid{
		display: none;
	}
</style>
</head>
<body
	style="padding: 10px; overflow: auto; text-align: center; background: #FFFFFF;">
	<div class="navbar">
		<div class="navbar-l"></div>
		<div class="navbar-r"></div>
		<div class="navline"></div>
		<div class="links"> 
        </div>
        <div class="l-clear"></div>
		<div class="button" onclick="LG.addfavorite(loadMyFavorite)">
			<div class="button-l"></div>
			<div class="button-r"></div>
			<div class="button-icon">
				<img src="<%=basePath%>resource/uilib/skins/icons/add.gif" />
			</div>
			<span>增加快捷方式</span>
		</div>
		<div id="Portal" style="width: 100%;overflow:hidden;">
		<div tabid="home" title="我的信息" lselected="true"  style="height:100%" >
			<table id="mymessage">
				<tbody>
				</tbody>
			</table>
			<div id="orgpagination" class="pagination">
            </div>
		</div>
	</div>
	</div>	
	
	<div id="detail" style="display: none;">
		<form id="addForm" method="post">
		</form>
	</div>
	
			
	<script type="text/javascript">
		
		$("div.link").live("mouseover", function() {
			$(this).addClass("linkover");
	
		}).live("mouseout", function() {
			$(this).removeClass("linkover");
	
		}).live('click', function(e) {
			var text = $("a", this).html();
			var url = $(this).attr("url");
			var tabid = $(this).attr("tabid");
			parent.f_addTab(tabid, text, url);
		});
	
		$("div.link .close").live("mouseover", function() {
			$(this).addClass("closeover");
		}).live("mouseout", function() {
			$(this).removeClass("closeover");
		}).live('click', function() {
			var id = $(this).parent().attr("id");
	
			LG.ajax({
				loading : '正在删除用户收藏中...',
				url : basepath+'SystemFavorite/delete.do',
				data : {
					ID : id
				},
				success : function() {
					loadMyFavorite();
				},
				error : function(message) {
					LG.showError(message);
				}
			});
	
			return false;
		});
	
		var links = $(".links");
	
		function loadMyFavorite() {
			LG.ajax({
					loading : '正在加载用户收藏中...',
					url : basepath+'SystemFavorite/load.do',
					success : function(Favorite) {
						links.html("");
						$(Favorite).each(
							function(i, data) {
								var item = $('<div class="link"><img src="" /><a href="javascript:void(0)"></a><div class="close"></div></div>');
								$("img", item).attr(
										"src",
										data.icon);
								$("a", item).html(data.favoritetitle);
								item.attr("id",data.id);
								item.attr("title", data.favoritecontent || data.favoritetitle);
								item.attr("url",basepath+data.url+"?MenuNo="+data.menuno);
								item.attr("tabid",data.menuid);
								links.append(item);
							});
					},
					error : function(message) {
						LG.showError(message);
					}
				});
		}
	
		loadMyFavorite();
		
	</script>
</body>
</html>
