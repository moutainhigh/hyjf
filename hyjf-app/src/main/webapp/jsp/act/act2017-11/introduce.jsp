<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>双十一活动</title>
		<style type="text/css">
			* {
				margin: 0;
				padding: 0;
			}
			
			ul,
			li {
				list-style: none;
			}
			
			.introduce img {
				display: block;
				width: 100%;
			}
			
			.tab-list {
				width: 10.05rem;
				margin: auto;
				position: absolute;
				bottom: 1.22rem;
				left: 0;
				right: 0;
			}
			
			.tab-cur {
				width: 4.12rem;
				height: 1.43rem;
				font-size: 0.55rem;
				color: #FFFFFF;
				background: url(${ctx}/img/act20171111/tab-cur-1.png) no-repeat center;
				background-size: cover;
				line-height: 1.43rem;
				position: absolute;
				top: 0;
				padding-left: 0.80rem;
				box-sizing: border-box;
				-webkit-box-sizing: border-box;
			}
			
			.cur-0 {
				left: 0;
				z-index: 3;
			}
			
			.cur-1 {
				left: 3.10rem;
				z-index: 2
			}
			
			.cur-2 {
				left: 6.20rem;
				z-index: 1;
			}
			
			.active-cur {
				background: url(${ctx}/img/act20171111/tab-cur-2.png) no-repeat center;
				background-size: cover;
				color: #ff1c01;
			}
			
			.tab-cont {
				display: none;
			}
			
			.active-cont {
				display: block;
			}
			
			.introduce-tab {
				position: absolute;
				top: 9.75rem;
				left: 0;
				right: 0;
			}
		</style>
	</head>

	<body>
		<div class="introduce">
			<div class="introduce-tab">
				<ul class="tab-list">
					<li class="tab-cur cur-0 active-cur">活动一</li>
					<li class="tab-cur cur-1">活动二</li>
					<li class="tab-cur cur-2">活动三</li>
				</ul>
			</div>
			<div class="content-list">
				<div class="tab-cont cont-0 active-cont">
					<img src="${ctx}/img/act20171111/doubleEleven-1-1.jpg?version=20171121" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-1-2.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-1-3.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-1-4.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-1-5.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-1-6.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-1-7.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-1-8.jpg" alt="" />
				</div>
				<div class="tab-cont cont-1">
					<img src="${ctx}/img/act20171111/doubleEleven-2-1.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-2-2.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-2-3.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-2-4.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-2-5.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-2-6.jpg" alt="" />
				</div>
				<div class="tab-cont cont-2">
					<img src="${ctx}/img/act20171111/doubleEleven-3-1.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-3-2.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-3-3.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-3-4.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-3-5.jpg" alt="" />
					<img src="${ctx}/img/act20171111/doubleEleven-3-6.jpg" alt="" />
				</div>
			</div>
		</div>
	</body>
</html>
<script src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript">
	document.documentElement.style.fontSize = $(document.documentElement).width() / 12.42 + 'px';
	$(window).on('resize', function() {
		document.documentElement.style.fontSize = $(document.documentElement).width() / 12.42 + 'px';
	});
</script>
<script type="text/javascript">
	$(".tab-list").delegate(".tab-cur", "click", function() {
		var idx = $(this).index();
		$(this).addClass('active-cur').siblings().removeClass('active-cur');
		$('.cont-' + idx).show().siblings().hide()
	})

	$('.cur-0').click(function() {
		$(this).css({
			'z-index': '3'
		});
		$('.cur-1').css({
			'z-index': '2'
		});
		$('.cur-2').css({
			'z-index': '1'
		});

	})
	$('.cur-1').click(function() {
		$(this).css({
			'z-index': '3'
		});
		$('.cur-0').css({
			'z-index': '1'
		});
		$('.cur-2').css({
			'z-index': '2'
		});

	})
	$('.cur-2').click(function() {
		$(this).css({
			'z-index': '3'
		});
		$('.cur-0').css({
			'z-index': '1'
		});
		$('.cur-1').css({
			'z-index': '2'
		});

	})
</script>