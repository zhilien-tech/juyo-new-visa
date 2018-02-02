//只能输入数字
$(document).on("input",".mustNumber",function(){
	$(this).val($(this).val().replace(/[^\d]/g,''));
});
//只能输入数字（带小数点）
$(document).on("input",".mustNumberPoint",function(){
	$(this).val($(this).val().replace(/[^.\d]/g,''));
});

//只能输入时间 （例如12:00）
$(document).on("input",".mustTimes",function(){
	$(this).val($(this).val().replace(/[^\d]/g,''));
	/*if($(this).val().length == 2){
		$(this).val($(this).val()+':');
	}else if($(this).val().length > 5){	*/
		$(this).val($(this).val().substr(0,4));
	//}
});
//只能输入时间 （例如12:00）
$(document).on("input",".mustArriveTimes",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36))
		   return;
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	$(this).val($(this).val().replace(/[^-+\d]/g,''));
	if($(this).val().length > 6){
		var afterstr = $(this).val().substr(pos-(temp.length-$(this).val().length),$(this).val().length);
		var beforestr = $(this).val().substr(0,pos-1);
		$(this).val(beforestr + afterstr);
	}
	pos=pos-(temp.length-$(this).val().length);//当前光标位置
	setCaretPosition($(this)[0],pos);//设置光标
	/*if($(this).val().length == 2){
		$(this).val($(this).val()+':');
	}else if($(this).val().length > 7){	*/
	//}
});
//获取光标位置
(function($){
	$.fn.extend({
		// 获取当前光标位置的方法
		getCurPos:function() {
			var getCurPos = '';
			if ( navigator.userAgent.indexOf("MSIE") > -1 ) {  // IE
				// 创建一个textRange,并让textRange范围包含元素里所有内容
				var all_range = document.body.createTextRange();all_range.moveToElementText($(this).get(0));$(this).focus();
				// 获取当前的textRange,如果当前的textRange是一个具体位置而不是范围,则此时textRange的范围是start到end.此时start等于end
				var cur_range = document.selection.createRange();
				// 将当前textRange的start,移动到之前创建的textRange的start处,这时,当前textRange范围就变成了从整个内容的start处,到当前范围end处
				cur_range.setEndPoint("StartToStart",all_range);
				// 此时当前textRange的Start到End的长度,就是光标的位置
				curCurPos = cur_range.text.length;
			} else {
				// 获取当前元素光标位置
				curCurPos = $(this).get(0).selectionStart;
			}
			// 返回光标位置
			return curCurPos;
		}
	});
})(jQuery);
/*
定位光标
*/
function setCaretPosition(ctrl, pos){
    if(ctrl.setSelectionRange)
    {
        ctrl.focus();
        ctrl.setSelectionRange(pos,pos);
    }
    else if (ctrl.createTextRange) {
        var range = ctrl.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
}
document.write('<script language=javascript src="/references/common/js/pinyin.js"></script>');
//联想拼音
$(document).on("input","#firstName",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	//var hanzi = temp.split('/')[0];
	var pinyinchar = getPinYinStr(temp);
	var dex = temp.length;
	//$("#firstNameEn").css('left',200);
	if($(this).val().length == 0){
		$("#firstNameEn").val("");
	}else{
		$("#firstNameEn").val("/"+pinyinchar.toUpperCase());
	}
	//斜杠的位置
	//var sepindex = hanzi.length + 1;
	//设置值
	//pos=pos-(temp.length-$(this).val().length);//当前光标位置
	/*if(pos < sepindex){
		$(this).val(hanzi+'/'+pinyinchar.toUpperCase());
//		setCaretPosition($(this)[0],pos);//设置光标
	}else{
		$(this).val(hanzi+'/'+temp.split('/')[1].toUpperCase());
	}
	setCaretPosition($(this)[0],pos);//设置光标
*/});
$(document).on("input","#lastName",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	//var hanzi = temp.split('/')[0];
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#lastNameEn").val("");
	}else{
		$("#lastNameEn").val("/"+pinyinchar.toUpperCase());
	}
	//斜杠的位置
	//var sepindex = hanzi.length + 1;
	//设置值
	//pos=pos-(temp.length-$(this).val().length);//当前光标位置
	/*if(pos < sepindex){
		$(this).val(hanzi+'/'+pinyinchar.toUpperCase());
//		setCaretPosition($(this)[0],pos);//设置光标
	}else{
		$(this).val(hanzi+'/'+temp.split('/')[1].toUpperCase());
	}
	setCaretPosition($(this)[0],pos);//设置光标
	 */});
$(document).on("input","#otherFirstName",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#otherFirstNameEn").val("");
	}else{
		$("#otherFirstNameEn").val("/"+pinyinchar.toUpperCase());
	}
});
$(document).on("input","#otherLastName",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#otherLastNameEn").val("");
	}else{
		$("#otherLastNameEn").val("/"+pinyinchar.toUpperCase());
	}
});
$(document).on("input","#birthAddress",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#birthAddressEn").val("");
	}else{
		$("#birthAddressEn").val("/"+pinyinchar.toUpperCase());
	}
});
$(document).on("input","#issuedPlace",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#issuedPlaceEn").val("");
	}else{
		$("#issuedPlaceEn").val("/"+pinyinchar.toUpperCase());
	}
});
/*$(document).on("input","#cardId",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var card = $(this).val();
	if(card.length == 18){
		
		layer.load(1);
		$.ajax({
			type: 'POST',
			data : {
				cardId : card
			},
			url: 'getAllInfoByCard',
			success :function(data) {
				console.log(JSON.stringify(data));
				layer.closeAll('loading');
				$("#sex").val(data.sex);
				$("#birthday").val(data.birthday);
				$('#cardProvince').val(data.province.province);
				$('#cardCity').val(data.province.city);
			}
		});
		var str="";  
		//是否同身份证相同
		$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
			str=$(this).val();     
		});     
		if(str == 1){//相同
			searchByCard();
		}
	}
});*/
//获取拼音字符串
function getPinYinStr(hanzi){
	var onehanzi = hanzi.split('');
	var pinyinchar = '';
	for(var i=0;i<onehanzi.length;i++){
		pinyinchar += PinYin.getPinYin(onehanzi[i]);
	}
	return pinyinchar;
}