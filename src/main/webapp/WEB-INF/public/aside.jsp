<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<link rel="stylesheet" href="${base}/references/common/css/nav.css">
<link rel="stylesheet" href="${base}/references/common/css/iconfont.css">
<script src="${base}/references/common/js/jquery-1.10.2.js"></script>
<script src="${base}/references/common/js/nav.js"></script>
<section>
	 <div class="nav">
        <div class="nav-top">
            <div id="mini" style="border-bottom:1px solid rgba(255,255,255,.1)"><img src="${base}/references/public/dist/newvisacss/img/02.png" ></div>
        </div>
        <ul>
        <c:forEach items="${auths }" var="function" varStatus="status">	
        	<c:if test="${function.level eq 1}">
            <li id="${status.index+1 }" class="nav-item">
            	<c:choose>
				<c:when test="${empty function.url }">
                    <a href="#"><i class="my-icon nav-icon icon_1"></i><span>${function.funName }</span><i class="my-icon nav-more"></i></a>
               	</c:when>
               	<c:otherwise>
					<a href="${function.url }?currentPageIndex=${status.index+1 }" target="main"><i class="my-icon nav-icon icon_1"></i><span>${function.funName }</span><i class="my-icon nav-more"></i></a>
				</c:otherwise>
               	</c:choose>
                <ul>
                	<c:forEach items="${auths }" var="twofun" varStatus="twostatus">
							<c:if test="${twofun.level eq 2 and twofun.parentId eq function.id}">
                   			<li id="${twostatus.index+(status.index+1)*100 }"><a href="${twofun.url }?currentPageIndex=${twostatus.index+(status.index+1)*100 }" target="main"><span>${twofun.funName }</span></a></li>
                   			</c:if>
					</c:forEach>		
                </ul>
            </li>
            </c:if>
        </c:forEach>    
        </ul>
    </div>
</section>