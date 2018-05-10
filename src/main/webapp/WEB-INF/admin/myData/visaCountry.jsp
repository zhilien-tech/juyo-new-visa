<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/firstTrialJp" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<title></title>
		<style>
			* { margin: 0; padding: 0; }
			li { list-style: none; }
			.title { width: 98%; margin-left: 2%; height: 30px; line-height: 30px; color: #000; font-weight: bold; display: block; }
			.main ul { margin-left: 2%;}
			.main ul li { float: left; text-align: center; margin: 2% 5% 0 0; }
			.main ul li a { display: block; width: 120px; height: 80px; overflow: hidden; }
			.main ul li a img { width: 120px; height: 80px; }
			.main ul li span { line-height: 30px;}
		</style>
	</head>
	<body>
		<div class="body">
			<div class="title">
				<span></span>
			</div>
			<div class="main">
				<ul>
					<li>
						<a href="/admin/myData/visaInfo">
							<img src="${base}/references/common/images/JapanFlag.png" />
						</a>
						<span>日本</span>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>

