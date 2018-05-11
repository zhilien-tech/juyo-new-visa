<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta charset="utf-8">
  <meta http-equlv="proma" content="no-cache" />
  <meta http-equlv="cache-control" content="no-cache" />
  <meta http-equlv="expires" content="0" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title> 优悦签</title>
  <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
  <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
  <link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
  <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
  <script>
	  $(function(){
		  $(document).on('click',".dropdown-toggle",function(){
			  $(".menu1").removeClass('active');
			  $(".menu-ul").hide();
			  $(".menu-ul li").removeClass("activeTwo");
		  });
	  });
  </script>
  <style>
  .main-header .logo { width:220px;}
  .main-header .navbar { margin-left:220px;}
  </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
  <!-- Main Header -->
  <header class="main-header">
    <!-- Logo -->
    <a href="${base}/" class="logo">
      <span class="logo-lg"> 优悦签</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">

      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li><img class="headPortrait-img" alt="" src="${base}/references/public/dist/newvisacss/img/TouXiang.jpg"></li>
          <li class="dropdown messages-menu">
            <span class="dian"></span>
          </li>
          <li class="dropdown messages-menu">
          	<c:choose>
	            <c:when test="${userType == 5 or userType == 7}">
	            	<!-- 送签社管理员 -->
	            	<a id="psersonal" href="${base}/admin/companyInfo/list.html" target="main" class="dropdown-toggle name" data-toggle="dropdown">${loginuser.name}</a>
	        	</c:when>
	            <c:otherwise>
           			<a id="psersonal" href="${base}/admin/personalInfo/listInfo.html" target="main" class="dropdown-toggle name" data-toggle="dropdown">${loginuser.name}</a>
	        	</c:otherwise>
	        </c:choose>
          </li>
          <%-- <li class="setUp-li">
             <a href="javascript:;">设置<img class="setUp" src="${base}/references/public/dist/newvisacss/img/setUp.jpg"></a>
          </li> --%>
          <li class="signOut-li">
             <a href="${base}/admin/logout.html?logintype=${logintype}">退出<img class="setUp" src="${base}/references/public/dist/newvisacss/img/signOut.jpg"></a>
          </li>
        </ul>
      </div>
    </nav>
  </header>

