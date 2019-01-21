$(function() {

	var roomid = getCookie("roomid"); //房间名
	var roomid1 = getCookie("roomid") + "!";
	var nickname = getCookie("uname"); //自己的昵称
	var flag = 'join';
	var info = flag + '|' + roomid + '|' + nickname;
	var socket;
	//建立一条与服务器的连接
	var socket = new WebSocket(
			"ws://localhost:8080/GuaranteeSystem/websocket?flag=" + flag
					+ "&roomid*=" + roomid1 + "&nickname_=" + nickname);
	//var url = "ws://localhost:8080/InformSystemDemo/chatSocket"; 
	//进入聊天页面就是一个通信管道

	var text = '';
	var welcome = JSON.stringify({ //加入房间时的欢迎消息
		nickname : nickname, //用户名
		content : text, //消息内容
		target : roomid, //推送到目标房间
		flag : "chatroom"
	});

	var exitroom = JSON.stringify({ //退出房间
		nickname : nickname,
		flag : "exitroom",
		roomid : roomid
	})
	//接收服务器的消息
	socket.onmessage = function(ev) {
		var obj = eval('(' + ev.data + ')');
		addMessage(obj)
	};
	//当服务端执行onopen后触发此方法
	socket.onopen = function() {
		socket.send(welcome);
	};
	//发送按钮被点击时
	$(".ensure button").click(function() {
		ensure();
	});

	$("body").keyup(function(event) { //监听回车键
		if (event.keyCode == "13") { //keyCode=13是回车键
			$(".ensure button").trigger("click");
		}
	});

	function ensure() {
		//获取输入框内容
		var txt = document.getElementById("center_input").value;
		if (txt = '') {
			alert("不能发送空内容");
		} else {
			//构建一个标准格式的JSON对象
			var obj = JSON.stringify({
				nickname : nickname, //用户名
				content : document.getElementById("center_input").value, //消息内容
				flag : 'chatroom', //标识--chatroom代表是聊天室的消息
				target : roomid
			//消息推送的目的地
			});
			//向服务器发送消息
			socket.send(obj);
			//清空消息输入框
			document.getElementById("center_input").value = ""
			//消息输入框获取焦点
			$("center-input").focus();
		}
	}
	function addMessage(msg) {
		if (msg.isSelf && msg.content == "") { //消息是发送给自己发送的，并且内容为空
			$(".center-info").append("<div calss='welcome'>欢迎你进入聊天</div>");
			//refreshMember(msg.uname);            //刷新成员
		}
		if (!msg.isSelf && msg.content == "") { //消息是别人发送的，并且内容为空
			$(".center-info").append(
					"<div class='welcome'>欢迎管理员" + msg.nickname + "加入聊天</div>");
			//refreshMember(msg.uname);            //刷新成员
		}
		if (!msg.content == "") { //内容不为空时
			var align;
			if (msg.isSelf) {
				align = "right";
			} else {
				align = "left";
			}
			$(".center-info")
					.append(
							"<div class='basicInfo' style='float:"
									+ align
									+ ";width:100%;margin-bottom:10px;'>"
									+ "<div class='basicInfo-left' style=float:"
									+ align
									+ ">"
									+ "<img src='/GuaranteeSystem/imgs/touxiang.jpg' width=50 height=50 style='border-radius:100%;padding:5px;'>"
									+ "</div>"
									+ "<div class='basicInfo-right' style=float:"
									+ align
									+ ">"
									+ "<div class='username' style='text-align:"
									+ align + ";color:#ccc;font-size:14px;padding-top:10px;'>"
									+ "<span>" + msg.nickname + "</span> "
									+ "<span>" + msg.date + "</span>"
									+ "</div>" + "<div class='context'  style='padding:3px 5px;border-radius:10px;background-color:#efe6f7;'>"
									+ "<span style='font-size:15px;'>" + msg.content + "</span>"
									+ "</div>" + "</div>" + "</div>");
		}
		if (msg.flag == "exitroom") {
			$(".center-info").append(
					"<div class='welcome'>" + msg.message + "</div>");
			//刷新成员列表
			//refreshMember(msg.uname)
		}
		$(".center-info").scrollTop(999999); //让滚动条始终保持在最下
	}

	$(".exitroom").click(function() { //退出房间
		socket.send(exitroom); //像服务器发出退出房间的信号
		location.href = "index.html";
	})

})
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