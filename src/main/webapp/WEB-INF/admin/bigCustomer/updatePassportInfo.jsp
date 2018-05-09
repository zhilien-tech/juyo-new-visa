<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>护照信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/appAddStaff.css">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/public/css/passportInfo.css">
</head>
<body>
	<div class="modal-content">
		<a id="toVisa" class="leftNav" onclick="visaBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第一步</i>
			<span></span>
		</a>
		<a id="toBase" class="rightNav" onclick="baseBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第三步</i>
			<span></span>
		</a>
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">护照信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save(1);" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
			</div>
			<div class="modal-body">
				<div class="dislogHide"></div>
				<div class="tab-content row">
					<div class="col-sm-5 padding-right-0">
						
						<div class="info-imgUpload front has-error" id="borderColor"><!-- 护照 -->
							<div class="col-xs-6 mainWidth">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传护照</span>
									<input id="passportUrl" name="passporturl" type="hidden" value="${obj.passurl.url }"/>
									<input id="uploadFile" name="uploadfile" class="btn btn-primary btn-sm" type="file"  value="上传"/>
									<img id="sqImg" alt="" src="${obj.passurl.url }" >
									<i class="delete" onclick="deleteApplicantFrontImg();"></i>
								</div>
							</div>
						</div>
						</div><!-- end 护照 -->
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;margin:0px 0 0 8px !important;">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="passportUrl" data-bv-result="IVVALID" style="display: none;">护照必须上传</small>
						</div>
						
					</div>
						
					<div class="col-sm-7 padding-right-0">
						<div class="row">
						<!-- 姓/拼音 -->
							<div class="col-sm-10 col-sm-offset-1 padding-right-0">
									<div class="form-group" style="position:relative;">
									<label><span>*</span>姓/拼音</label> <input id="firstName"
										name="firstname" type="text" class="form-control input-sm " tabIndex="1"
										placeholder=" " value="${obj.passport.firstname }" />
										<input type="text" id="firstNameEn" style="position:absolute;top:32px;border:none;left:150px;"  name="firstnameen" value="${obj.firstnameen }"/>
									<!-- <i class="bulb"></i> -->
								</div>
									<!-- <i class="bulb"></i> -->
							</div>
						</div>
						<!-- end 姓/拼音 -->
					<div class="row">
							<!-- 名/拼音 -->
							<div class="col-sm-10 col-sm-offset-1 padding-right-0">
								<div class="form-group" style="position:relative;">
									<label><span>*</span>名/拼音</label> <input id="lastName"
										name="lastname" type="text" class="form-control input-sm" tabIndex="2"
										placeholder=" " value="${obj.passport.lastname }" />
										<input type="text" id="lastNameEn" style="position:absolute;top:32px;border:none;left:150px;" name="lastnameen" value="${obj.lastnameen }"/>

									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 名/拼音 -->
						<div class="row"><!-- 类型/护照号 -->
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label><span>*</span>护照号</label>
									<input id="id" name="id" type="hidden" value="${obj.passport.possportid }"/>
									<input id="orderid" name="orderid" type="hidden" value="${obj.orderid }"/>
									<input id="OCRline1" name="OCRline1" type="hidden" value="">
									<input id="OCRline2" name="OCRline2" type="hidden" value="">
									<input name="userType" type="hidden" value="${obj.usertype }"/>
									<input id="staffId" name="staffId" type="hidden" value="${obj.passport.staffid }"/>
									<input id="passport" name="passport" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.passport }"/>
								</div>
							</div>
						</div><!-- end 类型/护照号 -->
						<div class="row"><!-- 性别/ 出生地点 拼音 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0 ">
								<div class="form-group">
									<label><span>*</span>性别</label>
									<select class="form-control input-sm selectHeight" id="sex" name="sex">
										<option value="男" ${obj.passport.sex == "男"?"selected":"" }>男</option>
										<option value="女" ${obj.passport.sex == "女"?"selected":"" }>女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<input id="sexEn" name="sexen" class="form-control input-sm" type="text" value="${obj.passport.sexen }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
							 	<div class="form-group groupWidth" style="position:relative;">
									<label><span>*</span>出生地点/拼音</label>
									<input id="birthAddress" name="birthaddress"  type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.birthaddress }"/>
									<input id="birthAddressEn" name="birthaddressen" style="position:absolute;top:32px;border:0px;left:80px; width:120px;" type="text"  placeholder=" " value="${obj.passport.birthaddressen }"/>
								</div>
							</div>
						</div><!-- end 性别/出生地点 拼音 -->
						<div class="row"><!-- 出生日期/签发地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期</label>
									<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.birthday}"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth" style="position:relative;">
									<label>
										<span>*</span>签发地点/拼音
									</label>
									<input id="issuedPlace" name="issuedplace"  type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.issuedplace }"/>
									<input id="issuedPlaceEn" name="issuedplaceen" type="text" style="position:absolute;top:32px;border:0px;left:80px;width:120px;" placeholder=" " value="${obj.passport.issuedplaceen }"/>
								</div>
							</div>
						</div><!-- end 出生日期/签发地点 拼音 -->
						<div class="row"><!-- 签发日期/有效期至 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>签发日期
									</label>
									<input id="issuedDate" name="issueddate" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issueddate }"/>
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<select id="validType" name="validtype" class="form-control input-sm selectHeight" >
									<c:forEach var="map" items="${obj.passporttype}">
										<option value="${map.key}" ${map.key == obj.passport.validtype?'selected':'' }>${map.value}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label>
										<span>*</span>有效期至
									</label>
									<input id="validEndDate" name="validenddate" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.validenddate }"/>
								</div>
							</div>
						</div><!-- end 签发日期/有效期至 -->
						<div class="row none"><!-- 签发机关 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>签发机关
									</label>
									<input id="issuedOrganization" name="issuedorganization" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedorganization }"/>
								</div>
							</div>
						</div><!-- end 签发机关 -->
						
						<div class="row none">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<input id="issuedOrganizationEn" name="issuedorganizationen" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedorganizationen }"/>
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
		var staffId = '${obj.passport.staffId}';
		var infoType = '${obj.infotype}';
		var isDisable = '${obj.isDisable}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
	
	<!-- 本页面js -->
	<script type="text/javascript" src="${base}/admin/bigCustomer/passportInfo.js"></script>
	
	
	<script type="text/javascript">
		
		$(function(){
			//页面不可编辑
			if(isDisable == 1){
				$(".modal-body").attr('readonly', true);
				$(".dislogHide").show();
				$("#addBtn").hide();
			}
		});
		
	    var firstname = '${obj.passport.firstname }';
	    var firstnameen = '${obj.firstnameen }';
	    var lastname = '${obj.passport.lastname }';
	    var lastnameen = '${obj.lastnameen }';
	    if(firstnameen == "" && firstname != ""){
			$('#firstNameEn').val("/"+getPinYinStr(firstname));
	    }
	    if(lastnameen == "" && lastname != ""){
			$('#lastNameEn').val("/"+getPinYinStr(lastname));
	    }
	
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
		$("#issuedDate").datetimepicker({
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
	</script>


</body>
</html>
