<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>2018年第一季度运营报告 </title>
		<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/animations.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/report-index.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/page.css"/>
		<style>
			.page-201803-1-1{background: url(${ctx}/img/report201803/report-201803-1-1.jpg) no-repeat;background-size: cover;-webkit-background-size: cover;}
			.page-9-1 img{
				width:90%;
				margin:0 5%;
			}
		</style>
	</head>
	<body class="bg_grey response report-page">
		<div>
			<img src="${ctx}/img/report-up.png" class="reprot-up">
			<div class="page page-1-1 page-current page-201803-1-1">
				<div class="wrap">
					<div class="report-may-1-main">
						<img src="${ctx}/img/report201803/report-201803-1-2.png" class="report-half-1-11" style="margin-top: 24% !important;"/>
					</div>
				</div>
			</div>
			<div class="page page-2-1 hide">
				<div class="wrap">
					<div class="report-page-may-2-topImg"></div>
					<div class="report-page-may-2-div">
						<p class="colorWhite leftToRight font12 report-page-may-p">累计交易额(元)</p>
						<img src="${ctx}/img/report201803/report-201803-2-1.jpg" class="report-page-may-img leftToRight"/>
						<p class="colorWhite leftToRight font12 report-page-may-p">平台注册人数(人)</p>
						<img src="${ctx}/img/report201803/report-201803-2-2.jpg" class="report-page-may-img leftToRight"/>
						<p class="colorWhite leftToRight font12 report-page-may-p">累计赚取收益(元)</p>
						<img src="${ctx}/img/report201803/report-201803-2-3.jpg" class="report-page-may-img leftToRight"/>
						<%--<p class="colorWhite leftToRight font12 report-page-may-p">风险保证金(元)</p>--%>
						<%--<img src="${ctx}/img/report201803/report-201803-2-4.jpg" class="report-page-may-img leftToRight"/>--%>
					</div>
				</div>
			</div>
			<div class="page page-3-1 hide">
				<div class="wrap" style="">
					<div class="report-page-1th-3-topImg"></div>
					<div class="report-page-may-3-div report-page-3rd-3">
						<img src="/hyjf-app/img/report201803/report-201803-3-1.jpg" class="report-page-may-img report-page-3rd-31">
						<img src="/hyjf-app/img/report201803/report-201803-3-2.jpg" class="report-page-may-img report-page-3rd-32">
						<img src="/hyjf-app/img/report201803/report-201803-3-3.jpg" class="report-page-may-img report-page-3rd-33">
						<img src="/hyjf-app/img/report201803/report-201803-3-4.jpg" class="report-page-may-img report-page-3rd-34">
						<img src="/hyjf-app/img/report201803/report-201803-3-5.jpg" class="report-page-may-img report-page-3rd-35">
						<img src="/hyjf-app/img/report201803/report-201803-3-6.jpg" class="report-page-may-img report-page-3rd-36">
					</div>
				</div>
			</div>
			<div class="page page-4-1 report-page-aug-4 hide">
				<div class="wrap" style="">
					<div class="report-page-aug-4-topImg"></div>
					<img src="/hyjf-app/img/report201803/report-201803-4-1.jpg" class="report-page-aug-4-1">
					<img src="/hyjf-app/img/report201803/report-201803-4-2.jpg" class="report-page-aug-4-2">
					<img src="/hyjf-app/img/report201803/report-201803-4-3.jpg" class="report-page-aug-4-3">
					<img src="/hyjf-app/img/report201803/report-201803-4-4.jpg" class="report-page-aug-4-4">
				</div>
			</div>
			<div class="page page-5-1  hide">
				<div class="wrap" style="">
					<div class="report-page-may-7-topImg"></div>
					<div class="report-page-aug-5-div">
						<div style="height: 26px;"></div>
						<img src="/hyjf-app/img/report201803/report-201803-5-1.jpg" class="report-3rd-5-1" style="width:59%">
						<img src="/hyjf-app/img/report201803/report-201803-5-2.jpg" class="report-3rd-5-2">
						<div style="height: 26px;"></div>
						<img src="/hyjf-app/img/report201803/report-201803-5-3.jpg" class="report-3rd-5-3" style="width:59%">
						<img src="/hyjf-app/img/report201803/report-201803-5-4.jpg" class="report-3rd-5-4">
					</div>
				</div>
			</div>
			<div class="page page-6-1 report-page-aug-6 hide">
				<div class="wrap" style="">
					<div class="report-page-3rd-6-topImg"></div>
					<img src="/hyjf-app/img/report201803/report-201803-6-1.jpg" class="report-oct-6-1">
					<img src="/hyjf-app/img/report201803/report-201803-6-2.jpg" class="report-oct-6-2">
					<img src="/hyjf-app/img/report201803/report-201803-6-3.jpg" class="report-oct-6-3" style="margin-top:10px">
					<img src="/hyjf-app/img/report201803/report-201803-6-4.jpg" class="report-oct-6-4">
					<img src="/hyjf-app/img/report201803/report-201803-6-5.jpg" class="report-oct-6-5">
				</div>
			</div>
			<div class="page page-7-1 report-half-13 hide">
				<div class="wrap" style="transform: scale(0.96, 0.96) translate(0px, -10px);">
					<div class="report-page-3rd-8-topImg"></div>
					<img src="/hyjf-app/img/report201803/report-201803-7-1.jpg" class="report-may-11-1" style="margin-top:50px">
					<img src="/hyjf-app/img/report201803/report-201803-7-2.jpg" class="report-may-11-2" style="margin-top:40px">
				</div>
			</div>
			<div class="page page-8-1 report-half-13 hide">
				<div class="wrap" style="transform: scale(0.96, 0.96) translate(0px, -10px);">
					<div class="report-page-may-10-topImg"></div>
					<img src="/hyjf-app/img/report201803/report-201803-8-1.jpg" class="report-may-11-1" style="margin-top: 30px;">
					<img src="/hyjf-app/img/report201803/report-201803-8-2.jpg" class="report-may-11-2" style="margin-top: 30px;">
				</div>
			</div>
			<div class="page page-9-1 report-half-13 hide">
				<div class="wrap" style="transform: scale(0.96, 0.96) translate(0px, -10px);">
					<div class="report-page-may-11-topImg"></div>
					<img src="/hyjf-app/img/report201803/report-201803-9-1.jpg" class="report-may-11-1" style="margin-top:10%;margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-9-2.jpg" class="report-may-11-2" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-9-3.jpg" class="report-may-11-3" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-9-4.jpg" class="report-half-11-4" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-9-5.jpg" class="report-half-11-5" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-9-6.jpg" class="report-half-11-6" style="margin-bottom:1%">
				</div>
			</div>
			<div class="page page-10-1 report-half-13 hide">
				<div class="wrap" style="transform: scale(0.96, 0.96) translate(0px, -10px);">
					<div class="report-page-may-11-topImg"></div>
					<img src="/hyjf-app/img/report201803/report-201803-10-1.jpg" class="report-may-11-1" style="margin-top:10%;margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-10-2.jpg" class="report-may-11-2" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-10-3.jpg" class="report-may-11-3" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-10-4.jpg" class="report-half-11-4" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-10-5.jpg" class="report-half-11-5" style="margin-bottom:1%">
					<img src="/hyjf-app/img/report201803/report-201803-10-6.jpg" class="report-half-11-6" style="margin-bottom:1%">
				</div>
			</div>
		</div>	
		<script src="${ctx}/js/zepto.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx}/js/report/touch.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx}/js/report/index.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function(){
			var length = $(".leftToRight").length;
			for(var i =0;i<length;i++){
				$(".leftToRight").eq(i).css("animation","fadeInLeft 1s ease "+0.3*i+"s 1 both")
			}
			var length1 = $(".leftToRight1").length;
			for(var i =0;i<length1;i++){
				$(".leftToRight1").eq(i).css("animation","fadeInLeft 1s ease "+0.3*i+"s 1 both")
			}
			var length2 = $(".leftToRight2").length;
			for(var i =0;i<length2;i++){
				$(".leftToRight2").eq(i).css("animation","fadeInLeft 1s ease "+0.3*i+"s 1 both")
			}
		})
	</script>
	</body>
</html>
