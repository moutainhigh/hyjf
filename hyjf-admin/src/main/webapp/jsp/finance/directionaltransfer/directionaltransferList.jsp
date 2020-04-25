<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/base/pageBase.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="hyjf" uri="/hyjf-tags"%>

<%-- 画面功能菜单设置开关 --%>
<c:set var="hasSettings" value="true" scope="request"></c:set>
<c:set var="hasMenu" value="true" scope="request"></c:set>
<c:set var="searchAction" value="#" scope="request"></c:set>


<shiro:hasPermission name="directionaltransfer:VIEW">
<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
	<%-- 画面的标题 --%>
	<tiles:putAttribute name="pageTitle" value="定向转账" />

	<%-- 画面的CSS (ignore) --%>
	<tiles:putAttribute name="pageCss" type="string">
		<link href="${themeRoot}/vendor/plug-in/jstree/themes/default/style.min.css" rel="stylesheet" media="screen">
	</tiles:putAttribute>

	<%-- 画面主面板的标题块 --%>
	<tiles:putAttribute name="pageFunCaption" type="string">
		<h1 class="mainTitle">定向转账</h1>
		<span class="mainDescription">定向转账</span>
	</tiles:putAttribute>

	<%-- 画面主面板 --%>
	<tiles:putAttribute name="mainContentinner" type="string">
		<div class="container-fluid container-fullw bg-white">
			<div class="row">
				<div class="col-md-12">
					<div class="search-classic">
						<%-- 功能栏 --%>
						<div class="well">
							<c:set var="jspPrevDsb" value="${directionaltransferForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
							<c:set var="jspNextDsb" value="${directionaltransferForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
							<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}" data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
							<a class="btn btn-o btn-primary btn-sm margin-right-15 hidden-xs fn-Next${jspNextDsb}" data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
							<a class="btn btn-o btn-primary btn-sm fn-Refresh" data-toggle="tooltip" data-placement="bottom" data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i> </a>
							<shiro:hasPermission name="directionaltransfer:EXPORT">
								<a class="btn btn-o btn-primary btn-sm fn-Export" data-toggle="tooltip" data-placement="bottom" data-original-title="导出Excel">导出Excel<i class="fa fa-Export"></i></a>
							</shiro:hasPermission>
							<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel" data-toggle="tooltip" data-placement="bottom" data-original-title="检索条件" data-toggle-class="active" data-toggle-target="#searchable-panel"><i class="fa fa-search margin-right-10"></i> <i class="fa fa-chevron-left"></i></a>
						</div>
						<br />
						<%-- 角色列表一览 --%>
						<table id="equiList" class="table table-striped table-bordered table-hover">
							<colgroup>
								<col style="width: 55px;" />
							</colgroup>
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">转出账户</th>
									<th class="center">转入账户</th>
									<th class="center">转账订单号</th>
									<th class="center">转账金额</th>
									<th class="center">转账状态</th>
									<th class="center">转账时间</th>
									<th class="center">说明</th>
								</tr>
							</thead>
							<tbody id="roleTbody">
								<c:choose>
									<c:when test="${empty directionaltransferForm.accountDirectionalTransferList}">
										<tr>
											<td colspan="12">暂时没有数据记录</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${directionaltransferForm.accountDirectionalTransferList }" var="record" begin="0" step="1" varStatus="status">
											<tr>
												<td class="center"><c:out value="${status.index+((directionaltransferForm.paginatorPage - 1) * directionaltransferForm.paginator.limit) + 1 }"></c:out></td>
												<td class="center"><c:out value="${record.turnOutUsername }"></c:out></td>
												<td class="center"><c:out value="${record.shiftToUsername }"></c:out></td>
												<td class="center"><c:out value="${record.orderId }"></c:out></td>
												<td class="center"><fmt:formatNumber value="${record.transferAccountsMoney}" type="number" pattern="#,##0.00#" /></td>
												<td class="center">
													<c:if test="${record.transferAccountsState == 0}"> 转账中 </c:if>
													<c:if test="${record.transferAccountsState == 1}"> 成功 </c:if>
													<c:if test="${record.transferAccountsState == 2}"> 失败 </c:if>
												</td>
												<td class="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${record.transferAccountsTime}" type="both"/></td>
												<td class="center"><c:out value="${record.remark }"></c:out></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<%-- 分页栏 --%>
						<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="getDirectionalTransferList" paginator="${directionaltransferForm.paginator}"></hyjf:paginator>
						<br /> <br />
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>

	<%-- 检索面板 (ignore) --%>
	<tiles:putAttribute name="searchPanels" type="string">
		<input type="hidden" name="paginatorPage" id="paginator-page" value="${directionaltransferForm.paginatorPage}" />
		<!-- 查询条件 -->
		<div class="form-group">
			<label>转出账户</label>
			<input type="text" name="turnOutUsername" id="turnOutUsername" class="form-control input-sm underline" value="${directionaltransferForm.turnOutUsername}" />
		</div>
		<div class="form-group">
			<label>转入账户</label>
			<input type="text" name="shiftToUsername" id="shiftToUsername" class="form-control input-sm underline" value="${directionaltransferForm.shiftToUsername}" />
		</div>
		<div class="form-group">
			<label>转账状态</label>
			<select name="statusSearch" class="form-control underline form-select2">
				<option value="">全部</option>
				<option value="0" <c:if test="${'0' eq directionaltransferForm.statusSearch}">selected="selected"</c:if>>
					<c:out value="转账中"></c:out>
				</option>
				<option value="1" <c:if test="${'1' eq directionaltransferForm.statusSearch}">selected="selected"</c:if>>
					<c:out value="成功"></c:out>
				</option>
				<option value="2" <c:if test="${'2' eq directionaltransferForm.statusSearch}">selected="selected"</c:if>>
					<c:out value="失败"></c:out>
				</option>
			</select>
		</div>
		<div class="form-group">
			<label>订单号</label>
			<input type="text" name="orderId" id="orderId" class="form-control input-sm underline" value="${directionaltransferForm.orderId}" />
		</div>
		<div class="form-group">
			<label>转账时间</label>
			<div class="input-group input-daterange datepicker">
				<span class="input-icon"><input type="text" name="startDate" id="start-date-time" class="form-control underline" value="${directionaltransferForm.startDate}" /> <i class="ti-calendar"></i></span>
				<span class="input-group-addon no-border bg-light-orange">~</span><input type="text" name="endDate" id="end-date-time" class="form-control underline" value="${directionaltransferForm.endDate}" />
			</div>
		</div>
	</tiles:putAttribute>

	<%-- Javascripts required for this page only (ignore) --%>
	<tiles:putAttribute name="pageJavaScript" type="string">
		<script type="text/javascript" src="${themeRoot}/vendor/plug-in/jstree/jstree.min.js"></script>
		<script type='text/javascript' src="${webRoot}/jsp/finance/directionaltransfer/directionaltransferList.js"></script>
	</tiles:putAttribute>

</tiles:insertTemplate>
</shiro:hasPermission>
