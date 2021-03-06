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

<shiro:hasPermission name="productredeem:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="转出记录明细" />
		
		<%-- 画面主面板的标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle">汇天利转出记录</h1>
			<span class="mainDescription">本功能可以查看汇天利转出记录信息</span>
		</tiles:putAttribute>
		
		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
				<div class="row">
					<div class="col-md-12">
						<div class="search-classic">
							<shiro:hasPermission name="productredeem:SEARCH">
								<%-- 功能栏 --%>
								<div class="well">
									<c:set var="jspPrevDsb" value="${productredeemdetailForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
									<c:set var="jspNextDsb" value="${productredeemdetailForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
									<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
									<a class="btn btn-o btn-primary btn-sm fn-Refresh"
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
										<th class="center">转出订单号</th>
										<th class="center">转入订单号</th>
										<th class="center">用户名</th>
										<th class="center">转出本金</th>
										<th class="center">转出收益</th>
										<th class="center">状态</th>
										<th class="center">转出时间</th>
									</tr>
								</thead>
								<tbody id="roleTbody">
									<c:choose>
										<c:when test="${empty productredeemdetailForm.recordList}">
											<tr><td colspan="8">暂时没有数据记录</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${productredeemdetailForm.recordList }" var="record" begin="0" step="1" varStatus="status">
												<tr>
	    											<td class="center">${(productredeemdetailForm.paginatorPage - 1 ) * productredeemdetailForm.paginator.limit + status.index + 1 }</td>
	    											<td class="center"><c:out value="${record.listId }"></c:out></td>
	    											<td class="center"><c:out value="${record.orderId }"></c:out></td>
													<td><c:out value="${record.username }"></c:out></td>
													<td align="right"><fmt:formatNumber value="${record.amount }" pattern="#,##0.00#" /></td>
													<td align="right"><fmt:formatNumber value="${record.interest }" pattern="#,##0.00#" /></td>
													<td class="center">
													<c:choose>
													<c:when test="${record.status==0}">成功</c:when>
												    <c:when test="${record.status==1}">失败</c:when>
												    </c:choose>
													</td>
													<td class="center"><c:out value="${record.redeemTime }"></c:out></td>
												</tr>
											</c:forEach>					
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<%-- 分页栏 --%>
							<shiro:hasPermission name="producterror:VIEW">
								<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="redeemDetail" paginator="${productredeemdetailForm.paginator}"></hyjf:paginator>
							</shiro:hasPermission>
							<br/><br/>
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>
		
		<%-- 检索面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<shiro:hasPermission name="producterror:SEARCH">
					<input type="hidden" name="paginatorPage" id="paginator-page" value="${productredeemdetailForm.paginatorPage}" />
					<!-- 检索条件 -->
					<div class="form-group">
						<label>汇盈订单号</label> 
						<input type="text" name="listIdSrh" class="form-control input-sm underline" maxlength="20" value="${productredeemdetailForm.listIdSrh}"/>
					</div>
					<div class="form-group">
						<label>用户名</label> 
						<input type="text" name="usernameSrh" class="form-control input-sm underline"  maxlength="20" value="${ productredeemdetailForm.usernameSrh}" />
					</div>
					<div class="form-group">
						<label>状态</label>
						<select name="statusSrh" class="form-control underline form-select2">
							<option value=""></option>
							<option value="0" <c:if test="${productredeemdetailForm.statusSrh=='0'}">selected</c:if>>成功</option>
							<option value="1" <c:if test="${productredeemdetailForm.statusSrh=='1'}">selected</c:if>>失败</option>
						</select>
					</div>
					<div class="form-group">
						<label>转出时间</label>
						<div class="input-group input-daterange datepicker">
							<span class="input-icon">
								<input type="text" name="timeStartSrch" id="start-date-time" class="form-control underline" value="${productredeemdetailForm.timeStartSrch}" />
								<i class="ti-calendar"></i> 
							</span>
							<span class="input-group-addon no-border bg-light-orange">~</span>
							<span class="input-icon">
								<input type="text" name="timeEndSrch" id="end-date-time" class="form-control underline" value="${productredeemdetailForm.timeEndSrch}" />
								<i class="ti-calendar"></i> 
							</span>
						</div>
					</div>
			</shiro:hasPermission>
		</tiles:putAttribute>			

		<%-- Javascripts required for this page only (ignore) --%>
		<tiles:putAttribute name="pageJavaScript" type="string">
			<script type='text/javascript' src="${webRoot}/jsp/exception/htlexception/redeemfaildetail.js"></script>
		</tiles:putAttribute>
		
	</tiles:insertTemplate>
</shiro:hasPermission>
