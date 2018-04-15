<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/pcVisa" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="UTF-8">
		<title>报名</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/common/css/switchCardOfOrder.css"><!-- 订单切换卡 样式 -->
    <!-- 加载中。。。样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/spinner.css">
	<!-- 本页样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/viasDetailUS.css">
	</head>
	<body>
		<header>
			<div class="returnAndSave">
				<i></i>
				<span>报名</span>	
			</div>
		</header>
		<section>
			<div class="listMessage">
				<form id="insertOrder">
				<ul>
					<li>
						<label>姓</label>
						<input type="text" id="firstname" name="firstname" class="surName" />
						<input type="hidden" id="eventid" name="eventid"/>
					</li>
					<li>
						<label>名</label>
						<input type="text" id="lastname" name="lastname" class="name" />
					</li>
					<li>
						<label>手机</label>
						<input type="text" id="telephone" name="telephone" class="telphone" maxlength="11" />
					</li>
					<li>
						<label>邮箱</label>
						<input type="text" id="email" name="email" class="email" />
					</li>
				</ul>
				</form>
			</div>
			<div class="payForCase">
				<a class="btnPayment" id="insertbtn">去付款</a>
			</div>
			<input id="redirect" type="hidden" value="0" >
		</section>
		<!-- 弹出层 -->
		<div class="dialog"></div>
	</body>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>		
	<script src="${base}/references/common/js/base/baseIcon.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script>
	function applyValidate(){
		$('#insertOrder').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				telephone : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '手机号不能为空'
						},
						regexp: {
							regexp: /^[1][34578][0-9]{9}$/,
							message: '手机号格式错误'
						}
					}
				},
				email : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '邮箱不能为空'
						},
						regexp: {
							regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
							message: '邮箱格式错误'
						}
					}
				},
				firstname : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '姓不能为空'
						}
					}
				},
				lastname : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '名不能为空'
						}
					}
				}
			}
		});
		//$('#applicantInfo').bootstrapValidator('validate');
	}
		$("#insertbtn").click(function(){
			$.ajax({
				type:"post",
				url:"/admin/appEvents/signUpEventByPublicNum",
				data:$("#insertOrder").serialize(),
				dataType : "json",
				success:function(data){
					layer.closeAll("loading");
					window.location.href = '/admin/orderUS/listUS.html';
				}
			});
		});
	</script>
</html>
