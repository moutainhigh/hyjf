<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/pageBase.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="hyjf" uri="/hyjf-tags"%>

<%-- 画面功能菜单设置开关 --%>
<c:set var="hasSettings" value="true" scope="request"></c:set>
<c:set var="hasMenu" value="true" scope="request"></c:set>
<c:set var="searchAction" value="" scope="request"></c:set>

<shiro:hasPermission name="repayDetail:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="加息还款明细列表" />

		<%-- 画面主面板的标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle">加息还款明细列表</h1>
		</tiles:putAttribute>

		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
				<div class="row">
					<div class="col-md-12">
						<div class="search-classic">
							<shiro:hasPermission name="repayDetail:SEARCH">
								<%-- 功能栏 --%>
								<div class="well">
									<c:set var="jspPrevDsb" value="${repayInfoForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
									<c:set var="jspNextDsb" value="${repayInfoForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
									   data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
									<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
									   data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
										<%--<shiro:hasPermission name="repayDetail:EXPORT">--%>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Export"
									   data-toggle="tooltip" data-placement="bottom" data-original-title="导出列表">导出列表 <i class="fa fa-plus"></i></a>
										<%--</shiro:hasPermission>--%>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Refresh"
									   data-toggle="tooltip" data-placement="bottom" data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i></a>
									<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
									   data-toggle="tooltip" data-placement="bottom" data-original-title="检索条件"
									   data-toggle-class="active" data-toggle-target="#searchable-panel"><i class="fa fa-search margin-right-10"></i> <i class="fa fa-chevron-left"></i></a>
								</div>
							</shiro:hasPermission>
							<br/>
								<%-- 列表一览 --%>
							<table id="equiList" class="table table-striped table-bordered table-hover">
								<colgroup>
									<col style="width:55px;" />
								</colgroup>
								<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">项目编号</th>
									<th class="center">借款期限</th>
									<th class="center">还款方式</th>
									<th class="center">出借人用户名</th>
									<th class="center">还款期数</th>
									<th class="center">出借利率</th>
									<th class="center">产品加息收益率</th>
									<th class="center">出借本金</th>
									<th class="center">应还加息收益</th>
									<th class="center">应回时间</th>
									<th class="center">实际回款时间</th>
									<th class="center">转账状态</th>
								</tr>
								</thead>
								<tbody id="roleTbody">
								<c:choose>
									<c:when test="${empty repayInfoForm.recordList}">
										<tr><td colspan="12">暂时没有数据记录</td></tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${repayInfoForm.recordList }" var="record" begin="0" step="1" varStatus="status">
											<tr>
												<td class="center">${(repayInfoForm.paginatorPage - 1 ) * repayInfoForm.paginator.limit + status.index + 1 }</td>
												<td class="center"><c:out value="${record.borrowNid }"></c:out></td>
												<td class="center"><c:out value="${record.borrowPeriod }"></c:out>
													<c:choose>
														<c:when test="${record.borrowStyle eq 'endday' }">
															天
														</c:when>
														<c:otherwise>
															个月
														</c:otherwise>
													</c:choose>
												</td>
												<td class="center"><c:out value="${record.repayStyleName }"></c:out></td>
												<td class="center"><c:out value="${record.investUserName }"></c:out></td>
												<td class="center">第<c:out value="${record.repayPeriod }"></c:out>期</td>
												<td class="center"><c:out value="${record.borrowApr }"></c:out>%</td>
												<td class="center"><c:out value="${record.borrowExtraYield }"></c:out>%</td>
												<td class="center">
													<fmt:formatNumber type="number" value="${record.repayCapital }" />
												</td>
												<td class="center">
													<fmt:formatNumber type="number" value="${record.repayInterest }" />
												</td>
												<td class="center">
													<hyjf:date value="${record.repayTime}"></hyjf:date>
												</td>
												<td class="center">
													<hyjf:date value="${record.repayActionTime}"></hyjf:date>
												</td>
												<td class="center">
													<c:if test="${record.repayStatus eq 0}">
														未转账
													</c:if>
													<c:if test="${record.repayStatus eq 1}">
														已转账
													</c:if>
													<c:if test="${record.repayStatus eq 2}">
														转账中
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
									<%-- add by LSY START --%>
								<tr>
									<th class="center">总计</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<%--<td align="center"><fmt:formatNumber value="${sumBorrowRepay.sumRepayCapital }" pattern="#,##0"/></td>--%>
									<td align="center"><fmt:formatNumber value="${sumBorrowRepay.sumLoanInterest }" pattern="#,##0.00#"/></td>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
								</tr>
									<%-- add by LSY END --%>
								</tbody>
							</table>
								<%-- 分页栏 --%>
							<shiro:hasPermission name="repayDetail:SEARCH">
								<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="searchAction" paginator="${repayInfoForm.paginator}"></hyjf:paginator>
							</shiro:hasPermission>
							<br/><br/>
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>

		<%-- 检索面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<shiro:hasPermission name="repayDetail:SEARCH">
				<input type="hidden" name="paginatorPage" id="paginator-page" value="${repayInfoForm.paginatorPage}" />
				<input type="hidden" name="repayPeriodSrch" id="repayPeriodSrch"  value="${repayInfoForm.repayPeriodSrch}" />
				<!-- 检索条件 -->
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" name="borrowNidSrch" id="borrowNidSrch" class="form-control input-sm underline" value="${repayInfoForm.borrowNidSrch}" />
				</div>
				<div class="form-group">
					<label>出借人用户名</label>
					<input type="text" name="userNameSrch" id="userNameSrch" class="form-control input-sm underline" value="${repayInfoForm.userNameSrch}" />
				</div>
				<div class="form-group">
					<label>应回时间</label>
					<div class="input-group input-daterange datepicker">
						<span class="input-icon">
							<input type="text" name="timeStartSrch" id="start-date-time" class="form-control underline" value="${repayInfoForm.timeStartSrch}" />
							<i class="ti-calendar"></i> </span>
						<span class="input-group-addon no-border bg-light-orange">~</span>
						<input type="text" name="timeEndSrch" id="end-date-time" class="form-control underline" value="${repayInfoForm.timeEndSrch}" />
					</div>
				</div>
			</shiro:hasPermission>
		</tiles:putAttribute>

		<%-- 对话框面板 (ignore) --%>
		<tiles:putAttribute name="dialogPanels" type="string">
			<iframe class="colobox-dialog-panel" id="urlDialogPanel" name="dialogIfm" style="border:none;width:100%;height:100%"></iframe>
		</tiles:putAttribute>

		<%-- 画面的CSS (ignore) --%>
		<tiles:putAttribute name="pageCss" type="string">
			<link href="${themeRoot}/vendor/plug-in/colorbox/colorbox.css" rel="stylesheet" media="screen">
		</tiles:putAttribute>

		<%-- JS全局变量定义、插件 (ignore) --%>
		<tiles:putAttribute name="pageGlobalImport" type="string">
			<script type="text/javascript" src="${themeRoot}/vendor/plug-in/colorbox/jquery.colorbox-min.js"></script>
		</tiles:putAttribute>

		<%-- Javascripts required for this page only (ignore) --%>
		<tiles:putAttribute name="pageJavaScript" type="string">
			<script type='text/javascript' src="${webRoot}/jsp/manager/borrow/increaseinterest/repaydetail/increaseinterestrepaydetaillist.js"></script>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</shiro:hasPermission>
