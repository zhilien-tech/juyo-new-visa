<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/flight" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
<meta charset="UTF-8">
<title>更新</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>

	<div class="modal-content">
		<form id="flightUpdateForm">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.id}">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>航班号：</label> <input id="flightnum"
									name="flightnum" value="${obj.flight.flightnum}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>航空公司：</label> <input id="airlinecomp"
									name="airlinecomp" value="${obj.flight.airlinecomp}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞机场：</label> <input id="takeOffName"
									name="takeOffName" value="${obj.flight.takeOffName}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞机场三字代码：</label> <input id="takeOffCode"
									name="takeOffCode" value="${obj.flight.takeOffCode}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落机场：</label> <input id="landingName"
									name="landingName" value="${obj.flight.landingName}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落机场三字代码：</label> <input id="landingCode"
									name="landingCode" value="${obj.flight.landingCode}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞城市：</label> 
								<select id = "takeOffCityId" name="takeOffCityId" 
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
										<option value="${obj.takeOffCity.id }" selected="selected">${obj.takeOffCity.city}</option>
								</select>
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落城市：</label> 
								<select id = "landingCityId" name="landingCityId" 
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
										<option value="${obj.landingCity.id }" selected="selected">${obj.landingCity.city}</option>
								</select>
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞时间：</label> <input id="takeOffTime"
									name="takeOffTime" value="${obj.flight.takeOffTime}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落时间：</label> <input id="landingTime"
									name="landingTime" value="${obj.flight.landingTime}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞航站楼：</label> <input id="takeOffTerminal"
									name="takeOffTerminal" value="${obj.flight.takeOffTerminal}"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落航站楼：</label> <input id="landingTerminal"
									name="landingTerminal" value="${obj.flight.landingTerminal}"
									type="text" class="form-control input-sm" placeholder=" " />
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
		src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/admin/city/customerNeeds.js"></script>
	<script src="${base}/references/common/js/select2/initSelect2.js"></script>
	<script type="text/javascript">
		var base = "${base}";

		function initvalidate(){
			//校验
			$('#flightUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
											flightnum : {
							validators : {
								notEmpty : {
									message : '航班号不能为空'
								}
							}
						},
											airlinecomp : {
							validators : {
								notEmpty : {
									message : '航空公司不能为空'
								}
							}
						},
											takeOffName : {
							validators : {
								notEmpty : {
									message : '起飞机场不能为空'
								}
							}
						},
											takeOffCode : {
							validators : {
								notEmpty : {
									message : '起飞机场三字代码不能为空'
								}
							}
						},
											landingName : {
							validators : {
								notEmpty : {
									message : '降落机场不能为空'
								}
							}
						},
											landingCode : {
							validators : {
								notEmpty : {
									message : '降落机场三字代码不能为空'
								}
							}
						},
											takeOffCityId : {
							validators : {
								notEmpty : {
									message : '起飞城市id不能为空'
								}
							}
						},
											landingCityId : {
							validators : {
								notEmpty : {
									message : '降落城市id不能为空'
								}
							}
						},
											takeOffTime : {
							validators : {
								notEmpty : {
									message : '起飞时间不能为空'
								}
							}
						},
											landingTime : {
							validators : {
								notEmpty : {
									message : '降落时间不能为空'
								}
							}
						},
											takeOffTerminal : {
							validators : {
								notEmpty : {
									message : '起飞航站楼不能为空'
								}
							}
						},
											landingTerminal : {
							validators : {
								notEmpty : {
									message : '降落航站楼不能为空'
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
	    initCityNeedsSelect2();
		$('#flightUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#flightUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#flightUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
			
				//获取必填项信息
									var flightnum = $("#flightnum").val();
					if(flightnum==""){
						layer.msg('flightnum不能为空');
						return;
					}
									var airlinecomp = $("#airlinecomp").val();
					if(airlinecomp==""){
						layer.msg('airlinecomp不能为空');
						return;
					}
									var takeOffName = $("#takeOffName").val();
					if(takeOffName==""){
						layer.msg('takeOffName不能为空');
						return;
					}
									var takeOffCode = $("#takeOffCode").val();
					if(takeOffCode==""){
						layer.msg('takeOffCode不能为空');
						return;
					}
									var landingName = $("#landingName").val();
					if(landingName==""){
						layer.msg('landingName不能为空');
						return;
					}
									var landingCode = $("#landingCode").val();
					if(landingCode==""){
						layer.msg('landingCode不能为空');
						return;
					}
									var takeOffCityId = $("#takeOffCityId").val();
					if(takeOffCityId==""){
						layer.msg('takeOffCityId不能为空');
						return;
					}
									var landingCityId = $("#landingCityId").val();
					if(landingCityId==""){
						layer.msg('landingCityId不能为空');
						return;
					}
									var takeOffTime = $("#takeOffTime").val();
					if(takeOffTime==""){
						layer.msg('takeOffTime不能为空');
						return;
					}
									var landingTime = $("#landingTime").val();
					if(landingTime==""){
						layer.msg('landingTime不能为空');
						return;
					}
									var takeOffTerminal = $("#takeOffTerminal").val();
					if(takeOffTerminal==""){
						layer.msg('takeOffTerminal不能为空');
						return;
					}
									var landingTerminal = $("#landingTerminal").val();
					if(landingTerminal==""){
						layer.msg('landingTerminal不能为空');
						return;
					}
				
				$.ajax({
					type : 'POST',
					data : $("#flightUpdateForm").serialize(),
					url : '${base}/admin/flight/update.html',
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

