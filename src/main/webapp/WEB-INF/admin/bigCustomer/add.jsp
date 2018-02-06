
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/bigCustomer" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>添加</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="bigcustomerAddForm">
			<div class="modal-header">
				<span class="heading">添加</span>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消"/>
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存"/>
			</div>
			<div class="modal-body">
				<div class="tab-content">
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>公司id：</label>
										<input id="comId" name="comId" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																												<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>用户id（登录用户id）：</label>
												<input id="userId" name="userId" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																														</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>姓：</label>
										<input id="firstName" name="firstName" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																														<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>姓(拼音)：</label>
												<input id="firstNameEn" name="firstNameEn" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																												</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>名：</label>
										<input id="lastName" name="lastName" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>名(拼音)：</label>
												<input id="lastNameEn" name="lastNameEn" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																										</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>状态：</label>
										<input id="status" name="status" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																		<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>手机号：</label>
												<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																								</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>邮箱：</label>
										<input id="email" name="email" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																				<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>性别：</label>
												<input id="sex" name="sex" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>部门：</label>
										<input id="department" name="department" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																						<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>职位：</label>
												<input id="job" name="job" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>民族：</label>
										<input id="nation" name="nation" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																								<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>出生日期：</label>
												<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>住址：</label>
										<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																										<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>身份证号：</label>
												<input id="cardId" name="cardId" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																																																																</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>身份证正面：</label>
										<input id="cardFront" name="cardFront" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																												<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>身份证反面：</label>
												<input id="cardBack" name="cardBack" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																																																														</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>签发机关：</label>
										<input id="issueOrganization" name="issueOrganization" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																														<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>有效期始：</label>
												<input id="validStartDate" name="validStartDate" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																																																												</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>有效期至：</label>
										<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>现居住地址省份：</label>
												<input id="province" name="province" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																																																										</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>现居住地址城市：</label>
										<input id="city" name="city" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																		<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>详细地址：</label>
												<input id="detailedAddress" name="detailedAddress" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																																																								</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>曾用姓：</label>
										<input id="otherFirstName" name="otherFirstName" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																				<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>曾用姓(拼音)：</label>
												<input id="otherFirstNameEn" name="otherFirstNameEn" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																																																						</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>曾用名：</label>
										<input id="otherLastName" name="otherLastName" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>曾用名(拼音)：</label>
												<input id="otherLastNameEn" name="otherLastNameEn" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																				</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>紧急联系人姓名：</label>
										<input id="emergencyLinkman" name="emergencyLinkman" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																								<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>紧急联系人手机：</label>
												<input id="emergencyTelephone" name="emergencyTelephone" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																		</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>'是否另有国籍：</label>
										<input id="hasOtherNationality" name="hasOtherNationality" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																										<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>是否有曾用名：</label>
												<input id="hasOtherName" name="hasOtherName" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>结婚证/离婚证地址：</label>
										<input id="marryUrl" name="marryUrl" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																												<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>结婚状况：</label>
												<input id="marryStatus" name="marryStatus" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																														</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>婚姻状况证件类型：</label>
										<input id="marryurltype" name="marryurltype" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																														<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>国籍：</label>
												<input id="nationality" name="nationality" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																												</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>现居住地是否与身份证相同：</label>
										<input id="addressIsSameWithCard" name="addressIsSameWithCard" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>操作人：</label>
												<input id="opId" name="opId" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																										</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>更新时间：</label>
										<input id="updateTime" name="updateTime" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>创建时间：</label>
												<input id="createTime" name="createTime" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																								</div>
												
										
												
										
				</div>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#bigcustomerAddForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
				
									comId : {
						validators : {
							notEmpty : {
								message : '公司id不能为空'
							}
						}
					},
									userId : {
						validators : {
							notEmpty : {
								message : '用户id（登录用户id）不能为空'
							}
						}
					},
									firstName : {
						validators : {
							notEmpty : {
								message : '姓不能为空'
							}
						}
					},
									firstNameEn : {
						validators : {
							notEmpty : {
								message : '姓(拼音)不能为空'
							}
						}
					},
									lastName : {
						validators : {
							notEmpty : {
								message : '名不能为空'
							}
						}
					},
									lastNameEn : {
						validators : {
							notEmpty : {
								message : '名(拼音)不能为空'
							}
						}
					},
									status : {
						validators : {
							notEmpty : {
								message : '状态不能为空'
							}
						}
					},
									telephone : {
						validators : {
							notEmpty : {
								message : '手机号不能为空'
							}
						}
					},
									email : {
						validators : {
							notEmpty : {
								message : '邮箱不能为空'
							}
						}
					},
									sex : {
						validators : {
							notEmpty : {
								message : '性别不能为空'
							}
						}
					},
									department : {
						validators : {
							notEmpty : {
								message : '部门不能为空'
							}
						}
					},
									job : {
						validators : {
							notEmpty : {
								message : '职位不能为空'
							}
						}
					},
									nation : {
						validators : {
							notEmpty : {
								message : '民族不能为空'
							}
						}
					},
									birthday : {
						validators : {
							notEmpty : {
								message : '出生日期不能为空'
							}
						}
					},
									address : {
						validators : {
							notEmpty : {
								message : '住址不能为空'
							}
						}
					},
									cardId : {
						validators : {
							notEmpty : {
								message : '身份证号不能为空'
							}
						}
					},
									cardFront : {
						validators : {
							notEmpty : {
								message : '身份证正面不能为空'
							}
						}
					},
									cardBack : {
						validators : {
							notEmpty : {
								message : '身份证反面不能为空'
							}
						}
					},
									issueOrganization : {
						validators : {
							notEmpty : {
								message : '签发机关不能为空'
							}
						}
					},
									validStartDate : {
						validators : {
							notEmpty : {
								message : '有效期始不能为空'
							}
						}
					},
									validEndDate : {
						validators : {
							notEmpty : {
								message : '有效期至不能为空'
							}
						}
					},
									province : {
						validators : {
							notEmpty : {
								message : '现居住地址省份不能为空'
							}
						}
					},
									city : {
						validators : {
							notEmpty : {
								message : '现居住地址城市不能为空'
							}
						}
					},
									detailedAddress : {
						validators : {
							notEmpty : {
								message : '详细地址不能为空'
							}
						}
					},
									otherFirstName : {
						validators : {
							notEmpty : {
								message : '曾用姓不能为空'
							}
						}
					},
									otherFirstNameEn : {
						validators : {
							notEmpty : {
								message : '曾用姓(拼音)不能为空'
							}
						}
					},
									otherLastName : {
						validators : {
							notEmpty : {
								message : '曾用名不能为空'
							}
						}
					},
									otherLastNameEn : {
						validators : {
							notEmpty : {
								message : '曾用名(拼音)不能为空'
							}
						}
					},
									emergencyLinkman : {
						validators : {
							notEmpty : {
								message : '紧急联系人姓名不能为空'
							}
						}
					},
									emergencyTelephone : {
						validators : {
							notEmpty : {
								message : '紧急联系人手机不能为空'
							}
						}
					},
									hasOtherNationality : {
						validators : {
							notEmpty : {
								message : ''是否另有国籍不能为空'
							}
						}
					},
									hasOtherName : {
						validators : {
							notEmpty : {
								message : '是否有曾用名不能为空'
							}
						}
					},
									marryUrl : {
						validators : {
							notEmpty : {
								message : '结婚证/离婚证地址不能为空'
							}
						}
					},
									marryStatus : {
						validators : {
							notEmpty : {
								message : '结婚状况不能为空'
							}
						}
					},
									marryurltype : {
						validators : {
							notEmpty : {
								message : '婚姻状况证件类型不能为空'
							}
						}
					},
									nationality : {
						validators : {
							notEmpty : {
								message : '国籍不能为空'
							}
						}
					},
									addressIsSameWithCard : {
						validators : {
							notEmpty : {
								message : '现居住地是否与身份证相同不能为空'
							}
						}
					},
									opId : {
						validators : {
							notEmpty : {
								message : '操作人不能为空'
							}
						}
					},
									updateTime : {
						validators : {
							notEmpty : {
								message : '更新时间不能为空'
							}
						}
					},
									createTime : {
						validators : {
							notEmpty : {
								message : '创建时间不能为空'
							}
						}
					},
									
				}
			});
		});
		/* 页面初始化加载完毕 */
		
		/*保存页面*/ 
		function save() {
			//初始化验证插件
			$('#bigcustomerAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#bigcustomerAddForm").data('bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
									var comId = $("#comId").val();
					if(comId==""){
						layer.msg('comId不能为空');
						return;
					}
									var userId = $("#userId").val();
					if(userId==""){
						layer.msg('userId不能为空');
						return;
					}
									var firstName = $("#firstName").val();
					if(firstName==""){
						layer.msg('firstName不能为空');
						return;
					}
									var firstNameEn = $("#firstNameEn").val();
					if(firstNameEn==""){
						layer.msg('firstNameEn不能为空');
						return;
					}
									var lastName = $("#lastName").val();
					if(lastName==""){
						layer.msg('lastName不能为空');
						return;
					}
									var lastNameEn = $("#lastNameEn").val();
					if(lastNameEn==""){
						layer.msg('lastNameEn不能为空');
						return;
					}
									var status = $("#status").val();
					if(status==""){
						layer.msg('status不能为空');
						return;
					}
									var telephone = $("#telephone").val();
					if(telephone==""){
						layer.msg('telephone不能为空');
						return;
					}
									var email = $("#email").val();
					if(email==""){
						layer.msg('email不能为空');
						return;
					}
									var sex = $("#sex").val();
					if(sex==""){
						layer.msg('sex不能为空');
						return;
					}
									var department = $("#department").val();
					if(department==""){
						layer.msg('department不能为空');
						return;
					}
									var job = $("#job").val();
					if(job==""){
						layer.msg('job不能为空');
						return;
					}
									var nation = $("#nation").val();
					if(nation==""){
						layer.msg('nation不能为空');
						return;
					}
									var birthday = $("#birthday").val();
					if(birthday==""){
						layer.msg('birthday不能为空');
						return;
					}
									var address = $("#address").val();
					if(address==""){
						layer.msg('address不能为空');
						return;
					}
									var cardId = $("#cardId").val();
					if(cardId==""){
						layer.msg('cardId不能为空');
						return;
					}
									var cardFront = $("#cardFront").val();
					if(cardFront==""){
						layer.msg('cardFront不能为空');
						return;
					}
									var cardBack = $("#cardBack").val();
					if(cardBack==""){
						layer.msg('cardBack不能为空');
						return;
					}
									var issueOrganization = $("#issueOrganization").val();
					if(issueOrganization==""){
						layer.msg('issueOrganization不能为空');
						return;
					}
									var validStartDate = $("#validStartDate").val();
					if(validStartDate==""){
						layer.msg('validStartDate不能为空');
						return;
					}
									var validEndDate = $("#validEndDate").val();
					if(validEndDate==""){
						layer.msg('validEndDate不能为空');
						return;
					}
									var province = $("#province").val();
					if(province==""){
						layer.msg('province不能为空');
						return;
					}
									var city = $("#city").val();
					if(city==""){
						layer.msg('city不能为空');
						return;
					}
									var detailedAddress = $("#detailedAddress").val();
					if(detailedAddress==""){
						layer.msg('detailedAddress不能为空');
						return;
					}
									var otherFirstName = $("#otherFirstName").val();
					if(otherFirstName==""){
						layer.msg('otherFirstName不能为空');
						return;
					}
									var otherFirstNameEn = $("#otherFirstNameEn").val();
					if(otherFirstNameEn==""){
						layer.msg('otherFirstNameEn不能为空');
						return;
					}
									var otherLastName = $("#otherLastName").val();
					if(otherLastName==""){
						layer.msg('otherLastName不能为空');
						return;
					}
									var otherLastNameEn = $("#otherLastNameEn").val();
					if(otherLastNameEn==""){
						layer.msg('otherLastNameEn不能为空');
						return;
					}
									var emergencyLinkman = $("#emergencyLinkman").val();
					if(emergencyLinkman==""){
						layer.msg('emergencyLinkman不能为空');
						return;
					}
									var emergencyTelephone = $("#emergencyTelephone").val();
					if(emergencyTelephone==""){
						layer.msg('emergencyTelephone不能为空');
						return;
					}
									var hasOtherNationality = $("#hasOtherNationality").val();
					if(hasOtherNationality==""){
						layer.msg('hasOtherNationality不能为空');
						return;
					}
									var hasOtherName = $("#hasOtherName").val();
					if(hasOtherName==""){
						layer.msg('hasOtherName不能为空');
						return;
					}
									var marryUrl = $("#marryUrl").val();
					if(marryUrl==""){
						layer.msg('marryUrl不能为空');
						return;
					}
									var marryStatus = $("#marryStatus").val();
					if(marryStatus==""){
						layer.msg('marryStatus不能为空');
						return;
					}
									var marryurltype = $("#marryurltype").val();
					if(marryurltype==""){
						layer.msg('marryurltype不能为空');
						return;
					}
									var nationality = $("#nationality").val();
					if(nationality==""){
						layer.msg('nationality不能为空');
						return;
					}
									var addressIsSameWithCard = $("#addressIsSameWithCard").val();
					if(addressIsSameWithCard==""){
						layer.msg('addressIsSameWithCard不能为空');
						return;
					}
									var opId = $("#opId").val();
					if(opId==""){
						layer.msg('opId不能为空');
						return;
					}
									var updateTime = $("#updateTime").val();
					if(updateTime==""){
						layer.msg('updateTime不能为空');
						return;
					}
									var createTime = $("#createTime").val();
					if(createTime==""){
						layer.msg('createTime不能为空');
						return;
					}
								
				$.ajax({
					type : 'POST',
					data : $("#bigcustomerAddForm").serialize(),
					url : '${base}/admin/bigCustomer/add.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("添加成功", "", 3000);
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
					}
				});
			}
		}
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
	
	
</body>
</html>
