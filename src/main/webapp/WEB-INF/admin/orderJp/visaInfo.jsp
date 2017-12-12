<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/firstTrialJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>签证信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/css/style.css">
	<style type="text/css">

    .input-box {
      position: relative;
      display: inline-block;
    }
    
    .input-box input {
      background-color: transparent;
      background-image: none;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
      color: #555;
      display: block;
      font-size: 14px;
      line-height: 1.42857;
      padding: 6px 6px;
      transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
      width: 200px;
      display: inline;
      position: relative;
      z-index: 1;
    }
    
    .tip-l {
      width: 0;
      height: 0;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;
      border-top: 10px solid #555;
      display: inline-block;
      right: 10px;
      z-index: 0;
      position: absolute;
      top: 12px;
    }
    
    .dropdown {
      position: absolute;
      top: 32px;
      left: 0px;
      width: 200px;
      background-color: #FFF;
      border: 1px solid #23a8ce;
      border-top: 0;
      box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
      z-index: 999;
      padding: 0;
      margin: 0;
    }
    
    .dropdown li {
      display: block;
      line-height: 1.42857;
      padding: 0 6px;
      min-height: 1.2em;
      cursor: pointer;
    }
    
    .dropdown li:hover {
      background-color: #23a8ce;
      color: #FFF;
    }
    .colSm { 
    	display:block;
    	float:left;
    	width:200px;
    }
    .padding-right-0 {
    	margin-left:10%;
    	width:323px;
    	height:200px;
    	border:1px solid #eee;
    }
    .delete {
    	right:0;
    }
