var
// --------------------------------------------------------------------------------------------------------------------------------
/* 对应JAVA的Controllor的@RequestMapping */
Action = {
	// 查找的Action
	searchAction : "searchAction",
	// 删除的Action
	deleteAction : "deleteAction",
    // 详细画面的Action
    infoAction : "infoAction",
	// 导出Aciton
	exportExcelAction : "exportAction"
		
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
		
		$("#borrowNidSrch").val("");
		$("#start-date-time").val("");
		$("#end-date-time").val("");
	},
	// 刷新按钮的单击动作事件
	refreshClkAct : function() {
		window.location.reload();
	},
	// 删除按钮的单击动作事件
	deleteClkAct : function(selection, cds) {
		// 取得选择行
		Page.primaryKey.val($(this).data("id"))
		if (!Page.primaryKey.val()) {
			Page.alert("请选择要删除的数据！", "");
		} else {
			Page.confirm("", "该删除执行的是不可逆操作，确定要物理删除吗？", function(isConfirm) {
				if(isConfirm) {
					Page.submit(Action.deleteAction);
				}
			});
		}// Endif
	},
    // 添加按钮的单击动作事件
    addClkAct : function() {
        $.colorbox({
            overlayClose : false,
            href : "#urlDialogPanel",
            title : "<i class=\"fa fa-plus\"></i> 添加异常标的",
            width : "50%",
            height : 430,
            inline : true,
            fixed : true,
            returnFocus : false,
            open : true,
            // Open事件回调
            onOpen : function() {
                setTimeout(function() {
                    Page.primaryKey.val(""), Page.form.attr("target",
                        "dialogIfm").attr("action", Action.infoAction)
                        .submit();
                }, 0)
            },
            // Close事件回调
            onClosed : function() {
                Page.form.attr("target", "");
            }
        })
    },
    // 编辑按钮的单击动作事件
    modifyClkAct : function(event) {
        if (event) {
            Page.primaryKey.val($(this).data("id"))
        }
        $.colorbox({
            overlayClose : false,
            href : "#urlDialogPanel",
            title : "<i class=\"fa fa-plus\"></i> 修改异常标的信息",
            width : "50%",
            height : 430,
            inline : true,
            fixed : true,
            returnFocus : false,
            open : true,
            // Open事件回调
            onOpen : function() {
                setTimeout(function() {
                    Page.form.attr("target", "dialogIfm").attr("action",
                        Action.infoAction).submit();
                }, 0)
            },
            // Close事件回调
            onClosed : function() {
                Page.form.attr("target", "");
            }
        })
    },
	// 导出按钮的单击事件绑定
	exportClkAct : function(selection, cds) {
		$("#mainForm").attr("target", "_blank");
		Page.notice("正在处理下载数据,请稍候...");
		setTimeout(function(){Page.coverLayer()},1);
		Page.submit(Action.exportExcelAction);
	}
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
		// 删除按钮的单击事件绑定
		$(".fn-Delete").click(Events.deleteClkAct),
		// 添加按钮的单击事件绑定
		$(".fn-Add").click(Events.addClkAct),
		// 修改按钮的单击事件绑定
		$(".fn-Modify").click(Events.modifyClkAct),
		// 列表导出
		$(".fn-Export").click(Events.exportClkAct);
	}
});
