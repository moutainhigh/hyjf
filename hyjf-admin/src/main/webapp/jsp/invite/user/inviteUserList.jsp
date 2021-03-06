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


<shiro:hasPermission name="activitylist:VIEW">
	<tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
		<%-- 画面的标题 --%>
		<tiles:putAttribute name="pageTitle" value="邀请会员列表" />

		<%-- 画面主面板标题块 --%>
		<tiles:putAttribute name="pageFunCaption" type="string">
			<h1 class="mainTitle">邀请会员列表</h1>
			<span class="mainDescription">本功能可以查询相应的邀请会员信息。</span>
		</tiles:putAttribute>

		<%-- 画面主面板 --%>
		<tiles:putAttribute name="mainContentinner" type="string">
			<div class="container-fluid container-fullw bg-white">
			<div class="tabbable">
				<ul class="nav nav-tabs" id="myTab"> 
					<shiro:hasPermission name="activitylist:VIEW">
			      		<li class="active"><a href="javascript:void(0);">账户信息</a></li>
			      	</shiro:hasPermission>
					<shiro:hasPermission name="activitylist:VIEW">
						<li ><a href="${webRoot}/invite/getRecommend/init">推荐星获取明细</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="activitylist:VIEW">
						<li ><a href="${webRoot}/invite/usedRecommend/init">推荐星使用明细</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="activitylist:VIEW">
						<li ><a href="${webRoot}/invite/exchangeconf/conf/init">兑换配置</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="activitylist:VIEW">
						<li ><a href="${webRoot}/invite/drawconf/conf/init">抽奖配置</a></li>
					</shiro:hasPermission>
			    </ul>
			    
				<div class="tab-content">
					<div class="tab-pane fade in active">
							<shiro:hasPermission name="activitylist:SEARCH">
								<%-- 功能栏 --%>
								<div class="well">
									<c:set var="jspPrevDsb" value="${inviteUserForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
									<c:set var="jspNextDsb" value="${inviteUserForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
									<a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
									<a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
											data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>
									<shiro:hasPermission name="activitylist:EXPORT">
										<a class="btn btn-o btn-primary btn-sm fn-Export"
												data-toggle="tooltip" data-placement="bottom" data-original-title="导出列表">导出列表 <i class="fa fa-plus"></i></a>
									</shiro:hasPermission>
									<a class="btn btn-o btn-primary btn-sm fn-Refresh"
											data-toggle="tooltip" data-placement="bottom" data-original-title="刷新列表"> 刷新 <i class="fa fa-refresh"></i></a>
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
								</colgroup>
								<thead>
									<tr>
										<th class="center">序号</th>
										<th class="center">用户名</th>
										<th class="center">姓名</th>
										<th class="center">手机号</th>
										<th class="center">所属部门</th>
										<!-- <th class="center">用户属性</th> -->
										<th class="center">可用推荐星数量</th>
										<th class="center">累计推荐星数量</th>
										<th class="center">使用推荐星数量</th>
									</tr>
								</thead>
								<tbody id="userTbody">
									<c:choose>
										<c:when test="${empty inviteUserForm.recordList}">
											<tr><td colspan="9">暂时没有数据记录</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${inviteUserForm.recordList }" var="record" begin="0" step="1" varStatus="status">
												<tr>
													<td class="center">${(inviteUserForm.paginatorPage-1)*inviteUserForm.paginator.limit+status.index+1 }</td>
													<td class="center"><c:out value="${record.userName }"></c:out></td>
													<td class="center"><c:out value="${record.trueName }"></c:out></td>
													<td class="center"><c:out value="${record.mobile }"></c:out></td>
													<td class="center"><c:out value="${record.departmentName }"></c:out></td>
													<%-- <td class="center"><c:out value="${record.userAttrName }"></c:out></td> --%>
													<td class="center"><c:out value="${record.recommendValidCount }"></c:out></td>
													<td class="center"><c:out value="${record.recommendAllCount }"></c:out></td>
													<td class="center"><c:out value="${record.recommendUsedCount }"></c:out></td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<%-- 分页栏 --%>
							<shiro:hasPermission name="activitylist:SEARCH">
								<hyjf:paginator id="equiPaginator" hidden="paginator-page" action="search" paginator="${inviteUserForm.paginator}"></hyjf:paginator>
							</shiro:hasPermission>
							<br/><br/>
						
					</div>
				</div>
			</div>
		</tiles:putAttribute>

		<%-- 检索面板 (ignore) --%>
		<tiles:putAttribute name="searchPanels" type="string">
			<shiro:hasPermission name="activitylist:SEARCH">
				<input type="hidden" name="ids" id="ids" />
				<input type="hidden" name="userId" id="userId" value= "${inviteUserForm.userId}"/>
				<input type="hidden" name="paginatorPage" id="paginator-page" value="${inviteUserForm.paginatorPage}" />
				<!-- 检索条件 -->
				<div class="form-group">
					<label>用户名</label>
					<input type="text" name="userName" class="form-control input-sm underline"  maxlength="20" value="${ inviteUserForm.userName}" />
				</div>
				<div class="form-group">
					<label>手机号码</label>
					<input type="text" name="mobile" class="form-control input-sm underline" maxlength="20" value="${inviteUserForm.mobile}"/>
				</div>
				<div class="form-group">
					<label>部门</label>
					<div class="dropdown-menu no-radius">
						<input type="text" class="form-control input-sm underline margin-bottom-10 " value="" id="combotree_search" placeholder="Search" >
						<input type="hidden" id="combotree_field_hidden"  name="combotreeSrch" value="${inviteUserForm.combotreeSrch}">
						<div id="combotree-panel" style="width:270px;height:300px;position:relative;overflow:hidden;">
							<div id="combotree" class="tree-demo" ></div>
						</div>
					</div>

					<span class="input-icon input-icon-right" data-toggle="dropdown" >
						<input id="combotree-field" type="text" class="form-control underline form " readonly="readonly">
						<i class="fa fa-remove fn-ClearDep" style="cursor:pointer;"></i>
					</span>
				</div>
				
				<div class="form-group">
					<label>用户属性</label>
					<select name="userAttrCd" class="form-control underline form-select2">
						<option value=""></option>
						<c:forEach items="${userPropertys }" var="property" begin="0" step="1" >
							<option value="${property.nameCd }"
								<c:if test="${property.nameCd eq inviteUserForm.userAttrCd}">selected="selected"</c:if>>
								<c:out value="${property.name }"></c:out>
							</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="form-group">
					<label>可用推荐星数量</label>
					<div class="input-group">
						<span class="input-icon">
							<input type="text" name="recommendValidCountMin" id="recommendValidCountMin" class="form-control underline" value="${inviteUserForm.recommendValidCountMin}" />
						</span>
						<span class="input-group-addon no-border bg-light-orange">~</span>
						<span class="input-icon">
							<input type="text" name="recommendValidCountMax" id="recommendValidCountMax" class="form-control underline" value="${inviteUserForm.recommendValidCountMax}" />
						</span>
					</div>
				</div>
				<div class="form-group">
					<label>累计推荐星数量</label>
					<div class="input-group">
						<span class="input-icon">
							<input type="text" name="recommendAllCountMin" id="recommendAllCountMin" class="form-control underline" value="${inviteUserForm.recommendAllCountMin}" />
						</span>
						<span class="input-group-addon no-border bg-light-orange">~</span>
						<span class="input-icon">
							<input type="text" name="recommendAllCountMax" id="recommendAllCountMax" class="form-control underline" value="${inviteUserForm.recommendAllCountMax}" />
						</span>
					</div>
				</div>
				<div class="form-group">
					<label>使用推荐星数量</label>
					<div class="input-group">
						<span class="input-icon">
							<input type="text" name="recommendUsedCountMin" id="recommendUsedCountMin" class="form-control underline" value="${inviteUserForm.recommendUsedCountMin}" />
						</span>
						<span class="input-group-addon no-border bg-light-orange">~</span>
						<span class="input-icon">
							<input type="text" name="recommendUsedCountMax" id="recommendUsedCountMax" class="form-control underline" value="${inviteUserForm.recommendUsedCountMax}" />
						</span>
					</div>
				</div>
			</shiro:hasPermission>
		</tiles:putAttribute>

		<%-- 画面的CSS (ignore) --%>
		<tiles:putAttribute name="pageCss" type="string">
			<link href="${themeRoot}/vendor/plug-in/colorbox/colorbox.css" rel="stylesheet" media="screen">
			<link href="${themeRoot}/vendor/plug-in/jstree/themes/default/style.min.css" rel="stylesheet" media="screen">
		</tiles:putAttribute>

		<%-- JS全局变量定义、插件 (ignore) --%>
		<tiles:putAttribute name="pageGlobalImport" type="string">
			<script type="text/javascript" src="${themeRoot}/vendor/plug-in/colorbox/jquery.colorbox-min.js"></script>
		</tiles:putAttribute>

		<%-- Javascripts required for this page only (ignore) --%>
		<tiles:putAttribute name="pageJavaScript" type="string">
			<script type="text/javascript" src="${themeRoot}/vendor/plug-in/jstree/jstree.min.js"></script>
			<script type='text/javascript' src="${webRoot}/jsp/invite/user/inviteUserList.js"></script>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</shiro:hasPermission>
