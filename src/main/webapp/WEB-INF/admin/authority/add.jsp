
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/authority" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<title>添加</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
<!-- zTree -->
<link rel="stylesheet" href="${base}/references/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<body>
	<div class="modal-content">
		<form id="authorityAddForm">
			<div class="modal-header">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn"
					type="button" onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height: 435px; overflow-y: auto;">
				<div class="departmentName form-group">
					<!--部门权限 设置-->
					<ul class="addDepartment">
						<input id="jobJson" name="jobJson" type="hidden" value="" />
						<li><label class=" text-right">部门名称：</label></li>
						<li class="li-input">
							<div>
								<input id="deptName" name="deptName" type="text"
									class="form-control input-sm inputText" placeholder="请输入部门名称">
								<span class="prompt">*</span>
							</div>
						</li>
						<li>
							<button type="button" class="btn btn-primary btn-sm btnPadding" id="addJob">添加职位</button>
						</li>
					</ul>
				</div>
				<!--end 部门权限 设置-->

				<div class="jobName cf">
					<!--begin 职位权限 设置-->

				</div>
				<!--end 职位权限 设置-->
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
	<!--zTree -->
	<script src="${base}/references/common/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script src="${base}/references/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script src="${base}/references/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>


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
		//默认选中的功能
		function zTreeBeforeCheck(treeId, treeNode) {
			if((treeNode.id == 43 || treeNode.id == 44) && treeNode.checked){
				return false ;
			}else{
				return true;
			}
		};
		//遍历得到的对象
		var zNodes =[
			 {id:"0", pId:"0", name:"职位权限设置", open:true},
			 <c:forEach var="p" items="${obj.moduleList}">
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
	 	
	   var treeIndex = 0 ;
	   $(function () {
			//部门职位 添加职位
		    $('#addJob').click(function(){
		       $(".job_container .ztree").hide();
		       $('.jobName').append('<div class="job_container form-group"><ul class="addDepartment marHei"><li><label class="text-right">职位名称：</label></li><li class="li-input inpPadd"><input id="jobName" name="jobName'+(treeIndex)+'" type="text" class="form-control input-sm inputText" placeholder="请输入职位名称"></li><li><button type="button" class="btn btn-primary btn-sm btnPadding" id="settingsPermis">设置权限</button><button type="button" class="btn btn-primary btn-sm btnPadding" id="deleteBtn" >删除</button></li></ul>'
		       +'<div class="ztree"><ul id="tree_'+treeIndex+'"></ul></div></div>');
		       treeIndex++;
			   var ztree_container = $(".job_container:last").find("div.ztree").find("ul:first");
		       var treeId = ztree_container.attr("id") ;
		       var treeObj = $.fn.zTree.getZTreeObj(treeId);
		       if(null == treeObj || undefined == treeObj){
			      	//初始化ztree
				    $.fn.zTree.init(ztree_container, setting, zNodes);
		      	}
		    });
		    //删除按钮
		    $('.jobName').on("click","#deleteBtn",function() {
		      $(this).parent().parent().next().remove();
		      $(this).closest('.job_container').remove();
		
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
			   var treeObj = $.fn.zTree.getZTreeObj("tree_" + index);
			   var nodes =  treeObj.getCheckedNodes(true);
			   var funcIds = "" ;
				$(nodes).each(function(i,node){
					funcIds += node.id + ",";
				});
			   var job = new Object();
			   job.jobName=jobName;
			   job.functionIds=funcIds;
			   jobInfos.push(job);
		   });
		   var jobJson = JSON.stringify(jobInfos) ;
		   $("#jobJson").val(jobJson) ;
		}
	</script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#authorityAddForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					deptName : {
						validators : {
							notEmpty : {
								message : '部门名称不能为空'
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
			$('#authorityAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#authorityAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
				var deptName = $("#deptName").val();
				if (deptName == "") {
					layer.msg('deptName不能为空');
					return;
				}

				$.ajax({
					type : 'POST',
					data : $("#authorityAddForm").serialize(),
					url : '${base}/admin/authority/add.html',
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
