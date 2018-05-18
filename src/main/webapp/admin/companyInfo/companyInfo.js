$(function() {
	//校验
	$('#companyInfoForm').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : '公司名称不能为空'
					},
					remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
						url: BASE_PATH+'/admin/companyInfo/checkCompanyNameExist.html',//验证地址
						message: '公司全称已存在，请重新输入',
						delay :  2000,
						type: 'POST',
						data: function(validator) {
							return {
								companyName:$('#fullname').val(),
								id:$('#id').val()
							};
						}
					}
				}
			},
//			shortName : {
//				validators : {
//					notEmpty : {
//						message : '公司简称不能为空'
//					},
//					stringLength: {
//						min: 1,
//						max: 6,
//						message: '公司简称长度为6'
//					}
//				}
//			},
//			cdesignNum : {
//				validators : {
//					notEmpty : {
//						message : '指定番号不能为空'
//					}
//				}
//			},
//			linkman : {
//				validators : {
//					notEmpty : {
//						message : '联系人不能为空'
//					}
//				}
//			},
//			mobile : {
//				validators : {
//					notEmpty : {
//						message : '电话不能为空'
//					}
//				}
//			},
//			email : {
//				validators : {
//					regexp: {
//                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
//                        message: '邮箱格式错误'
//                    }
//				}
//			},
//			address : {
//				validators : {
//					notEmpty : {
//						message : '地址不能为空'
//					}
//				}
//			}
		}
	});

});
/* 页面初始化加载完毕 */
$('#name').select2({
	ajax : {
		url : "/admin/companyInfo/companyList.html",
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
				name : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				//公司ID
				obj.id = obj.id;
				//公司名称
				obj.text = obj.name;
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
/* 选中联系人 */
$("#name").on('select2:select', function (evt) {
	var companyid = $(this).select2("val");
	var id = parseInt($("#name").val());
	$.ajax({
		url : "/admin/companyInfo/getSelectdCompany.html",
		type : 'POST',
		data : {
			'id' : id
		},
		dataType:'json',
		success : function(data) {
			/*公司ID*/
			$("#id").val(data.id);
			/* 指定番号补全 */
			$("#cdesignNum").val(data.designatednum);
			/* 公司简称补全 */
			$("#shortName").val(data.shortname);
			/* 联系人补全 */
			$("#linkman").val(data.linkman);
			/* 电话补全 */
			$("#mobile").val(data.mobile);
			/* 邮箱补全 */
			$("#email").val(data.email);
			/* 地址补全 */
			$("#address").val(data.address);
		},
		error : function() {
		}
	});

});
function clearText(){
	$("#name").empty();
	$("#cdesignNum").val("");
	$("#shortName").val("");
	$("#linkman").val("");
	$("#mobile").val("");
	$("#email").val("");
	$("#address").val("");

}

/* 取消选中时 */
$("#name").on('select2:unselect', function (evt) {
	clearText();
}); 

