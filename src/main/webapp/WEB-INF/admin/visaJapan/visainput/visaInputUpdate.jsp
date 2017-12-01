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
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>

	<div class="modal-content">
		<form id="applicantvisaUpdateForm">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.applicantvisa.id}">
					
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<div class="upload-btn">
									<input id="license" name="license" type="hidden"/>
									<a href="javascript:;" class="uploadP">
										上传签证
										<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file" value="上传签证" />
									</a>
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<img id="visapic" src="${obj.applicantvisa.picUrl}" width="400px" height="200px">
						</div>
						<input type="hidden" name="picUrl" id="picUrl" value="${obj.applicantvisa.picUrl}">
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签证国家：</label> <input id="visaCountry"
									name="visaCountry" value="${obj.applicantvisa.visaCountry}" type="text"
									class="form-control input-sm" placeholder=" " />
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
								<input id="visaDate" onfocus="WdatePicker()"
									name="visaDate" value="<fmt:formatDate value="${obj.applicantvisa.visaDate}" pattern="yyyy-MM-dd" />" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>年限(年)：</label> <input id="visaYears"
									name="visaYears" value="${obj.applicantvisa.visaYears}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>有效期至：</label> 
								<input id="validDate" onfocus="WdatePicker()"
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

					<%-- <div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>上传签证图片URL：</label> <input id="picUrl"
									name="picUrl" value="${obj.applicantvisa.picUrl}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

					</div> --%>

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
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>

	<script type="text/javascript">
		var base = "${base}";

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
			$('#applicantvisaUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#applicantvisaUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
			
				$.ajax({
					type : 'POST',
					data : $("#applicantvisaUpdateForm").serialize(),
					url : '${base}/admin/visaJapan/visainput/update.html',
					success : function(data) {
						/* var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("编辑成功", "", 3000);
						parent.successCallBack();
						parent.layer.close(index); */
						closeWindow();
					},
					error : function(xhr) {
						layer.msg("编辑失败", "", 3000);
					}
				});
			}
		}
	
		//返回刷新页面 
		function closeWindow() {
			/* var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	parent.layer.close(index); */
        	var applicantId = '${obj.applicantvisa.applicantId}';
			window.location.href = '/admin/visaJapan/visaInput.html?applyid='+applicantId;
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
		            	if('200' === obj.status){
		            		$('#picUrl').val(obj.data);
		            		$('#visapic').attr('src',obj.data);
		            		//thisDiv.find('[name=fileName]').html(file.name);
		            	}
		            },  
		            error: function (obj) {
		            }
		    	});  // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});
	</script>


</body>
</html>

