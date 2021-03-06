var
// --------------------------------------------------------------------------------------------------------------------------------
    /* 对应JAVA的Controllor的@RequestMapping */
    Action = {
        // 查询的Action
        searchAction : "rewardInit",
        // 详细画面的Action
        infoAction : "infoAction",
        exportExcelAction : "exportExcelRewardAction"
    },
    /* 事件动作处理 */
    Events = {
        // 刷新按钮的单击动作事件
        refreshClkAct : function() {
            window.location.reload();
        },
        // 编辑按钮的单击动作事件
        modifyClkAct: function(event) {
            if(event) {
                Page.primaryKey.val($(this).data("id"))
            }
            $.colorbox({
                overlayClose: false,
                href: "#urlDialogPanel",
                title: "<i class=\"fa fa-plus\"></i> 修改活动配置",
                width: 500, height: 250,
                inline: true,  fixed: true, returnFocus: false, open: true,
                // Open事件回调
                onOpen: function() {
                    setTimeout(function() {
                        Page.form.attr("target", "dialogIfm").attr("action", Action.infoAction).submit();
                    }, 0)
                },
                // Close事件回调
                onClosed: function() {
                    Page.form.attr("target", "");
                }
            })
        },
        // 边界面板查询按钮的单击事件
        searchClkAct : function(event) {
            $("#paginator-page").val(1);
            Page.submit(Action.searchAction);
        },
        sortClkAct : function() {
            $("#col").val($(this).data("col"));
            $("#sort").val($(this).data("sort") == "ASC" ? 'DESC' : 'ASC');
            $("#paginator-page").val(1);
            Page.submit(Action.searchAction);
        },
        // 导出按钮的单击事件绑定
        exportClkAct : function(selection, cds) {
            setTimeout(function(){Page.coverLayer()},1);
            Page.submit(Action.exportExcelAction);
        },
        // 重置表单
        resetFromClkAct: function() {
            $(".form-select2").val("").change();
        }
    };

// --------------------------------------------------------------------------------------------------------------------------------
/* 画面对象 */
$.extend(Page, {
    // 画面的主键
    primaryKey : $("#ids"),
    // 画面布局
    doLayout: function() {
        // 条件下拉框
        $(".form-select2").select2({
            width: 268,
            placeholder: "请选择条件",
            allowClear: true,
            language: "zh-CN"
        })
    },
    // 初始化画面事件处理
    initEvents : function() {
         $(".fn-Export").click(Events.exportClkAct),
        // 刷新按钮的单击事件绑定
        $(".fn-Refresh").click(Events.refreshClkAct),
         // 修改按钮的单击事件绑定
         $(".fn-Modify").click(Events.modifyClkAct),
        // 边界面板查询按钮的单击事件绑定
        $(".fn-Search").click(Events.searchClkAct);
        // 刷新按钮的单击事件绑定
        $(".fn-Sort").click(Events.sortClkAct),
        // 重置按钮的单击事件绑定
        $(".fn-Reset").click(Events.resetFromClkAct);
    }
});
