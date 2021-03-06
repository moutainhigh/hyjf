<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/jsp/base/pageBase.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="hyjf" uri="/hyjf-tags"%>
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.js"></script>
<script type='text/javascript' src="${webRoot}/jsp/manager/content/contentoperationreport/monoperationreport.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.js"></script>
<script src="${themeRoot}/vendor/plug-in/bootstrap-fileinput/jasny-bootstrap.js"></script>
<script type="text/javascript" src="${themeRoot}/vendor/plug-in/bootstrap-ladda/spin.min.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/bootstrap-ladda/ladda.min.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/tinymce/jquery.tinymce.min.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/region-select.js"></script>
<script type="text/javascript"
		src="${themeRoot}/assets/js/common.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/load-image.all.min.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/canvas-to-blob.min.js"></script>
<!-- blueimp Gallery script -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-ui.js"></script>
<script type="text/javascript"
		src="${themeRoot}/vendor/plug-in/jquery.validform_v5.3.2.js"></script>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    var webPath = "${ctx}";
</script>

<style type="text/css">
	.myimg img{
		width:50px;height:50px;
	}
	.Validform_error {
		background-color: #ffe7e7;
	}
	.Validform_checktip {
		display: block;
		/*margin-left: 8px;*/
		line-height: 20px;
		height: 20px;
		overflow: hidden;
		color: #999;
		font-size: 12px;
	}
	.Validform_wrong {
		color: red;
		/*padding-left: 20px;*/
		white-space: nowrap;
		background: url(images/error.png) no-repeat left center;
	}

