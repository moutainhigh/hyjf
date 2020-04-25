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
		<tiles:putAttribute name="pageTitle" value="转出记录" />
		
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
									<c:set var="jspPrevDsb" value="${productredeemcustomizeForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
									<c:set var="jspNextDsb" value="${productredeemcustomizeForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
									<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
									<shiro:hasPermission name="productredeem:EXPORT">
										<a class="btn btn-o btn-primary btn-sm fn-Export"
												data-toggle="tooltip" data-placement="bottom" data-original-title="导出列表">导出列表 <i class="fa fa-plus"></i></a>
									</shiro:hasPermission>
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
										<th class="center">用户名</th>
										<th class="center">推荐人</th>
										<th class="center">一级部门</th>
										<th class="center">二级部门</th>
										<th class="center">团队</th>
										<th class="center">转出金额</th>
										<th class="center">转出收益</th>
										<th class="center">实际到账</th>
										<th class="center">操作平台</th>
										<th class="center">状态</th>
										<th class="center">转出时间</th>
										<th class="center">操作</th>
									</tr>
								</thead>
								<tbody id="roleTbody">
									<c:choose>
										<c:when test="${empty productredeemcustomizeForm.recordList}">
											<tr><td colspan="14">暂时没有数据记录</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${productredeemcustomizeForm.recordList }" var="record" begin="0" step="1" varStatus="status">
												<tr>
	    											<td class="center">${(productredeemcustomizeForm.paginatorPage - 1 ) * productredeemcustomizeForm.paginator.limit + status.index + 1 }</td>
	    											<td class="center"><c:out value="${record.orderId }"></c:out></td>
													<td><c:out value="${record.username }"></c:out></td>
													<td><c:out value="${record.refername }"></c:out></td>
													<td><c:out value="${record.regionName }"></c:out></td>
													<td><c:out value="${record.branceName }"></c:out></td>
													<td><c:out value="${record.departmentName }"></c:out></td>
													<td align="right"><fmt:formatNumber value="${record.amount }" pattern="#,##0.00#" /></td>
													<td align="right"><c:out value="${record.interest }"></c:out></td>
													<td align="right"><fmt:formatNumber value="${record.total }" pattern="#,##0.00#" /></td>
													<td>
													<c:forEach items="${clients }" var="cnt" begin="0" step="1">
														<c:if test="${cnt.nameCd eq record.client}"><c:out value="${cnt.name }"></c:out></c:if>
													</c:forEach>
													</td>
													<td class="center">
													<c:choose>
													<c:when test="${record.status==0}">成功</c:when>
												    <c:when test="${record.status==1}">失败</c:when>
												    </c:choose>
													</td>
													<td class="center"><c:out value="${record.redeemTime }"></c:out></td>
													<td class="center">
														<div class="visible-md visible-lg hidden-sm hidden-xs">
															<a class="btn btn-transparent btn-xs fn-Modify" 
																	data-toggle="tooltip" tooltip-placement="top" href= "${webRoot}/htl/productredeemdetail/init?listId=${record.orderId }" data-original-title="明细"><i class="fa fa-list"></i></a>
														</div>
														<div class="visible-xs visible-sm hidden-md hidden-lg">
															<div class="btn-group" dropdown="">
																<button type="button" class="btn btn-primary btn-o btn-sm" data-toggle="dropdown">
																	<i class="fa fa-cog"></i>&nbsp;<span class="caret"></span>
																</button>
																<ul class="dropdown-menu pull-right dropdown-light" role="menu">
																	<li>
																		<a href= "${webRoot}/htl/productredeemdetail/init?listId=${record.orderId }" class="fn-Modify" >明细</a>
																	</li>
																</ul>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>					
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<%-- 分页栏 --%>
							<shiro:hasPermission name="productredeem:VIEW">
								<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="init" paginator="${productredeemcustomizeForm.paginator}"></hyjf:paginator>
							</shiro:hasPermission>
							<br/><br/>
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>
		
		<%-- 检索面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<shiro:hasPermission name="productredeem:SEARCH">
				    <input type="hidden" name="userid" id="userid" value= "${userid}"/>
					<input type="hidden" name="paginatorPage" id="paginator-page" value="${productredeemcustomizeForm.paginatorPage}" />
					<!-- 检索条件 -->
					<div class="form-group">
						<label>汇盈订单号</label> 
						<input type="text" name="orderIdSrh" class="form-control input-sm underline"  maxlength="20" value="${ productredeemcustomizeForm.orderIdSrh}" />
					</div>
					<div class="form-group">
						<label>用户名</label> 
						<input type="text" name="usernameSrh" class="form-control input-sm underline"  maxlength="20" value="${ productredeemcustomizeForm.usernameSrh}" />
					</div>
					<div class="form-group">
						<label>推荐人</label> 
						<input type="text" name="refernameSrh" class="form-control input-sm underline" maxlength="20" value="${productredeemcustomizeForm.refernameSrh}"/>
					</div>
					<div class="form-group">
						<label>操作平台</label> 
						<select name="clientSrh" class="form-control underline form-select2">
							<option value=""></option>
							<c:forEach items="${clients }" var="cnt" begin="0" step="1">
								<option value="${cnt.nameCd }"
									<c:if test="${cnt.nameCd eq productredeemcustomizeForm.clientSrh}">selected="selected"</c:if>>
									<c:out value="${cnt.name }"></c:out>
								</option>
							</c:forEach>								
						</select>
					</div>
					<div class="form-group">
						<label>状态</label>
						<select name="statusSrh" class="form-control underline form-select2">
							<option value=""></option>
							<option value="0" <c:if test="${productredeemcustomizeForm.statusSrh=='0'}">selected</c:if>>成功</option>
							<option value="1" <c:if test="${productredeemcustomizeForm.statusSrh=='1'}">selected</c:if>>失败</option>
						</select>
					</div>
					<div class="form-group">
						<label>转出时间</label>
						<div class="input-group input-daterange datepicker">
							<span class="input-icon">
								<input type="text" name="timeStartSrch" id="start-date-time" class="form-control underline" value="${productredeemcustomizeForm.timeStartSrch}" />
								<i class="ti-calendar"></i> 
							</span>
							<span class="input-group-addon no-border bg-light-orange">~</span>
							<span class="input-icon">
								<input type="text" name="timeEndSrch" id="end-date-time" class="form-control underline" value="${productredeemcustomizeForm.timeEndSrch}" />
								<i class="ti-calendar"></i> 
							</span>
						</div>
					</div>
			</shiro:hasPermission>
		</tiles:putAttribute>			

		<%-- Javascripts required for this page only (ignore) --%>
		<tiles:putAttribute name="pageJavaScript" type="string">
			<script type='text/javascript' src="${webRoot}/jsp/htl/productredeem/productredeem.js"></script>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</shiro:hasPermission>
