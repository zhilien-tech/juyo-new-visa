<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/receiveaddress" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
	<meta charset="UTF-8">
	<title>更新</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	
	<div class="modal-content">
		<form id="receiveaddressUpdateForm">
			<div class="modal-header">
			<span class="heading">编辑</span>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消"/>
				<input id="updateBtn" type="button" onclick="save()" class="btn btn-primary pull-right btn-sm btn-right" value="保存"/>
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.id}">
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>用户id：</label>
										<input id="userId" name="userId" value="${obj.userId}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																												<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>公司id：</label>
												<input id="comId" name="comId" value="${obj.comId}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																														</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>收件人：</label>
										<input id="receiver" name="receiver" value="${obj.receiver}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																														<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>电话：</label>
												<input id="mobile" name="mobile" value="${obj.mobile}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																												</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>收件地址：</label>
										<input id="address" name="address" value="${obj.address}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>操作人id：</label>
												<input id="opId" name="opId" value="${obj.opId}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																										</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>创建时间：</label>
										<input id="createTime" name="createTime" value="${obj.createTime}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																		<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>更新时间：</label>
												<input id="updateTime" name="updateTime" value="${obj.updateTime}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																								</div>
											
										
											
										
				</div>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";

		function initvalidate(){
			//校验
			$('#receiveaddressUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
											userId : {
							validators : {
								notEmpty : {
									message : '用户id不能为空'
								}
							}
						},
											comId : {
							validators : {
								notEmpty : {
									message : '公司id不能为空'
								}
							}
						},
											receiver : {
							validators : {
								notEmpty : {
									message : '收件人不能为空'
								}
							}
						},
											mobile : {
							validators : {
								notEmpty : {
									message : '电话不能为空'
								}
							}
						},
											address : {
							validators : {
								notEmpty : {
									message : '收件地址不能为空'
								}
							}
						},
											opId : {
							validators : {
								notEmpty : {
									message : '操作人id不能为空'
								}
							}
						},
											createTime : {
							validators : {
								notEmpty : {
									message : '创建时间不能为空'
								}
							}
						},
											updateTime : {
							validators : {
								notEmpty : {
									message : '更新时间不能为空'
								}
							}
						},
									}
			});
		}
		
		//更新时刷新页面
		function update() {
			window.location.reload();
		}
		
	    initvalidate();
		$('#receiveaddressUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#receiveaddressUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#receiveaddressUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
			
				//获取必填项信息
									var userId = $("#userId").val();
					if(userId==""){
						layer.msg('userId不能为空');
						return;
					}
									var comId = $("#comId").val();
					if(comId==""){
						layer.msg('comId不能为空');
						return;
					}
									var receiver = $("#receiver").val();
					if(receiver==""){
						layer.msg('receiver不能为空');
						return;
					}
									var mobile = $("#mobile").val();
					if(mobile==""){
						layer.msg('mobile不能为空');
						return;
					}
									var address = $("#address").val();
					if(address==""){
						layer.msg('address不能为空');
						return;
					}
									var opId = $("#opId").val();
					if(opId==""){
						layer.msg('opId不能为空');
						return;
					}
									var createTime = $("#createTime").val();
					if(createTime==""){
						layer.msg('createTime不能为空');
						return;
					}
									var updateTime = $("#updateTime").val();
					if(updateTime==""){
						layer.msg('updateTime不能为空');
						return;
					}
								
				
				$.ajax({
					type : 'POST',
					data : $("#receiveaddressUpdateForm").serialize(),
					url : '${base}/admin/receiveaddress/update.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("编辑成功", "", 3000);
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("编辑失败", "", 3000);
					}
				});
			}
		}
	
		//返回刷新页面 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	parent.layer.close(index);
		}
	</script>
	
	
</body>
</html>

