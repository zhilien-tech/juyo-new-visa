<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/sale" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>初审 - 快递</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-switch.min.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		#datatableId tbody tr td:nth-child(1){width:10%;}
		#datatableId tbody tr td:nth-child(2){width:18%;}
		#datatableId tbody tr td:nth-child(3){width:13%;}
		#datatableId tbody tr td:nth-child(4){width:12%;}
		#datatableId tbody tr td:nth-child(5){width:37%;}
		#datatableId tbody tr td:nth-child(6){width:10%;}
		#tableId{position: relative;top: 15px;}
		/*左右滑块*/
		.switch{width:41px; display:inline-block;padding-left: 8px;}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="expressForm">
			<div class="modal-header">
				<span class="heading">快递</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="row">
						<div class="col-sm-3">
							<div class="form-group">
								<select id="express" class="form-control input-sm selectHeight">
									<option value="1">快递</option>
									<option value="2">门店送</option>
									<option value="3">自送</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row form-div">
						<div class="col-sm-3">
							<div class="form-group">
								<label>收件人：</label> 
								<input id="" name="remark" type="text" class="form-control input-sm" v-model=""/>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group">
								<label>电话：</label> 
								<input id="" name="remark" type="text" class="form-control input-sm" v-model=""/>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label>邮寄地址：</label> 
								<input id="" name="remark" type="text" class="form-control input-sm" v-model=""/>
							</div>
						</div>
					</div>
					<table id="tableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>姓名</span></th>
								<th><span>电话</span></th>
								<th><span>邮箱</span></th>
								<th><span>资料类型</span></th>
								<th><span>审核确认资料</span></th>
								<th><span>统一联系人</span></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>王思思</td>
								<td>010-2562320</td>
								<td>wangsisi@163.com</td>
								<td>在职</td>
								<td>护照、身份证、一寸照片、退休证明、健康证明、学生证</td>
								<td>
									<div class="switch">  
									 	<input type="checkbox" id="multiPass_roundTrip" checked data-on-text=" " data-off-text=" "/>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap-switch.min.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			$('#multiPass_roundTrip').bootstrapSwitch();//统一联系人
			$('#multiPass_roundTrip').on('switchChange.bootstrapSwitch', function (event,state) { 
	            if(state){//统一
	            	console.log("统一");
	            	event.target.parentNode.parentNode.style="background-color:#3087f1 !important";
	            } else {//不统一
	            	console.log("不统一");
	            	event.target.parentNode.parentNode.style="background-color:#ddd !important";
	            }
	    	});
			
			//快递下拉框
			$("#express").change(function(){
				var expressVal = $(this).val();
				if(expressVal == "1"){//快递
					$(".form-div").show();
				 	$("#tableId").show();
				 	$("#tableId").find("th:eq(5)").show();//显示统一联系人
				 	$("#tableId").find("td:eq(5)").show();
				}else if(expressVal == "2"){//门店送
					$(".form-div").show();
				 	$("#tableId").show();
					$("#tableId").find("th:eq(5)").hide();//隐藏统一联系人
					$("#tableId").find("td:eq(5)").hide();
				}else{//自送
					$(".form-div").hide();
				 	$("#tableId").hide();
				}
					
			});
			
			
			/* //保存护照信息
			$(document).on('blur','.passportInp',function(){
				var thisval = $(this).val();
				var applicatid = $(this).parent().find('#applicatid').val();
				$.ajax({ 
	            	url: '${base}/admin/visaJapan/editPassportCount.html',
	            	data:{applicatid:applicatid,inputVal:thisval},
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	              	}
	            });
				$(this).parent().find("span").each(function(index,value){
					if($(this).text().indexOf("护照") !== -1){
						$(this).text(thisval);
						$(".passportInp").remove();
						$(this).removeClass("titleStyle");
					}
				});
			});*/
		});

		//vue表格数据对象
	    /*var _self;
		new Vue({
			el: '#expressForm',
			data: {orderinfo:""},
			/* created:function(){
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
				var datatext = '';
				$(this).find('.titleStyle').each(function(index){
					datatext += $(this).text() + ',';
				});
				datatext = datatext.substring(0, datatext.length-1);
				applicatobj.datatext = datatext;
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
					parent.successCallBack(1);
					closeWindow();

              	}
            });
		} */

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>
