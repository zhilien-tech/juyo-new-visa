<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<link rel="stylesheet" href="${base}/references/public/css/menuLeft.css">
<script src="${base}/references/public/js/jquery.js"></script>
<!-- dropDown 显示隐藏 -->
<script src="${base}/references/public/js/dropDown.js"></script>
<aside>
	<section>
		<!-- Sidebar Menu -->
		<dl class="leftmenu">
			<c:forEach items="${auths }" var="function" varStatus="status">
				<c:if test="${function.level eq 1}">
					<!-- 一级菜单 -->
				<dd id="${status.index+1 }">
					<c:choose>
						<c:when test="${empty function.url }">
							<div class="title">
								<span>
								    <img alt="" src="${base}/references/common/images/leftico01.png">
								</span>
								${function.funName }
								<i class="more"></i>
							</div>
						</c:when>
						<c:otherwise>
							<div class="title">
								<a class="menuOnly" href="${function.url }?currentPageIndex=${status.index+1 }" target="main">
									<img alt="" src="${base}/references/common/images/leftico01.png">
									<label>${function.funName }</label>
									<i class="more"></i>
								</a>
							</div>
						</c:otherwise>
					</c:choose>
					<!-- 二级菜单 -->
					<ul class="menuson">
						<c:forEach items="${auths }" var="twofun" varStatus="twostatus">
							<c:if test="${twofun.level eq 2 and twofun.parentId eq function.id}">
								<li class="" id="${twostatus.index+(status.index+1)*100 }">
									<cite></cite>
									<a target="main" href="${twofun.url }?currentPageIndex=${twostatus.index+(status.index+1)*100 }">${twofun.funName }</a>
									<i></i>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</dd>
				</c:if>
			</c:forEach>
		</dl>
		<!-- /.sidebar-menu -->
	</section>
	<!-- /.sidebar -->
</aside>