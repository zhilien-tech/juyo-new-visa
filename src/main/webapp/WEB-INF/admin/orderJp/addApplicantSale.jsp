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
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
<style type="text/css">
img[src=""],img:not([src]) { opacity:0;}
input[type="file"] { z-index:99999;}
#sqImg { z-index:999999;}
#sqImgBack { z-index:999999;}
.delete { z-index:1000000;}
.modal-body { height:100%; margin-top:50px;}
.modal-header { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:10000000; padding:0px 15px;}
.btn-margin { margin-top:10px; }
.modal-content { position:relative; }
.modal-body { padding:15px 100px 15px 20px; }
.nameBeforeYes { margin-right:30px; }
.onceIDYes { margin-right:30px; }
.onceIDTop { display:inline-block;float:left;margin-left:52px;}
.nameBeforeHide ,.nationalityHide{ display:none; }
.nationalityHide { width:41%;}
.wordSpell { display:none; }
.nationalityHide { margin-left:3%;}
.marginL { margin-left:30px;}
.nameBeforeTop { margin-left:36px;}
.mainWidth { width:100% !important;}
#uploadFile,#uploadFileBack { width:100% !important }
/*右导航*/
.rightNav { position:fixed;top:15px;right:0;z-index:999; width:40px;height:100%; cursor:pointer;}
.rightNav span { width: 24px; height: 24px; position: absolute;top:50%; border-left: 4px solid #999;  border-bottom: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
<!--  -->
.front, .back { margin-bottom:8px !important;}
.nowProvince { width:12px; height:12px; vertical-align: middle; margin-top:0px !important;}
/*省、市、国籍检索*/
.IdInfo { border:1px solid #7a9cd3;}
.IdInfo li { padding-left:2%;}
.IdInfo li:hover { background:#1e90ff; cursor:pointer;}
.IdInfo li:hover a { color:#FFF;}
.IdInfo li a { color:#000;}
</style>
</head>
<body>
	<div class="modal-content">
		<a id="toPassport" class="rightNav" onclick="toPassport();">
			<span></span>
		</a>
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">添加申请人</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" onclick="saveApplicant(1);"/>
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<div class="info-QRcode"> <!-- 身份证 正面 -->
							<img width="100%" height="100%" alt="" src="${obj.qrCode }">
						</div> <!-- end 身份证 正面 -->
						
						
						<div class="info-imgUpload front"><!-- 身份证 正面 -->
							<div class="col-xs-6 mainWidth">
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
							<div class="col-xs-6 mainWidth">
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
						
						<!-- <div class="row" style="margin-top:27px;">签发机关
							<div class="col-sm-10 padding-right-0 marginL">
								<div class="form-group">
									<label>签发机关</label>
									<input id="issueOrganization" name="issueOrganization" type="hidden" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
						</div>end 签发机关 -->
						<!-- <div class="row">
							是否有曾用名/曾有的或另有的国际(或公民身份)
							<div class="col-sm-5 padding-right-0 nameBeforeTop">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes">
											<input type="radio" name="hasOtherName" class="nameBefore" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherName" class="nameBefore" checked value="2" />否
										</span>
									</div>
								</div>
							</div>
							姓/名 拼音
							<div class="nameBeforeHide">
							    <div class="col-sm-10 padding-right-0 marginL">
									<div class="form-group" style="position:relative;">
										<label>姓/拼音</label> <input id="otherFirstName"
											name="otherFirstName"  type="text" class="form-control input-sm "
											placeholder=" " value="" />
											<input type="text" id="otherFirstNameEn" style="position:absolute;top:32px;border:none;left:150px;"  name="otherFirstNameEn" value=""/>

										<i class="bulb"></i>
									</div>
								</div>
							</div>
							姓/名 拼音 end
							<div class="col-sm-offset-1 padding-right-0 onceIDTop">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasOtherNationality" class="onceID" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherNationality" class="onceID" checked value="2"  />否
										</span>
									</div>
								</div>
							</div>
							曾用国籍
							<div class="col-sm-5 padding-right-0 nationalityHide">
								<div class="form-group" id="nationalityDiv">
									<label>国籍</label> 
									<input id="nationality" name="nationality" type="text" class="form-control input-sm"/>
								</div>
							</div>
						</div> -->
					</div>
						
					<div class="col-sm-6 padding-right-0">
						<div class="row"><!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>手机号</label>
									<input id="issueOrganization" name="issueOrganization" type="hidden" class="form-control input-sm" placeholder=" " />
									<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
									<input type="hidden" id="applyId"/>
									<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱</label>
									<input id="email" name="email" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 手机号/邮箱 -->
						<div class="row"><!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>公民身份证</label>
									<input id="cardId" name="cardId" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 公民身份证 -->
						<div class="row"><!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>性别</label>
									<!-- <input id="sex" name="sex" type="text" class="form-control input-sm" placeholder=" " value=""/> -->
									<select class="form-control input-sm selectHeight" id="sex" name="sex">
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label>民族</label>
									<input id="nation" name="nation" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5 padding-right-0">
								<div class="form-group">
									<label>出生日期</label>
									<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 姓名/民族 -->
						<div class="row"><!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>住址</label>
									<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 住宅 -->						
						<div class="row"><!-- 有效期限 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>有效期限</label>
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
						<div class="row"><!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地是否与身份证相同</label><input type="checkbox" class="nowProvince" name="addressIsSameWithCard" value="1"/>
									<input type="hidden" name="cardProvince" id="cardProvince"/>
									<input type="hidden" name="cardCity" id="cardCity"/>
									<input id="province" name="province" type="text" class="form-control input-sm" placeholder="省" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地址城市</label>
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder="市" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 现居住地址省份/现居住地址城市 -->
						<div class="row"><!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址</label>
									<input id="detailedAddress" name="detailedAddress" type="text" class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->
						<div class="row">
							<!-- 是否有曾用名/曾有的或另有的国际(或公民身份) -->
							<div class="col-sm-5 padding-right-0 nameBeforeTop">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes">
											<input type="radio" name="hasOtherName" class="nameBefore" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherName" class="nameBefore" checked value="2" />否
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 姓/名 拼音 -->
							<div class="nameBeforeHide">
							    <div class="col-sm-11 padding-right-0 col-sm-offset-1">
									<div class="form-group" style="position:relative;">
										<label>姓/拼音</label> <input id="otherFirstName"
											name="otherFirstName"  type="text" class="form-control input-sm "
											placeholder=" " value="" />
											<input type="text" id="otherFirstNameEn" style="position:absolute;top:32px;border:none;left:150px;"  name="otherFirstNameEn" value=""/>

										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 end -->
						</div>
						<div class="row">
							<div class="nameBeforeHide">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group" style="position:relative;" >
									<label>名/拼音</label> 
									<input id="otherLastName" name="otherLastName" type="text" class="form-control input-sm" placeholder=" " value="" />
									<input type="text" id="otherLastNameEn" style="position:absolute;top:32px;border:none;left:150px;" name="otherLastNameEn" value=""/>
								</div>
							</div>
						</div>
						</div>
						<div class="row">
							<div class="col-sm-offset-1 padding-right-0 onceIDTop">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasOtherNationality" class="onceID" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherNationality" class="onceID" checked value="2"  />否
										</span>
									</div>
								</div>
							</div>
							<!-- 曾用国籍 -->
							<div class="col-sm-5 padding-right-0 nationalityHide">
								<div class="form-group" id="nationalityDiv">
									<label>国籍</label> 
									<input id="nationality" name="nationality" type="text" class="form-control input-sm"/>
								</div>
							</div>
						</div>
						
						
						<div class="row">
							<!-- 紧急联系人姓名/手机 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人姓名</label> <input id="emergencyLinkman"
										name="emergencyLinkman" type="text" class="form-control input-sm"
										placeholder=" " value="${obj.applicant.emergencyLinkman }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人手机</label> <input id="emergencyTelephone" name="emergencyTelephone"
										type="text" class="form-control input-sm" placeholder=" "
										value="${obj.applicant.emergencyTelephone }" />
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
		var sessionId = '${obj.sessionid}';
		var localAddr = '${obj.localAddr}';
		var localPort = '${obj.localPort}';
		var websocketaddr = '${obj.websocketaddr}';
		var orderProcessType = '${obj.orderProcessType}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	
	<script type="text/javascript" src="${base}/admin/orderJp/addApplicantSale.js"></script>
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
	
	<script type="text/javascript">
		
		
		

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
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
		
		$("#validStartDate").datetimepicker({
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
		
		
		//点击右侧箭头，跳转到护照信息
		function toPassport(){
			saveApplicant(2);
		}
		function successCallBack(status){
			if(status == 1){
				parent.successCallBack(3);
			}
			closeWindow();
		}
		function cancelCallBack(status){
			closeWindow();
		}
	</script>


</body>
</html>
