<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>添加基本信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/appAddStaff.css">
</head>
<body>
	<div class="modal-content">
		<!-- 跳转到护照页 -->
		<a id="toPassport" class="rightNav" onclick="toPassport();"> <span></span>
		</a>
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">添加基本信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" onclick="saveApplicant(1);" />
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<div></div>
						<!-- start 身份证 正面 -->
						<div class="info-imgUpload front">
							<div class="col-xs-6 mainWidth">
								<div class="form-group">
									<div class="cardFront-div">
										<span>点击上传身份证正面</span> 
										<input id="cardFront" name="cardFront" type="hidden" />
										<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file" value="上传" /> 
										<img id="sqImg" alt="" src=""> 
										<i class="delete" style="display: none;" onclick="deleteApplicantFrontImg();"></i>
									</div>
								</div>
							</div>
						</div>
						<!-- end 身份证 正面 -->

						<!-- start 身份证 反面 -->
						<div class="info-imgUpload back">
							<div class="col-xs-6 mainWidth">
								<div class="form-group">
									<div class="cardFront-div">
										<span>点击上传身份证背面</span> 
										<input id="cardBack" name="cardBack" type="hidden" /> 
										<input id="uploadFileBack" name="uploadFile" class="btn btn-primary btn-sm" type="file" value="上传" /> 
										<img id="sqImgBack" alt="" src=""> 
										<i class="delete" style="display: none;" onclick="deleteApplicantBackImg();"></i>
									</div>
								</div>
							</div>
						</div>
						<!-- end 身份证 反面 -->

						<div class="row" style="margin-top: 27px;">
							<!-- 签发机关 -->
							<div class="col-sm-10 padding-right-0 marginL">
								<div class="form-group">
									<label>签发机关</label> 
									<input id="issueOrganization" name="issueOrganization" type="text" class="form-control input-sm" placeholder=" " />
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
											<input type="radio" name="hasOtherName" class="nameBefore" value="1" />是
										</span> 
										<span> 
											<input type="radio" name="hasOtherName" class="nameBefore" checked value="2" />否
										</span>
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 -->
							<div class="nameBeforeHide">
								<div class="col-sm-10 padding-right-0 marginL">
									<div class="form-group" style="position: relative;">
										<label>姓/拼音</label> 
										<input id="otherFirstName" name="otherFirstName" type="text" class="form-control input-sm " placeholder=" " value="" /> 
										<input id="otherFirstNameEn" name="otherFirstNameEn" type="text" style="position: absolute; top: 32px; border: none; left: 150px;" value="" />
									</div>
								</div>
							</div>
							<div class="wordSpell">
								<div class="col-sm-10 padding-right-0 marginL">
									<div class="form-group" style="position: relative;">
										<label>名/拼音</label> 
										<input id="otherLastName" name="otherLastName" type="text" class="form-control input-sm" placeholder=" " value="" /> 
										<input id="otherLastNameEn" name="otherLastNameEn" type="text" style="position: absolute; top: 32px; border: none; left: 150px;" value="" />
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
											<input type="radio" name="hasOtherNationality" class="onceID" checked value="2" />否
										</span>
									</div>
								</div>
							</div>
							<!-- 曾用国籍 -->
							<div class="col-sm-5 padding-right-0 nationalityHide">
								<div class="form-group" id="nationalityDiv">
									<label>国籍</label> 
									<input id="nationality" name="nationality" type="text" class="form-control input-sm" />
								</div>
							</div>
						</div>
					</div>

					<div class="col-sm-6 padding-right-0">
						<div class="row">
							<!-- 姓/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group" style="position: relative;">
									<label><span>*</span>姓/拼音</label> 
									<input id="firstName" name="firstName" type="text" class="form-control input-sm req " placeholder=" " /> 
									<input id="firstNameEn" name="firstNameEn" type="text" style="position: absolute; top: 32px; border: none; left: 150px;" value="" />
								</div>
							</div>
						</div>
						<!-- end 姓/拼音 -->
						<div class="row">
							<!-- 名/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0 ">
								<div class="form-group" style="position: relative;">
									<label>
										<span>*</span>名/拼音
									</label> 
									<input id="lastName" name="lastName" type="text" class="form-control input-sm " placeholder=" " /> 
									<input id="lastNameEn"  name="lastNameEn" type="text" style="position: absolute; top: 32px; border: none; left: 150px;" value="" />
									<!-- <i class="bulb"></i> -->
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
									<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱</label> 
									<input id="email" name="email" type="text" class="form-control input-sm" />
								</div>
							</div>
						</div>
						<!-- end 手机号/邮箱 -->
						<div class="row">
							<!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>公民身份证</label> 
									<input id="cardId" name="cardId" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<!-- end 公民身份证 -->
						<div class="row">
							<!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>性别
									</label>
									<select class="form-control input-sm selectHeight" id="sex" name="sex">
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label>民族</label> 
									<input id="nation" name="nation" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-5 padding-right-0">
								<div class="form-group">
									<label>出生日期</label> 
									<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<!-- end 姓名/民族 -->
						<div class="row">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>住址</label> 
									<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<!-- end 住宅 -->
						<div class="row">
							<!-- 有效期限 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>有效期限</label> 
									<input id="validStartDate" name="validStartDate" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label> &nbsp; &nbsp;</label> 
									<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<!-- end 有效期限 -->
						<div class="row">
							<!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地是否与身份证相同</label>
									<input name="addressIsSameWithCard"  type="checkbox" class="nowProvince" value="1" />
									<input id="cardProvince" name="cardProvince" type="hidden" /> 
									<input id="cardCity" name="cardCity" type="hidden" /> 
									<input id="province" name="province" type="text" class="form-control input-sm" placeholder="省" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地址城市</label> 
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder="市" />
								</div>
							</div>
						</div>
						<!-- end 现居住地址省份/现居住地址城市 -->
						<div class="row">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址</label> 
									<input id="detailedAddress" name="detailedAddress" type="text" class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间" />
								</div>
							</div>
						</div>
						<!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->
						
						<div class="row">
							<!-- 紧急联系人姓名/手机 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人姓名</label> 
									<input id="emergencyLinkman" name="emergencyLinkman" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.emergencyLinkman }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人手机</label> 
									<input id="emergencyTelephone" name="emergencyTelephone" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.emergencyTelephone }" />
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
	<script type="text/javascript" src="${base}/admin/bigCustomer/addStaff.js"></script>
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>

	<script type="text/javascript">
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		function deleteApplicantFrontImg(){
			$('#cardFront').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
		}
		function deleteApplicantBackImg(){
			$('#cardBack').val("");
			$('#sqImgBack').attr('src', "");
			$("#uploadFileBack").siblings("i").css("display","none");
		}
		
		$("#validStartDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		$("#validEndDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
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
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		
		//点击右侧箭头，跳转到护照信息
		function toPassport(){
			saveApplicant(2);
		}
		function successCallBack(status){
			if(status == 1){
				parent.successCallBack(3);
			}
			closeWindow();
		}
		function cancelCallBack(status){
			closeWindow();
		}
	</script>
</body>
</html>
