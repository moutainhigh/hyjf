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



    <tiles:insertTemplate template="/jsp/layout/listLayout.jsp" flush="true">
        <%-- 画面的标题 --%>
        <tiles:putAttribute name="pageTitle" value="合作额度不足" />

        <%-- 画面主面板标题块 --%>
        <tiles:putAttribute name="pageFunCaption" type="string">
            <h1 class="mainTitle">合作额度不足</h1>
            <span class="mainDescription">合作额度不足处理。</span>
        </tiles:putAttribute>

        <%-- 画面主面板 --%>
        <tiles:putAttribute name="mainContentinner" type="string">
            <div class="container-fluid container-fullw bg-white">
                <div class="row">
                    <div class="col-md-12">
                        <div class="search-classic">
<%--
                            <shiro:hasPermission name="cashdeposite:VIEW">
--%>

                                <%-- 功能栏 --%>
                                <div class="well">
                                    <c:set var="jspPrevDsb" value="${assetListForm.paginator.firstPage ? ' disabled' : ''}"></c:set>
                                    <c:set var="jspNextDsb" value="${assetListForm.paginator.lastPage ? ' disabled' : ''}"></c:set>
                                    <a class="btn btn-o btn-primary btn-sm hidden-xs fn-Prev${jspPrevDsb}"
                                       data-toggle="tooltip" data-placement="bottom" data-original-title="上一页"><i class="fa fa-chevron-left"></i></a>
                                    <a class="btn btn-o btn-primary btn-sm hidden-xs margin-right-15 fn-Next${jspNextDsb}"
                                       data-toggle="tooltip" data-placement="bottom" data-original-title="下一页"><i class="fa fa-chevron-right"></i></a>

                                    <a class="btn btn-o btn-primary btn-sm fn-Refresh"
                                       data-toggle="tooltip" data-placement="bottom" data-original-title="刷新列表"> 刷新 <i class="fa fa-refresh"></i></a>

                                    <a class="btn btn-o btn-primary btn-sm hidden-xs fn-Add"
                                       data-toggle="tooltip" data-placement="bottom" data-original-title="批量处理"> 批量处理 <i class="fa"></i></a>


                             <%--       <a class="btn btn-o btn-primary btn-sm hidden-xs fn-Add"
                                       data-toggle="tooltip" data-placement="bottom"
                                       data-original-title="添加">添加 <i class="fa fa-plus"></i></a>--%>


                                    <a class="btn btn-o btn-info btn-sm pull-right fn-searchPanel"
                                       data-toggle="tooltip" data-placement="bottom" data-original-title="检索条件"
                                       data-toggle-class="active" data-toggle-target="#searchable-panel"><i class="fa fa-search margin-right-10"></i> <i class="fa fa-chevron-left"></i></a>
                                </div>


                            <%--</shiro:hasPermission>--%>

                            <br/>
                                <%-- 角色列表一览 --%>
                            <table id="equiList" class="table table-striped table-bordered table-hover">
                                <colgroup>
                                    <col style="width:55px;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th class="hidden-xs center">
                                        <div align="left" class="checkbox clip-check check-primary checkbox-inline"
                                             data-toggle="tooltip" tooltip-placement="top" data-original-title="选择所有行">
                                            <input type="checkbox" id="checkall">
                                            <label for="checkall"></label>
                                        </div>
                                    </th>
                                    <th class="center">序号</th>
                                    <th class="center">资产编号</th>
                                    <th class="center">资产来源</th>
                                    <th class="center">产品类型</th>
                                    <th class="center">项目编号</th>
                                    <th class="center">智投编号</th>
                                    <th class="center">用户名</th>
                                    <th class="center">手机号</th>
                                    <th class="center">银行电子账号</th>
                                    <th class="center">开户状态</th>
                                    <th class="center">姓名</th>
                                    <th class="center">身份证号</th>
                                    <th class="center">借款金额（元）</th>
                                    <th class="center">借款期限</th>
                                    <th class="center">还款方式</th>
                                    <th class="center">审核状态</th>
                                    <th class="center">项目状态</th>
                                    <th class="center">标的标签</th>
                                    <th class="center">推送时间</th>
                                    <th class="center">操作</th>
                                </tr>
                                </thead>
                                <tbody id="userTbody">
                                <c:choose>
                                    <c:when test="${empty assetListForm.recordList}">
                                        <tr><td colspan="21">暂时没有数据记录</td></tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${assetListForm.recordList }" var="record" begin="0" step="1" varStatus="status">
                                            <tr>
                                                <td class="hidden-xs center">
                                                    <div class="checkbox clip-check check-primary checkbox-inline">
                                                        <input type="checkbox" class="listCheck"  name="assetid" id="row${status.index }" value="${record.assetId }">
                                                        <label for="row${status.index }"></label>
                                                    </div>
                                                </td>
                                                <td class="center">${(assetListForm.paginatorPage-1)*assetListForm.paginator.limit+status.index+1 }</td>
                                                <td class="center"><c:out value="${record.assetId }"></c:out></td>
                                                <td class="center"><c:out value="${record.instName }"></c:out></td>
                                                <td class="center"><c:out value="${record.assetTypeName }"></c:out></td>
                                                <td class="center"><c:out value="${record.borrowNid }"></c:out></td>
                                                <td class="center"><c:out value="${record.planNid }"></c:out></td>
                                                <td class="center"><c:out value="${record.userName }"></c:out></td>
                                               <%-- <td class="center"><c:out value="${record.mobile }"></c:out></td>--%>

                                                <td>
                                                    <hyjf:asterisk value="${record.mobile }" permission="cashdeposite:HIDDEN_SHOW"></hyjf:asterisk></td>

                                                <td class="center"><c:out value="${record.accountId }"></c:out></td>
                                                <td class="center"><c:out value="${record.bankOpenAccount }"></c:out></td>
                                                <td class="center"><c:out value="${record.truename }"></c:out></td>
                                                <td class="center"><c:out value="${record.idcard }"></c:out></td>
                                                <td class="center"><c:out value="${record.account }"></c:out></td>
                                                <td class="center"><c:out value="${record.borrowPeriod }"></c:out></td>
                                                <td class="center"><c:out value="${record.borrowStyleName }"></c:out></td>
                                                <td class="center"><c:out value="${record.verifyStatus }"></c:out></td>
                                                <td class="center"><c:out value="${record.status }"></c:out></td>
                                                <td class="center"><c:out value="${record.labelName }"></c:out></td>
                                                <td class="center"><c:out value="${record.recieveTime }"></c:out></td>
                                                <td class="center">

                                                    <div class="visible-md visible-lg hidden-sm hidden-xs">
                                                                <a class="btn btn-transparent btn-xs tooltips fn-Modify" data-assetid="${record.assetId }"
                                                                   data-toggle="tooltip" tooltip-placement="top" data-original-title="处理" ><i class="fa ">处理</i></a>
                                                    </div>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                </tbody>
                            </table>
                                <%-- 分页栏 --%>

                            <%--<shiro:hasPermission name="cashdeposite:SEARCH">--%>

                                <hyjf:paginator id="equiPaginator" hidden="paginator-page" action="init" paginator="${assetListForm.paginator}"></hyjf:paginator>

                            <%--</shiro:hasPermission>--%>

                            <br/><br/>
                        </div>
                    </div>
                </div>
            </div>
        </tiles:putAttribute>
        <tiles:putAttribute name="dialogPanels" type="string">
            <iframe class="colobox-dialog-panel" id="urlDialogPanel" name="dialogIfm" style="border:none;width:100%;height:100%"></iframe>
        </tiles:putAttribute>
        <%-- 检索面板 (ignore) --%>
        <tiles:putAttribute name="searchPanels" type="string">
