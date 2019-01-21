window.onload = function() {
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	var login = document.getElementById("worker_login");
	
	//判断用户名是否为空
	username.onblur = function() {
		if (username.value == null || username.value == "") {
			document.getElementById("username_wrong").innerHTML = "学号不能为空";
		} else {
			document.getElementById("username_wrong").innerHTML = "";
		}
	};

	//判断密码是否为空
	password.onblur = function() {
		if (password.value == null || password.value == "") {
			document.getElementById("password_wrong").innerHTML = "密码不能为空";
		} else {
			document.getElementById("password_wrong").innerHTML = "";
		}
	};

	// 判断是否点击登录按钮
	login.onclick = function() {
		var url = "/GuaranteeSystem/WorkerLoginServlet";
		var args = {
			"username" : username.value,
			"password" : password.value
		};
		$.post(url, args, function(data) {
					if (data.msg == "登录成功") {
						Setcookie ("worker_cookie", data.data);
						swal({
									title : "登录成功",
									text : "2秒后跳转登录界面。",
									timer : 2000,
									showConfirmButton : false
								});
						setTimeout(function() {
							window.location.href = "/GuaranteeSystem/html/work_index.html";
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

}


function Setcookie (name, value){ 
    //设置名称为name,值为value的Cookie
    var expdate = new Date();   //初始化时间
    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);   //时间单位毫秒
    document.cookie = name+"="+value+";expires="+expdate.toGMTString()+";path=/";
 
   //即document.cookie= name+"="+value+";path=/";  时间默认为当前会话可以不要，但路径要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！
}