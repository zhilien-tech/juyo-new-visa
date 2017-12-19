/*
 * 签证日本 编辑   visaDetail.js
 * @author fan
 *
 */

$(function(){
	//签证类型  按钮的点击状态
	$(".viseType-btn input").click(function(){
		if($(this).hasClass('btnState-true')){
			$(this).removeClass('btnState-true');
		}else{
			$(this).addClass('btnState-true');
			var btnInfo=$(this).val();//获取按钮的信息
			console.log(btnInfo);
		}
	});
	$('#visatype').change(function(){
		var thisval = $(this).val();
		if(thisval == 2){
			$('#visacounty').show();
			$('#threefangwen').show();
		}else{
			$('#visacounty').hide();
			$('#threefangwen').hide();
		}
	});

	$('#isVisit').change(function(){
		var thisval = $(this).val();
		if(thisval == 1){
			$('#threexian').show();
		}else{
			$('#threexian').hide();
		}
	});

	//主申请人
	/*$('#applicantTable').DataTable({
		"autoWidth":true,
		"ordering": false,
		"searching":false,
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/references/public/plugins/datatables/cn.json"
		}
	});*/

	//生成行程安排 click按钮
	/*$(".schedulingBtn").click(function(){
		$("#schedulingTable").toggle();
	});*/
});


function schedulingEdit(){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['800px', '400px'],
		content: '/admin/visaJapan/schedulingEdit.html'
	});
}

var orderobj;
//VUE准备数据
//orderinfo订单信息 travelinfo出行信息 visainfo 签证信息 applyinfo申请人信息 travelplan行程安排
new Vue({
	el: '#wrapper',
	data: {orderinfo:"",
		travelinfo:"",
		applyinfo:"",
		travelplan:""

	},
	created:function(){
		orderobj=this;
		var url = BASE_PATH + '/admin/visaJapan/getJpVisaDetailData.html';
		$.ajax({ 
			url: url,
			dataType:"json",
			data:{orderid:orderid},
			type:'post',
			success: function(data){
				orderobj.orderinfo = data.orderinfo;
				orderobj.travelinfo = data.travelinfo;
				orderobj.applyinfo = data.applyinfo;
				orderobj.travelplan = data.travelplan;
			}
		});
	},
	methods:{
		schedulingEdit:function(planid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['800px', '400px'],
				content: '/admin/visaJapan/schedulingEdit.html?planid='+planid
			});
		},
		resetPlan:function(planid){
			$.ajax({ 
				url: '/admin/visaJapan/resetPlan.html',
				dataType:"json",
				data:{planid:planid,orderid:orderid},
				type:'post',
				success: function(data){
					orderobj.travelplan = data;
					layer.msg('重置成功');
				}
			});
		},
		//签证信息
		visa:function(applyid){
			//window.location.href = '/admin/visaJapan/visaInput.html?applyid='+applyid;
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/visaInfo.html?id='+applyid+'&orderid='+orderid+'&isOrderUpTime'
			});
		},
		//护照信息
		/*passport:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false, 
				area: ['900px', '550px'],
				content: '/admin/visaJapan/passportInfo.html?applyId='+applyId
			});
		},*/
		//修改护照信息
		passport : function(applyid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/passportInfo.html?applicantId='+applyid+'&orderid='+orderid

			});
		},
		//基本信息
		updateApplicant:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/updateApplicant.html?id='+applyId+'&orderid='+orderid
			});
		},
		//签证录入
		visainput:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['1000px', '750px'],
				content: '/admin/visaJapan/visaInput.html?applyid='+applyId
			});
		}
	}
});
function commitdata(){
	//绑定日期数据
	orderobj.orderinfo.gotripdate = $('#gotripdate').val();
	orderobj.orderinfo.backtripdate = $('#backtripdate').val();
	orderobj.orderinfo.sendvisadate = $('#sendvisadate').val();
	orderobj.orderinfo.number = $('#number').val();
	orderobj.orderinfo.money = $('#money').val();
	orderobj.orderinfo.stayday = $('#stayday').val();
	orderobj.orderinfo.outvisadate = $('#outvisadate').val();
	orderobj.orderinfo.sendvisanum = $('#sendvisanum').val();
	orderobj.travelinfo.goDate = $('#goDate').val();
	orderobj.travelinfo.returnDate = $('#returnDate').val();
	//绑定签证城市
	var visacounty = "";
	$('[name=visacounty]').each(function(){
		if($(this).hasClass('btnState-true')){
			visacounty += $(this).val() + ',';
		}
	});
	if(visacounty){
		visacounty = visacounty.substr(0,visacounty.length-1);
	}
	orderobj.orderinfo.visacounty = visacounty;
	//绑定三年城市
	var threecounty = "";
	$('[name=threecounty]').each(function(){
		if($(this).hasClass('btnState-true')){
			threecounty += $(this).val() + ',';
		}
	});
	if(threecounty){
		threecounty = threecounty.substr(0,threecounty.length-1);
	}
	orderobj.orderinfo.threecounty = threecounty;

	//多程信息
	var multiways = [];
	$('.duochengdiv').each(function(index,name){
		var multiway = {};
		//出发日期
		var departuredate = $(this).find('[name=departuredate]').val();
		var isnull = departuredate;
		multiway.departureDate = departuredate;
		//出发城市
		var departurecity = $(this).find('[name=departurecity]').val();
		if (departurecity) {
			departurecity = departurecity.join(',');
			isnull += '111';
		}else{
			departurecity = '';
			isnull += '';
		}
		multiway.departureCity = departurecity;
		//抵达城市
		var arrivedcity = $(this).find('[name=arrivedcity]').val();
		if (arrivedcity) {
			arrivedcity = arrivedcity.join(',');
			isnull += '111';
		}else{
			arrivedcity = '';
			isnull += '';
		}
		multiway.arrivedCity = arrivedcity;
		// 航班号
		var flightnum = $(this).find('[name=flightnum]').val();
		if (flightnum) {
			flightnum = flightnum.join(',');
			isnull += '111';
		}else{
			flightnum = '';
			isnull += '';
		}
		multiway.flightNum = flightnum;
		if(isnull){
			multiways.push(multiway);
		}
	});
	var editdata = orderobj.orderinfo;
	editdata.travelinfo = JSON.stringify(orderobj.travelinfo);
	editdata.multiways = JSON.stringify(multiways);
	console.log("orderinfo:"+JSON.stringify(editdata));
	layer.load(1);
	$.ajax({ 
		url: BASE_PATH + '/admin/visaJapan/saveJpVisaDetailInfo.html',
		dataType:"json",
		data:editdata,
		type:'post',
		success: function(data){
			layer.closeAll('loading');
			window.location.reload();
		}
	}); 
}

