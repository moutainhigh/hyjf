<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/club.css">
</head>
<body>
	<div class="club-level-top text_white font11">
			<div class="my-club-top-left">
				<img src="${ctx}/img/icon_woman.png" alt="头像" class="my-club-top-img"/>
				<div class="my-club-top-level border-radius10"><img src="${ctx}/img/l1.png" class="my-club-l1"/>铂金会员</div>
			</div>
			<div class="my-club-top-right">
				<p>Hi,&nbsp;san</p>
				<div class="my-club-top-process-out border-radius10">
					<span class="my-club-top-process-in border-radius10 ">35000/500000</span>
				</div>
				<span>还差15000点成长值升级至黑金会员</span>
			</div>
		</div>
		<div class="my-club-more">VIP特权<a href="">More</a></div>
		<div class="my-club-content">
			<div class="my-club-content-item">
				<a href="">
					<img src="${ctx}/img/my-club-1.jpg" alt="img" />
					<p>欢迎礼包</p>
					<p class="my-club-content-p-2">有券在手畅享加息</p>
				</a>
			</div>
			<div class="my-club-content-item">
				<a href="">
					<img src="${ctx}/img/my-club-2.jpg" alt="img" />
					<p>升级礼包</p>
					<p>任性投资开心赚</p>
				</a>
			</div>
			<div class="my-club-content-item">
				<a href="">
					<img src="${ctx}/img/my-club-3.jpg" alt="img" />
					<p>专属活动</p>
					<p>惊喜连连享不停</p>
				</a>
			</div>
			<div class="my-club-content-item">
				<a href="">
					<img src="${ctx}/img/my-club-4.jpg" alt="img" />
					<p>尊贵标识</p>
					<p>彰显您的与众不同</p>
				</a>
			</div>
			<div class="my-club-content-item">
				<div class="my-club-content-wait">敬请期待</div>
				<a href="">
					<img src="${ctx}/img/my-club-5.jpg" alt="img" />
					<p>加倍积分</p>
					<p>万千好礼轻松换</p>
				</a>
			</div>
			<div class="my-club-content-item">
				<div class="my-club-content-wait">敬请期待</div>
				<a href="">
					<img src="${ctx}/img/my-club-6.jpg" alt="img" />
					<p>生日礼包</p>
					<p>慢慢人生汇盈见证</p>
				</a>
			</div>
		</div>
		<div class="my-club-more">升级攻略</div>
		<div class="my-club-bottom">
			<p>成长值=投资金额*成长系数</p>
			<p>仅汇直投项目投资金额参与成长值计算</p>
			<table border="" cellspacing="" cellpadding="" class="my-club-table">
				<tr class="my-club-table-header">
						<td>月份</td>
						<td>成长系数</td>
				</tr>
				<tr>
					<td>1个月</td>
					<td>0.10</td>
				</tr>
				<tr>
					<td>2个月</td>
					<td>0.30</td>
				</tr>
				<tr>
					<td>3个月</td>
					<td>0.50</td>
				</tr>
				<tr>
					<td>4个月</td>
					<td>0.70</td>
				</tr>
				<tr>
					<td>5个月</td>
					<td>0.90</td>
				</tr>
				<tr>
					<td>6个月</td>
					<td>1.10</td>
				</tr>
				<tr>
					<td>12个月</td>
					<td>2.30</td>
				</tr>
			</table>
			<p class="my-club-bottom-p-last">会员有效期至：2017-07-01</p>
			<div class="newRegReg"><a href="">立即投资&nbsp;赚取V值</a></div>
		</div>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script>
	$(function(){
		var numberA = $(".my-club-top-process-in").text()
		var index = numberA.indexOf("/");
		var left = numberA.substring(0,index);
		var right = numberA.substring(index+1)
		var percent = (left/right)*100;
		if(percent==0){
			$(".my-club-top-process-in").css("min-width","0%")
		}
		$(".my-club-top-process-in").css("width",percent+"%")
	})
</script>
</body>
</html>