</style>
	<style type="text/css">
		body {min-width:auto;}
		.tab-content {background-color: #f8f8f8;}
		.remove-btn {right: 0;}
		.modal-body{background-color:#f9f9f9;}
		.houseProperty,.vehicle,.deposit,.financial,.vice{display:none;}
	</style>
</head>
<body>
	<div class="modal-content">
	<div style="position:absolute;top:40%;left:5%;z-index:999;">
			<a id="toPassport" onclick="passportBtn();">
				<h1><</h1>
			</a>
		</div>
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">签证信息</span> 
				<input type="hidden" value="${obj.visaInfo.applicantId }" name="applicantId"/>
				<input type="hidden" value="${obj.isOrderUpTime }" name="isOrderUpTime"/>
				<input type="hidden" value="${obj.orderid }" name="orderid"/>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right" value="保存退出" />
				<input id="addContinueBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right" value="保存继续" />
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<!-- 结婚状况 -->
					<div class="info">
						<div class="info-head">结婚状况 </div>
						<div class="info-body-from cf ">
							<div class="row colSm">
								<div class="">
									<div class="form-group">
										<select id="marryStatus" name="marryStatus" class="form-control input-sm selectHeight">
											<option value="">请选择</option>
											<c:forEach var="map" items="${obj.marryStatus}">
												<option value="${map.key}" ${map.key==obj.applicant.marryStatus?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							
							<div class="col-sm-4 padding-right-0">
								<div class="cardFront-div">
									<span>上传结婚证/离婚证</span>
									<input id="marryUrl" name="marryUrl" type="hidden" value="${obj.applicant.marryUrl }"/>
									<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									<img id="sqImg" alt="" src="${obj.applicant.marryUrl }" >
									<i class="delete" onclick="deleteApplicantFrontImg();"></i>
								</div>
							</div>
							
						</div>
					</div>
					<!-- 申请人 -->
					<div class="info">
						<div id="mainApply" class="info-head">主申请人 </div>
						<div class="info-body-from"><!--class=" cf "-->
							<div class="row"><!-- 申请人/备注 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>申请人：</label>
										<select id="applicant" name="applicant" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.mainOrVice}">
												<option value="${map.key}" ${map.key==obj.visaInfo.isMainApplicant?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-4 applymain">
									<div class="form-group">
										<label><span>*</span>备注：</label>
										</br>
										<div class="input-box">
											<input type="text" id="relationRemark" name="relationRemark" class="input" value="${obj.visaInfo.relationRemark}">
											<ul class="dropdown">
												<li>主卡</li>
												<li>朋友</li>
												<li>同事</li>
												<li>同学</li>
											</ul>
										</div>
									</div>
								</div>
								
								<div class="applyvice">
									<div class="col-sm-4">
										<div class="form-group">
											<label><span>*</span>主申请人：</label>
											<!-- <input id="mainApplicant" name="mainApplicant" type="text" class="form-control input-sm" placeholder=" " /> -->
											<select id="mainApplicant" name="mainApplicant" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.mainApply}">
												<option value="${map.id}" ${map.id==obj.mainApplicant.id?'selected':'' } >${map.applyname}</option>
											</c:forEach>
										</select>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label><span>*</span>与主申请人关系：</label>
											</br>
										<div class="input-box">
											<input type="text" id="mainRelation" name="mainRelation" class="input" value="${obj.visaInfo.mainRelation}">
												<ul class="dropdown">
													<li>之妻</li>
													<li>之夫</li>
													<li>之子</li>
													<li>之女</li>
													<li>之父</li>
													<li>之母</li>
													<li>朋友</li>
													<li>同事</li>
													<li>同学</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div><!-- end 申请人/备注-->
						</div>
					</div>
					<!-- end 申请人 -->
					
					<!-- 出行信息 -->
					<div class="info tripvice">
						<div class="info-head">出行信息 </div>
						<div class="info-body-from cf ">
							<div class="row"><!-- 是否同主申请人 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>是否同主申请人：</label>
										<input id="trip" name="sameMainTrip" class="form-control input-sm selectHeight" value="是" disabled="disabled"/>
										<%-- <select id="trip" name="sameMainTrip" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.isOrNo}">
												<option value="${map.key}" ${map.key==obj.visaInfo.sameMainTrip?'selected':''}>${map.value}</option>
											</c:forEach>
										</select> --%>
									</div>
								</div>
							</div><!-- end 是否同主申请人 -->
						</div>
					</div>
					<!-- end 出行信息 -->
					
					<!-- 工作信息 -->
					<div class="info">
						<div class="info-head">工作信息 </div>
						<div class="info-body-from cf ">
							<div class="row "><!-- 我的职业/单位名称/单位电话 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>我的职业：</label>
										<!-- <input id="occupation"  name="occupation" type="text" class="form-control input-sm" placeholder=" " /> -->
										<select id="careerStatus" name="careerStatus" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.jobStatusEnum}">
												<option value="${map.key}" ${map.key==obj.workJp.careerStatus?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="schoolName"><span>*</span>单位名称：</label>
										<input id="name" name="name" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.name }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="schoolTelephone"><span>*</span>单位电话：</label>
										<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.telephone }"/>
									</div>
								</div>
							</div><!-- end 我的职业/单位名称/单位电话 -->
							<div class="row"><!-- 单位地址 -->
								<div class="col-sm-8">
									<div class="form-group">
										<label id="schoolAddress"><span>*</span>单位地址：</label>
										<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.address }"/>
									</div>
								</div>
							</div>
							
							<!-- end 单位地址 -->
						</div>
					</div>
					<!-- end 工作信息 -->
					
					<!-- 财产信息 -->
					<div class="info" style="padding-bottom: 15px;">
						<div class="info-head">财产信息 </div>
						<div class="wealthvice row info-body-from clone-module cf">
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>是否同主申请人：</label>
										<select id="wealth" name="sameMainWealth" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.isOrNo}">
												<option value="${map.key}" ${map.key==obj.visaInfo.sameMainWealth?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
						</div>
						<div class="info-body-from finance-btn wealthmain">
							<input id="depositType" name="wealthType" value="银行存款" type="button" class="btn btn-sm btnState" />
							<input id="vehicleType" name="wealthType" value="车产" type="button" class="btn btn-sm btnState" />
							<input id="housePropertyType" name="wealthType" value="房产" type="button" class="btn btn-sm btnState" />
							<input id="financialType" name="wealthType" value="理财" type="button" class="btn btn-sm btnState" />
						</div>
						<div class="info-body-from  clone-module cf deposit">
							<div class="row body-from-input"><!-- 银行存款 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>银行存款：</label>
										<input id="" name="" type="text" class="form-control input-sm" value="银行存款" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="deposit" name="deposit" type="text" class="form-control input-sm" placeholder="万"  />
									</div>
								</div>
							</div><!-- end 银行存款 -->
							<i class="remove-btn"></i>
						</div>
						<div class="info-body-from clone-module cf vehicle">
							<div class="row body-from-input"><!-- 车产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>车产：</label>
										<input id="" name="" type="text" class="form-control input-sm" value="车产" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="vehicle" name="vehicle" type="text" class="form-control input-sm" placeholder=" "/>
									</div>
								</div>
							</div><!-- end 车产 -->
							<i class="remove-btn"></i>
						</div>
						<div class="info-body-from clone-module cf houseProperty">
							<div class="row body-from-input"><!-- 房产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>房产：</label>
										<input id="" name="" type="text" class="form-control input-sm" value="房产" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="houseProperty" name="houseProperty" type="text" class="form-control input-sm" placeholder="平米"  />
									</div>
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn"></i>
						</div>
						<div class="info-body-from clone-module cf financial">
							<div class="row body-from-input"><!-- 房产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>理财：</label>
										<input id="" name="" type="text" class="form-control input-sm" value="理财" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="financial" name="financial" type="text" class="form-control input-sm" placeholder="万"  />
									</div>
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn"></i>
						</div>
						
					</div>
					<!-- end 财产信息 -->
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
	<script type="text/javascript" src="${base}/admin/orderJp/visaInfo.js"></script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
			var marry = $("#marryUrl").val();
			if(marry != ""){
				$("#uploadFile").siblings("i").css("display","block");
			}else{
				$("#uploadFile").siblings("i").css("display","none");
			}
			
			var career = $("#careerStatus").val();
			if(career == 4){
				$("#schoolName").html("<span>*</span>学校名称：");
				$("#schoolTelephone").html("<span>*</span>学校电话：");
				$("#schoolAddress").html("<span>*</span>学校地址：");
			}
			$("#careerStatus").change(function(){
				$("#name").val("");
				$("#telephone").val("");
				$("#address").val("");
				var career = $(this).val();
				if(career == 4){
					$("#schoolName").html("<span>*</span>学校名称：");
					$("#schoolTelephone").html("<span>*</span>学校电话：");
					$("#schoolAddress").html("<span>*</span>学校地址：");
				}else{
					$("#schoolName").html("<span>*</span>单位名称：");
					$("#schoolTelephone").html("<span>*</span>单位电话：");
					$("#schoolAddress").html("<span>*</span>单位地址：");
				}
			});
			
			//主申请人 or 副申请人
			var applicVal = $("#applicant").val();
			if(applicVal == "1"){//主申请人
				$(".applyvice").hide();
				$(".tripvice").hide();
				//$(".workvice").hide();
				$(".wealthvice").hide();
				$(".applymain").show();
				//$(".workmain").show();
				$(".wealthmain").show();
				$("#mainApply").text("主申请人");
			}else{//副申请人
				$(".applyvice").show();
				$(".tripvice").show();
				$(".wealthvice").show();
				//$(".workvice").show();
				$(".applymain").hide();
				//$(".workmain").hide();
				$(".wealthmain").hide();
				$("#mainApply").text("副申请人");
			}
			
			//主申请人 or 副申请人
			$("#applicant").change(function(){
				var applicVal = $(this).val();
				if(applicVal == "1"){//主申请人
					$(".applyvice").hide();
					$(".tripvice").hide();
					//$(".workvice").hide();
					$(".wealthvice").hide();
					$(".applymain").show();
					//$(".workmain").show();
					$(".wealthmain").show();
					$("#mainApply").text("主申请人");
				}else{//副申请人
					$(".applyvice").show();
					$(".tripvice").show();
					$(".wealthvice").show();
					//$(".workvice").show();
					$(".applymain").hide();
					$(".workmain").hide();
					$(".wealthmain").hide();
					$("#mainApply").text("副申请人");
					$(".deposit").css("display","none");
					$(".vehicle").css("display","none");
					$(".houseProperty").css("display","none");
					$(".financial").css("display","none");
				}
			});
			
			var wealthType = '${obj.wealthJp}';
			console.log(wealthType);
			if(wealthType){
				$('[name=wealthType]').each(function(){
					var wealth = $(this);
					$.each(JSON.parse(wealthType), function(i, item){     
						if(item.type == wealth.val()){
							if(wealth.val() == "银行存款"){
								$(".deposit").css("display","block");
								wealth.addClass("btnState-true");
								$("#deposit").val(item.details);
							}
							if(wealth.val() == "车产"){
								$(".vehicle").css("display","block");
								wealth.addClass("btnState-true");
								$("#vehicle").val(item.details);
							}
							if(wealth.val() == "房产"){
								$(".houseProperty").css("display","block");
								wealth.addClass("btnState-true");
								$("#houseProperty").val(item.details);
							}
							if(wealth.val() == "理财"){
								$(".financial").css("display","block");
								wealth.addClass("btnState-true");
								$("#financial").val(item.details);
							}
						}
						});
					});
				
			}
			
			/* var work = $("#work").val();
			if(work == 0){
				$(".workmain").show();
				//$(".address").show();
			}
			$("#work").change(function(){
				if($(this).val() == 1){
					$(".workmain").hide();
				}else{
					$(".workmain").show();
					/* $("#careerStatus").val(1);
					$("#name").val("");
					$("#telephone").val("");
					$("#address").val(""); 
				}
			}); */
			
			var wealth = $("#wealth").val();
			if(wealth == 0){
				$(".wealthmain").show();
				//$(".address").show();
			}else{
				if(applicVal == 1){
					
				}else{
					$(".deposit").css("display","none");
					$(".vehicle").css("display","none");
					$(".houseProperty").css("display","none");
					$(".financial").css("display","none");
				}
			}
			$("#wealth").change(function(){
				if($(this).val() == 1){
					$(".wealthmain").hide();
					$(".deposit").css("display","none");
					$(".vehicle").css("display","none");
					$(".houseProperty").css("display","none");
					$(".financial").css("display","none");
				}else{
					$(".wealthmain").show();
					$('[name=wealthType]').each(function(){
						$(this).removeClass("btnState-true");
					});
				}
			});
			
			
			//财务信息 部分按钮效果
			$(".finance-btn input").click(function(){
				var financeBtnInfo=$(this).val();
				if(financeBtnInfo == "银行存款"){
					if($(this).hasClass("btnState-true")){
						$(".deposit").css("display","none");
						$(this).removeClass("btnState-true");
						$("#deposit").val("");
					}else{
						$(".deposit").css("display","block");
						$(this).addClass("btnState-true");
						$("#deposit").val("万");
						//$("#deposit").placeholder("万");
					}
				}else if(financeBtnInfo == "车产"){
					if($(this).hasClass("btnState-true")){
						$(".vehicle").css("display","none");
						$(this).removeClass("btnState-true");
						$("#vehicle").val("");
					}else{
						$(".vehicle").css("display","block");
						$(this).addClass("btnState-true");
						$("#vehicle").val("");
					}
				}else if(financeBtnInfo == "房产"){
					if($(this).hasClass("btnState-true")){
						$(".houseProperty").css("display","none");
						$(this).removeClass("btnState-true");
						$("#houseProperty").val("");
					}else{
						$(".houseProperty").css("display","block");
						$(this).addClass("btnState-true");
						$("#houseProperty").val("平米");
						//$("#houseProperty").placeholder("平米");
					}
				}else if(financeBtnInfo == "理财"){
					if($(this).hasClass("btnState-true")){
						$(".financial").css("display","none");
						$(this).removeClass("btnState-true");
						$("#financial").val("");
					}else{
						$(".financial").css("display","block");
						$(this).addClass("btnState-true");
						$("#financial").val("万");
						//$("#financial").placeholder("万");
					}
				}
			});
			$(".remove-btn").click(function(){
				//$(this).parent().css("display","none");
				if($(this).parent().is(".deposit")){
					$(".deposit").css("display","none");
					$("#depositType").removeClass("btnState-true");
					$("#deposit").val("");
				}
				if($(this).parent().is(".vehicle")){
					$(".vehicle").css("display","none");
					$("#vehicleType").removeClass("btnState-true");
					$("#vehicle").val("");
				}
				if($(this).parent().is(".houseProperty")){
					$(".houseProperty").css("display","none");
					$("#housePropertyType").removeClass("btnState-true");
					$("#houseProperty").val("");
				}
				if($(this).parent().is(".financial")){
					$(".financial").css("display","none");
					$("#financialType").removeClass("btnState-true");
					$("#financial").val("");
				}
			});
			
			
			
		});
		
		$("#addContinueBtn").click(function(){
			save(2);
		});
		$("#addBtn").click(function(){
			save(1);
		});
		
		
		//保存
		function save(status){
			//绑定财产类型
			var wealthType = "";
			$('[name=wealthType]').each(function(){
				if($(this).hasClass('btnState-true')){
					wealthType += $(this).val() + ',';
				}
			});
			if(wealthType){
				wealthType = wealthType.substr(0,wealthType.length-1);
			}
			//var passportInfo = $("#passportInfo").serialize();
			var applicVal = $("#applicant").val();
			//主申请人时，是否同主申请人设置为空，不然默认为1
			if(applicVal == 1){
				//$("#work").val(0);
				$("#wealth").val(0);
			}
			var passportInfo = $.param({"wealthType":wealthType}) + "&" +  $("#passportInfo").serialize();
			$.ajax({
				type: 'POST',
				async: false,
				data : passportInfo,
				url: '${base}/admin/orderJp/saveEditVisa',
				success :function(data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					layer.close(index);
					if(status == 1){
						parent.successCallBack(1);
					}
				}
			});
		}
		
		//上传结婚证
		
		function dataURLtoBlob(dataurl) { 
			var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
			bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
			while(n--){
				u8arr[n] = bstr.charCodeAt(n);
			}
			return new Blob([u8arr], {type:mime});
		}
		
		$('#uploadFile').change(function() {
			var layerIndex = layer.load(1, {
				shade : "#000"
			});
			$("#addBtn").attr('disabled', true);
			$("#addContinueBtn").attr('disabled', true);
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
					url : BASE_PATH + '/admin/orderJp/marryUpload',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (200 == obj.status) {
							$('#marryUrl').val(obj.data);
							$('#sqImg').attr('src', obj.data);
							$("#uploadFile").siblings("i").css("display","block");
						}
						$("#addBtn").attr('disabled', false);
						$("#addContinueBtn").attr('disabled', false);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.close(layerIndex);
						$("#addBtn").attr('disabled', false);
						$("#addContinueBtn").attr('disabled', false);
					}
				}); // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});
		
		function deleteApplicantFrontImg(){
			$('#marryUrl').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
		}
		
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.cancelCallBack(1);
		}
		function passportBtn(){
			save(2);
			var applicantId = ${obj.applicant.id};
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
				content:'/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid
			});
		}
	</script>


</body>
</html>
