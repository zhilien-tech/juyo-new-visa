/*
 * 销售-日本的js文件     listCard.js
 * 
 * */
//console.log(666666);

new Vue({
	el: '#card',
	data: {
		url:
		orderData:[
		    {
				orderNumber:"170808-FR0001",
				orderState:"移交签证",
				number:2,
				everybodyInfo:[
				     {companyAbbreviation:"网先僧",passportNo:"316252136"},
				     {companyAbbreviation:"事实上",passportNo:"022251229"}
				]
			},
			{
				orderNumber:"170808-FR0002",
				orderState:"移交签证",
				number:3,
				everybodyInfo:[
				     {companyAbbreviation:"利奴斯",passportNo:"3162598936"},
				     {companyAbbreviation:"六李思三",passportNo:"022251229"},
				     {companyAbbreviation:"哈哈哈",passportNo:"666231203"}
				]
			},
			{
				orderNumber:"170808-FR0003",
				orderState:"移交签证",
				number:4,
				everybodyInfo:[
				     {companyAbbreviation:"打码量",passportNo:"035602136"},
				     {companyAbbreviation:"网显色",passportNo:"235502139"},
				     {companyAbbreviation:"张先森",passportNo:"310002136"},
				     {companyAbbreviation:"liuliu",passportNo:"022251229"}
				]
			},
			{
				orderNumber:"170808-FR0004",
				orderState:"移交签证",
				number:4,
				everybodyInfo:[
				     {companyAbbreviation:"的生日",passportNo:"066602136"},
				     {companyAbbreviation:"发发发",passportNo:"235502999"},
				     {companyAbbreviation:"电饭锅",passportNo:"318882136"},
				     {companyAbbreviation:"规划局",passportNo:"022251000"}
				]
			}
		]
	}
});



//卡片式 label宽度设置
$(".everybody-info").each(function(){
	$(this).find('div').each(function(index){
		var $thisLabel = $(this).find('label');
		var labelLength = ($thisLabel.text()).length;
		if(labelLength == 3){//label 两个字时
			$thisLabel.css("width","36px");
		}else if(labelLength == 4){//label 三个字时
			$thisLabel.css("width","48px");
		}else if(labelLength == 5){//label 四个字时
			$thisLabel.css("width","60px");
		}
	});
});

//除第一行label有列名 其他行列名全部清除
$(".card-list").each(function(){
	$(this).find(".everybody-info").not(":first").find("label").empty();
});






/* layer添加 
function add(){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '550px'],
		content: BASE_PATH + '/admin/hotel/add.html'
	});
}
 layer编辑 
function edit(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '550px'],
		content: BASE_PATH + '/admin/hotel/update.html?id='+id
	});
}

//删除提示
function deleteById(id) {
	layer.confirm("您确认要删除吗？", {
		title:"删除",
		btn: ["是","否"], //按钮
		shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = BASE_PATH + '/admin/hotel/delete.html';
		$.ajax({
			type : 'POST',
			data : {
				id : id
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				layer.msg("删除成功",{time:2000});
				datatable.ajax.reload();
			},
			error : function(xhr) {
				layer.msg("删除失败",{time:2000});
			}
		});
	}, function(){
		// 取消之后不用处理
	});
}
*/