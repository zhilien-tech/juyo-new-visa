<%@ page contentType="text/html; charset=utf-8" language="java" isErrorPage="true"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%
	Throwable ex = null;
	if (exception != null){
	  ex = exception;
	}
	if (request.getAttribute("javax.servlet.error.exception") != null){
	  ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
	}
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	if(ex!=null){
		logger.error(ex.getMessage(), ex);
	}
%>
<div id="wrap" class="ccig500" style="background: #f2f2f2;">
	<div class="main">
		<h1>500</h1>
		<div class="box500">
			<div class="details">
				<c:choose>
					<c:when test='${not empty REQ_ERROR_KEY }'>出错了:${REQ_ERROR_KEY}</c:when>
					<c:otherwise>出错了:不知道啥错，你中奖了！</c:otherwise>
				</c:choose>
				<br>系统会在<label id="jumpTo"></label>秒内自动返回上一页，如果没有自动返回，请点击下面链接。
				<br>请<a href="javascript:history.go(-1);">返回上一页</a> 或 <a href="/">返回首页</a>
			</div>
		</div>
	</div>
</div>