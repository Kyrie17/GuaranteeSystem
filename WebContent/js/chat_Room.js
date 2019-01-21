function $(id) {
	return document.getElementById(id);
}
window.onload = function() {
	var $j = jQuery.noConflict(); // 自定义一个比较短快捷方式
	$j.ajaxSetup (
			   {
			    async: false
			   }); 
	var url2 = "http://localhost:8080/GuaranteeSystem/AdminChatServlet";

	var async=false;
	$j
			.post(
					url2,
					function(data) {
						var str = "";
						var str2 = "";
						var str3="";
						for ( var k in data.data) {
							str += "<div class='student' id='"+data.data[k].roomid+"'><img src='/GuaranteeSystem/imgs/form.jpg' style='width: 30px;height: 30px;'><span>"
									+ data.data[k].username + "</span><button class='exitroom' type='button'>删除</button></div>";
							str2 += "<div class='chat_con' style='display:none ;' id='"+data.data[k].roomid+"chat'><span>"
									 + "</span></div>";
							str3 +="<div class='center_input' id='center_input' style='display: none;'><textarea  id='"+data.data[k].roomid+"area'></textarea></div>";
						}
						document.getElementById("chat_room_middle").innerHTML = str;
						document.getElementById("center-info").innerHTML = str2;
						document.getElementById("next-info").innerHTML = str3;
					});

	var oA = $("chat_box").getElementsByClassName("student");
	var oDiv = document.getElementById("center-info").getElementsByClassName(
			"chat_con");
	 var oInput=document.getElementById("next-info").getElementsByClassName("center_input");
	for (var i = 0; i < oA.length; i++) {
		oA[i].index = i;
		oA[i].onclick = function() {
			for (var n = 0; n < oA.length; n++) {
				oA[n].className = "student";
			}
			this.className = "student show";
			Setcookie("roomid",this.id);
			for (var j = 0; j < oDiv.length; j++) {
				 oDiv[j].style.display="none";
	             oInput[j].style.display="none";
			}
			 oDiv[this.index].style.display="block";
	           oInput[this.index].style.display="block";
			var roomid = getCookie("roomid"); // 房间名
			var roomid1 = getCookie("roomid") + "!";
			var nickname = "Admin"//getCookie("uname"); // 自己的昵称
			var flag = 'join';
			var info = flag + '|' + roomid + '|' + nickname;
			var socket;
			// 建立一条与服务器的连接
			 socket = new WebSocket("ws://localhost:8080/GuaranteeSystem/websocket?flag="
					+ flag + "&roomid*=" + roomid1 + "&nickname_=" + nickname);
			// var url = "ws://localhost:8080/InformSystemDemo/chatSocket";
			// 进入聊天页面就是一个通信管道

			var text = '';
			var welcome = JSON.stringify({ // 加入房间时的欢迎消息
				nickname : nickname, // 用户名
				content : text, // 消息内容
				target : roomid, // 推送到目标房间
				flag : "chatroom"
			});

			var exitroom = JSON.stringify({ // 退出房间
				nickname : nickname,
				flag : "exitroom",
				roomid : roomid
			})
			// 接收服务器的消息
			socket.onmessage = function(ev) {
				var obj = eval('(' + ev.data + ')');
				addMessage(obj)
			};
			// 当服务端执行onopen后触发此方法
			socket.onopen = function() {
				//socket.send(welcome);
			};
			//服务端执行close执行此方法
			socket.onclose = function() {
			};
			// 发送按钮被点击时
			$j(".ensure button").click(function() {
				ensure();
			});

			$j("body").keyup(function(event) { // 监听回车键
				if (event.keyCode == "13") { // keyCode=13是回车键
					$(".ensure button").trigger("click");
				}
			});

			function ensure() {
				// 获取输入框内容
				var txt = document.getElementById(getCookie("roomid")+"area").innerHTML;
				if (txt = '') {
					alert("不能发送空内容");
				} else {
					// 构建一个标准格式的JSON对象
					var obj = JSON.stringify({
						nickname : nickname, // 用户名
						content : document.getElementById(getCookie("roomid")+"area").value, // 消息内容
						flag : 'chatroom', // 标识--chatroom代表是聊天室的消息
						target : roomid
					// 消息推送的目的地
					});
					// 向服务器发送消息
					socket.send(obj);
					// 清空消息输入框
					document.getElementById(getCookie("roomid")+"area").value="";
					// 消息输入框获取焦点
					$(getCookie("roomid")+"area").focus();
				}
			}
			
			function addMessage(msg) {
				if (msg.isSelf && msg.content == "") { // 消息是发送给自己发送的，并且内容为空
				}
				if (!msg.isSelf && msg.content == "") { // 消息是别人发送的，并且内容为空
					
				}
				if (!msg.content == "") { // 内容不为空时
					var align;
					if (msg.isSelf) {
						align = "right";
					} else {
						align = "left";
					}
					var roomid_chat=getCookie("roomid")+"chat";
					$j("#"+roomid_chat)
							.append(
									"<div class='basicInfo' style='float:"+align+";width:100%;margin-bottom:10px;'>"+
									"<div class='basicInfo-left' style=float:"+align+">"+
										"<img src='/GuaranteeSystem/imgs/touxiang.jpg' width=50px height=50px style='border-radius:100%;padding:5px;' >"+
									"</div>"+
									"<div class='basicInfo-right' style=float:"+align+">"+
										"<div class='username' style='text-align:"+align+";color:#ccc;font-size:14px;padding-top:10px;'>"+
											"<span style='color:#aaa;'>"+msg.nickname+"</span> "+
											"<span style='color:#aaa;'>"+msg.date+"</span>"+
										"</div>"+
										"<div class='context' style='padding:3px 5px;border-radius:10px;background-color:#efe6f7;'>"+
											"<span  style='font-size:15px;'>"+
												msg.content+
											"</span>"+
										"</div>"+
									"</div>"+
								"</div>"
							); 
				}
				if (msg.flag == "exitroom") {
					$j(".center-info").append(
							"<div class='welcome'>" + msg.message + "</div>");
					// 刷新成员列表
					// refreshMember(msg.uname)
				}
				$j(".center-info").scrollTop(999999); // 让滚动条始终保持在最下
			}

			$j(".exitroom").click(function() { // 退出房间
				socket.send(exitroom); // 像服务器发出退出房间的信号
				var url2 = "http://localhost:8080/GuaranteeSystem/AdminChatServlet";
				var arg={
						"delete":"delete",
						"roomid":getCookie("roomid")
				}
				var $j = jQuery.noConflict(); // 自定义一个比较短快捷方式
				$j
						.post(
								url2,arg,
								function(data) {
									var str = "";
									var str2 = "";
									var str3="";
									for ( var k in data.data) {
										str += "<div class='student' id='"+data.data[k].roomid+"'><img src='/GuaranteeSystem/imgs/form.jpg' style='width: 30px;height: 30px;'><span>"
												+ data.data[k].username + "</span></div>";
										str2 += "<div class='chat_con' style='display:none ;' id='"+data.data[k].roomid+"chat'><span>"
												 + "</span></div>";
										str3 +="<div class='center_input' id='center_input' style='display: none;'><textarea  id='"+data.data[k].roomid+"area'></textarea></div>";
									}
									document.getElementById("chat_room_middle").innerHTML = str;
									document.getElementById("center-info").innerHTML = str2;
									document.getElementById("next-info").innerHTML = str3;
									socket.close();
									a();
								});

				
			})
		}
	}
	

	
	

}



