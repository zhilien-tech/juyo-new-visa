<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>公司信息</title>
	<link rel="stylesheet"href="${base}/references/public/css/companyInfo.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<!-- 按钮 -->
	<div class="head">
		<div class="head-right">
			<a class="updatePW headBtn" onclick="updatePassword();">修改密码</a>
			&nbsp;&nbsp;&nbsp;
			<a class="addInfo headBtn" onclick="addCompanyOfSqs();">添加送签社</a>
		</div>
	</div>
	<!-- 列表 -->
	<div class="section">
		<div class="listShow">
			<div class="list listName">
				<label>公司全称：</label> <span>${obj.adminComInfo.fullname }</span>
			</div>
			<div class="list listNameTag">
				<label>公司简称：</label> <span>${obj.adminComInfo.shortname }</span>
			</div>
			<div class="list listNum">
				<label>指定番号：</label> <span>${obj.adminComInfo.designatedNum }</span>
			</div>
			<div class="list listPer">
				<label>联系人：</label> <span>${obj.adminComInfo.linkman }</span>
			</div>
			<div class="list listTel">
				<label>电话：</label> <span>${obj.adminComInfo.mobile }</span>
			</div>
		</div>
		<div class="listHover">
			<div class="list listType">
				<label>公司类型：</label> <span>${obj.adminComInfo.comType }</span>
			</div>
			<div class="list listScope">
				<label>经营范围：</label> <span>${obj.adminComInfo.scopes }</span>
			</div>
			<div class="list listMail">
				<label>邮箱：</label> <span>${obj.adminComInfo.email }</span>
			</div>
			<div class="list listsAddress">
				<label>地址：</label> <span>${obj.adminComInfo.address }</span>
			</div>
		</div>
		<div class="pic">
		
			<c:choose>
	            <c:when test='${obj.adminComInfo.license != "" }'>
	            	<img id="license" src="${obj.adminComInfo.license}" />
	        	</c:when>
	            <c:otherwise>
           			<img id="license" src="/references/common/images/companyInfo.png" />
					<div class="fontWord">
						<p>缩略图</p>
						<p>点击可放大显示</p>
					</div>
	        	</c:otherwise>
	        </c:choose>
		
			
		</div>
	</div>
	
	
	<div class="section">
		<div class="listShow">
			<div class="list listName">
				<label>公司全称：</label> <span>北京快旅国际旅行社</span>
			</div>
			<div class="list listNameTag">
				<label>公司简称：</label> <span>快旅国际</span>
			</div>
			<div class="list listNum">
				<label>指定番号：</label> <span>GIP：987543</span>
			</div>
			<div class="list listPer">
				<label>联系人：</label> <span>刘德华</span>
			</div>
			<div class="list listTel">
				<label>电话：</label> <span>13315679521</span>
			</div>
			<div class="list updateList">
				<a>编辑</a>
			</div>
		</div>
		<div class="listHover">
			<div class="list listAddress">
				<label>地址：</label> <span>北京市朝阳区朝阳门天辰大厦10号-908室</span>
			</div>
		</div>
	</div>

	<div class="section">
		<div class="listShow">
			<div class="list listName">
				<label>公司全称：</label> <span>北京快旅国际旅行社</span>
			</div>
			<div class="list listNameTag">
				<label>公司简称：</label> <span>快旅国际</span>
			</div>
			<div class="list listNum">
				<label>指定番号：</label> <span>GIP：987543</span>
			</div>
			<div class="list listPer">
				<label>联系人：</label> <span>刘德华</span>
			</div>
			<div class="list listTel">
				<label>电话：</label> <span>13315679521</span>
			</div>
			<div class="list updateList">
				<a>编辑</a>
			</div>
		</div>
		<div class="listHover">
			<div class="list listAddress">
				<label>地址：</label> <span>北京市朝阳区朝阳门天辰大厦10号-908室</span>
			</div>
		</div>
	</div>
	
	<!-- 分页 -->
	<input type="hidden" id="pageNumber" name="pageNumber" value="1">
	<input type="hidden" id="pageTotal" name="pageTotal">
	<input type="hidden" id="pageListCount" name="pageListCount">
	
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		function updatePassword(){
			 layer.open({
		    	    type: 2,
		    	    title: false,
		    	    closeBtn:false,
		    	    fix: false,
		    	    maxmin: false,
		    	    shadeClose: false,
		    	    scrollbar: false,
		    	    area: ['900px', '550px'],
		    	    content: BASE_PATH + '/admin/personalInfo/updatePassword.html'
		    	  });
		}
		
		function addCompanyOfSqs(){
			layer.open({
	    	    type: 2,
	    	    title: false,
	    	    closeBtn:false,
	    	    fix: false,
	    	    maxmin: false,
	    	    shadeClose: false,
	    	    scrollbar: false,
	    	    area: ['900px', '550px'],
	    	    content: BASE_PATH + '/admin/companyInfo/addEdit.html'
	    	});
		}
	</script>

</body>
</html>

