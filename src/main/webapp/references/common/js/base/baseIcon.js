/*
 *
 *列表图标提示语 js文件  baseIcon.js
 * 
 */
	/*--------------------table---------------------*/
	$(document).on("mouseover",".edit-icon",function(){
		var t = "编辑";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".delete-icon",function(){
		var t = "删除";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	/*------------------end table--------------------*/
	
	$(document).on("mouseover",".salesBtn",function(){
		var t = "通知销售";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});	
	
	$(document).on("mouseover",".viseBtn",function(){
		var t = "签证补录";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});	
	/*---------------------card-----------------------*/
	$(document).on("mouseover",".edit",function(){
		var t = "编辑";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".share",function(){
		var t = "分享";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".theTrial",function(){
		var t = "初审";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".return",function(){
		var t = "回邮";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".toVoid",function(){
		var t = "作废";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	$(document).on("mouseover",".toVoid1",function(){
		var t = "还原";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".basicInfo",function(){
		var t = "基本信息";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".passportInfo",function(){
		var t = "护照信息";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".visaInfo",function(){
		var t = "签证信息";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".qualified",function(){
		var t = "合格";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".unqualified",function(){
		var t = "不合格";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".express",function(){
		var t = "快递";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".addressNotice",function(){
		var t = "地址通知";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".shiShou",function(){
		var t = "实收";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".sendZB",function(){
		var t = "发招宝";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".ZBchange",function(){
		var t = "招宝变更";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".ZBcancel",function(){
		var t = "招宝取消";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".download",function(){
		var t = "下载";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".handoverTable",function(){
		//var t = "交接表";
		var t = "送签";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".visaInput",function(){
		var t = "签证录入";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".Refusal",function(){
		var t = "报告拒签";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".afterSales",function(){
		var t = "售后";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".guiGuoUpload",function(){
		var t = "归国上传";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".videoShoot",function(){
		var t = "拍视频";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".visaTransfer",function(){
		var t = "签证移交";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".sendSms",function(){
		var t = "发短信";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	$(document).on("mouseover",".updateApplicant",function(){
		var t = "基本信息";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".passport",function(){
		var t = "护照信息";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".visa",function(){
		var t = "签证信息";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".otherVisa",function(){
		var t = "其他证件";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	$(document).on("mouseover",".deleteIcon",function(){
		var t = "删除";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	$(document).on("mouseover",".visaEntry",function(){
		var t = "签证录入";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	/*--------------------end card---------------------*/
	
	
	
	
	
	
