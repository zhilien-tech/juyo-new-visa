<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title>优悦签</title>
<link rel="stylesheet" href="${base}/references/common/css/menuMain.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/common/css/top.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/common/css/nav.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/common/css/iconfont.css?v='20180510'">
<script src="${base}/references/common/js/jquery-1.10.2.js"></script>
<script src="${base}/references/common/js/nav.js"></script>
<script>
$(function(){
	/* 点击名字关闭左菜单栏状态 */
	$("#psersonal").click(function(){
		   $(".nav-item>a").next('ul').slideUp(300);
           $('.nav-item.nav-show').removeClass('nav-show');
           $(".navUl li").removeClass('navLi');
	});
});
</script>
</head>
<body>
	<div class="top">
		<div class="logo">优悦签</div>
		<div class="loginInfo">
			<!-- 头像 -->
			<img class="headPortrait-img" alt=""
				src="${base}/references/public/dist/newvisacss/img/TouXiang.jpg">
			<!-- 点 -->
			<span class="dian"></span>
			<!-- 名字 -->
			<c:choose>
				<c:when test="${userType == 5 or userType == 7}">
					<!-- 送签社管理员 -->
					<a id="psersonal" href="${base}/admin/companyInfo/list.html"
						target="mainIframe" class="dropdown-toggle name"
						data-toggle="dropdown">${loginuser.name}</a>
				</c:when>
				<c:otherwise>
					<a id="psersonal" href="${base}/admin/personalInfo/listInfo.html"
						target="mainIframe" class="dropdown-toggle name"
						data-toggle="dropdown">${loginuser.name}</a>
				</c:otherwise>
			</c:choose>
			<!-- 退出 -->
			<a href="${base}/admin/logout.html?logintype=${logintype}"
				class="closeWindow">退出<img class="setUp"
				src="${base}/references/public/dist/newvisacss/img/signOut.jpg"></a>
		</div>
	</div>
	<div class="left">
		<div class="nav">
			<div class="nav-top">
				<div id="mini"
					style="border-bottom: 1px solid rgba(255, 255, 255, .1)">
					<img src="${base}/references/public/dist/newvisacss/img/02.png">
				</div>
			</div>
			<ul class="ul">
				<c:forEach items="${auths }" var="function" varStatus="status">
					<c:if test="${function.level eq 1}">
						<li id="${status.index+1 }" class="nav-item"><c:choose>
								<c:when test="${empty function.url }">
									<a> <c:if test="${function.funName == '公司管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '客户管理'}">
											<i class="iconfont icon-zhucetianjiahaoyou"></i>
										</c:if> <c:if test="${function.funName == '销售'}">
											<i class="iconfont icon-huobi"></i>
										</c:if> <c:if test="${function.funName == '初审'}">
											<i class="iconfont icon-SQLshenhe"></i>
										</c:if> <c:if test="${function.funName == '前台'}">
											<i class="iconfont icon-computer"></i>
										</c:if> <c:if test="${function.funName == '签证'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '我的签证'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '申请人资料'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '售后'}">
											<i class="iconfont icon-iconfontfuwushichang"></i>
										</c:if> <c:if test="${function.funName == '城市管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '航班管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '酒店管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '景点管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '统计'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '地接社'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '送签社发招宝'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '送签社下载'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '统计'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '精简版'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '人员管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '活动管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '账户安全'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '日本'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '美国'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <span>${function.funName }</span><i
										class="iconfont icon-arrowright btnMore"></i></a>
								</c:when>
								<c:otherwise>
									<a href="${function.url }?currentPageIndex=${status.index+1 }"
										class="subnavTwo" target="mainIframe"> <c:if
											test="${function.funName == '公司管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '客户管理'}">
											<i class="iconfont icon-zhucetianjiahaoyou"></i>
										</c:if> <c:if test="${function.funName == '销售'}">
											<i class="iconfont icon-huobi"></i>
										</c:if> <c:if test="${function.funName == '初审'}">
											<i class="iconfont icon-SQLshenhe"></i>
										</c:if> <c:if test="${function.funName == '前台'}">
											<i class="iconfont icon-computer"></i>
										</c:if> <c:if test="${function.funName == '我的签证'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '申请人资料'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '签证'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '售后'}">
											<i class="iconfont icon-iconfontfuwushichang"></i>
										</c:if> <c:if test="${function.funName == '城市管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '航班管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '酒店管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '景点管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '统计'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '地接社'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '送签社发招宝'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '送签社下载'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '统计'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '精简版'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '人员管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '活动管理'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '账户安全'}">
											<i class="iconfont icon-wxbzhanghu"></i>
										</c:if> <c:if test="${function.funName == '日本'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <c:if test="${function.funName == '美国'}">
											<i class="iconfont icon-homepage"></i>
										</c:if> <span>${function.funName }</span><i class="iconfont"></i></a>
								</c:otherwise>
							</c:choose>
							<ul class="navUl">
								<c:forEach items="${auths }" var="twofun" varStatus="twostatus">
									<c:if
										test="${twofun.level eq 2 and twofun.parentId eq function.id}">
										<li id="${twostatus.index+(status.index+1)*100 }"><a
											href="${twofun.url }?currentPageIndex=${twostatus.index+(status.index+1)*100 }"
											target="mainIframe"><span>${twofun.funName }</span></a></li>
									</c:if>
								</c:forEach>
							</ul>
							<div class="miniName">${function.funName }</div></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="main">
		<iframe name="mainIframe" width="100%" height="100%"
			allowtransparency="yes" frameborder="no" border="0" marginwidth="0"
			marginheight="0" src="${mainurl }"></iframe>
	</div>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript">
	function successCallBack(status){
		if(status == 1){
			layer.msg('添加成功');
		}else if(status == 2){
			layer.msg('保存成功');
		}else if(status == 3){
			layer.msg('编辑成功');
		}else if(status == 4){
			layer.msg('发招宝');
		}else if(status == 5){
			layer.msg('招宝变更');
		}else if(status == 6){
			layer.msg('招宝取消');
		}else if(status == 7){
			layer.msg('报告拒签');
		}else if(status == 8){
			layer.msg('删除成功');
		}else if(status == 9){
			layer.msg('已移交售后');
		}else if(status == 10){
			layer.msg('已作废');
		}else if(status == 11){
			layer.msg('已还原');
		}
	}
</script>
</body>
</html>