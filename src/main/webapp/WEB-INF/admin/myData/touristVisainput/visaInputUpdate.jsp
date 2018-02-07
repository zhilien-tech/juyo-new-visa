<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/applicantvisa" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
<meta charset="UTF-8">
<title>更新</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
<style type="text/css">
#uploadFile { padding: 0; width: 125px !important; height: 30px;cursor:pointer;left:0 !important;}
.imgR { border:1px solid #d1d5dd; padding: 0 !important; width: 46%; margin-left: 2%; min-height: 200px }
</style>
</head>
<body>
	<div class="modal-content">
		<form id="applicantvisaUpdateForm">
			<div class="modal-header" style="position:fixed;top:0;left:0;width:100%;height:50px;line-height:50px; background:#FFF; z-index:9999; padding:0px 15px;">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" style="margin-top:10px;" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" style="margin-top:10px;" value="保存" />
			</div>
			<div class="modal-body" style="height:100%; margin-top:50px;">
				<div class="tab-content">
					
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<div class="upload-btn">
									<a href="javascript:;" class="uploadP">
										上传签证
										<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file" value="上传签证" />
									</a>
								</div>
							</div>
						</div>
						<div class="col-sm-5 imgR">
						<c:choose>
							<c:when test="${empty obj.applicantvisa.picUrl }">
								<img id="visapic" src=" " width="100%">
							</c:when>
							<c:otherwise>
								<img id="visapic" src="${obj.applicantvisa.picUrl}" width="100%">
							</c:otherwise>
						</c:choose>
						</div>
						<input type="hidden" name="picUrl" id="picUrl" value="${obj.applicantvisa.picUrl}">
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<input name="id" value="${obj.visainputId }" type="hidden"/>
								<label><span>*</span>签证国家：</label>
									<select id="visaCountry" name="visaCountry" class="form-control input-sm select2" multiple="multiple">
										<c:if test="${not empty obj.applicantvisa.visaCountry}">
											<option value="${obj.applicantvisa.visaCountry}" selected="selected">${obj.applicantvisa.visaCountry}</option>
										</c:if>
									</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发编号：</label> <input id="visaNum"
									name="visaNum" value="${obj.applicantvisa.visaNum}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发地：</label> <input id="visaAddress"
									name="visaAddress" value="${obj.applicantvisa.visaAddress}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签证类型：</label> 
								<select id="visaType" name="visaType"
									class="form-control input-sm" placeholder=" " >
									<c:forEach var="map" items="${obj.visadatatypeenum}">
										<c:choose>
											<c:when test="${obj.applicantvisa.visaType eq map.key}">
												<option value="${map.key}" selected="selected">${map.value}</option>
											</c:when>
											<c:otherwise>
												<option value="${map.key}">${map.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发时间：</label> 
								<input id="visaDate" 
									name="visaDate" value="<fmt:formatDate value="${obj.applicantvisa.visaDate}" pattern="yyyy-MM-dd" />" type="text"
									class="form-control input-sm autoCalcToDate" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>年限(年)：</label> <input id="visaYears"
									name="visaYears" value="${obj.applicantvisa.visaYears}" type="text"
									class="form-control input-sm autoCalcToDate" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>有效期至：</label> 
								<input id="validDate" 
									name="validDate" value="<fmt:formatDate value="${obj.applicantvisa.validDate}" pattern="yyyy-MM-dd" />" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>停留时间(天)：</label> <input id="stayDays"
									name="stayDays" value="${obj.applicantvisa.stayDays}" type="text"
									class="form-control input-sm" placeholder=" " />
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
	<!-- jQuery 2.2.3 -->
	<script
		src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript">
		var base = "${base}";
		var userId = '${obj.userId}';
		
		function initvalidate(){
			//校验
			$('#applicantvisaUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
				}
			});
		}
		
		//更新时刷新页面
		function update() {
			window.location.reload();
		}
		
		initvalidate();
		$('#applicantvisaUpdateForm').bootstrapValidator('validate');
		
		function save() {
			var updateForm = $("#applicantvisaUpdateForm").serialize();
			//alert($("#picUrl").val().length);
			console.log(updateForm);
			$('#applicantvisaUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#applicantvisaUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				layer.load(1);
				$.ajax({
					type : 'POST',
					data : updateForm,
					dataType:"json",
					url : '${base}/admin/myData/touristVisainput/updateVisainput.html',
					success : function(data) {
						layer.closeAll('loading');
						closeWindow();
					},
					error : function(xhr) {
						layer.closeAll('loading');
						layer.msg("编辑失败", "", 3000);
					}
				});
			}
		}
	
		//返回刷新页面 
		function closeWindow() {
			window.location.href = '/admin/myData/touristVisainput/visaInput.html?userId='+userId;
		}
		function dataURLtoBlob(dataurl) { 
		    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
		        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
		    while(n--){
		        u8arr[n] = bstr.charCodeAt(n);
		    }
		    return new Blob([u8arr], {type:mime});
		}
		
		$('#uploadFile').change(function(){
			layer.load(1);
			var file = this.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var dataUrl = e.target.result;
				var blob = dataURLtoBlob(dataUrl) ;
		    	var formData = new FormData();
		    	formData.append("uploadfile", blob,file.name);
		    	$.ajax({ 
		            type: "POST",//提交类型  
		            dataType: "json",//返回结果格式  
		            url: '${base}/admin/visaJapan/uploadVisaPic.html',//请求地址  
		            async: true  ,
		            processData: false, //当FormData在jquery中使用的时候需要设置此项
		            contentType: false ,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
		          	//请求数据  
		            data:formData ,
		            success: function (obj) {//请求成功后的函数  
		            	layer.closeAll('loading');
		            	if('200' === obj.status){
		            		$('#picUrl').val(obj.data);
		            		$('#visapic').attr('src',obj.data);
		            		//thisDiv.find('[name=fileName]').html(file.name);
		            	}
		            },  
		            error: function (obj) {
		            	layer.closeAll('loading');
		            }
		    	});  // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});
		
		$("#validDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		}).on("click",function(){  
		    $("#validDate").datetimepicker("setStartDate",$("#visaDate").val());  
		});
		$("#visaDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		//国家下拉
		$('#visaCountry').select2({
			ajax : {
				url : "/admin/visaJapan/visainput/getNationalSelect.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					return {
						searchstr : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function (obj) {
						//obj.id = obj.id; // replace pk with your identifier
						obj.id = obj.chinesename; // replace pk with your identifier
						obj.text = obj.chinesename;
						/*obj.text = obj.dictCode;*/
						return obj;
					});
					return {
						results : selectdata
					};
				},
				cache : false
			},
			//templateSelection: formatRepoSelection,
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false //设置必须存在的选项 才能选中
		});
		$('.autoCalcToDate').on('input',function(){
			var visaDate = $('#visaDate').val();
			if(!visaDate){
				return;
			}
			var visaYears = $('#visaYears').val();
			if(!visaYears){
				return;
			}
			$.ajax({
	            type: "POST",//提交类型  
	            dataType: "json",//返回结果格式  
	            url: '${base}/admin/visaJapan/visainput/autoCalcToDate.html',//请求地址  
	            async: true,
	          	//请求数据  
	            data:{
	            	visaDate:visaDate,
	            	visaYears:visaYears
	            },
	            success: function (data) {//请求成功后的函数  
	            	$('#validDate').val(data);
	            },  
	            error: function (obj) {
	            }
	    	});  // end of ajaxSubmit
		});
	</script>


</body>
</html>

