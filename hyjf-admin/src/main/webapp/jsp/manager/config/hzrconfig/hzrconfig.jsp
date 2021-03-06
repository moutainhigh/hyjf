<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/base/pageBase.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="hyjf" uri="/hyjf-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 画面功能菜单设置开关 --%>
<c:set var="hasSettings" value="true" scope="request"></c:set>
<c:set var="hasMenu" value="true" scope="request"></c:set>
<c:set var="searchAction" value="#" scope="request"></c:set>

<shiro:hasPermission name="hzrconfig:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="汇转让设置" />
		
		<%-- 画面主面板的标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle">汇转让设置</h1>
			<span class="mainDescription">本功能可以增加修改删除汇转让。</span>
		</tiles:putAttribute>
		
		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
				<div class="row">
					<div class="col-md-12">
						<div class="search-classic">
							<%-- 功能栏 --%>
							<div class="well">
								<c:set var="jspPrevDsb" value="${hzrconfigForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
								<c:set var="jspNextDsb" value="${hzrconfigForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
								<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
										data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
								<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
										data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
 								<shiro:hasPermission name="hzrconfig:ADD"> 
								<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Add"
										data-toggle="tooltip" data-placement="bottom" data-original-title="添加">添加<i class="fa fa-plus"></i></a>
								</shiro:hasPermission>
								<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Refresh"
										data-toggle="tooltip" data-placement="bottom" data-original-title="刷新列表">刷新 <i class="fa fa-refresh"></i></a>
							</div>
							<br/>
							<%-- 汇转让列表一览 --%>
							<table id="equiList" class="table table-striped table-bordered table-hover">
								<colgroup>
									<col style="width:55px;" />
								</colgroup>
								<thead>
									<tr>
										<th class="center">序号</th>
										<th>编号</th>
										<th>名称</th>
										<th>数值</th>
										<th>单位</th>
										<th>说明</th>
										<th class="center">操作</th>
									</tr>
								</thead>
								<tbody id="roleTbody">
									<c:choose>
										<c:when test="${empty hzrconfigForm.recordList}">
											<tr><td colspan="7">暂时没有数据记录</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${hzrconfigForm.recordList }" var="record" begin="0" step="1" varStatus="status">
												<tr>
													<td class="center"><c:out value="${status.index+((hzrconfigForm.paginatorPage - 1) * hzrconfigForm.paginator.limit) + 1 }"></c:out></td>
													<td><c:out value="${record.code }"></c:out></td>
													<td><c:out value="${record.name }"></c:out></td>
													<td align="right"><fmt:formatNumber value="${record.value }" pattern="#,##0.00#" /></td>
													<td class="center"><c:out value="${record.unit }"></c:out></td>
													<td><c:out value="${record.remark }"></c:out></td>
													<td class="center">
														<div class="visible-md visible-lg hidden-sm hidden-xs">
 																	<shiro:hasPermission name="hzrconfig:MODIFY"> 
																<a class="btn btn-transparent btn-xs fn-Modify" data-id="${record.id }"
																	data-toggle="tooltip" tooltip-placement="top" data-original-title="修改"><i class="fa fa-pencil"></i></a>
																	</shiro:hasPermission>
 																<shiro:hasPermission name="hzrconfig:DELETE"> 
																<a class="btn btn-transparent btn-xs fn-Delete" data-id="${record.id }"
																	data-toggle="tooltip" tooltip-placement="top" data-original-title="删除"><i class="fa fa-times fa fa-white"></i></a>
																	</shiro:hasPermission>
														</div>
														<div class="visible-xs visible-sm hidden-md hidden-lg">
															<div class="btn-group" dropdown="">
																<button type="button" class="btn btn-primary btn-o btn-sm" data-toggle="dropdown">
																	<i class="fa fa-cog"></i>&nbsp;<span class="caret"></span>
																</button>
																<ul class="dropdown-menu pull-right dropdown-light" role="menu">
 																		<shiro:hasPermission name="hzrconfig:MODIFY"> 
																		<li>
																			<a class="fn-Modify" data-id="${record.id }">修改</a>
																		</li>
																		</shiro:hasPermission>
 																		<shiro:hasPermission name="hzrconfig:DELETE"> 
																		<li>
																			<a class="fn-Delete" data-id="${record.id }">删除</a>
																		</li>
																		</shiro:hasPermission>
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
							<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="init" paginator="${hzrconfigForm.paginator}"></hyjf:paginator>
							<br/><br/>
						</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>
		
		<%-- 检索面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<input type="hidden" name="id" id="id" />
			<input type="hidden" name="paginatorPage" id="paginator-page" value="${hzrconfigForm.paginatorPage}" />
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
			<script type='text/javascript' src="${webRoot}/jsp/manager/config/hzrconfig/hzrconfig.js"></script>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</shiro:hasPermission>