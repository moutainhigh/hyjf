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

<shiro:hasPermission name="bankaccountlog:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="银行卡管理" />

		<%-- 画面主面板标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle">用户绑卡操作记录</h1>
			<span class="mainDescription">本功能可以查询相应的会员用户绑卡操作记录。</span>
		</tiles:putAttribute>
		
		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
				<div class="row">
					<div class="col-md-12">
						<div class="search-classic">
							<shiro:hasPermission name="bankaccountlog:SEARCH">
								<%-- 功能栏 --%>
								<div class="well">
									<c:set var="jspPrevDsb" value="${bankaccountlogForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
									<c:set var="jspNextDsb" value="${bankaccountlogForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
									<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
									<shiro:hasPermission name="bankaccountlog:EXPORT">
										<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Export"
												data-toggle="tooltip" data-placement="bottom" data-original-title="导出列表">导出列表 <i class="fa fa-plus"></i></a>
									</shiro:hasPermission>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Refresh"
											data-toggle="tooltip" data-placement="bottom" data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i></a>
									<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
											data-toggle="tooltip" data-placement="bottom" data-original-title="检索条件"
											data-toggle-class="active" data-toggle-target="#searchable-panel"><i class="fa fa-search margin-right-10"></i> <i class="fa fa-chevron-left"></i></a>
								</div>
							</shiro:hasPermission>
							<br/>
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
										<th class="center">银行账号</th>
										<th class="center">所属银行</th>
										<th class="center">操作</th>
										<th class="center">银行卡属性</th>
										<th class="center">操作时间</th>
									</tr>
								</thead>
								<tbody id="bankcardTbody">
									<c:choose>
										<c:when test="${empty bankaccountlogForm.recordList}">
											<tr><td colspan="8">暂时没有数据记录</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${bankaccountlogForm.recordList }" var="record" begin="0" step="1" varStatus="status">
												<tr>
													<td class="center">${(bankaccountlogForm.paginatorPage-1)*bankaccountlogForm.paginator.limit+status.index+1 }</td>
													<td><c:out value="${record.userName }"></c:out></td>
													<td><c:out value="${record.bankAccount }"></c:out></td>
													<td><c:out value="${record.bankName }"></c:out></td>
													<c:choose>
														<c:when test="${record.operationType== '0' }">
															<td class="center">绑定</td>
														</c:when>
														<c:when test="${record.operationType== '1' }">
															<td class="center">删除</td>
														</c:when>
														<c:when test="${record.operationType  == '2' }">
															<td class="center">修改</td>
														</c:when>
													</c:choose>
													<%--<td class="center"><c:out value="${record.operationType == '0' ? '绑定' : '删除'  }"></c:out></td>--%>
													<td class="center"><c:out value="${record.cardType  == '0' ? '普通提现卡' : '快捷支付卡'  }"></c:out></td>
													<td class="center"><hyjf:datetime value="${record.createTime }"></hyjf:datetime></td>
												</tr>
											</c:forEach>					
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<%-- 分页栏 --%>
							<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="searchAction" paginator="${bankaccountlogForm.paginator}"></hyjf:paginator>
							<br/><br/>
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>
		<%-- 边界面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<input type="hidden" name="userId" id="userId" value= "${bankaccountlogForm.userId}"/>
			<input type="hidden" name="paginatorPage" id="paginator-page" value="${bankaccountlogForm.paginatorPage}" />
			<!-- 检索条件 -->
			<div class="form-group">
				<label>用户名</label> 
				<input type="text" name="userNameSrch" class="form-control input-sm underline"  maxlength="20" value="${bankaccountlogForm.userNameSrch}" />
			</div>
			<div class="form-group">
				<label>所属银行</label> 
				<select name="bankIdSrch" class="form-control underline form-select2">
					<option value=""></option>
					<c:forEach items="${banks}" var="bank" begin="0" step="1">
						<option value="${bank.code }"
							<c:if test="${bank.code eq bankaccountlogForm.bankIdSrch}">selected="selected"</c:if>>
							<c:out value="${bank.name }"></c:out>
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group">
				<label>操作时间</label>
				<div class="input-group input-daterange datepicker">
					<span class="input-icon">
						<input type="text" name="startTime" id="start-date-time" class="form-control underline" value="${bankaccountlogForm.startTime}" />
						<i class="ti-calendar"></i> 
					</span>
					<span class="input-group-addon no-border bg-light-orange">~</span>
					<span class="input-icon">
						<input type="text" name="endTime" id="end-date-time" class="form-control underline" value="${bankaccountlogForm.endTime}" />
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
			<script type='text/javascript' src="${webRoot}/jsp/manager/users/bankaccountlog/bankaccountlogList.js"></script>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</shiro:hasPermission>
