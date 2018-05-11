<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>流程图</title>
		 <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		  <link rel="stylesheet" href="${base}/references/public/css/pikaday.css?v='20180510'">
		  <link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
		  <!-- 流程图 -->
		  <link rel="stylesheet" href="${base}/references/common/css/flowChart.css?v='20180510'">
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
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>资料填写</label>
							</div>
							<div class="mainInfo">
								<label>基本信息、护照信息、签证信息</label>&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript: void(0);" onclick="baseInfo(${obj.applicant.applicantid })">填写</a>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>当前状态</label>
							</div>
							<div class="mainInfo">
								<c:if test="${'不合格' != obj.applicant.applicantstatus }">
									<label>${obj.unqualifiedInfo}</label>
									<a  style="color:#656565;">${obj.applicant.applicantstatus}</a>
								</c:if>
								<c:if test="${'不合格' == obj.applicant.applicantstatus  }">
									<label>${obj.unqualifiedInfo}</label>
									<a href="javascript: void(0);" style="color:#2a7be5;" onclick="editUnqualified(${obj.indexOfPage})">${obj.applicant.applicantstatus}</a>
								</c:if>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>快递单号</label>
							</div>
							<div class="mainInfo">
								<c:if test="${null != obj.expressEntity }">
									<label>${obj.expressType }</label>&nbsp;&nbsp;
									<label>${obj.expressNum }</label>&nbsp;&nbsp;
									<a href="javascript: void(0);" onclick="expressNum(${obj.applicant.applicantid })">修改</a>
								</c:if>
								<c:if test="${null == obj.expressEntity }">
									<label>${obj.expressType }</label>
									<label>${obj.expressNum }</label>
									<a href="javascript: void(0);" onclick="expressNum(${obj.applicant.applicantid })">填写</a>
								</c:if>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>快递状态</label>
							</div>
							<div class="mainInfo">
								<label>已寄出</label>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>收件状态</label>
							</div>
							<div class="mainInfo">
								<label>前台已收件</label>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>预计送签时间</label>
							</div>
							<div class="mainInfo">
								<label>${obj.sendVisaDate }</label>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>资料已进入使馆</label>
							</div>
							<div class="mainInfo">
								<label></label>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>签证已返回</label>
							</div>
							<div class="mainInfo">
								<label></label>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>回邮地址</label>
							</div>
							<div class="mainInfo">
								<c:if test="${null != obj.backmail }">
									<a href="javascript: void(0);" onclick="backmail(${obj.applicant.applicantid })">修改</a>
								</c:if>
								<c:if test="${null == obj.backmail}">
									<a href="javascript: void(0);" onclick="backmail(${obj.applicant.applicantid })">填写</a>
								</c:if>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="writeData">
								<label>资料已寄出</label>
							<div class="mainInfo">
								<label></label>
							</div>
							
						</li>
						<li style="display:none" class="orderOver">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
							</div>
							<div class="writeData">
								<label>订单结束</label>
							</div>
							<div class="mainInfo">
								<label></label>
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
		<%-- <script src="${base}/admin/myVisa/flowChart.js"></script> --%>
		<script type="text/javascript">
		
			$(function(){
			  $('.flowChart').find('li').each(function() {
					 var indexLi = $(this).index();
					 if(indexLi <= indexOfBlue){
						 $(this).attr("style", "display:block");
						 //$(this).find("div .vertical").attr("style", "display:none");
					 }
					 if(indexLi == indexOfBlue && indexOfBlue != 9){
						 $(this).find('.vertical').attr("style", "display:none");
						 
					 }
					 if(indexOfBlue == 9){
						 $('.orderOver').attr("style", "display:block");
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
					content:'/admin/orderJp/updateApplicant.html?id='+applyId+'&orderid='+'&isTrial=0&flowChart=1&tourist=1'
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
					content:'/admin/myVisa/youkeExpressInfo.html?applicantId='+applyId+'&orderId='+orderId
				});
			}
			//回邮信息
			function backmail(applyId){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/backMailJp/backMailInfo.html?applicantId='+applyId+'&orderId=${obj.orderid}&isAfterMarket=0&orderProcessType=1&flowChart=1'

				});
			}
			
			function editUnqualified(indexOfPage){
				var url = "";
				if(indexOfPage == 1){
					//基本信息
					url = '/admin/orderJp/updateApplicant.html?id='+applicantId+'&orderid='+orderId+'&isTrial=0&addApply=2'
				}else if(indexOfPage == 2){
					//护照信息
					url = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderId+'&isTrial=0&addApply=2'
				}else{
					//签证信息
					url = '/admin/orderJp/visaInfo.html?id='+applicantId+'&orderid='+orderId+'&isOrderUpTime=0&isTrial=0&addApply=2'
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
				window.location.reload();
				/* $.ajax({ 
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
				}); */
			}
			function cancelCallBack(status){
				successCallBack(4);
			}
		</script>
	</body>
</html>
