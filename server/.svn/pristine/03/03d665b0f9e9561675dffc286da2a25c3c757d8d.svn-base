<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>黑洞路由攻击状态</title>
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
}

.theme-switch input {
	display: none;
}

.slider {
	background-color: #ccc;
	bottom: 0;
	cursor: pointer;
	left: 0;
	position: absolute;
	right: 0;
	top: 0;
	transition: 400ms;
}

.slider::before {
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

input:checked+.slider::before {
	transform: translateX(26px);
}

.slider.round {
	border-radius: 34px;
}

.slider.round::before {
	border-radius: 50%;
}
</style>
</head>
<body class="dark-mode">

	<div class="nav-link">
		<label class="theme-switch" for="checkbox"
			style="margin-top: 0.5rem; margin-bottom: 0;"> <input
			type="checkbox" id="checkbox" onclick="protect(this)" /> <span
			class="slider round"></span>
		</label>
	</div>
	<!-- /.container-fluid -->
	<!-- Main content -->
	<section class="content">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-6">
					<div class="card card-primary">
						<div class="card-header">
							<h3 class="card-title">
								攻击路径: 
							</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body" style="text-align: center">
							<iframe id="apSrc" src="${result.sceneUrl }"
								style="min-height: 250px; height: 250px; max-height: 250px; width: 100%;">
							</iframe>
						</div>
						<!-- /.card-body -->
					</div>
					<!-- AREA CHART -->
					<div class="card card-primary">
						<div class="card-header">
							<h3 class="card-title">
								R1: <span id="r1id"></span>
							</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body">
							<div class="chart">
								<canvas id="r1idCanvas"
									style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
							</div>
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->

				</div>
				<!-- /.col (LEFT) -->
				<div class="col-md-6">
					<!-- LINE CHART -->
					<div class="card card-info">
						<div class="card-header">
							<h3 class="card-title">
								R2: <span id="r2id"></span>
							</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body">
							<div class="chart">
								<canvas id="r2idCanvas"
									style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
							</div>
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->

					<!-- BAR CHART -->
					<div class="card card-success">
						<div class="card-header">
							<h3 class="card-title">
								R3: <span id="r3id"></span>
							</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body">
							<div class="chart">
								<canvas id="r3idCanvas"
									style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
							</div>
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->

				</div>
				<!-- /.col (RIGHT) -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.container-fluid -->
	</section>
	<!-- /.content -->
	<script type="text/javascript" src="../y/js/jquery.min.js"></script>
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

		var maxArrLen = 60;

		function initData() {
			var xArr = [];
			var y1Arr = [];
			var y2Arr = [];
			for (var i = 0; i < maxArrLen; i++) {
				xArr.push(i);
				y1Arr.push(0);
				y2Arr.push(0);
			}
			return {
				idx : maxArrLen,
				x : xArr,
				y1 : y1Arr,
				y2 : y2Arr
			};
		}

		function initDataAndChart(id, data) {
			var cd = {
				labels : data.x,
				datasets : [ {
					label : 'Recv',
					borderColor : 'rgb(54, 162, 235)',
					backgroundColor : 'rgba(54, 162, 235, 0.5)',
					fill : false,
					data : data.y1
				}, {
					label : 'Send',
					borderColor : 'rgb(255, 99, 132)',
					backgroundColor : 'rgba(255, 99, 132, 0.5)',
					fill : false,
					data : data.y2
				} ]
			};
			console.log(id)
			return new Chart($('#' + id + 'Canvas')[0].getContext('2d'), {
				type : 'line',
				data : cd,
				options : {}
			});
		}

		var cidArr = [ [ 'r1id', initData() ], [ 'r2id', initData() ],
				[ 'r3id', initData() ] ]

		// Reduce the animation steps for demo clarity.
		var ridMap = {}
		
		var chartMap = {}

		var ridIdx = 0;

		$(document).ready(function() {
			/*$('#apUrl').blur(function() {
				$('#apSrc').attr('src', $('#apUrl').val());
				console.log($('#apUrl').attr('src'));
			});*/
			for (var i = 0; i < cidArr.length; i++) {
				chartMap[cidArr[i][0]] = initDataAndChart(cidArr[i][0], cidArr[i][1]);
			}

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
			SUBSCRIBE = SUBSCRIBE_PREFIX + "/bhr";
			// 输出订阅地址
			console.log("设置订阅地址为：" + SUBSCRIBE);
			// 执行订阅消息
			stompClient.subscribe(SUBSCRIBE, function(responseBody) {
				var data = JSON.parse(responseBody.body);
				var d = ridMap[data.rid];
				console.log(d);
				if (d == undefined) {
					if (ridIdx > 2)
						return;
					var ca = cidArr[ridIdx++];
					$('#' + ca[0]).text(data.rid);
					d = ca;
					ridMap[data.rid] = d;
				}

				var dx = d[1].x
				var dy1 = d[1].y1
				var dy2 = d[1].y2
				dx.push(d[1].idx++);
				dy1.push(data.recv);
				dy2.push(data.send);
				dx.shift();
				dy1.shift();
				dy2.shift();
				chartMap[d[0]].update();
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
				url : "ddosProtectAjax",
				type : "POST",
				data : {
					protect : $(obj).prop('checked')
				},
				success : function(data) {
					console.log(data.retMsg);
				}
			});
		}
	</script>
</body>
</html>