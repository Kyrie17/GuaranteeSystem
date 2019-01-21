function check() {
	// 信息填写不完整或学号已被注册
	if (($("#password").val() == "")
			|| ($("#password").val() == null)
			|| ($("#stu_id").val() == "")
			|| ($("#stu_id").val() == null)
			|| ($("#mess").val() == "")
			|| ($("#mess").val() == null)
			|| ($("#password").val() == "")
			|| ($("#password").val() == null)
			|| ($("#stu-Wrong").text() == "该学号已被注册"
					|| ($("#code-Wrong").text() == "验证码错误") || (document
					.getElementById("submit").value == 0))) {
		swal("提示", "请填写完整信息!", "warning");
		return false;
	}

	// 两次输入密码不一致
	if ($("#password").val() != $("#repeatPassword").val()) {
		swal("提示", "密码输入不一致!", "warning");
		return false;
	} else {
		swal({
					title : "注册成功",
					text : "2秒后跳转主界面。",
					timer : 2000,
					showConfirmButton : false
				});
		
		setTimeout(function() {
			true;
				// swal("Ajax请求完成！");
			}, 2000);
	}

}

//参数n为休眠时间，单位为毫秒:
function sleep(n) {
	var start = new Date().getTime();
	//  console.log('休眠前：' + start);
	while (true) {
		if (new Date().getTime() - start > n) {
			break;
		}
	}
	// console.log('休眠后：' + new Date().getTime());
}
