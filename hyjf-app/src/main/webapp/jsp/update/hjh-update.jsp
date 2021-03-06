<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>更新</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<style type="text/css">
	*{
		margin:0;padding: 0;
	}
	html{height: 100%;}
    body{background: #282b3b;height: 100%;}
	img{width: 100%;}
	div{text-align: center;}
	.update-text{
		color:#25348c;
		font-size: 16px;
		position: absolute;
		top: 40%;
		width:80%;
		padding:10%;
	}
	.updated-download-3{background: url(${ctx}/images/ios-update-bg.png?v=20171123) no-repeat;background-size: 100%;height: 100%;}
	.updated-download-3 a{display: inline-block;width: 84%;height: 11%;background: url(${ctx}/images/hjh-update-btn.png) no-repeat;background-size: 100%;position:absolute;top:65%;left:8%}
	@media only screen and (min-width: 768px) {
		.updated-download-3 a{
			top:79%;
		}
	}
</style>
	</head>
	<body>
		<div class="updated-download-3">
			<div class="update-text">
				新版APP上线啦，<br/>体验全面升级!<br/>为了更好地用户体验，请更新APP！
			</div>
			<a href="${downLoadUrl }"></a>
		</div>
	</body>
</html>