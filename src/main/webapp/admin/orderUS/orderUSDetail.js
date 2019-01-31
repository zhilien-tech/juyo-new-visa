//酒店检索
$("#hotelname").on('input',function(){
	$("#hotelname").nextAll("ul.ui-autocomplete").remove();
	plancity = $("#plancity").val();
	if(plancity){
		plancity = plancity.join(',');
	}
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			plancity : plancity,
			searchstr : $("#hotelname").val()
		},
		url : BASE_PATH+'/admin/neworderUS/selectUSHotel.html',
		success : function(data) {
			if(data != ""){
				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' style='border: 1px solid #7a9cd3;' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
				$.each(data,function(index,element) { 
					liStr += "<li onclick='setProvince("+JSON.stringify(element.nameen)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element.nameen+"</span></li>";
				});
				liStr += "</ul>";
				$("#hotelname").after(liStr);
			}
		}
	});
});
//省份上下键
var provinceindex = -1;
$(document).on('keydown','#hotelname',function(e){
	
	if(e == undefined)
		e = window.event;
	
	switch(e.keyCode){
	case 38:
		e.preventDefault();
		provinceindex--;
		if(provinceindex ==0) provinceindex = 0;
		break;
	case 40:
		e.preventDefault();
		provinceindex++;
		if(provinceindex ==5) provinceindex = 0;
		break;
	case 13:
		
		$(this).val($(this).next().find('li:eq('+provinceindex+')').children().html());
		$("#hotelname").nextAll("ul.ui-autocomplete").remove();
		$("#hotelname").blur();
		var province = $("#hotelname").val();
		setProvince(province);
		provinceindex = -1;
		break;
	}
	var li = $(this).next().find('li:eq('+provinceindex+')');
	li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
});
//省份 检索下拉项
function setProvince(province){
	$("#hotelname").nextAll("ul.ui-autocomplete").remove();
	$("#hotelname").val(province).change();
} 
$("#provinceDiv").mouseleave(function(){
	$("#hotelname").nextAll("ul.ui-autocomplete").remove();
});

//不能输入中文
$(document).on("input","#hotelname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var reg = /([\u4E00-\u9FA5])+/;
	//var reg =/[\u4e00-\u9fa5]/g;
	if(reg.test(temp)){
		$("#hotelname").val(temp.replace(reg, ""));
    }
});