</style>
<%--<div class="container">--%>
<form id="monthForm" action="monthOperationReportAction" method="post" class="form-inline" role="form" target="_top">
	<%-- 运营报告 --%>
	<input type="hidden" name="operationReport.id" id="operationReportId" value="${operationreportCommonForm.operationReport.id }" />
	<input type="hidden" name="operationReport.isRelease" id="isRelease" value="${operationreportCommonForm.operationReport.isRelease }" />
	<input type="hidden" name="operationReport.operationReportType" id="operationReportType" class="form-select2"　style="width:200%" value="${operationreportCommonForm.operationReport.operationReportType}"/>
	<input type="hidden" id="monthInput" name="monthlyOperationReport.month" class="monthStyle" value="${operationreportCommonForm.monthlyOperationReport.month}"/>
	<input type="hidden" id="yearInput" name="operationReport.year"  class="yearStyle" value="${operationreportCommonForm.operationReport.year}"/>
	<input type="hidden" name="monthlyOperationReport.id" id="monthId" value="${operationreportCommonForm.monthlyOperationReport.id }" />
	<input type="hidden" name="userOperationReport.id" id="userId" value="${operationreportCommonForm.userOperationReport.id }" />
	<input type="hidden" name="tenthOperationReport.id" id="tenthId" value="${operationreportCommonForm.tenthOperationReport.id }" />
	<!-- 中、英文标题 -->
	<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<div style="width:83%;" class="form-group">
		<div  class="text">
			<h5><font size="4px" style="margin:4px 0px 0px 0px"><b>Top</b></font></h5>
		</div>

		<div class="col-xs-10">
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					中文标题：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text"  id="cnName" class="cnName inputxt" datatype="*1-12" errormsg="请输入标题" name="operationReport.cnName" placeholder="" value="${operationreportCommonForm.operationReport.cnName}"/>
				</div>

			</div>
			<div  style="width:66.6%; float:left;">
				<div style="float:left;width: 15%">
					英文标题：
				</div>
				<div style="float:left;width: 85%" >
					<input type="text" id="enName" class="enName" name="operationReport.enName" datatype="/^[a-zA-Z\d ]+$/"  nullmsg="请输入标题" errormsg="只能输入英文，空格或数字" placeholder="" value="${operationreportCommonForm.operationReport.enName}"/>
				</div>
			</div>
		</div>
	</div>

	<!-- 业绩总览 -->
	<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<div style="width:83%;" class="form-group" >
		<div  class="text">
			<h5><font size="4px" style="margin:0px 0px 0px 0px"><b>业绩总览</b></font></h5>
		</div>
		<div class="col-xs-10">
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					累计交易额：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="allAmount" name="operationReport.allAmount" class="moneyStyle" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" placeholder=""  value="${operationreportCommonForm.operationReport.allAmount}" />&nbsp;&nbsp;元
				</div>

			</div>
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					累计赚取收益：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="allProfit" name="operationReport.allProfit" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.operationReport.allProfit}" />&nbsp;&nbsp;元
				</div>
			</div>
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					平台注册人数：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="registNum" name="operationReport.registNum" class="positiveNumber" placeholder="" datatype="n1-15" errormsg="请输入15位以内的整数" value="${operationreportCommonForm.operationReport.registNum}" />&nbsp;&nbsp;人
				</div>
			</div>
		</div>
	</div>

	<!-- 当月业绩 -->
	<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<div class="form-group">
		<div  class="text">
			<h5><font size="4px" style="margin:0px 0px 0px 0px"><b>当月业绩</b></font></h5>
		</div>
		<div class="col-xs-10">
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					本月成交金额：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="operationAmount" name="operationReport.operationAmount" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" class="form-control moneyStyle" placeholder=""
						   value="${operationreportCommonForm.operationReport.operationAmount}" />&nbsp;&nbsp;元
				</div>
			</div>

			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					去年本月成交金额：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="lastYearMonthAmount" name="monthlyOperationReport.lastYearMonthAmount" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" class="moneyStyle" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.lastYearMonthAmount}" />&nbsp;&nbsp;元
				</div>
			</div>

			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					同比增长：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="amountIncrease" name="monthlyOperationReport.amountIncrease" class="moneyStyle" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.amountIncrease}"  datatype="/^(-)?(?:0|[1-9][0-9]{0,2}|[0-9]{1,3}[\.][0-9]+)$/"errormsg="请输入整数位在3位以内的数"/>&nbsp;&nbsp;%
				</div>
			</div>
		</div>
		<div class="col-xs-10">
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					本月赚取收益：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="operationProfit" name="operationReport.operationProfit" class="moneyStyle" placeholder=""
						   value="${operationreportCommonForm.operationReport.operationProfit}" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数"/>&nbsp;&nbsp;元
				</div>
			</div>
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					去年本月赚取收益：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="lastYearMonthProfit" name="monthlyOperationReport.lastYearMonthProfit" class="moneyStyle" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.lastYearMonthProfit}" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数">&nbsp;&nbsp;元
				</div>
			</div>

			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					同比增长：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="profitIncrease" name="monthlyOperationReport.profitIncrease" class="moneyStyle" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.profitIncrease}"   datatype="/^(-)?(?:0|[1-9][0-9]{0,2}|[0-9]{1,3}[\.][0-9]+)$/"errormsg="请输入整数位在3位以内的数"/>&nbsp;&nbsp;%
				</div>
			</div>
		</div>

		<div class="col-xs-10">
			<div  style="width:33.3%; float:left;">
				<div style="float:left;width: 30%">
					本月成交笔数：
				</div>
				<div style="float:left;width: 70%" >
					<input type="text" id="successDealNum" name="operationReport.successDealNum" class="positiveNumber" placeholder="" value="${operationreportCommonForm.operationReport.successDealNum}" datatype="n1-9" errormsg="请输入9位以内的整数">&nbsp;&nbsp;笔
				</div>
			</div>
			<div  style="width:66.6%; float:left;">
				<div style="float:left;width: 30%">
					本月平均年利率：
				</div>
				<div style="float:left;width: 60%;margin:0px 0px 0px -164px;">
					<input type="text" id="monthAvgProfit" name="monthlyOperationReport.monthAvgProfit" class="moneyStyle" placeholder="" value="${operationreportCommonForm.monthlyOperationReport.monthAvgProfit}"   datatype="/^(?:0|[1-9][0-9]{0,2}|[0-9]{1,3}[\.][0-9]+)$/"errormsg="请输入整数位在3位以内的数"/>&nbsp;&nbsp;%
				</div>
			</div>
		</div>
	</div>

	<!-- 渠道分析 -->
	<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<div class="form-group">
		<div  class="text">
			<h5><font size="4px" style="margin:0px 0px 0px 0px"><b>渠道分析</b></font></h5>
		</div>
		<div class="col-xs-10">
			<div  style="width:38%; float:left;">
				<div style="float:left;width: 32%">
					本月APP成交笔数：
				</div>

				<%--datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/"--%>
				<div style="float:left;width: 65%" >
					<input type="text" name="monthlyOperationReport.monthAppDealNum" class="positiveNumber" placeholder="" value="${operationreportCommonForm.monthlyOperationReport.monthAppDealNum}"
						   datatype="n1-9" errormsg="请输入9位以内的整数"/>&nbsp;&nbsp;笔
				</div>

			</div>
			<div  style="width:62%; float:left;">
				<div style="float:left;width: 15%">
					占比：
				</div>
				<div style="float:left;width: 85%" >
					<input type="text" id="monthAppDealProportion" name="monthlyOperationReport.monthAppDealProportion"  class="moneyStyle" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.monthAppDealProportion}"   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数"/>&nbsp;&nbsp;%
				</div>
			</div>
		</div>
		<div class="col-xs-10">
			<div  style="width:38%; float:left;">
				<div style="float:left;width: 32%">
					本月微信成交笔数：
				</div>

				<%--datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/"--%>
				<div style="float:left;width: 68%" >
					<input type="text" id="monthWechatDealNum" name="monthlyOperationReport.monthWechatDealNum"  class="positiveNumber" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.monthWechatDealNum}"  datatype="n1-9" errormsg="请输入9位以内的整数"/>&nbsp;&nbsp;笔
				</div>

			</div>
			<div  style="width:62%; float:left;">
				<div style="float:left;width: 15%">
					占比：
				</div>
				<div style="float:left;width: 85%" >
					<input type="text" id="monthWechatDealProportion" name="monthlyOperationReport.monthWechatDealProportion" class="moneyStyle" placeholder=""

						   value="${operationreportCommonForm.monthlyOperationReport.monthWechatDealProportion}"   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数"/>&nbsp;&nbsp;%
				</div>
			</div>
		</div>
		<div class="col-xs-10">
			<div  style="width:38%; float:left;">
				<div style="float:left;width: 32%">
					本月PC成交笔数：
				</div>
				<div style="float:left;width: 65%" >
					<input type="text" id="monthPcDealNum" name="monthlyOperationReport.monthPcDealNum" class="positiveNumber" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.monthPcDealNum}" datatype="n1-9" errormsg="请输入9位以内的整数" />&nbsp;&nbsp;笔
				</div>

			</div>
			<div  style="width:62%; float:left;">
				<div style="float:left;width: 15%">
					占比：
				</div>
				<div style="float:left;width: 85%" >
					<input type="text" id="monthPcDealProportion" name="monthlyOperationReport.monthPcDealProportion" class="moneyStyle" placeholder=""
						   value="${operationreportCommonForm.monthlyOperationReport.monthPcDealProportion}"   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/" errormsg="请输入不大于100的数"/>&nbsp;&nbsp;%
				</div>
			</div>
		</div>
	</div>
	<!-- 用户分析 -->
		<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<div class="form-group">

		<div  class="text">
			<h5><font size="4px" style="margin:0px 0px 0px 0px"><b>用户分析</b></font></h5>
			<div  class="text">
				<p><span style="font-family:'应用字体 Bold', '应用字体';"><b style="margin:4px 0px 0px 0px">性别分布:</b></span></p>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						男性出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="manTenderNum" name="userOperationReport.manTenderNum" class="positiveNumber" datatype="n1-9" errormsg="请输入9位以内的整数" placeholder="" value="${operationreportCommonForm.userOperationReport.manTenderNum}" />&nbsp;&nbsp;人
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="manTenderNumProportion" name="userOperationReport.manTenderNumProportion"  class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.manTenderNumProportion}" />&nbsp;&nbsp;%
					</div>
				</div>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						女性出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="womanTenderNum" name="userOperationReport.womanTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数" value="${operationreportCommonForm.userOperationReport.womanTenderNum}" />&nbsp;&nbsp;人
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="womanTenderNumProportion" name="userOperationReport.womanTenderNumProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.womanTenderNumProportion}"/>&nbsp;&nbsp;%
					</div>
				</div>
			</div>

			<div style="margin:0px 1000px 0px 0px"  class="text">
				<p><span style="font-family:'应用字体 Bold', '应用字体';"><b style="margin:4px 0px 0px 0px">年龄分布:</b></span></p>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						18~29岁出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="ageFirstStageTenderNum" name="userOperationReport.ageFirstStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数" value="${operationreportCommonForm.userOperationReport.ageFirstStageTenderNum}"/>&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="ageFirstStageTenderProportion" name="userOperationReport.ageFirstStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.ageFirstStageTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>
			</div>
			<div class="col-xs-10">

				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						30~39岁出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="ageSecondStageTenderNum" name="userOperationReport.ageSecondStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数" value="${operationreportCommonForm.userOperationReport.ageSecondStageTenderNum}" />&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="ageSecondStageTenderProportion" name="userOperationReport.ageSecondStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入整数位在3位以内的数" value="${operationreportCommonForm.userOperationReport.ageSecondStageTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						40~49岁出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="ageThirdStageTenderNum" name="userOperationReport.ageThirdStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数"  value="${operationreportCommonForm.userOperationReport.ageThirdStageTenderNum}"/>&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="ageThirdStageTenderProportion" name="userOperationReport.ageThirdStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.ageThirdStageTenderProportion}"/>&nbsp;&nbsp;%
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						50~59岁出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="ageFourthStageTenderNum" name="userOperationReport.ageFourthStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数"  value="${operationreportCommonForm.userOperationReport.ageFourthStageTenderNum}" />&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="ageFourthStageTenderProportion" name="userOperationReport.ageFourthStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入整数位在3位以内的数" value="${operationreportCommonForm.userOperationReport.ageFourthStageTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						60岁以上出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="ageFirveStageTenderNum" name="userOperationReport.ageFirveStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数"  value="${operationreportCommonForm.userOperationReport.ageFirveStageTenderNum}"/>&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="ageFirveStageTenderProportion" name="userOperationReport.ageFirveStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入整数位在3位以内的数" value="${operationreportCommonForm.userOperationReport.ageFirveStageTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>

			</div>
			<div style="margin:0px 1000px 0px 0px"  class="text">
				<p><span style="font-family:'应用字体 Bold', '应用字体';"><b style="margin:4px 0px 0px 0px">金额分布:</b></span></p>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						1万以下出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="amountFirstStageTenderNum" name="userOperationReport.amountFirstStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数"  value="${operationreportCommonForm.userOperationReport.amountFirstStageTenderNum}"/>&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="amountFirstStageTenderProportion" name="userOperationReport.amountFirstStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.amountFirstStageTenderProportion}"/>&nbsp;&nbsp;%
					</div>
				</div>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						1~5万出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="amountSecondStageTenderNum" name="userOperationReport.amountSecondStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数"  value="${operationreportCommonForm.userOperationReport.amountSecondStageTenderNum}" />&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="amountSecondStageTenderProportion" name="userOperationReport.amountSecondStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.amountSecondStageTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						5~10万出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="amountThirdStageTenderNum" name="userOperationReport.amountThirdStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数"  value="${operationreportCommonForm.userOperationReport.amountThirdStageTenderNum}" />&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="amountThirdStageTenderProportion" name="userOperationReport.amountThirdStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.amountThirdStageTenderProportion}"/>&nbsp;&nbsp;%
					</div>
				</div>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						10~50万出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="amountFourthStageTenderNum" name="userOperationReport.amountFourthStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数" value="${operationreportCommonForm.userOperationReport.amountFourthStageTenderNum}" />&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="amountFourthStageTenderProportion" name="userOperationReport.amountFourthStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/" errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.amountFourthStageTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						50万以上出借人数：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="amountFirveStageTenderNum" name="userOperationReport.amountFirveStageTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入9位以内的整数"  value="${operationreportCommonForm.userOperationReport.amountFirveStageTenderNum}" />&nbsp;&nbsp;人
					</div>
				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="amountFirveStageTenderProportion" name="userOperationReport.amountFirveStageTenderProportion" class="moneyStyle" placeholder=""   datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" value="${operationreportCommonForm.userOperationReport.amountFirveStageTenderProportion}"/>&nbsp;&nbsp;%
					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- 当月之最 -->
	<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<div class="form-group">
		<div  class="text">
			<h5><font size="4px" style="margin:0px 0px 0px 0px"><b>当月之最</b></font></h5>
			<div  class="text">
				<p><span style="font-family:'应用字体 Bold', '应用字体';"><b style="margin:4px 0px 0px 0px">十大出借人:</b></span></p>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第1名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="firstTenderUsername" name="tenthOperationReport.firstTenderUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.firstTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第1名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="firstTenderAmount" name="tenthOperationReport.firstTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.firstTenderAmount}"/>&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">

				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第2名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="secondTenderUsername" name="tenthOperationReport.secondTenderUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.secondTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第2名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="secondTenderAmount" name="tenthOperationReport.secondTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.secondTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">

				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第3名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="thirdTenderUsername" name="tenthOperationReport.thirdTenderUsername" datatype="*" nullmsg="请输入姓名" value="${operationreportCommonForm.tenthOperationReport.thirdTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第3名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="thirdTenderAmount" name="tenthOperationReport.thirdTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.thirdTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第4名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="fourthTenderUsername" name="tenthOperationReport.fourthTenderUsername" datatype="*" nullmsg="请输入姓名" value="${operationreportCommonForm.tenthOperationReport.fourthTenderUsername}"/>
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第4名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="fourthTenderAmount" name="tenthOperationReport.fourthTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.fourthTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第5名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="fifthTenderUsername" name="tenthOperationReport.fifthTenderUsername" datatype="*" nullmsg="请输入姓名" value="${operationreportCommonForm.tenthOperationReport.fifthTenderUsername}"/>
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第5名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="fifthTenderAmount" name="tenthOperationReport.fifthTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.fifthTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第6名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="sixthTenderUsername" name="tenthOperationReport.sixthTenderUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.sixthTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第6名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="sixthTenderAmount" name="tenthOperationReport.sixthTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.sixthTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第7名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="seventhTenderUsername" name="tenthOperationReport.seventhTenderUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.seventhTenderUsername}"/>
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第7名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="seventhTenderAmount" name="tenthOperationReport.seventhTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.seventhTenderAmount}"/>&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第8名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="eighthTenderUsername" name="tenthOperationReport.eighthTenderUsername" datatype="*" nullmsg="请输入姓名" value="${operationreportCommonForm.tenthOperationReport.eighthTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第8名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="eighthTenderAmount" name="tenthOperationReport.eighthTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.eighthTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第9名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="ninthTenderUsername" name="tenthOperationReport.ninthTenderUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.ninthTenderUsername}"/>
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第9名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="ninthTenderAmount" name="tenthOperationReport.ninthTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.ninthTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						第10名用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="tenthTenderUsername" name="tenthOperationReport.tenthTenderUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.tenthTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						第10名出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="tenthTenderAmount" name="tenthOperationReport.tenthTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.tenthTenderAmount}"/>&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						10大出借人金额之和：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="tenTenderAmount" name="tenthOperationReport.tenTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.tenTenderAmount}" />&nbsp;&nbsp;元
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="tenTenderProportion" name="tenthOperationReport.tenTenderProportion" class="moneyStyle" placeholder=""    datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/" errormsg="请输入整数位在3位以内的数" value="${operationreportCommonForm.tenthOperationReport.tenTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						其他出借人金额之和：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="otherTenderAmount" name="tenthOperationReport.otherTenderAmount" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.otherTenderAmount}" />&nbsp;&nbsp;元
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						占比：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="otherTenderProportion" name="tenthOperationReport.otherTenderProportion" class="moneyStyle"    datatype="/^(?:0|[1-9][0-9]{0,1}|[0-9]{1,2}[\.][0-9]+|100)$/"errormsg="请输入不大于100的数" placeholder="" value="${operationreportCommonForm.tenthOperationReport.otherTenderProportion}" />&nbsp;&nbsp;%
					</div>
				</div>

			</div>
			<div style="margin:0px 1000px 0px 0px"  class="text">
				<p><span style="font-family:'应用字体 Bold', '应用字体';"><b style="margin:4px 0px 0px 0px">最多金:</b></span></p>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="mostTenderUsername" name="tenthOperationReport.mostTenderUsername"  datatype="*" nullmsg="请输入姓名"  placeholder="" value="${operationreportCommonForm.tenthOperationReport.mostTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						出借金额：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="mostTenderAmount" name="tenthOperationReport.mostTenderAmount" class="moneyStyle" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/"  errormsg="请输入整数位在13位以内的数" placeholder="" value="${operationreportCommonForm.tenthOperationReport.mostTenderAmount}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						年龄：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="mostTenderUserAge" name="tenthOperationReport.mostTenderUserAge" class="ageNumber" placeholder="" datatype="/^(?:0|[1-9][0-9]?|100)$/"  errormsg="年龄应在100以内" value="${operationreportCommonForm.tenthOperationReport.mostTenderUserAge}" />&nbsp;&nbsp;岁
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						地区：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="mostTenderUserArea" name="tenthOperationReport.mostTenderUserArea" datatype="*" nullmsg="请输入地区"  value="${operationreportCommonForm.tenthOperationReport.mostTenderUserArea}" />
					</div>
				</div>
			</div>
			<div style="padding:80px 5px 5px 0px;margin:0px 1000px 0px 0px"  class="text">
				<p><span style="font-family:'应用字体 Bold', '应用字体';"><b style="margin:4px 0px 0px 0px">大赢家:</b></span></p>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="bigMinnerUsername" name="tenthOperationReport.bigMinnerUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.bigMinnerUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						预期收益：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="bigMinnerProfit" name="tenthOperationReport.bigMinnerProfit" class="moneyStyle" placeholder="" datatype="/^(?:0|[1-9][0-9]{0,12}|[0-9]{1,13}[\.][0-9]+)$/" errormsg="请输入整数位在13位以内的数" value="${operationreportCommonForm.tenthOperationReport.bigMinnerProfit}" />&nbsp;&nbsp;元
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						年龄：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="bigMinnerUserAge" name="tenthOperationReport.bigMinnerUserAge" class="ageNumber" placeholder="" datatype="/^(?:0|[1-9][0-9]?|100)$/"  errormsg="年龄应在100以内"  value="${operationreportCommonForm.tenthOperationReport.bigMinnerUserAge}" />&nbsp;&nbsp;岁
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						地区：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="bigMinnerUserArea" name="tenthOperationReport.bigMinnerUserArea" datatype="*" nullmsg="请输入地区"  value="${operationreportCommonForm.tenthOperationReport.bigMinnerUserArea}" />
					</div>
				</div>
			</div>
			<div style="padding:100px 5px 5px 0px;"  class="text">
				<p><span style="font-family:'应用字体 Bold', '应用字体';"><b style="margin:4px 0px 0px 0px">超活跃:</b></span></p>
			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						用户名：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="activeTenderUsername" name="tenthOperationReport.activeTenderUsername" datatype="*" nullmsg="请输入姓名"  value="${operationreportCommonForm.tenthOperationReport.activeTenderUsername}" />
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						出借次数：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="activeTenderNum" name="tenthOperationReport.activeTenderNum" class="positiveNumber" placeholder="" datatype="n1-9" errormsg="请输入10位以内的整数"  value="${operationreportCommonForm.tenthOperationReport.activeTenderNum}" />&nbsp;&nbsp;次
					</div>
				</div>

			</div>
			<div class="col-xs-10">
				<div  style="width:33.3%; float:left;">
					<div style="float:left;width: 30%">
						年龄：
					</div>
					<div style="float:left;width: 70%" >
						<input type="text" id="activeTenderUerAge" name="tenthOperationReport.activeTenderUserAge" class="ageNumber" placeholder="" datatype="/^(?:0|[1-9][0-9]?|100)$/" errormsg="年龄应在100以内"  value="${operationreportCommonForm.tenthOperationReport.activeTenderUserAge}" />&nbsp;&nbsp;岁
					</div>

				</div>
				<div  style="width:66.6%; float:left;">
					<div style="float:left;width: 15%">
						地区：
					</div>
					<div style="float:left;width: 85%" >
						<input type="text" id="activeTenderUerArea" name="tenthOperationReport.activeTenderUserArea"  datatype="*" nullmsg="请输入地区"  value="${operationreportCommonForm.tenthOperationReport.activeTenderUserArea}"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<c:if test="${operationreportCommonForm != null&&operationreportCommonForm.operationReport.id == null}">
		<!-- 精彩活动 -->
		<div id = "insertDiv" class="wonderfulActivity" style="height:200px;overflow-y:auto;">
			<div style="width:65%; float:left;"  class="text">
				<h5><font size="4px" style="margin:0px -2px 0px 0px;width:80%;overflow-y:auto;"><b>精彩活动</b></font></h5>
			</div>
			<div style="float:left;width:35%">
				<p><span ><a href="javascript:void(0)" class="addActive" value="添加活动">添加活动</a> </span></p>
			</div>
			<div class="col-xs-10">
				<div style="min-width: 1400px;min-height: 80px;">
					<div style="float: left;margin:30px 0px 10px 0px;">
						<input type="hidden"  name="wonderfulActivities[0].activtyType" value="2"/>
						<input type="hidden" name="wonderfulActivities[0].id" id="wonderfulActivitieId0" value="${wonderfulActivities.id }" />
						<p><span style="font-family:'应用字体 Regular', '应用字体';margin: auto">活动标题：</span></p>
					</div>
					<div style="float: left;margin:30px 0px 0px 20px;">
						<input type="text" maxlength="30" name="wonderfulActivities[0].activtyName" class="wonderfulActivitiesActivtyName" ></input>
					</div>

					<div style="float: left;margin:30px 0px 0px 50px">
						<p><span style="font-family:'应用字体 Regular', '应用字体';">活动时间：</span></p>
					</div>
					<div style="float: left;margin:30px 0px 0px 30px;">
						<input style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="wonderfulActivities[0].startTime"   class="form-control underline datepicker"/>
					</div>
					<div style="float: left;margin:36px 0px 0px 20px">
						<p><span style="font-family:'应用字体 Regular', '应用字体';">至</span></p>
					</div>
					<div style="float: left;margin:30px 0px 0px 30px">
						<input  style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="wonderfulActivities[0].endTime"  class="form-control underline datepicker" />
					</div>

					<!-- 图片 -->
					<div style="float: left;width: 20%;" class="fileinput fileinput-new col-xs-6 picClass" data-provides="fileinput">
						<!-- 活动图片标题 -->
						<div style="float: left;margin:35px 0px 0px 45px;">
							<p><span style="font-family:'应用字体 Regular', '应用字体';">活动图片：</span></p>
						</div>
						<!-- 显示图片 -->
						<div style="float: left;margin:35px 0px 0px 10px;" height="20" width="20" class="fileinput-preview fileinput-exists thumbnail myimg"></div>
						<!-- 图片路径 -->
						<input type="hidden" readonly="readonly" name="wonderfulActivities[0].activtyPictureUrl" class="activtyPictureUrl"  value="" />
						<!-- 按钮管理 -->
						<div style="float: left;margin:-38px 0px 0px 180px;" class="user-edit-image-buttons">
								<span class="btn btn-azure btn-file" >
									<input type="file" name="file" value="上传" accept="image/jpeg,image/png,image/gif,image/x-ms-bmp,image/tiff" class="fileupload">
								</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 足迹 -->
		<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
		<div class="form-group wonderActivity" id = "insertFootActive"  style="height:300px;overflow-y:auto;">
			<div style="width:65%; float:left;" class="text">
				<h5><font size="4px" style="margin: 0px 0px 0px 9px;float:left;overflow-y:auto;"><b>足迹</b></font></h5>
			</div>
			<div style="float:left;width:35%;margin: 0px 0px 0px 1288px;">
				<p><span ><a href="javascript:void(0)" class="addFootprint" value="添加足迹">添加足迹</a></span></p>
			</div>
			<div class="col-xs-10">
				<div style="min-width: 1800px;min-height: 80px;width:80%" >
					<input type="hidden" name="footprints[0].activtyType" value="3"/>
					<input type="hidden" name="footprints[0].id" id="footprintId0" value="" />
					<div style="float:left;margin: 0px -30px -20px 5px;">
						<p><span style="font-family:'应用字体 Regular', '应用字体';margin: auto">标题：</span></p>
					</div>
					<div style="float:left;margin:-3px 0px 0px 75px">
						<input type="text"  name="footprints[0].activtyName" />
					</div>
					<div style="float:left;margin:1px 0px 0px 49px">
						<p><span style="font-family:'应用字体 Regular', '应用字体';">活动时间：</span></p>
					</div>
					<div style="width:15%;float:left;margin:-5px 0px -10px 25px;" class="input-group input-daterange ">
						<input style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="footprints[0].time"  class="form-control underline datepicker"   />
					</div>
				</div>
			</div>
		</div>
	</c:if>


	<c:if test="${operationreportCommonForm != null&&operationreportCommonForm.operationReport.id != null}">
		<!-- 精彩活动 -->
		<div id = "insertDiv" class="form-group"  style="height:200px;overflow-y:auto;">
			<div style="width:65%; float:left;"  class="text">
				<h5><font size="4px" style="margin:0px -2px 0px 0px;width:80%;overflow-y:auto;"><b>精彩活动</b></font></h5>
			</div>
			<div style="float:left;width:35%">
				<p><span ><a href="javascript:void(0)" class="addActive" value="添加活动">添加活动</a> </span></p>
			</div>
				<%--<input type="hidden"  name="size"/>--%>
			<c:choose>
			<c:when test="${fn:length(operationreportCommonForm.wonderfulActivities) ==0}">
				<div class="col-xs-10 wonderfulActivity">
					<div style="min-width: 1400px;min-height: 80px;">
						<div style="float: left;margin:30px 0px 10px 0px;">
							<input type="hidden"  name="wonderfulActivities[0].activtyType" value="2"/>
							<input type="hidden" name="wonderfulActivities[0].id" id="wonderfulActivitieId0" value="${wonderfulActivities.id }" />
							<p><span style="font-family:'应用字体 Regular', '应用字体';margin: auto">活动标题：</span></p>
						</div>
						<div style="float: left;margin:30px 0px 0px 20px;">
							<input type="text"  name="wonderfulActivities[0].activtyName" class="wonderfulActivitiesActivtyName" ></input>
						</div>

						<div style="float: left;margin:30px 0px 0px 50px">
							<p><span style="font-family:'应用字体 Regular', '应用字体';">活动时间：</span></p>
						</div>
						<div style="float: left;margin:30px 0px 0px 30px;">
							<input style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="wonderfulActivities[0].startTime"   class="form-control underline datepicker"/>
						</div>
						<div style="float: left;margin:36px 0px 0px 20px">
							<p><span style="font-family:'应用字体 Regular', '应用字体';">至</span></p>
						</div>
						<div style="float: left;margin:30px 0px 0px 30px">
							<input  style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="wonderfulActivities[0].endTime"  class="form-control underline datepicker" />
						</div>

						<!-- 图片 -->
						<div style="float: left;width: 20%;" class="fileinput fileinput-new col-xs-6 picClass" data-provides="fileinput">
							<!-- 活动图片标题 -->
							<div style="float: left;margin:35px 0px 0px 45px;">
								<p><span style="font-family:'应用字体 Regular', '应用字体';">活动图片：</span></p>
							</div>
							<!-- 显示图片 -->
							<div style="float: left;margin:35px 0px 0px 10px;" height="20" width="20" class="fileinput-preview fileinput-exists thumbnail myimg"></div>
							<!-- 图片路径 -->
							<input type="hidden" readonly="readonly" name="wonderfulActivities[0].activtyPictureUrl" class="activtyPictureUrl"  value="" />
							<!-- 按钮管理 -->
							<div style="float: left;margin:-38px 0px 0px 180px;" class="user-edit-image-buttons">
								<span class="btn btn-azure btn-file" >
									<input type="file" name="file" value="上传"class="fileupload" accept="image/jpeg,image/png,image/gif,image/x-ms-bmp,image/tiff">
								</span>
							</div>
						</div>
					</div>
				</div>
			</c:when >
			<c:otherwise>
			<c:forEach items="${operationreportCommonForm.wonderfulActivities}" var="wonderfulActivitie" begin="0" step="1" varStatus="status">
				<div class="col-xs-10">
					<div style="width: 1800px;height: 50px;" class="deleteWonderfulActivity wonderfulActivity">
						<input type="hidden"  id="wonderfulActivities${status.index}" name="wonderfulActivities[${status.index}].activtyType" value="2"/>
						<input type="hidden" name="operationReportActivity.id" id="wonderfulActivitieId${status.index}" value="${operationreportCommonForm.operationReportActivity.id }" />
						<input type="hidden"  name="wonderfulActivities[${status.index}].id" value="${wonderfulActivitie.id }"/>
						<div style="float: left;margin:30px 0px 10px 0px;">
							<p><span style="font-family:'应用字体 Regular', '应用字体';margin: auto">活动标题：</span></p>
						</div>
						<div style="float: left;margin:30px 0px 0px 20px;">
							<input type="text" class="activtyPictureUrlValidform" name="wonderfulActivities[${status.index}].activtyName" value="${wonderfulActivitie.activtyName }"  ></input>
						</div>

						<div style="float: left;margin:30px 0px 0px 50px">
							<p><span style="font-family:'应用字体 Regular', '应用字体';">活动时间：</span></p>
						</div>
						<div style="float: left;margin:30px 0px 0px 30px;">
							<input style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="wonderfulActivities[${status.index}].startTime"  class="form-control underline datepicker" value="${wonderfulActivitie.startTime }"  />
						</div>
						<div style="float: left;margin:36px 0px 0px 20px">
							<p><span style="font-family:'应用字体 Regular', '应用字体';">至</span></p>
						</div>
						<div style="float: left;margin:30px 0px 0px 30px">
							<input  style="width:180px;border:1px solid #c8c7cc" type="text" name="wonderfulActivities[${status.index}].endTime"  class="form-control underline datepicker"value="${wonderfulActivitie.endTime}" />
						</div>

						<!-- 图片 -->
						<div style="float: left;width: 20%;" class="fileinput fileinput-new col-xs-6 picClass" data-provides="fileinput">
							<!-- 活动图片标题 -->
							<div style="float: left;margin:35px 0px 0px 45px;">
								<p><span style="font-family:'应用字体 Regular', '应用字体';">活动图片：</span></p>
							</div>
							<!-- 显示图片 -->
							<div style="float: left;margin:15px 0px 0px 10px;"  height="60" width="20" class="fileinput-preview fileinput-exists thumbnail myimg myimgr">
								<img src="${imgSrc}${wonderfulActivitie.activtyPictureUrl}" height="60" width="60"/>
							</div>
							<!-- 图片路径 -->
							<input type="hidden" readonly="readonly"  name="wonderfulActivities[${status.index}].activtyPictureUrl" class="activtyPictureUrl"  value="${wonderfulActivitie.activtyPictureUrl}" />
							<!-- 	</div> -->
							<!-- 按钮管理 -->
							<div style="float: left;margin:-40px 0px 0px 240px;width: 10%;" class="user-edit-image-buttons">
								<span class="btn btn-azure btn-file" ><span class="fileinput-new"><i class="fa fa-picture"></i></span><span class="fileinput-exists"><i class="fa fa-picture"></i></span><input type="file" name="file" class="fileupload" accept="image/jpeg,image/png,image/gif,image/x-ms-bmp,image/tiff" onclick="fileUpload(${status.index})"></span>
								</a>
							</div>
						</div>
						<c:if test="${status.index !=0}">
							<div style="float: right;margin:50px 365px 0px 100px" >
								<input type="button" onclick="deleteWonderfulActivity_Button(${status.index});" value="删除本条"/>
							</div>
						</c:if>
					</div>
				</div>
			</c:forEach>
			</c:otherwise>
			</c:choose>
		</div>
		<!-- 足迹 -->
		<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
		<div class="form-group" id = "insertFootActive"  style="height:150px;width:2000px;overflow-y:auto;float: left;margin: 0px 0px 0px 0px;">
			<div style="width:65%; float:left;" class="text">
				<h5><font size="4px" style="margin: 0px 630px 0px 0px;float:left;"><b>足迹</b></font></h5>
			</div>
			<div style="float:left;width:35%">
				<p><span ><a href="javascript:void(0)" class="addFootprint" value="添加足迹">添加足迹</a></span></p>
			</div>
			<c:choose>
			<c:when test="${fn:length(operationreportCommonForm.footprints) ==0}">
				<div class="col-xs-10 wonderActivity">
					<div style="min-width: 1800px;min-height: 80px;width:80%" >
						<input type="hidden" name="footprints[0].activtyType" value="3"/>
						<input type="hidden" name="footprints[0].id" id="footprintId0" value="" />
						<div style="float:left;margin: 0px -30px -20px 5px;">
							<p><span style="font-family:'应用字体 Regular', '应用字体';margin: auto">标题：</span></p>
						</div>
						<div style="float:left;margin:-3px 0px 0px 75px">
							<input type="text"  name="footprints[0].activtyName" />
						</div>
						<div style="float:left;margin:1px 0px 0px 49px">
							<p><span style="font-family:'应用字体 Regular', '应用字体';">活动时间：</span></p>
						</div>
						<div style="width:15%;float:left;margin:-5px 0px -10px 25px;" class="input-group input-daterange ">
							<input style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="footprints[0].time"  class="form-control underline datepicker"   />
						</div>
					</div>
				</div>
			</c:when >
			<c:otherwise>
			<c:forEach items="${operationreportCommonForm.footprints}" var="footprint" begin="0" step="1" varStatus="status">


				<div class="col-xs-10">
					<div style="width: 1800px;height: 50px;margin: 38px 0px 0px -35px;" class="col-xs-10 wonderActivity">
						<input type="hidden"  id="wonderActivities${status.index}" name="footprints[${status.index}].activtyType" value="3"/>
						<input type="hidden"  name="footprints[${status.index}].id" value="${footprint.id }"/>
						<div style="float:left;margin: 0px -30px -20px 29px;">
							<p><span style="font-family:'应用字体 Regular', '应用字体';margin: auto">标题：</span></p>
						</div>

						<div style="float:left;margin:-3px 0px 0px 75px">
							<input type="text"  name="footprints[${status.index}].activtyName" value="${footprint.activtyName}" />
						</div>
						<div style="float:left;margin:1px 0px 0px 49px">
							<p><span style="font-family:'应用字体 Regular', '应用字体';">活动时间：</span></p>
						</div>
						<div style="width:15%;float:left;margin:-5px 0px -10px 25px;" class="input-group input-daterange ">
							<input style="width:180px;border:1px solid #c8c7cc" type="text" readonly="readonly" name="footprints[${status.index}].time"  class="form-control underline datepicker"  value="${footprint.time}" />
						</div>
						<c:if test="${status.index !=0}">
							<div style="float: right;margin:10px 400px 0px 250px;" >
								<input type="button" onclick="deleteWonderActivity_Button(${status.index});" value="删除本条"/>
							</div>
						</c:if>
					</div>
				</div>
			</c:forEach>
			</c:otherwise>
			</c:choose>
		</div>
	</c:if>
	<div style="float:left;margin: 0px 0px 0px 0px">
		<span style="color:red;" class="monyear_error"></span>
		<!-- Unnamed (形状) -->
		<div id="publishSubmitControl" style="display:inline-block;margin:90px 0px 0px 500px" id="u881" class="ax_形状">
			<div id="u882" class="text">
				<p> <input type="button" id="publishSubmit"  style="font-family:'应用字体 Regular', '应用字体';"  value="保存并发布"/></p>
			</div>
		</div>
		<!-- Unnamed (形状) -->
		<div style="display:inline-block;margin:8px 0px 0px 60px" id="u883" class="ax_形状">
			<div id="u884" class="text">
				<p><input type="button" id="addSubmit" style="font-family:'应用字体 Regular', '应用字体';"  value="保存"/></p>
			</div>
		</div>
		<%--<input type="button" id="addPreview" style="font-family:'应用字体 Regular', '应用字体';"  value="预览"/>--%>
		<!-- Unnamed (形状) -->
		<div style="display:inline-block;margin:8px 0px 0px 60px" id="u885" class="ax_形状">
			<div id="u886" class="text" >
				<%--<p><a href="#" id="addPreview" style="font-family:'应用字体 Regular', '应用字体';"  target="_blank">预览</a></p>--%>
				<p><input type="button" id="addPreview" style="font-family:'应用字体 Regular', '应用字体';"  value="预览"/></p>
			</div>
		</div>

		<!-- Unnamed (形状) -->
		<div style="display:inline-block;margin:8px 0px 0px 50px" id="u887" class="ax_形状">
			<div id="u888" class="text">
				<p><input type="button" style="font-family:'应用字体 Regular', '应用字体';" value="取消" onclick="javascript:window.history.back(-1)"/></p>
			</div>
		</div>
	</div>
</form>
<%--</div >--%>
