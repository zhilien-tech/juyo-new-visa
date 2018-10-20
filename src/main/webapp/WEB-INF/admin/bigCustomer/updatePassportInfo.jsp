<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>护照信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/appAddStaff.css?v='20180510'">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/public/css/passportInfo.css?v='20180510'">
	<style>
		.qrcode{
			margin: 20px auto;
			width: 150px;
			height: 150px;
			background: oldlace;
		}
		.datetimepicker {
			top: 494.563px!important;
		}
	</style>
</head>
<body>
	<div class="modal-content">
		<a id="toVisa" class="leftNav" onclick="visaBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第二步</i>
			<span></span>
		</a>
		<a id="toBase" class="rightNav" onclick="baseBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第四步</i>
			<span></span>
		</a>
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">护照信息</span> 
				<input autocomplete="new-password" id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input autocomplete="new-password" id="addBtn" type="button" onclick="save(1);" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
			</div>
			<div class="modal-body">
				<div class="dislogHide"></div>
				<div class="tab-content row">
					<div class="col-sm-12">
						<div class="info-imgUpload front has-error" id="borderColor"><!-- 护照 -->
							
							<div class="col-xs-6 mainWidth">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传护照</span>
									<input autocomplete="new-password" id="passportUrl" name="passporturl" type="hidden" value="${obj.passurl.url }"/>
									<input autocomplete="new-password" id="uploadFile" name="uploadfile" class="btn btn-primary btn-sm" type="file"  value="上传"/>
									<img style="top:-219px;" id="sqImg" alt="" src="${obj.passporturl }" >
									<!-- <i class="delete" onclick="deleteApplicantFrontImg();"></i> -->
								</div>
							</div>
						</div>
						</div><!-- end 护照 -->
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;margin:0px 0 0 8px !important;">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="passportUrl" data-bv-result="IVVALID" style="display: none;">护照必须上传</small>
						</div>
						
					</div>
						
					<div class="col-sm-7 padding-right-0">
						<div class="row"><!-- 出生日期/签发地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>护照号</label>
									<input autocomplete="new-password" type="hidden" name="staffid" value="${obj.staffid }"/>
									<input autocomplete="new-password" id="passport" name="passport" type="text" class="form-control input-sm" placeholder=" " value="${obj.passportinfo.passport}"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth" style="position:relative;">
									<label>
										<span>*</span>签发地点/拼音
									</label>
									<input autocomplete="new-password" id="issuedPlace" name="issuedplace"  type="text" class="form-control input-sm " placeholder=" " value="${obj.passportinfo.issuedplace }"/>
									<input autocomplete="new-password" id="issuedPlaceEn" name="issuedplaceen" type="text" style="position:absolute;top:32px;border:0px;left:80px;width:120px;" placeholder=" " value="${obj.passportinfo.issuedplaceen }"/>
								</div>
							</div>
						</div><!-- end 出生日期/签发地点 拼音 -->
						<div class="row"><!-- 签发日期/有效期至 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>签发日期
									</label>
									<input autocomplete="new-password" id="issuedDate" name="issueddate" type="text" class="form-control input-sm" placeholder=" " value="${obj.issueddate }"/>
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<select id="validType" name="validtype" class="form-control input-sm selectHeight" >
									<c:forEach var="map" items="${obj.passporttype}">
										<option value="${map.key}" ${map.key == obj.passportinfo.validtype?'selected':'' }>${map.value}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label>
										<span>*</span>有效期至
									</label>
									<input autocomplete="new-password" id="validEndDate" name="validenddate" type="text" class="form-control input-sm" placeholder=" " value="${obj.validenddate }"/>
								</div>
							</div>
						</div><!-- end 签发日期/有效期至 -->
						<div class="row"><!-- 签发机关 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>签发机关
									</label>
									<input autocomplete="new-password" id="issuedOrganization" name="issuedorganization" type="text" class="form-control input-sm" placeholder=" " value="${obj.passportinfo.issuedorganization }"/>
								</div>
							</div>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0" style="width: 230px;">
								<div class="form-group">
										<label>
												<span>*</span>Exit & Entry Administration
											</label>
									<input autocomplete="new-password" id="issuedOrganizationEn" name="issuedorganizationen" type="text" class="form-control input-sm" placeholder="Ministry of Public Security" value="${obj.passportinfo.issuedorganizationen }"/>
								</div>
							</div>
						</div><!-- end 签发机关 -->
					</div>	
					<div class="row">
						<div class="col-sm-5 col-sm-offset-1 padding-right-0">
							<div class="form-group">
								<label><span>*</span>是否丢失过护照号</label>
								<div>
									<input type="radio" name="islostpassport" value="1" class="is-duishihuzhao">是
									<input type="radio" name="islostpassport" value="2" class="is-duishihuzhao" style="margin-left: 20px;">否
								</div>
							</div>
						</div>
					</div>

					<div class="row jidehuzhao" style="display: none;">
						<div class="col-sm-5 col-sm-offset-1 padding-right-0" style="width: 210px;">
							<div class="form-group">
								<label><span>*</span>是否记得丢失的护照号</label>
								<div>
									<input type="radio" name="isrememberpassportnum" value="1" class="is-jideduishihuzhao ">是
									<input type="radio" name="isrememberpassportnum" value="2" class="is-jideduishihuzhao is-jideduishihuzhao-2" style="margin-left: 20px;">否
								</div>
							</div>
						</div>
						<div class="col-sm-5 col-sm-offset-1 padding-right-0 jideduishihuzhao-ipt" style="width: 230px;display: none;">
							<div class="form-group">
									<label>
										<span>*</span>护照号码
									</label>
								<input id="lostpassportnum" tabindex="1" name="lostpassportnum" type="text" class="form-control input-sm" placeholder="" value="${obj.passportinfo.lostpassportnum }"/>
							</div>
						</div>
					</div><!-- end 签发机关 -->
						
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId = '${obj.staffid}';
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
	<script type="text/javascript" src="${base}/admin/bigCustomer/passportInfo.js?v=<%=System.currentTimeMillis() %>"></script>
	
	
	<script type="text/javascript">
	
		if('${obj.passportinfo.issuedorganization}' == ''){
			$("#issuedOrganization").val("公安部出入境管理局")
		}
		if('${obj.passportinfo.issuedorganizationen}' == ''){
			$("#issuedOrganizationEn").val("MPS Exit & Entry Administration")
		}
		
		$('.is-duishihuzhao').change(function() {
			var v = $(this).val();
			if (v == 1) {
				$('.jidehuzhao').show();
			} else {
				$('.jidehuzhao').hide();
				$('.is-jideduishihuzhao-2').attr('checked','checked');
				$('.jideduishihuzhao-ipt').hide();
				$('.jideduishihuzhao-ipt').val('');
			}
		});

		$('.is-jideduishihuzhao').change(function() {
			var v = $(this).val();
			if (v == 1) {
				$('.jideduishihuzhao-ipt').show();
			} else {
				$('.jideduishihuzhao-ipt').hide();
				$('.jideduishihuzhao-ipt').val('');
			}
		});
		
		//回显radio处理
		//是否丢失护照
		var islostpassport = '${obj.passportinfo.islostpassport}';
		$("input[name='islostpassport'][value='" + islostpassport + "']").attr("checked", 'checked');
		if(islostpassport == 1){
			$('.jidehuzhao').show();
		}else{
			$('.jidehuzhao').hide();
		}
		//是否记得护照号
		var isrememberpassportnum = '${obj.passportinfo.isrememberpassportnum}';
		$("input[name='isrememberpassportnum'][value='" + isrememberpassportnum + "']").attr("checked", 'checked');
		if(isrememberpassportnum == 1){
			$('.jideduishihuzhao-ipt').show();
		}else{
			$('.jideduishihuzhao-ipt').hide();
		}
		
	
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
