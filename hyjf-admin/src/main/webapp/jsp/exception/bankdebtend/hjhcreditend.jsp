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
<shiro:hasPermission name="hjhcreditend:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp"
		flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="汇转让" />

		<%-- 画面主面板的标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle">转让记录</h1>
			<span class="mainDescription">汇添金计划清算转让债权。</span>
		</tiles:putAttribute>

		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
				<div class="tabbable">
					<ul class="nav nav-tabs" id="myTab">
						<shiro:hasPermission name="bankdebtend:VIEW">
							<li><a href="${webRoot}/exception/bankdebtend/init">(新)结束债权</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="bankdebtend:VIEW">
							<li><a href="${webRoot}/exception/bankdebtend/oldinit">结束债权</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="hjhcreditend:VIEW">
							<li class="active"><a
								href="${webRoot}/exception/hjhcreditendexception/init">汇计划债转结束债权</a></li>
						</shiro:hasPermission>
					</ul>
					<div class="tab-content">

						<div class="tab-pane fade in active">
							<%-- 功能栏 --%>
							<shiro:hasPermission name="hjhcreditend:SEARCH">
								<div class="well">
									<c:set var="jspPrevDsb"
										value="${hjhDebtCreditForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
									<c:set var="jspNextDsb"
										value="${hjhDebtCreditForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
									<a
										class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
										data-toggle="tooltip" data-placement="bottom"
										data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
									<a
										class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
										data-toggle="tooltip" data-placement="bottom"
										data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
									<shiro:hasPermission name="hjhcreditend:EXPORT">
										<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Export"
											data-toggle="tooltip" data-placement="bottom"
											data-original-title="导出列表">导出列表 <i class="fa fa-plus"></i></a>
									</shiro:hasPermission>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Refresh"
										data-toggle="tooltip" data-placement="bottom"
										data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i></a>
									<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
										data-toggle="tooltip" data-placement="bottom"
										data-original-title="检索条件" data-toggle-class="active"
										data-toggle-target="#searchable-panel"><i
										class="fa fa-search margin-right-10"></i> <i
										class="fa fa-chevron-left"></i></a>
								</div>
							</shiro:hasPermission>
							<br />
							<%-- 列表一览 --%>
							<div class="search-classic">
								<table id="equiList"
									class="table table-striped table-bordered table-hover">
									<colgroup>
										<col style="width: 55px;" />
									</colgroup>
									<thead>
										<tr>
											<th class="center">序号</th>
											<th class="center">出让人智投编号</th>
											<th class="center">出让人智投订单号</th>
											<th class="center">清算后智投编号</th>
											<th class="center">出让人</th>
											<th class="center">债转编号</th>
											<th class="center">原项目编号</th>
											<th class="center">原项目出借利率</th>
											<th class="center">还款方式</th>
											<th class="center">债权本金</th>
											<th class="center">债权价值</th>
											<th class="center">预计实际收益率</th>
											<th class="center">已转让本金</th>
											<th class="center">垫付利息</th>
											<th class="center">在途资金</th>
											<th class="center">出让人实际到账金额</th>
											<th class="center">最新清算时间</th>
											<th class="center">转让状态</th>
											<th class="center">还款状态</th>
											<th class="center">项目总期数</th>
											<th class="center">清算时所在期数</th>
											<th class="center">当期应还款时间</th>
											<th class="center">操作</th>
										</tr>
									</thead>
									<tbody id="roleTbody">
										<c:choose>
											<c:when test="${empty recordList}">
												<tr>
													<td colspan="24">暂时没有数据记录</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${recordList }" var="record" begin="0"
													step="1" varStatus="status">
													<tr>
														<td class="center">${(hjhDebtCreditForm.paginatorPage -1 ) * hjhDebtCreditForm.paginator.limit + status.index + 1 }</td>
														<td class="center"><c:out value="${record.planNid }" /></td>
														<td class="center"><c:out
																value="${record.planOrderId }" /></td>
														<td class="center"><c:out
																value="${record.planNidNew }" /></td>
														<td align="right"><c:out value="${record.userName }" /></td>
														<td class="center"><c:out
																value="${record.creditNid }" /></td>
														<td class="center"><c:out
																value="${record.borrowNid }" /></td>
														<td align="right"><c:out value="${record.borrowApr }" />%</td>
														<td align="right"><c:out
																value="${record.repayStyleName }" /></td>
														<td align="right"><c:out
																value="${record.creditCapital }" /></td>
														<td align="right"><c:out
																value="${record.liquidationFairValue }" /></td>
														<td align="right"><c:out value="${record.actualApr }" />%</td>
														<td class="center"><c:out
																value="${record.assignCapital }" /></td>
														<td class="center"><c:out
																value="${record.assignAdvanceInterest }" /></td>
														<%-- <td align="right"><c:out value="${record.creditAccountWait }"/></td> --%>
														<td align="right"><c:out value="0" /></td>
														<td align="right"><c:out
																value="${record.accountReceive }" /></td>
														<td class="center"><c:out
																value="${record.liquidatesTime }" /></td>
														<td class="center"><c:out
																value="${record.creditStatusName }" /></td>
														<td class="center"><c:out
																value="${record.repayStatusName }" /></td>
														<td class="center"><c:out
																value="${record.borrowPeriod }" /></td>
														<td class="center"><c:out
																value="${record.liquidatesPeriod }" /></td>
														<td class="center"><c:out
																value="${record.repayNextTime }" /></td>
														<td class="center">
															<shiro:hasPermission
																name="hjhcreditend:MODIFY">
																<a class="btn btn-transparent btn-xs fn-Modify"
																	data-creditnid="${record.creditNid }" data-toggle="tooltip" tooltip-placement="top" data-original-title="更新">
																	<i class="fa fa-pencil"></i>
																</a>
															</shiro:hasPermission></td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							<%-- 分页栏 --%>
							<shiro:hasPermission name="hjhcreditend:SEARCH">
								<hyjf:paginator id="equiPaginator" hidden="paginator-page"
									action="init" paginator="${hjhDebtCreditForm.paginator}"></hyjf:paginator>
							</shiro:hasPermission>
							<br /> <br />
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>

		<%-- 边界面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<shiro:hasPermission name="hjhcreditend:SEARCH">
				<input type="hidden" name="export" id="export" />
				<input type="hidden" name="paginatorPage" id="paginator-page"
					value="${hjhDebtCreditForm.paginatorPage}" />
				<!-- 检索条件 -->
				<div class="form-group">
					<label>出让人智投编号</label> <input type="text" name="planNid"
						class="form-control input-sm underline"
						value="${hjhDebtCreditForm.planNid}" />
				</div>
				<div class="form-group">
					<label>出让人智投订单号</label> <input type="text" name="planOrderId"
						class="form-control input-sm underline"
						value="${hjhDebtCreditForm.planOrderId}" />
				</div>
				<div class="form-group">
					<label>出让人</label> <input type="text" name="userName"
						class="form-control input-sm underline"
						value="${hjhDebtCreditForm.userName}" />
				</div>
				<div class="form-group">
					<label>债转编号</label> <input type="text" id="creditNid"
						name="creditNid" class="form-control input-sm underline"
						value="${hjhDebtCreditForm.creditNid}" />
				</div>
				<div class="form-group">
					<label>原标的编号</label> <input type="text" name="borrowNid"
						class="form-control input-sm underline"
						value="${hjhDebtCreditForm.borrowNid}" />
				</div>
				<div class="form-group">
					<label>还款方式</label> <select name="repayStyle"
						class="form-control underline form-select2">
						<option value=""></option>
						<c:forEach items="${borrowStyleList }" var="repayStyle" begin="0"
							step="1">
							<option value="${repayStyle.nid }"<c:if test="${repayStyle.nid eq hjhDebtCreditForm.repayStyle}">selected="selected"</c:if>>
								<c:out value="${repayStyle.name }"></c:out></option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>债转时间</label>
					<div class="input-group input-daterange datepicker">
						<span class="input-icon">
							<input type="text" name="liquidatesTimeStart" id="liquidatesTimeStart" class="form-control underline" value="${hjhDebtCreditForm.liquidatesTimeStart}" />
							<i class="ti-calendar"></i> </span>
						<span class="input-group-addon no-border bg-light-orange">~</span>
						<input type="text" name="liquidatesTimeEnd" id="liquidatesTimeEnd" class="form-control underline" value="${hjhDebtCreditForm.liquidatesTimeEnd}" />
					</div>
				</div>
				<div class="form-group">
					<label>转让状态</label>
					<select name="creditStatus" class="form-control underline form-select2">
						<option value=""></option>
						<c:forEach items="${creditStatusList }" var="creditStatus" begin="0" step="1">
							<option value="${creditStatus.nameCd }"
								<c:if test="${creditStatus.nameCd eq hjhDebtCreditForm.creditStatus}">selected="selected"</c:if>>
								<c:out value="${creditStatus.name }"></c:out></option>
						</c:forEach>
					</select>
				</div>
				
				<div class="form-group">
					<label>当期应还款时间</label>
					<div class="input-group input-daterange datepicker">
						<span class="input-icon">
							<input type="text" name="repayNextTimeStart" id="repayNextTimeStart" class="form-control underline" value="${hjhDebtCreditForm.repayNextTimeStart}" />
							<i class="ti-calendar"></i> </span>
						<span class="input-group-addon no-border bg-light-orange">~</span>
						<input type="text" name="repayNextTimeEnd" id="repayNextTimeEnd" class="form-control underline" value="${hjhDebtCreditForm.repayNextTimeEnd}" />
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
			<style type ="text/css">
				.search-classic{overflow-x:scroll;}
				#equiList{display:block;min-width:1500px;}
			</style>
		</tiles:putAttribute>
		
		<%-- Javascripts required for this page only (ignore) --%>
		<tiles:putAttribute name="pageJavaScript" type="string">
		    <script type="text/javascript"> var webRoot = "${webRoot}";</script>
			<script type="text/javascript" src="${themeRoot}/vendor/plug-in/colorbox/jquery.colorbox-min.js"></script> 
			<script type='text/javascript' src="${webRoot}/jsp/exception/bankdebtend/hjhcreditend.js"></script>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</shiro:hasPermission>
