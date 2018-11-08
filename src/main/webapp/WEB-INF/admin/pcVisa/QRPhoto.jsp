<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>拍摄资料</title>
<link rel="stylesheet"
	href="/references/public/dist/newvisacss/css/photograph.css?v='20180510'" />
<style>
.imgInfoRight {
	float: left !important;
}

.sectionHead {
	margin-top: 20px;
	margin-bottom: 10px;
}
</style>
</head>
<body>

	<div class="section">
		<div class="dislogHide"></div>
		<div class="QRCode">
			<div class="explain">微信扫描二维码上传识别</div>
			<div class="scan">
				<img src="${obj.qrCode }" width="100%" height="auto" />
			</div>
		</div>

	</div>
</body>
<script
	src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
<script src="/references/common/js/layer/layer.js"></script>
<script type="text/javascript" src="/admin/common/commonjs.js"></script>
<script src="/appmobileus/js/jquery-1.10.2.js"></script>
<script src="/appmobileus/js/lrz.bundle.js"></script>
<script src="/admin/pcVisa/photoGroups.js"></script>
<script src="/admin/pcVisa/ClassifyPhoto.js"></script>
<script>

			
		
	</script>
</html>
