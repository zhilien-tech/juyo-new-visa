<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/company" />
<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>更新</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/company.css?v='20180510'">
	<style type="text/css">
		img#sqImg {top: 0;}
		#sgImg{top:1%}
		.uploadP { position:relative; cursor:pointer;}
		#uploadFile,#uploadFileSeal { position:absolute !important;top:0 !important;left:0 !important; width:100% !important; cursor:pointer;opacity:0;}
	</style>
</head>
<body>

	<div class="modal-content">
		<form id="companyUpdateForm">
			<div class="modal-header" style="width:100%;z-index:10000;position:fixed;top:0;left:0;height:62px; background:#FFF;">
				<span class="heading">编辑</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="updateBtn" type="button" onclick="save()" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height:100%;margin-top:62px;">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.company.id}">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司全称：</label> 
								<input id="name" name="name" value="${obj.company.name}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司简称：</label> 
								<input id="shortName" name="shortName" value="${obj.company.shortName}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>用户名：</label> 
								<input id="adminId" name="adminId" value="${obj.company.adminId}" type="hidden" />
								<input id="adminLoginName" name="adminLoginName" value="${obj.company.adminloginname}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>联系人：</label> 
								<input id="linkman" name="linkman" value="${obj.company.linkman}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>电话：</label> 
								<input id="mobile" name="mobile" value="${obj.company.mobile}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label>邮箱：</label> 
								<input id="email" name="email" value="${obj.company.email}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>传真：</label> 
								<input id="fax" name="fax" value="${obj.company.fax}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label>地址：</label> 
								<input id="address" name="address" value="${obj.company.address}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司类型：</label>
								<select id="comType" name="comType" onchange="changeComType()" class="form-control input-sm inpImportant">
									<c:forEach items="${obj.companyTypeEnum }" var="map">
										<c:choose>
											<c:when test="${obj.company.comType eq map.key }">
												<option value="${map.key }" selected="selected">${map.value }</option>
											</c:when>
											<c:otherwise>
												<option value="${map.key }">${map.value }</option>
											</c:otherwise>
										</c:choose>
									
									</c:forEach>
									<%-- <option value="1" <c:if test="${'1' eq obj.company.comType}">selected</c:if>>送签社</option>
									<option value="2" <c:if test="${'2' eq obj.company.comType}">selected</c:if>>地接社</option> --%>
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div id="scopeDiv" class="form-group">
								<label><span>*</span>经营范围：</label> 
								<input id="businessScopes" name="businessScopes" value="${obj.company.scopes}" type="hidden" />
								<div class="multiselectBtn scopeDivInputValue form-control input-sm"></div>
								<div class="btnVal">
									<input id="jpScopeDiv" type="button" value="日本" class="btn btn-sm btn-state1" />
									<input id="usScopeDiv" type="button" value="美国" class="btn btn-sm btn-state1" style="display:none;"/>
									<!-- <input type="button" value="美国" class="btn btn-sm btn-state1" /> -->
								</div>
								<small class="help-block" data-bv-validator="choice" data-bv-for="scopeBtn[]" data-bv-result="VALID" style="display: none;">经营范围不能为空</small>
							</div>
						</div>
					</div>
					
					

					<!-- <div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>营业执照：</label> 
								<input id="license" name="license" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div> -->
					<!-- 上传营业执照  -->
					<div class="row" style="margin-top: 15px;">
						<div class="col-xs-3">
							<div class="form-group">
								<div class="upload-btn">
									<input id="license" name="license" value="${obj.company.license}" type="hidden"/>
									<a href="javascript:;" class="uploadP">
										上传营业执照
										<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file" value="上传营业执照" />
									</a>
									
								</div>
							</div>
						</div>
						<div class="col-xs-6">
							<div class="form-group">
								<div class="sqImgPreview">
									<img id="sqImg" alt="营业执照" src="${obj.company.license}">
								</div>
							</div>
						</div>
					</div>
					<!-- end 上传营业执照 -->
					<!-- 上传公章  -->
					<div class="row" style="margin-top: 15px;">
						<div class="col-xs-3">
							<div class="form-group">
								<div class="upload-btn">
									<input id="seal" name="seal" value="${obj.company.seal}" type="hidden"/>
									<a href="javascript:;" class="uploadP">
										上传公章
										<input id="uploadFileSeal" name="uploadFileSeal" class="btn btn-primary btn-sm" type="file" value="上传公章" />
									</a>
									
								</div>
							</div>
						</div>
						<div class="col-xs-6">
							<div class="form-group">
								<div class="sqImgPreview">
									<img id="sqImgSeal" alt="公章" src="${obj.company.seal}">
								</div>
							</div>
						</div>
					</div>
					<!-- end 上传公章-->
				</div>

				<!-- 指定番号 -->
				<div id="jpDesignNum_div" class="row none">
					<p class="info-head">日本</p>
					<div class="col-sm-6">
						<div class="form-group">
							<label><span>*</span>指定番号：</label> 
							<input id="designatedNum" name="designatedNum" value="${obj.company.designatednum}" type="text" style="text-transform:uppercase" class="form-control input-sm" placeholder=" " />
						</div>
					</div>
				</div>
				
				<!-- PDF模板 -->
				<div id="pdf_div" class="row none">
					
				</div>

			</div>
	</div>
	</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var comtype = '${obj.company.comType}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- uploadify -->
	<%-- <script src="${base}/references/public/plugins/uploadify/jquery.uploadify.min.js"></script> --%>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 经营范围校验 -->
	<script src="${base}/admin/company/validateScope.js"></script>
	<!-- 上传图片 -->
	<script src="${base}/admin/company/uploadFile.js"></script>
	<script type="text/javascript">
		$(function(){
			if(comtype == 3 || comtype == 5){
				$("#usScopeDiv").show();
			}else{
				$("#usScopeDiv").hide();
			}
			if(comtype == 4){
				$("#scopeDiv").hide();
			}
		});
		function initvalidate() {
			//校验
			$('#companyUpdateForm').bootstrapValidator({
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
								message : '公司全称不能为空'
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
										adminId:$('#adminId').val()
									};
								}
							}
						}
					},
					shortName : {
						validators : {
							notEmpty : {
								message : '公司简称不能为空'
							}
						},
						stringLength: {
	                   	    min: 1,
	                   	    max: 6,
	                   	    message: '公司简称长度为6'
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
										adminId:$('#adminId').val()
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
							/* regexp: {
		                	 	regexp: /^[1][34578][0-9]{9}$/,
		                        message: '电话号格式错误'
		                    } */
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
					},
					comType : {
						validators : {
							notEmpty : {
								message : '公司类型不能为空'
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
		$('#companyUpdateForm').bootstrapValidator('validate');
		function save() {
			validateScope();
			$('#companyUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#companyUpdateForm").data('bootstrapValidator');
			//大客户不校验经营范围
			if($("#comType").val() == 4){
				removerValid();
			}
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
				/* var mobile = $("#mobile").val();
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
				} */
				var comType = $("#comType").val();
				if (comType == "") {
					layer.msg('公司类型不能为空');
					return;
				}
				/* var license = $("#license").val();
				if (license == "") {
					layer.msg('营业执照不能为空');
					return;
				} */
				var scopes = $("#businessScopes").val();
				if (scopes == "") {
					//layer.msg('经营范围不能为空');
					return;
				}

				$.ajax({
					type : 'POST',
					data : $("#companyUpdateForm").serialize(),
					url : '${base}/admin/company/update.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("编辑成功");
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("编辑失败");
					}
				});
			}
		}

		//返回刷新页面 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		$(function() {
			validateScope();
			var scopesVal = $("#businessScopes").val();
			if (scopesVal != null) {
				var scopesList = scopesVal.split(",");

				for (var i = 0; i < scopesList.length; i++) {
					if (scopesList[i] == "日本") {
						$(".multiselectBtn").append("<span>日本,</span>");
						$(".btnVal input").each(function() {
							var btnVal = $(this).val();//按钮 text
							if ((btnVal + ",") == "日本,") {
								$(this).addClass("btn-state2");//变灰
								$(this).removeClass("btn-state1");//清除蓝色按钮 样式
							}else if((btnVal + ",") == "美国,") {
								$(this).addClass("btn-state2");//变灰
								$(this).removeClass("btn-state1");//清除蓝色按钮 样式
							}
						});
						$("#jpDesignNum_div").removeClass("none");
					} else if (scopesList[i] == "美国") {
						$(".multiselectBtn").append( "<span>美国,</span>");
						$(".btnVal input").each(function(){
							var btnVal = $(this).val();//按钮 text
							if((btnVal+",") == "美国,"){
								$(this).addClass("btn-state2");//变灰
								$(this).removeClass("btn-state1");//清除蓝色按钮 样式
							}
						});
					}

				}

			}

			//---------------------------经营范围 js---------------------------	
			$(".btnVal input").click(
					function() {
						if ($(this).hasClass("btn-state1")) {//蓝色按钮
							$(this).addClass("btn-state2");//变灰
							$(this).removeClass("btn-state1");//清除蓝色按钮 样式
							var btnText = $(this).val();
							//console.log(btnText);
							$(".multiselectBtn").append("<span>" + btnText + ",</span>");
							if(btnText.indexOf("日本")!=-1){
								$("#jpDesignNum_div").removeClass("none");
							}
						} else if ($(this).hasClass("btn-state2")) {//灰色按钮
							$(this).addClass("btn-state1");//变蓝
							$(this).removeClass("btn-state2");//清除灰色按钮 样式
							var btnText = $(this).val();
							$(".multiselectBtn span").each(function() {
								var spanVal = $(this).text();
								if ((btnText + ",") == spanVal) {
									$(this).remove();
								}
								;
							});
							if(btnText.indexOf("日本")!=-1){
								$("#jpDesignNum_div").addClass("none");
								$("#designatedNum").val("");
							}
						}
						var busScopes = "";
						$(".multiselectBtn span").each(function() {
							var spanVal = $(this).text();
							busScopes += spanVal;
						});
						$("#businessScopes").val(busScopes);
						validateScope();
					});
			//-------------------------end 经营范围 js-------------------------
		});
		
		function changeComType(){
			var type = $("#comType").val();
			if(type == ""){
				$("#scopeDiv").hide();
			}else{
				$("#scopeDiv").show();
				if(type == 3 || type == 5){
					$("#usScopeDiv").show();
				}else{
					$("#usScopeDiv").hide();
				}
				
				$(".scopeDivInputValue").empty();
				$(".btnVal input").each(function(){
					$(this).removeClass("btn-state2");//取消灰色
				    $(this).addClass("btn-state1");//变蓝
				});
				$("#businessScopes").val("");
				
				//经营范围（大客户隐藏）
				if(type==4){
					//隐藏并设置为日本（权限使用）
					$("#scopeDiv").hide();
					$(".scopeDivInputValue").html("<span>日本,</span>");
					var busScopes = "";
					$(".multiselectBtn span").each(function() {
						var spanVal = $(this).text();
						busScopes += spanVal;
					});
					$("#businessScopes").val(busScopes);
				}else{
					$("#scopeDiv").show();
					$(".scopeDivInputValue").html("");
					$("#businessScopes").val("");
				}
				
			}
			
		}
	</script>


</body>
</html>

