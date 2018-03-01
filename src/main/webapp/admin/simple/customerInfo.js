/* 加载公司全称下拉 */
$("#compName").select2({
	ajax : {
		url : '/admin/orderJp/getcompNameSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				compName : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.name; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});

/* 加载公司简称下拉 */
$("#comShortName").select2({
	ajax : {
		url : '/admin/orderJp/getComShortNameSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				comShortName : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.shortname; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});

//客户类型选中事件
$('#customerType').on('change',function(){
	var thisval = $(this).val();
	if (thisval == 4) {
		$('.zhiKe').removeClass('none');
		$('.on-line').addClass('none');
		$('#customamount').removeClass('none');
	}else if(thisval){
		$('.zhiKe').addClass('none');
		$('.on-line').removeClass('none');
		$('#customamount').addClass('none');
	}
});

//客户名称选中事件
$("#compName").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	$.ajax({ 
		url: '/admin/simple/getCustomerinfoById.html',
		async : false,
		dataType:"json",
		data:{customerid:thisval},
		type:'post',
		success: function(data){
			$('#customerType').val(data.source);
			$('#customerid').val(data.id);
			$('#comShortName').html('<option value="'+data.id+'" selected="selected">'+data.shortname+'</option>');
			$('#payType').val(data.payType);
			$('#customamount').addClass('none');
		}
	});
});

$("#compName").on("select2:unselect",function(e){
	$('#customerType').val('');
	$('#customerid').val('');
	$('#comShortName').html('');
	$('#payType').val('');
	$('#visatype').val('');
	$('#amount').val('');
});
//客户简称选中事件
$("#comShortName").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	$.ajax({ 
		url: '/admin/simple/getCustomerinfoById.html',
		async : false,
		dataType:"json",
		data:{customerid:thisval},
		type:'post',
		success: function(data){
			$('#customerType').val(data.source);
			$('#customerid').val(data.id);
			$('#compName').html('<option value="'+data.id+'" selected="selected">'+data.name+'</option>');
			$('#payType').val(data.payType);
			$('#customamount').addClass('none');
		}
	});
});

$("#comShortName").on("select2:unselect",function(e){
	$('#customerType').val('');
	$('#customerid').val('');
	$('#compName').html('');
	$('#payType').val('');
	$('#visatype').val('');
	$('#amount').val('');
});
$('#visatype').on('change',function(){
	var visatype = $(this).val();
	var customerid = $('#customerid').val();
	if(visatype && customerid){
		$.ajax({ 
			url: '/admin/simple/getCustomerAmount.html',
			async : false,
			dataType:"json",
			data:{customerid:customerid,visatype:visatype},
			type:'post',
			success: function(data){
				$('#amount').val(data.amount);
			}
		});
	}
});