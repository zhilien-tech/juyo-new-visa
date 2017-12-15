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
		.col-sm-offset-1 { margin-left:3% !important;}
		.groupWidth { width:215px;}
		.NoInfo {
	width:100%;
	height:30px;
	margin-left:3.5%;
	transtion:height 1s;
	-webkit-transtion:height 1s;
	-moz-transtion:height 1s;
}
.ipt-info {
	display:none;
}
.Unqualified, .qualified  {
	margin-right:10px;
}
	</style>
</head>
<body>
	<div class="modal-content">
	<div style="position:absolute;top:40%;right:5%;z-index:999;">
			<a id="toVisa" onclick="visaBtn();">
				<h1>></h1>
			</a>
		</div>
		<div style="position:absolute;top:40%;left:5%;z-index:999;">
			<a id="toApply" onclick="applyBtn();">
				<h1><</h1>
			</a>
		</div>
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">护照信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save(1);" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
				<c:choose>
						<c:when test="${obj.orderStatus > 4 && obj.orderStatus < 9}">  
					<input id="unqualifiedBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right Unqualified" value="不合格" />
				<input id="qualifiedBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right qualified" value="合格" />
						</c:when>
						<c:otherwise> 
						</c:otherwise>
					</c:choose>
			</div>
			<div class="modal-body">
			<div class="ipt-info">
					<input id="passRemark" name="passRemark" type="text" value="${obj.unqualified.passRemark }" class="NoInfo" />
				</div>
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
									<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
									<input id="type" name="type" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.type }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label><span>*</span>护照号：</label>
									<input id="passport" name="passport" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.passport }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 类型/护照号 -->
						<div class="row"><!-- 性别/ 出生地点 拼音 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0 ">
								<div class="form-group">
									<label><span>*</span>性别：</label>
									<%-- <input id="sex" name="sex" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.sex }"/> --%>
									<select class="form-control input-sm selectHeight" id="sex" name="sex">
										<%-- <c:forEach var="map" items="${obj.boyOrGirlEnum}">
												<option value="${map.key}" ${map.key==obj.passport.sex?'selected':''}>${map.value}</option>
											</c:forEach> --%>
											<option value="男" ${obj.passport.sex == "男"?"selected":"" }>男</option>
										<option value="女" ${obj.passport.sex == "女"?"selected":"" }>女</option>
									</select>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<input id="sexEn" class="form-control input-sm" name="sexEn" type="text" value="${obj.passport.sexEn }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label><span>*</span>出生地点/拼音：</label>
									<input id="birthAddress" name="birthAddress" style="position:relative;" type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.birthAddress }"/>
									<input id="birthAddressEn" name="birthAddressEn" style="position:absolute;top:45px;border:0px;left:80px;" type="text"  placeholder=" " value="${obj.passport.birthAddressEn }"/>
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
								<div class="form-group groupWidth">
									<label><span>*</span>签发地点/拼音：</label>
									<input id="issuedPlace" name="issuedPlace" style="position:relative;" type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.issuedPlace }"/>
									<input id="issuedPlaceEn" name="issuedPlaceEn" type="text" style="position:absolute;top:45px;border:0px;left:80px;" placeholder=" " value="${obj.passport.issuedPlaceEn }"/>
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
	
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
	
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
			//校验
			$('#passportInfo').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					passport : {
						trigger:"change keyup",
						validators : {
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/orderJp/checkPassport.html',
								message: '护照号已存在，请重新输入',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										passport:$('#passport').val(),
										adminId:$('#id').val(),
										orderid:$('#orderid').val()
									};
								}
							}
						}
					}
				}
			});
			$('#passportInfo').bootstrapValidator('validate');
			
			var remark = $("#passRemark").val();
			if(remark != ""){
				$(".ipt-info").show();
			}
			
			if($("#sex").val() == "男"){
				$("#sexEn").val("M");
			}else{
				$("#sexEn").val("F");
			}
			
			$("#issuedDate").change(function(){
				if($("#issuedDate").val() != ""){
					if($("#validType").val() == 1){
						$('#validEndDate').val(getNewDay($('#issuedDate').val(), 5));
					}else{
						$('#validEndDate').val(getNewDay($('#issuedDate').val(), 10));
					}
				}
			});
		});
		
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
							$('#passport').val(obj.num).change();
							$('#sex').val(obj.sex);
							$('#sexEn').val(obj.sexEn);
							$('#birthAddress').val(obj.birthCountry);
							$('#birthAddressEn').val("/"+getPinYinStr(obj.birthCountry));
							$('#birthday').val(obj.birth);
							$('#issuedPlace').val(obj.visaCountry);
							$('#issuedPlaceEn').val("/"+getPinYinStr(obj.visaCountry));
							$('#issuedDate').val(obj.issueDate);
							$('#validEndDate').val(obj.expiryDay);
							var years = getDateYearSub($('#issuedDate').val(),$('#validEndDate').val());
							if(years == 5){
								$("#validType").val(1);
							}else{
								$("#validType").val(2);
							}
							
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
		
		//保存
		function save(status){
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return;
			}
			
			var passportInfo = $("#passportInfo").serialize();
			$.ajax({
				type: 'POST',
				async : false,
				data : passportInfo,
				url: '${base}/admin/orderJp/saveEditPassport',
				success :function(data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					/* var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					layer.close(index); */
					if(status == 1){
						closeWindow();
						parent.successCallBack(1);
					}
				}
			});
		}
		
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.cancelCallBack(1);
		}
		function cancelCallBack(status){
			closeWindow();
			parent.cancelCallBack(1);
		}
		function successCallBack(status){
				parent.successCallBack(1);
				closeWindow();
		}
		$(function(){
			var passport = $("#passportUrl").val();
			if(passport != ""){
				$("#uploadFile").siblings("i").css("display","block");
			}else{
				$("#uploadFile").siblings("i").css("display","none");
			}
			
		});
		
		$("#sex").change(function(){
			var sex = $(this).val();
			if(sex == "男"){
				$("#sexEn").val("M");
			}else{
				$("#sexEn").val("F");
			}
		});
		
		$("#validType").change(function(){
			var type = $(this).val();
			if(type == 1){
				$('#validEndDate').val(getNewDay($('#issuedDate').val(), 5));
			}else{
				$('#validEndDate').val(getNewDay($('#issuedDate').val(), 10));
			}
			
		});
		function getNewDay(dateTemp, days) {  
		    var dateTemp = dateTemp.split("-");  
		    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
		    var millSeconds = Math.abs(nDate) + (days * 365.2 * 24 * 60 * 60 * 1000);  
		    var rDate = new Date(millSeconds);  
		    var year = rDate.getFullYear();  
		    var month = rDate.getMonth() + 1;  
		    if (month < 10) month = "0" + month;  
		    var date = rDate.getDate();  
		    if (date < 10) date = "0" + date;  
		    return (year + "-" + month + "-" + date);  
		}
		
		function returnYears(year){
			if(((year%4==0)&&(year%100!=0))||(year%400==0)){
		    	return 366;
		 	}else{
		    	return 365; 
			}
		}
		
		/* function f1(time, years){
			var time = $('#issuedDate').val()
			var year = time.getFullYear()+years;
			var month = time.getMonth();
			var day = time.getDate();
			return year + "-" + (month + 1) + "-" + day ;
			//document.getElementById('d1').innerHTML = dateStr;
		} */
		
		
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
		function getPinYinStr(hanzi){
			var onehanzi = hanzi.split('');
			var pinyinchar = '';
			for(var i=0;i<onehanzi.length;i++){
				pinyinchar += PinYin.getPinYin(onehanzi[i]);
			}
			return pinyinchar.toUpperCase();
		}
		
		 function getDateYearSub(startDateStr, endDateStr) {
		        var day = 24 * 60 * 60 *1000; 

		        var sDate = new Date(Date.parse(startDateStr.replace(/-/g, "/")));
		        var eDate = new Date(Date.parse(endDateStr.replace(/-/g, "/")));

		        //得到前一天(算头不算尾)
		        sDate = new Date(sDate.getTime() - day);

		        //获得各自的年、月、日
		        var sY  = sDate.getFullYear();     
		        var sM  = sDate.getMonth()+1;
		        var sD  = sDate.getDate();
		        var eY  = eDate.getFullYear();
		        var eM  = eDate.getMonth()+1;
		        var eD  = eDate.getDate();

		        if(eY > sY && sM == eM && sD == eD) {
		            return eY - sY;
		        } else {
		            //alert("两个日期之间并非整年，请重新选择");
		            return 0;
		        }
		    }
		 
		 function applyBtn(){
			 var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return;
			}
			save(2);
			var id = ${obj.applicantId};
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/updateApplicant.html?id='+id+'&orderid=',
				success: function(index, layero){
						    //do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				}
			});
		 }
			
		 function visaBtn(){
			 var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return;
			}
			save(3);
			var id = ${obj.applicantId};
			var orderid = ${obj.orderid};
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/visaInfo.html?id='+id+'&orderid='+orderid+'&isOrderUpTime'
			});
		 }
		 
		//合格/不合格
			$(".Unqualified").click(function(){
				$(".ipt-info").slideDown();
			});
			$(".qualified").click(function(){
				$(".ipt-info").slideUp();
				var applicantId = ${obj.applicantId};
				var orderid = ${obj.orderid};
				var orderJpId = ${obj.orderJpId};
				var infoType = ${obj.infoType};
				layer.load(1);
				$.ajax({
					type: 'POST',
					data : {
						applicantId : applicantId,
						orderid : orderid,
						orderjpid : orderJpId,
						infoType : infoType
					},
					url: '${base}/admin/qualifiedApplicant/qualified.html',
					success :function(data) {
						console.log(JSON.stringify(data));
						layer.closeAll('loading');
						$("#baseRemark").val("");
					}
				});
			});
	</script>


</body>
</html>
