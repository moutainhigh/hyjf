var
// --------------------------------------------------------------------------------------------------------------------------------
/* 对应JAVA的Controllor的@RequestMapping */
Action = {
	// 页面查询
	searchAction : "ini",
	// 导出的Action
	exportAction : "export"
},
/* 事件动作处理 */
Events = {
	// 全选checkbox的change动作事件
	selectAllAct : function() {
		$.uniform.update($(".listCheck").attr("checked", this.checked));
	},
	// 重置表单
	resetFromClkAct: function() {
		$(".form-select2").val("").trigger('change');
	},
	// 编辑按钮的单击动作事件
	modifyClkAct : function(event) {
		// 取得选择行
		Page.primaryKey.val($(this).data("id"))
		if (!Page.primaryKey.val()) {
			Page.alert("请选择要编辑的数据！", "");
		} else {
			$.colorbox({
				overlayClose: false,
				href: "#urlDialogPanel",
				title: "<i class=\"fa fa-plus\"></i> 预注册修改",
				width: "40%", height: "500",
				inline: true,  fixed: true, returnFocus: false, open: true,
				// Open事件回调
				onOpen: function() {
					setTimeout(function() {
						Page.form.attr("target", "dialogIfm").attr("action", Action.updateAction).submit();
					}, 0)
				},
				// Close事件回调
				onClosed: function() {
					Page.form.attr("target", "");
				}
			})
		}// Endif
	},
	// 查找按钮的单击事件绑定
	searchClkAct : function(selection, cds) {
		$("#paginator-page").val(1);
		Page.submit(Action.searchAction);
	},
	// 刷新按钮的单击动作事件
	refreshClkAct : function() {
		window.location.reload();
	},
	exportClkAct : function() {
		$("#mainForm").attr("target", "_blank");
		Page.notice("正在处理下载数据,请稍候...");
		setTimeout(function(){Page.coverLayer()},1);
		$("#paginator-page").val(1);
		Page.submit(Action.exportAction);
	},
};

// --------------------------------------------------------------------------------------------------------------------------------
/* 画面对象 */
$.extend(Page, {
	// 画面的主键
	primaryKey : $("#id"),
	// 画面布局
	doLayout: function() {
		// 条件下拉框
		$(".form-select2").select2({
			width: 268,
			placeholder: "全部",
			allowClear: true,
			language: "zh-CN"
		}),
		// 日历选择器
		$('.datepicker').datepicker({
			autoclose: true,
			todayHighlight: true
		}),
		// 日历范围限制
		$('#start-date-time').on("show", function(evnet, d) {
			d = $('#end-date-time').datepicker("getDate"),
			d && $('#start-date-time').datepicker("setEndDate", d)
		}),
		$('#end-date-time').on("show", function(evnet, d) {
			d = $('#start-date-time').datepicker("getDate"),
			d && $('#end-date-time').datepicker("setStartDate", d)
		});
	},
	// 初始化画面事件处理
	initEvents : function() {
		// 重置表单
		$(".fn-Reset").click(Events.resetFromClkAct),
		// 导出按钮的单击事件绑定
		$(".fn-Export").click(Events.exportClkAct),
		// 修改按钮的单击事件绑定
		$(".fn-Modify").click(Events.modifyClkAct),
		// 刷新按钮的单击事件绑定
		$(".fn-Refresh").click(Events.refreshClkAct);
		// 边界面板按钮的单击事件绑定
		$(".fn-searchPanel").click(Events.searchPanelClkAct);
		// 查找按钮的单击事件绑定
		$(".fn-Search").click(Events.searchClkAct)
	}
});