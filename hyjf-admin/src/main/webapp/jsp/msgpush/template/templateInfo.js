var
// --------------------------------------------------------------------------------------------------------------------------------
/* 事件动作处理 */
Events = {
	// 确认按键单击事件绑定
	confirmClkAct : function() {
		if (Page.validate.check(false)&& $(".has-error").length == 0) {
			var message = "确定要保存当前填写的内容吗？";
			Page.confirm("", "确定要保存当前的信息吗？", function(isConfirm) {
				if (isConfirm) {
					Page.submit();
				}
			})
		}else{
			Page.countValidErr();
		}
	},
	// 删除图片
	redClkAct : function() {
		$("#iconUrl").val('');
	},
	// 下一步按钮的单击动作事件
	nextClkAct : function(event) {
		event.preventDefault(), $('a[href="' + $(this).attr("href") + '"]').tab('show');
	},
	// 消息标签单选框change事件
	tagIdChgAct : function(){
		if (this.value =="") {
			$("#templateCodePanel0").show();
			$("#templateCodePanel1").hide();
			$("#templateCode_pre").html("");// 前缀
			$("#tagCode").val("");
// Page.validate.unignore("#templateCode_end");
		} else {
			$("#templateCodePanel0").hide();
			$("#templateCodePanel1").show();
			$("#templateCode_pre").html($("#tagId").find("option:selected").attr("tid")+"_");// 前缀
			$("#tagCode").val($("#tagId").find("option:selected").attr("tid"));
// Page.validate.ignore("#templateCode_end");
		}
	},
	templateCodeChgAct : function(){
		$("#templateCode").attr("ajaxurl","checkAction?id="+$("#id").val()+"&templateCode="+$("#templateCode").val());
	},
	// 后续动作单选框change事件
	templateActionChgAct : function() {
		Page.validate.ignore("#templateActionUrl1");// 使验证无效
		Page.validate.ignore("#templateActionUrl2");// 使验证无效
        Page.validate.ignore("#templateActionUrl3");// 使验证无效
		if ($("#templateAction").val() == "0") {// 打开APP
			$("#actionPanel0").show();
			$("#actionPanel1").hide();
			$("#actionPanel2").hide();
            $("#actionPanel3").hide();
			$("#templateActionUrl1").val("");
            $("#templateActionUrl3").val("");
			$("#templateActionUrl2 option:first").prop("selected", 'selected');
		} else if ($("#templateAction").val() == "1") {// 打开H5页面

			if($("#id").val()!=""){  //增加新增和修改逻辑判断
                $("#actionPanel0").hide();
                $("#actionPanel1").show();
                $("#actionPanel2").hide();
                $("#actionPanel3").hide();
                $("#templateActionUrl2 option:first").prop("selected", 'selected');
                Page.validate.unignore("#templateActionUrl1");
                Page.validate.ignore("#templateActionUrl3");
                Page.validate.ignore("#templateActionUrl2");
			}else {
                $("#actionPanel0").hide();
                $("#actionPanel1").show();
                $("#actionPanel2").hide();
                $("#templateActionUrl2 option:first").prop("selected", 'selected');
                Page.validate.unignore("#templateActionUrl1");
                Page.validate.ignore("#templateActionUrl2");
			}

		}else if ($("#templateAction").val() == "3") {// 打开微信页面

            if($("#id").val()!=""){  //增加新增和修改逻辑判断
                $("#actionPanel0").hide();
                $("#actionPanel1").hide();
                $("#actionPanel2").hide();
                $("#actionPanel3").show();
                $("#templateActionUrl2 option:first").prop("selected", 'selected');
                Page.validate.ignore("#templateActionUrl1");
                Page.validate.unignore("#templateActionUrl3");
                Page.validate.ignore("#templateActionUrl2");
            }else {
                $("#actionPanel0").hide();
                $("#actionPanel1").show();
                $("#actionPanel2").hide();
                $("#templateActionUrl2 option:first").prop("selected", 'selected');
                Page.validate.unignore("#templateActionUrl1");
                Page.validate.ignore("#templateActionUrl2");
            }

        } else if ($("#templateAction").val() == "2") {// 指定原生页面
			$("#actionPanel0").hide();
			$("#actionPanel1").hide();
			$("#actionPanel2").show();
            $("#actionPanel3").hide();
			$("#templateActionUrl1").val("");
            $("#templateActionUrl3").val("");
			Page.validate.ignore("#templateActionUrl1");
			Page.validate.unignore("#templateActionUrl2");
            Page.validate.ignore("#templateActionUrl3");
		}
	},
	delfileClkAct : function(){
		$(this).parents("div.picClass").find("div.purMargin input").val("");
		$(this).parents("div.picClass").find("div.thumbnail img").attr("src","");
	}
};

