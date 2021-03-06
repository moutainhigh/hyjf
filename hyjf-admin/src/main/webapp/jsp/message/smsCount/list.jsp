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

<%-- 画面功能菜单设置开关 --%>
<c:set var="hasSettings" value="true" scope="request"></c:set>
<c:set var="hasMenu" value="true" scope="request"></c:set>
<c:set var="searchAction" value="#" scope="request"></c:set>
<%-- 画面功能路径 (ignore) --%>
<c:set value="${fn:split('汇盈金服,消息中心,发送记录', ',')}" var="functionPaths"
	scope="request"></c:set>
<shiro:hasPermission name="smsCountList:VIEW">
<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
	<%-- 画面的标题 --%>
	<tiles:putAttribute name="pageTitle" value="发送记录" />
	<%-- 画面的CSS (ignore) --%>
	<tiles:putAttribute name="pageCss" type="string">
		<link href="${themeRoot}/vendor/plug-in/select2/select2.min.css"
			rel="stylesheet" media="screen">
		<link
			href="${themeRoot}/vendor/plug-in/bootstrap-datepicker/bootstrap-datepicker3.standalone.min.css"
			rel="stylesheet" media="screen">
		<link href="${themeRoot}/vendor/plug-in/sweetalert/sweet-alert.css"
			rel="stylesheet" media="screen">
		<link href="${themeRoot}/vendor/plug-in/sweetalert/ie9.css"
			rel="stylesheet" media="screen">
		<link href="${themeRoot}/vendor/plug-in/toastr/toastr.min.css"
			rel="stylesheet" media="screen">
		<link href="${themeRoot}/vendor/plug-in/colorbox/colorbox.css"
			rel="stylesheet" media="screen">
		<style>
