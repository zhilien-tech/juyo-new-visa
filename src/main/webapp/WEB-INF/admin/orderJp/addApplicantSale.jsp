<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>添加申请人</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
</head>
<body>
	<div class="modal-content">
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">添加申请人</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right" value="保存" onclick="saveApplicant();"/>
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<!-- <div class="info-QRcode"> --><!-- 身份证 正面 -->
							
						<!-- </div> --><!-- end 身份证 正面 -->
						
						
						<div class="info-imgUpload front"><!-- 身份证 正面 -->
							<div class="col-xs-6">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传身份证正面</span>
									<input id="cardFront" name="cardFront" type="hidden"/>
									<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									<img id="sqImg" alt="" src="" >
									<i class="delete" style="display:none;" onclick="deleteApplicantFrontImg(${obj.orderid});"></i>
								</div>
							</div>
						</div>
						</div><!-- end 身份证 正面 -->
						
						<div class="info-imgUpload back"><!-- 身份证 反面 -->
							<div class="col-xs-6">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传身份证背面</span>
									<input id="cardBack" name="cardBack" type="hidden"/>
									<input id="uploadFileBack" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									<img id="sqImgBack" alt="" src="" >
									<i class="delete" style="display:none;" onclick="deleteApplicantBackImg(${obj.orderid});"></i>
								</div>
							</div>
						</div>

						</div><!-- end 身份证 反面 -->
						
						<div class="row"><!-- 签发机关 -->
							<div class="col-sm-11 padding-right-0">
								<div class="form-group">
									<label>签发机关：</label>
									<input id="issueOrganization" name="issueOrganization" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 签发机关 -->
					</div>
						
					<div class="col-sm-6 padding-right-0">
						<div class="row"><!-- 姓/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>姓/拼音：</label>
									<input id="firstName" name="firstName" type="text" class="form-control input-sm req" placeholder=" " />
									<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 姓/拼音 -->
						<div class="row"><!-- 名/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>名/拼音：</label>
									<input id="lastName" name="lastName" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 名/拼音 -->	
						<div class="row"><!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>手机号：</label>
									<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱：</label>
									<input id="email" name="email" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 手机号/邮箱 -->
						<div class="row"><!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地址省份：</label>
									<input id="province" name="province" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地址城市：</label>
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 现居住地址省份/现居住地址城市 -->
						<div class="row"><!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址/区(县)/街道/小区(社区)/楼号/单元/房间：</label>
									<input id="detailedAddress" name="detailedAddress" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->
						<div class="row"><!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>公民身份证：</label>
									<input id="cardId" name="cardId" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 公民身份证 -->
						<div class="row"><!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别：</label>
									<input id="sex" name="sex" type="text" class="form-control input-sm" placeholder=" " value=""/>
									<%-- <select class="form-control input-sm selectHeight" id="sex" name="sex">
										<c:forEach var="map" items="${obj.MOrFEnum}">
												<option value="${map.key}" >${map.value}</option>
											</c:forEach>
									</select> --%>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label>民族：</label>
									<input id="nation" name="nation" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5 padding-right-0">
								<div class="form-group">
									<label>出生日期：</label>
									<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 姓名/民族 -->
						<div class="row"><!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>住址：</label>
									<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 住宅 -->						
						<div class="row"><!-- 有效期限 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>有效期限：</label>
									<input id="validStartDate" name="validStartDate" type="text" class="form-control input-sm" placeholder=" "  />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label> &nbsp; &nbsp;</label>
									<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" "  />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 有效期限 -->
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
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		$(function(){
			//校验
			$('#applicantInfo').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {

					firstName : {
						validators : {
							notEmpty : {
								message : '姓不能为空'
							}
						}
					},
					lastName : {
						validators : {
							notEmpty : {
								message : '名不能为空'
							}
						}
					},
					telephone : {
						validators : {
							regexp: {
		                	 	regexp: /^[1][34578][0-9]{9}$/,
		                        message: '电话号格式错误'
		                    }
						}
					},
					email : {
						validators : {
							regexp: {
		                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
		                        message: '邮箱格式错误'
		                    }
						}
					}
				}
			});
			$('#applicantInfo').bootstrapValidator('validate');
			
		});
		function saveApplicant(){
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#applicantInfo").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()){
				//获取必填项信息
				var firstName = $("#firstName").val();
				if (firstName == "") {
					layer.msg('姓不能为空');
					return;
				}
				var lastName = $("#lastName").val();
				if (lastName == "") {
					layer.msg('名不能为空');
					return;
				}
				
				
				var applicantInfo = $("#applicantInfo").serialize();
				
				$.ajax({
					type : 'POST',
					data : applicantInfo,
					url : '${base}/admin/orderJp/saveAddApplicant',
					success : function(data) {
						var applicantIdParent = window.parent.document.getElementById("appId").value;
						applicantIdParent += data.id +",";
						window.parent.document.getElementById("appId").value = applicantIdParent;
						layer.closeAll('loading');
						parent.successCallBack(3,data);
						closeWindow();
					},
					error : function() {
						alert("error");
					}
				}); 
			}
			
		}
		
		
		//正面上传,扫描
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
					url : BASE_PATH + '/admin/orderJp/IDCardRecognition',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (true === obj.success) {
							$('#cardFront').val(obj.url);
							$('#sqImg').attr('src', obj.url);
							$("#uploadFile").siblings("i").css("display","block");
							$('#address').val(obj.address);
							$('#nation').val(obj.nationality);
							$('#cardId').val(obj.num);
							$('#province').val(obj.province);
							$('#city').val(obj.city);
							$('#birthday').val(obj.birth);
							$('#sex').val(obj.sex);
						}
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(XMLHttpRequest.status);
		                alert(XMLHttpRequest.readyState);
		                alert(textStatus);
						layer.close(layerIndex);
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					}
				}); // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});
		
		//背面上传,扫描
		$('#uploadFileBack').change(function() {
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
					url : BASE_PATH + '/admin/orderJp/IDCardRecognitionBack',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (true === obj.success) {
							$('#cardBack').val(obj.url);
							$('#sqImgBack').attr('src', obj.url);
							$("#uploadFileBack").siblings("i").css("display","block");
							$('#validStartDate').val(obj.starttime);
							$('#validEndDate').val(obj.endtime);
							$('#issueOrganization').val(obj.issue);
						}
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(XMLHttpRequest.status);
		                alert(XMLHttpRequest.readyState);
		                alert(textStatus);
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
		
		/* $(function(){
			$("#uploadFile").click(function(){//上传身份证正面  add 删除按钮
				$(this).siblings("i").css("display","block");
			});
			$("#uploadFileBack").click(function(){//上传身份证反面  add 删除按钮
				$(this).siblings("i").css("display","block");
			});
		}); */
		
		function deleteApplicantFrontImg(id){
			$('#cardFront').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
		}
		function deleteApplicantBackImg(id){
			$('#cardBack').val("");
			$('#sqImgBack').attr('src', "");
			$("#uploadFileBack").siblings("i").css("display","none");
		}
		
		$("#birthday").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		$("#validStartDate").datetimepicker({
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
			todayHighlight: true,
			startView: 4,//从年开始选择
			forceParse: 0,
	        showMeridian: false,
			pickerPosition:"top-left",
			minView: "month"//只显示年月日
		});
		
	</script>


</body>
</html>
