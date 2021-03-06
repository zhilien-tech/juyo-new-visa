<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>更新基本信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/appAddStaff.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/appUpdateStaff.css?v='20180510'">
</head>
<body>
	<div class="modal-content">
		<a id="toPassport" class="rightNav" onclick="passportBtn();">
			<span></span>
		</a>
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">更新基本信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" onclick="saveApplicant(1)" />
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<input id="comId" name="comid" type="hidden" value="${obj.applicant.comid }">
					<input id="userId" name="userid" type="hidden" value="${obj.applicant.userid }">
					<div class="col-sm-6 padding-right-0">
						<div></div>
						<!-- start 身份证 正面 -->
						<div class="info-imgUpload front has-error" id="borderColorFront">
							<div class="col-xs-6 mainWidth">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传身份证正面</span>
									<input id="cardFront" name="cardfront" type="hidden" value="${obj.applicant.cardfront }"/>
									<input id="uploadFile" name="uploadfile" class="btn btn-primary btn-sm" type="file"  value="上传"/>
									<img id="sqImg" name="sqimg" alt="" src="${obj.applicant.cardfront }" >
									<i class="delete" onclick="deleteApplicantFrontImg();"></i>
								</div>
							</div>
						</div>
						</div>
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;margin:-20px 0 -20px 32px !important">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">身份证正面必须上传</small>
						</div>
						<!-- end 身份证 正面 -->
						<!-- start 身份证 反面 -->
						<div class="info-imgUpload back has-error" id="borderColorBack">
							<div class="col-xs-6 mainWidth">
								<div class="form-group">
									<div class="cardFront-div">
										<span>点击上传身份证背面</span>
										<input id="cardBack" name="cardBack" type="hidden" value="${obj.applicant.cardback }"/>
										<input id="uploadFileBack" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="上传"/>
										<img id="sqImgBack" alt="" src="${obj.applicant.cardback }" >
										<i class="delete" onclick="deleteApplicantBackImg();"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-6 front has-error" style="width:320px; height:20px; border:0 !important; color:red;margin:-20px 0 0 32px !important">
							<small class="help-blockBack" data-bv-validator="notEmpty" data-bv-for="cardBack" data-bv-result="IVVALID" style="display: none;">身份证背面必须上传</small>
						</div>
						<!-- end 身份证 反面 -->

						<div class="row">
							<!-- 签发机关 -->
							<div class="col-sm-10 padding-right-0 marginL">
								<div class="form-group">
									<label><span>*</span>签发机关</label> 
									<input id="issueOrganization" name="issueorganization" tabIndex="1" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.issueorganization }"/>
								</div>
							</div>
						</div>
						<!-- end 签发机关 -->
						<div class="row">
							<!-- 是否有曾用名/曾有的或另有的国际(或公民身份) -->
							<div class="col-sm-5 padding-right-0 nameBeforeTop">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes">
											<input type="radio" name="hasothername" class="nameBefore" value="1"/>是
										</span>
										<span>
											<input type="radio" name="hasothername" class="nameBefore" value="2"/>否
										</span>
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 -->
							<div class="nameBeforeHide">
							    <div class="col-sm-10 padding-right-0 marginL">
									<div class="form-group" style="position:relative;">
										<label>姓/拼音</label> 
										<input id="otherFirstName" name="otherfirstname" type="text" class="form-control input-sm "  tabIndex="15" placeholder=" " value="${obj.applicant.otherfirstname }" />
										<input id="otherFirstNameEn" name="otherfirstnameen" type="text" style="position:absolute;top:32px;border:none;left:150px;" value="${obj.otherfirstnameen }"/>
									</div>
								</div>
							</div>
							<!-- 名/拼音 -->
							<div class="wordSpell">
								<div class="col-sm-10 padding-right-0 marginL" >
									<div class="form-group" style="position:relative;">
										<label>名/拼音</label> 
										<input id="otherLastName" name="otherlastname"  tabIndex="16" type="text" class="form-control input-sm otherLastName" placeholder=" " value="${obj.applicant.otherlastname }" />
										<input id="otherLastNameEn" name="otherlastnameen" type="text" style="position:absolute;top:32px;border:none;left:150px;" value="${obj.otherlastnameen }"/>
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 end -->
							<div class="col-sm-offset-1 padding-right-0 onceIDTop">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasothernationality" class="onceID" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasothernationality" class="onceID"  value="2" />否
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
					</div>

					<div class="col-sm-6 padding-right-0">
						<div class="row">
							<!-- 姓/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0 " >
								<div class="form-group" style="position:relative;">
									<label>
										<span>*</span>姓/拼音
									</label> 
									<input id="firstName" name="firstname" type="text" class="form-control input-sm "  tabIndex="2" placeholder=" " value="${obj.applicant.firstname }" />
									<input type="hidden" id="id" name="id" value="${obj.applicant.id }"/>
									<input id="firstNameEn" name="firstnameen" type="text" style="position:absolute;top:32px;border:none;left:150px;" value="${obj.firstnameen }"/>
								</div>
							</div>
						</div>
						<!-- end 姓/拼音 -->
						<div class="row">
							<!-- 名/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group" style="position:relative;">
									<label>
										<span>*</span>名/拼音
									</label> 
									<input id="lastName" name="lastname" type="text" class="form-control input-sm "  tabIndex="3" placeholder=" " value="${obj.applicant.lastname }" />
									<input id="lastNameEn" name="lastnameen" type="text" style="position:absolute;top:32px;border:none;left:150px;" value="${obj.lastnameen }"/>
								</div>
							</div>
						</div>
						<!-- end 名/拼音 -->
						<div class="row">
							<!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>手机号
									</label> 
									<input id="telephone" name="telephone" type="text" class="form-control input-sm"  tabIndex="4" placeholder=" " value="${obj.applicant.telephone }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱</label> 
									<input id="email" name="email" type="text" class="form-control input-sm" placeholder=" "  tabIndex="5" value="${obj.applicant.email }" />
								</div>
							</div>
						</div>
						<!-- end 手机号/邮箱 -->
						
						<div class="row">
							<!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>公民身份证</label> 
									<input id="cardId" name="cardId" type="text" class="form-control input-sm"  tabIndex="6" placeholder=" " value="${obj.applicant.cardId }" />
								</div>
							</div>
						</div>
						<!-- end 公民身份证 -->
						<div class="row">
							<!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别</label> 
									<select class="form-control input-sm selectHeight" id="sex" name="sex">
										<option value="男" ${obj.applicant.sex == "男"?"selected":"" }>男</option>
										<option value="女" ${obj.applicant.sex == "女"?"selected":"" }>女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label>民族</label> 
									<input id="nation" name="nation" type="text" class="form-control input-sm"  tabIndex="7" placeholder=" " value="${obj.applicant.nation }" />
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
									<label>住址</label> 
									<input id="address" name="address" type="text" class="form-control input-sm"  tabIndex="9" placeholder=" " value="${obj.applicant.address }" />
								</div>
							</div>
						</div>
						<!-- end 住宅 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>有效期限</label> 
									<input id="validStartDate" name="validstartdate" tabIndex="10" type="text" class="form-control input-sm" value="${obj.validstartdate }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label> &nbsp; &nbsp;</label> 
									<input id="validEndDate" name="validenddate" tabIndex="11" type="text" class="form-control input-sm" value="${obj.validenddate }">
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group" id="provinceDiv">
									<label>现居住地是否与身份证相同</label>
									<input type="hidden" name="cardprovince" id="cardProvince" value="${obj.applicant.cardprovince }"/>
									<input type="hidden" name="cardcity" id="cardCity" value="${obj.applicant.cardcity }"/>
									<input type="hidden" id="sameAddress" value=""/>
									<input class="nowProvince" type="checkbox" name="addressIssamewithcard" value="1" /> 
									<input id="province" name="province" type="text" class="form-control input-sm"  tabIndex="12" placeholder="省" value="${obj.applicant.province }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group" id="cityDiv">
									<label>现居住地址城市</label> 
									<input id="city" name="city" type="text" class="form-control input-sm" tabIndex="13" placeholder="市" value="${obj.applicant.city }" />
								</div>
							</div>
						</div>
						<!-- end 现居住地址省份/现居住地址城市 -->
						<div class="row">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址</label> 
									<input id="detailedAddress" name="detailedaddress" type="text"  tabIndex="14" class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间" value="${obj.applicant.detailedaddress }" />
								</div>
							</div>
						</div>
						<!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->
						
						<div class="row">
							<!-- 紧急联系人姓名/手机 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人姓名</label> 
									<input id="emergencyLinkman" name="emergencylinkman" type="text" class="form-control input-sm"  tabIndex="18" placeholder=" " value="${obj.applicant.emergencylinkman }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人手机</label> 
									<input id="emergencyTelephone" name="emergencytelephone"  tabIndex="19" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.emergencytelephone }" />
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
		var staffId = '${obj.staffId}';
		var passportId = '${obj.passportId}';
		var infoType = '${obj.infoType}';
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
	<script type="text/javascript" src="${base}/admin/bigCustomer/updateStaff.js"></script>
	<script type="text/javascript">
	
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	
		$(function(){
			var nation = '${obj.applicant.hasothernationality}';
			var otherName = '${obj.applicant.hasothername}';
			var address = '${obj.applicant.addressIssamewithcard}';
			$("input[name='hasothernationality'][value='"+nation+"']").attr("checked",'checked');
			$("input[name='hasothername'][value='"+otherName+"']").attr("checked",'checked');
			if(nation == 1){
				$(".nameBeforeTop").css('float','none');
				$(".nationalityHide").show();
				$(".onceIDTop").css({'float':'left','margin-left':'45px','padding':'0px'});
			}else {
				$(".nationalityHide").hide();
			}
			
			if(otherName == 1){
				$(".nameBeforeTop").css('float','none');
				$(".nameBeforeHide").show();
				$(".wordSpell").show();
				$(".onceIDTop").removeClass('col-sm-offset-1');
				$(".onceIDTop").css({"margin-left":"45px"});
			}else {
				
				$(".nameBeforeHide").hide();
				$(".wordSpell").hide();
			}
			
			if(address == 1){
				var boxObj = $("input:checkbox[name='addressIssamewithcard']").attr("checked",true);
			}else{
				var boxObj = $("input:checkbox[name='addressIssamewithcard']").attr("checked",false);
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
		function deleteApplicantFrontImg(){
			$('#cardFront').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
			if(userType == 2){
				$(".front").attr("class", "info-imgUpload front has-error");  
		        $(".help-blockFront").attr("data-bv-result","INVALID");  
			}
		}
		function deleteApplicantBackImg(){
			$('#cardBack').val("");
			$('#sqImgBack').attr('src', "");
			$("#uploadFileBack").siblings("i").css("display","none");
			if(userType == 2){
				$(".back").attr("class", "info-imgUpload back has-error");  
		        $(".help-blockBack").attr("data-bv-result","INVALID");  
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
