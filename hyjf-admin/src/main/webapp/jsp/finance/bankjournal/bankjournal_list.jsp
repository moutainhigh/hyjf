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


<shiro:hasPermission name="bankjournal:VIEW">
<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
	<%-- 画面的标题 --%>
	<tiles:putAttribute name="pageTitle" value="银行交易明细" />

	<%-- 画面的CSS (ignore) --%>
	<tiles:putAttribute name="pageCss" type="string">
		<link href="${themeRoot}/vendor/plug-in/jstree/themes/default/style.min.css" rel="stylesheet" media="screen">
	</tiles:putAttribute>

	<%-- 画面主面板的标题块 --%>
	<tiles:putAttribute name="pageFunCaption" type="string">
		<h1 class="mainTitle">银行交易明细</h1>
		<span class="mainDescription">银行交易明细。</span>
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
								value="${bankJournalForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
							<c:set var="jspNextDsb"
								value="${bankJournalForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
							<a
								class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
								data-toggle="tooltip" data-placement="bottom"
								data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
							<a
								class="btn btn-o btn-primary btn-sm margin-right-15 hidden-xs fn-Next${jspNextDsb}"
								data-toggle="tooltip" data-placement="bottom"
								data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
							<a class="btn btn-o btn-primary btn-sm fn-Refresh"
								data-toggle="tooltip" data-placement="bottom"
								data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i> </a>
							<shiro:hasPermission name="bankjournal:EXPORT">
								<a class="btn btn-o btn-primary btn-sm fn-Export"
									data-toggle="tooltip" data-placement="bottom"
									data-original-title="导出Excel">
									导出Excel <i class="fa fa-Export"></i></a>
							</shiro:hasPermission>
							<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
									data-toggle="tooltip" data-placement="bottom" data-original-title="检索条件"
									data-toggle-class="active" data-toggle-target="#searchable-panel"><i class="fa fa-search margin-right-10"></i> <i class="fa fa-chevron-left"></i></a>
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
									<th class="center">系统跟踪号</th>
									<th class="center">交易传输时间</th>
									<th class="center">电子账号</th>
									<th class="center">交易金额</th>
									<th class="center">交易金额符号</th>
									<th class="center">订单号</th>
									<th class="center">内部交易流水号</th>
									<th class="center">内部保留域</th>
									<th class="center">冲正撤销标志</th>
									<th class="center">交易类型</th>
								</tr>
							</thead>
							<tbody id="roleTbody">
								<c:choose>
									<c:when test="${empty bankJournalForm.recordList}">
										<tr>
											<td colspan="11">暂时没有数据记录</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${bankJournalForm.recordList }" var="record" begin="0" step="1" varStatus="status">
											<tr>
												<td class="center"><c:out value="${status.index+((bankJournalForm.paginatorPage - 1) * bankJournalForm.paginator.limit) + 1 }"></c:out></td>
												<td class="center"><c:out value="${record.seqno }"></c:out></td>
												<td class="center"><c:out value="${record.cendtString}"></c:out></td>
												<td align="center"><c:out value="${record.cardnbr}"></c:out></td>
												<td align="center"><fmt:formatNumber value="${record.amount}" type="number" pattern="#,##0.00#" /></td>

												<td align="center"><c:out value="${record.crflag}"></c:out></td>
												<td class="center"><c:out value="${record.orderno }"></c:out></td>
												<td class="center"><c:out value="${record.tranno }"></c:out></td>
												<td class="center"><c:out value="${record.reserved }"></c:out></td>
												<td class="center"><c:out value="${record.revind == 1? '已撤销/冲正':'' }"></c:out></td>
												<td class="center"><c:out value="${record.transtype }"></c:out></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<%-- 分页栏 --%>
						<hyjf:paginator id="equiPaginator" hidden="paginator-page"
							action="bankjournal_list"
							paginator="${bankJournalForm.paginator}"></hyjf:paginator>
						<br /> <br />
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>

	<%-- 检索面板 (ignore) --%>
	<tiles:putAttribute name="searchPanels" type="string">
		<input type="hidden" name="userId" id="userId" />
		<input type="hidden" name="paginatorPage" id="paginator-page" value="${bankJournalForm.paginatorPage}" />
		<!-- 查询条件 -->
		<div class="form-group">
			<label>电子账号</label> <input type="text" name="cardnbr" id="cardnbr"
				class="form-control input-sm underline"
				value="${bankJournalForm.cardnbr}" />
		</div>
		<div class="form-group">
			<label>系统跟踪号</label> <input type="text" name="seqno" id="seqno"
									   class="form-control input-sm underline"
									   value="${bankJournalForm.seqno}" />
		</div>
		<div class="form-group">
			<label>交易类型</label> <input type="text" name="transtype" id="transtype"
									   class="form-control input-sm underline"
									   value="${bankJournalForm.transtype}" />
		</div>
		<div class="form-group">
			<label>时间</label>
			<div class="input-group input-daterange datepicker">
					<span class="input-icon">
					<input type="text" name="startDate" id="start-date-time" class="form-control underline" value="${bankJournalForm.startDate}" /> <i class="ti-calendar"></i>
					</span> <span class="input-group-addon no-border bg-light-orange">~</span>
				<input type="text" name="endDate" id="end-date-time" class="form-control underline" value="${bankJournalForm.endDate}" />
			</div>
		</div>
	</tiles:putAttribute>

	<tiles:putAttribute name="pageJavaScript" type="string">
		<script type='text/javascript' src="${webRoot}/jsp/finance/bankjournal/bankjournal_list.js"></script>
	</tiles:putAttribute>

</tiles:insertTemplate>
</shiro:hasPermission>
