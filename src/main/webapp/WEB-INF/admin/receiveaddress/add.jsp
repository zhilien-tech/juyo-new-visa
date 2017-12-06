
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/receiveaddress" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<title>添加</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="receiveaddressAddForm">
			<div class="modal-header">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn" type="button"
					onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height:339px;">
				<div class="tab-content">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>收件人：</label> <input id="receiver"
									name="receiver" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>电话：</label> <input id="mobile"
									name="mobile" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>收件地址：</label> <input id="address"
									name="address" type="text" class="form-control input-sm"
									placeholder=" " />
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
	<script
		src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#receiveaddressAddForm').bootstrapValidator({
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
							},
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/receiveaddress/checkMobile.html',
								message: '电话已存在，请重新输入',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										mobile:$('#mobile').val(),
										adminId:""
									};
								}
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
				}
			});
		});
		/* 页面初始化加载完毕 */
		
		/*保存页面*/ 
		function save() {
			//初始化验证插件
			$('#receiveaddressAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#receiveaddressAddForm").data('bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
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
				$.ajax({
					type : 'POST',
					data : $("#receiveaddressAddForm").serialize(),
					url : '${base}/admin/receiveaddress/add.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("添加成功", "", 3000);
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
					}
				});
			}
		}
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>


</body>
</html>
