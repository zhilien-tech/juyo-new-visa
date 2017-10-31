<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>签证详情 - 行程安排 编辑</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">编辑</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height: 338px;overflow-y: auto;">
				<div class="tab-content">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>天数：</label> 
								<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>日期：</label> 
								<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>城市：</label> 
								<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>景区：</label> 
								<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>酒店：</label> 
								<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
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
	<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 经营范围校验 -->
	<script src="${base}/admin/company/validateScope.js"></script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			/* $('#companyAddForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {

					name : {
						validators : {
							notEmpty : {
								message : '公司名称不能为空'
							}
						}
					},
					shortName : {
						validators : {
							notEmpty : {
								message : '公司简称不能为空'
							}
						}
					},
					adminLoginName : {
						validators : {
							notEmpty : {
								message : '用户名不能为空'
							}
						}
					},
					linkman : {
						validators : {
							notEmpty : {
								message : '联系人不能为空'
							}
						}
					},
					mobile : {
						validators : {
							notEmpty : {
								message : '电话不能为空'
							},
							regexp: {
		                	 	regexp: /^[1][34578][0-9]{9}$/,
		                        message: '电话号格式错误'
		                    }
						}
					},
					email : {
						validators : {
							notEmpty : {
								message : '邮箱不能为空'
							},
							regexp: {
		                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
		                        message: '邮箱格式错误'
		                    }
						}
					},
					address : {
						validators : {
							notEmpty : {
								message : '地址不能为空'
							}
						}
					},
					comType : {
						validators : {
							notEmpty : {
								message : '公司类型不能为空'
							}
						}
					},
					license : {
						validators : {
							notEmpty : {
								message : '营业执照不能为空'
							}
						}
					},
				}
			}); */
		});
		/* 页面初始化加载完毕 */

		/*保存页面*/
		/* function save() {
			validateScope();
			//初始化验证插件
			$('#companyAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#companyAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
				var name = $("#name").val();
				if (name == "") {
					layer.msg('公司全称不能为空');
					return;
				}
				var shortName = $("#shortName").val();
				if (shortName == "") {
					layer.msg('公司简称不能为空');
					return;
				}
				var adminLoginName = $("#adminLoginName").val();
				if (adminLoginName == "") {
					layer.msg('用户名不能为空');
					return;
				}
				var linkman = $("#linkman").val();
				if (linkman == "") {
					layer.msg('联系人不能为空');
					return;
				}
				var mobile = $("#mobile").val();
				if (mobile == "") {
					layer.msg('电话不能为空');
					return;
				}
				var email = $("#email").val();
				if (email == "") {
					layer.msg('邮箱不能为空');
					return;
				}
				var address = $("#address").val();
				if (address == "") {
					layer.msg('地址不能为空');
					return;
				}
				var comType = $("#comType").val();
				if (comType == "") {
					layer.msg('公司类型不能为空');
					return;
				}
				var license = $("#license").val();
				if (license == "") {
					layer.msg('营业执照不能为空');
					return;
				}
				var scopes = $("#businessScopes").val();
				if (scopes == "") {
					//layer.msg('经营范围不能为空');
					return;
				}

				$.ajax({
					type : 'POST',
					data : $("#companyAddForm").serialize(),
					url : '${base}/admin/company/add.html',
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
		} */

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>


</body>
</html>
