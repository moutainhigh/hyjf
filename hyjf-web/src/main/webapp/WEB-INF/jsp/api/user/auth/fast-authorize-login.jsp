<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汇盈金服 - 真诚透明自律的互联网金融服务平台</title>
<jsp:include page="/headResponsive.jsp"></jsp:include>
	<link rel="stylesheet" href="${cdn}/css/fast-authorize.css" type="text/css" />
<style>

</style>
</head>
<body style="background:#f2f2f2">
<header id="header">
	<div class="nav-main">
		<div class="container">
			<a href="javascript:;" class="logo" itemid="hd1"><img src="${cdn}/dist/images/logo.png?v=20171123" alt="汇盈金服" /></a>
		</div>
	</div>
</header>
     <section class="main-content">
        <div class="container result">
            <div class="fast-sq">
            	<form action="" id="loginForm">
            		<c:forEach items='${apiForm }' var="record" begin="0" step="1" varStatus="status">
						<input type="hidden" size=800 name='apiUserPostBean_${record.key }' id='apiUserPostBean_${record.key }' value='<c:out value='${record.value}' escapeXml="true" />'/>
					</c:forEach>
					<input type="hidden" name="loginBean_loginUserName" id="loginBean_loginUserName" value="${mobile}"/>
	           		<p class="tit">账号密码授权</p>
	           		<p class="welcome">欢迎使用汇盈金服账户进行安全授权，并同时登录汇盈金服</p>
	           		<div class="fast-login" style="margin-bottom:30px">
	           			<label><span>账号</span><input type="text" placeholder="请输入手机号或用户名" name="loginBean_loginUserName_Text" value="" class="text" id="loginBean_loginUserName_Text" maxlength="16"/></label>
	           			<label><span>密码</span><input type="password" placeholder="请输入密码"  class="text" name="loginBean_loginPassword" id="loginBean_loginPassword" maxlength="20"/></label>
	           			<div><p style="text-align:right;line-height:25px;width:320px;margin:0 auto;position:relative;top:10px"><a href="${ctx }/user/findpassword/init.do" style="text-decoration:none;color: #666;" class="forget">忘记密码</a></p></div>
	           		</div>
	           		<div class="check"><label><input type="checkbox" name="loginBean_readAgreement" id="loginBean_readAgreement" checked/><span>我已阅读并同意</span></label><a href="${ctx}/agreement/authagreement.do" target="_blank">《汇盈金服授权协议》</a></div>
	           		<a class="btn-sq">立即授权</a>
	           		<c:if test="${instcode != '11000002' }">
		           		<div class="otherway"><a href="${ctx }/user/regist/init.do">注册新账号</a></div>
	           		</c:if>
           		</form>
            </div>
        </div>
    </section>
    <script src="${cdn }/dist/js/lib/jquery.min.js"></script>
    <script src="${cdn}/dist/js/lib/jquery-migrate-1.2.1.js"></script>
    <script src="${cdn }/dist/js/lib/jquery.validate.js"></script>
    <script src="${cdn }/dist/js/lib/jquery.metadata.js" type="text/javascript" charset="utf-8"></script>
    <script src="${cdn }/js/jquery.md5.js" type="text/javascript" charset="utf-8"></script>
    <script src="${cdn }/dist/js/lib/nprogress.js"></script>
	<script src="${cdn }/dist/js/utils.js"></script>
	<script src="${cdn }/js/common/common.js"></script>
    <script src="${cdn }/dist/js/login/fast-authorize-login.js"></script>
</body>
</html>