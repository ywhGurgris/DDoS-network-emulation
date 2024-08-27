<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>接入攻击状态</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Font Awesome -->
<link rel="stylesheet" href="../y/css/all.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="../y/css/adminlte.min.css">
<link rel="stylesheet" href="../y/css/common.css">
<style>
.max-height-300 pre {
	max-height: 300px;
}

.theme-switch {
	display: inline-block;
	height: 24px;
	position: relative;
	width: 50px;
	margin-right: 10px;
}

.theme-switch input {
	display: none;
}

.slider,.slider2 {
	background-color: #ccc;
	bottom: 0;
	cursor: pointer;
	left: 0;
	position: absolute;
	right: 0;
	top: 0;
	transition: 400ms;
}

.slider::before,.slider2::before {
	background-color: #fff;
	bottom: 4px;
	content: "";
	height: 16px;
	left: 4px;
	position: absolute;
	transition: 400ms;
	width: 16px;
}

input:checked+.slider {
	background-color: #66bb6a;
}

input:checked+.slider2 {
	background-color: #bb6666;
}

input:checked+.slider::before,input:checked+.slider2::before {
	transform: translateX(26px);
}

.slider.round,.slider2.round {
	border-radius: 34px;
}

.slider.round::before,.slider2.round::before {
	border-radius: 50%;
}
</style>
</head>
<body class="dark-mode">

	<div class="nav-link">
		<label class="theme-switch" for="checkbox2"
			style="margin-top: 0.5rem; margin-bottom: 0;"> <input
			type="checkbox" id="checkbox2" onclick="protect(this)" /> <span
			class="slider round"></span>
		</label>
	</div>
	<!-- /.container-fluid -->
	<!-- Main content -->
	<section class="content">
		<div class="container-fluid">
			<div id="app" class="card">
				<div class="card-header">
					<h3 class="card-title">主机列表</h3>
					
				</div>
				<!-- /.card-header -->
				<div class="card-body table-responsive p-0">
					<table class="table table-hover text-nowrap">
						<thead>
							<tr>
								<th>IP</th>
								<th>MAC</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="data in datas">
								<td>{{data.ip}}</td>
								<td>{{data.mac}}</td>
								<td>{{data.status}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div id="bl" class="card">
				<div class="card-header">
					<h3 class="card-title">黑名单</h3>
					
				</div>
				<!-- /.card-header -->
				<div class="card-body table-responsive p-0">
					<table class="table table-hover text-nowrap">
						<thead>
							<tr>
								<th>IP</th>
								<th>MAC</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="data in datas">
								<td>{{data.ip}}</td>
								<td>{{data.mac}}</td>
								<td>{{data.status}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</section>
	
	<!-- /.content -->
	<script type="text/javascript" src="../y/js/jquery.min.js"></script>
	<script type="text/javascript" src="../y/js/vue.min.js"></script>
	<!-- Bootstrap 4 -->
	<script src="../y/js/bootstrap.bundle.min.js"></script>
	<!-- overlayScrollbars -->
	<script src="../y/js/jquery.overlayScrollbars.min.js"></script>
	<!-- AdminLTE App -->
	<script src="../y/js/adminlte.js"></script>
	<script type="text/javascript" src="../y/js/common.js"></script>
	<script type="text/javascript" src="../y/js/Chart.min.js"></script>
	<script type="text/javascript" src="../y/js/sockjs.min.js"></script>
	<script type="text/javascript" src="../y/js/stomp.min.js"></script>
	<script type="text/javascript">
		//prettier-ignore

		var vum;
		var vumbl;
		var macmap = {};

		$(document).ready(function() {
			vum = new Vue({
				el : "#app",
				data : {
					datas : []
				},
				methods : {
					update : function(obj) {
						if(obj.event == 'list'){
							var newdata = [];
							for(var i = obj.mac.length - 1; i >= 0; i--){
								if(obj.ip[i] != ""){
									newdata.push({ip:obj.ip[i],mac:obj.mac[i],status:'Alive'});
									delete macmap[obj.mac[i]];
								}
							}
							for(var i in macmap){
								newdata.push(macmap[i]);
							}
							vum.datas = newdata;
						}else{
							var newobj = {ip:"",mac:obj.mac,status:obj.count+'次认证'};
							if(macmap[obj.mac] == undefined){
								macmap[obj.mac] = newobj;
							}else{
								macmap[obj.mac].status = newobj.status;
							}
						}
					}
				}
			});
			
			vumbl = new Vue({
				el : "#bl",
				data : {
					datas : []
				},
				methods : {
					update : function(obj) {
						vumbl.datas.push({ip:"",mac:obj.mac,status:""})
					},
					clear : function(){
						vumbl.datas = [];
					}
				}
			});
			

			connect();

		});

		// 设置 STOMP 客户端
		var stompClient = null;
		// 设置 WebSocket 进入端点
		var SOCKET_ENDPOINT = "../stomp-websocket";
		// 设置订阅消息的请求前缀
		var SUBSCRIBE_PREFIX = "/topic"
		// 设置订阅消息的请求地址
		var SUBSCRIBE = "";

		/* 进行连接 */
		function connect() {
			// 设置 SOCKET
			var socket = new SockJS(SOCKET_ENDPOINT);
			// 配置 STOMP 客户端
			stompClient = Stomp.over(socket);
			// STOMP 客户端连接
			stompClient.connect({}, function(frame) {
				console.log("连接成功");
				subscribeSocket();
			});
		}

		/* 订阅信息 */
		function subscribeSocket() {
			// 设置订阅地址
			SUBSCRIBE = SUBSCRIBE_PREFIX + "/aa";
			// 输出订阅地址
			console.log("设置订阅地址为：" + SUBSCRIBE);
			// 执行订阅消息
			stompClient.subscribe(SUBSCRIBE, function(responseBody) {
				var data = JSON.parse(responseBody.body);
				//alert(data);
				if(data.event == 'bl'){
					vumbl.update(data);
				}else{
					vum.update(data);
				}
			});
		}

		/* 断开连接 */
		function disconnect() {
			stompClient.disconnect(function() {
				alert("断开连接");
			});
		}

		function protect(obj) {
			$.ajax({
				url : "accessAttackProtectAjax",
				type : "POST",
				data : {
					protect : $(obj).prop('checked')
				},
				success : function(data) {
					if(data.retCode != 'ok'){
						alert(data.retMsg);
					}else{
						vumbl.clear();
					}
				}
			});
		}
		function attack(obj) {
			$.ajax({
				url : "accessAttackAjax",
				type : "POST",
				data : {
					start : $(obj).prop('checked')
				},
				success : function(data) {
					if(data.retCode != 'ok'){
						alert(data.retMsg);
					}
				}
			});
		}
	</script>
</body>
</html>