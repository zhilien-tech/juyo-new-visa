<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
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
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
	<style>
		.info-imgUpload {width: 100%;}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">护照信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="col-sm-5 padding-right-0">
						<!-- <div class="info-QRcode"> --><!-- 二维码 -->
							
						<!-- </div> --><!-- end 二维码 -->
						
						<div class="info-imgUpload front"><!-- 护照 -->
							<div class="col-xs-6">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传护照</span>
									<input id="passportUrl" name="passportUrl" type="hidden" value="${obj.passport.passportUrl }"/>
									<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									<img id="sqImg" alt="" src="${obj.passport.passportUrl }" >
									<i class="delete" onclick="deleteApplicantFrontImg();"></i>
								</div>
							</div>
						</div>
						</div><!-- end 护照 -->
					</div>
						
					<div class="col-sm-7 padding-right-0">
						<div class="row"><!-- 类型/护照号 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>类型：</label>
									<input type="hidden" id="id" name="id" value="${obj.passport.id }"/>
									<input type="hidden" id="applicantId" name="applicantId" value="${obj.applicantId }"/>
									<input id="type" name="type" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.type }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>护照号：</label>
									<input id="passport" name="passport" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.passport }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 类型/护照号 -->
						<div class="row"><!-- 性别/ 出生地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别：</label>
									<input id="sex" name="sex" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.sex }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生地点/拼音：</label>
									<input id="birthAddress" name="birthAddress" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.birthAddress }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 性别/出生地点 拼音 -->
						<div class="row"><!-- 出生日期/签发地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期：</label>
									<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.birthday}"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发地点/拼音：</label>
									<input id="issuedPlace" name="issuedPlace" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedPlace }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 出生日期/签发地点 拼音 -->
						<div class="row"><!-- 签发日期/有效期至 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发日期：</label>
									<input id="issuedDate" name="issuedDate" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedDate }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<select id="validType" name="validType" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.passportType}">
										<option value="${map.key}" ${map.key == obj.passport.validType?'selected':'' }>${map.value}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>有效期至：</label>
									<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.validEndDate }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 签发日期/有效期至 -->
						<div class="row none"><!-- 签发机关 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发机关：</label>
									<input id="issuedOrganization" name="issuedOrganization" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganization }"/>
								</div>
							</div>
						</div><!-- end 签发机关 -->
						
						<div class="row none">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganization }"/>
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
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
		});
		//保存
		function save(){
			var passportInfo = $("#passportInfo").serialize();
			$.ajax({
				type: 'POST',
				data : passportInfo,
				url: '${base}/admin/orderJp/saveEditPassport',
				success :function(data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					parent.successCallBack(1);
					closeWindow();
				}
			});
		}
		
		
		//护照上传,扫描
		
		$('#uploadFile').change(function() {
			var layerIndex = layer.load(1, {
				shade : "#000"
			});
			$("#addBtn").attr('disabled', true);
			$("#updateBtn").attr('disabled', true);
			var file = this.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var dataUrl = e.target.result;
				var blob = dataURLtoBlob(dataUrl);
				var formData = new FormData();
				formData.append("image", blob, file.name);
				$.ajax({
					type : "POST",//提交类型  
					//dataType : "json",//返回结果格式  
					url : BASE_PATH + '/admin/orderJp/passportRecognition',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (true === obj.success) {
							$('#passportUrl').val(obj.url);
							$('#sqImg').attr('src', obj.url);
							$("#uploadFile").siblings("i").css("display","block");
							$('#type').val(obj.type);
							$('#passport').val(obj.num);
							$('#sex').val(obj.sex);
							$('#birthAddress').val(obj.birthCountry);
							$('#birthday').val(obj.birth);
							$('#issuedPlace').val(obj.visaCountry);
							$('#issuedDate').val(obj.issueDate);
							$('#validEndDate').val(obj.expiryDay);
						}
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
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
			return new Blob([ u8arr ], {
				type : mime
			});
		}
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		$(function(){
			var passport = $("#passportUrl").val();
			if(passport != ""){
				$("#uploadFile").siblings("i").css("display","block");
			}else{
				$("#uploadFile").siblings("i").css("display","none");
			}
			
		});
		
		function deleteApplicantFrontImg(){
			$('#passportUrl').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
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
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		$("#issuedDate").datetimepicker({
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
		$("#validEndDate").datetimepicker({
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
	</script>


</body>
</html>
