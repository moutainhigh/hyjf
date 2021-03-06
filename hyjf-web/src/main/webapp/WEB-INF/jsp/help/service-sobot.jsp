<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汇盈金服 - 真诚透明自律的互联网金融服务平台</title>
	<style type="text/css">
			
        * {
            margin: 0;
            padding: 0;
        }

        html,
        body {
            width: 100%;
            height: 100%;
            font-family: "Microsoft Yahei", "Hiragino Sans GB", "Helvetica Neue", Helvetica, tahoma, arial, Verdana, sans-serif, "WenQuanYi Micro Hei", "\5B8B\4F53";
        }

        .container {
            background: #f5f9fa;
            width: 100%;
            height: 100%;
            overflow-y: auto;
            padding: 18px 0 18px 14px;
            box-sizing: border-box;
            -ms-overflow-style: none;
            overflow: -moz-scrollbars-none;
        }

        .section {
            padding-right: 14px;
        }

        .section:first-child {
            padding-bottom: 20px;
            margin-bottom: 20px;
            border-bottom: 1px solid #e6e6e6;
        }

        .section>.title {
            font-size: 16px;
            color: #333;
            font-weight: bold;
            height: 22px;
        }
        .section>.title a{
        	cursor: pointer;
        	font-weight: normal;
        	font-size: 16px;
        	padding-bottom:5px;
        }
		.section>.title a.select{
			color:#ff5b29;
			position: relative;
		}
		.section>.title a.select::before{
			content: '';
			width: 55px;
			margin: 0 auto;
			height: 2px;
			background-color:#ff5b29;
            background-size: 100% 100%;
            display: inline-block;
            position: absolute;
            bottom: -2px;
            left: 5px;
		}
        .section>.title.top::before {
            content: '';
            display: inline-block;
            width: 4px;
            height: 18px;
            margin-right: 8px;
            background-color:#ff5b29;
            background-size: 100% 100%;
            vertical-align: top;
            margin-top: 2px;
        }

        .section .list-item {
            list-style: none;
        }

        .section .list-item a {
            text-decoration: none;
        }

        .section .content.service {
            overflow: hidden;
        }

        .section .service .list-item {
            width: calc((100% - 14px) / 2);
            float: left;
            margin-right: 14px;
        }

        .section .service .list-item:nth-child(2n) {
            margin-right: 0;
        }

        .service .list-item .name {
            font-size: 14px;
            color: #666;
            display: block;
            width: 100%;
            margin: 14px auto 0;
            text-align: center;
            position: relative;
        }



        .service .list-item .icon {
            width: 60px;
            height: 60px;
            margin: 10px auto 0;
            background-size: contain;
        }

        .section .content.question {
            margin-top: 15px;
        }

        .question .list-item {
            margin-bottom: 10px;
            padding-left: 12px;
            background-repeat: no-repeat;
            background-position: left center;
            background-image: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyNpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQwIDc5LjE2MDQ1MSwgMjAxNy8wNS8wNi0wMTowODoyMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjZFRjg0M0I4MzYyNzExRThBQjhERjg3QkMxNkYwM0Q5IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjZFRjg0M0I5MzYyNzExRThBQjhERjg3QkMxNkYwM0Q5Ij4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MzcwQ0E0ODkzNjI3MTFFOEFCOERGODdCQzE2RjAzRDkiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6MzcwQ0E0OEEzNjI3MTFFOEFCOERGODdCQzE2RjAzRDkiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4r065+AAAAKklEQVR42mKcOXNmOAMDw1QGCMhmgXKEoQJTmRjQAEggG4jfQnE2QIABAAxCBxlGVHz0AAAAAElFTkSuQmCC')
        }

        .question .list-item.position {
            background-position-y: 10px;
            width: 240px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow:ellipsis;
        }

        .question .list-item a {
            font-size: 14px;
            color: #666;
        }

        .question .list-item a:hover {
            color: #ff5e4d;
        }

        .password-icon {
        	background-image: url('${cdn}/data/cdn/service-img/6.png')
        }

        .list-icon {
        	background-image: url('${cdn}/data/cdn/service-img/1.png')
        }

        .card-icon {
        	background-image: url('${cdn}/data/cdn/service-img/3.png')
        }

        .guard-icon {
        	background-image: url('${cdn}/data/cdn/service-img/5.png')
        }
        
        .bank-card-icon {
        	background-image: url('${cdn}/data/cdn/service-img/2.png')
        }
        .recharge-icon {
        	background-image: url('${cdn}/data/cdn/service-img/4.png')
        }

        ::-webkit-scrollbar {
            width: 4px;
            height: 12px
        }

        ::-webkit-scrollbar-button:vertical {
            display: none
        }

        ::-webkit-scrollbar-thumb {
            border-radius: 10px;
            background-color: transparent
        }

        ::-webkit-scrollbar-thumb:hover {
            background-color: #cccccc
        }
    
		</style>
