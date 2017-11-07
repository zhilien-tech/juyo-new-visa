
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/visaJapan/visainput" />

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
		<form id="applicantvisaAddForm">
			<div class="modal-header">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn" type="button"
					onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
					<input type="hidden" id="applicantId" name="applicantId" value="${obj.applicantId }">
			</div>
			<div class="modal-body">
				<div class="tab-content">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签证国家：</label> <input id="visaCountry"
						name="visaCountry" type="text" class="form-control input-sm"
						placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发编号：</label> <input id="visaNum"
						name="visaNum" type="text" class="form-control input-sm"
						placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发地：</label> <input id="visaAddress"
						name="visaAddress" type="text" class="form-control input-sm"
						placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签证类型：</label> 
								<select id="visaType"
										name="visaType" type="text" class="form-control input-sm"
										placeholder="" >
									<c:forEach var="map" items="${obj.visadatatypeenum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发时间：</label> 
								<input id="visaDate" name="visaDate" onfocus="WdatePicker()" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>年限(年)：</label> 
								<input id="visaYears" name="visaYears" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>有效期至：</label> 
								<input id="validDate" name="validDate" onfocus="WdatePicker()" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>停留时间(天)：</label> 
								<input id="stayDays" name="stayDays" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<!-- <div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>上传签证图片URL：</label> <input id="picUrl"
						name="picUrl" type="text" class="form-control input-sm"
						placeholder=" " />
							</div>
						</div>

					</div> -->


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
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#applicantvisaAddForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
				
					/* visaNum : {
						validators : {
							notEmpty : {
								message : '签发编号不能为空'
							}
						}
					},
					visaAddress : {
						validators : {
							notEmpty : {
								message : '签发地不能为空'
							}
						}
					},
					visaType : {
						validators : {
							notEmpty : {
								message : '签证类型不能为空'
							}
						}
					},
						visaYears : {
						validators : {
							notEmpty : {
								message : '年限(年)不能为空'
							}
						}
					},
						visaDate : {
						validators : {
							notEmpty : {
								message : '签发时间不能为空'
							}
						}
					},
						validDate : {
						validators : {
							notEmpty : {
								message : '有效期至不能为空'
							}
						}
					},
						stayDays : {
						validators : {
							notEmpty : {
								message : '停留时间(天)不能为空'
							}
						}
					},
						visaCountry : {
						validators : {
							notEmpty : {
								message : '签证国家不能为空'
							}
						}
					} */
						
				}
			});
		});
		/* 页面初始化加载完毕 */
		
		/*保存页面*/ 
		function save() {
			//初始化验证插件
			$('#applicantvisaAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#applicantvisaAddForm").data('bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				$.ajax({
					type : 'POST',
					data : $("#applicantvisaAddForm").serialize(),
					url : '${base}/admin/visaJapan/visainput/add.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("添加成功", "", 3000);
						parent.successCallBack();
						parent.layer.close(index);
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
