var
// --------------------------------------------------------------------------------------------------------------------------------
/* 对应JAVA的Controllor的@RequestMapping */
Action = {
	// 查找的Action
	searchAction : "searchAction",
	// 导出的Action
	exportAction : "exportAction",
	// 详细画面的Action
	infoAction : "infoAction",
	// 预览的Action
	previewAction : "previewAction"
},
/* 事件动作处理 */
Events = {
	// 查找按钮的单击事件绑定
	searchClkAct : function(selection, cds) {
		$("#paginator-page").val(1);
		Page.submit(Action.searchAction);
	},
	// 重置表单
	resetFromClkAct: function() {
		$(".form-select2").val("").trigger('change');
		
		$("#planNidSrch").val("");
		$("#repayStatus").val("");
		$("#userName").val("");
		$("#planOrderId").val("");
		$("#liquidateFactTimeStart").val("");
		$("#liquidateFactTimeEnd").val("");
		$("#repayTimeStart").val("");
		$("#repayTimeEnd").val("");
	},
	exportClkAct : function() {
		var startTime =  $("#liquidateShouldTime").val();
		var endTime = $("#liquidateShouldTimeEnd").val();
		var esTime =  $("#repayTimeStart").val();
		var eeTime =$("#repayTimeEnd").val();
		var flag1 = false;
		var flag2 = false;
		if(startTime == "" || endTime == ""){
			flag1 = true;
		}
		if(esTime == "" || eeTime == ""){
			flag2 = true;
		}
		if(flag1 && flag2){
			Page.confirm("","请选择导出数据的起止时间（起止时间需小于等于31天）","error",{showCancelButton: false}, function(){});
			return false;
		}
		$("#mainForm").attr("target", "_blank");
		Page.notice("正在处理下载数据,请稍候...");
		setTimeout(function(){Page.coverLayer()},1);
		$("#paginator-page").val(1);
		Page.submit(Action.exportAction);
	},
	// 添加按钮的单击动作事件
	addClkAct : function() {
		Page.submit(Action.infoAction);
	},
	// 修改按钮的单击动作事件
	modifyClkAct: function(event) {
		Page.primaryKey.val($(this).data("debtplannid"));
		$("#planNidSrch").val($(this).data("debtplannid"));
			Page.submit(Action.infoAction);
	},
	
	// 预览按钮的单击动作事件
	previewClkAct : function(event) {
		if(event) {
			Page.primaryKey.val($(this).data("debtPlanNid"))
		}
		$.colorbox({
			overlayClose: false,
			href: "#urlDialogPanel",
			title: "<i class=\"fa fa-plus\"></i> 预览地址",
			width: "50%", height: 300,
			inline: true,  fixed: true, returnFocus: false, open: true,
	        // Open事件回调
	        onOpen: function() {
	        	setTimeout(function() {
	        		Page.form.attr("target", "dialogIfm").attr("action", Action.previewAction).submit();
	        	}, 0)
	        },
	        // Close事件回调
	        onClosed: function() {
	        	Page.form.attr("target", "");
	        }
		})
	},
	// 刷新按钮的单击动作事件
	refreshClkAct : function() {
		window.location.reload();
	},
	sortClkAct : function() {
		$("#col").val($(this).data("col"));
		$("#sort").val($(this).data("sort") == "asc" ? 'asc' : 'desc');
		$("#paginator-page").val(1);
		Page.submit(Action.searchAction);
	}
};