</head>
<body>
    <div class="container" style="padding-top:0">
        <div class="section">
            <ul class="content service">
                <li class="list-item">
                    <a rel="noopener noreferrer" target="_blank" href="${ctx}/wisdomtooth/modifyCode.do">
                        <div class="icon password-icon"></div>
                        <span class="name">修改密码</span>
                    </a>
                </li>
                <li class="list-item">
                    <a rel="noopener noreferrer" target="_blank" href="${ctx}/wisdomtooth/modifyTelPhone.do">
                        <div class="icon list-icon"></div>
                        <span class="name">修改手机号</span>
                    </a>
                </li>
                <li class="list-item">
                    <a rel="noopener noreferrer" target="_blank" href="${ctx}/wisdomtooth/modifyBusinessCode.do">
                        <div class="icon card-icon"></div>
                        <span class="name">修改交易密码</span>
                    </a>
                </li>
                <li class="list-item">
                    <a rel="noopener noreferrer" target="_blank" href="${ctx}/wisdomtooth/findPassword.do">
                        <div class="icon guard-icon"></div>
                        <span class="name">找回密码</span>
                    </a>
                </li>
                <li class="list-item">
                    <a rel="noopener noreferrer" target="_blank" href="${ctx}/wisdomtooth/modifyBankCard.do">
                        <div class="icon bank-card-icon"></div>
                        <span class="name">修改绑定银行卡</span>
                    </a>
                </li>
                <li class="list-item">
                    <a rel="noopener noreferrer" target="_blank" href="${ctx}/wisdomtooth/offlineRecharge.do">
                        <div class="icon recharge-icon"></div>
                        <span class="name">线下充值流程</span>
                    </a>
                </li>
            </ul>
        </div>
        <div class="section" id="tabList">
            <h1 class="title" style="margin-left: 7px;"><a class="select">常见问题</a><a style="margin-left: 15px;">公司动态</a></h1>
            <ul class="content question">
              
            </ul>
            <ul class="content question" style="display: none;">
                
            </ul>
        </div>
    </div>
</body>
<script src="${cdn }/dist/js/lib/jquery.min.js"></script>
<script type="text/javascript">
	$('.section .title a').each(function(i,el){
		$(this).click(function(){
			$('#tabList ul').hide();
			$('.section .title a').removeClass('select')
			$(this).addClass('select')
			$('#tabList ul').eq(i).show()
		})
	})
	$.ajax({
		type:"get",
		url:"/wisdomtooth/contentHelps.do",
		success:function(res){
			var s='';
			for (var i=0;i<res.pageList.length;i++) {
				s+=' <li class="list-item position"><a href="/help/index.do?issure='+res.pageList[i].itemId+'&side='+res.pageList[i].typeId+'" target="_blank" title="'+res.pageList[i].title+'">'+res.pageList[i].title+'</a></li>'
			}
			$('#tabList ul:eq(0)').append(s)
		}
	});
	$.ajax({
		type:"POST",
		url:"/contentarticle/getNoticeListPage.do?type=2",
		data:{
			pageSize:5,
			paginatorPage:1
		},
		success:function(res){
			var s='';
			for (var i=0;i<res.contentArticleList.length;i++) {
				s+=' <li class="list-item position"><a href="/contentarticle/getNoticeInfo.do?id='+res.contentArticleList[i].id+'" target="_blank" title="'+res.contentArticleList[i].title+'">'+res.contentArticleList[i].title+'</a></li>'
			}
			$('#tabList ul:eq(1)').append(s)
		}
	});
</script>


</html>
