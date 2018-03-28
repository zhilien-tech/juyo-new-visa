<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>拍摄资料</title>
<link rel="stylesheet"
	href="/references/public/dist/newvisacss/css/photograph.css" />
</head>
<body>
	<div class="head">
		<span>拍摄资料</span>
		<div class="btnGroup">
			<a class="btnSave">保存</a> <a class="btnCancel">取消</a>
		</div>
	</div>
	<div class="topHide"></div>
	<div class="section">
		<div class="QRCode">
			<div class="explain">微信扫描二维码上传识别</div>
			<div class="scan">
				<img src="${base}/references/public/dist/newvisacss/img/QRCode.png" />
			</div>
		</div>
		<!--二寸免冠照-->
		<div class="photo">
			<div class="sectionHead">照片</div>
			<div class="explain">
				<span>二寸免冠照片注意事项</span> <span>1.白底</span> <span>2.摘掉帽子</span> <span>3.漏出耳朵</span>
			</div>
			<div class="samplePhoto">
				<c:if test="1">
					<img
						src="${base}/references/public/dist/newvisacss/img/picture.png" />
				</c:if>
			</div>
			<div class="uploadPhoto">
				<div>上传</div>
				<%-- <c:if test="${not empty obj.twoinchphoto }">
					<img src="${obj.twoinchphoto }" class="loadImg" width="100%"
						height="100%" />
				</c:if> --%>
			</div>
			<input type="file" class="publicFile uploadFileImg" name="" />
		</div>
		<!--护照首页-->
		<div class="passport">
			<div class="sectionHead">护照首页</div>
			<div class="explain"></div>
			<div class="samplePassport">
				<img
					src="${base}/references/public/dist/newvisacss/img/passport.png" />
			</div>
			<div class="uploadPassport">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" />
		</div>
		<!--旧护照-->
		<div class="passport">
			<div class="sectionHead">旧护照</div>
			<div class="explain"></div>
			<div class="samplePassport">
				<img
					src="${base}/references/public/dist/newvisacss/img/passport.png" />
			</div>
			<div class="uploadPassport">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" />
		</div>
		<!--身份证-->
		<div class="passport">
			<div class="sectionHead">身份证</div>
			<div class="explain"></div>
			<div class="samplePassport">
				<c:if test="${empty obj.cardfront }">
					<img
						src="${base}/references/public/dist/newvisacss/img/passport.png" />
				</c:if>
				<c:if test="${not empty obj.cardfront }">
					<img src="${obj.cardfront }" width="400px" height="300px" />
					<img src="${obj.cardback }" width="400px" height="300px" />
				</c:if>

			</div>
			<div class="uploadPassport">
				<div>上传</div>
				<img src="">
			</div>
			<input type="file" class="publicFile" name="" />
		</div>
		<!--户口本-->
		<div class="huKouBook">
			<div class="sectionHead">户口本</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>户主页</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--房产证-->
		<div class="huKouBook">
			<div class="sectionHead">房产证</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--婚姻状况-->
		<div class="huKouBook">
			<div class="sectionHead">婚姻状况</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--银行流水-->
		<div class="huKouBook">
			<div class="sectionHead">银行流水</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--在职证明-->
		<div class="huKouBook">
			<div class="sectionHead">在职证明</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--营业执照或机构代码证-->
		<div class="huKouBook">
			<div class="sectionHead">营业执照或机构代码证</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--行驶证-->
		<div class="huKouBook">
			<div class="sectionHead">行驶证</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--过期美签-->
		<div class="huKouBook">
			<div class="sectionHead">过期美签</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>
		<!--美国出签-->
		<div class="huKouBook">
			<div class="sectionHead">美国出签</div>
			<div class="explain"></div>
			<div class="sampleHuKouBook">
				<img
					src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
			</div>
			<div class="uploadHuKouBook">
				<div>上传</div>
				<img />
			</div>
			<input type="file" class="publicFile" name="" multiple />
		</div>

	</div>
</body>
<script
	src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
<script src="/references/common/js/layer/layer.js"></script>
<script type="text/javascript" src="/admin/common/commonjs.js"></script>
<script src="/appmobileus/js/jquery-1.10.2.js"></script>
<script src="/appmobileus/js/lrz.bundle.js"></script>
<script src="${base}/admin/pcVisa/photo.js"></script>
<script>
	$(function() {
		$(".uploadPhoto").click(function() {
			$(".uploadFileImg").click();
			$(".uploadFileImg").change(function() {
				layer.load(1, {
					shade : "#000"
				});
				uploadImg();
			});

		})
	})
</script>
</html>
