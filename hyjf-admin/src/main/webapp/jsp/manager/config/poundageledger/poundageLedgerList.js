var
// --------------------------------------------------------------------------------------------------------------------------------
/* 对应JAVA的Controllor的@RequestMapping */
Action = {
	// 页面查询
	searchAction : "searchAction?fid="+$("#fid").val(),
	//弹出申请框
	InfoAction: "infoAction",
	//删除
	deleteAction: "deleteAction"
	
	
},
/* 事件动作处理 */
Events = {
		// 刷新按钮的单击动作事件
		refreshClkAct : function() {
			//window.location.reload();
			$("#mainForm").attr("target", "");
			$("#paginator-page").val(1), Page.submit(Action.searchAction);
		},
		
		// 新增按钮的单击动作事件
		PoundageLedgerClkAct : function() {
			$.colorbox({
				overlayClose: false,
				href: "#urlDialogPanel",
				title: "<i class=\"fa fa-plus\"></i>添加手续费分账信息",
				width: "50%", height: "70%",
				inline: true,  fixed: true, returnFocus: false, open: true,
		        // Open事件回调
		        onOpen: function() {
		        	setTimeout(function() {
		        		Page.primaryKey.val(""),
		        		Page.form.attr("target", "dialogIfm").attr("action", Action.InfoAction).submit();
		        	}, 0)
		        },
		        // Close事件回调
		        onClosed: function() {
		        	Page.form.attr("target", "");
		        }
			})
		},
		// 编辑按钮的单击动作事件
		modifyClkAct : function(event) {
			if(event) {
				Page.primaryKey.val($(this).data("id"));
			}
			$.colorbox({
				overlayClose: false,
				href: "#urlDialogPanel",
				title: "<i class=\"fa fa-plus\"></i>修改手续费分账信息",
				width: "50%", height: "70%",
				inline: true,  fixed: true, returnFocus: false, open: true,
		        // Open事件回调
		        onOpen: function() {
		        	setTimeout(function() {
		        		Page.form.attr("target", "dialogIfm").attr("action", Action.InfoAction).submit();
		        	}, 0)
		        },
		        // Close事件回调
		        onClosed: function() {
		        	Page.form.attr("target", "");
		        }
			})
		},
		// 删除按钮的单击动作事件
		deleteClkAct : function(event) {
			if(event) {
				Page.primaryKey.val($(this).data("id"));
			}
			Page.confirm("", "确定要执行本次删除操作吗？", function(isConfirm) {
				isConfirm && Page.submit(Action.deleteAction);
			})
		},
		// 查找按钮的单击事件绑定
		searchClkAct : function(selection, cds) {
			$("#mainForm").attr("target", "");
			$("#paginator-page").val(1), Page.submit(Action.searchAction);
		},
		// 重置表单
		resetFromClkAct : function() {
			$(".form-select2").val("").trigger('change');
		}
};

// --------------------------------------------------------------------------------------------------------------------------------
/* 画面对象 */
$.extend(Page, {
	// 画面的主键
	primaryKey : $("#id"),
	// 画面布局
	doLayout : function() {
		// 条件下拉框
		$(".form-select2").select2({
			//width : 268,
			placeholder : "全部",
			allowClear : true,
			language : "zh-CN"
		})

	},
	// 初始化画面事件处理
	initEvents : function() {
		// 刷新按钮的单击事件绑定
		$(".fn-Refresh").click(Events.refreshClkAct),
		// ‘推荐人修改申请’按钮的单击事件绑定
		$(".fn-PoundageLedger").click(Events.PoundageLedgerClkAct),
		// 修改按钮的单击事件绑定
		$(".fn-Modify").click(Events.modifyClkAct),
		// 查找按钮的单击事件绑定
		$(".fn-Search").click(Events.searchClkAct),
		// 删除按钮的单击事件绑定
		$(".fn-Delete").click(Events.deleteClkAct),
		// 重置表单
		$(".fn-Reset").click(Events.resetFromClkAct)

	}
});



