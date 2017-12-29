<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>流程图</title>
		 <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		  <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		  <link rel="stylesheet" href="${base}/references/public/css/style.css">
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
		<ul class="title">
				<!-- <li style="display:none">办理中签证</li>
				<li class="arrow"></li>
				<li style="display:none">日本</li>
				<li class="arrow"></li> -->
				<a href="/admin/myVisa/visaList.html"><li>订单</li></a>
				<li class="arrow"></li>
				<a href="/admin/myVisa/inProcessVisa.html?orderJpId=${obj.orderJpId }"><li>申请人</li></a>
				<li class="arrow"></li>
				<li>${obj.applicant.applicantname }</li>
			</ul>
		<section class="content">
			<div class="box">
				<div class="box-body">
					<ul class="flowChart">
						<li style="display:none"></li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>资料填写</label>
								<span><a href="javascript: void(0);" onclick="baseInfo(${obj.applicant.applicantid })">填写</a><label>(基本信息、护照信息、签证信息)</label></span>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>当前状态</label>
								<!-- <span>初审合格</span> -->
								<c:if test="${'不合格' != obj.applicant.applicantstatus }">
									<span><a href="javascript: void(0);">${obj.applicant.applicantstatus}</a><label>${obj.unqualifiedInfo}</label></span>
								</c:if>
								<c:if test="${'不合格' == obj.applicant.applicantstatus  }">
									<span><a href="javascript: void(0);" onclick="editUnqualified(${obj.indexOfPage})">${obj.applicant.applicantstatus}</a><label>${obj.unqualifiedInfo}</label></span>
								</c:if>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>快递单号</label>
								<c:if test="${null != obj.expressEntity }">
									<span><a href="javascript: void(0);" onclick="expressNum(${obj.applicant.applicantid })">编辑</a><label>${obj.expressNum }</label></span>
								</c:if>
								<c:if test="${null == obj.expressEntity }">
									<span><a href="javascript: void(0);" onclick="expressNum(${obj.applicant.applicantid })">填写</a><label>${obj.expressNum }</label></span>
								</c:if>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>快递状态</label>
								<span><label>已寄出</label></span>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>收件状态</label>
								<span><label>前台已收件</label></span>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>预计发招宝时间</label>
								<span><label>${obj.prepareDate }</label></span>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>已发招宝</label>
								<span></span>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>预计送签时间</label>
								<span><label>${obj.sendVisaDate }</label></span>
							</div>
							
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>资料已进入使馆</label>
								<span></span>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>签证已返回，预计邮寄时间</label>
								<span><label>1-3个工作日</label></span>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<!-- <div class="vertical"></div> -->
							</div>
							<div class="date-info">
								<label>资料已发出，查看物流</label>
								<span></span>
							</div>
						</li>
					</ul>	
				</div>
			</div>
		</section>
		<script type="text/javascript">
			var orderId = ${obj.order.id};
			var orderstatus = ${obj.order.status};
			var applicantId = ${obj.applicant.applicantid};
			
			var indexOfBlue = ${obj.indexOfBlue};
		</script>
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap-switch.min.js"></script>
		<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
		<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/admin/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/admin/myVisa/flowChart.js"></script>
		<script type="text/javascript">
		
			$(function(){
			  $('.flowChart').find('li').each(function() {
					 var indexLi = $(this).index();

					 if(indexLi <= indexOfBlue){
						 $(this).attr("style", "display:block");
						 //$(this).find("div .vertical").attr("style", "display:none");
					 }
					 if(indexLi == indexOfBlue){
						 $(this).find('.vertical').attr("style", "display:none");
						 
					 }
	           })
			});
			
			function baseInfo(applyId){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '551px'],
					content:'/admin/orderJp/updateApplicant.html?id='+applyId+'&orderid='+'&isTrial=0'
				});
			}
			
			function expressNum(applyId){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['600px', '300px'],
					content:'/admin/myVisa/youkeExpressInfo.html?applicantId='+applyId
				});
			}
			
			function editUnqualified(indexOfPage){
				var url = "";
				if(indexOfPage == 1){
					//基本信息
					url = '/admin/orderJp/updateApplicant.html?id='+applicantId+'&orderid='+orderId
				}else if(indexOfPage == 2){
					//护照信息
					url = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderId
				}else{
					//签证信息
					url = '/admin/orderJp/visaInfo.html?id='+applicantId+'&orderid='+orderId+'&isOrderUpTime=0'
				}
				
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '551px'],
					content: url
				});
			}
			function successCallBack(status){
				if(status == 1){
					layer.msg('填写成功');
				}else if(status == 2){
					layer.msg('操作成功');
				}else if(status == 3){
					layer.msg('操作失败');
				}
				
				$.ajax({ 
					url: '/admin/myVisa/flowChart.html',
					data:{
						orderid:orderId,
						applicantid:applicantId
					},
					dataType:"json",
					type:'post',
					success: function(data){
						_self.myVisaData = data.myVisaData;
					}
				});
			}
			function cancelCallBack(status){
				successCallBack(1);
			}
		</script>
	</body>
</html>