<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/authority" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
<meta charset="UTF-8">
<title>更新</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
<!-- zTree -->
<link rel="stylesheet"
	href="${base }/references/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<body>

	<div class="modal-content">
		<form id="authorityUpdateForm" method="POST">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height: 435px; overflow-y: auto;">
				<div class="departmentName form-group">
					<!--部门权限 设置-->
					<input id="jobJson" name="jobJson" type="hidden" value="" /> <input
						name="id" type="hidden" value="${obj.dept.id}" />
					<ul class="addDepartment">
						<li><label class=" text-right">部门名称：</label></li>
						<li class="li-input"><input id="deptName" name="deptName"
							type="text" class="form-control input-sm inputText"
							value="${obj.dept.deptName }"> <span class="prompt">*</span>
						</li>
						<li><button type="button"
								class="btn btn-primary btn-sm btnPadding" id="addJob">添加职位</button></li>
					</ul>
				</div>
				<!--end 部门权限 设置-->

				<div class="jobName cf">
					<!--职位权限 设置-->
					<c:forEach var="one" items="${obj.list}" varStatus="stat">
						<div class="job_container">
							<ul class="addDepartment marHei">
								<li><label class="text-right">职位名称：</label></li>
								<li class="li-input inpPadd"><input name="jobName"
									type="text" class="form-control input-sm inputText"
									value='${one.jobName }'> <input name="jobId"
									type="hidden" value='${one.jobId }'></li>
								<li><button type="button"
										class="btn btn-primary btn-sm btnPadding" id="settingsPermis">设置权限</button>
									<button type="button" class="btn btn-primary btn-sm btnPadding"
										id="deleteBtn">删除</button></li>
							</ul>
							<c:choose>
								<c:when test="${stat.index == 0}">
									<div class="ztree none">
										<ul id="tree_${stat.index}"></ul>
									</div>
								</c:when>
								<c:otherwise>
									<div class="ztree none">
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
	<script
		src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!--zTree -->
	<script
		src="${base}/references/common/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script
		src="${base}/references/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script
		src="${base}/references/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>



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
		//默认选中个人信息和操作台
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
		       $('.jobName').append('<div class="job_container"><ul class="addDepartment marHei"><li><label class="text-right">职位名称：</label></li><li class="li-input inpPadd"><input id="jobName" name="jobName" type="text" class="form-control input-sm inputText" placeholder="请输入职位名称"></li><li><button type="button" class="btn btn-primary btn-sm btnPadding" id="settingsPermis">设置权限</button><button type="button" style="width:70px;" class="btn btn-primary btn-sm btnPadding" id="deleteBtn1" >删除</button></li></ul>'
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
			   var jobName = $(container).find("input[name='jobName']").val();
			   var jobId = $(container).find("input[name='jobId']").val();
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
											
				}
			});
		}
		
		//更新时刷新页面
		function update() {
			window.location.reload();
		}
		
	    initvalidate();
		$('#authorityUpdateForm').bootstrapValidator('validate');
		function save() {
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
		}
	
		//返回刷新页面 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	parent.layer.close(index);
		}
	</script>


</body>
</html>

