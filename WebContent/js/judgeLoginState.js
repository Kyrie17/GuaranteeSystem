
$().ready(function () { 
    	var photo=document.getElementById("photo");
    	var login=document.getElementById("login");
    	var loginInform=document.getElementById("loginInform");
    	var url = "http://localhost:8080/GuaranteeSystem/JudgeLoginState";
		var args = {
		"token" : getCookie("cookie")
	};
	$.post(url, args, function(data) {
				if(data.code=="1"){
				login.style.display="none";
				loginInform.innerHTML="欢迎&nbsp;<a href='http://localhost:8080/GuaranteeSystem/html/person.html' style='color:#5c307d;text-decoration:underline;font-size:18px;'>" + data.data + "</a>&nbsp;回来";
				}else{
					//login_span.innerHTML="";
					//var btn=document.getElementById("repair_btn");
					btn.style.display="none";
					//person_con.innerHTML="";
				}
			});

    	
});




function getCookie(name){
    //可以搜索RegExp和match进行学习
    var arr,reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
} 

