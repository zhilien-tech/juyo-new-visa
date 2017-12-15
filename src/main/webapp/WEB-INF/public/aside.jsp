<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
  <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
  <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
  <link rel="stylesheet" href="${base}/references/public/css/style.css">
<style type="text/css">
.sidebar-mini .wrapper .wrapper footer:nth-child(even) {
	display: none;
}
.menu-ul {
	display:none;
}
.menufirst {
  border-left:solid 3px #2f86f0;
}
.menufirst a {
	color:#FFF !important;
	background:#232d23;
}

</style>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="">
		<!-- Sidebar Menu -->
		<ul class="sidebar-menu">

			<div class="hamburgerImg">
				<img src="${base}/references/public/dist/newvisacss/img/02.png" />
			</div>
			<c:forEach items="${auths }" var="function" varStatus="status">
				<c:if test="${function.level eq 1}">
					<!-- 一级菜单 -->
				<li id="${status.index+1 }" class="menu1 menuLi">
					<c:choose>
						<c:when test="${empty function.url }">
							<a href="#">
								<img src="${base}/references/public/dist/newvisacss/img/index.png" />
								<font>${function.funName }</font>
								<i class="moreI more"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a href="${function.url }?currentPageIndex=${status.index+1 }" target="main">
								<img src="${base}/references/public/dist/newvisacss/img/index.png" />
								<font>${function.funName }</font>
							</a>
						</c:otherwise>
					</c:choose>
					<!-- 二级菜单 -->
					<ul class="menu-ul">
						<c:forEach items="${auths }" var="twofun" varStatus="twostatus">
							<c:if test="${twofun.level eq 2 and twofun.parentId eq function.id}">
								<li id="${twostatus.index+(status.index+1)*100 }"><a href="${twofun.url }?currentPageIndex=${twostatus.index+(status.index+1)*100 }" target="main">${twofun.funName }</a></li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				</c:if>
			</c:forEach>
		</ul>
		<!-- /.sidebar-menu -->
	</section>
	<!-- /.sidebar -->
	<script src="${base}/references/public/js/jquery.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
</aside>
