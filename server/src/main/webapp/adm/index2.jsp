<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<title>主界面</title>
<script type="text/javascript" src="../y/js/jquery.min.js"></script>
<link href="../y/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../y/css/dashboard.css" rel="stylesheet" type="text/css" />
<style>
.nav-sidebar>li>ul>li>a {
	color: #428bca;
}

.nav-sidebar>li>ul>li>a:focus {
	color: #428bca;
	font-weight: bold;
}

.secondmenu {
	padding-left: 25px;
}
</style>
<script>
	
</script>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">网络攻击模拟系统</a>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div id="navbar" class="col-sm-3 col-md-2 col-lg-2 sidebar">
				<ul class="nav nav-sidebar" id="myTabs">
					<li class="active"><a href="#a" data-toggle="tab"
						data-src="welcome.jsp"><span class="glyphicon glyphicon-home"></span>
							首页</a></li>
					<li><a href="#a" data-toggle="tab"
						data-src="ddosConfigLoad"><span
							class="glyphicon glyphicon-tree-conifer"></span> DDoS攻击配置</a></li>
					<li><a href="#a" data-toggle="tab"
						data-src="dosNodeMgm.jsp"><span
							class="glyphicon glyphicon-user"></span> DoS节点管理</a></li>
					<li><a href="#a" data-toggle="tab"
						data-src="dosAdState.jsp"><span
							class="glyphicon glyphicon-user"></span> DoS攻防状态</a></li>
					<li><a href="#a" data-toggle="tab"
						data-src="blackholeRouteMgm.jsp"><span
							class="glyphicon glyphicon-user"></span> 黑洞路由管理</a></li>
				</ul>
			</div>
			<div
				class="col-md-12 col-sm-12 col-xs-12 col-lg-10 col-lg-offset-2 col-sm-offset-3 main">
				<div class="tab-pane active" id="a">
					<iframe src="welcome.jsp" frameborder="0" scrolling="auto"
						style="width: 100%; height: 80vh"></iframe>
				</div>
			</div>
		</div>
	</div>

	<script src="../y/js/jquery.min.js" type="text/javascript"></script>
	<script src="../y/js/jquery.form.min.js" type="text/javascript"></script>
	<script src="../y/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../y/js/holder.min.js" type="text/javascript"></script>

	<script>
		$(function() {
			$('a')
					.click(
							function() {
								$("li").removeClass("active");
								$(this).parents("li").addClass("active");
								var src = $(this).attr('data-src');

								if (src === undefined) {//没有data-src的就是一级菜单
									var span = $(this).children("span:last");
									var cls = span.attr("class");
									console.log(cls);
									if (cls == "pull-right glyphicon glyphicon-chevron-down") {
										span
												.attr("class",
														"pull-right glyphicon glyphicon-chevron-right");
									} else if (cls == "pull-right glyphicon glyphicon-chevron-right") {
										span
												.attr("class",
														"pull-right glyphicon glyphicon-chevron-down");
									}
									return;
								}

								var paneId = $(this).attr('href');
								$(paneId + " iframe").attr("src", src);
								if ($(this).attr('data-toggle') === undefined) {
									return false;
								}
							});
		});
	</script>
</body>
</html>
