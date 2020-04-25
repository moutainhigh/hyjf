<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/base/pageBase.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="hyjf" uri="/hyjf-tags"%>
																																									


<%-- 画面功能菜单设置开关 --%>

<tiles:insertTemplate template="/jsp/layout/dialogLayout.jsp"
	flush="true">
	<%-- 画面的标题 --%>
	<tiles:putAttribute name="pageTitle" value="银行管理" />
	
	<%-- 画面主面板 --%>
	<tiles:putAttribute name="mainContentinner" type="string">
		<c:set var="jspEditType"
			value="${empty holidaysconfigForm.id ? '添加' : '修改'}"></c:set>
		<div class="panel panel-white" style="margin: 0">
			<div class="panel-body" style="margin: 0 auto">
				<div class="panel-scroll height-430">
					<form id="mainForm"
						action="${empty holidaysconfigForm.id ? 'insertAction' : 'updateAction'}"
						method="post" role="form" class="form-horizontal">
						<%-- 银行列表一览 --%>
						<input type="hidden" name="id" id="id"
							value="${holidaysconfigForm.id }" /> <input type="hidden"
							name="pageToken" value="${sessionScope.RESUBMIT_TOKEN}" /> <input
							type="hidden" id="success" value="${success}" />
						<%-- <div class="form-group">
							<label class="col-xs-3 control-label padding-top-5" for="name"> <span
								class="symbol required"></span>年份
							</label>
							<div class="col-xs-7">
								<input type="text" placeholder="请输入正确年份，如：2016" id="year" name="year"
									value="${holidaysconfigForm.year}" class="form-control"
									 datatype="/^\d{4}$/" errormsg="请输入正确年份">
								<hyjf:validmessage key="type" label="年份"></hyjf:validmessage>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="col-xs-3 control-label padding-top-5" for="name"> <span
								class="symbol required"></span>节日名称
							</label>
							<div class="col-xs-7">
								<input type="text" placeholder="节日名称" id="eventsName" name="eventsName"
									value="${holidaysconfigForm.eventsName}" class="form-control" 
									datatype="*1-50" errormsg="节日名称输入1~50个字符！" maxlength="50">
								<hyjf:validmessage key="type" label="节日名称"></hyjf:validmessage>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label padding-top-5" for="name"> <span
								class="symbol required"></span>时间
							</label>
							<div class="col-xs-3">	
								<div class="input-group">																														
									<input type="text"  placeholder="开始时间" id="statrTime" name="statrTime"
									class="form-control input-sm datepicker" value="${holidaysconfigForm.statrTime}"
									datatype="*1-50" errormsg="节日名称输入1~50个字符！" maxlength="50">																													
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>																													
								</div>																														
								
								<hyjf:validmessage key="type" label="开始时间"></hyjf:validmessage>
							</div>
							<div class="col-xs-1" class="form-control">
								~
							</div>
							<div class="col-xs-3">
								<div class="input-group">
									<input type="text"  placeholder="结束时间" id="endTime" name="endTime"
									class="form-control input-sm datepicker" value="${holidaysconfigForm.endTime}"
									datatype="*1-50" errormsg="节日名称输入1~50个字符！" maxlength="50">																													
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
								<hyjf:validmessage key="type" label="结束时间"></hyjf:validmessage>
							</div>
						</div>
						<br/>
						<div class="form-group margin-bottom-0">
							<div class="col-xs-offset-2 col-xs-10">
								<a class="btn btn-o btn-primary fn-Confirm"><i
									class="fa fa-check"></i> 确 认</a> <a
									class="btn btn-o btn-primary fn-Cancel"><i
									class="fa fa-close"></i> 取 消</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</tiles:putAttribute>

	<%-- 画面的CSS (ignore) --%>
	<tiles:putAttribute name="pageCss" type="string">
		<link href="${themeRoot}/vendor/plug-in/bootstrap-fileinput/jasny-bootstrap.min.css" rel="stylesheet" media="screen">
		<link href="${themeRoot}/vendor/plug-in/bootstrap-datepicker/bootstrap-datepicker3.standalone.min.css" rel="stylesheet" media="screen">																																										
		
		
		<style>
			.purMargin{
				margin:8px 0;
			}
			.purMargin input{
				width:200px;
			}
		</style>
	</tiles:putAttribute>

	<%-- JS全局变量定义、插件 (ignore) --%>
	<tiles:putAttribute name="pageGlobalImport" type="string">
		<!-- Form表单插件 -->
		<%@include file="/jsp/common/pluginBaseForm.jsp"%>
		<script src="${themeRoot}/vendor/plug-in/bootstrap-fileinput/jasny-bootstrap.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/bootstrap-ladda/spin.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/bootstrap-ladda/ladda.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/tinymce/jquery.tinymce.min.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/region-select.js"></script>
		<script type="text/javascript" src="${themeRoot}/assets/js/common.js"></script>
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/jquery.ui.widget.js"></script>
		<!-- The Templates plugin is included to render the upload/download listings -->
		<%-- 		<script type="text/javascript" src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/tmpl.min.js"></script> --%>
		<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/load-image.all.min.js"></script>
		<!-- The Canvas to Blob plugin is included for image resizing functionality -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/canvas-to-blob.min.js"></script>
		<!-- blueimp Gallery script -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/vendor/jquery.blueimp-gallery.min.js"></script>
		<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.iframe-transport.js"></script>
		<!-- The basic File Upload plugin -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload.js"></script>
		<!-- The File Upload processing plugin -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-process.js"></script>
		<!-- The File Upload image preview & resize plugin -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-image.js"></script>
		<!-- The File Upload audio preview plugin -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-audio.js"></script>
		<!-- The File Upload video preview plugin -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-video.js"></script>
		<!-- The File Upload validation plugin -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-validate.js"></script>
		<!-- The File Upload user interface plugin -->
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery-file-upload/jquery.fileupload-ui.js"></script>
		<script type="text/javascript" 
			src="${themeRoot}/vendor/plug-in/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>	
		<
	</tiles:putAttribute>
	<%-- Javascripts required for this page only (ignore) --%>
	<tiles:putAttribute name="pageJavaScript" type="string">
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery.validform_v5.3.2.js"></script>
		<script type='text/javascript'
			src="${webRoot}/jsp/manager/config/withdrawalstimeconfig/withdrawalstimeconfigInfo.js"></script>
		<script type="text/javascript">
		//日历选择器																						
		$('.datepicker').datepicker({																				
			autoclose: true,																			
			todayHighlight: true																			
		}),																				
		// 日历范围限制																				
		$('#start-date').on("change", function(evnet, d) {																				
			d = $('#end-date').datepicker("getDate"),																			
			d && $('#start-date').datepicker("setEndDate", d)																			
		}),																				
		$('#end-date').on("change", function(evnet, d) {																				
			d = $('#start-date').datepicker("getDate"),																			
			d && $('#end-date').datepicker("setStartDate", d)																			
		});																				

</script>
	</tiles:putAttribute>
</tiles:insertTemplate>
