$('.addApplicantBtn').click(function(){
	addApplicant();
});
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
		content:'/admin/simple/passportInfo.html?orderid='+orderid
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
			console.log(JSON.stringify(data));
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