function Setcookie (name, value){ 
    //设置名称为name,值为value的Cookie
    var expdate = new Date();   //初始化时间
    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);   //时间单位毫秒
    document.cookie = name+"="+value+";expires="+expdate.toGMTString()+";path=/";
 
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
function a(){
	var $j = jQuery.noConflict(); // 自定义一个比较短快捷方式
	var oA = $("chat_box").getElementsByClassName("student");
	var oDiv = document.getElementById("center-info").getElementsByClassName(
			"chat_con");
	 var oInput=document.getElementById("next-info").getElementsByClassName("center_input");
	for (var i = 0; i < oA.length; i++) {
		oA[i].index = i;
		oA[i].onclick = function() {
			for (var n = 0; n < oA.length; n++) {
				oA[n].className = "student";
			}
			this.className = "student show";
			Setcookie("roomid",this.id);
			for (var j = 0; j < oDiv.length; j++) {
				 oDiv[j].style.display="none";
	             oInput[j].style.display="none";
			}
			 oDiv[this.index].style.display="block";
	           oInput[this.index].style.display="block";
			var roomid = getCookie("roomid"); // 房间名
			var roomid1 = getCookie("roomid") + "!";
			var nickname = "Admin"//getCookie("uname"); // 自己的昵称
			var flag = 'join';
			var info = flag + '|' + roomid + '|' + nickname;
			var socket;
			// 建立一条与服务器的连接
			 socket = new WebSocket("ws://localhost:8080/GuaranteeSystem/websocket?flag="
					+ flag + "&roomid*=" + roomid1 + "&nickname_=" + nickname);
			// var url = "ws://localhost:8080/InformSystemDemo/chatSocket";
			// 进入聊天页面就是一个通信管道

			var text = '';
			var welcome = JSON.stringify({ // 加入房间时的欢迎消息
				nickname : nickname, // 用户名
				content : text, // 消息内容
				target : roomid, // 推送到目标房间
				flag : "chatroom"
			});

			var exitroom = JSON.stringify({ // 退出房间
				nickname : nickname,
				flag : "exitroom",
				roomid : roomid
			})
			// 接收服务器的消息
			socket.onmessage = function(ev) {
				var obj = eval('(' + ev.data + ')');
				addMessage(obj)
			};
			// 当服务端执行onopen后触发此方法
			socket.onopen = function() {
				//socket.send(welcome);
			};
			//服务端执行close执行此方法
			socket.onclose = function() {
			};
			// 发送按钮被点击时
			$j(".ensure button").click(function() {
				ensure();
			});

			$j("body").keyup(function(event) { // 监听回车键
				if (event.keyCode == "13") { // keyCode=13是回车键
					$(".ensure button").trigger("click");
				}
			});

			function ensure() {
				// 获取输入框内容
				var txt = document.getElementById(getCookie("roomid")+"area").innerHTML;
				if (txt = '') {
					alert("不能发送空内容");
				} else {
					// 构建一个标准格式的JSON对象
					var obj = JSON.stringify({
						nickname : nickname, // 用户名
						content : document.getElementById(getCookie("roomid")+"area").value, // 消息内容
						flag : 'chatroom', // 标识--chatroom代表是聊天室的消息
						target : roomid
					// 消息推送的目的地
					});
					// 向服务器发送消息
					socket.send(obj);
					// 清空消息输入框
					document.getElementById(getCookie("roomid")+"area").value;
					// 消息输入框获取焦点
					$(getCookie("roomid")+"area").focus();
				}
			}
			
			function addMessage(msg) {
				if (msg.isSelf && msg.content == "") { // 消息是发送给自己发送的，并且内容为空
				}
				if (!msg.isSelf && msg.content == "") { // 消息是别人发送的，并且内容为空
				}
				if (!msg.content == "") { // 内容不为空时
					var align;
					if (msg.isSelf) {
						align = "right";
					} else {
						align = "left";
					}
					var roomid_chat=getCookie("roomid")+"chat";
					$j("#"+roomid_chat)
							.append(
									"<div class='basicInfo' style='float:"+align+";width:100%;margin-bottom:10px;'>"+
									"<div class='basicInfo-left' style=float:"+align+">"+
										"<img src='/GuaranteeSystem/imgs/touxiang.jpg' width=50px height=50px style='border-radius:100%;padding:5px;' >"+
									"</div>"+
									"<div class='basicInfo-right' style=float:"+align+">"+
										"<div class='username' style='text-align:"+align+";color:#ccc;font-size:14px;padding-top:10px;'>"+
											"<span style='color:#aaa;'>"+msg.nickname+"</span> "+
											"<span style='color:#aaa;'>"+msg.date+"</span>"+
										"</div>"+
										"<div class='context' style='padding:3px 5px;border-radius:10px;background-color:#efe6f7;'>"+
											"<span  style='font-size:15px;'>"+
												msg.content+
											"</span>"+
										"</div>"+
									"</div>"+
								"</div>"
							); 
				}
				if (msg.flag == "exitroom") {
					$j(".center-info").append(
							"<div class='welcome'>" + msg.message + "</div>");
					// 刷新成员列表
					// refreshMember(msg.uname)
				}
				$j(".center-info").scrollTop(999999); // 让滚动条始终保持在最下
			}

			$j(".exitroom").click(function() { // 退出房间
				socket.send(exitroom); // 像服务器发出退出房间的信号
				var url2 = "http://localhost:8080/GuaranteeSystem/AdminChatServlet";
				var arg={
						"delete":"delete",
						"roomid":getCookie("roomid")
				}
				var $j = jQuery.noConflict(); // 自定义一个比较短快捷方式
				$j
						.post(
								url2,arg,
								function(data) {
									var str = "";
									var str2 = "";
									var str3="";
									for ( var k in data.data) {
										str += "<div class='student' id='"+data.data[k].roomid+"'><img src='/GuaranteeSystem/imgs/form.jpg' style='width: 30px;height: 30px;'><span>"
												+ data.data[k].username + "</span><button class='exitroom' type='button'>删除</button></div>";
										str2 += "<div class='chat_con' style='display:none ;' id='"+data.data[k].roomid+"chat'><span>"
												 + "</span></div>";
										str3 +="<div class='center_input' id='center_input' style='display: none;'><textarea  id='"+data.data[k].roomid+"area'></textarea></div>";
									}
									document.getElementById("chat_room_middle").innerHTML = str;
									document.getElementById("center-info").innerHTML = str2;
									document.getElementById("next-info").innerHTML = str3;
									socket.close();
								});

				
			})
		}
	}
	

	
	


}