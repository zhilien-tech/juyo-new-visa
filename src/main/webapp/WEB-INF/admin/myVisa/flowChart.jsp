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
			.flowChart li div label{margin: 0; float:left;font-size: 14px;text-align: right; display: inline-block; margin:0 10px;}
			.flowChart li:nth-child(1){height: 85px;padding-left: 10%;padding-top: 10px;}
			.flowChart li div span{position: relative;left: 10%; display:inline-block; float:left;}
			.flowChart li {height: 82px;}
			.circle{width: 8%;height: 80px;float: left;margin-left: 2%;}
			.date-info{display: inline-block;width: 85%;position: relative;top: 7px;}
			.date-info span{width:40%;}
			.principalApplicantTable { width:100%;}
			.date-info span a{color: #2a7be5;}
			.circle .circle-outside{height: 25px;width: 25px;background-color: #e4e4e4;margin: 0 auto;border-radius: 50%;padding: 4px;}
			.circle .circle-outside i{display: block;width: 100%;height: 100%;background-color: #d2d2d2;border-radius: 50%;}
			.circle .vertical{width: 3px;height:50px;background-color:#e4e4e4;margin: 3px auto;}
			.blue .circle-outside{background-color:#9fc4f6 !important;}
			.blue .circle-outside i{background-color: #5e9fef !important;}
			.blue .vertical{background-color: #9fc4f4 !important;}
			.NoClcik { cursor:default; color:#656565 !important;}
			table { width:100%; }
			td,th { height:30px; text-align:center;}
			.tdL { text-align:left; padding-left:25px;}
			.tableUpdate { float:left; margin-left:12%;margin-top:10%;}
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
							<div class="">
								<label>当前状态</label>
								<!-- <span>初审合格</span> -->
								<c:if test="${'不合格' != obj.applicant.applicantstatus }">
									<span><a href="javascript: void(0);" style="color:#656565;">${obj.applicant.applicantstatus}</a><label>${obj.unqualifiedInfo}</label></span>
								</c:if>
								<c:if test="${'不合格' == obj.applicant.applicantstatus  }">
									<span><a href="javascript: void(0);" style="color:#2a7be5;" onclick="editUnqualified(${obj.indexOfPage})">${obj.applicant.applicantstatus}</a><label>${obj.unqualifiedInfo}</label></span>
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
									<span><a class="NoClcik">${obj.expressType }</a>&nbsp;<label>${obj.expressNum }</label><a href="javascript: void(0);" onclick="expressNum(${obj.applicant.applicantid })">修改</a></span>
								</c:if>
								<c:if test="${null == obj.expressEntity }">
									<span><label>${obj.expressType }</label>&nbsp;<label>${obj.expressNum }</label><a href="javascript: void(0);" onclick="expressNum(${obj.applicant.applicantid })">填写</a></span>
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
								<span><a class="NoClcik">已寄出</a></span>
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
								<label>签证已返回</label>
								<span></span>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<div class="vertical"></div>
							</div>
							<div class="date-info">
								<label>回邮地址</label>
								<c:if test="${null != obj.backmail }">
									<span>
									
									<table id="principalApplicantTable" border="1">
										<tbody name="applicantsTable" id="applicantsTable">
											<tr>
												<td>资料来源</td>
												<td>${obj.source }</td>
												<td>团队名称</td>
												<td>${obj.backmail.teamName }</td>
											</tr>
											<tr>
												<td class="tdL" colspan="2">回邮方式</td>
												<td class="tdL" colspan="2">${obj.exprType }</td>
											</tr>
											<tr>
												<td>联系人</td>
												<td>${obj.backmail.linkman }</td>
												<td>电话</td>
												<td>${obj.backmail.telephone }</td>
											</tr>
											<tr>
												<td class="tdL" colspan="2">回邮地址</td>
												<td class="tdL" colspan="2">${obj.backmail.expressAddress }</td>
											</tr>
											<tr>
												<td>发票项目内容</td>
												<td>${obj.backmail.invoiceContent }</td>
												<td>发票抬头</td>
												<td>${obj.backmail.invoiceHead }</td>
											</tr>
											<tr>
												<td>地址</td>
												<td>${obj.backmail.invoiceAddress }</td>
												<td>电话</td>
												<td>${obj.backmail.invoiceMobile }</td>
											</tr>
											<tr>
												<td>税号</td>
												<td>${obj.backmail.taxNum }</td>
												<td>备注</td>
												<td>${obj.backmail.remark }</td>
											</tr>
										</tbody>
									</table>
									</span>
									<a class="tableUpdate" href="javascript: void(0);" onclick="backmail(${obj.applicant.applicantid })">修改</a>
								</c:if>
								<c:if test="${null == obj.backmail}">
									<span><a href="javascript: void(0);" onclick="backmail(${obj.applicant.applicantid })">填写</a></span>
								</c:if>
							</div>
						</li>
						<li style="display:none">
							<div class="circle blue">
								<div class="circle-outside blue"><i></i></div>
								<!-- <div class="vertical"></div> -->
							</div>
							<div class="date-info">
								<label>资料已寄出</label>
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
					content:'/admin/orderJp/updateApplicant.html?id='+applyId+'&orderid='+'&isTrial=0&flowChart=1'
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
