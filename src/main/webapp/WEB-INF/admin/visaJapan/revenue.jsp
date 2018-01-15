<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/sale" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>签证日本-实收</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		#datatableId tbody tr td:nth-child(1){width: 12%;}
		#datatableId tbody tr td:nth-child(2){width: 15%;}
		#datatableId tbody tr td:nth-child(3){width:25%;}
		#datatableId tbody tr td:nth-child(4){width: 10%;}
		.editInp{height: 19px;position: relative;top: -5px;border: solid 1px #b8d3e9;width: 60px;border-radius: 3px;margin-right: 2px;font-size: 12px;line-height: 19px;padding-left: 3px;}
		.frontcertificates{text-align:left !important;}
		.frontcertificates span{margin-right: 2px;display: inline-block;width: unset;height: unset; border: solid 1px #cee1ff;color:#287ae7;padding: 0 5px;font-size: 12px;border-radius: 3px;}
		
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">实收资料</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
				<input id="addBtn" type="button" onclick="frontRvenue();" class="btn btn-primary pull-right btn-sm btn-right" value="前台实收" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<table id="datatableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>姓名</span></th>
								<th><span>电话</span></th>
								<th class="visadata"><span>邮箱</span></th>
								<th class="visadata"><span>资料类型</span></th>
								<th class="frontdata none"><span>前台实收</span></th>
								<th><span>签证实收</span></th>
							</tr>
						</thead>
						<tbody id="applyinfo">
							<c:forEach items="${obj.applicant }" var="apply">
								<tr>
									<td>${apply.applicant }</td>
									<td>${apply.telephone }</td>
									<td class="visadata">${apply.email }</td>
									<td class="visadata">${apply.dataType }</td>
									<td class="frontdata frontcertificates none">
										<c:forEach items="${apply.frontrevenue }" var="frontrevenue">
											<c:choose>
												<c:when test="${frontrevenue.status == 0 }">
													<span class="titleStyle">${frontrevenue.realInfo }</span>
												</c:when>
												<c:otherwise>
													<span>${frontrevenue.realInfo }</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</td>
									<td class="certificates">
										<c:forEach items="${apply.revenue }" var="revenue">
											<c:choose>
												<c:when test="${revenue.status == 0 }">
													<span class="titleStyle realinfo">${revenue.realInfo }</span>
													<input type="hidden" id="revenueid" name="revenueid" value="${revenue.id }">
												</c:when>
												<c:otherwise>
													<span class="realinfo">${revenue.realInfo }</span>
													<input type="hidden" id="revenueid" name="revenueid" value="${revenue.id }">
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<input id="" name="" type="text" class="addInp none">
										<span class="addText">+</span>
										<input type="hidden" id="applicatid" name="applicatid" value="${apply.applicatid}">
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="row" id="orderremark">
						<div class="col-sm-12">
							<div class="form-group">
								<label>备注：</label> 
								<input id="remark" name="remark" type="text" class="form-control input-sm" v-model="orderinfo.remark"/>
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
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
			/*点击表格中的加号标签*/
			$(".addText").click(function(){
				var thisobj = $(this);
				$(this).siblings(".addInp").removeClass("none");
				$(".addInp").focus();//add input 添加默认光标
				var applicatid = $(this).parent().find('#applicatid').val();
				var inputVal = $(this).siblings(".addInp").val();
				if(inputVal != null && inputVal != ""){
					$.ajax({ 
		            	url: '${base}/admin/visaJapan/saveApplicatRevenue.html',
		            	data:{
		            		applicatid:applicatid,
		            		realInfo:inputVal
		            	}
		            	},
		            	dataType:"json",
		            	type:'post',
		            	success: function(data){
		            		var str = '<span class="realinfo titleStyle">'+ inputVal +'</span>';
		            		str += '<input type="hidden" id="revenueid" name="revenueid" value="'+data.id+'">';
		            		thisobj.siblings(".addInp").before(str);//在input前面 添加span标签
		            		thisobj.siblings(".addInp").val("");
		              	}
		            });
				}
			});
			
			//点击真是资料 护照标签
			//$(".certificates span").click(function(){
			$(document).on('click','.certificates .realinfo',function(){
				var thisobj = $(this);
				var paperid = thisobj.next().val();
				var nextobj = thisobj.next();
				$.ajax({ 
	            	url: '${base}/admin/visaJapan/validateIsoriginal.html',
	            	data:{paperid:paperid},
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	            		if(data){
							var spanText = thisobj.text();
							var HZlength = ($(".certificates").has(".passportInp")).length > 0;
							if(spanText.indexOf("护照原件")!== -1 && HZlength != true){
								thisobj.removeClass("titleStyle");
								thisobj.after('<input type="text" class="passportInp"/>');
								var passport = $(".passportInp").val();
								var passnumber = passport.substring(2);
								$(".passportInp").val("").focus().val(passnumber); //把光标加入到字符串后面
							}else if(HZlength == true){
								$(".passportInp").remove();
								thisobj.addClass("titleStyle");
							}
							
							if(thisobj.hasClass("titleStyle")){
								thisobj.removeClass("titleStyle");
							}else{
								thisobj.addClass("titleStyle");
							}
	            		}else{
	            			var inputstr = '<input id="" name="" type="text" class="editInp" value="'+thisobj.text()+'">';
	            			thisobj.replaceWith(inputstr);
	            			//nextobj.prepend(inputstr);
	            		}
	            	}
	            });
				
			});
			//保存护照信息
			$(document).on('blur','.passportInp',function(){
				var thisval = $(this).val();
				var thisobj = $(this);
				var applicatid = $(this).parent().find('#applicatid').val();
				$.ajax({ 
	            	url: '${base}/admin/visaJapan/editPassportCount.html',
	            	data:{applicatid:applicatid,inputVal:'护照原件'+thisval},
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	              	}
	            });
				$(this).parent().find("span").each(function(index,value){
					if($(this).text().indexOf("护照原件") !== -1){
						$(this).text('护照原件'+thisval);
						$(".passportInp").remove();
						if(thisval){
							$(this).addClass("titleStyle");
						}else{
							$(this).removeClass("titleStyle");
						}
					}
				});
			});
			$(document).on('input','.passportInp',function(){
				$(this).val($(this).val().replace(/[^\d]/g,''));
			});
		});

		//vue表格数据对象
	    var _self;
		new Vue({
			el: '#orderremark',
			data: {orderinfo:""},
			created:function(){
	            _self=this;
	            var orderid = '${obj.orderid}';
	            $.ajax({ 
	            	url: '${base}/admin/visaJapan/visaRevenue.html',
	            	data:{orderid:orderid},
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	            		_self.orderinfo = data.orderinfo;
	            		console.log(JSON.stringify(_self.orderinfo));
	              	}
	            });
	        }
		});
		//保存
		function save(){
			var applicatinfo = [];
			$('#applyinfo').find('tr').each(function(index,value){
				var applicatobj = {};
				var applicatid = $(this).find('#applicatid').val();
				applicatobj.applicatid = applicatid;
				//var datatext = '';
				//var graydata = '';
				var revenues = [];
				$(this).find(".certificates span").each(function(index){
					var thisobj = $(this);
					if(thisobj.hasClass('titleStyle')){
						var revenue = {};
						revenue.realInfo = thisobj.text();
						revenue.id = thisobj.next().val();
						//datatext += $(this).text() + ',';
						revenue.status = 0;
						revenues.push(revenue);
					}else{
						if(thisobj.text() != '+'){
							//graydata += $(this).text() + ',';
							var revenue = {};
							revenue.realInfo = thisobj.text();
							revenue.id = thisobj.next().val();
							revenue.status = 1;
							revenues.push(revenue);
						}
					}
				});
				$(this).find(".certificates .editInp").each(function(index){
					var thisobj = $(this);
					var revenue = {};
					revenue.realInfo = thisobj.val();
					revenue.id = thisobj.next().val();
					//datatext += $(this).text() + ',';
					revenue.status = 0;
					revenues.push(revenue);
				});
				if(!$(this).find('.addInp').hasClass('none')){
					var thisobj = $(this).find('.addInp');
					var revenue = {};
					revenue.realInfo = thisobj.val();
					revenue.status = 0;
					revenues.push(revenue);
				}
				/* $(this).find('.certificates span').each(function(index){
					var thisobj = $(this);
					if(thisobj.hasClass('titleStyle')){
						console.log('hui'+thisobj.text());
					}else{
						if(thisobj.text() != '+'){
							console.log('lan'+thisobj.text());
						}
					}
				}); */
				/* $(this).find('.titleStyle').each(function(index){
					datatext += $(this).text() + ',';
				}); */
				/* datatext = datatext.substring(0, datatext.length-1);
				graydata = graydata.substring(0, graydata.length-1);
				applicatobj.datatext = datatext;
				applicatobj.graydata = graydata; */
				applicatobj.revenue = JSON.stringify(revenues);
				applicatinfo.push(applicatobj);
			});
			layer.load(1);
			var orderinfo = _self.orderinfo;
			orderinfo.applicatinfo = JSON.stringify(applicatinfo);
			$.ajax({
            	url: '${base}/admin/visaJapan/saveRealInfoData.html',
            	data:orderinfo,
            	dataType:"json",
            	type:'post',
            	success: function(data){
					layer.closeAll('loading');
					parent.successCallBack(2);
					closeWindow();
              	}
            });
		}

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		//前台实收
		function frontRvenue(){
			var frontdata = $('.frontdata').first();
			if(frontdata.hasClass('none')){
				$('.frontdata').each(function(){
					$(this).removeClass('none');					
				});
				$('.visadata').each(function(){
					$(this).addClass('none');					
				});
			}else{
				$('.frontdata').each(function(){
					$(this).addClass('none');					
				});
				$('.visadata').each(function(){
					$(this).removeClass('none');					
				});
			}
		}
	</script>
</body>
</html>
