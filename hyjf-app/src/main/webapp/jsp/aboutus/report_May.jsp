<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>2016年5月运营报告 </title>
		<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/animations.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/report-index.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/page.css"/>
	</head>
	<body class="bg_grey response report-page">
		<div>
			<img src="${ctx}/img/report-up.png" class="reprot-up">
			<div class="page page-1-1 page-current page-may-1-1">
				<div class="wrap">
					<div class="report-may-1-main">
						<img src="${ctx}/img/report-may-logo.png?v=20180326" class="pt-page-moveFromTop"/>
					</div>
				</div>
			</div>
			<div class="page page-2-1 hide">
				<div class="wrap">
					<div class="report-page-may-2-topImg"></div>
					<div class="report-page-may-2-div">
						<p class="colorWhite leftToRight font12 report-page-may-p">累计交易额(元)</p>
						<img src="${ctx}/img/report-may-2-num1.png" class="report-page-may-img leftToRight"/>
						<p class="colorWhite leftToRight font12 report-page-may-p">平台注册人数(人)</p>
						<img src="${ctx}/img/report-may-2-num2.png" class="report-page-may-img leftToRight"/>
						<p class="colorWhite leftToRight font12 report-page-may-p">累计赚取收益(元)</p>
						<img src="${ctx}/img/report-may-2-num3.png" class="report-page-may-img leftToRight"/>
						<p class="colorWhite leftToRight font12 report-page-may-p">风险保证金(元)</p>
						<img src="${ctx}/img/report-may-2-num4.png" class="report-page-may-img leftToRight"/>
					</div>
				</div>
			</div>
			<div class="page page-3-1 hide">
				<div class="wrap">
					<div class="report-page-may-3-topImg"></div>
					<div class="report-page-may-3-div">
						<img src="${ctx}/img/report-may-3-1.png" class="report-may-3-1"/>
						<img src="${ctx}/img/report-may-3-2.png" class="report-may-3-2"/>
						<img src="${ctx}/img/report-may-3-3.png" class="report-may-3-3"/>
					</div>
					<p class="colorWhite font12 tal page-3-1-p-1">当月预期平均收益率<span class="font16 colore0b274">&nbsp;11.83%</span></p>
					<p class="colorWhite font12 tal page-3-1-p-2">当月充值<span class="font16 colore0b274">&nbsp;7,241</span>笔,共<span class="font16 colore0b274">&nbsp;4.27亿</span>元</p>
				</div>
			</div>
			<div class="page page-4-1 hide">
				<div class="wrap">
					<div class="report-page-may-4-topImg"></div>
					<div class="report-page-may-4-div">
						<div>
							<img src="${ctx}/img/report-may-4-l1.png" class="report-may-4-1 report-may-4-11"/>
							<img src="${ctx}/img/report-may-4-r1.png" class="report-may-4-2"/>
						</div>
						<div>
							<img src="${ctx}/img/report-may-4-l2.png" class="report-may-4-1 report-may-4-12"/>
							<img src="${ctx}/img/report-may-4-r2.png" class="report-may-4-2"/>
						</div>
						<div>
							<img src="${ctx}/img/report-may-4-l3.png" class="report-may-4-1 report-may-4-13"/>
							<img src="${ctx}/img/report-may-4-r3.png" class="report-may-4-2"/>
						</div>
						<img src="${ctx}/img/report-may-4-p1.png" class="page-4-1-p-1" style="margin:1% 0 0 0"/>
						<img src="${ctx}/img/report-may-4-p2.png" class="page-4-1-p-2" style="top:0px"/>
					</div>
				</div>
			</div>
			<div class="page page-5-1 hide">
				<div class="wrap">
					<div class="report-page-may-5-topImg"></div>
					<div class="report-page-may-2-div">
						<p class="colorWhite leftToRight1 font12 report-page-may-p">累计待还金额(元)</p>
						<img src="${ctx}/img/report-may-5-num1.png" class="report-page-may-img leftToRight1"/>
						<p class="colorWhite leftToRight1 font12 report-page-may-p">当月新增待还金额(人)</p>
						<img src="${ctx}/img/report-may-5-num2.png" class="report-page-may-img leftToRight1"/>
						<p class="colorWhite leftToRight1 font12 report-page-may-p">风险保证金(元)</p>
						<img src="${ctx}/img/report-may-5-num3.png" class="report-page-may-img leftToRight1"/>
					</div>
				</div>
			</div>
			<div class="page page-6-1 hide">
				<div class="wrap">
					<div class="report-page-may-6-topImg"></div>
					<div class="report-page-may-6-div">
						<img src="${ctx}/img/report-may-6-num1.png" class="report-page-may-img1 leftToRight2"/>
						<img src="${ctx}/img/report-may-6-num2.png" class="report-page-may-img2 leftToRight2" style="margin-top: 2%;"/>
						<div class="page-6-1-div"></div>
						<img src="${ctx}/img/report-may-6-num3.png" class="report-page-may-img1 leftToRight2"/>
						<img src="${ctx}/img/report-may-6-num4.png" class="report-page-may-img2 leftToRight2" style="margin-top: 2%;"/>
						<div class="page-6-1-div"></div>
						<img src="${ctx}/img/report-may-6-num5.png" class="report-page-may-img1 leftToRight2"/>
						<img src="${ctx}/img/report-may-6-num6.png" class="report-page-may-img2 leftToRight2" style="margin-top: 2%;"/>
					</div>
				</div>
			</div>
			<div class="page page-7-1 hide">
				<div class="wrap">
					<div class="report-page-may-7-topImg"></div>
					<div class="report-page-may-7-div">
						<div>
							<img src="${ctx}/img/report-may-7-i1.png" class="report-may-71"/>
							<img src="${ctx}/img/report-may-7-c1.png" class="report-may-72"/>
						</div>
						<div>
							<img src="${ctx}/img/report-may-7-c2.png" class="report-may-73"/>
							<img src="${ctx}/img/report-may-7-i2.png" class="report-may-74"/>
						</div>
						<div>
							<img src="${ctx}/img/report-may-7-i3.png" class="report-may-75"/>
							<img src="${ctx}/img/report-may-7-c3.png" class="report-may-76"/>
						</div>
					</div>
				</div>
			</div>
			<div class="page page-8-1 hide">
				<div class="wrap">
					<div class="page-8-1-div">
						<p class="font20">本月十大投资人</p>
						<img src="${ctx}/img/report-may-8-1.png" class="report-may-8-1"/>
						<img src="${ctx}/img/report-may-8-2.png" class="report-may-8-2"/>
						<img src="${ctx}/img/report-may-8-3.png" class="report-may-8-3"/>
					</div>
				</div>
			</div>
			<div class="page page-9-1 hide">
				<div class="wrap">
					<div class="report-page-may-9-topImg"></div>
					<div class="page-8-1-div page-9-1-div">
						<img src="${ctx}/img/report-may-9-1.png" class="report-may-9-1"/>
						<img src="${ctx}/img/report-may-9-2.png" class="report-may-9-2"/>
						<img src="${ctx}/img/report-may-9-3.png" class="report-may-9-3"/>
						<img src="${ctx}/img/report-may-9-4.png" class="report-may-9-4"/>
					</div>
				</div>
			</div>
			<div class="page page-10-1 hide">
				<div class="wrap">
					<div class="report-page-may-10-topImg"></div>
					<div class="page-10-1-div page-9-1-div">
						<img src="${ctx}/img/report-may-10-1.png" class="report-may-10-1"/>
						<img src="${ctx}/img/report-may-10-2.png" class="report-may-10-2"/>
					</div>
				</div>
			</div>
			<div class="page page-11-1 hide">
				<div class="wrap">
					<div class="report-page-may-11-topImg"></div>
					<img src="${ctx}/img/report-may-11-1.png" class="report-may-11-1"/>
					<img src="${ctx}/img/report-may-11-2.png" class="report-may-11-2"/>
					<img src="${ctx}/img/report-may-11-3.png" class="report-may-11-3"/>
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
				$(".leftToRight").eq(i).css("animation","fadeInLeft 1s ease "+0.2*i+"s 1 both")
			}
			var length1 = $(".leftToRight1").length;
			for(var i =0;i<length1;i++){
				$(".leftToRight1").eq(i).css("animation","fadeInLeft 1s ease "+0.2*i+"s 1 both")
			}
			var length2 = $(".leftToRight2").length;
			for(var i =0;i<length2;i++){
				$(".leftToRight2").eq(i).css("animation","fadeInLeft 1s ease "+0.2*i+"s 1 both")
			}
		})
	</script>
	</body>
</html>>
</html>
