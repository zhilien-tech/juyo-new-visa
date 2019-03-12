$('.addApplicantBtn').click(function(){
	addApplicant();
});
//历史订单号回车
function onkeyEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode == 13){
    	$.ajax({
    		type : 'POST',
    		data : {
    			ordernum:$("#historyOrdernum").val(),
    			orderid:$('#orderid').val()
    		} ,
    		async: false,
    		url : '/admin/simple/getInfobyOrdernum.html',
    		success : function(data) {
    			//layer.closeAll("loading");
    			console.log(JSON.stringify(data));
    			if(data.errMsg == ""){
    				$('#orderid').val(data.orderid);
    				initTravalPlanTable(data.data);
    				setFresh(data);
    				layer.msg("操作成功");
    			}else{
    				layer.msg(data.errMsg);
    			}
    			
    		},
    		error : function() {
    			console.log("error");
    		}
    	});
	 }
}

function setFresh(data){
	//setCustomerinfo(data);
	setOrderinfo(data);
	setTripinfo(data);
	
	 
}

function setCustomerinfo(data){
	if(data.customer.id){
		$('#customerid').val(data.customer.id);
		$("#customerType").val(data.customer.source);
		if(data.customer.source == 4){
			$('.zhiKe').removeClass('none');
			$('.on-line').addClass('none');
			$('#customamount').removeClass('none');
			
			$("#compName2").val(data.customer.name);
			$("#comShortName2").val(data.customer.shortname);
		}else{
			$('.zhiKe').addClass('none');
			$('.on-line').removeClass('none');
			$('#customamount').addClass('none');
			
			$('#compName').html('<option selected="selected" value="' + data.customer.name + '">' + data.customer.name + '</option>').trigger("change");
			$('#comShortName').html('<option selected="selected" value="' + data.customer.shortname + '">' + data.customer.shortname + '</option>').trigger("change");
		}
		$("#payType").val(data.customer.payType);
	}else{
		$('.zhiKe').addClass('none');
		$('.on-line').removeClass('none');
		$('#customamount').addClass('none');
		
		$('#compName').empty();
		$('#compName2').val("");
		$('#comShortName').empty();
		$('#comShortName2').val("");
		$('#customerType').val("");
		$('#payType').val(1);
	}
}
function setOrderinfo(data){
	$("#cityid").val(data.orderinfo.cityid);
	if(data.orderinfo.cityid > 2){
		$(".transfer").show();
		$(".direct").hide();
	}else{
		$(".transfer").hide();
		$(".direct").show();
	}
	$("#urgentType").val(data.orderinfo.urgenttype);
	if(data.orderinfo.urgenttype == 1){
		$("#urgentDays").addClass("none");
	}else{
		$("#urgentDays").removeClass("none");
	}
	$("#urgentDay").val(data.orderinfo.urgentday);
	$("#sendVisaDate").val(data.orderinfo.sendVisaDate);
	$("#outVisaDate").val(data.orderinfo.outVisaDate);
	$("#sendvisanum").val(data.orderinfo.sendvisanum);
	$("#visatype").val(data.orderjpinfo.visaType);
	$("#amount").val(data.orderjpinfo.amount);
}
function setTripinfo(data){
	$("#tripPurpose").val(data.tripjpinfo.trippurpose);
	//$("#triptype").val(data.tripjpinfo.triptype);
	$("#goDate").val(data.tripjpinfo.goDate);
	$("#stayday").val(data.orderinfo.stayday);
	$("#returnDate").val(data.tripjpinfo.returnDate);
	
	//alert(data.tripjpinfo.goflightnum);
	//$("#goDepartureCity").val(data.tripjpinfo.godeparturecity).trigger("change");
	//$("#goArrivedCity").val(data.tripjpinfo.goarrivedcity).trigger("change");
	//$("#returnDepartureCity").val(data.tripjpinfo.returndeparturecity).trigger("change");
	//$("#returnArrivedCity").val(data.tripjpinfo.returnarrivedcity).trigger("change");
	
	//$("#goFlightNum").val(data.tripjpinfo.goflightnum).trigger("change");
	
	
	$('#goFlightNum').html('<option selected="selected" value="' + data.tripjpinfo.goflightnum + '">' + data.tripjpinfo.goflightnum + '</option>').trigger("change");
	$('#goDepartureCity').html('<option selected="selected" value="' + data.tripjpinfo.godeparturecity + '">' + data.tripjpinfo.godeparturecityname + '</option>').trigger("change");
	$('#goArrivedCity').html('<option selected="selected" value="' + data.tripjpinfo.goarrivedcity + '">' + data.tripjpinfo.goarrivedcityname + '</option>').trigger("change");
	$('#returnFlightNum').html('<option selected="selected" value="' + data.tripjpinfo.returnflightnum + '">' + data.tripjpinfo.returnflightnum + '</option>').trigger("change");
	$('#returnDepartureCity').html('<option selected="selected" value="' + data.tripjpinfo.returndeparturecity + '">' + data.tripjpinfo.returndeparturecityname + '</option>').trigger("change");
	$('#returnArrivedCity').html('<option selected="selected" value="' + data.tripjpinfo.returnarrivedcity + '">' + data.tripjpinfo.returnarrivedcityname + '</option>').trigger("change");
	
	
	
	$('#newgodeparturecity').html('<option selected="selected" value="' + data.tripjpinfo.newgodeparturecity + '">' + data.tripjpinfo.newgodeparturecityname + '</option>').trigger("change");
	$('#gotransferarrivedcity').html('<option selected="selected" value="' + data.tripjpinfo.gotransferarrivedcity + '">' + data.tripjpinfo.gotransferarrivedcityname + '</option>').trigger("change");
	$('#gotransferflightnum').html('<option selected="selected" value="' + data.tripjpinfo.gotransferflightnum + '">' + data.tripjpinfo.gotransferflightnum + '</option>').trigger("change");
	$('#gotransferdeparturecity').html('<option selected="selected" value="' + data.tripjpinfo.gotransferdeparturecity + '">' + data.tripjpinfo.gotransferdeparturecityname + '</option>').trigger("change");
	$('#newgoarrivedcity').html('<option selected="selected" value="' + data.tripjpinfo.newgoarrivedcity + '">' + data.tripjpinfo.newgoarrivedcityname + '</option>').trigger("change");
	$('#newgoflightnum').html('<option selected="selected" value="' + data.tripjpinfo.newgoflightnum + '">' + data.tripjpinfo.newgoflightnum + '</option>').trigger("change");
	$('#newreturndeparturecity').html('<option selected="selected" value="' + data.tripjpinfo.newreturndeparturecity + '">' + data.tripjpinfo.newreturndeparturecityname + '</option>').trigger("change");
	$('#returntransferarrivedcity').html('<option selected="selected" value="' + data.tripjpinfo.returntransferarrivedcity + '">' + data.tripjpinfo.returntransferarrivedcityname + '</option>').trigger("change");
	$('#returntransferflightnum').html('<option selected="selected" value="' + data.tripjpinfo.returntransferflightnum + '">' + data.tripjpinfo.returntransferflightnum + '</option>').trigger("change");
	$('#returntransferdeparturecity').html('<option selected="selected" value="' + data.tripjpinfo.returntransferdeparturecity + '">' + data.tripjpinfo.returntransferdeparturecityname + '</option>').trigger("change");
	$('#newreturnarrivedcity').html('<option selected="selected" value="' + data.tripjpinfo.newreturnarrivedcity + '">' + data.tripjpinfo.newreturnarrivedcityname + '</option>').trigger("change");
	$('#newreturnflightnum').html('<option selected="selected" value="' + data.tripjpinfo.newreturnflightnum + '">' + data.tripjpinfo.newreturnflightnum + '</option>').trigger("change");
	
	
	/*$("#newgodeparturecity").val(data.tripjpinfo.newgodeparturecity).trigger("change");
	$("#gotransferarrivedcity").val(data.tripjpinfo.gotransferarrivedcity).trigger("change");
	$("#gotransferflightnum").val(data.tripjpinfo.gotransferflightnum).trigger("change");
	$("#gotransferdeparturecity").val(data.tripjpinfo.gotransferdeparturecity).trigger("change");
	$("#newgoarrivedcity").val(data.tripjpinfo.newgoarrivedcity).trigger("change");
	$("#newgoflightnum").val(data.tripjpinfo.newgoflightnum).trigger("change");
	$("#newreturndeparturecity").val(data.tripjpinfo.newreturndeparturecity).trigger("change");
	$("#returntransferarrivedcity").val(data.tripjpinfo.returntransferarrivedcity).trigger("change");
	$("#returntransferflightnum").val(data.tripjpinfo.returntransferflightnum).trigger("change");
	$("#returntransferdeparturecity").val(data.tripjpinfo.returntransferdeparturecity).trigger("change");
	$("#newreturnarrivedcity").val(data.tripjpinfo.newreturnarrivedcity).trigger("change");
	$("#newreturnflightnum").val(data.tripjpinfo.newreturnflightnum).trigger("change");*/
}

