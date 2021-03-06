$(function() {
    $(".kaihu-input").focus(function() {
        $(this).parent(".kh-item").addClass("focus");
    }).blur(function() {
        $(this).parent(".kh-item").removeClass("focus");
    });
    $('.drag').drag();
//    var getver = function() {
//
//        var auth_url = webPath + "/bank/web/user/bankopen/sendCode.do"; //获取注册码的url地址
//        var remainingTime = countDown; //倒计时时间
//        var timerClock = null;

        //发送验证码 校验手机号 手机号校验成功后才能发送手机号
//        var mobile = $('#mobile').val();
//        var logOrderId=$('#logOrderId').val();
//        //开始计时器
//            startTimerClock();
//            $.ajax({
//                type: "POST",
//                url: auth_url,
//                data: {
//                	mobile : mobile,
//                	logOrderId:logOrderId
//    			},
//                success: function(data) {
//                    if (data.status) {
//                    	//utils.alert({content:data.message});
//
//                    } else {
//                    	endTimerClock();
//                        utils.alert({content:data.message});
//                    }
//                }
//            });

//        function startTimerClock() {
//            clearTimeout(timerClock);
//            if (remainingTime <= 0) {
//                endTimerClock();
//                return;
//            }
//            $(".get-btn").attr('disabled', 'disabled').val("剩余" + remainingTime + "秒");
//            timerClock = setTimeout(function() {
//                startTimerClock();
//                remainingTime--;
//            }, 1000);
//        };
//        //结束计时器
//        function endTimerClock() {
//            clearTimeout(timerClock);
//            isTimeing = false; //重置倒计时状态
//            remainingTime = 60;
//            $(".get-btn").removeAttr('disabled').val("获取");
//        };
//    }

//    $(".get-btn").on("click", function(){
//        if($("#mobile").valid()){
//            getver();
//        }
//    });
});

$().ready(function() {
	var submitFlag = true;
    // 在键盘按下并释放及提交后验证提交表单
    $("#bankOpenForm").validate({
    	onkeyup:false,
        errorElement: "span",
        rules: {
        	bankLow: "required",
            //	      用户名验证
            trueName: {
                required: true,
                userName: true,
            },
            //	      身份证验证
            idNo: {
            	required:true,
	            minlength:15,
	            maxlength:20,
	            isIdCard:true
            },
            //	      电话号码验证
            mobile: {
            	required:true,
				number : true,
				minlength : 11,
				isMobile : true,
				maxlength : 11
            },
//            smsCode: {
//            	required:true,
//	            minlength:6,
//	            maxlength:6,
//            },
        },
        messages: {
        	trueName: {
                required: "姓名输入不正确",
                userName: "用户名只能包括中文字、英文字母、数字和下划线"
            },
            idNo: {
            	required:"请填写身份证号码",
	            minlength:"请输入正确的身份证号码",
	            maxlength:"请输入正确的身份证号码",
	            isIdCard:"请输入正确的身份证号码"
            },
            mobile: {
            	required:"请填写手机号",
				number : "请填写您的真实手机号码",
				minlength : "请填写您的真实手机号码",
				isMobile : "请填写您的真实手机号码",
				maxlength : "请填写您的真实手机号码"
            },
//            smsCode: {
//            	required:"请填写短信验证码",
//	            minlength:"短信校验码不正确",
//	            maxlength:"短信校验码不正确",
//	            remote: "短信校验码不正确"
//            },
            bankLow: "需同意协议",
        },
        submitHandler: function(form) { //通过之后回调
    	    // 神策开户提交
    	    saEvents.submitAccount($('#trueName').val())
        	form.submit()
        }
    });
});
