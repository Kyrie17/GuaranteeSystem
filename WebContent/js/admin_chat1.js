$(function() {
	var url2 = "http://localhost:8080/GuaranteeSystem/AdminChatServlet";
	  var $j = jQuery.noConflict(); //自定义一个比较短快捷方式
	  $j.post(url2,  function(data) {
				var str = "";
				var str2="";
				for(var k in data.data){
					str += "<div class='student '><img src='/GuaranteeSystem/imgs/form.jpg' style='width: 30px;height: 30px;'><span>"+data.data[k].username+"</span></div>";
					 str2+="<div class='chat_con' style='display: ;'><span>"+Math.random()+"</span></div>"	;				
				}
				document.getElementById("chat_room_middle").innerHTML=str;
				document.getElementById("center-info").innerHTML=str2;
					});
});