<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/authority" />
<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
	<meta charset="UTF-8">
	<title>更新</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/css/authority.css"><!-- 本页面style -->
	<!-- zTree -->
	<link rel="stylesheet" href="${base }/references/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<body>
	<div class="modal-content">
		<form id="authorityUpdateForm" method="POST">
			<div class="modal-header">
				<span class="heading">编辑</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" />
				<input id="submit" type="button" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height:488px; overflow-y: auto;">
				<div class="departmentName form-group">
					<!--部门权限 设置-->
					<input id="jobJson" name="jobJson" type="hidden" value="" /> 
					<input id="deptId" name="id" type="hidden" value="${obj.dept.id}" />
					<ul class="addDepartment">
						<li><label><span>*</span>部门名称：</label></li>
						<li class="li-input">
							<input id="deptName" name="deptName" type="text" class="form-control input-sm inputText" value="${obj.dept.deptName }">
						</li>
						<li>
							<button id="addJob" type="button" class="btn btn-primary btn-sm btnPadding">添加职位</button>
						</li>
					</ul>
				</div>
				<!--end 部门权限 设置-->

				<div class="jobName cf">
					<!--职位权限 设置-->
					<c:forEach var="one" items="${obj.list}" varStatus="stat">
						<div class="job_container form-group has-success">
							<ul class="addDepartment marHei">
								<li><label class="text-right"><span>*</span> 职位名称：</label></li>
								<li class="li-input inpPadd">
									<input id="jobName" name="jobName[]" onkeyup="jobNameKeyup(this);" type="text" class="form-control input-sm inputText" value='${one.jobName }'> 
									<input id="jobId" type="hidden" value='${one.jobId }'></li>
								<li>
									<button type="button" class="btn btn-primary btn-sm btnPadding" id="settingsPermis">设置权限</button>
									<button type="button" class="btn btn-primary btn-sm btnPadding" id="deleteBtn">删除</button>
								</li>
							</ul>
							<c:choose>
								<c:when test="${stat.index == 0}">
									<div class="ztree none">
										<small class="help-block" data-bv-validator="notEmpty" data-bv-for="jobName[]" data-bv-result="VALID" style="display: none;">职位名称不能为空</small>
										<small class="help-block" data-bv-validator="remote" data-bv-for="jobName[]" data-bv-result="INVALID" style="display: none;">职位名称已存在，请重新输入</small>
										<ul id="tree_${stat.index}"></ul>
									</div>
								</c:when>
								<c:otherwise>
									<div class="ztree none">
										<small class="help-block" data-bv-validator="notEmpty" data-bv-for="jobName[]" data-bv-result="VALID" style="display: none;">职位名称不能为空</small>
										<small class="help-block" data-bv-validator="remote" data-bv-for="jobName[]" data-bv-result="INVALID" style="display: none;">职位名称已存在，请重新输入</small>
										<ul id="tree_${stat.index}"></ul>
									</div>
								</c:otherwise>
							</c:choose>
							<input type="hidden" class="znodes" value='${one.znodes }' />
						</div>
					</c:forEach>
				</div>
				<!--end 职位权限 设置-->
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!--zTree -->
	<script src="${base}/references/common/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script src="${base}/references/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script src="${base}/references/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 引入validateJobName JS -->
	<script src="${base}/admin/authority/validateJobName.js"></script>
	<script type="text/javascript">
		var setting = {
				check: {
					enable: true,//显示复选框
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeCheck: zTreeBeforeCheck
				}
			};
		//默认选中
		function zTreeBeforeCheck(treeId, treeNode) {
			if((treeNode.id == 43 || treeNode.id == 44) && treeNode.checked){
				return false ;
			}else{
				return true;
			}
		};
	   var treeIndex = "${obj.list.size()}";
	   $(function () {
			//部门职位 编辑职位
		    $('#addJob').click(function(){
		       $(".job_container .ztree").hide();
		       $('.jobName').append('<div class="job_container form-group has-success"><ul class="addDepartment marHei"><li><label class="text-right"><span>*</span> 职位名称：</label></li><li class="li-input inpPadd"><input id="jobName" name="jobName[]" type="text"  onkeyup="jobNameKeyup(this);" class="form-control input-sm inputText" placeholder="请输入职位名称"></li><li><button type="button" class="btn btn-primary btn-sm btnPadding" id="settingsPermis">设置权限</button><button type="button" class="btn btn-primary btn-sm btnPadding" id="deleteBtn" >删除</button></li></ul>'
						+'<div class="ztree"><small class="help-block" data-bv-validator="notEmpty" data-bv-for="jobName[]" data-bv-result="IVVALID" style="display: none;">职位名称不能为空</small><small class="help-block" data-bv-validator="remote" data-bv-for="jobName[]" data-bv-result="INVALID" style="display: none;">职位名称已存在，请重新输入</small><ul id="tree_'+treeIndex+'"></ul></div></div>');
				treeIndex++;
		       
		       var ztree_container = $(".job_container:last").find("div.ztree").find("ul:first");
		       var treeId = ztree_container.attr("id") ;
		       var treeObj = $.fn.zTree.getZTreeObj(treeId);
		       if(null == treeObj || undefined == treeObj){
			      	//初始化ztree
				    $.fn.zTree.init(ztree_container, setting, zNodes);
		      	}
		    });
		    
		  	//新增职位的时候需要的功能节点
			var zNodes =[
				 {id:"0", pId:"0", name:"职位权限设置", open:true},
				<c:forEach var="p" items="${obj.zNodes}">
					<c:choose>
						<c:when test="${p.id eq 43 || p.id eq 44}">
							{ id:"${p.id }", pId:"${p.parentId }", name:"${p.funName }", open:true,checked:true},
						</c:when>
						<c:otherwise>
							{ id:"${p.id }", pId:"${p.parentId }", name:"${p.funName }", open:true,checked:"${p.checked}"},
						</c:otherwise>
					</c:choose>
				</c:forEach>
			];
		    var root =  {id:"0", pId:"0", name:"职位权限设置", open:true};
		    //创建所有的树
		    $('.job_container').each(function(index,element){
		    	var znodesJson = $(element).find("input.znodes").val();
		    	var treeContainer = $(element).find("div.ztree").find("ul:first");
		    	
		    	//每个职位的节点
		    	var nodes = eval('(' +znodesJson+ ')');
		    	
		    	nodes.push(root) ;
		    	$.fn.zTree.init(treeContainer, setting, nodes);
		    });
		    //设置权限 按钮
		    $('.jobName').on("click","#settingsPermis",function() {
			    $(this).parents('.marHei').next().toggle('500');
		        $(this).parents(".job_container").siblings().children('.ztree').hide();
		      	var ztree_container = $(this).parents(".marHei").next("div.ztree").find("ul:first");
		      	var treeId = ztree_container.attr("id") ;
		      	
		      	var treeObj = $.fn.zTree.getZTreeObj(treeId);
		      	if(null == treeObj || undefined == treeObj){
		      	//初始化ztree
			    	$.fn.zTree.init(ztree_container, setting, zNodes);
		      	}
		    });
	     });
	   //设置功能
		function setFunc(){
		   var jobInfos = [];
		   //取所有树
		   $(".job_container").each(function(index,container){
			   var jobName = $(container).find("input[id='jobName']").val();
			   var jobId = $(container).find("input[id='jobId']").val();
			   var treeObj = $.fn.zTree.getZTreeObj("tree_" + index);
			   var nodes =  treeObj.getCheckedNodes(true);
			   var funcIds = "" ;
			   $(nodes).each(function(i,node){
				  funcIds += node.id + ",";
			   });
			   var job = new Object();
			   job.jobName=jobName;
			   job.jobId=jobId;
			   job.functionIds=funcIds;
			   jobInfos.push(job);
		   });
		   
		   var jobJson = JSON.stringify(jobInfos) ;
		   $("#jobJson").val(jobJson) ;
		}
	</script>
	<script type="text/javascript">
		var base = "${base}";

		function initvalidate(){
			//校验
			$('#authorityUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					deptName: {
		                validators: {
		                    notEmpty: {
		                        message: '部门名称不能为空!'
		                    },
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
		                         url: '${base}/admin/authority/checkDeptNameExist.html',//验证地址
		                         message: '部门名称已存在，请重新输入',//提示消息
		                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
		                         type: 'POST',//请求方式
		                         //自定义提交数据，默认值提交当前input value
		                         data: function(validator) {
		                            return {
		                            	deptName:$('#deptName').val(),
		                            	deptId:$('#deptId').val()
		                            };
		                         }
		                     }
		                }
		            },
		            /* 'jobName[]':{
		                validators: {
		                    notEmpty: {
		                        message: '职位名称不能为空!'
		                    },
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
		                         url: '${base}/admin/authority/checkJobNameExist.html',//验证地址
		                         message: '此职位已存在，请重新输入',//提示消息
		                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
		                         type: 'POST',//请求方式
		                         //自定义提交数据，默认值提交当前input value
		                         data: function(validator) {
		                            return {
		                            	jobName:$('#jobName').val()
		                            };
		                         }
		                     }
		                }
		            } */
		        }					
			});
		}
		
		//更新时刷新页面
		function update() {
			window.location.reload();
		}
		
	    initvalidate();
		$('#authorityUpdateForm').bootstrapValidator('validate');
		/* function save() {
			$('#authorityUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#authorityUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
			
				//获取必填项信息
				
				var deptName = $("#deptName").val();
				if(deptName==""){
					layer.msg('deptName不能为空');
					return;
				}
				
				$.ajax({
					type : 'POST',
					data : $("#authorityUpdateForm").serialize(),
					url : '${base}/admin/authority/update.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("编辑成功", "", 3000);
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("编辑失败", "", 3000);
					}
				});
			}
		} */
	
		
		//编辑保存
		$("#submit").click(function(){
			$('#authorityUpdateForm').bootstrapValidator('validate');
			setFunc();
			validateJobName();
			var funBoolean = validateFuc();
			if(funBoolean!=""){
				layer.msg(funBoolean,{time:2000});
				return false;
			}
			var hasClass = validateSave();
			if(hasClass){
				return false;
			}
			var bootstrapValidator = $("#authorityUpdateForm").data('bootstrapValidator');
			var _deptName = $("input#deptName").val();
			var _jobJson = $("input#jobJson").val();
			
			try{
				$("input[id='jobName']").each(function(index,element){
					var eachJobName = $(element).val();
					if(null == eachJobName || undefined == eachJobName || "" == eachJobName || "" == $.trim(eachJobName)){
						throw "职位名称不能为空";
					}
				}) ;
			}catch(e){
				layer.msg("职位不能为空且至少存在一个") ;
				return false ;
			}
			
			if(bootstrapValidator.isValid()){
				var loadLayer = layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				$.ajax({
		           type: "POST",
		           url:'${base}/admin/authority/update.html',
		           data:{
						deptName:_deptName,
						jobJson:_jobJson,
						deptId:"${obj.dept.id}"
				   },
		           error: function(request) {
		              layer.msg('编辑失败!',{time:2000});
		           },
		           success: function(data) {
						if(data.status == '200'){
							layer.close(loadLayer) ;
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
							parent.datatable.ajax.reload();
							window.parent.layer.msg("编辑成功", "", 3000);
						}else{
							layer.close(loadLayer) ;
							console.log("==================="+JSON.stringify(data)); //职位名称已存在 或者其他。。。
							layer.msg("职位不能为空且至少存在一个") ;
						}
		           }
		       });
			}
		});
		
		//删除提示
		$(document).on("click","#deleteBtn",function(jobId){
			var dele= this;
			layer.confirm("您确认删除信息吗？", {
			    btn: ["是","否"], //按钮
			    shade: false //不显示遮罩
			},function(index){
			     layer.close(index);
			     $(dele).parent().parent().next().remove();
			     $(dele).closest('.job_container').remove();
			},function(index){
				//点击否
				layer.close(index);
			});
		});
		
		//删除不提示
		$(document).on("click","#deleteBtn1",function(jobId){
			var dele= this;
			//删除按钮
		    $(dele).parent().parent().next().remove();
		    $(dele).closest('.job_container').remove();
			
		});	
		
		//返回刷新页面 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	parent.layer.close(index);
		}
	</script>


</body>
</html>

