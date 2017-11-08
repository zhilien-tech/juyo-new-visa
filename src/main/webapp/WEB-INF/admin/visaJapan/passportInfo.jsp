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
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
</head>
<body>
	<div class="modal-content">
		<form id="">
			<div class="modal-header">
				<span class="heading">护照信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" id="passportvue">
				<div class="tab-content row">
					<div class="col-sm-5 padding-right-0">
						<div class="info-QRcode"><!-- 二维码 -->
							
						</div><!-- end 二维码 -->
						
						<div class="info-imgUpload front"><!-- 护照 -->

						</div><!-- end 护照 -->
					</div>
						
					<div class="col-sm-7 padding-right-0">
						<div class="row"><!-- 类型/护照号 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>类型：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder="" v-model="passport.type"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>护照号：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder="" v-model="passport.passport"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 类型/护照号 -->
						<div class="row"><!-- 性别/ 出生地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="passport.sexstr"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生地点/拼音：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="passport.birthaddressstr"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 性别/出生地点 拼音 -->
						<div class="row"><!-- 出生日期/签发地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="passport.birthday" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发地点/拼音：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="passport.issuedplacestr"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 出生日期/签发地点 拼音 -->
						<div class="row"><!-- 签发日期/有效期至 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发日期：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="passport.issueddate"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;</label>
									<select class="form-control input-sm selectHeight">
										<option>5年</option>
										<option>10年</option>
									</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>有效期至：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="passport.validenddate"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 签发日期/有效期至 -->
						<div class="row none"><!-- 签发机关 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发机关：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div><!-- end 签发机关 -->
						
						<div class="row none">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
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
	<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/vue/vue-multiselect.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		var vueobj;
		new Vue({
			el: '#passportvue',
			data: {
				passport:""
			},
			created:function(){
				vueobj = this;
				var url = BASE_PATH + '/admin/visaJapan/getPassportData.html';
		        $.ajax({ 
		        	url: url,
		        	dataType:"json",
		        	data:{applyId:'${obj.applyId}'},
		        	type:'post',
		        	success: function(data){
		        		vueobj.passport = data.passport;
						console.log(JSON.stringify(data.passport));
		        	}
		        });
			}
		});
		$(function() {
			
		});
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>


</body>
</html>
