<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/company" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>公司信息-添加</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/company.css">
	<style type="text/css">
		img#sqImg {top: 0;}
		#sgImg{top:1%}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">添加送签社</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司全称：</label> 
								<input id="fullName" name="fullName" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司简称：</label> 
								<input id="shortName" name="shortName" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>指定番号：</label> 
								<input id="designatedNum" name="designatedNum" type="text" class="form-control input-sm" placeholder="必须大写字母" />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>联系人：</label> 
								<input id="linkman" name="linkman" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>电话：</label> 
								<input id="mobile" name="mobile" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label>邮箱：</label> <input id="email" name="email" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label>地址：</label> 
								<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
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
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript">
		$(function() {
			//校验
			$('#companyAddForm').bootstrapValidator({
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
							},
							remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/company/checkCompanyNameExist.html',//验证地址
								message: '公司全称已存在，请重新输入',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										companyName:$('#name').val(),
										adminId:""
									};
								}
							}
						}
					},
					shortName : {
						validators : {
							notEmpty : {
								message : '公司简称不能为空'
							},
							stringLength: {
		                   	    min: 1,
		                   	    max: 6,
		                   	    message: '公司简称长度为6'
		                   	}
						}
					},
					adminLoginName : {
						validators : {
							notEmpty : {
								message : '用户名不能为空'
							},
							regexp: {
		                	 	regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '用户名不能为汉字'
		                    },
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/company/checkLoginNameExist.html',//验证地址
								message: '用户名已存在，请重新输入',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										loginName:$('#adminLoginName').val(),
										adminId:""
									};
								}
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
							/* notEmpty : {
								message : '电话不能为空'
							}, */
							regexp: {
		                	 	regexp: /^[1][34578][0-9]{9}$/,
		                        message: '电话号格式错误'
		                    }
						}
					},
					email : {
						validators : {
							/* notEmpty : {
								message : '邮箱不能为空'
							}, */
							regexp: {
		                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
		                        message: '邮箱格式错误'
		                    }
						}
					},
					address : {
						validators : {
							/* notEmpty : {
								message : '地址不能为空'
							} */
						}
					}
				}
			});

		});
		/* 页面初始化加载完毕 */

		/*保存页面*/
		function save() {
			validateScope();
			//初始化验证插件
			$('#companyAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#companyAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				
				$.ajax({
					type : 'POST',
					data : $("#companyAddForm").serialize(),
					url : '${base}/admin/company/add.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("添加成功");
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("添加失败");
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
