var
// --------------------------------------------------------------------------------------------------------------------------------
/* 事件动作处理 */
Events = {
	// 确认按键单击事件绑定
	confirmClkAct : function() {
		if (Page.validate.check(false)&&Page.form.find(".has-error").length == 0) {
			Page.confirm("", "确定要保存当前的账户信息吗？", function(isConfirm) {
				if (isConfirm) {
					Page.submit();
				}
			})
		}
	},
	// 取消按键单击事件绑定
	cancelClkAct : function() {
		parent.$.colorbox.close();
	},
	// 可用券
	kyqCheckChgAct: function() {
		var isChecked = $("#checkAllUse").prop("checked");
		
		$("input[name='couponConfig']").each(function(index,element){
			$(this).prop("checked",isChecked);
			//Page.validate.check(false,element);
		});
	}

};

// --------------------------------------------------------------------------------------------------------------------------------
/* 画面对象 */
$.extend(Page, {
	// 初始化画面事件处理
	initEvents : function() {
		// 确认按键单击事件绑定
		$(".fn-Confirm").click(Events.confirmClkAct);
		$(".fn-Cancel").click(Events.cancelClkAct);
		// 可用券
		$("#checkAllUse").click(Events.kyqCheckChgAct);
		
		$("input[name='couponConfig']").change(function(){
			var len = $("input[name='couponConfig']").length;
			var checkedLen = $("input[name='couponConfig']:checked").length;
			if(checkedLen<len){
				$("#checkAllUse").prop("checked",false).change();
			}else{
				$("#checkAllUse").prop("checked",true).change();
			}
		});
	},
	// 画面布局
	doLayout : function() {
		// 初始化下拉框
		$(".form-select2").select2({
			allowClear : true,
			language : "zh-CN"
		});
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
	},
	// 画面初始化
	initialize : function() {
		// 执行成功后刷新画面
		($("#success").val() && parent.Events.refreshClkAct())
				|| Page.coverLayer();

		// 初始化表单验证
		Page.validate = Page.form.Validform({
			tiptype : 3,
		});
	},
}),

Page.initialize();
