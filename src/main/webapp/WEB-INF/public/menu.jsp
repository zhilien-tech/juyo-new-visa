<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优悦签</title>
<link rel="stylesheet" href="${base}/references/common/css/menuMain.css">
<link rel="stylesheet" href="${base}/references/common/css/top.css">
<link rel="stylesheet" href="${base}/references/common/css/nav.css">
<link rel="stylesheet" href="${base}/references/common/css/iconfont.css">
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
	/**/
});
</script>
</head>
<body>
<div class="top">
	<div class="logo">优悦签</div>
	<div class="loginInfo">
		<!-- 头像 -->
		<img class="headPortrait-img" alt="" src="${base}/references/public/dist/newvisacss/img/TouXiang.jpg">
		<!-- 点 -->
		<span class="dian"></span>
		<!-- 名字 -->
		<c:choose>
	    	<c:when test="${userType == 5}">
	            <!-- 送签社管理员 -->
	            <a id="psersonal" href="${base}/admin/companyInfo/list.html" target="mainIframe" class="dropdown-toggle name" data-toggle="dropdown">${loginuser.name}</a>
	        </c:when>
	    <c:otherwise>
           		<a id="psersonal" href="${base}/admin/personalInfo/listInfo.html" target="mainIframe" class="dropdown-toggle name" data-toggle="dropdown">${loginuser.name}</a>
	    </c:otherwise>
	    </c:choose>
		<!-- 退出 -->
		<a href="${base}/admin/logout.html?logintype=${logintype}" class="closeWindow">退出<img class="setUp" src="${base}/references/public/dist/newvisacss/img/signOut.jpg"></a>
	</div>
</div>
<div class="left">
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
					<a href="${function.url }?currentPageIndex=${status.index+1 }" class="subnavTwo" target="mainIframe"><i class="my-icon nav-icon icon_1"></i><span>${function.funName }</span><i class="my-icon nav-more moreHide"></i></a>
				</c:otherwise>
               	</c:choose>
                <ul class="navUl">
                	<c:forEach items="${auths }" var="twofun" varStatus="twostatus">
							<c:if test="${twofun.level eq 2 and twofun.parentId eq function.id}">
                   			<li id="${twostatus.index+(status.index+1)*100 }"><a href="${twofun.url }?currentPageIndex=${twostatus.index+(status.index+1)*100 }" target="mainIframe"><span>${twofun.funName }</span></a></li>
                   			</c:if>
					</c:forEach>		
                </ul>
            </li>
            </c:if>
        </c:forEach>    
        </ul>
    </div>
</div>
<div class="main">
<iframe name="mainIframe" width="100%" height="100%" allowtransparency="yes" frameborder="no" border="0" marginwidth="0" marginheight="0" src="${mainurl }"></iframe>
</div>
</body>
</html>