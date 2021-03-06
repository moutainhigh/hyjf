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
	<tiles:putAttribute name="pageTitle" value="合作伙伴管理" />
	
	<%-- 画面主面板 --%>
	<tiles:putAttribute name="mainContentinner" type="string">
		<c:set var="jspEditType"
			value="${empty contentpartnerForm.id ? '添加' : '修改'}"></c:set>
		<div class="panel panel-white" style="margin: 0">
			<div class="panel-body" style="margin: 0 auto">
				<div class="panel-scroll height-430">
					<form id="mainForm"
						action="${empty contentpartnerForm.id ? 'insertAction' : 'updateAction'}"
						method="post" role="form" class="form-horizontal">
						<%-- 合作伙伴列表一览 --%>
						<input type="hidden" name="id" id="id"
							value="${contentpartnerForm.id }" /> <input type="hidden"
							name="pageToken" value="${sessionScope.RESUBMIT_TOKEN}" /> <input
							type="hidden" id="success" value="${success}" />
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5" for="webname"> <span
								class="symbol required"></span>名称
							</label>
							<div class="col-xs-10">
								<input type="text" placeholder="名称" id="webname"
									name="webname" value="${contentpartnerForm.webname}"
									class="form-control" datatype="*2-30"
									errormsg="名称 只能是字符汉字，长度2~30个字符！" maxlength="30">
								<hyjf:validmessage key="webname" label="名称"></hyjf:validmessage>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5" for="partnerType"> <span
								class="symbol required"></span>伙伴类别
							</label>
							<div class="col-xs-10">
								<select name="partnerType" class="form-control underline form-select2"
									datatype="*" errormsg="请选择文章类型">
									<option value="">全部</option>
									<c:forEach items="${categoryList }" var="record" begin="0"
										step="1" varStatus="status">
										<option value="${record.id }"
											<c:if test="${contentpartnerForm.partnerType==record.id}">selected="selected"</c:if>>${record.title }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5" for="url">官网
							</label>
							<div class="col-xs-10">
								<input type="text" placeholder="网站url" id="url" name="url"  value="${contentpartnerForm.url}" class="form-control">
								<hyjf:validmessage key="url" label="网站url"></hyjf:validmessage>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5" for="author"> <span
								class="symbol required"></span>排序
							</label>
							<div class="col-xs-10">
								<input type="text" placeholder="排序只能是数字" id="order" name="order"
									value="${contentpartnerForm.order}" class="form-control"
									datatype="n1-3" errormsg="排序 只能是数字，长度1-3位数字！" maxlength="3">
								<hyjf:validmessage key="order" label="排序"></hyjf:validmessage>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5" for="logo"> <span
								class="symbol required"></span>合作伙伴图片
							</label>
							<div class="fileinput fileinput-new col-xs-6" data-provides="fileinput">
								<!-- 缺省图片 -->
								<div class="fileinput-new thumbnail">
									<img width="160" height="120" src="${fileDomainUrl}${contentpartnerForm.logo}" alt="">
								</div> 
								<!-- 显示图片 -->
								<div class="fileinput-preview fileinput-exists thumbnail"></div>
								<!-- 图片路径 -->
								<div class = "purMargin">
									<input type="text" readonly="readonly" 
										name="logo" id="logo" value="${contentpartnerForm.logo}" placeholder="上传图片路径"/>
								</div>
								<!-- 按钮管理 -->
								<div class="user-edit-image-buttons">
									<span class="btn btn-azure btn-file"><span class="fileinput-new"><i class="fa fa-picture"></i> 上传图片</span><span class="fileinput-exists"><i class="fa fa-picture"></i> 重新上传</span>
										<input type="file" name="file" id="fileupload" class= "fileupload" accept="image/jpeg,image/png,image/gif,image/x-ms-bmp,image/tiff">
									</span>
									<a href="#" class="btn fileinput-exists btn-red" data-dismiss="fileinput">
										<i class="fa fa-times"></i> 删除
									</a>
								</div>
								<hyjf:validmessage key="type" label="logo"></hyjf:validmessage>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5" for="logo1"> <span
								class="symbol required"></span>logo1
							</label>
							<div class="fileinput fileinput-new col-xs-6" data-provides="fileinput">
								<!-- 缺省图片 -->
								<div class="fileinput-new thumbnail">
									<img width="160" height="120" src="${fileDomainUrl}${contentpartnerForm.logo1}" alt="">
								</div> 
								<!-- 显示图片 -->
								<div class="fileinput-preview fileinput-exists thumbnail"></div>
								<!-- 图片路径 -->
								<div class = "purMargin">
									<input type="text" readonly="readonly" 
										name="logo1" id="logo1" value="${contentpartnerForm.logo1}" placeholder="上传图片路径"/>
								</div>
								<!-- 按钮管理 -->
								<div class="user-edit-image-buttons">
									<span class="btn btn-azure btn-file"><span class="fileinput-new"><i class="fa fa-picture"></i> 上传图片</span><span class="fileinput-exists"><i class="fa fa-picture"></i> 重新上传</span>
										<input type="file" name="file" id="fileupload1" class= "fileupload" accept="image/jpeg,image/png,image/gif,image/x-ms-bmp,image/tiff">
									</span>
									<a href="#" class="btn fileinput-exists btn-red" data-dismiss="fileinput">
										<i class="fa fa-times"></i> 删除
									</a>
								</div>
								<hyjf:validmessage key="type" label="logo1"></hyjf:validmessage>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5" for="content"> <span
								class="symbol required"></span>合作伙伴内容
							</label>
							<div class="col-xs-10">
								<textarea placeholder="合作伙伴内容说明" id="summary" name="summary"
									class="form-control limited" datatype="*" errormsg="合作伙伴必填！"
									maxlength="200">${contentpartnerForm.summary}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label padding-top-5"> <span
								class="symbol required"></span>状态
							</label>
							<div class="col-xs-10">
								<div class="radio clip-radio radio-primary ">
									<input type="radio" id="statusOn" name="status" datatype="*"
										value="0" class="event-categories"
										${contentpartnerForm.status == '0' ? 'checked' : ''}>
									<label for="statusOn"> 关闭 </label> <input type="radio"
										id="statusOff" name="status" datatype="*" value="1"
										class="event-categories"
										${contentpartnerForm.status == '1' ? 'checked' : ''}>
									<label for="statusOff"> 启用 </label>
								</div>
							</div>
							<hyjf:validmessage key="status" label="合作伙伴状态"></hyjf:validmessage>
						</div>
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


	</tiles:putAttribute>

	<%-- Javascripts required for this page only (ignore) --%>
	<tiles:putAttribute name="pageJavaScript" type="string">
		<script type="text/javascript"
			src="${themeRoot}/vendor/plug-in/jquery.validform_v5.3.2.js"></script>
		<script type='text/javascript'
			src="${webRoot}/jsp/manager/content/contentpartner/contentpartnerInfo.js"></script>
	</tiles:putAttribute>
</tiles:insertTemplate>