// --------------------------------------------------------------------------------------------------------------------------------
/* 画面对象 */
$.extend(Page, {
	// 统计画面验证错误数
	countValidErr: function() {
		$("#mainTabs a").find(".badge").remove().end()
			.each(function(errCount) {
				errCount = $(".Validform_wrong", $(this).attr("href")).length,
				errCount && $("<span class='badge badge-danger margin-left-5'>" + errCount + "</span>").appendTo(this);
			});
	},
	// table2JSON
	getJsonByTable: function(table, rs) {
		return rs = [],
			table && table[0] && (
					table.find("tr").each(function(row, flag) {
						row = {},
						flag = false,
						$(this).find("td").each(function(cell, val) {
							cell = $("input, select", this),
							cell[0] && (
									val = row[cell.attr("name")] = cell.val(),
									val && !cell.data("ignore") && (flag = true));
						}),
						flag && rs.push(row);
					})), rs;
	},
	// table2JSON
	getJsonByTableName: function(table, rs) {
		return rs = [],
			table && table[0] && (
					table.find("tr").each(function(row) {
						row = {},
						flag = false,
						$(this).find("td").each(function(cell, val) {
							cell = $("input", this),
							cell[0] && (
									val = row[cell.attr("name")] = cell.val());
						}),
						rs.push(row);
					})), rs;
	},
	getJsonByTableImage: function(table, rs) {
		return rs = [],
		table && table[0] && (
				table.find("tr").each(function(row, flag) {
					row = {},
					flag = false,
					$(this).find("td").each(function(cell, val) {
						$("input", this).each(function() {
							val = row[$(this).attr("name")] = $(this).val(),
							val && (flag = true);
						})
					}),
					flag && rs.push(row);
				})), rs;
	},
	// 画面布局
	doLayout: function() {
		// 初始化富文本编辑域
		$('textarea.tinymce').tinymce({
			// Location of TinyMCE script
			script_url: themeRoot + '/vendor/plug-in/tinymce/tinymce.min.js',
			language_url : themeRoot + '/vendor/plug-in/tinymce/langs/zh_CN.js',
			
			height: 260,
			
			// theme: "modern",
			plugins: [
				"advlist autolink link image lioniteimages lists charmap print preview hr anchor pagebreak spellchecker",
				"searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
				"save table contextmenu directionality emoticons template paste textcolor colorpicker textpattern"
			],
			external_plugins: {
				// "moxiemanager": "/moxiemanager-php/plugin.js"
			},
			
			// Example content CSS (should be your site CSS)
			// content_css: "css/development.css",
			add_unload_trigger: false,
	
			toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image lioniteimages | print preview media fullpage | forecolor backcolor emoticons table",
	
			image_advtab: true,
	
			template_replace_values : {
				username : "Jack Black"
			},
	
			template_preview_replace_values : {
				username : "Preview user name"
			},
	
			link_class_list: [
				{title: 'Example 1', value: 'example1'},
				{title: 'Example 2', value: 'example2'}
			],
	
			image_class_list: [
				{title: 'Example 1', value: 'example1'},
				{title: 'Example 2', value: 'example2'}
			],
	
			templates: [
				{title: 'Some title 1', description: 'Some desc 1', content: '<strong class="red">My content: {$username}</strong>'},
				{title: 'Some title 2', description: 'Some desc 2', url: 'development.html'}
			],
	
			setup: function(ed) {
				
			},
	
			spellchecker_callback: function(method, data, success) {
				if (method == "spellcheck") {
					var words = data.match(this.getWordCharPattern());
					var suggestions = {};
	
					for (var i = 0; i < words.length; i++) {
						suggestions[words[i]] = ["First", "second"];
					}
					success({words: suggestions, dictionary: true});
				}
	
				if (method == "addToDictionary") {
					success();
				}
			}
		});
		Events.templateActionChgAct();
	},
	// 初始化画面事件处理
	initEvents : function() {
		$(".btn-red").click(Events.redClkAct);
		// 条件下拉框
		$(".form-select2").select2({
			width : 268,
			placeholder : "请选择条件",
			allowClear : true,
			language : "zh-CN"
		}),
		// 图片上传
		$('.fileupload').fileupload({
			url : "uploadFile",
			autoUpload : true,
			done : function(e, data) {
				var file = data.result[0];
				$(this).parents("div.picClass").find("div.purMargin input").val(file.imagePath);
				/* $("#image").val(file.imagePath); */
			}
		});
		// 删除图片
		$('#delfile').click(Events.delfileClkAct);
		// 日历选择器
		$('.datepicker').datepicker({
			autoclose : true,
			todayHighlight : true,
			dateFormat : "yyyy年MM月dd日"
		});
		// 日历范围限制
		$('#startTime').on("change", function(evnet, d) {
			d = $('#endTime').datepicker("getDate"), d && $('#startTime').datepicker("setEndDate", d)
		}), $('#endTime').on("change", function(evnet, d) {
			d = $('#startTime').datepicker("getDate"), d && $('#endTime').datepicker("setStartDate", d)
		});
		// 下一步按钮的单击事件绑定
		$(".fn-Next").click(Events.nextClkAct);
		// 确认按键单击事件绑定
		$(".fn-Confirm").click(Events.confirmClkAct);
		// 消息标签单选框change事件
		$("#tagId").change(Events.tagIdChgAct);
		$("#templateCode").change(Events.templateCodeChgAct);
		// 借款类型单选框change事件
		$("#templateAction").change(Events.templateActionChgAct);
	},
	ready : function() {
		// 渲染验证错误消息
		$(".Validform_wrong").closest(".form-group").addClass("has-error"),
		// 统计画面错误数
		this.countValidErr();
	},
	// 画面初始化
	initialize : function() {
		// 执行成功后刷新画面
		($("#success").val() && parent.Events.refreshClkAct()) || Page.coverLayer();

		// 初始化表单验证
		Page.validate = Page.form.Validform({
			tiptype : 3
		});
	}
}),

Page.initialize();
