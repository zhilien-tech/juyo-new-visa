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
		body {min-width:auto;}
		.tab-content {background-color: #f8f8f8;}
		.remove-btn {right: 0;}
		.modal-body{background-color:#f9f9f9;}
		.houseProperty,.vehicle,.deposit,.financial,.vice{display:none;}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">签证信息</span> 
				<input type="hidden" value="${obj.visaInfo.applicantId }" name="applicantId"/>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<!-- 申请人 -->
					<div class="info">
						<div id="mainApply" class="info-head">主申请人 </div>
						<div class="info-body-from cf ">
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
								<div class="col-sm-4 main">
									<div class="form-group">
										<label><span>*</span>备注：</label>
										<!-- <input id="relationRem" name="relationRem" type="text" class="form-control input-sm" placeholder=" " /> -->
										<select id="relationRemark" name="relationRemark" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.applicantRemark}">
												<option value="${map.key}" ${map.key==obj.visaInfo.relationRemark?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="vice">
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
											<!-- <input id="" name="" type="text" class="form-control input-sm" placeholder=" " /> -->
											<select id="mainRelation" name="mainRelation" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.applicantRelation}">
												<option value="${map.key}" ${map.key==obj.visaInfo.mainRelation?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
										</div>
									</div>
								</div>
							</div><!-- end 申请人/备注-->
						</div>
					</div>
					<!-- end 申请人 -->
					
					<!-- 出行信息 -->
					<div class="info vice">
						<div class="info-head">出行信息 </div>
						<div class="info-body-from cf ">
							<div class="row"><!-- 是否同主申请人 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>是否同主申请人：</label>
										<select id="trip" name="sameMainTrip" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.isOrNo}">
												<option value="${map.key}" ${map.key==obj.visaInfo.sameMainTrip?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
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
							<div class="row main"><!-- 我的职业/单位名称/单位电话 -->
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
										<label><span>*</span>单位名称：</label>
										<input id="name" name="name" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.name }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>单位电话：</label>
										<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.telephone }"/>
									</div>
								</div>
							</div><!-- end 我的职业/单位名称/单位电话 -->
							<div class="row main"><!-- 单位地址 -->
								<div class="col-sm-8">
									<div class="form-group">
										<label><span>*</span>单位地址：</label>
										<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.address }"/>
									</div>
								</div>
							</div>
							<div class="vice row">
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>是否同主申请人：</label>
										<select id="work" name="sameMainWork" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.isOrNo}">
												<option value="${map.key}" ${map.key==obj.visaInfo.sameMainWork?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
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
						<div class="info-body-from finance-btn main">
							<input id="" name="wealthType" value="银行存款" type="button" class="btn btn-sm btnState" />
							<input id="" name="wealthType" value="车产" type="button" class="btn btn-sm btnState" />
							<input id="" name="wealthType" value="房产" type="button" class="btn btn-sm btnState" />
							<input id="" name="wealthType" value="理财" type="button" class="btn btn-sm btnState" />
						</div>
						<div class="info-body-from  clone-module cf deposit">
							<div class="row body-from-input"><!-- 银行存款 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>银行存款：</label>
										<input id="deposit" name="deposit" type="text" class="form-control input-sm" placeholder="银行存款" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " value="万" />
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
										<input id="vehicle" name="vehicle" type="text" class="form-control input-sm" placeholder="车产" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" "/>
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
										<input id="houseProperty" name="houseProperty" type="text" class="form-control input-sm" placeholder="房产" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " value="平米" />
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
										<input id="financial" name="financial" type="text" class="form-control input-sm" placeholder="理财" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " value="平米" />
									</div>
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn"></i>
						</div>
						<div class="vice row info-body-from clone-module cf">
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
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
			//主申请人 or 副申请人
			var applicVal = $("#applicant").val();
			if(applicVal == "1"){//主申请人
				$(".vice").hide();
				$(".main").show();
				$("#mainApply").text("主申请人");
			}else{//副申请人
				$(".vice").show();
				$(".main").hide();
				$("#mainApply").text("副申请人");
			}
			
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
			
			
			//财务信息 部分按钮效果
			$(".finance-btn input").click(function(){
				var financeBtnInfo=$(this).val();
				if(financeBtnInfo == "银行存款"){
					if($(this).hasClass("btnState-true")){
						$(".deposit").css("display","none");
						$(this).removeClass("btnState-true");
					}else{
						$(".deposit").css("display","block");
						$(this).addClass("btnState-true");
					}
				}else if(financeBtnInfo == "车产"){
					if($(this).hasClass("btnState-true")){
						$(".vehicle").css("display","none");
						$(this).removeClass("btnState-true");
					}else{
						$(".vehicle").css("display","block");
						$(this).addClass("btnState-true");
					}
				}else if(financeBtnInfo == "房产"){
					if($(this).hasClass("btnState-true")){
						$(".houseProperty").css("display","none");
						$(this).removeClass("btnState-true");
					}else{
						$(".houseProperty").css("display","block");
						$(this).addClass("btnState-true");
					}
				}else if(financeBtnInfo == "理财"){
					if($(this).hasClass("btnState-true")){
						$(".financial").css("display","none");
						$(this).removeClass("btnState-true");
					}else{
						$(".financial").css("display","block");
						$(this).addClass("btnState-true");
					}
				}
			});
			$(".remove-btn").click(function(){
				$(this).parent().css("display","none");
			});
			
			
			//主申请人 or 副申请人
			$("#applicant").change(function(){
				var applicVal = $(this).val();
				if(applicVal == "1"){//主申请人
					$(".vice").hide();
					$(".main").show();
					$("#mainApply").text("主申请人");
				}else{//副申请人
					$(".vice").show();
					$(".main").hide();
					$("#mainApply").text("副申请人");
				}
			});
		});
		
		//保存
		function save(){
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
			var passportInfo = $.param({"wealthType":wealthType}) + "&" +  $("#passportInfo").serialize();
			$.ajax({
				type: 'POST',
				data : passportInfo,
				url: '${base}/admin/orderJp/saveEditVisa',
				success :function(data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					parent.successCallBack(1);
					closeWindow();
				}
			});
		}
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>


</body>
</html>