//加载select2
$('.select2City').select2({
	ajax : {
		url : BASE_PATH + "/admin/city/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			/*var cArrivalcity = $('#cArrivalcity').val();
			if(cArrivalcity){
				cArrivalcity = cArrivalcity.join(',');
			}*/
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.city; // replace pk with your identifier
				/*obj.text = obj.dictCode;*/
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	//templateSelection: formatRepoSelection,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//出发航班select2
$('#goFlightNum').select2({
	ajax : {
		url : BASE_PATH + "/admin/flight/getFlightSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			/*var cArrivalcity = $('#cArrivalcity').val();
			if(cArrivalcity){
				cArrivalcity = cArrivalcity.join(',');
			}*/
			//去程出发城市
			var goDepartureCity = $('#goDepartureCity').val();
			if (goDepartureCity) {
				goDepartureCity = goDepartureCity.join(',');
			}else{
				goDepartureCity = '';
			}
			//去程抵达城市
			var goArrivedCity = $('#goArrivedCity').val();
			if (goArrivedCity) {
				goArrivedCity = goArrivedCity.join(',');
			}else{
				goArrivedCity = '';
			}
			return {
				//exname : cArrivalcity,
				gocity:goDepartureCity,
				arrivecity:goArrivedCity,
				flight : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				//obj.text = obj.flightnum; // replace pk with your identifier
				obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
				/*obj.text = obj.dictCode;*/
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	//templateSelection: formatRepoSelection,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//返程航班select2
$('#returnFlightNum').select2({
	ajax : {
		url : BASE_PATH + "/admin/flight/getFlightSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			/*var cArrivalcity = $('#cArrivalcity').val();
			if(cArrivalcity){
				cArrivalcity = cArrivalcity.join(',');
			}*/
			//去程出发城市
			var returnDepartureCity = $('#returnDepartureCity').val();
			if (returnDepartureCity) {
				returnDepartureCity = returnDepartureCity.join(',');
			}else{
				returnDepartureCity += '';
			}
			//去程抵达城市
			var returnArrivedCity = $('#returnArrivedCity').val();
			if (returnArrivedCity) {
				returnArrivedCity = returnArrivedCity.join(',');
			}else{
				returnArrivedCity += '';
			}
			return {
				//exname : cArrivalcity,
				gocity:returnDepartureCity,
				arrivecity:returnArrivedCity,
				flight : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				//obj.text = obj.flightnum; // replace pk with your identifier
				obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
				/*obj.text = obj.dictCode;*/
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	//templateSelection: formatRepoSelection,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//去程出发城市
$("#goDepartureCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程抵达城市
	var thistext = $(this).text();
	console.log(thistext);
	$("#returnArrivedCity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
	orderobj.travelinfo.godeparturecity = thisval;
	orderobj.travelinfo.returnarrivedcity = thisval;
});
//去程抵达城市
$("#goArrivedCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程出发城市
	var thistext = $(this).text();
	console.log(thistext);
	$("#returnDepartureCity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
	orderobj.travelinfo.goarrivedcity = thisval;
	orderobj.travelinfo.returndeparturecity = thisval;
});
//回程出发城市
$("#returnDepartureCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	orderobj.travelinfo.returndeparturecity = thisval;
});
//回程抵达城市
$("#returnArrivedCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	orderobj.travelinfo.returnarrivedcity = thisval;
});
//出发航班
$("#goFlightNum").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	orderobj.travelinfo.goflightnum = thisval;
});
//返回航班
$("#returnFlightNum").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	orderobj.travelinfo.returnflightnum = thisval;
});
//去程出发城市
$("#goDepartureCity").on("select2:unselect",function(e){
	orderobj.travelinfo.godeparturecity = '';
	orderobj.travelinfo.returnarrivedcity = '';
	$("#returnArrivedCity").empty();
	$(this).text('');
});
//去程抵达城市
$("#goArrivedCity").on("select2:unselect",function(e){
	orderobj.travelinfo.goarrivedcity = '';
	orderobj.travelinfo.returndeparturecity = '';
	$("#returnDepartureCity").empty();
	$(this).text('');
});
//回程出发城市
$("#returnDepartureCity").on("select2:unselect",function(e){
	orderobj.travelinfo.returndeparturecity = '';
});
//回程抵达城市
$("#returnArrivedCity").on("select2:unselect",function(e){
	orderobj.travelinfo.returnarrivedcity = '';
});
//出发航班
$("#goFlightNum").on("select2:unselect",function(e){
	orderobj.travelinfo.goflightnum = '';
});
//返回航班
$("#returnFlightNum").on("select2:unselect",function(e){
	orderobj.travelinfo.returnflightnum = '';
});

//生成行程安排
$(".schedulingBtn").click(function(){
	//var goArrivedCity = orderobj.travelinfo.goarrivedcity;
	var goArrivedCity = $('#goArrivedCity').val();
	if (goArrivedCity) {
		goArrivedCity = goArrivedCity.join(',');
	}else{
		goArrivedCity += '';
	}
	var goFlightNum = $('#goFlightNum').val();
	if (goFlightNum) {
		goFlightNum = goFlightNum.join(',');
	}else{
		goFlightNum += '';
	}
	var returnFlightNum = $('#returnFlightNum').val();
	if (returnFlightNum) {
		returnFlightNum = returnFlightNum.join(',');
	}else{
		returnFlightNum += '';
	}
	var goDate = $('#goDate').val();
	var returnDate = $('#returnDate').val();
	var triptype = $('#triptype').val();
	//多程信息
	var multiways = [];
	$('.duochengdiv').each(function(index,name){
		var multiway = {};
		//出发日期
		var departuredate = $(this).find('[name=departuredate]').val();
		multiway.departureDate = departuredate;
		//出发城市
		var departurecity = $(this).find('[name=departurecity]').val();
		if (departurecity) {
			departurecity = departurecity.join(',');
		}else{
			departurecity = '';
		}
		multiway.departureCity = departurecity;
		//抵达城市
		var arrivedcity = $(this).find('[name=arrivedcity]').val();
		if (arrivedcity) {
			arrivedcity = arrivedcity.join(',');
		}else{
			arrivedcity = '';
		}
		multiway.arrivedCity = arrivedcity;
		// 航班号
		var flightnum = $(this).find('[name=flightnum]').val();
		if (flightnum) {
			flightnum = flightnum.join(',');
		}else{
			flightnum = '';
		}
		multiway.flightNum = flightnum;
		multiways.push(multiway);
	});
	layer.load(1);
	$.ajax({ 
		url: '/admin/visaJapan/generatePlan.html',
		dataType:"json",
		data:{orderid:orderid,
			goArrivedCity:goArrivedCity,
			goDate:goDate,
			returnDate:returnDate,
			triptype:triptype,
			multiways:JSON.stringify(multiways),
			returnFlightNum:returnFlightNum,
			goFlightNum:goFlightNum},
		type:'post',
		success: function(data){
			layer.closeAll('loading');
			if(data.status == 'success'){
				orderobj.travelplan = data.data;
				layer.msg('生成成功');
			}else{
				layer.msg(data.message);
			}
		}
	});
});

function downLoadFile(){
	$.fileDownload("/admin/visaJapan/downloadFile.html?orderid=" + orderobj.orderinfo.orderid, {
        successCallback: function (url) {
        },
        failCallback: function (html, url) {
       	layer.msg("下载失败");
        }
    });
}
$(document).on("input","#stayday",function(){
	var gotripdate = $('#gotripdate').val();
	orderobj.orderinfo.gotripdate = gotripdate;
	var thisval = $(this).val();
	thisval = thisval.replace(/[^\d]/g,'');
	$(this).val(thisval);
	if(gotripdate && thisval){
		$.ajax({ 
			url: '/admin/visaJapan/autoCalculateBackDate.html',
			dataType:"json",
			data:{gotripdate:gotripdate,stayday:thisval},
			type:'post',
			success: function(data){
				$('#backtripdate').val(data);
			}
		});
	}
});