// --------------------------------------------------------------------------------------------------------------------------------
/* 画面对象 */
$.extend(Page, {
	// 画面的主键
	primaryKey : $("#debtPlanNid"),
	primaryKey1 : $("#debtPlanNidSrch"),
	// 画面布局
	doLayout: function() {
		// 条件下拉框
		$(".form-select2").select2({
			width: 268,
			placeholder: "请选择条件",
			allowClear: true,
			language: "zh-CN"
		}),
		// 日历选择器
		$('#liquidateShouldTime').datepicker({
			autoclose: true,
			todayHighlight: true
		});
		$('#liquidateShouldTimeEnd').datepicker({
			autoclose: true,
			todayHighlight: true
		});
	    // 日历选择器
		$('#repayTimeStart').datepicker({
			autoclose: true,
			todayHighlight: true
		});
		$('#repayTimeEnd').datepicker({
			autoclose: true,
			todayHighlight: true
		});
		var day30 = 30 * 24 * 60 * 60 * 1000;
		$("#liquidateShouldTime").on("changeDate", function(ev) {
	        var selectedDate = new Date(ev.date.valueOf());
	        var endDate = $('#liquidateShouldTimeEnd').datepicker("getDate");
	        var finalEndDate = new Date(+selectedDate + day30);
	        if(endDate==null){
	        	 $('#liquidateShouldTimeEnd').datepicker("setDate", finalEndDate);
	        }else if (+selectedDate > +endDate) {
	        	 $('#liquidateShouldTimeEnd').datepicker("setDate", finalEndDate);
	        }else if(+endDate - selectedDate > day30){
	        	$('#liquidateShouldTimeEnd').datepicker("setDate", finalEndDate);
	        }
	    });
	    $("#liquidateShouldTimeEnd").on("changeDate", function(ev) {
	        var selectedDate = new Date(ev.date.valueOf());
	        var startDate = $('#liquidateShouldTime').datepicker("getDate");
	        var finalEndDate = new Date(+selectedDate -day30);
	        if(startDate==null){
	        	$('#liquidateShouldTime').datepicker("setDate", finalEndDate);
	        }else if (+startDate > + selectedDate) {
	            $('#liquidateShouldTime').datepicker("setDate", finalEndDate);
	        }else if(+selectedDate - startDate > day30){
	        	$('#liquidateShouldTime').datepicker("setDate", finalEndDate);
	        }
	    });
	    $("#repayTimeStart").on("changeDate", function(ev) {
	        var selectedDate = new Date(ev.date.valueOf());
	        var endDate = $('#repayTimeEnd').datepicker("getDate");
	        var finalEndDate = new Date(+selectedDate + day30);
	        if(endDate==null){
	        	 $('#repayTimeEnd').datepicker("setDate", finalEndDate);
	        }else if (+selectedDate > +endDate) {
	        	 $('#repayTimeEnd').datepicker("setDate", finalEndDate);
	        }else if(+endDate - selectedDate > day30){
	        	$('#repayTimeEnd').datepicker("setDate", finalEndDate);
	        }
	    });
	    $("#repayTimeEnd").on("changeDate", function(ev) {
	        var selectedDate = new Date(ev.date.valueOf());
	        var startDate = $('#repayTimeStart').datepicker("getDate");
	        var finalEndDate = new Date(+selectedDate -day30);
	        if(startDate==null){
	        	$('#repayTimeStart').datepicker("setDate", finalEndDate);
	        }else if (+startDate > + selectedDate) {
	            $('#repayTimeStart').datepicker("setDate", finalEndDate);
	        }else if(+selectedDate - startDate > day30){
	        	$('#repayTimeStart').datepicker("setDate", finalEndDate);
	        }
	    });
	},
	// 初始化画面事件处理
	initEvents : function() {
		// 边界面板按钮的单击事件绑定
		$(".fn-searchPanel").click(Events.searchPanelClkAct),
		// 重置表单
		$(".fn-Reset").click(Events.resetFromClkAct),
		// 添加按钮的单击事件绑定
		$(".fn-Add").click(Events.addClkAct),
		// 导出按钮的单击事件绑定
		$(".fn-Export").click(Events.exportClkAct),
		// 查找按钮的单击事件绑定
		$(".fn-Search").click(Events.searchClkAct),
		// 修改按钮的单击事件绑定
		$(".fn-Modify").click(Events.modifyClkAct),
		// 刷新按钮的单击事件绑定
		$(".fn-Refresh").click(Events.refreshClkAct),
		// 刷新按钮的单击事件绑定
		$(".fn-Sort").click(Events.sortClkAct),
		// 刷新按钮的单击事件绑定
		$(".fn-preview").click(Events.previewClkAct),
		// 出借明细按钮的单击事件绑定
		$(".fn-TenderInfo").click(Events.tenderInfoClkAct);
	}
});
