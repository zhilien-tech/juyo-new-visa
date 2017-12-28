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
			shortname : {
				validators : {
					notEmpty : {
						message : '公司简称不能为空'
					},
					stringLength: {
						min: 1,
						max: 6,
						message: '公司简称长度为6'
					}
				}
			},
			cdesignatednum : {
				validators : {
					notEmpty : {
						message : '指定番号不能为空'
					}
				}
			},
			linkman : {
				validators : {
					notEmpty : {
						message : '联系人不能为空'
					}
				}
			},
			mobile : {
				validators : {
					notEmpty : {
						message : '电话不能为空'
					}
				}
			},
			email : {
				validators : {
					regexp: {
                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                        message: '邮箱格式错误'
                    }
				}
			},
			address : {
				validators : {
					notEmpty : {
						message : '地址不能为空'
					}
				}
			}
		}
	});

});
/* 页面初始化加载完毕 */
