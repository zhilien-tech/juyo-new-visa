
$(function(){
	$('.kalendae').attr('id','kalendae');
	var spanVal=document.getElementById('kalendae').getElementsByTagName("span");
	for(var i=0;i<spanVal.length;i++){
				var dataVal=spanVal[i].getAttribute("data-date");//.substring(5,10);
				if (dataVal!=null) {
					var valu=dataVal.substring(5,10);
					//alert(valu);
					if (valu=='12-25') {
					 $(".k-days span[data-date*='12-25']").text('圣诞');
					};
				};
			}

	//alert('calendar.js 结束');

});















