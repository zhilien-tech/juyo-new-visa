
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/flight" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title>添加</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="flightAddForm">
			<div class="modal-header" style="width:100%;z-index:10000;position:fixed;top:0;left:0;height:62px; background:#FFF;">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn" type="button"
					onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height:100%;margin-top:62px;">
				<div class="tab-content">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>航班号：</label> <input id="flightnum"
									name="flightnum" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>航空公司：</label> <input id="airlinecomp"
									name="airlinecomp" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞机场：</label> <input id="takeOffName"
									name="takeOffName" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞机场三字代码：</label> <input id="takeOffCode"
									name="takeOffCode" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落机场：</label> <input id="landingName"
									name="landingName" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落机场三字代码：</label> <input id="landingCode"
									name="landingCode" type="text" class="form-control input-sm"
									placeholder=" " />
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
								</select>
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落城市：</label> 
								<select id = "landingCityId" name="landingCityId"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select>
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞时间：</label> <input id="takeOffTime"
									name="takeOffTime" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落时间：</label> <input id="landingTime"
									name="landingTime" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>起飞航站楼：</label> <input id="takeOffTerminal"
									name="takeOffTerminal" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>降落航站楼：</label> <input id="landingTerminal"
									name="landingTerminal" type="text"
									class="form-control input-sm" placeholder=" " />
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
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/admin/city/customerNeeds.js"></script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#flightAddForm').bootstrapValidator({
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
								message : '起飞城市不能为空'
							}
						}
					},
									landingCityId : {
						validators : {
							notEmpty : {
								message : '降落城市不能为空'
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
			initCityNeedsSelect2();
		});
		/* 页面初始化加载完毕 */
		
		/*保存页面*/ 
		function save() {
			//初始化验证插件
			$('#flightAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#flightAddForm").data('bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
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
					data : $("#flightAddForm").serialize(),
					url : '${base}/admin/flight/add.html',
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
