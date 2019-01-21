window.onload = function() {
	var custom = document.getElementById("custom");
	custom.onclick = function() {
		var myDate = new Date().getHours();
		var loginInform;
		alert(myDate);
		if (false) {//myDate<8||myDate>16
			alert("请在管理员上班时间进行咨询");
		} else {
			$.ajaxSettings.async = false;
			var url = "http://localhost:8080/GuaranteeSystem/GetStuIdServlet";
			$.post(url,  function(data) {
				if(data.msg=="获取学号成功"){
					loginInform=data.data;
				}else{
					location.href = "http://localhost:8080/GuaranteeSystem/html/HomePage.html";
				}
			});

			alert(loginInform);
			var url2 = "http://localhost:8080/GuaranteeSystem/StudentChatServlet";
			var args2 = {
				"username" : loginInform
			};
			$.post(url2, args2, function(data) {
				if (data.msg == "添加房间成功") {
					Setcookie("uname", loginInform);
					Setcookie("roomid", data.data);
					location.href = "chat_Room_stu.html";
				} else {
					alert("请等待");
				}
			});
		}

	}
}
function Setcookie(name, value) {
	//设置名称为name,值为value的Cookie
	var expdate = new Date(); //初始化时间
	expdate.setTime(expdate.getTime() + 30 * 60 * 1000); //时间单位毫秒
	document.cookie = name + "=" + value + ";expires=" + expdate.toGMTString()
			+ ";path=/";

	//即document.cookie= name+"="+value+";path=/";  时间默认为当前会话可以不要，但路径要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！
}
function getCookie(name) {
	var strcookie = document.cookie;//获取cookie字符串
	var arrcookie = strcookie.split("; ");//分割
	//遍历匹配
	for (var i = 0; i < arrcookie.length; i++) {
		var arr = arrcookie[i].split("=");
		if (arr[0] == name) {
			return arr[1];
		}
	}
	return "";
}