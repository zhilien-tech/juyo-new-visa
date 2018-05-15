
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/customer" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title>添加</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="customerAddForm">
			<div class="modal-header" style="width:100%;position:fixed;top:0;left:0;height:62px;background:#FFF; z-index:1000;">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn"
					type="button" onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height:100%;margin-top:62px;">
				<div class="tab-content">

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>客户来源：</label> <select id="customerType"
									name="source" class="form-control input-sm inpImportant"
									>
									<option>--请选择--</option>
									<c:forEach var="map" items="${obj.customerTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-8">
							<div class="form-group">
								<label><span>*</span>公司全称：</label> 
								<input name="isCustomerAdd" type="hidden" id="isCustomerAdd" value="${obj.isCustomerAdd}"/>
								<input id="name" name="name"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>公司简称：</label> <input id="shortname"
									name="shortname" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-grouxp">
								<label><span>*</span>支付方式：</label> 
								<select id="payType"
									name="payType" class="form-control input-sm inpImportant"
									>
									<option>--请选择--</option>
									<c:forEach var="map" items="${obj.payTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>联系人：</label> <input id="linkman"
									name="linkman" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>
					<div class="row">
						
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>手机：</label> <input id="mobile"
									name="mobile" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label>邮箱：</label> <input id="email" name="email"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>
					<c:forEach items="${obj.mainSaleVisaTypeEnum }" var="map">
						<div class="row visatyperow">
							<div class="col-sm-4">
								<div class="form-group">
									<label><span>*</span>签证类型：</label> <input type="text" class="form-control input-sm"
										placeholder=" " disabled="disabled" value="${map.value }"/>
									<input type="hidden" id="visatype" name="visatype" value="${map.key }">
								</div>
							</div>
	
							<div class="col-sm-4">
								<div class="form-group">
									<label><span>*</span>金额：</label> <input id="amount" name="amount"
										type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
					</c:forEach>

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
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#customerAddForm')
					.bootstrapValidator(
							{
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
												message : '用户不能为空'
											}
										}
									},
									compId : {
										validators : {
											notEmpty : {
												message : '公司不能为空'
											}
										}
									},
									name : {
										validators : {
											notEmpty : {
												message : '公司名称不能为空'
											}
										}
									},
									shortname : {
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
									source : {
										validators : {
											notEmpty : {
												message : '客户来源不能为空'
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
												message : '手机不能为空'
											},
											regexp : {
												regexp : /^[1][34578][0-9]{9}$/,
												message : '手机号格式错误'
											}
										}
									},
									/* email : {
										validators : {
											notEmpty : {
												message : '邮箱不能为空'
											},
											regexp : {
												regexp : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
												message : '邮箱格式错误'
											}
										}
									}, */

								}
							});
		});
		/* 页面初始化加载完毕 */

		/*保存页面*/
		function save() {
			//初始化验证插件
			$('#customerAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#customerAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
				var userId = $("#userId").val();
				if (userId == "") {
					layer.msg('userId不能为空');
					return;
				}
				var compId = $("#compId").val();
				if (compId == "") {
					layer.msg('compId不能为空');
					return;
				}
				var name = $("#name").val();
				if (name == "") {
					layer.msg('name不能为空');
					return;
				}
				var shortname = $("#shortname").val();
				if (shortname == "") {
					layer.msg('shortname不能为空');
					return;
				}
				var source = $("#customerType").val();
				if (source == "") {
					layer.msg('source不能为空');
					return;
				}
				var linkman = $("#linkman").val();
				if (linkman == "") {
					layer.msg('linkman不能为空');
					return;
				}
				var mobile = $("#mobile").val();
				if (mobile == "") {
					layer.msg('mobile不能为空');
					return;
				}
				var email = $("#email").val();
				if (email == "") {
					layer.msg('email不能为空');
					return;
				}
				var formdata = getFormJson('#customerAddForm');
				var visatypes = [];
				$('.visatyperow').each(function(){
					var visatypeinfo = {};
					var visatypechil = $(this).find('#visatype').val();
					visatypeinfo.visatype = visatypechil;
					var amount = $(this).find('#amount').val();
					visatypeinfo.amount = amount;
					visatypes.push(visatypeinfo);
				});
				formdata.visatype = JSON.stringify(visatypes);
				console.log(formdata);
				$.ajax({
					type : 'POST',
					//data : $("#customerAddForm").serialize(),
					data : formdata,
					url : '${base}/admin/simple/customer/add.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("添加成功", "", 3000);
						parent.successAddCustomer(data);
						parent.layer.close(index);
						//parent.datatable.ajax.reload();
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
		
		 //获取form下所有值
		  function getFormJson(form) {
			  var o = {};
			  var a = $(form).serializeArray();
			  $.each(a, function (){
				  if (o[this.name] != undefined) {
				  	if (!o[this.name].push) {
			  	  		o[this.name] = [o[this.name]];
			  		}
			  		o[this.name].push(this.value || '');
			  	  } else {
			  	  	o[this.name] = this.value || '';
			  	  }
			  });
			  return o;
		  }
	</script>


</body>
</html>
