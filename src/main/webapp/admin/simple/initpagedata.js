function initApplicantTable(){
	var orderid = $('#orderid').val();
	if(orderid){
		//layer.load(1);
		$.ajax({ 
			url: '/admin/visaJapan/getVisaDetailApply.html',
			//async : false,
			dataType:"json",
			data:{orderid:orderid},
			type:'post',
			success: function(data){
				layer.closeAll("loading");
				var html = '';
				$.each(data.applyinfo,function(index,value){
					html += '<tr>';
					if(value.id == value.mainid){
						html += '<td>主</td>';
					}else{
						html += '<td></td>';
					}
					if(value.applyname != undefined){
						html += '<td>'+value.applyname+'</td>';
					}else{
						html += '<td></td>';
					}
					if(value.telephone != undefined){
						html += '<td>'+value.telephone+'</td>';
					}else{
						html += '<td></td>';
					}
					if(value.passport != undefined){
						html += '<td>'+value.passport+'</td>';
					}else{
						html += '<td></td>';
					}
					if(value.type != undefined){
						html += '<td>'+value.type+'</td>';
					}else{
						html += '<td></td>';
					}
					if(value.realinfo != undefined){
						html += '<td>'+value.realinfo+'</td>';
					}else{
						html += '<td></td>';
					}
					if(value.id == value.mainid){
						if(value.relationremark != undefined){
							html += '<td>'+value.relationremark+'</td>';
						}else{
							html += '<td></td>';
						}
					}else{
						if(value.mainrelation != undefined){
							html += '<td>'+value.mainrelation+'</td>';
						}else{
							html += '<td></td>';
						}
					}
					html += '<td><a onclick="passportInfo('+value.id+')">护照信息</a>&nbsp;';
					html += '<a onclick="updateApplicant('+value.id+')">基本信息</a>&nbsp;';
					html += '<a onclick="visaInfo('+value.id+')">签证信息</a>&nbsp;<br />';
					//html += '<a onclick="visaInput('+value.applyid+')">出境记录</a>&nbsp;';
					html += '<a onclick="deleteApplicant('+value.id+')">删除</a>&nbsp;';
					html += '</td>';
					html += '</tr>';
				});
				$('#applicantsTable').html(html);
				if(html){
					$('#mySwitch').show();
					$('#applicantInfo').hide();
				}
			}
		});
	}
}
function initTravelPlanTable(){
	var orderid = $('#orderid').val();
	if(orderid){
		//layer.load(1);
		$.ajax({ 
			url: '/admin/visaJapan/getTrvalPlanData.html',
			//async : false,
			dataType:"json",
			data:{orderid:orderid},
			type:'post',
			success: function(data){
				layer.closeAll("loading");
				var html = '';
				console.log(data);
				$.each(data,function(index,value){
					html += '<tr>';
					html += '<td>第'+value.day+'天</td>';
					html += '<td>'+value.outdate+'</td>';
					html += '<td>'+value.cityname+'</td>';
					if(value.scenic != undefined){
						html += '<td>'+value.scenic+'</td>';
					}else{
						html += '<td></td>';
					}
					if(value.hotelname != undefined ){
						//html += '<td>'+value.hotelname+'</td>';
						html += '<td><table style="width:100%;"><tr><td style="text-align:center;">'+value.hotelname+'</td></tr><tr><td style="text-align:center;">'+value.hoteladdress+'</td></tr><tr><td style="text-align:center;">'+value.hotelmobile+'</td></tr></table></td>';
					}else{
						if(index != data.length -1){
							html += '<td>連泊</td>';
						}else{
							html += '<td></td>';
						}
					}
					if(index != data.length - 1 && index != 0){
						html += '<td><i class="editHui" onclick="schedulingEdit('+value.id+')"></i><i class="resetHui" onclick="resetPlan('+value.id+')"></i></td>';
					}
					html += '</tr>';
				});
				$('#travelplantbody').html(html);
			}
		});
	}
}

function schedulingEdit(planid){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['800px', '400px'],
		content: '/admin/visaJapan/schedulingEdit.html?planid='+planid+'&visatype='+visatype
	});
}
function resetPlan(planid){
	var orderid = $('#orderid').val();
	$.ajax({ 
		url: '/admin/visaJapan/resetPlan.html',
		dataType:"json",
		data:{planid:planid,orderid:orderid,visatype:visatype},
		type:'post',
		success: function(data){
			initTravelPlanTable();
			layer.msg('重置成功');
		}
	});
}


/*$('.datetimepickercss').each(function(){
	$(this).datetimepicker({
		format: 'yyyy-mm-dd',
		language: 'zh-CN',
		autoclose: true,//选中日期后 自动关闭
		pickerPosition:"top-left",//显示位置
		minView: "month"//只显示年月日
	});
});*/


/*$("#sendVisaDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
}).on("changeDate",function(){
	//自动计算预计出签时间
	var stayday = 7;
	var sendvisadate = $("#sendVisaDate").val();
	$.ajax({ 
		url: '/admin/visaJapan/autoCalculateBackDate.html',
		dataType:"json",
		data:{gotripdate:sendvisadate,stayday:stayday+1},
		type:'post',
		success: function(data){
			$("#outVisaDate").val(data); 
		}
	});
});
*/
$(document).on("input","#stayday",function(){
	var gotripdate = $('#goDate').val();
	var thisval = $(this).val();
	thisval = thisval.replace(/[^\d]/g,'');
	$(this).val(thisval);
	if(!thisval){
		$('#returnDate').val('');
	}
	if(gotripdate && thisval){
		$.ajax({ 
			url: '/admin/visaJapan/autoCalculateBackDate.html',
			dataType:"json",
			data:{gotripdate:gotripdate,stayday:thisval},
			type:'post',
			success: function(data){
				$('#backtripdate').val(data);
				//设置出行信息返回日期的时间
				var triptype = $('#triptype').val();
				if(triptype == 1){
					//往返设置返回日期
					$('#returnDate').val(data);
				}
			}
		});
	}
});
$(document).on("focus",".select2-search__field",function(){
	var thisval = $(this).val();
	if(!thisval){
		$(this).val(' ');
		$(this).trigger("input");
		$(this).val('');
	}
});