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

