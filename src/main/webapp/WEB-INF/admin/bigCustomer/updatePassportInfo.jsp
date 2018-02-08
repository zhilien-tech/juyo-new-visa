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
	<style>
	img[src=""],img:not([src]) { opacity:0;}
	input[type="file"] { z-index:99999;}
	#sqImg { z-index:999999;}
	#sqImgBack { z-index:999999;}
	.modal-content { position:relative;}
	.info-imgUpload {width: 98%;}
	.col-sm-offset-1 { margin-left:3% !important;}
	.groupWidth { width:215px;}
	.NoInfo { width:101.5%; height:30px; transtion:height 1s; -webkit-transtion:height 1s; -moz-transtion:height 1s; }
	.ipt-info { display:none; }
    .Unqualified, .qualified  { margin-right:10px; }
    .delete { z-index:1000000;}
    /*弹框头部固定*/
    .modal-header { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:10000000; padding:0px 15px;}
    .btn-margin { margin-top:10px;}
    .modal-body { margin-top:50px; height:100%; padding:15px 37px 15px 40px;}
    #sqImg { width:332px;}
    /*左右导航样式*/
    .rightNav { position:fixed;top:15px;right:0;z-index:999; width:40px;height:100%; cursor:pointer;}
	.rightNav span { width: 24px; height: 24px; position: absolute;top:50%; border-left: 4px solid #999;  border-bottom: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
    .leftNav { position:fixed;top:15px;left:4px;z-index:999; width:40px;height:100%; cursor:pointer;}
	.leftNav span { width: 24px; height: 24px; position: absolute;top:50%;margin-left:10px; border-right: 4px solid #999;  border-top: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
	.info-QRcode { width:153px;}
	.mainWidth,#uploadFile { width:100% !important;}
	</style>
</head>
<body>
	<div class="modal-content">
		<a id="toVisa" class="rightNav" onclick="visaBtn();">
			<span></span>
		</a>
		<a id="toBase" class="leftNav" onclick="baseBtn();">
			<span></span>
		</a>
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">护照信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save(1);" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="col-sm-5 padding-right-0">
						
						<div class="info-imgUpload front has-error" id="borderColor"><!-- 护照 -->
							<div class="col-xs-6 mainWidth">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传护照</span>
									<input id="passportUrl" name="passportUrl" type="hidden" value="${obj.passport.passportUrl }"/>
									<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="上传"/>
									<img id="sqImg" alt="" src="${obj.passport.passporturl }" >
									<i class="delete" onclick="deleteApplicantFrontImg();"></i>
								</div>
							</div>
						</div>
						</div><!-- end 护照 -->
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;margin:-20px 0 0 8px !important;">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="passportUrl" data-bv-result="IVVALID" style="display: none;">护照必须上传</small>
						</div>
						
					</div>
						
					<div class="col-sm-7 padding-right-0">
						<div class="row"><!-- 类型/护照号 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>类型</label>
									<input id="id" name="id" type="hidden" value="${obj.passport.possportid }"/>
									<input id="OCRline1" name="OCRline1" type="hidden" value="">
									<input id="OCRline2" name="OCRline2" type="hidden" value="">
									<input name="userType" type="hidden" value="${obj.userType }"/>
									<input id="staffId" name="staffId" type="hidden" value="${obj.passport.staffid }"/>
									<input id="type" name="type" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.type }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label><span>*</span>护照号</label>
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
									<input id="sexEn" name="sexEn" class="form-control input-sm" type="text" value="${obj.passport.sexEn }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
							 	<div class="form-group groupWidth" style="position:relative;">
									<label><span>*</span>出生地点/拼音</label>
									<input id="birthAddress" name="birthAddress"  type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.birthAddress }"/>
									<input id="birthAddressEn" name="birthAddressEn" style="position:absolute;top:32px;border:0px;left:80px; width:120px;" type="text"  placeholder=" " value="${obj.passport.birthAddressEn }"/>
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
									<input id="issuedPlace" name="issuedPlace"  type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.issuedPlace }"/>
									<input id="issuedPlaceEn" name="issuedPlaceEn" type="text" style="position:absolute;top:32px;border:0px;left:80px;width:120px;" placeholder=" " value="${obj.passport.issuedPlaceEn }"/>
								</div>
							</div>
						</div><!-- end 出生日期/签发地点 拼音 -->
						<div class="row"><!-- 签发日期/有效期至 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>签发日期
									</label>
									<input id="issuedDate" name="issuedDate" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedDate }"/>
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<select id="validType" name="validType" class="form-control input-sm selectHeight" >
									<c:forEach var="map" items="${obj.passportType}">
										<option value="${map.key}" ${map.key == obj.passport.validType?'selected':'' }>${map.value}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label>
										<span>*</span>有效期至
									</label>
									<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.validEndDate }"/>
								</div>
							</div>
						</div><!-- end 签发日期/有效期至 -->
						<div class="row none"><!-- 签发机关 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>签发机关
									</label>
									<input id="issuedOrganization" name="issuedOrganization" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganization }"/>
								</div>
							</div>
						</div><!-- end 签发机关 -->
						
						<div class="row none">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<input id="issuedOrganizationEn" name="issuedOrganizationEn" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganizationEn }"/>
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
		var infoType = '${obj.infoType}';
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
		$("#issuedDate").datetimepicker({
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
	</script>


</body>
</html>
