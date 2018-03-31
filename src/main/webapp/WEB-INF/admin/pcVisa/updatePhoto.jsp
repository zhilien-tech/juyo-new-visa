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
		<input id="staffid" type="hidden" value="${obj.basicInfo.id }">
		<input id="passportId" type="hidden" value="${obj.passportId }">
		<div class="btnGroup">
			<a class="btnSave" onclick="nextWindow()">保存</a> <a class="btnCancel" onclick="closeWindow()">取消</a>
		</div>
	</div>
	<div class="topHide"></div>
	<div class="section">
		<div class="QRCode">
			<div class="explain">微信扫描二维码上传识别</div>
			<div class="scan">
				<img src="${obj.encodeQrCode }" />
			</div>
		</div>
		<!--二寸免冠照-->
		<div class="photo">
			<div class="sectionHead">照片</div>
			<div class="explain">
				<span>二寸免冠照片注意事项</span> <span>1.白底</span> <span>2.摘掉帽子</span> <span>3.漏出耳朵</span>
			</div>
			<div class="samplePhoto">
					<img id="twonichphoto"
						src="${base}/references/public/dist/newvisacss/img/picture.png" />
			</div>
			<div class="uploadPhoto">
				<div>上传</div>
				<%-- <c:if test="${not empty obj.twoinchphoto }">
					<img src="${obj.twoinchphoto }" class="loadImg" width="100%"
						height="100%" />
				</c:if> --%>
			</div>
			<input id="1" type="file" class="publicFile uploadFileImg" name="" />
		</div>
		<!--护照首页-->
		<div class="passport">
			<div class="sectionHead">护照首页</div>
			<div class="explain"></div>
			<div class="samplePassport">
				<img id = "huzhao"
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
				<img id="oldhuzhao"
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
					<img id="card"
						src="${base}/references/public/dist/newvisacss/img/passport.png" />


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
<script>
	$(function() {
		var staffid = $("#staffid").val();
		twonichphoto(staffid,13);
		$(".uploadPhoto").click(function(){
			$(".uploadFileImg").click();
			$(".uploadFileImg").change(function(){
				layer.load(1,{
					shade : "#000"
				});
				var that = this;
	            lrz(this.files[0]).then(function (rst) {
	                /* $('.bacimg2').attr('src',rst.base64);*/
	                $('#upload').hide(); 
	                // 处理成功会执行
	                sourceSize = (that.files[0].size / 1024).toFixed(2);
	                resultSize = (rst.fileLen / 1024).toFixed(2);
	                scale = parseInt(100 - (resultSize / sourceSize * 100));
	                rst.formData.append('image',rst.file);
//	                alert(rst);
//	                alert(JSON.stringify(rst.formData));
//	                alert(rst.file);
//	                alert('sourceSize:'+sourceSize+' resultSize:'+resultSize+' scale:'+scale);
					console.log('压缩后大小为：'+resultSize+'K  压缩率：'+scale+'%');
	                uploadPositive(rst,rst.formData,staffid); 
	            }).catch(function (err) {
	                console.log(err);
	            });
			});
		})
	});
	
	
	//单张图片上传
	function uploadPositive(rst, formData,staffid){
				if(!formData){
					formData = new FormData();
				}
		        formData.append("image", rst.file); 
		        formData.append("type", 13); 
		        formData.append("staffid", staffid); 
		        console.log('------------------------------');
		        console.log(formData); 
		        console.log(rst.file);
		        /* var layerIndex = layer.load(1, {
					shade : "#000"
				}); */
		        $.ajax({
					type : "POST",//提交类型  
					//dataType : "json",//返回结果格式  
					url : '/admin/mobileVisa/uploadImage.html',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//console.log('=====成功========');
						//关闭加载层
						layer.closeAll('loading');
						if (obj!=null) {
							location.reload();
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						//console.log('-----------------失败-------------');
						layer.closeAll('loading');
					}
				}); // end of ajaxSubmit
			}
	//2寸照片回显
	function twonichphoto(staffid, type) {
		$.ajax({
			url : "/admin/mobileVisa/getInfoByType.html",
			data : {
				type : type,
				staffid : staffid,
			},
			dataType : "json",
			type : 'post',
			success : function(data) {
				/* _self.passportdata = data.passportdata; */
				console.log(data);
				if (data != null) {
					if(13==data.type){
						$("#twonichphoto").attr("src", data.url);
					}
				}
			}
		});
	};
	
	//返回 
	function closeWindow() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	//保存跳转下一页
	function nextWindow(){
		var staffid = $("#staffid").val();
		var passportId = $("#passportId").val();
		window.location.href='/admin/bigCustomer/updatePassportInfo.html?passportId='+passportId;
	}
</script>
</html>
