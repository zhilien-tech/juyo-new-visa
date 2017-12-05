<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>流程图</title>
		<style type="text/css">
			.flowChart li div label{margin: 0;font-size: 14px;text-align: right;width:105px;display: inline-block;}
			.flowChart li:nth-child(1){height: 85px;padding-left: 10%;padding-top: 10px;}
			.flowChart li div span{position: relative;left: 20px;}
			.flowChart li {height: 82px;}
			.circle{width: 8%;height: 80px;float: left;margin-left: 2%;}
			.date-info{display: inline-block;width: 85%;position: relative;top: 7px;}
			.date-info span{width:100%;}
			.date-info span label{width: 75% !important;text-align: left !important;position: relative;left: 20px;color: #8e8e8e;font-size: 12px !important;}
			.date-info span a{color: #2a7be5;}
			.circle .circle-outside{height: 40px;width: 40px;background-color: #e4e4e4;margin: 0 auto;border-radius: 50%;padding: 4px;}
			.circle .circle-outside i{display: block;width: 100%;height: 100%;background-color: #d2d2d2;border-radius: 50%;}
			.circle .vertical{width: 3px;height:35px;background-color:#e4e4e4;margin: 3px auto;}
			.blue .circle-outside{background-color:#9fc4f6 !important;}
			.blue .circle-outside i{background-color: #5e9fef !important;}
			.blue .vertical{background-color: #9fc4f4 !important;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper"  style="min-height: 848px;">
				<ul class="title">
						<!-- <li>办理中签证</li>
						<li class="arrow"></li>
						<li>日本</li>
						<li class="arrow"></li> -->
						<a href="/admin/myVisa/inProcessVisa.html"><li>申请人</li></a>
						<li class="arrow"></li>
						<li>${obj.applicant.applicantname }</li>
					</ul>
				<section class="content">
					<div class="box">
						<div class="box-body">
							<ul class="flowChart">
								<li>
										<!-- <label>订单号：</label>
										<span>170808-JP0045</span> -->
									<div class="date-info">
										<label>订单号：</label>
										<span>${obj.order.orderNum}</span>
									</div>
								</li>
								<li>
									<div class="circle blue">
										<div class="circle-outside blue"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>资料填写：</label>
										<span><a href="">填写</a><label>(护照信息、基本信息、签证信息)</label></span>
									</div>
									
								</li>
								<li>
									<div class="circle blue">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>当前状态：</label>
										<!-- <span>初审合格</span> -->
										<span><a href="">${obj.orderstatus}</a><label>(基本信息、签证信息)</label></span>
									</div>
									
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>快递单号：</label>
										<span></span>
									</div>
									
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>快递状态：</label>
										<span></span>
									</div>
									
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>收件状态：</label>
										<span></span>
									</div>
									
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>预计出签时间：</label>
										<span></span>
									</div>
									
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>录指纹时间：</label>
										<span></span>
									</div>
									
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>通知录指纹：</label>
										<span></span>
									</div>
									
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>签证进度：</label>
										<span></span>
									</div>
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>签约时间：</label>
										<span></span>
									</div>
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>通过/拒接原因：</label>
										<span></span>
									</div>
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>签证进度：</label>
										<span></span>
									</div>
								</li>
								<li>
									<div class="circle">
										<div class="circle-outside"><i></i></div>
										<div class="vertical"></div>
									</div>
									<div class="date-info">
										<label>签证进度：</label>
										<span></span>
									</div>
								</li>
							</ul>	
						</div>
					</div>
				</section>
			</div>
			<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	
		</div>
		<script type="text/javascript">
			var orderstatus = ${obj.order.status};
		</script>
		<script src="${base}/references/public/plugins/jQuery/jquery.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/admin/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/admin/myVisa/flowChart.js"></script>
		<script type="text/javascript">
			$(function(){
				$(".flowChart li:last-child").find(".vertical").remove();
				
				if(orderstatus == 1){
					//下单状态
					
				}
				
			});
			
		</script>
	</body>
</html>