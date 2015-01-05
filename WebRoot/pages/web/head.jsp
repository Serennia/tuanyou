<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../basepath.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header" type="cnwidescreenhead">
		<div style="display: none;" id="channel">public</div>
		<div class="commonhead">
			<span id="bulletin"></span>
			<div id="topNavWrapper">
				<div id="topNavPanel">
					<div id="topLogin">
						<span>您好<em>游客</em>，欢迎访问团游！</span> <a class="login" rel="nofollow" href="<%=basePath%>pages/login.jsp">登录</a>
						<a class="register" rel="nofollow" href="#">注册</a>
					</div>
					<ul id="topNav">
						<li class="" id="topMyTc"><a href="#">我的团游<i></i></a>
							<div style="height: 100px;display: none;">
								<a rel="nofollow" href="#">我的订单</a> <i></i> 
								<a rel="nofollow" href="#">我的收藏</a><i></i>  
								<a rel="nofollow"href="#">我的信息</a>
							</div></li>
						<li class="" id="weixinTc"><a href="javascript:void(0);"><em></em>微信<i></i></a>
							<div class="weixindiv">
								<div class="weixindiv1">	
										<p>团队旅游，<em>团游旅游微信</em>统统帮您搞定</p>
										<p>团游旅游微信号:<em>xxxxx</em>或直接扫右侧二维码</p>
								</div>
									<img src="<%=basePath%>resource/tuanyou/index/2weima.png"/>
							</div></li>
						<li id="servicePhone"><a href="javascript:void(0);">客户服务<i></i></a>
							<div style="height: 120px;display: none;">
								<p>国内电话：4007-777-777</p>
								<p>
									<a target="_blank" href="#">帮助中心</a><a target="_blank" href="#">人工申诉</a>
								</p>
							</div>
						</li>
					</ul>
				</div>
			</div>
			
<div class="top">
	<div class="mid">
    	<div class="logo"><p>团游网</p></div>
        <div class="nav">
        	<ul>
            	<li><a href="#">首页</a></li>
                <li><a href="#">酒店</a></li>
                <li><a href="#">景点门票</a></li>
                <li><a href="#">周边游</a></li>
                <li><a href="#">长线游</a></li>
                <li><a href="#">出境游</a></li>
                <li><a href="#">攻略</a></li>
            </ul>
        </div>

    </div>
</div>


		</div>

	</div>
</body>
</html>