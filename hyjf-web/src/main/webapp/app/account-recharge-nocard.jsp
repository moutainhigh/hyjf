<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汇盈金服 - 真诚透明自律的互联网金融服务平台</title>
<jsp:include page="/head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/header.jsp"></jsp:include>
	<article class="main-content">
	    	<div class="container result">
	    		<!-- start 内容区域 -->
	    		<div class="acount-recharge">
	    			<form id="rechargeForm">
		    			<ul>
		    				<li class="avail">
		    					<div class="prompt">可用金额：</div>
		    					<div class="info"><span>541.00</span>元</div>
		    				</li>
		    				<li class="type">
		    					<div class="prompt">充值方式：</div>
		    					<div class="info">
		    						<a data-val=1 class="check onlinebank">快捷充值<span></span></a>
		    						<a class="offline" data-val=2>线下充值<span></span></a>
		    						<div class="hint">使用快捷充值前需要先绑定快捷银行卡</div>
		    					</div>
		    					<input type="hidden" value=1>
		    				</li>
		    				<li class="bound-card">
		    					<div class="prompt">绑定银行卡：</div>
		    					<div class="info acount-input">
		    						<input name="card" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入银行卡号" autocomplete="off"><em>元</em>
		    					</div>
		    				</li>
		    				<li class="offline">
		    					<div class="prompt">支付宝/网银转账：</div>
		    					<div class="info"> 
		    						收款方户名：陈松超<br/>
 									收款方帐号：6212461100002677107<br/>
 									收款方开户行：    江西银行股份有限公司总行营业部<br/>
 									注：网银转账时，银行请选择（城市商业银行）江西银行或者南昌银行
		    					</div>
		    				</li>
		    			</ul>
		    			<a class="sub">立即充值</a>
	    			</form>
	    			<div class="reminder">
	    				<h2>温馨提示</h2>
	    				<ul class="reminder-list">
		    				<li>1. 投资人充值投资所有项目均不收取充值费用；</li>
							<li>2. 最低充值金额应大于等于 1 元；</li>
							<li>3. 快捷充值当日充值资金不可当日操作取现；</li>
							<li>4. 使用快捷充值的资金，取现时原卡返回；</li>
							<li>5. 充值/提现必须为银行借记卡，不支持存折、信用卡充值；</li>
							<li>6. 汇付天下严禁利用充值功能进行信用卡套现、转账、洗钱等行为，一经发现，将封停账号30天；</li>
							<li>7. 充值期间，请勿关闭浏览器，待充值成功并返回首页后，所充资金才能入账，如有疑问，请联系客服；</li>
							<li>8. 充值需开通银行卡网上支付功能，如有疑问请咨询开户行客服；</li>
							<li>9. 支付限额请参照 <a href="#">支付说明</a>。</li>
						</ul>
	    			</div>
	    		</div>
	    		<!-- end 内容区域 --> 
	    	</div>
	    </article>
	<jsp:include page="/footer.jsp"></jsp:include>
	<script src="../dist/js/acc-re-cash/account-recharge.js"></script>
</body>
</html>