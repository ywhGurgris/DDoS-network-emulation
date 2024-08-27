<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>DDoS攻击状态</title>
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
		<label class="theme-switch" for="checkbox"
			style="margin-top: 0.5rem; margin-bottom: 0;"> <input
			type="checkbox" id="checkbox" onclick="attack(this)" /> <span
			class="slider2 round"></span>
		</label>
		<label class="theme-switch" for="checkbox2"
			style="margin-top: 0.5rem; margin-bottom: 0;"> <input
			type="checkbox" id="checkbox2" onclick="protect(this)" /> <span
			class="slider round"></span>
		</label>
		<span>攻击检测响应时间：<b id="delay"></b></span>；<span>攻击检测准确率：<b id="rate"></b></span>；<span>攻击检测误报率：0%</span>
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
								遥感视频
							</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body" style="text-align: center">
							<video
								style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"
								controls>
								<source id="videoSrc" src="${result.videoUrl }" type="video/mp4">
							</video>
						</div>
						<!-- /.card-body -->
					</div>
					<!-- AREA CHART -->
					<div class="card card-primary">
						<div class="card-header">
							<h3 class="card-title">Network</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body">
							<canvas id="networkChart"
								style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->

					<!-- DONUT CHART -->
					<div class="card card-danger">
						<div class="card-header">
							<h3 class="card-title">CPU</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body">
							<div class="chart">
								<canvas id="cpuChart"
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
								攻击路径
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
					<!-- /.card -->

					<!-- LINE CHART -->
					<div class="card card-info">
						<div class="card-header">
							<h3 class="card-title">Backlog</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body">
							<div class="chart">
								<canvas id="backlogChart"
									style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
							</div>
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->

					<!-- BAR CHART -->
					<div class="card card-success">
						<div class="card-header">
							<h3 class="card-title">Memory</h3>

							<div class="card-tools">
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="card-body">
							<div class="chart">
								<canvas id="memoryChart"
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

		var xArr = [];
		var cpuArr = [];
		var memoryArr = [];
		var recvArr = [];
		var sendArr = [];
		var backlogArr = [];
		var maxArrLen = 60;
		var xStart = maxArrLen;
		for (var i = 0; i < maxArrLen; i++) {
			xArr.push(i);
			cpuArr.push(0);
			memoryArr.push(0);
			recvArr.push(0);
			sendArr.push(0);
			backlogArr.push(0);
		}

		var cpuData = {
			labels : xArr,
			datasets : [ {
				borderColor : 'rgb(54, 162, 235)',
				backgroundColor : 'rgba(54, 162, 235, 0.5)',
				data : cpuArr
			} ]
		};

		var memoryData = {
			labels : xArr,
			datasets : [ {
				borderColor : 'rgb(54, 162, 235)',
				backgroundColor : 'rgba(54, 162, 235, 0.5)',
				data : memoryArr
			} ]
		};
		var networkData = {
			labels : xArr,
			datasets : [ {
				label : 'Recv',
				borderColor : 'rgb(54, 162, 235)',
				backgroundColor : 'rgba(54, 162, 235, 0.5)',
				fill : false,
				data : recvArr
			}, {
				label : 'Send',
				borderColor : 'rgb(255, 99, 132)',
				backgroundColor : 'rgba(255, 99, 132, 0.5)',
				fill : false,
				data : sendArr
			} ]
		};
		var backlogData = {
			labels : xArr,
			datasets : [ {
				borderColor : 'rgb(54, 162, 235)',
				backgroundColor : 'rgba(54, 162, 235, 0.5)',
				data : backlogArr
			} ]
		};

		// Reduce the animation steps for demo clarity.
		var cpuChart;
		var memoryChart;
		var networkChart;
		var backlogChart;

		$(document).ready(
				function() {
					/*$('#videoUrl').blur(
							function() {
								$('#videoSrc').attr(
										'src',
										'http://' + $('#videoUrl').val()
												+ '/space.mp4');
								$('#videoSrc').parent()[0].load();
								console.log($('#videoSrc').attr('src'));
							})
					$('#apUrl').blur(function() {
						$('#apSrc').attr('src', $('#apUrl').val());
						console.log($('#apUrl').attr('src'));
					});*/
					cpuChart = new Chart($('#cpuChart')[0].getContext('2d'), {
						type : 'line',
						data : cpuData,
						options : {
							legend : {
								display : false
							},
							scales : {
								yAxes : [ {
									display : true,
									ticks : {
										beginAtZero : true,
										max : 100
									}
								} ]
							}
						}
					});
					memoryChart = new Chart($('#memoryChart')[0]
							.getContext('2d'), {
						type : 'line',
						data : memoryData,
						options : {
							legend : {
								display : false
							}
						}
					});
					networkChart = new Chart($('#networkChart')[0]
							.getContext('2d'), {
						type : 'line',
						data : networkData,
						options : {}
					});
					backlogChart = new Chart($('#backlogChart')[0]
							.getContext('2d'), {
						type : 'line',
						data : backlogData,
						options : {
							legend : {
								display : false
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
			SUBSCRIBE = SUBSCRIBE_PREFIX + "/ddos";
			// 输出订阅地址
			console.log("设置订阅地址为：" + SUBSCRIBE);
			// 执行订阅消息
			stompClient.subscribe(SUBSCRIBE, function(responseBody) {
				
				var data = JSON.parse(responseBody.body);
				if(data.detectedTime != undefined){
					var dt = parseInt(data.detectedTime);
					console.log("detectedTime:" + dt);
					if(dt - detectTime > 3000){
						detectTime = dt;
						detectCount++;
						tj()
					}
					detectTime = dt;
				} else {
					xArr.push(xStart++);
					cpuArr.push(data.cpu);
					memoryArr.push(data.memory);
					recvArr.push(data.recv);
					sendArr.push(data.send);
					backlogArr.push(data.backlog);
					xArr.shift();
					cpuArr.shift();
					memoryArr.shift();
					recvArr.shift();
					sendArr.shift();
					backlogArr.shift();
					cpuChart.update();
					memoryChart.update();
					networkChart.update();
					backlogChart.update();
				}
				
			});
		}

		/* 断开连接 */
		function disconnect() {
			stompClient.disconnect(function() {
				alert("断开连接");
			});
		}

		var attackTime = 0;
		var detectTime = -1;
		var attackCount = 0;
		var detectCount = 0;
		
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
		function attack(obj) {
			if(!$(obj).prop('checked')){
				return;
			}
			attackTime = Date.now()
			attackCount++;
			$.ajax({
				url : "ddosAttackStartAjax",
				type : "POST",
				success : function(data) {
					alert(data.retMsg);
				}
			});
		}
		function tj(){
			ct = Date.now()
			if(detectTime > attackTime){
				$('#delay').text((detectTime - attackTime) + "ms")
			}
			if(attackCount == 0){
				$('#rate').text("0%")
			} else {
				if(detectCount > attackCount)
					detectCount = attackCount
				$('#rate').text((detectCount/attackCount) + "%")
			}
		}
	</script>
</body>
</html>