//添加申请人
function addApplicant(){
	var orderid = $('#orderid').val();
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/simple/addPassport.html?orderid='+orderid
	});
}

function saveZhaobaoOrder(status){
	var orderinfo = {};
	var orderid = $('#orderid').val();
	orderinfo.orderid = orderid;
	
	var visatype = $('#visatype').val();
	orderinfo.visatype = visatype;
	
	var cityid = $('#cityid').val();
	orderinfo.cityid = cityid;
	
	var goDate = $('#goDate').val();
	orderinfo.goDate = goDate;
	var stayday = $('#stayday').val();
	orderinfo.stayday = stayday;
	var returnDate = $('#returnDate').val();
	orderinfo.returnDate = returnDate;
	
	layer.load(1);
	$.ajax({
		type : 'POST',
		data : orderinfo ,
		async: false,
		url : '/admin/simple/saveZhaobaoOrderinfo.html',
		success : function(data) {
			//console.log(JSON.stringify(data));
			layer.closeAll("loading");
			if(status == 1){
				window.location.href = '/admin/simple/list.html';
			}else if(status == 2){
			}else{
				window.close();
			}
		},
		error : function() {
			console.log("error");
		}
	});
}

//下单保存  status 标注的编辑还是添加
function saveAddOrder(status){
	var orderinfo = {};
	var orderid = $('#orderid').val();
	orderinfo.orderid = orderid;
	var tripPurpose = $('#tripPurpose').val();
	orderinfo.tripPurpose = tripPurpose;
	var triptype = $('#triptype').val();
	orderinfo.triptype = triptype;
	var goDate = $('#goDate').val();
	orderinfo.goDate = goDate;
	var stayday = $('#stayday').val();
	orderinfo.stayday = stayday;
	var returnDate = $('#returnDate').val();
	orderinfo.returnDate = returnDate;
	var goDepartureCity = $('#goDepartureCity').val();
	if (goDepartureCity) {
		goDepartureCity = goDepartureCity.join(',');
	}else{
		goDepartureCity += '';
	}
	orderinfo.goDepartureCity = goDepartureCity;
	var goArrivedCity = $('#goArrivedCity').val();
	if (goArrivedCity) {
		goArrivedCity = goArrivedCity.join(',');
	}else{
		goArrivedCity += '';
	}
	orderinfo.goArrivedCity = goArrivedCity;
	var goFlightNum = $('#goFlightNum').val();
	if (goFlightNum) {
		goFlightNum = goFlightNum.join(',');
	}else{
		goFlightNum += '';
	}
	orderinfo.goFlightNum = goFlightNum;
	var returnDepartureCity = $('#returnDepartureCity').val();
	if (returnDepartureCity) {
		returnDepartureCity = returnDepartureCity.join(',');
	}else{
		returnDepartureCity += '';
	}
	orderinfo.returnDepartureCity = returnDepartureCity;
	var returnArrivedCity = $('#returnArrivedCity').val();
	if (returnArrivedCity) {
		returnArrivedCity = returnArrivedCity.join(',');
	}else{
		returnArrivedCity += '';
	}
	orderinfo.returnArrivedCity = returnArrivedCity;
	var returnFlightNum = $('#returnFlightNum').val();
	if (returnFlightNum) {
		returnFlightNum = returnFlightNum.join(',');
	}else{
		returnFlightNum += '';
	}
	orderinfo.returnFlightNum = returnFlightNum;
	
	var newgodeparturecity = $('#newgodeparturecity').val();
	if (newgodeparturecity) {
		newgodeparturecity = newgodeparturecity.join(',');
	}else{
		newgodeparturecity += '';
	}
	orderinfo.newgodeparturecity = newgodeparturecity;
	
	var gotransferarrivedcity = $('#gotransferarrivedcity').val();
	if (gotransferarrivedcity) {
		gotransferarrivedcity = gotransferarrivedcity.join(',');
	}else{
		gotransferarrivedcity += '';
	}
	orderinfo.gotransferarrivedcity = gotransferarrivedcity;
	
	var gotransferflightnum = $('#gotransferflightnum').val();
	if (gotransferflightnum) {
		gotransferflightnum = gotransferflightnum.join(',');
	}else{
		gotransferflightnum += '';
	}
	orderinfo.gotransferflightnum = gotransferflightnum;
	
	var gotransferdeparturecity = $('#gotransferdeparturecity').val();
	if (gotransferdeparturecity) {
		gotransferdeparturecity = gotransferdeparturecity.join(',');
	}else{
		gotransferdeparturecity += '';
	}
	orderinfo.gotransferdeparturecity = gotransferdeparturecity;
	
	var gotransferdeparturecity = $('#gotransferdeparturecity').val();
	if (gotransferdeparturecity) {
		gotransferdeparturecity = gotransferdeparturecity.join(',');
	}else{
		gotransferdeparturecity += '';
	}
	orderinfo.gotransferdeparturecity = gotransferdeparturecity;
	
	var newgoarrivedcity = $('#newgoarrivedcity').val();
	if (newgoarrivedcity) {
		newgoarrivedcity = newgoarrivedcity.join(',');
	}else{
		newgoarrivedcity += '';
	}
	orderinfo.newgoarrivedcity = newgoarrivedcity;
	
	var newgoflightnum = $('#newgoflightnum').val();
	if (newgoflightnum) {
		newgoflightnum = newgoflightnum.join(',');
	}else{
		newgoflightnum += '';
	}
	orderinfo.newgoflightnum = newgoflightnum;
	
	var newreturndeparturecity = $('#newreturndeparturecity').val();
	if (newreturndeparturecity) {
		newreturndeparturecity = newreturndeparturecity.join(',');
	}else{
		newreturndeparturecity += '';
	}
	orderinfo.newreturndeparturecity = newreturndeparturecity;
	
	var returntransferarrivedcity = $('#returntransferarrivedcity').val();
	if (returntransferarrivedcity) {
		returntransferarrivedcity = returntransferarrivedcity.join(',');
	}else{
		returntransferarrivedcity += '';
	}
	orderinfo.returntransferarrivedcity = returntransferarrivedcity;
	
	var returntransferflightnum = $('#returntransferflightnum').val();
	if (returntransferflightnum) {
		returntransferflightnum = returntransferflightnum.join(',');
	}else{
		returntransferflightnum += '';
	}
	orderinfo.returntransferflightnum = returntransferflightnum;
	
	var returntransferdeparturecity = $('#returntransferdeparturecity').val();
	if (returntransferdeparturecity) {
		returntransferdeparturecity = returntransferdeparturecity.join(',');
	}else{
		returntransferdeparturecity += '';
	}
	orderinfo.returntransferdeparturecity = returntransferdeparturecity;
	
	var newreturnarrivedcity = $('#newreturnarrivedcity').val();
	if (newreturnarrivedcity) {
		newreturnarrivedcity = newreturnarrivedcity.join(',');
	}else{
		newreturnarrivedcity += '';
	}
	orderinfo.newreturnarrivedcity = newreturnarrivedcity;
	
	var newreturnflightnum = $('#newreturnflightnum').val();
	if (newreturnflightnum) {
		newreturnflightnum = newreturnflightnum.join(',');
	}else{
		newreturnflightnum += '';
	}
	orderinfo.newreturnflightnum = newreturnflightnum;
	
	
	var customerType = $('#customerType').val();
	orderinfo.customerType = customerType;
	var compName = $('#compName').val();
	orderinfo.compName = compName;
	var comShortName = $('#comShortName').val();
	orderinfo.comShortName = comShortName;
	var compName2 = $('#compName2').val();
	orderinfo.compName2 = compName2;
	var comShortName2 = $('#comShortName2').val();
	orderinfo.comShortName2 = comShortName2;
	var payType = $('#payType').val();
	orderinfo.payType = payType;
	var visatype = $('#visatype').val();
	orderinfo.visatype = visatype;
	var amount = $('#amount').val();
	orderinfo.amount = amount;
	var cityid = $('#cityid').val();
	orderinfo.cityid = cityid;
	var urgentType = $('#urgentType').val();
	orderinfo.urgentType = urgentType;
	var urgentDay = $('#urgentDay').val();
	orderinfo.urgentDay = urgentDay;
	var sendvisadate = $('#sendVisaDate').val();
	orderinfo.sendvisadate = sendvisadate;
	var outvisadate = $('#outVisaDate').val();
	orderinfo.outvisadate = outvisadate;
	var sendvisanum = $('#sendvisanum').val();
	orderinfo.sendvisanum = sendvisanum;
	var customerid = $('#customerid').val();
	orderinfo.customerid = customerid;
	var zhikecustomid = $('#zhikecustomid').val();
	orderinfo.zhikecustomid = zhikecustomid;
	layer.load(1);
	$.ajax({
		type : 'POST',
		data : orderinfo ,
		async: false,
		url : '/admin/simple/saveAddOrderinfo.html',
		success : function(data) {
			//console.log(JSON.stringify(data));
			layer.closeAll("loading");
			if(status == 1){
				window.location.href = '/admin/simple/list.html';
			}else if(status == 2){
			}else{
				window.close();
			}
		},
		error : function() {
			console.log("error");
		}
	});
}