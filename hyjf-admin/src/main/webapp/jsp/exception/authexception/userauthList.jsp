<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/base/pageBase.jsp"%>
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
<c:set var="searchAction" value="#" scope="request"></c:set>

<shiro:hasPermission name="exceptionuserauth:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="自动出借/债转授权异常处理" />

		<%-- 画面主面板标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle">自动出借/债转授权异常处理</h1>
			<span class="mainDescription">本功能可以处理自动出借/债转授权异常。</span>
		</tiles:putAttribute>
		
		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
				<div class="tabbable">
					<ul class="nav nav-tabs" id="myTab"> 
						<shiro:hasPermission name="exceptionuserauth:SEARCH">
				      		<li  class="active"><a href="${webRoot}/exception/authexception/userauthlist">授权状态</a></li>
				      	</shiro:hasPermission>
				    </ul>
				    <div class="tab-content">
					    <div class="tab-pane fade in active">
							<div class="row">
								<div class="col-md-12">
									<div class="search-classic">
										<shiro:hasPermission name="exceptionuserauth:SEARCH">
											<%-- 功能栏 --%>
											<div class="well">
												<c:set var="jspPrevDsb" value="${userauthListForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
												<c:set var="jspNextDsb" value="${userauthListForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
												<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
														data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
												<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
														data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
												<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
														data-toggle="tooltip" data-placement="bottom" data-original-title="检索条件"
														data-toggle-class="active" data-toggle-target="#searchable-panel"><i class="fa fa-search margin-right-10"></i> <i class="fa fa-chevron-left"></i></a>
											</div>
										</shiro:hasPermission>
										<br/>
										<%-- 角色列表一览 --%>
										<table id="equiList" class="table table-striped table-bordered table-hover">
											<colgroup>
												<col style="width:55px;" />
												<col style="width:;" />
												<col style="width:;" />
												<col style="width:;" />
												<col style="width:;" />
												<col style="width:;" />
												<col style="width:;" />
											</colgroup>
											<thead>
												<tr>
													<th class="center">序号</th>
													<th class="center">用户名</th>
													<th class="center">推荐人</th>
													<th class="center">自动投标到期日</th>
													<th class="center">自动投标订单号</th>
													<th class="center">自动投标授权状态</th>
													<th class="center">自动债转授权状态</th>
													<th class="center">自动债转授权订单号</th>
													<th class="center">操作</th>
												</tr>
											</thead>
											<tbody id="userauthTbody">
												<c:choose>
													<c:when test="${empty userauthListForm.recordList}">
														<tr><td colspan="11">暂时没有数据记录</td></tr>
													</c:when>
													<c:otherwise>
														<c:forEach items="${userauthListForm.recordList }" var="record" begin="0" step="1" varStatus="status">
															<tr>
																<td class="center">${(userauthListForm.paginatorPage-1)*userauthListForm.paginator.limit+status.index+1 }</td>
																<td class="center"><c:out value="${record.userName }"></c:out></td>
																<td class="center"><c:out value="${record.recommendName }"></c:out></td>
																<td class="center"><c:out value="${record.autoInvesEndTime }"></c:out></td>
																<td class="center"><c:out value="${record.autoOrderId }"></c:out></td>
																<td class="center"><c:out value="${record.autoInvesStatus }"></c:out></td>
																<td class="center"><c:out value="${record.autoCreditStatus }"></c:out></td>
																<td class="center"><c:out value="${record.autoCreditOrderId }"></c:out></td>

																<td class="center">
																	<a class="fn-invesCancel" data-userid="${record.userId }">自动投标同步</a>
																	<a class="fn-creditCancel" data-userid="${record.userId }">自动债转同步</a>
																</td>
															</tr>
														</c:forEach>					
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
										<%-- 分页栏 --%>
										<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="userauthlist" paginator="${userauthListForm.paginator}"></hyjf:paginator>
										<br/><br/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>
		<%-- 边界面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<input type="hidden" name="userId" id="userId" value= "${userauthListForm.userId}"/>
			<input type="hidden" name="paginatorPage" id="paginator-page" value="${userauthListForm.paginatorPage}" />
			<!-- 检索条件 -->
			<div class="form-group">
				<label>用户名</label> 
				<input type="text" name="userName" class="form-control input-sm underline"  maxlength="20" value="${userauthListForm.userName}" />
			</div>
			<div class="form-group">
				<label>推荐人</label>
				<input type="text" name="recommendName" class="form-control input-sm underline"  maxlength="20" value="${userauthListForm.recommendName}" />
			</div>
			<div class="form-group">
				<label>自动投标签约状态</label>
				<select name="autoInvesStatus" class="form-control underline form-select2">
					<option value=""></option>
					<option value="1"<c:if test="${'1' eq userauthListForm.autoInvesStatus}">selected="selected"</c:if>>已授权</option>
					<option value="0"<c:if test="${'0' eq userauthListForm.autoInvesStatus}">selected="selected"</c:if>>未授权</option>
					<option value="2"<c:if test="${'2' eq userauthListForm.autoInvesStatus}">selected="selected"</c:if>>已解约</option>
				</select>
			</div>
			<div class="form-group">
				<label>自动债转签约状态</label>
				<select name="autoCreditStatus" class="form-control underline form-select2">
					<option value=""></option>
					<option value="1"<c:if test="${'1' eq userauthListForm.autoCreditStatus}">selected="selected"</c:if>>已授权</option>
					<option value="0"<c:if test="${'0' eq userauthListForm.autoCreditStatus}">selected="selected"</c:if>>未授权</option>
					<option value="2"<c:if test="${'2' eq userauthListForm.autoCreditStatus}">selected="selected"</c:if>>已解约</option>
				</select>
			</div>
			<div class="form-group">
				<label>授权时间</label>
				<div class="input-group input-daterange datepicker">
					<span class="input-icon">
						<input type="text" name="invesAddTimeStart" id="inves-start-date-time" class="form-control underline" value="${userauthListForm.invesAddTimeStart}" />
						<i class="ti-calendar"></i>
					</span>
					<span class="input-group-addon no-border bg-light-orange">~</span>
					<span class="input-icon">
						<input type="text" name="invesAddTimeEnd" id="inves-end-date-time" class="form-control underline" value="${userauthListForm.invesAddTimeEnd}" />
					</span>
				</div>
			</div>
			<div class="form-group">
				<label>签约到期日</label>
				<div class="input-group input-daterange datepicker">
					<span class="input-icon">
						<input type="text" name="investEndTimeStart" id="credit-start-date-time" class="form-control underline" value="${userauthListForm.investEndTimeStart}" />
						<i class="ti-calendar"></i>
					</span>
					<span class="input-group-addon no-border bg-light-orange">~</span>
					<span class="input-icon">
						<input type="text" name="investEndTimeEnd" id="credit-end-date-time" class="form-control underline" value="${userauthListForm.investEndTimeEnd}" />
					</span>
				</div>
			</div>
		</tiles:putAttribute>
		<%-- 对话框面板 (ignore) --%>
		<tiles:putAttribute name="dialogpanels" type="string">
			<iframe class="colobox-dialog-panel" id="urlDialogPanel" name="dialogIfm" style="border:none;width:100%;height:100%"></iframe>
		</tiles:putAttribute>
		
		<%-- Javascripts required for this page only (ignore) --%>
		<tiles:putAttribute name="pageJavaScript" type="string">
		<script type="text/javascript"> var webRoot = "${webRoot}";</script>
		<script type='text/javascript' src="${webRoot}/jsp/exception/authexception/userauthList.js"></script>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</shiro:hasPermission>