.table-striped .checkbox {
	width: 20px;
	margin-right: 0 !important;
	overflow: hidden
}
</style>
	</tiles:putAttribute>
	<%-- 画面主面板的标题块 --%>
	<tiles:putAttribute name="pageFunCaption" type="string">
		<h1 class="mainTitle">消息记录</h1>
		<span class="mainDescription">消息记录说明。</span>
	</tiles:putAttribute>
	<%-- 画面主面板 --%>
	<tiles:putAttribute name="mainContentinner" type="string">
		<div class="container-fluid container-fullw bg-white">
			<div class="row">
				<div class="col-md-12">
					<div class="search-classic">
						<%-- 功能栏 --%>
						<div class="well">
							<c:set var="jspPrevDsb"
								value="${logForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
							<c:set var="jspNextDsb"
								value="${logForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
							<a
								class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
								data-toggle="tooltip" data-placement="bottom"
								data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
							<a
								class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
								data-toggle="tooltip" data-placement="bottom"
								data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
							<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Refresh"
								data-toggle="tooltip" data-placement="bottom"
								data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i></a>
							<shiro:hasPermission name="smsCountList:EXPORT">
                                <a class="btn btn-o btn-primary btn-sm fn-Export"
                                   data-toggle="tooltip" data-placement="bottom" data-original-title="导出Excel">导出Excel <i class="fa fa-plus"></i></a>
							</shiro:hasPermission>
							<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
								data-toggle="tooltip" data-placement="bottom"
								data-original-title="检索条件" data-toggle-class="active"
								data-toggle-target="#searchable-panel"><i
								class="fa fa-search margin-right-10"></i> <i
								class="fa fa-chevron-left"></i></a>
						</div>
						<br />
						<%-- 角色列表一览 --%>
						<table id="equiList"
							class="table table-striped table-bordered table-hover">
							<colgroup>
								<col class="con1" style="" />
								<col class="con0" style="" />
								<col class="con0" style="" />
								<col class="con1" style="" />
								<col class="con0" style="" />
							</colgroup>
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">分公司</th>
									<th class="center">数量(条)</th>
									<th class="center">费用(元)</th>
									<th class="center">时间</th>
								</tr>
							</thead>
							<tbody id="roleTbody">
								<c:choose>
									<c:when test="${empty listSms}">
										<tr>
											<td colspan="5">暂时没有数据记录</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${listSms}" var="record" begin="0" step="1"
											varStatus="status">
											<tr>
												<td class="center">${(logForm.paginatorPage - 1 ) * logForm.paginator.limit + status.index + 1 }</td>
												<td class="center"><c:out value="${record.departmentName }"></c:out></td>
												<td class="center"><c:out value="${record.smsNumber}"></c:out></td>
												<td class="center"><c:out value="${record.smsMoney}"></c:out></td>
												<td class="center"><c:out value="${record.posttime}"></c:out></td>
											</tr>
										</c:forEach>
										<tr>
											<td class="center">总计</td>
											<td class="center"></td>
											<td class="center">${logForm.smsNumber}</td>
											<td class="center">${logForm.smsNumberMoney}</td>
											<td class="center"></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<%-- 分页栏 --%>
						<hyjf:paginator id="equiPaginator" hidden="paginator-page"
							action="init" paginator="${logForm.paginator}"></hyjf:paginator>
						<br /> <br />
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>

	<%-- 边界面板 (ignore) --%>
	<tiles:putAttribute name="searchPanels" type="string">
		<input type="hidden" name="logid" id="logid" />
		<input type="hidden" name="paginatorPage" id="paginator-page"
			value="${logForm.paginatorPage}" />

		<div class="form-group">
			<label>发送时间</label>
			<div class="input-group input-daterange datepicker">
				<span class="input-icon"> <input type="text"
					name="post_time_begin" id="post_time_begin"
					class="form-control underline" value="${logForm.post_time_begin}" />
					<i class="ti-calendar"></i>
				</span> <span class="input-group-addon no-border bg-light-orange">~</span>
				<input type="text" name="post_time_end" id="post_time_end"
					class="form-control underline" value="${logForm.post_time_end}" />
			</div>
		</div>
		<div class="form-group">
			<label>分公司</label>
			<div class="dropdown-menu no-radius">
				<input type="text" class="form-control input-sm underline margin-bottom-10 " value="" id="combotree_search" placeholder="Search" >
				<input type="hidden" id="combotree_field_hidden"  name="combotreeSrch" value="${logForm.combotreeSrch}">
				<div id="combotree-panel" style="width:270px;height:300px;position:relative;overflow:hidden;">
					<div id="combotree" class="tree-demo" ></div>
				</div>
			</div>

			<span class="input-icon input-icon-right" data-toggle="dropdown" >
						<input id="combotree-field" type="text" class="form-control underline form " readonly="readonly">
						<i class="fa fa-remove fn-ClearDep" style="cursor:pointer;"></i>
					</span>
		</div>
	</tiles:putAttribute>

	<%-- Javascripts required for this page only (ignore) --%>
	<tiles:putAttribute name="pageJavaScript" type="string">
		<script type="text/javascript">
			var webRoot = "${webRoot}";
		</script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery.validform_v5.3.2.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/moment.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/select2/select2.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/select2/i18n/zh-CN.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/sweetalert/sweet-alert.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/toastr/toastr.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/colorbox/jquery.colorbox-min.js"></script>
		<%--<script type='text/javascript'--%>
			<%--src="${webRoot}/jsp/message/timemessagelist/timelist.js"></script>--%>
		<link href="${themeRoot}/vendor/plug-in/colorbox/colorbox.css" rel="stylesheet" media="screen">
		<link href="${themeRoot}/vendor/plug-in/jstree/themes/default/style.min.css" rel="stylesheet" media="screen">
		<script type="text/javascript" src="${themeRoot}/vendor/plug-in/jstree/jstree.min.js"></script>
		<script type='text/javascript' src="${webRoot}/jsp/message/smsCount/list.js"></script>
	</tiles:putAttribute>
</tiles:insertTemplate>
</shiro:hasPermission>
