window.onload = function() {
	// 获取对象并注册失去焦点监听
	var stu_id = document.getElementById("stu_id");
	var phone = document.getElementById("phone");
	var mess = document.getElementById("mess");
	var password = document.getElementById("password");
	var repeatPassword = document.getElementById("repeatPassword");
	var submit = document.getElementById("submit1");
	var getCode = document.getElementById("getCode");
	var phone_Wrong = document.getElementById("phone-Wrong");
	phone_Wrong.innerText = null;
	// 判断学号是否为空
	stu_id.onblur = function() {
		if (stu_id.value == null || stu_id.value == "") {
			document.getElementById("stu-Wrong").innerHTML = "学号不能为空";
		} else {
			document.getElementById("stu-Wrong").innerHTML = null;
		}
		if (stu_id.value != null && stu_id.value != "") {
			var url = "http://localhost:8080/GuaranteeSystem/RegisterServlet";
			var args = {
				"stu_id" : stu_id.value,
				"action" : "check"
			};
			$.post(url, args, function(data) {
						if(data.message=="该学号已被注册"){
						var span = document.getElementById("stu-Wrong");
						document.getElementById("stu-Wrong").innerHTML = data.message;
						}else{
							var span = document.getElementById("stu-Wrong");
							document.getElementById("stu-Wrong").innerHTML = "";
							document.getElementById("submit").value=1;
						}
					});
		}
	};

	// 判断手机号是否为空且是否格式正确
	phone.onblur = function() {
		if (phone.value == null || phone.value == "") {
			document.getElementById("phone-Wrong").innerHTML = "手机号不能为空";
		} else {
			var regu = /^1[3|4|5|8][0-9]\d{4,8}$/;
			var re = new RegExp(regu);
			if (!re.test(phone.value)) {
				document.getElementById("phone-Wrong").innerHTML = "手机号码不符合要求";
			} else {
				document.getElementById("phone-Wrong").innerHTML = null;
			}
		}
	};

	// 判断验证码是否为空
	mess.onblur = function() {
		if (mess.value == null || mess.value == "") {
			document.getElementById("code-Wrong").innerHTML = "验证码不能为空";
		} else {
			document.getElementById("code-Wrong").innerHTML = null;
			var url = "http://localhost:8080/GuaranteeSystem/SendMessServlet";
			var args = {
				"check" : 1,
				"mess" : mess.value
			};
			$.post(url, args, function(data) {
						if(data.msg=="验证码错误"){
						document.getElementById("code-Wrong").innerHTML = data.msg;
						document.getElementById("submit").value=0;
						}else{
							document.getElementById("code-Wrong").innerHTML = "";
							document.getElementById("submit").value=1;
						}
					});
		}

	};

	// 判断密码是否为空
	password.onblur = function() {
		if (password.value == null || password.value == "") {
			document.getElementById("pass-Wrong").innerHTML = "密码不能为空";
		} else {
			document.getElementById("pass-Wrong").innerHTML = null;
		}
	};

	//检查验证码是否输入有误
	repeatPassword.onblur = function() {
		if (repeatPassword.value != password.value) {
			document.getElementById("repass-Wrong").innerHTML = "密码输入不一致";
		} else {
			document.getElementById("repass-Wrong").innerHTML = null;
		}
	};

};
// 倒计时
var countdown = 60;
function settime(val) {
	if (countdown == 0) {
		val.removeAttribute("disabled");
		val.value = "获取验证码";
		countdown = 60;
		return false;
	} else {
		val.setAttribute("disabled", true);
		val.value = "重新发送(" + countdown + ")";
		countdown--;
	}
	setTimeout(function() {
				settime(val);
			}, 1000);
}
// 判断手机号码是否输入正确，正确则请求发送验证码
function SendMess(val) {
	if (phone.value == null || phone.value == ""
			|| $("#phone-Wrong").text() == "手机号码不符合要求"
			|| $("#phone-Wrong").text() == "请输入手机号码"
			|| $("#phone-Wrong").text() == "请正确输入手机号") {
		document.getElementById("phone-Wrong").innerHTML = "请输入手机号码";
	} else {
		if ($("#phone-Wrong").text() == null) {
			swal("提示", "请正确输入手机号!", "warning");
		} else {
			/*ajax({
						url : "/GuaranteeSystem/SendMessServlet?phone="
								+ document.getElementById("phone").value,
						type : "json",
						method : "post",
						callback : function(data) {
							
						}
					});*/
			var url = "/GuaranteeSystem/SendMessServlet";
			var args = {
				"phone" : document.getElementById("phone").value
			};
			$.post(url, args, function(data) {
						settime(val);

					});
		}
	}

}

