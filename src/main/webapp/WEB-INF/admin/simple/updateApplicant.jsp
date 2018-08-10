<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>基本信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v=<%=System.currentTimeMillis() %>">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
	<!-- 护照信息css -->
	<link rel="stylesheet" href="${base}/references/common/css/simplePassportInfo.css?v=<%=System.currentTimeMillis() %>">
	<!-- 基本信息css -->
	<link rel="stylesheet" href="${base}/references/common/css/liteUpdateApplicant.css?v=<%=System.currentTimeMillis() %>">
	<style>
		#juzhudi-checkbox{
			position: absolute;
			top: 1px;
			right: -10px;
		}
		.dropdown-menu {
			top: 284px !important;
		}
	</style>
</head>
<body>
	<div class="modal-content">
		<!-- 左右箭头  -->
		<a id="toPassport" class="rightNav" onclick="toVisaInfo();"><span></span></a>
		<a id="toApply" class="leftNav" onclick="toPassport();"><span></span></a>
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">基本信息</span>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" />
				<input id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" onclick="saveApplicant(1)"/>
			</div>
			<div class="modal-body c-modal-body">
				<div class="ipt-info"></div>
				<!-- 2018_07_31 start -->
				<div class="row">
					<!-- 姓/拼音 -->
					<div class="col-sm-6">
						<div class="form-group" style="position:relative;">
							<label><span>*</span>姓 / 拼音</label> 
							<input 
								id="firstName"
								name="firstName" 
								type="text" 
								class="form-control input-sm" 
								tabIndex="1" 
								autocomplete="off"
								value="${obj.passport.firstName }" 
								onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"
							/>
							<input 
								type="text" 
								id="firstNameEn" 
								name="firstNameEn" 
								autocomplete="off" 
								style="position:absolute;top:32px;border:none;left:150px;" 
								value="${obj.firstNameEn }"
								onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"
							/>
						</div>
						<input type="hidden" id="id" name="id" value="${obj.passport.id }"/>
						<input type="hidden" id="OCRline1" name="OCRline1" value="">
						<input type="hidden" id="OCRline2" name="OCRline2" value="">
						<input type="hidden" id="applicantid" name="applicantid" value="${obj.applicantid }"/>
						<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
					</div>
					<!-- 名/拼音 -->
					<div class="col-sm-6">
						<div class="form-group" style="position:relative;">
							<label><span>*</span>名 / 拼音</label> 
							<input 
								id="lastName"
								name="lastName" 
								type="text" 
								class="form-control input-sm" 
								tabIndex="2" 
								autocomplete="off"
								value="${obj.passport.lastName }" 
							/>
							<input 
								type="text" 
								id="lastNameEn" 
								autocomplete="off" 
								style="position:absolute;top:32px;border:none;left:150px;" 
								name="lastNameEn" 
								value="${obj.lastNameEn }"
							/>
						</div>
					</div>
				</div>
				<div class="row">
					<!-- 护照号 && 性别 -->
					<div class="col-sm-6">
						<!-- 护照号 -->
						<div class="col-sm-6 pl-0 pr-5">
							<div class="form-group">
								<label><span>*</span>护照号</label>
								<input 
									id="passport" 
									name="passport" 
									type="text" 
									class="form-control input-sm" 
									autocomplete="off" 
									maxlength="9" 
									tabIndex="3" 
									value="${obj.passport.passport }"
								/>
							</div>
						</div>
						<!-- 性别 -->
						<div class="col-sm-6 pr-0 pl-5">
							<div class="col-sm-8 padding-0">
								<div class="form-group">
									<label><span>*</span>性别</label>
									<select class="form-control input-sm selectHeight" id="sex" name="sex" tabIndex="4">
										<option value="男" ${obj.passport.sex == "男"?"selected":"" }>男</option>
										<option value="女" ${obj.passport.sex == "女"?"selected":"" }>女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-4 pr-0 pl-10">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<input id="sexEn" class="form-control input-sm" autocomplete="off" name="sexEn" tabIndex="5" type="text" value="${obj.passport.sexEn }"/>
								</div>
							</div>
						</div>
					</div>
					<!-- 出生地点 && 签发地点  -->
					<div class="col-sm-6">
						<!-- 出生地点 -->
						<div class="col-sm-6 pl-0 pr-5">
							<div class="form-group relative">
								<label><span>*</span>出生地点 / 拼音</label>
								<input 
									id="birthAddress" 
									name="birthAddress" 
									autocomplete="off" 
									type="text" 
									class="form-control input-sm" 
									tabIndex="6" 
									value="${obj.passport.birthAddress }"
								/>
								<input 
									id="birthAddressEn" 
									name="birthAddressEn" 
									type="text" 
									autocomplete="off" 
									value="${obj.passport.birthAddressEn }"
								/>
							</div>
						</div>
						<!-- 签发地点 -->
						<div class="col-sm-6 pr-0 pl-5">
							<div class="form-group relative">
								<label><span>*</span>签发地点 / 拼音</label>
								<input 
									id="issuedPlace" 
									name="issuedPlace" 
									autocomplete="off" 
									type="text" 
									class="form-control input-sm" 
									tabIndex="8" 
									value="${obj.passport.issuedPlace }"
								/>
								<input 
									id="issuedPlaceEn" 
									name="issuedPlaceEn" 
									autocomplete="off" 
									type="text" 
									value="${obj.passport.issuedPlaceEn }"
								/>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<!-- 出生日期 -->
						<div class="col-sm-6  pl-0 pr-5">
							<div class="form-group">
								<label><span>*</span>出生日期</label>
								<input id="birthday" name="birthday" type="text" autocomplete="off" class="form-control input-sm" tabIndex="7" value="${obj.birthday}"/>
							</div>
						</div>
						<!-- 签发日期 -->
						<div class="col-sm-6 pr-0 pl-5">
							<div class="col-sm-8 padding-0">
								<div class="form-group">
									<label><span>*</span>签发日期</label>
									<input id="issuedDate" name="issuedDate" autocomplete="off" type="text" class="form-control input-sm" tabIndex="9" value="${obj.issuedDate }"/>
								</div>
							</div>
							<div class="col-sm-4 pr-0 pl-10">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<select id="validType" name="validType" class="form-control input-sm selectHeight" tabIndex="10" style="padding: 0">
										<c:forEach var="map" items="${obj.passportType}">
											<option value="${map.key}" ${map.key == obj.passport.validType?'selected':'' }>${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<!-- 有效期至 -->
						<div class="col-sm-6 pl-0 pr-5">
							<div class="form-group">
								<label><span>*</span>有效期至</label>
								<input id="validEndDate" name="validEndDate" autocomplete="off" type="text" class="form-control input-sm" tabIndex="11" value="${obj.validEndDate }"/>
							</div>
						</div>
						<div class="col-sm-6">
						
						</div>
					</div>
				</div>
				<div class="row">
					<!-- 公民身份号码 -->
					<div class="col-sm-6">
						<div class="form-group">
							<label><span>*</span>公民身份证</label>
							<input id="cardId" name="cardId" autocomplete="off" type="text" class="form-control input-sm" value="${obj.applicant.cardId }"/>
						</div>
					</div>
					<div class="col-sm-6">
						<!-- 手机号 -->
						<div class="col-sm-6 pl-0 pr-5">
							<div class="form-group">
								<label><span>*</span>手机号</label>
								<input id="issueOrganization" name="issueOrganization" type="hidden" value="${obj.applicant.issueOrganization }" />
								<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }" />
								<input type="hidden" id="id" name="id" value="${obj.applicant.id }" />
								<input id="telephone" name="telephone" type="text" autocomplete="off" class="form-control input-sm" value="${obj.applicant.telephone }" />
							</div>
						</div>
						<!-- 邮箱 -->
						<div class="col-sm-6 pr-0 pl-5">
							<div class="form-group">
								<label><span>*</span>邮箱</label>
								<input id="email" name="email" type="text" autocomplete="off" class="form-control input-sm" value="${obj.applicant.email }"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<!-- 现居住地 -->
					<div class="col-sm-3 pr-5">
						<div class="form-group">
							<label style="position: relative;"><span>*</span>现居住地（同主申请人）<input id="juzhudi-checkbox" name="juzhudicheckbox" type="checkbox" value=""></label>
							<input id="province" name="province" type="text" class="form-control input-sm" placeholder="省" value="${obj.applicant.province }"/>
						</div>
					</div>
					<!-- 详细地址 -->
					<div class="col-sm-6 pl-5 pr-5">
						<div class="form-group">
							<label><span>*</span>详细地址</label>
							<input id="detailedAddress" name="detailedAddress" type="text" class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间" autocomplete="off" value="${obj.applicant.detailedAddress }" />
						</div>
					</div>
					<!-- 婚姻状况 -->
					<div class="col-sm-3 pl-5">
						<div class="form-group">
							<label><span>*</span>婚姻状况</label>
							<select id="marryStatus" name="marryStatus" class="form-control input-sm selectHeight">
								<option value="">请选择</option>
								<c:forEach var="map" items="${obj.marryStatusEnum}">
									<option value="${map.key}" ${map.key==obj.applicant.marryStatus?'selected':''}>${map.value}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<!-- 是否有曾用名/曾有的或另有的国际(或公民身份) -->
					<div class="col-sm-6">
						<div class="form-group">
							<label>是否有曾用名</label>
							<div>
								<span class="nameBeforeYes">
									<input type="radio" name="hasOtherName" class="nameBefore" value="1" />
									是
								</span>
								<span>
									<input type="radio" name="hasOtherName" class="nameBefore" checked value="2" />
									否
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<!-- 姓/名 拼音 -->
					<div class="col-sm-6">
						<div class="nameBeforeHide">
							<div class="form-group relative">
								<label><span>*</span>姓 / 拼音</label>
								<input id="otherFirstName" autocomplete="off" name="otherFirstName" type="text" class="form-control input-sm" value="${obj.applicant.otherFirstName }" />
								<input type="text" id="otherFirstNameEn" autocomplete="off" name="otherFirstNameEn" value="${obj.otherFirstNameEn }" />
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<!-- 名/拼音 -->
						<div class="nameBeforeHide">
							<div class="form-group relative">
								<label><span>*</span>名 / 拼音</label>
								<input id="otherLastName" name="otherLastName" autocomplete="off" type="text" class="form-control input-sm otherLastName" value="${obj.applicant.otherLastName }" />
								<input type="text" id="otherLastNameEn" autocomplete="off" name="otherLastNameEn" value="${obj.otherLastNameEn }" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="col-sm-6 padding-0">
							<div class="form-group"><label>曾有的或另有的国籍(或公民身份)</label></div>
						</div>
						<div class="col-sm-6 padding-0">
							<div class="form-group"><label><span>*</span>国籍</label></div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="col-sm-6 padding-0">
							<div class="form-group" id="onceNationalityRadio">
								<div style="height: 35px;">
									<span class="onceIDYes">
										<input type="radio" name="hasOtherNationality" class="onceID" value="1" />
										是
									</span>
									<span>
										<input type="radio" name="hasOtherNationality" class="onceID" checked value="2" />
										否
									</span>
								</div>
							</div>
						</div>
						<div class="col-sm-6 padding-0 nationalityHide guoji">
							<div class="form-group" id="nationalityDiv">
								<input id="nationality" name="nationality" type="text" class="form-control input-sm" autocomplete="off" value="${obj.applicant.nationality}" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="col-sm-6 pl-0 pr-5">
							<div class="form-group">
								<label>紧急联系人姓名</label>
								<input id="emergencyLinkman" name="emergencyLinkman" type="text" class="form-control input-sm" autocomplete="off" value="${obj.applicant.emergencyLinkman }" />
							</div>
						</div>
						<div class="col-sm-6 pr-0 pl-5">
							<div class="form-group">
								<label id="updateApplicantHead">与主申请人的关系</label>
								</br>
								<div class="input-box c-input-box">
									<input type="text" id="mainRelation" name="mainRelation" style="font-size: 10px !important;" class="input" autocomplete="off"
									value=" ${obj.orderjp.mainRelation }">
									<ul class="dropdown c-dropdown" style="font-size: 10px !important;">
										<li>配偶</li>
										<li>父母</li>
										<li>子女</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="col-sm-6 padding-0">
							<div class="form-group">
								<label>紧急联系人手机</label>
								<input id="emergencyTelephone" name="emergencyTelephone" type="text" class="form-control input-sm" autocomplete="off" value="${obj.applicant.emergencyTelephone }" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="col-sm-12 padding-0">
							<div class="form-group">
								<label>紧急联系人地址</label>
								<input id="emergencyaddress" name="emergencyaddress" type="text" class="form-control input-sm" autocomplete="off" value="${obj.applicant.emergencyaddress }" />
							</div>
						</div>
					</div>
				</div>
				<!-- 2018_07_31 end -->
			</div>
		</form>
	</div>
	<script type="text/javascript">var BASE_PATH = '${base}';</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/admin/common/commonjs.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript" src="${base}/admin/simple/validationZh.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
		$(function () {

			var remark = $("#baseRemark").val();
			if (remark != "") {
				$(".ipt-info").show();
			}

			var nation = '${obj.applicant.hasOtherNationality}';
			var otherName = '${obj.applicant.hasOtherName}';
			var address = '${obj.applicant.addressIsSameWithCard}';
			$("input[name='hasOtherNationality'][value='" + nation + "']").attr("checked", 'checked');
			$("input[name='hasOtherName'][value='" + otherName + "']").attr("checked", 'checked');
			if (nation == 1) {
				$(".nameBeforeTop").css('float', 'none');
				$(".nationalityHide").show();
			} else {
				$(".nationalityHide").hide();
			}

			if (otherName == 1) {
				$(".nameBeforeHide").show();
				$(".wordSpell").show();
			} else {

				$(".nameBeforeHide").hide();
				$(".wordSpell").hide();
			}

			if (address == 1) {
				var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked", true);
			} else {
				var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked", false);
			}

			$('#applicantInfo').bootstrapValidator({
				message: '验证不通过',
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields: {
					telephone: {
						trigger: "change keyup",
						validators: {
							regexp: {
								regexp: /^[1][34578][0-9]{9}$/,
								message: '手机号格式错误'
							}
						}
					},
					email: {
						trigger: "change keyup",
						validators: {
							regexp: {
								regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
								message: '邮箱格式错误'
							}
						}
					},
					emergencyTelephone: {
						trigger: "change keyup",
						validators: {
							regexp: {
								regexp: /^[1][34578][0-9]{9}$/,
								message: '手机号格式错误'
							}
						}
					},
					passport: {
						trigger: "change keyup",
						validators: {
							stringLength: {
								min: 1,
								max: 9,
								message: '护照号不能超过9位'
							},
							remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/orderJp/checkPassport.html',
								message: '护照号已存在，请重新输入',//提示消息
								delay: 2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function (validator) {
									return {
										passport: $('#passport').val(),
										adminId: $('#id').val(),
										orderid: $('#orderid').val()
									};
								}
							}
						}
					},
					firstName: {
						validators: {
							notEmpty: {
								message: '姓不能为空'
							}
						}
					},
					lastName: {
						validators: {
							notEmpty: {
								message: '名不能为空'
							}
						}
					},
					firstNameEn: {
						trigger: "change keyup",
						validators: {
							regexp: {
								regexp: /^[\/a-zA-Z0-9_]{0,}$/,
								message: '拼音中不能包含汉字或其他特殊符号'
							},
						}
					},
					lastNameEn: {
						trigger: "change keyup",
						validators: {
							regexp: {
								// regexp: /\/{1}[a-zA-Z]+$/,
								regexp: /^[\/a-zA-Z0-9_]{0,}$/,
								message: '拼音中不能包含汉字或其他特殊符号'
							},
						}
					},
					birthAddressEn: {
						trigger: "change keyup",
						validators: {
							regexp: {
								regexp: /^[\/a-zA-Z0-9_]{0,}$/,
								message: '拼音中不能包含汉字或其他特殊符号'
							},
						}
					},
					issuedPlaceEn: {
						trigger: "change keyup",
						validators: {
							regexp: {
								regexp: /^[\/a-zA-Z0-9_]{0,}$/,
								message: '拼音中不能包含汉字或其他特殊符号'
							},
						}
					}
				}
			});
			$('#applicantInfo').bootstrapValidator('validate');


			var front = $("#cardFront").val();
			var back = $("#cardBack").val();
			if (front != "") {
				$("#uploadFile").siblings("i").css("display", "block");
			} else {
				$("#uploadFile").siblings("i").css("display", "none");
			}

			if (back != "") {
				$("#uploadFileBck").siblings("i").css("display", "block");
			} else {
				$("#uploadFileBack").siblings("i").css("display", "none");
			}
		});

		//国籍检索
		$("#nationality").on('input', function () {
			$("#nationality").nextAll("ul.ui-autocomplete").remove();
			$.ajax({
				type: 'POST',
				async: false,
				data: {
					searchStr: $("#nationality").val()
				},
				url: BASE_PATH + '/admin/orderJp/getNationality.html',
				success: function (data) {
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data, function (index, element) {
						liStr += "<li onclick='setNationality(" + JSON.stringify(element) + ")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>" + element + "</span></li>";
					});
					liStr += "</ul>";
					$("#nationality").after(liStr);
				}
			});
		});

		var index = -1;
		$(document).on('keydown', '#nationality', function (e) {
			var lilength = $(this).next().children().length;
			if (e == undefined)
				e = window.event;

			switch (e.keyCode) {
				case 38:
					e.preventDefault();
					index--;
					if (index == 0) index = 0;
					break;
				case 40:
					e.preventDefault();
					index++;
					if (index == lilength) index = 0;
					break;
				case 13:

					$(this).val($('#ui-id-1').find('li:eq(' + index + ')').children().html());
					$("#nationality").nextAll("ul.ui-autocomplete").remove();
					$("#nationality").blur();
					var nationality = $("#nationality").val();
					setNationality(nationality);
					index = -1;
					break;
			}
			var li = $('#ui-id-1').find('li:eq(' + index + ')');
			li.css({ 'background': '#1e90ff', 'color': '#FFF' }).siblings().css({ 'background': '#FFF', 'color': '#000' });
		});
		//国籍检索下拉项
		function setNationality(nationality) {
			$("#nationality").nextAll("ul.ui-autocomplete").remove();
			$("#nationality").val(nationality).change();
		}

		
		var provinceindex = -1;
		$(document).on('keydown', '#province', function (e) {

			if (e == undefined)
				e = window.event;

			switch (e.keyCode) {
				case 38:
					e.preventDefault();
					provinceindex--;
					if (provinceindex == 0) provinceindex = 0;
					break;
				case 40:
					e.preventDefault();
					provinceindex++;
					if (provinceindex == 5) provinceindex = 0;
					break;
				case 13:

					$(this).val($(this).next().find('li:eq(' + provinceindex + ')').children().html());
					$("#province").nextAll("ul.ui-autocomplete").remove();
					$("#province").blur();
					var province = $("#province").val();
					setProvince(province);
					provinceindex = -1;
					break;
			}
			var li = $(this).next().find('li:eq(' + provinceindex + ')');
			li.css({ 'background': '#1e90ff', 'color': '#FFF' }).siblings().css({ 'background': '#FFF', 'color': '#000' });
		});
		//省份 检索下拉项
		function setProvince(province) {
			$("#province").nextAll("ul.ui-autocomplete").remove();
			$("#province").val(province).change();
		}

		//市检索
		$("#city").on('input', function () {
			$("#city").nextAll("ul.ui-autocomplete").remove();
			$.ajax({
				type: 'POST',
				async: false,
				data: {
					province: $("#province").val(),
					searchStr: $("#city").val()
				},
				url: BASE_PATH + '/admin/orderJp/getCity.html',
				success: function (data) {
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data, function (index, element) {
						liStr += "<li onclick='setCity(" + JSON.stringify(element) + ")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>" + element + "</span></li>";
					});
					liStr += "</ul>";
					$("#city").after(liStr);
				}
			});
		});
		//市
		var cityindex = -1;
		$(document).on('keydown', '#city', function (e) {

			if (e == undefined)
				e = window.event;

			switch (e.keyCode) {
				case 38:
					e.preventDefault();
					cityindex--;
					if (cityindex == 0) cityindex = 0;
					break;
				case 40:
					e.preventDefault();
					cityindex++;
					if (cityindex == 5) cityindex = 0;
					break;
				case 13:

					$(this).val($(this).next().find('li:eq(' + provinceindex + ')').children().html());
					$("#city").nextAll("ul.ui-autocomplete").remove();
					$("#city").blur();
					var city = $("#city").val();
					setCity(city);
					cityindex = -1;
					break;
			}
			var li = $(this).next().find('li:eq(' + cityindex + ')');
			li.css({ 'background': '#1e90ff', 'color': '#FFF' }).siblings().css({ 'background': '#FFF', 'color': '#000' });
		});
		//市 检索下拉项
		function setCity(city) {
			$("#city").nextAll("ul.ui-autocomplete").remove();
			$("#city").val(city).change();
		}


		//背面上传,扫描
		$('#uploadFileBack').change(function () {
			var layerIndex = layer.load(1, {
				shade: "#000"
			});
			$("#addBtn").attr('disabled', true);
			$("#updateBtn").attr('disabled', true);
			var file = this.files[0];
			var reader = new FileReader();
			reader.onload = function (e) {
				var dataUrl = e.target.result;
				var blob = dataURLtoBlob(dataUrl);
				var formData = new FormData();
				formData.append("image", blob, file.name);
				$.ajax({
					type: "POST",//提交类型  
					//dataType : "json",//返回结果格式  
					url: BASE_PATH + '/admin/orderJp/IDCardRecognitionBack',//请求地址  
					async: true,
					processData: false, //当FormData在jquery中使用的时候需要设置此项
					contentType: false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data: formData,
					success: function (obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (true === obj.success) {
							layer.msg("识别成功");
							$('#cardBack').val(obj.url);
							$('#sqImgBack').attr('src', obj.url);
							$("#uploadFileBack").siblings("i").css("display", "block");
							$(".back").attr("class", "info-imgUpload back has-success");
							$(".help-blockBack").attr("data-bv-result", "IVALID");
							$(".help-blockBack").attr("style", "display: none;");
							$("#borderColorBack").attr("style", null);
							$('#validStartDate').val(obj.starttime).change();
							$('#validEndDate').val(obj.endtime).change();
							$('#issueOrganization').val(obj.issue).change();
						}
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						layer.close(layerIndex);
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					}
				}); // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});

		//把dataUrl类型的数据转为blob
		function dataURLtoBlob(dataurl) {
			var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
				n);
			while (n--) {
				u8arr[n] = bstr.charCodeAt(n);
			}
			return new Blob([u8arr], {
				type: mime
			});
		}

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.cancelCallBack(1);
		}

		//点击身份证图片上的删除按钮
		function deleteApplicantFrontImg(id) {
			$('#cardFront').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display", "none");
			

		}
		function deleteApplicantBackImg(id) {
			$('#cardBack').val("");
			$('#sqImgBack').attr('src', "");
			$("#uploadFileBack").siblings("i").css("display", "none");
			
		}

		$(function () {
		
			$("#validStartDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition: "bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			$("#validEndDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition: "bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			$("#birthday").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				weekStart: 1,
				todayBtn: 1,
				autoclose: true,
				todayHighlight: true,//高亮
				startView: 4,//从年开始选择
				forceParse: 0,
				showMeridian: false,
				pickerPosition: "bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
		});
		//checkbox 曾用名
		$(".nameBefore").change(function () {

			let checked = $("input[name='hasOtherName']:checked").val();
			if (checked == 1) {
				$(".nameBeforeHide").show();
				$(".wordSpell").show();
				$("#otherFirstName").val("").change();
				$("#otherFirstNameEn").val("");
				$("#otherLastName").val("").change();
				$("#otherLastNameEn").val("");
			} else {

				$(".nameBeforeHide").hide();
				$(".wordSpell").hide();
			}
		});
		//曾用国籍
		$(".onceID").change(function () {
			let checked = $("input[name='hasOtherNationality']:checked").val();
			if (checked == 1) {
				$(".nationalityHide").show();
				$("#nationality").val("").change();
			} else {

				$(".nationalityHide").hide();
			}
		});

		//居住地与身份证相同
		$(".nowProvince").change(function () {
			var str = "";
			//是否同身份证相同
			$("input:checkbox[name='addressIsSameWithCard']:checked").each(function () {
				str = $(this).val();
			});
			if (str == 1) {//相同
				searchByCard();
			} else {
				$("#province").val("").change();
				$("#city").val("").change();
				$("#detailedAddress").val("").change();
			}
		});

		function searchByCard() {

			var cardId = $("#cardId").val();
			layer.load(1);
			$.ajax({
				type: 'POST',
				data: {
					cardId: cardId
				},
				url: '${base}/admin/orderJp/getInfoByCard',
				success: function (data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					$("#detailedAddress").val($("#address").val()).change();
					if (data != null) {
						$("#province").val(data.province).change();
						$("#city").val(data.city).change();
					}
				}
			});

		}

		function toPassport() {

			saveApplicant(2);
		}
		function toVisaInfo() {

			saveApplicant(3);
		}
		function saveApplicant(status) {
			var str = "";
			$("input:checkbox[name='addressIsSameWithCard']:checked").each(function () {
				str = $(this).val();
			});
			var applicantid = '${obj.applicantid}';
			var orderid = '${obj.orderid}';
			var applicantInfo = getFormJson('#applicantInfo');
			if (status != 2) {
				//得到获取validator对象或实例 
				var bootstrapValidator = $("#applicantInfo").data(
					'bootstrapValidator');
				// 执行表单验证 
				bootstrapValidator.validate();
				if (bootstrapValidator.isValid()) {
					layer.load(1);
					ajaxConnection();
				}
			} else {
				layer.load(1);
				$.ajax({
					//async: false,
					type: 'POST',
					data: applicantInfo,
					url: '${base}/admin/simple/saveApplicantInfo.html',
					success: function (data) {
						layer.closeAll("loading");
						console.log(JSON.stringify(data));
						//socket.onclose();
						window.location.href = '/admin/simple/passportInfo.html?applicantid='+applicantid+'&orderid='+orderid;
						}
					});
					
			}
			var count = 0;
			function ajaxConnection() {
				$.ajax({
					//async: false,
					type: 'POST',
					data: applicantInfo,
					url: '${base}/admin/simple/saveApplicantInfo.html',
					success: function (data) {
						layer.closeAll("loading");
						console.log(JSON.stringify(data));
						//var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						//layer.close(index);
						if (status == 1) {
							parent.successCallBack(1);
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
						}else if(status == 2){
							//socket.onclose();
							window.location.href = '/admin/simple/passportInfo.html?applicantid='+applicantid+'&orderid='+orderid;
						}else if(status == 3){
							//socket.onclose();
							window.location.href = '/admin/simple/visaInfo.html?applicantid='+applicantid+'&orderid='+orderid;
						}
					}, error: function (error, XMLHttpRequest, status) {
						console.log("error:", error);
						console.log("XMLHttpRequest:", error);
						console.log("status:", error);
						if (status == 'timeout') {//超时,status还有success,error等值的情况
							//ajaxTimeOut.abort(); //取消请求
							count++;
							ajaxConnection();
							var index = layer.load(1, {
								content: '第' + count + '次重连中...<br/>取消重连请刷新！', success: function (layero) {
									layero.find('.layui-layer-content').css({
										'width': '140px',
										'padding-top': '50px',
										'background-position': 'center',
										'text-align': 'center',
										'margin-left': '-55px',
										'margin-top': '-10px'
									});


								}
							});
						}
					}, timeout: 10000
				});
			}
		}

		function getFormJson(form) {
			var o = {};
			var a = $(form).serializeArray();
			$.each(a, function () {
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
		
		/** 2018_08_03 */
		(() => {
			let flag = "${obj.orderjp.isMainApplicant}";
			let $jzdChenkbox = $('#juzhudi-checkbox');
			let v = "${obj.applicant.addressIsSameWithCard }";
			let maininfo;

			flag == 1 ? $jzdChenkbox.hide() : $jzdChenkbox.show();


			$.ajax({
				type: 'POST',
				url: '${base}/admin/simple/isSamewithMainapply.html',
				data: {
					orderid: '${obj.orderid}'
				},
				success: function (data) {
					maininfo = data.maininfo;
					console.log(maininfo);
				}
			});
			
			if (v == '' || v == null || v == undefined) {
				v = 0
			}
			
			if (Number(v) == 1) {
				$jzdChenkbox.attr('checked', 'checked');
			}

			$jzdChenkbox.val(v);
			
			$jzdChenkbox.change(function() {
				let $checkbox = $(this);
				let v = $checkbox.val();
				if (v == 0) {
					v = 1;
					$('#province').val(maininfo.province);
					$('#detailedAddress').val(maininfo.detailedaddress);
				} else {
					v = 0;
					$('#province').val('');
					$('#detailedAddress').val('');
				}
				
				$checkbox.val(v);
				
			});
		})();
	</script>
	<script>
		(function() {
			var base = "${base}";
			$(function () {
				var issuedOrganization = "${obj.passport.issuedOrganization }";
				var issuedOrganizationen = "${obj.passport.issuedOrganizationEn }";

				if (issuedOrganization == "") {
					$("#issuedOrganization").val("公安部出入境管理局");
				}
				if (issuedOrganizationen == "") {
					$("#issuedOrganizationEn").val("Ministry of Public Security");
				}

				//护照图片验证
				$('#applicantInfo').bootstrapValidator({
					message: '验证不通过',
					feedbackIcons: {
						valid: 'glyphicon glyphicon-ok',
						invalid: 'glyphicon glyphicon-remove',
						validating: 'glyphicon glyphicon-refresh'
					},
					fields: {
						passport: {
							trigger: "change keyup",
							validators: {
								stringLength: {
									min: 1,
									max: 9,
									message: '护照号不能超过9位'
								},
								remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
									url: '${base}/admin/orderJp/checkPassport.html',
									message: '护照号已存在，请重新输入',//提示消息
									delay: 2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
									type: 'POST',//请求方式
									//自定义提交数据，默认值提交当前input value
									data: function (validator) {
										return {
											passport: $('#passport').val(),
											adminId: $('#id').val(),
											orderid: $('#orderid').val()
										};
									}
								}
							}
						},
						firstName: {
							validators: {
								notEmpty: {
									message: '姓不能为空'
								}
							}
						},
						lastName: {
							validators: {
								notEmpty: {
									message: '名不能为空'
								}
							}
						},
						firstNameEn: {
							trigger: "change keyup",
							validators: {
								regexp: {
									regexp: /^[\/a-zA-Z0-9_]{0,}$/,
									message: '拼音中不能包含汉字或其他特殊符号'
								},
							}
						},
						lastNameEn: {
							trigger: "change keyup",
							validators: {
								regexp: {
									// regexp: /\/{1}[a-zA-Z]+$/,
									regexp: /^[\/a-zA-Z0-9_]{0,}$/,
									message: '拼音中不能包含汉字或其他特殊符号'
								},
							}
						},
						birthAddressEn: {
							trigger: "change keyup",
							validators: {
								regexp: {
									regexp: /^[\/a-zA-Z0-9_]{0,}$/,
									message: '拼音中不能包含汉字或其他特殊符号'
								},
							}
						},
						issuedPlaceEn: {
							trigger: "change keyup",
							validators: {
								regexp: {
									regexp: /^[\/a-zA-Z0-9_]{0,}$/,
									message: '拼音中不能包含汉字或其他特殊符号'
								},
							}
						}
					}
				});

				$('#passportInfo').bootstrapValidator('validate');


				var remark = $("#passRemark").val();
				if (remark != "") {
					$(".ipt-info").show();
				}

				if ($("#sex").val() == "男") {
					$("#sexEn").val("M");
				} else {
					$("#sexEn").val("F");
				}

				$("#issuedDate").change(function () {
					if ($("#issuedDate").val() != "") {
						if ($("#validType").val() == 1) {
							$('#validEndDate').val(getNewDates($('#issuedDate').val(), 10));
						} else {
							$('#validEndDate').val(getNewDates($('#issuedDate').val(), 5));
						}

					}
				});
			});
			

			//把dataUrl类型的数据转为blob
			function dataURLtoBlob(dataurl) {
				var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
					n);
				while (n--) {
					u8arr[n] = bstr.charCodeAt(n);
				}
				return new Blob([u8arr], {
					type: mime
				});
			}

			//保存
			function save(status) {
				//得到获取validator对象或实例 
				var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
				bootstrapValidator.validate();
				if (!bootstrapValidator.isValid()) {
					return;
				}
				var passportInfo = $("#passportInfo").serialize();
				var id = '${obj.applicantid}';
				var orderid = '${obj.orderid}';
				layer.load(1);
				ajaxConnection();
				var count = 0;
				function ajaxConnection() {
					$.ajax({
						type: 'POST',
						//async : false,
						data: passportInfo,
						url: '${base}/admin/simple/saveEditPassport.html',
						success: function (data) {
							layer.closeAll("loading");
							console.log(JSON.stringify(data));
							if (data.msg) {
								layer.msg(data.msg);
							} else {
								if (status == 2) {
									socket.onclose();
									window.location.href = '/admin/simple/updateApplicant.html?applicantid=' + id + '&orderid=' + orderid;
								}
								if (status == 1) {
									parent.successCallBack(1);
									closeWindow();
								}
							}
						}, error: function (error, XMLHttpRequest, status) {
							console.log("error:", error);
							console.log("XMLHttpRequest:", error);
							console.log("status:", error);
							if (status == 'timeout') {
								count++;
								ajaxConnection();
								var index = layer.load(1, {
									content: '第' + count + '次重连中...<br/>取消重连请刷新！', success: function (layero) {
										layero.find('.layui-layer-content').css({
											'width': '140px',
											'padding-top': '50px',
											'background-position': 'center',
											'text-align': 'center',
											'margin-left': '-55px',
											'margin-top': '-10px'
										});


									}
								});
							}
						}, timeout: 10000
					});

				}

			}


			//返回 
			function closeWindow() {
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				parent.successCallBack();
			}
			$(function () {
				var passport = $("#passportUrl").val();
				if (passport != "") {
					$("#uploadFile").siblings("i").css("display", "block");
				} else {
					$("#uploadFile").siblings("i").css("display", "none");
				}

			});

			$("#sex").change(function () {
				var sex = $(this).val();
				if (sex == "男") {
					$("#sexEn").val("M");
				} else {
					$("#sexEn").val("F");
				}
			});

			$("#validType").change(function () {
				var type = $(this).val();
				if ($("#issuedDate").val() != "") {
					if (type == 1) {
						$('#validEndDate').val(getNewDates($('#issuedDate').val(), 10));
					} else {
						$('#validEndDate').val(getNewDates($('#issuedDate').val(), 5));
					}
				}
			});

			function getNewDates(dateTemp, days) {
				var d1 = new Date(dateTemp);
				var d2 = new Date(d1);
				d2.setFullYear(d2.getFullYear() + days);
				d2.setDate(d2.getDate() - 1);
				var year = d2.getFullYear();
				var month = d2.getMonth() + 1;
				if (month < 10) month = "0" + month;
				var date = d2.getDate();
				if (date < 10) date = "0" + date;
				return (year + "-" + month + "-" + date);
			}

			function getNewDay(dateTemp, days) {
				var dateTemp = dateTemp.split("-");
				var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
				var millSeconds = Math.abs(nDate) + (days * 365.2 * 24 * 60 * 60 * 1000);
				var rDate = new Date(millSeconds);
				var year = rDate.getFullYear();
				var month = rDate.getMonth() + 1;
				if (month < 10) month = "0" + month;
				var date = rDate.getDate();
				if (date < 10) date = "0" + date;
				return (year + "-" + month + "-" + date);
			}

			function returnYears(year) {
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
					return 366;
				} else {
					return 365;
				}
			}

			function deleteApplicantFrontImg() {
				$('#passportUrl').val("");
				$('#sqImg').attr('src', "");
				$("#uploadFile").siblings("i").css("display", "none");
			}

			var now = new Date();
			$("#birthday").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				weekStart: 1,
				todayBtn: 1,
				autoclose: true,
				todayHighlight: true,//高亮
				startView: 4,//从年开始选择
				forceParse: 0,
				showMeridian: false,
				pickerPosition: "bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			$("#issuedDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				endDate: now,
				autoclose: true,//选中日期后 自动关闭
				pickerPosition: "bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			$("#validEndDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition: "bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			function getPinYinStr(hanzi) {
				var onehanzi = hanzi.split('');
				var pinyinchar = '';
				for (var i = 0; i < onehanzi.length; i++) {
					pinyinchar += PinYin.getPinYin(onehanzi[i]);
				}
				return pinyinchar.toUpperCase();
			}

			function getDateYearSub(startDateStr, endDateStr) {
				var day = 24 * 60 * 60 * 1000;

				var sDate = new Date(Date.parse(startDateStr.replace(/-/g, "/")));
				var eDate = new Date(Date.parse(endDateStr.replace(/-/g, "/")));

				//得到前一天(算头不算尾)
				sDate = new Date(sDate.getTime() - day);

				//获得各自的年、月、日
				var sY = sDate.getFullYear();
				var sM = sDate.getMonth() + 1;
				var sD = sDate.getDate();
				var eY = eDate.getFullYear();
				var eM = eDate.getMonth() + 1;
				var eD = eDate.getDate();

				if (eY > sY && sM == eM && sD == eD) {
					return eY - sY;
				} else {
					//alert("两个日期之间并非整年，请重新选择");
					return 0;
				}
			}

			function applyBtn() {
				var id = '${obj.applicantid}';
				var orderid = '${obj.orderid}';
				save(2);

			}
		})();
	</script>
</body>
</html>
