window.onload = function() {
	// 获取对象并注册失去焦点监听
	var student_id = document.getElementById("stu_id");
	var login_password = document.getElementById("password");
	var mess = document.getElementById("code");
	var submit = document.getElementById("login_submit");
	// 判断学号是否为空
	student_id.onblur = function() {
		if (student_id.value == null || student_id.value == "") {
			document.getElementById("id_wrong").innerHTML = "学号不能为空";
		} else {
			document.getElementById("id_wrong").innerHTML = "";
		}

	};
	// 判断密码是否为空
	login_password.onblur = function() {
		if (login_password.value == null || login_password.value == "") {
			document.getElementById("pass_Wrong").innerHTML = "密码不能为空";
		} else {
			document.getElementById("pass_Wrong").innerHTML = "";
		}
	};
	// 判断验证码是否为空
	mess.onblur = function() {
		if (mess.value == null || mess.value == "") {
			document.getElementById("mess_wrong").innerHTML = "验证码不能为空";
		} else {
			document.getElementById("mess_wrong").innerHTML = "";
		}
	};

	// 判断是否点击登录按钮
	submit.onclick = function() {
		var url = "/GuaranteeSystem/ImageServlet";
		var args = {
			"check" : 1,
			"mess" : mess.value
		};
		$.post(url, args, function(data) {
					if (data.msg == "验证码错误") {
						swal("提示", "信息填写有误!", "warning");
					} else {
						var url2 = "/GuaranteeSystem/LoginServlet";
						var args2 = {
							"student_id" : student_id.value,
							"login_password" : login_password.value,
							"mess" : mess.value
						};
						$.post(url2, args2, function(data) {
									if (data.msg == "登录成功") {
										Setcookie ("cookie", data.data);
										//document.cookie = data.data;
										swal({
													title : "登录成功",
													text : "2秒后跳转登录界面。",
													timer : 2000,
													showConfirmButton : false
												});
										setTimeout(function() {
											window.location.href = "/GuaranteeSystem/html/HomePage.html";
												// swal("Ajax请求完成！");
										}, 2000);

									}
									if (data.msg == "信息填写不正确") {
										swal("提示", "信息填写不正确!", "warning");
									}
									if (data.msg == "请登录") {
										swal("提示", "请登录!", "warning");
									}

								});
					}

				});

	}
	// 检验登录信息是否填写正确
	setTimeout(function() {
				if (($("#mess_wrong").text() == "验证码错误")) {
					window.location.href = "Login.html";
				}
				if (($("#mess_wrong").text() != "验证码错误")) {
				}
			}, 20);

}

// 验证码看不清楚
function change() {
	var t = new Date().getTime();
	document.getElementById("checkimgcode").src = "/GuaranteeSystem/ImageServlet?t="
			+ t;
}




function Setcookie (name, value){ 
    //设置名称为name,值为value的Cookie
    var expdate = new Date();   //初始化时间
    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);   //时间单位毫秒
    document.cookie = name+"="+value+";expires="+expdate.toGMTString()+";path=/";
 
   //即document.cookie= name+"="+value+";path=/";  时间默认为当前会话可以不要，但路径要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！
}

