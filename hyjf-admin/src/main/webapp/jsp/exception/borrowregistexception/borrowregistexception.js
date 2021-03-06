var
// --------------------------------------------------------------------------------------------------------------------------------
/* 对应JAVA的Controllor的@RequestMapping */
Action = {
	// 查找的Action
	searchAction : "searchAction",
	// 标的撤销Action
	borrowregistexception : "borrowregistexception",
	// 导出Aciton
	exportExcelAction : "exportAction"
		
},
/* 事件动作处理 */
Events = {
	// 查找按钮的单击事件绑定
	searchClkAct : function() {
		$("#paginator-page").val(1);
		Page.submit(Action.searchAction);
	},
	// 重置表单
	resetFromClkAct: function() {
		$(".form-select2").val("").trigger('change');
		$("#borrowNidSrch").val("");
		$("#borrowNameSrch").val("");
		$("#usernameSrch").val("");
		$("#start-date-time").val("");
		$("#end-date-time").val("");
	},
	// 刷新按钮的单击动作事件
	refreshClkAct : function() {
		window.location.reload();
	},
	// 导出按钮的单击事件绑定
	exportClkAct : function() {
		$("#mainForm").attr("target", "_blank");
		Page.notice("正在处理下载数据,请稍候...");
		setTimeout(function(){Page.coverLayer()},1);
		Page.submit(Action.exportExcelAction);
	},
	debtRegistAct : function(event){
		// 取得选择行
		Page.primaryKey.val($(this).data("id"))
		// 列表借款人用户名
		userName = $(this).data("username");
		if(!Page.primaryKey.val()){
			Page.alert("请选择要处理的标的!","");
		}else{
			Page.confirm("", "确定要处理标的备案异常吗？", function(isConfirm) {
				if(isConfirm) {
					var param = {};
					param.borrowNidSrch = Page.primaryKey.val();
					
					param.userName = userName;
					
				    Page.coverLayer("正在操作数据，请稍候...");
				    $.ajax({
						url : Action.borrowregistexception,
						type : "POST",
						async : true,
						data : JSON.stringify(param),
						dataType: "json",
						contentType : "application/json",
						success : function(data) {
							Page.coverLayer();
							Page.primaryKey.val("");
							if (data.success == "0") {
							    Page.confirm("",data.msg,"success",{showCancelButton: false}, function(){Events.refreshClkAct()});
							} else {
								Page.confirm("",data.msg,"error",{showCancelButton: false}, function(){Events.refreshClkAct()});
							}
						},
						error : function(err) {
							Page.coverLayer();
							Page.primaryKey.val("");
							Page.notice("标的备案发生错误,请重新操作!", "","error");
						}
				    });
				}
			});		
		}
	}
};

// --------------------------------------------------------------------------------------------------------------------------------
/* 画面对象 */
$.extend(Page, {
	// 画面的主键
	primaryKey : $("#borrowNid"),
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
		$('#start-date-time').datepicker({
			autoclose: true,
			todayHighlight: true,
			endDate:new Date()
		});
		$('#end-date-time').datepicker({
			autoclose: true,
			todayHighlight: true,
			endDate:new Date()
		});
		
		// 日历选择器
		$('#recover-start-date-time').datepicker({
			autoclose: true,
			todayHighlight: true,
			endDate:new Date()
		});
		$('#recover-end-date-time').datepicker({
			autoclose: true,
			todayHighlight: true,
			endDate:new Date()
		});
		
		
	    $("#recover-start-date-time").on("changeDate", function(ev) {
	        var selectedDate = new Date(ev.date.valueOf());
	        $('#recover-end-date-time').datepicker("setStartDate", selectedDate);
	        $('#recover-end-date-time').datepicker("setDate", selectedDate);
	    });
		
	    $("#start-date-time").on("changeDate", function(ev) {
	        var selectedDate = new Date(ev.date.valueOf());
	        $('#end-date-time').datepicker("setStartDate", selectedDate);
	        $('#end-date-time').datepicker("setDate", selectedDate);
	    });
	},
	// 初始化画面事件处理
	initEvents : function() {
		// 边界面板按钮的单击事件绑定
		$(".fn-searchPanel").click(Events.searchPanelClkAct),
		// 重置表单
		$(".fn-Reset").click(Events.resetFromClkAct),
		// 查找按钮的单击事件绑定
		$(".fn-Search").click(Events.searchClkAct),
		// 刷新按钮的单击事件绑定
		$(".fn-Refresh").click(Events.refreshClkAct),
		// 撤销按钮的单击事件绑定
		$(".fn-debtRegist").click(Events.debtRegistAct),
		// 列表导出
		$(".fn-Export").click(Events.exportClkAct);
	}
});
