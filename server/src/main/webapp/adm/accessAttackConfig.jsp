<!-- https://bootsnipp.com/snippets/d0A1k -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.HashMap"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>接入攻防配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Font Awesome -->
<link rel="stylesheet" href="../y/css/all.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="../y/css/adminlte.min.css">
<link rel="stylesheet" href="../y/css/common.css">
</head>
<body class="dark-mode">
	<section class="content-header">

		<!-- /.container-fluid -->
	</section>
	<section class="content">
		<div class="container-fluid">
			<div class="card card-primary">
				<div class="card-header">
					<h3 class="card-title">接入点配置</h3>
				</div>
				<form class="form-horizontal" id="configForm">
					<div class="card-body">

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="targetIp">接入点(靶机)IP</label> <input type="text" id="targetIp"
										name="targetIp" class="form-control" value="${result.targetIp }">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="targetPort">接入点(靶机)端口号</label> <input type="text" id="targetPort"
										name="targetPort" class="form-control" value="${result.targetPort }" />
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="threshold">最大攻击次数</label> <input type="text" id="threshold"
										name="threshold" class="form-control" value="${result.threshold }">
								</div>
							</div>
						</div>

					</div>
					<div class="card-footer">
						<button type="reset" class="btn btn-primary">重置</button>
						<button type="submit" id="submitZcxx" class="btn btn-primary">保存</button>
					</div>
				</form>
			</div>
		</div>
	</section>
	<!-- jQuery -->
	<script src="../y/js/jquery.min.js"></script>
	<script src="../y/js/jquery.validate.min.js"></script>
	<script src="../y/js/jquery.validate.ext.js"></script>
	<script src="../y/js/additional-methods.js"></script>
	<script src="../y/js/jquery.form.min.js"></script>
	<script src="../y/js/jquery.placeholder.min.js"></script>
	<script src="../y/js/common.js"></script>
	<!-- Bootstrap 4 -->
	<script src="../y/js/bootstrap.bundle.min.js"></script>
	<!-- overlayScrollbars -->
	<script src="../y/js/jquery.overlayScrollbars.min.js"></script>
	<!-- AdminLTE App -->
	<script src="../y/js/adminlte.js"></script>
	<script>
		$(document).ready(
				function() {
					$("#configForm").validate(
							{
								rules : {
									ip : {
										required : true
									},
									port : {
										required : true
									}
								},
								highlight : function(element, errorClass,
										validClass) {
									$(element).parent().addClass("has-error")
											.removeClass("has-success");
								},
								unhighlight : function(element, errorClass,
										validClass) {
									$(element).parent().addClass("has-success")
											.removeClass("has-error");
								},
								submitHandler : function(form) {
									$(form).ajaxSubmit({
										url : 'accessAttackConfigUpdateAjax',
										type : 'post',
										success : function(data) {
											alert(data.retMsg);
										},
										error : function(obj) {
											alert(obj.status);
										}
									});
								}
							});

				});

		function fillForm(obj) {
			$('#configForm input:text').val(function(index, value) {
				return obj[this.id];
			});
		}
	</script>
</body>
</html>