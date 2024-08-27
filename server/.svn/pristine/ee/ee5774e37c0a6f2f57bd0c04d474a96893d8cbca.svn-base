<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>DDoS攻击节点管理</title>
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
			<div id="app" class="card">
				<div class="card-header">
					<h3 class="card-title">攻击节点列表</h3>
					<div class="card-tools">
						<form id="searchForm">
							<div class="input-group input-group-sm">

								<input type="hidden" name="columns" value="c_name"> <input
									type="hidden" name="columns" value="c_ip"> <input
									type="hidden" name="columns" value="c_status"> <input
									type="hidden" name="operators" value="like"> <input
									type="hidden" name="operators" value="like"> <input
									type="hidden" name="operators" value="like"> <input
									type="hidden" name="orders" value="none"> <input
									type="hidden" name="orders" value="none"> <input
									type="hidden" name="orders" value="none"> <input
									type="hidden" name="logicalopts" value=""> <input
									type="hidden" name="logicalopts" value="and"> <input
									type="hidden" name="logicalopts" value="and"> <input
									type="text" name="values" class="form-control float-right"
									placeholder="节点名称"> <input type="text" name="values"
									class="form-control float-right" placeholder="IP"> <input
									type="text" name="values" class="form-control float-right"
									placeholder="端口号">

								<div class="input-group-append">
									<button type="button" class="btn btn-primary"
										onClick="search(1,'dosNodesListAjax','searchForm', vum);">
										<i class="fas fa-search"></i>
									</button>
									&nbsp;
									<button type="button" class="btn btn-primary" onClick="add();">添加节点</button>
									&nbsp;
									<button type="button" class="btn btn-primary"
										onClick="openImportForm()">
										<i class="fa fa-upload"></i> 导入节点
									</button>

								</div>

							</div>
						</form>
					</div>
				</div>
				<!-- /.card-header -->
				<div class="card-body table-responsive p-0">
					<table class="table table-hover text-nowrap">
						<thead>
							<tr>
								<th>节点名称</th>
								<th>IP</th>
								<th>端口号</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="data in datas">
								<td>{{data.name}}</td>
								<td>{{data.ip}}</td>
								<td>{{data.port}}</td>
								<td>{{data.status}}</td>
								<td><button title="编辑节点信息" class="btn btn-primary"
										@click="load(data)">
										<i class="fa fa-edit"></i>
									</button>
									<button title="删除节点" class="btn btn-primary"
										@click="del(data.id);">
										<i class="fa fa-times"></i>
									</button></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- /.card-body -->
				<div class="card-footer clearfix">
					<span>总数量：{{pages.rowCount}}，总页数：{{pages.lastPage}}，当前页：{{pages.pageNum}}</span>
					<ul class="pagination pagination-sm m-0 float-right">
						<li class="page-item"><a class="page-link" href="#"
							@click="showData(pages.firstPage)"><i
								class="fa fa-angle-double-left"></i></a></li>
						<li class="page-item"><a class="page-link" href="#"
							@click="showData(pages.prePage)"><i class="fa fa-angle-left"></i></a></li>
						<li class="page-item"><a class="page-link" href="#"
							@click="showData(pages.nextPage)"><i
								class="fa fa-angle-right"></i></a></li>
						<li class="page-item"><a class="page-link" href="#"
							@click="showData(pages.lastPage)"><i
								class="fa fa-angle-double-right"></i></a></li>
					</ul>
				</div>
			</div>
		</div>
	</section>

	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 id="addModalLabel" class="modal-title">添加节点</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="addForm" method="post" action="">
					<input type="hidden" name="id" id="id" value="">
					<div class="modal-body">
						<div class="form-group">
							<label for="name">节点名称</label> <input type="text" name="name"
								id="name" class="form-control" placeholder="节点名称">
						</div>
						<div class="form-group">
							<label for="ip">IP</label> <input type="text" name="ip" id="ip"
								class="form-control" placeholder="IP">
						</div>
						<div class="form-group">
							<label for="port">端口号</label> <input type="text" name="port"
								id="port" class="form-control" placeholder="端口号">
						</div>
					</div>
					<div class="modal-footer justify-content-between">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<input type="submit" class="btn btn-primary" value="提交">
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<div class="modal fade" id="importModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">导入节点</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="importForm" method="post" action=""
					enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label for="file">Excel文件</label> <input type="file" name="file"
								id="file" class="form-control">
						</div>
					</div>
					<div class="modal-footer justify-content-between">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<input type="submit" class="btn btn-primary" value="提交">
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<script type="text/javascript" src="../y/js/jquery.min.js"></script>
	<script type="text/javascript" src="../y/js/jquery.form.min.js"></script>
	<script type="text/javascript" src="../y/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../y/js/jquery.placeholder.min.js"></script>
	<script type="text/javascript" src="../y/js/vue.min.js"></script>
	<!-- Bootstrap 4 -->
	<script src="../y/js/bootstrap.bundle.min.js"></script>
	<!-- overlayScrollbars -->
	<script src="../y/js/jquery.overlayScrollbars.min.js"></script>
	<!-- AdminLTE App -->
	<script src="../y/js/adminlte.js"></script>
	<script type="text/javascript" src="../y/js/common.js"></script>
	<script type="text/javascript">
		var vum;
		var page = 1;

		$(document).ready(function() {
			vum = new Vue({
				el : "#app",
				data : {
					datas : [],
					pages : []
				},
				methods : {
					showData : function(p) {
						page = p;
						search(1, 'dosNodesListAjax', 'searchForm', vum);
					}
				}
			});
			vum.showData(page);

			$("#addForm").validate({
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(data) {
							alert(data.retMsg);
							$("#addModal").modal("hide");
							vum.showData(page);
						},
						error : function(data) {
							alert(data.status);
						}
					});
				}
			});

			$("#importForm").validate({
				submitHandler : function(form) {
					var btn = loading($("#importForm input[type='submit']"))
					if (btn.isloading) {
						return;
					}
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(data) {
							alert(data.retMsg);
							$("#importModal").modal("hide");
							vum.showData(page);
							unloading(btn);
						},
						error : function(data) {
							alert(data.status);
							unloading(btn);
						}
					});
				}
			});

		});

		function add() {
			fillForm({});
			$('#addForm').attr("action", "dosNodeAddAjax");
			$('#addModalLabel').text("添加节点");
			$('#addModal').modal("toggle");
		}

		function del(id) {
			if (confirm("确定删除该节点吗？")) {
				$.ajax({
					url : "dosNodeDeleteAjax",
					type : "POST",
					data : {
						id : id
					},
					success : function(data) {
						alert(data.retMsg);
						vum.showData(page);
					}
				});
			}
		}

		function openImportForm() {
			$('#importForm').attr("action", "importAjax");
			$('#importModal').modal("toggle");
		}

		function load(data) {
			$('#addForm').attr("action", "dosNodeUpdateAjax");
			$('#addModalLabel').text("修改节点信息");
			$('#addModal').modal("toggle");
			fillForm(data);
		}

		function fillForm(obj) {
			console.log(obj['id']);
			$('#id').val(obj['id']);
			$('#addForm input:text').val(function(index, value) {
				return obj[this.id];
			});
		}
	</script>
</body>
</html>