<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/base/pageBase.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="hyjf" uri="/hyjf-tags"%>

<%-- 画面功能菜单设置开关 --%>
<c:set var="hasSettings" value="true" scope="request"></c:set>
<c:set var="hasMenu" value="true" scope="request"></c:set>
<c:set var="searchAction" value="#" scope="request"></c:set>

<shiro:hasPermission name="allocationengine:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="标的分配规则引擎" />

		<%-- 画面主面板的标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle"></h1>
			<span class="mainDescription">本功能可以增加修改删除标的分配规则引擎。</span>
		</tiles:putAttribute>
		
		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
				<div class="row">
					<div class="col-md-12">
						<div class="search-classic">
							<%-- 功能栏 --%>
							<div class="well">
							
								<c:set var="jspPrevDsb" value="${allocationengineForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
								<c:set var="jspNextDsb" value="${allocationengineForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
								<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
										data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
								<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
										data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
								
								<shiro:hasPermission name="allocationengine:EXPORT">
									<a class="btn btn-o btn-primary btn-sm fn-Export" data-toggle="tooltip" data-placement="bottom" data-original-title="导出Excel">导出Excel<i class="fa fa-Export"></i></a>
								</shiro:hasPermission>
	
 								<shiro:hasPermission name="allocationengine:ADD"> 
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Add"
											data-toggle="tooltip" data-placement="bottom" data-original-title="添加智投">添加智投 <i class="fa fa-plus"></i></a>
 								</shiro:hasPermission> 
 								
								<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Refresh"
										data-toggle="tooltip" data-placement="bottom" data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i></a>
										
								<a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
										data-toggle="tooltip" data-placement="bottom" data-original-title="检索条件"
										data-toggle-class="active" data-toggle-target="#searchable-panel"><i class="fa fa-search margin-right-10"></i> <i class="fa fa-chevron-left"></i></a>
							</div>
							<br/>
							<%-- 计划专区 --%>
							
							<label>智投专区</label>
							
							<table id="equiList" class="table table-striped table-bordered table-hover">
								<colgroup>
									<col style="width:55px;" />
								</colgroup>
								<thead>
									<tr>
										<th class="center">序号</th>
										<th class="center">智投编号</th>
										<th class="center">智投名称</th>
										<th class="center">添加时间</th>
										<th class="center">状态</th>
										<!-- <th class="center">创建时间</th> -->
										<th class="center">操作</th>
									</tr>
								</thead>
								<tbody id="roleTbody">
									<c:choose>
										<c:when test="${empty allocationengineForm.recordList}">
											<tr><td colspan="7">暂时没有数据记录</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${allocationengineForm.recordList }" var="record" begin="0" step="1" varStatus="status">
												<tr>
												    <!-- 序号 -->
													<td class="center"><c:out value="${status.index+((allocationengineForm.paginatorPage - 1) * allocationengineForm.paginator.limit) + 1 }"></c:out></td>
													<!-- 智投编号 -->
													<td class="center"><c:out value="${record.planNid }"></c:out></td>
													<!-- 智投名称 -->
													<td class="center"><c:out value="${record.planName }"></c:out></td>
													<!-- 添加时间 -->
													<td class="center"><hyjf:datetime value="${record.configAddTime }"></hyjf:datetime></td>
													<!-- 状态 0：停用 1：启用-->
													<td class="center"><c:out value="${record.configStatus== '1' ? '启用' : '停用' }"></c:out></td>
													<!-- 操作 -->
													<td class="center">
														<div class="visible-md visible-lg hidden-sm hidden-xs">
															<shiro:hasPermission name="allocationengine:VIEW"><%-- data-planOrderId="${record.planOrderId }" --%>
																<a class="btn btn-transparent btn-xs fn-TenderInfo" data-id="${record.id }" data-debtplannid="${record.planNid }"
																		data-toggle="tooltip" data-placement="top" data-original-title="智投配置">
																		<i class="fa fa-list-ul fa-white"></i>
																		</a>
															</shiro:hasPermission>
															<shiro:hasPermission name="allocationengine:MODIFY">
																<a class="btn btn-transparent btn-xs tooltips fn-UpdateBy"
																	data-id="${record.id }" data-toggle="tooltip"
																	tooltip-placement="top" data-original-title="${record.configStatus== '0' ? '启用' : '停用' }"><i
																	class="fa fa-lightbulb-o fa-white"></i></a>
															</shiro:hasPermission>
															<!--
															<shiro:hasPermission name="allocationengine:MODIFY">
																<a class="btn btn-transparent btn-xs fn-Change" data-id="${record.id }" data-debtplannid="${record.planNid }"
																		data-toggle="tooltip" data-placement="top" data-original-title="换绑">换绑</a>
															</shiro:hasPermission>
															-->
															<!-- 模型无删除 -->
															<%-- <shiro:hasPermission name="allocationengine:DELETE">
															<a class="btn btn-transparent btn-xs tooltips fn-Delete" data-id="${record.id }"
																	data-toggle="tooltip" tooltip-placement="top" data-original-title="删除"><i class="fa fa-times fa fa-white"></i></a>
															</shiro:hasPermission> --%>
														</div>

														<div class="visible-xs visible-sm hidden-md hidden-lg">
															<div class="btn-group" dropdown="">
																<button type="button" class="btn btn-primary btn-o btn-sm" data-toggle="dropdown">
																	<i class="fa fa-cog"></i>&nbsp;<span class="caret"></span>
																</button>
																<ul class="dropdown-menu pull-right dropdown-light" role="menu">
																<shiro:hasPermission name="allocationengine:VIEW">
																	<li>
																		<a class="fn-TenderInfo" data-debtPlanNid="${record.planNid }">智投配置</a>
																	</li>
																</shiro:hasPermission>
																	<!-- 模型无删除 -->
																	<%-- <shiro:hasPermission name="allocationengine:DELETE">
																	<li>
																		<a class="fn-Delete" data-id="${record.id }">删除</a>
																	</li>
																	</shiro:hasPermission> --%>
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
							<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="init" paginator="${allocationengineForm.paginator}"></hyjf:paginator>
							<br/><br/>
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>

		<%-- 检索面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<input type="hidden" name="id" id="id" />
			<input type="hidden" name="paginatorPage" id="paginator-page" value="${allocationengineForm.paginatorPage}" />
			
			<div class="form-group">
				<label>智投编号</label>
				<input type="text" name="planNidSrch" id="planNidSrch" class="form-control input-sm underline" value="${allocationengineForm.planNidSrch}" />
			</div>
			
			<div class="form-group">
				<label>智投名称</label>
				<input type="text" name="planNameSrch" class="form-control input-sm underline" value="${allocationengineForm.planNameSrch}" />
			</div>
			
 			<div class="form-group">
				<label>添加时间</label>
				<div class="input-group input-daterange datepicker">
					<span class="input-icon">
						<input type="text" name="startCreateSrch" id="start-date-time" class="form-control underline" value="${allocationengineForm.startCreateSrch}" />
						<i class="ti-calendar"></i> </span>
					<span class="input-group-addon no-border bg-light-orange">~</span>
						<input type="text" name="endCreateSrch" id="end-date-time" class="form-control underline" value="${allocationengineForm.endCreateSrch}" />
				</div>
			</div>
 			<div class="form-group">
				<label>状态</label>
				<select name="configStatusSrch" class="form-control underline form-select2">
					<option value="">全部</option>
					<option value="1"<c:if test="${allocationengineForm.configStatusSrch==1}">selected="selected"</c:if>>启用</option>
					<option value="0"<c:if test="${allocationengineForm.configStatusSrch==0}">selected="selected"</c:if>>停用</option>
				</select>
			</div> 
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
			<script type='text/javascript' src="${webRoot}/jsp/manager/allocationengine/allocationengine.js"></script>
		</tiles:putAttribute>
		
	</tiles:insertTemplate>
</shiro:hasPermission>
