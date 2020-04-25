<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/common.jsp"%>
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
        <div class="container">
            <section class="about-detial creditcontract content">
                <div class="main-title">
                    汇盈金服互联网金融服务平台居间服务借款协议                    
                    <c:choose>
                    	<c:when test="${borrowNid == null || borrowNid == '' }">
                    		<!-- 不显示 下载协议 -->
                    	</c:when>
                    	<c:otherwise>
                    		<a class="download-txt" href="${ctx}/createAgreementPDF/creditPaymentPlan.do?borrowNid=${borrowNid }&nid=${nid }">下载协议</a>
                    	</c:otherwise>
                    </c:choose>
                </div>
                <div class="detial-list">
                    <div style="margin: 10px 0;">
                       	编号：${borrowNid }
                    </div>
                    <div style="margin: 10px 0;">
                       	签署日期： ${fn:substring(recoverTime,0,10) }
                    </div>
                    <div class="t20" style="margin-top: 30px;">
                    <p>甲方（出借人）：</p>
                   	<p>证件号码：${userInvest.idCard  }</p>
                   	<%-- <p>用户名：${userInvest.realName }</p>--%>
                   	<div class="t20" style="margin-top: 30px;">
                    <p>乙方（借款人）：${borrowUsername}</p><!-- ${borrowUsername.substring(0,1) }** -->
                    <p>证件号码：</p>
                    <div class="t20" style="margin-top: 30px;">
                    <p>丙方（居间方）：惠众商务顾问（北京）有限公司</p>
                    <p>经营地址：上海市长宁区延安西路2299号世贸大厦24楼</p>
                    <p>鉴于：</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;（1）丙方是一家在合法成立并有效存续的有限责任公司，拥有汇盈金服平台（www.hyjf.com）网站的经营权，提供信用咨询，为交易提供信息服务，并与江西银行（以下简称存管银行）建立第三方银行存管合作关系。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;（2）甲方自愿通过汇盈金服平台向乙方出借资金，甲方承诺资金来自合法的渠道，同时承诺其向丙方提供的全部信息是完全真实的；</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;（3）乙方自愿通过汇盈金服平台向甲方借入资金，乙方承诺将资金用于合法的用途，同时承诺其向丙方提供的全部信息是完全真实的。</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp; 根据《中华人民共和国合同法》及相关法律法规的规定，上述各方遵循诚实信用、平等自愿的原则，就借款事宜经协商一致，达成如下协议以资共同信守。</p>

                    <div style="margin-bottom: 20px;">
                    	<h3>第一条&nbsp;借款基本情况</h3>
                    	<p>1.1借款情况及出借金额</p>
                   	</div>
                    <ul class="agreement-bg">
                        <li>
                            <table style="width:60%" class="table-name" border="0" cellspacing="0" cellpadding="0">
									<!--  
									<tr class="head">
										<td>用户名</td>
										<td>投资金额</td>
										<td>投资期限</td>
										<td>历史年回报率</td>
										<td>投资开始日</td>
										<td>投资到期日</td>
										<td>借款方还款方式</td>
										<td>总还款本金及收益</td>
									</tr>

                                
                                    <tr class="head">
                                        <td>${userInvest.realName }${userInvest.idCard }</td>
                                        <td><fmt:formatNumber value="${userInvest.account }" pattern="#,##0.00#" />元</td>
                                        <td>${userInvest.investPeriod }</td>
                                        <td>${userInvest.interest }%</td>
                                        <td>${userInvest.investStartDay }</td>
                                        <td>${userInvest.investEndDay }</td>
                                        <td>${userInvest.repayMethod }</td>
                                        <td><fmt:formatNumber value="${userInvest.total }" pattern="#,##0.00#" />元</td>
                                    </tr>
                                    -->
                                <tbody>
                                    <tr>
                                    	<td style="width:10%">1</td>
                                    	<td style="width:25%">乙方借款金额</td>
                                    	<td style="width:55%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人民币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</td>
                                    </tr>
                                    <tr>
                                    	<td>2</td>
                                    	<td>借款期限</td>
                                    	<td>${record.borrowPeriod }</td>
                                    </tr>
                                    <tr>
                                    	<td>3</td>
                                    	<td>借款利率</td>
                                    	<td>${record.borrowApr }</td>
                                    </tr>
                                    <tr>
                                    	<td>4</td>
                                    	<td>还款方式</td>
                                    	<td>${record.borrowStyleName }</td>
                                    </tr>
                                    <tr>
                                    	<td>5</td>
                                    	<td>甲方出借金额</td>
                                    	<td>${userInvest.account }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人民币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</td>
                                    </tr>
                                    <tr>
                                    	<td>6</td>
                                    	<td>参考预期利息收益</td>
                                    	<td>${earnings }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人民币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</td>
                                    </tr>
                                </tbody>
                            </table>
                        </li>
                    </ul>
                </div>
				<div class="t20">
                  	<h3>第二条 &nbsp;借款支付</h3>
                   	<p>2.1 甲方出借资金前，应先向丙方委托的存管银行电子银行账户进行充值。甲方决定出借的，委托丙方及存管银行从其自有账户内支付全部出借本金至乙方或乙方授权委托的第三方在存管银行开立的电子银行账户。</p>
                </div>
                <div class="t20">
                	<h3>第三条 &nbsp;借款利率</h3>
                	<p>3.1 乙方应按照本协议第一条的约定，按时、足额向甲方支付借款本息。</p>
                	<p>3.2 各方一致同意在本协议借款期限内，无论国家相关政策是否发生变化，本协议项下借款利率均不作调整。</p>
                	<p>3.3 本协议项下的借款利率按年利率表示。月利率＝年利率/12,日利率＝年利率/360。</p>
                </div>
                <div class="t20">
                	<h3>第四条&nbsp;还款方式和还款日</h3>
                	<p>4.1 甲方同意乙方按下列其中之一种方式的约定偿还借款本息，具体还款方式以本协议第1.1款确定的为准：</p>
                	<p>A. 按月计息，到期还本付息。即借款到期后，一次性归还借款本息。还款日=借款发放日+月数；若遇到“借款发放日+月数”大于当月总天数的情况，则还款日为当月份的最后一天。</p>
                	<p>B. 按天计息，到期还本付息。即借款到期后，一次性归还借款本息。还款日=借款发放日+天数-1。</p>
                	<p>C. 先息后本。即乙方按月付息，借款到期后一次性偿还本金及最后一月利息。每个还款日=借款发放日+月数，若遇到“借款发放日+月数”大于当月总天数的情况，则还款日为当月份的最后一天。末次还款日为借款到期日。</p>
                	<p>D. 等额本息。即乙方按每月偿还同等数额的本息。每个还款日=借款发放日+月数，若遇到“借款发放日+月数”大于当月总天数的情况，则还款日为当月份的最后一天。末次还款日为借款到期日。</p>
                	<p>E. 等额本金。即乙方按月等额还本和剩余本金在该月所产生的利息。每个还款日=借款发放日+月数，若遇到“借款发放日+月数”大于当月总天数的情况，则还款日为当月份的最后一天。末次还款日为借款到期日。</p>
                	<p>4.2 乙方应于每期还款日或还款日前在其存管银行账户上备足当期应付之本金、利息、服务费等应付款项，并授权丙方和存管银行于还款日前或者还款日从其账户中将本金、利息划转至甲方账户。</p>
                	<p>4.3 如遇节假日还款的，乙方应提前至节假日前的最后一个工作日或还款日在其存管银行账户上备足当期应付之本金、利息、服务费等应付款项，否则视为逾期。甲乙双方同意，丙方有权根据丙方平台及存管银行系统安排（包括系统故障）通知还款日期顺延至法定节假日结束后的第一个工作日。</p>
                	<p>4.4 丙方有权向甲方、乙方收取约定的服务费（包括放款服务费及还款服务费，以下统称“服务费”）。丙方向甲方、乙方提供的服务是否收取服务费及服务费的具体标准和规则将由丙方与甲方、乙方签署的协议，以及丙方不时公开公布的规则确定。</p>
                </div>
                <div class="t20">
                	<h3>第五条&nbsp;提前还款</h3>
                	<p>5.1 甲方和乙方同意，丙方为借款标的在平台上发布的投资期限为日的，乙方如提前偿还本金的，乙方应按实际用款期限向甲方支付利息，并按协议约定借款期限向丙方支付全额服务费。</p>
                	<p>5.2 甲方和乙方同意，丙方为借款标的在平台上发布的投资期限为月的，乙方如提前偿还本金的，乙方于偿还日期前8日以内还款，按照原借款期限向甲方支付利息，并按协议约定借款期限向丙方支付全额服务费；若提前8日（包括8日）以上还款的，按照实际用款期限向甲方支付利息并额外向甲方支付对应3天的借款日利息（日利息=该笔借款年化利息/360），并按照协议约定借款期限向丙方支付全额服务费。（提前还款的计息规则详见平台公布的《提前还款计息公告》。）</p>
                </div>
                <div class="t20">
                	<h3>第六条&nbsp;逾期还款</h3>
                	<p>6.1 乙方未在还款日（包括被宣布提前到期和丙方决定顺延）在其存管银行账户上备足当期应付之本金、利息、服务费及其他应付款项的，视为乙方逾期还款。</p>
                	<p>6.2 乙方到期应付而未付的借款本息及服务费等费用，自逾期之日起，以逾期未偿还金额为基数，按日万分之六计收逾期罚息、违约金，直至乙方清偿为止。</p>
                	<p>6.3 乙方同意，若乙方任何一期应付款项逾期超过30日，则本协议项下全部借款于该期应付款项逾期第30日当日全部提前到期；该期借款逾期未满30日但已届最终借款到期日的，仍以最终借款到期日为全部借款到期日,乙方应立即清偿本协议下尚未偿付的全部本金、利息、罚息、服务费及根据本协议产生的其他全部费用。</p>
                	<p>6.4 自逾期还款之日起3天内，甲方不选择自行追讨的，则视为甲方确认委托丙方及丙方指定的第三方代为向乙方追讨，采取包括但不限于对抵质押物进行变现处置、对乙方和担保人提起诉讼等手段。但丙方及丙方指定的第三方不保证能够全额追回所有欠款，甲方对此了解并同意自行承担由此出借引起的损失和风险。</p>
                	<p>6.5 乙方同意，若乙方任何一期应付款项逾期超过30日，或乙方在逾期后出现逃避、拒绝沟通或拒不承认欠款等恶意行为的，丙方及丙方指定的第三方有权将乙方违约失信的相关信息在丙方网站上进行公示，或向媒体、用人单位、公安机关、检察机关等披露，或将乙方的“逾期记录”录入中国人民银行征信系统及/或信用数据库。同时，甲方、丙方及丙方指定的第三方可以通过发布乙方的相关信息或悬赏等方式追索债权。</p>
                	<p>6.6 乙方同意，如乙方的还款金额不足以清偿到期应付款项的，乙方的还款均应按照如下顺序清偿：实现债权费用、违约金、逾期罚息、服务费、利息、本金。</p>
                </div>
                <div class="t20">
                	<h3>第七条&nbsp;债权转让</h3>
                	<p>7.1 甲方有权将本协议项下的全部或部分借款债权转让给丙方的其他注册用户，并通过在丙方平台点击操作从而形成电子合同的形式进行转让。</p>
                	<p>7.2 甲方进行债权转让的，受让方同时受让甲方在本协议项下各项权利义务并受本协议项下各条款之约束。受让方在丙方平台点击确认受让甲方债权的，自动生成《债权转让协议》。</p>
                	<p>7.3 甲方同意，受让方有权获得甲方的债权信息；甲方同意委托丙方平台将本协议推送给受让甲方债权的受让方。</p>
                	<p>7.4 甲方进行债权转让的，该等转让信息将在乙方在丙方的平台账户显示；该等债权转让通知一经在乙方在丙方的平台网站账户显示，即视为甲方已经将债权转让事宜通知乙方，乙方不得以任何理由对此提出异议和抗辩。</p>
                	<p>7.5 在乙方出现违约事件时，如任何第三方为乙方向甲方或甲方债权的受让方部分或全部进行代偿的，自代偿之日起，甲方或甲方债权的受让方的债权及各项担保权利自动部分或全部转移至第三方名下，第三方有权向乙方及担保人追索。</p>
                </div>
                <div class="t20">
                	<h3>第八条&nbsp;陈述、声明和保证</h3>
                	<p>8.1甲方的陈述、声明和保证</p>
                	<p>8.1.1 甲方系具有完全民事权利能力和完全民事行为能力，能够独立承担民事责任的自然人，有权签订并履行本协议。</p>
                	<p>8.1.2 甲方保证在本协议有效期内保持丙方注册用户身份。</p>
                	<p>8.1.3 甲方向乙方提供的出借资金来源合法。</p>
                	<p>8.1.4 甲方保证不利用丙方进行信用卡套现、洗钱或其他违法行为。</p>
                	<p>8.1.5 甲方保证其向丙方提供的所有证件、资料均合法、真实、准确、完整有效。</p>
                	<p>8.1.6 甲方已详细阅读了丙方在其网站所发布的交易规则和出借详情等内容，充分知悉和了解本协议项下各方的权利和义务，对出借内容全部认可并接受，在本协议项下的全部意思表示真实。</p>
                	<p>8.1.7 甲方清楚可能面临的出借风险。甲方知悉乙方可能因为经营不善、丧失商业信誉等因素，存在着部分或全部借款不能回收的风险；甲方已充分了解并认识到了本次借款的特殊性、风险的不确定性以及回收该等借款可能面临的困难，经独立慎重判断后仍做出出借决定，自愿签署本协议。</p>
                	<p>8.1.8 甲方若成功出借，保证采取合法的手段和方式向乙方主张权利，并且保证不得因未实现参考预期利息收益或者出借损失而对丙方提出任何诉讼、仲裁或索赔，丙方对此不承担任何责任。</p>
                	<p>8.1.9 甲方同意并授权丙方在放款条件成就时将其出借资金划至乙方或乙方授权的第三方的存管银行账户，在乙方还款时将还款资金划入其存管银行账户。</p>
                	<p>8.1.10 甲方同意并授权丙方在必要时委托丙方或丙方指定的第三方与乙方及乙方的担保人签署抵质押借款、担保及监管协议，并将抵质押登记或交付至丙方或丙方指定的第三方名下，乙方逾期还款的，丙方或丙方指定的第三方有权按照国家法律法规及约定处理上述抵质押物用于偿还甲方及丙方的债权。</p>
                	<p>8.1.11 如乙方未按时履行还款义务，其他任何第三方向甲方代乙方履行了全部或部分还款义务的，甲方对该等履行代为还款行为予以接受并认可，该等还款履行由丙方以发送站内短信、电子邮件或者发布公告等方式进行通知。履行该等代为还款的主体有权向乙方追偿，甲方对此无任何异议。</p>
                	<p>8.1.12 甲方认可其授权丙方根据本协议所采取的全部行为和措施的法律后果均归属于其本人。</p>
                	<p>8.2 乙方的陈述、声明和保证</p>
                	<p>8.2.1 乙方是具有完全民事权利能力和完全民事行为能力，能够独立承担民事责任的自然人，有权签订并履行本协议。</p>
                	<p>8.2.2 乙方保证在本协议有效期内保持丙方注册用户身份。</p>
                	<p>8.2.3 乙方已仔细阅读并完全理解和接受本协议的内容，甲方签署和履行本协议是自愿的，其在本协议项下的全部意思表示真实。</p>
                	<p>8.2.4 乙方为了进行本协议项下的交易及签署本协议向丙方提供的所有文件、资料和凭证等都是真实、完整、准确和有效的，所提交的财务报表真实地反映了该财务报表在出具时的财务状况。若上述文件、资料发生变化时，保证及时登录丙方网站更新。</p>
                	<p>8.2.5 借款成功的，乙方保证按本协议约定的用途使用借款并按时足额归还借款本息，保证在每期还款日前将足额款项存入其银行存管账户，同意并授权丙方及存管银行将对应本息划至各甲方账户。</p>
                	<p>8.2.6 乙方保证不会利用丙方进行诈骗、非法集资或其他违法行为，否则应依法独立承担法律责任。</p>
                	<p>8.2.7 乙方承诺配合丙方及存管银行进行借款支付管理，保证自觉接受并积极配合丙方对其有关生产、经营及财务情况的调查、了解、监督及贷后管理；并按丙方的要求实时提供财务报表或其他反映资信情况的资料。</p>
                	<p>8.2.8 如乙方未按时履行还款义务，其他第三方代乙方履行了全部或部分还款义务的，乙方对该等履行代为还款行为予以接受并认可，该等还款履行由丙方以发送站内短信、电子邮件或者发布公告等方式进行通知。履行该等代为还款的主体有权向乙方追偿，乙方对此无任何异议。</p>
                	<p>8.2.9 乙方如出现下列事项对其履行本协议项下还款义务构成威胁的任何事件时，应当立即书面通知丙方，并积极按要求落实好本协议项下的借款及其他一切应付款项按期足额偿还的保障措施：</p>
                	<p>（1）乙方在与其他主体签订的借款或担保协议项下发生重大违约事项；</p>
                	<p>（2）乙方发生违法或被索赔事件；</p>
                	<p>（3）乙方经营出现严重困难和财务状况发生恶化；</p>
                	<p>（4）在本协议项下的债务未清偿前，乙方将要承担重大债务或或有债务；</p>
                	<p>（5）乙方发生重大债权债务纠纷引起诉讼、仲裁等事件；</p>
                	<p>（6）其他可能影响乙方财务状况和偿债能力的情况。</p>
                	<p>8.2.10 乙方同意甲方有权在必要时委托丙方或丙方指定的第三方与乙方及乙方的担保人签署抵质押借款、担保及监管协议，并将抵质押登记或交付至丙方或丙方指定的第三方名下，乙方逾期还款的，丙方或丙方指定的第三方有权按照国家法律法规及约定处理上述抵质押物用于偿还甲方及丙方的债权。</p>
                	<p>8.3 丙方的陈述、声明和保证</p>
                	<p>8.3.1 丙方严格按照本协议的约定，恪尽职守为甲方、乙方等提供服务。</p>
                	<p>8.3.2 在任何情形下，丙方只是提供投融资网络交易的撮合服务平台，对甲方的投资不承担保证责任。</p>
                	<p>8.3.3 在本协议项下借贷关系存续期间，丙方有权采取合法合理的措施向乙方进行还款提醒及催收工作（包括但不限于短信、电子邮件、电话、上门催收提醒、发律师函等），积极履行受托义务，协助甲方全面落实担保措施。甲方、乙方承诺对丙方的前述提醒及催收工作不持异议并积极配合。</p>
                	<p>8.3.4 丙方保留对可疑交易、非法交易、高风险交易或交易纠纷的独立判断和确定，并有权依法采取暂停交易、终止交易、向有关单位报告等必要处理措施或依有关单位合法指示行事。</p>
                	<p>8.3.5 甲乙一致同意，出现下列事由而导致丙方不能履行本协议或者造成相关损失的，丙方不承担任何法律责任：</p>
                	<p>（1）存管机构、第三方支付、丙方平台停机维护期间；</p>
                	<p>（2）电信设备、技术系统出现故障不能进行数据传输的；</p>
                	<p>（3）黑客攻击、网络供应商技术调整或故障、网站升级、银行方面的问题等原因而造成的服务中断或延迟；</p>
                	<p>（4）台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力因素，造成系统障碍不能提供服务的；</p>
                	<p>（5）境外操作风险：甲方、乙方在使用丙方平台进行投融资服务的所有期间，应当在中华人民共和国大陆境内进行操作，如因在中国大陆境外（包括港澳台地区）操作导致丙方无法向甲方、乙方提供服务，或发生错误，或受到境外法律监管，由此导致的全部法律责任和后果将由甲方、乙方独自承担。</p>
                </div>
                <div class="t20">
               		<h3>第九条&nbsp;违约事件</h3>
               		<p>9.1 乙方存在下述任一事件，均构成本协议项下的违约事件：</p>
               		<p>9.1.1 乙方在本协议项下作出的陈述、声明和保证被证明是不真实的，或是具有误导性的，或者乙方违反本协议所做的任何陈述、声明和保证的；</p>
                	<p>9.1.2 乙方涉及将会对其财务状况或乙方根据本协议履行其义务的能力构成严重不利影响的任何诉讼、仲裁或行政程序的；</p>
                	<p>9.1.3 乙方的财产被依法查封、冻结、扣押或监管，已经或可能影响乙方在本协议项下义务的履行，且不能及时提供有效补救措施的；</p>
                	<p>9.1.4 发生任何其他事件或情况，实质性地对甲方在本协议项下的权利产生不利影响的。</p>
                	<p>9.2 上述违约事件发生后，丙方有权采取以下任何一项或多项措施：</p>
                	<p>9.2.1 立即停止本协议项下借款的划付；</p>
                	<p>9.2.2 宣布借款立即到期，并要求乙方立即偿还全部已发放的借款本金、利息及其他实现债权的费用；</p>
                	<p>9.2.3 要求乙方追加或更换担保人、抵质物；</p>
                	<p>9.2.4 宣布实施或实现有关借款的任何担保项下的权利；</p>
                	<p>9.2.5 解除本协议；</p>
                	<p>9.2.6 采取其他适当的方式。</p>
                </div>
                <div class="t20">
               		<h3>第十条&nbsp;其他</h3>
               		<p>10.1 协议各方应当对为签署和履行本协议的目的而了解到的对方有关资料及情况等进行保密，非因促进交易、一方违约，或相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），不得对外披露。</p>
               		<p>10.2 乙方在本协议项下应付的所有款项应全额支付，不得作任何性质的冲抵、扣减或预提，亦不得同甲方所欠乙方的任何债务相抵销。</p>
               		<p>10.3 甲方承担和缴纳所获投资收益的应纳税额，丙方和乙方不负责代扣代缴。</p>
               		<p>10.4 各方确认并同意，委托丙方对本协议项下的任何数额进行计算；在无明显错误的情况下，丙方对本协议项下任何数额的任何证明或确定，应作为该数额有关事项的终局证明。</p>
               		<p>10.5 本协议及本协议所涉及的任何事项适用中国法律，并按照中国法律进行解释。各方在履行本协议过程中所发生的争议，首先应协商解决；协商无法达成一致的，各方一致同意将争议提交给丙方所在地上海市长宁区人民法院解决。</p>
               		<p>10.6 除非法律另有规定，本协议任何条款的无效或不可执行，不影响其他条款的有效性和可执行性，也不影响整个协议的效力。</p>
               		<p>10.7 本协议未尽事宜，由各方协商一致后另行签订补充协议。本协议的注解、附件、补充规定为本协议组成部分，与本协议具有同等法律效力。</p>
               		<p>10.8 本协议采用电子文本形式制成，自乙方发布的借款需求募集成功且丙方或存管银行将甲方的出借资金支付至乙方或乙方授权委托的第三方存管银行账户时自动生成并生效。甲方将出借资金支付至乙方或乙方授权委托的第三方存管银行账户之前，甲方投标支付的资金不产生利息。乙方发布的借款需求不能募集成功的，则甲方已投标的资金原路返回至甲方存管银行账户。本协议永久保存在丙方为此设立的专用服务器上备查，各方均认可该形式的协议效力。</p>
               		<p>10.9 本协议所涉借贷项目的所有甲方与乙方之间的借贷法律关系均是相互独立的，一旦乙方逾期还款，任何一个甲方均有权单独向乙方追索或者提起诉讼，或委托丙方向乙方追索或者提起诉讼。如乙方逾期支付付费或提供虚假信息的，丙方亦可单独向乙方追索或者提起诉讼。</p>
               		<p>10.10 本协议约定的各项内容与丙方平台发布的规则不一致的，以丙方平台实时发布的规则为准。</p>
               		<p>10.11 本协议的需传递的通知、文件、资料等，可采用在丙方上发送站内短信、发送电子邮件或者发布公告等方式送达，协议各方对此表示认可。</p>
               		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（以下无正文）</p>
                </div>
                <div class="t20">
                    <p>甲方（出借人）：</p>
                    <p>乙方（借款人）：</p>
                    <p>丙方（居间方）：</p>
                </div>
            </section>
        </div>
    </article>
	<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>