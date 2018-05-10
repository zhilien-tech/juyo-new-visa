
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/function" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title>添加</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="functionAddForm">
			<div class="modal-header">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn"
					type="button" onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>功能名称：</label> <input id="funName"
									name="funName" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>上级功能：</label> <select id="parentId"
									name="parentId" class="form-control input-sm">
									<option value="">请选择</option>
									<c:forEach items="${obj.functions}" var="pro">
										<option value="${pro.id}">${pro.funName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>功能等级：</label> <input id="level"
									name="level" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>访问地址：</label> <input id="url" name="url"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>序号：</label> <input id="sort" name="sort"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>备注：</label> <input id="remark"
									name="remark" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司类型：</label> <select id="comType"
									name="comType" class="form-control input-sm inpImportant">
									<option value="">请选择</option>
									<c:forEach var="map" items="${obj.companyType}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>经营范围：</label> <select id="bScope"
									name="bScope" class="form-control input-sm selectHeight">
									<option value="">请选择</option>
									<c:forEach var="map" items="${obj.businessScopes}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
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
			$('#functionAddForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					/* parentId : {
						validators : {
							notEmpty : {
								message : '上级功能不能为空'
							}
						}
					}, */
					funName : {
						validators : {
							notEmpty : {
								message : '功能名称不能为空'
							}
						}
					},
					/* url : {
						validators : {
							notEmpty : {
								message : '访问地址不能为空'
							}
						}
					}, */
					level : {
						validators : {
							notEmpty : {
								message : '功能等级不能为空'
							}
						}
					},
					remark : {
						validators : {
							notEmpty : {
								message : '备注不能为空'
							}
						}
					},
					sort : {
						validators : {
							notEmpty : {
								message : '序号不能为空'
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
					bScope : {
						validators : {
							notEmpty : {
								message : '经营范围不能为空'
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
			$('#functionAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#functionAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
				/* var parentId = $("#parentId").val();
				if (parentId == "") {
					layer.msg('parentId不能为空');
					return;
				} */
				var funName = $("#funName").val();
				if (funName == "") {
					layer.msg('功能名称不能为空');
					return;
				}
				/* var url = $("#url").val();
				if (url == "") {
					layer.msg('功能地址不能为空');
					return;
				} */
				var level = $("#level").val();
				if (level == "") {
					layer.msg('功能等级不能为空');
					return;
				}
				var remark = $("#remark").val();
				if (remark == "") {
					layer.msg('备注不能为空');
					return;
				}
				var sort = $("#sort").val();
				if (sort == "") {
					layer.msg('序号不能为空');
					return;
				}

				$.ajax({
					type : 'POST',
					data : $("#functionAddForm").serialize(),
					url : '${base}/admin/function/add.html',
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
