<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/page.css"/>
		<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css">
		<title></title>
	</head>
	<body>
		<div class="container specialFont response">
			<div class="outcome">
				<div class="outcome_icon"><i class="icon-ok-circle"></i></div>
				<h5>注册成功</h5>
				<p>投资金额：20000</p>
			</div>
			<div class="process">
				<div class="process_line btn_bg_color"><a href="hyjf://jumpRecharge/?">开通资金托管账户，开始赚钱</a></div>
				<div class="process_line btn_bg_color"><a href="hyjf://closeView/?">先逛逛</a></div>
				<div class="info">
					<!--<span class="info_icon">i</span>-->
					<i class="icon-i i_circle"></i>
					<span><a href="hyjf://jumpH5/?{'url':'flow.jsp'}">什么是资金托管账户</a></span>
				</div>
			</div>
		</div>
	</body>
</html>