<%--
            <shiro:hasPermission name="assetlist:SEARCH">
--%>

                <input type="hidden" name="assetId" id="assetId" />
                <input type="hidden" name="paginatorPage" id="paginator-page" value="${assetListForm.paginatorPage}" />
                <!-- 检索条件 -->
                <div class="form-group">
                    <label>资产编号</label>
                    <input type="text" name="assetIdSrch" class="form-control input-sm underline"  maxlength="30" value="${ assetListForm.assetIdSrch}" />
                </div>
                <div class="form-group">
                    <label>资产来源</label>
                    <select name="instCodeSrch" id="instCodeSrch" class="form-control underline form-select2">
                        <option value=""></option>
                        <c:forEach items="${hjhInstConfigList }" var="inst" begin="0" step="1">
                            <option value="${inst.instCode }"
                                    <c:if test="${inst.instCode eq assetListForm.instCodeSrch}">selected="selected"</c:if>>
                                <c:out value="${inst.instName }"></c:out>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>项目编号</label>
                    <input type="text" name="borrowNidSrch" class="form-control input-sm underline"  maxlength="50" value="${ assetListForm.borrowNidSrch}" />
                </div>
                <div class="form-group">
                    <label>智投编号</label>
                    <input type="text" name="planNidSrch" class="form-control input-sm underline"  maxlength="30" value="${ assetListForm.planNidSrch}" />
                </div>
                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" name="userNameSrch" class="form-control input-sm underline"  maxlength="20" value="${ assetListForm.userNameSrch}" />
                </div>


<%--
            </shiro:hasPermission>
--%>
        </tiles:putAttribute>
        <%-- 对话框面板 (ignore) --%>
        <tiles:putAttribute name="dialogPanels" type="string">
            <iframe class="colobox-dialog-panel" id="urlDialogPanel" name="dialogIfm" style="border:none;width:100%;height:100%"></iframe>
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
            <script type='text/javascript' src="${webRoot}/jsp/exception/cashdepositexception/cashdepositexception.js"></script>
        </tiles:putAttribute>
    </tiles:insertTemplate>
