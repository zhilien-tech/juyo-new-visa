
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/visaJapan/visainput" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<title>添加</title>
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
<style>
    .modal-header { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:9999; padding:0px 15px;}
    .btn-margin { margin-top:10px;}
    #uploadFile { padding: 0; width: 125px !important; height: 30px;cursor:pointer;left:0 !important;}
    .modal-body { background-color:#FFF !important; margin-top:50px; height:100%;}  
    .imgR { border:1px solid #d1d5dd; padding: 0 !important; width: 46%; margin-left: 2%; min-height: 200px }
</style>
</head>
<body>
	<div class="modal-content">
		<form id="applicantvisaAddForm">
			<div class="modal-header">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin"
					data-dismiss="modal" value="取消" /> <input id="addBtn" type="button"
					onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
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
						<div class="col-sm-5 imgR">
							<img id="visapic" src=" " width="100%">
						</div>
						<input type="hidden" name="picUrl" id="picUrl">
					</div>
					<!-- <div class="row">
					</div> -->
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签证国家：</label> <!-- <input id="visaCountry"
						name="visaCountry" type="text" class="form-control input-sm"
						placeholder=" " /> -->
								<select id="visaCountry" name="visaCountry" class="form-control input-sm select2" multiple="multiple">
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<input type="hidden" name="userId" value="${obj.userId }"/>
								<label><span>*</span>签发编号：</label> <input id="visaNum"
						name="visaNum" type="text" class="form-control input-sm"
						placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发地：</label> <input id="visaAddress"
						name="visaAddress" type="text" class="form-control input-sm"
						placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签证类型：</label> 
								<select id="visaType"
										name="visaType" type="text" class="form-control input-sm"
										placeholder="" >
									<c:forEach var="map" items="${obj.visadatatypeenum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>签发时间：</label> 
								<input id="visaDate" name="visaDate"  type="text" class="form-control input-sm autoCalcToDate" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>年限(年)：</label> 
								<input id="visaYears" name="visaYears" maxlength="4" type="text" class="form-control input-sm autoCalcToDate" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>有效期至：</label> 
								<input id="validDate" name="validDate"  type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>停留时间(天)：</label> 
								<input id="stayDays" name="stayDays" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<!-- <div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>上传签证图片URL：</label> <input id="picUrl"
						name="picUrl" type="text" class="form-control input-sm"
						placeholder=" " />
							</div>
						</div>

					</div> -->


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
		
		/*保存页面*/ 
		function save() {
			//初始化验证插件
			$('#applicantvisaAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#applicantvisaAddForm").data('bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				layer.load(1);
				$.ajax({
					type : 'POST',
					data : $("#applicantvisaAddForm").serialize(),
					url : '${base}/admin/myData/touristVisainput/add.html',
					success : function(data) {
						layer.closeAll('loading');
						window.location.href = '/admin/myData/touristVisainput/visaInput.html?userId='+userId;
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
					}
				});
			}
		}
		
		//返回 
		function closeWindow() {
			/* var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index); */
			var applicantId = $('#applicantId').val();
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
			pickerPosition:"bottom-right",//显示位置
			minView: "month"//只显示年月日
		}).on("click",function(){  
		    $("#validDate").datetimepicker("setStartDate",$("#visaDate").val());  
		});
		$("#visaDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-right",//显示位置
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
