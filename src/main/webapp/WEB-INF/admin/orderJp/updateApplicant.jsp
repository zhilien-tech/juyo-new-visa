<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
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
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css?v='20180510'">
<!-- 本页css -->
<link rel="stylesheet" href="${base}/references/common/css/updateApplicant.css?v='20180510'">
</head>
<body>
	<div class="modal-content">
		<a id="toPassport" class="rightNav" onclick="passportBtn();">
			<span></span>
		</a>
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">基本信息</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin"
					data-dismiss="modal" value="取消" /> <input id="addBtn"
					type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin"
					value="保存" onclick="saveApplicant(1)" />
					<c:choose>
						<c:when test="${(obj.orderStatus > 4 && obj.orderStatus < 9 )|| (obj.orderStatus ==88 && obj.isTrailOrder==1)}">  
							<input id="unqualifiedBtn" type="button" style="display:none" class="btn btn-primary pull-right btn-sm btn-right Unqualified btn-margin" value="不合格" />
							<input id="qualifiedBtn" type="button" style="display:none" class="btn btn-primary pull-right btn-sm btn-right qualified btn-margin" value="合格" />
						</c:when>
						<c:otherwise> 
						</c:otherwise>
					</c:choose>
			</div>
			<div class="modal-body">
			<div class="ipt-info">
					<input id="baseRemark" name="baseRemark" type="text" value="${obj.unqualified.baseRemark }" placeholder="请输入不合格原因"  class="NoInfo" />
				</div>
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<div class="info-QRcode"> <!-- 身份证 正面 -->
							<img width="100%" height="100%" alt="" src="${obj.qrCode }">
						</div> <!-- end 身份证 正面 -->
						<div class="info-imgUpload front has-error" id="borderColorFront">
							<!-- 身份证 正面 -->
							<div class="col-xs-6 mainWidth">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传身份证正面</span>
									<input id="cardFront" name="cardFront" type="hidden" value="${obj.applicant.cardFront }"/>
									<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									<img id="sqImg" name="sqImg" alt="" src="${obj.applicant.cardFront }" >
									<i class="delete" onclick="deleteApplicantFrontImg(${obj.orderid});"></i>
								</div>
							</div>
						</div>
						</div>
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;margin:-20px 0 -20px 32px !important">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">身份证正面必须上传</small>
						</div>
						<!-- end 身份证 正面 -->

						<div class="info-imgUpload back has-error" id="borderColorBack">
							<!-- 身份证 反面 -->
							<div class="col-xs-6 mainWidth">
								<div class="form-group">
									<div class="cardFront-div">
										<span>点击上传身份证背面</span>
										<input id="cardBack" name="cardBack" type="hidden" value="${obj.applicant.cardBack }"/>
										<input id="uploadFileBack" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
										<img id="sqImgBack" alt="" src="${obj.applicant.cardBack }" >
										<i class="delete" onclick="deleteApplicantBackImg(${obj.orderid});"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-6 front has-error" style="width:320px; height:20px; border:0 !important; color:red;margin:-20px 0 0 32px !important">
							<small class="help-blockBack" data-bv-validator="notEmpty" data-bv-for="cardBack" data-bv-result="IVVALID" style="display: none;">身份证背面必须上传</small>
						</div>
						<!-- end 身份证 反面 -->

						<%-- <div class="row">
							<!-- 签发机关 -->
							<div class="col-sm-10 padding-right-0 marginL">
								<div class="form-group">
									<label><span>*</span>签发机关</label> 
									<input id="issueOrganization" name="issueOrganization" tabIndex="1"
										type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.issueOrganization }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 签发机关 --> --%>
						<%-- <div class="row">
							<!-- 是否有曾用名/曾有的或另有的国际(或公民身份) -->
							<div class="col-sm-5 padding-right-0 nameBeforeTop">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes">
											<input type="radio" name="hasOtherName" class="nameBefore" value="1"
											/>是
										</span>
										<span>
											<input type="radio" name="hasOtherName" class="nameBefore"   value="2"
											/>否
										</span>
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 -->
							<div class="nameBeforeHide">
							    <div class="col-sm-10 padding-right-0 marginL">
									<div class="form-group" style="position:relative;">
										<label>姓/拼音</label> <input id="otherFirstName"
											name="otherFirstName" type="text" class="form-control input-sm "  tabIndex="15"
											placeholder=" " value="${obj.applicant.otherFirstName }" />
											<input type="text" id="otherFirstNameEn" style="position:absolute;top:32px;border:none;left:150px;"  name="otherFirstNameEn" value="${obj.otherFirstNameEn }"/>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 end -->
							<div class="col-sm-offset-1 padding-right-0 onceIDTop">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasOtherNationality" class="onceID" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherNationality" class="onceID"  value="2"  />否
										</span>
									</div>
								</div>
							</div>
							<!-- 曾用国籍 -->
							<div class="col-sm-5 padding-right-0 nationalityHide">
								<div class="form-group" id="nationalityDiv">
									<label>国籍</label> 
									<input id="nationality" name="nationality"  tabIndex="17" value="${obj.applicant.nationality}" type="text" class="form-control input-sm"/>
								</div>
							</div>
						</div> --%>
					</div>

					<div class="col-sm-6 padding-right-0">
						
						<div class="row">
							<!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>手机号</label> 
										<input id="issueOrganization" name="issueOrganization" type="hidden"  value="${obj.applicant.issueOrganization }"/>
										<input type="hidden" id="id" name="id" value="${obj.applicant.id }"/>
										<%-- <input type="hidden" name="userType" value="${obj.userType }"/> --%>
										<input type="hidden" name="tourist" value="${obj.tourist }"/>
										<input type="hidden" name="addApply" value="${obj.addApply }"/>
										<input type="hidden" id="isTrailOrder" name="isTrailOrder" value="${obj.isTrailOrder }"/>
										<input type="hidden" name="orderProcessType" value="${obj.orderProcessType }"/>
										<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
									<input id="telephone"
										name="telephone" type="text" class="form-control input-sm"  tabIndex="4"
										placeholder=" " value="${obj.applicant.telephone }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱</label> <input id="email" name="email"
										type="text" class="form-control input-sm" placeholder=" "  tabIndex="5"
										value="${obj.applicant.email }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 手机号/邮箱 -->
						
						<div class="row">
							<!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>公民身份证</label> <input id="cardId"
										name="cardId" type="text" class="form-control input-sm"  tabIndex="6"
										placeholder=" " value="${obj.applicant.cardId }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 公民身份证 -->
						<div class="row">
							<!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>性别</label> 
									<%-- <input id="sex" name="sex" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.sex }"/> --%>
									<select
										class="form-control input-sm selectHeight" id="sex" name="sex">
										<%-- <c:forEach var="map" items="${obj.boyOrGirlEnum}">
												<option value="${map.key}" ${map.key==obj.applicant.sex?'selected':''}>${map.value}</option>
											</c:forEach> --%>
											<option value="男" ${obj.applicant.sex == "男"?"selected":"" }>男</option>
										<option value="女" ${obj.applicant.sex == "女"?"selected":"" }>女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label>民族</label> <input id="nation"
										name="nation" type="text" class="form-control input-sm"  tabIndex="7"
										placeholder=" " value="${obj.applicant.nation }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5 padding-right-0">
								<div class="form-group">
									<label>出生日期</label> 
									<input id="birthday" name="birthday" type="text"  tabIndex="8" class="form-control input-sm" value="${obj.birthday }"/>
									
								</div>
							</div>
						</div>
						<!-- end 姓名/民族 -->
						<div class="row">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>住址</label> <input id="address"
										name="address" type="text" class="form-control input-sm"  tabIndex="9"
										placeholder=" " value="${obj.applicant.address }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 住宅 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>有效期限</label> 
									<input id="validStartDate" name="validStartDate"  tabIndex="10" type="text" class="form-control input-sm" value="${obj.validStartDate }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label> &nbsp; &nbsp;</label> 
									<input id="validEndDate" type="text" name="validEndDate"  tabIndex="11"  class="form-control input-sm" value="${obj.validEndDate }">
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group" id="provinceDiv">
									<label>现居住地是否与身份证相同</label>
									<input type="hidden" name="cardProvince" id="cardProvince" value="${obj.applicant.cardProvince }"/>
									<input type="hidden" name="cardCity" id="cardCity" value="${obj.applicant.cardCity }"/>
									<input type="hidden" id="sameAddress" value=""/>
									<input class="nowProvince" type="checkbox" name="addressIsSameWithCard" value="1" /> <input id="province"
										name="province" type="text" class="form-control input-sm"  tabIndex="12"
										placeholder="省" value="${obj.applicant.province }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group" id="cityDiv">
									<label>现居住地址城市</label> <input id="city"
										name="city" type="text" class="form-control input-sm" tabIndex="13"
										placeholder="市" value="${obj.applicant.city }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 现居住地址省份/现居住地址城市 -->
						<div class="row">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址</label> <input
										id="detailedAddress" name="detailedAddress" type="text"  tabIndex="14"
										class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间"
										value="${obj.applicant.detailedAddress }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->
						<div class="row">
							<div class="col-sm-5 padding-right-0 nameBeforeTop">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes">
											<input type="radio" name="hasOtherName" class="nameBefore" value="1"
											/>是
										</span>
										<span>
											<input type="radio" name="hasOtherName" class="nameBefore"   value="2"
											/>否
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 姓/名 拼音 -->
							<div class="nameBeforeHide">
							    <div class="col-sm-11 padding-right-0 col-sm-offset-1">
									<div class="form-group" style="position:relative;">
										<label>姓/拼音</label> <input id="otherFirstName"
											name="otherFirstName" type="text" class="form-control input-sm "  tabIndex="15"
											placeholder=" " value="${obj.applicant.otherFirstName }" />
											<input type="text" id="otherFirstNameEn" style="position:absolute;top:32px;border:none;left:150px;"  name="otherFirstNameEn" value="${obj.otherFirstNameEn }"/>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 end -->
						</div>
						<div class="row">
							<!-- 名/拼音 -->
						<div class="nameBeforeHide">
							<div class="col-sm-11 padding-right-0 col-sm-offset-1" >
								<div class="form-group" style="position:relative;">
									<label>名/拼音</label> 
									<input id="otherLastName" name="otherLastName"  tabIndex="16" type="text" class="form-control input-sm otherLastName" placeholder=" " value="${obj.applicant.otherLastName }" />
									<input type="text" id="otherLastNameEn" style="position:absolute;top:32px;border:none;left:150px;" name="otherLastNameEn" value="${obj.otherLastNameEn }"/>
								</div>
							</div>
						</div>
						</div>
						<div class="row">
							<div class="col-sm-offset-1 padding-right-0 onceIDTop">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasOtherNationality" class="onceID" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherNationality" class="onceID"  value="2"  />否
										</span>
									</div>
								</div>
							</div>
							<!-- 曾用国籍 -->
							<div class="col-sm-5 padding-right-0 nationalityHide">
								<div class="form-group" id="nationalityDiv">
									<label>国籍</label> 
									<input id="nationality" name="nationality"  tabIndex="17" value="${obj.applicant.nationality}" type="text" class="form-control input-sm"/>
								</div>
							</div>
						</div>
						
						<div class="row">
							<!-- 紧急联系人姓名/手机 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人姓名</label> <input id="emergencyLinkman"
										name="emergencyLinkman" type="text" class="form-control input-sm"  tabIndex="18"
										placeholder=" " value="${obj.applicant.emergencyLinkman }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人手机</label> <input id="emergencyTelephone" name="emergencyTelephone"  tabIndex="19"
										type="text" class="form-control input-sm" placeholder=" "
										value="${obj.applicant.emergencyTelephone }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 紧急联系人地址-->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人地址</label> <input id="emergencyaddress"
										name="emergencyaddress" type="text" class="form-control input-sm"  tabIndex="18"
										placeholder=" " value="${obj.applicant.emergencyaddress }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		//var userType = '${obj.userType}';
		var tourist = '${obj.tourist}';
		var isTrailOrder = '${obj.isTrailOrder}';
		var sessionId = '${obj.sessionid}';
		var localAddr = '${obj.localAddr}';
		var localPort = '${obj.localPort}';
		var websocketaddr = '${obj.websocketaddr}';
		var orderProcessType = '${obj.orderProcessType}';
		var orderid = '${obj.orderid}';
		var orderJpId = '${obj.orderJpId}';
		var applicantId = '${obj.applicantId}';
		var infoType = '${obj.infoType}';
		var addApply = '${obj.addApply}';
	</script>
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
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
	<script type="text/javascript" src="${base}/admin/orderJp/updateApplicant.js"></script>
	<script type="text/javascript">
	
		$(function(){
			var nation = '${obj.applicant.hasOtherNationality}';
			var otherName = '${obj.applicant.hasOtherName}';
			var address = '${obj.applicant.addressIsSameWithCard}';
			$("input[name='hasOtherNationality'][value='"+nation+"']").attr("checked",'checked');
			$("input[name='hasOtherName'][value='"+otherName+"']").attr("checked",'checked');
			if(nation == 1){
				$(".nationalityHide").show();
			}else {
				$(".nationalityHide").hide();
			}
			
			if(otherName == 1){
				$(".nameBeforeHide").show();
				$(".wordSpell").show();
			}else {
				
				$(".nameBeforeHide").hide();
				$(".wordSpell").hide();
			}
			
			if(address == 1){
				var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked",true);
			}else{
				var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked",false);
			}
		});
		function successCallBack(status){
			if(status == 1){
				parent.successCallBack(1);
				closeWindow();
			}
		}
		function cancelCallBack(status){
			closeWindow();
		}
		
		//点击身份证图片上的删除按钮
		function deleteApplicantFrontImg(id){
			$('#cardFront').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
			if(tourist == 1){
				$(".front").attr("class", "info-imgUpload front has-error");  
		        $(".help-blockFront").attr("data-bv-result","INVALID");  
		        //$(".help-blockFront").attr("style","display: block;");
		        //$("#borderColorFront").attr("style", "border-color:#ff1a1a");
			}
			
		}
		function deleteApplicantBackImg(id){
			$('#cardBack').val("");
			$('#sqImgBack').attr('src', "");
			$("#uploadFileBack").siblings("i").css("display","none");
			if(tourist == 1){
				$(".back").attr("class", "info-imgUpload back has-error");  
		        $(".help-blockBack").attr("data-bv-result","INVALID");  
		        //$(".help-blockBack").attr("style","display: block;");
		        //$("#borderColorBack").attr("style", "border-color:#ff1a1a");
			}
		}
		
		$(function(){
			$("#validStartDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			$("#validEndDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"bottom-right",//显示位置
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
				pickerPosition:"bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
		});
		function passportBtn(){
			saveApplicant(2);
		}
	</script>
</body>
</html>
