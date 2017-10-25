<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<style type="text/css">
.sidebar-mini .wrapper .wrapper footer:nth-child(even) {
	display: none;
}
</style>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar Menu -->
		<ul class="sidebar-menu">

			<div class="hamburgerImg">
				<img src="${base}/references/public/dist/newvisacss/img/02.jpg" />
			</div>
			<c:forEach items="${auths }" var="function" varStatus="status">
				<li id="${status.index+1 }"><a href="${function.url }?currentPageIndex=${status.index+1 }">
					<img src="${base}/references/public/dist/newvisacss/img/index.png" />
					<font>${function.funName }</font>
				</a></li>
			</c:forEach>

			<%-- <li id="1"><a href="/admin/flight/list.html?currentPageIndex=1">
					<img src="${base}/references/public/dist/newvisacss/img/index.png" />
					<font>航班管理</font>
			</a></li>
			<li id="2"><a href="/admin/city/list.html?currentPageIndex=2">
					<img src="${base}/references/public/dist/newvisacss/img/index.png" />
					<font>城市管理</font>
			</a></li>
			<li id="3"><a href="/admin/hotel/list.html?currentPageIndex=3">
					<img src="${base}/references/public/dist/newvisacss/img/index.png" />
					<font>酒店管理</font>
			</a></li>
			<li id="4"><a href="/admin/company/list.html?currentPageIndex=4">
					<img src="${base}/references/public/dist/newvisacss/img/index.png" />
					<font>公司管理</font>
			</a></li>
			<li id="5"><a
				href="/admin/receiveaddress/list.html?currentPageIndex=5"> <img
					src="${base}/references/public/dist/newvisacss/img/index.png" /> <font>收件地址</font>
			</a></li>
			<li id="6"><a href="/admin/scenic/list.html?currentPageIndex=6">
					<img src="${base}/references/public/dist/newvisacss/img/index.png" />
					<font>景点管理</font>
			</a></li>
			<li id="7"><a href="/admin/user/list.html?currentPageIndex=7">
					<img src="${base}/references/public/dist/newvisacss/img/index.png" />
					<font>员工管理</font>
			</a></li>
			<li id="8"><a
				href="/admin/customer/list.html?currentPageIndex=8"> <img
					src="${base}/references/public/dist/newvisacss/img/index.png" /> <font>客户管理</font>
			</a></li>
			<li id="9"><a
				href="/admin/function/list.html?currentPageIndex=9"> <img
					src="${base}/references/public/dist/newvisacss/img/index.png" /> <font>功能管理</font>

			</a></li>
			<li id="10"><a
				href="/admin/authority/list.html?currentPageIndex=10"> <img
					src="${base}/references/public/dist/newvisacss/img/index.png" /> <font>权限管理</font>
			</a></li>--%>

		</ul>
		<!-- /.sidebar-menu -->
	</section>
	<!-- /.sidebar -->
</aside>
