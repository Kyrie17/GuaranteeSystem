window.onload = function() {
	/*// 获取对象并注册失去焦点监听
	var stu_id = document.getElementById("stu_id");
	var phone = document.getElementById("phone");
	var mess = document.getElementById("mess");
	var password = document.getElementById("password");
	var repeatPassword = document.getElementById("repeatPassword");
	var submit=document.getElementById("submit");
	// 判断学号是否为空
	stu_id.onblur = function() {
		if (stu_id.value == null || stu_id.value == "") {
			document.getElementById("stu-Wrong").innerHTML = "学号不能为空";
		} 
		else {
			document.getElementById("stu-Wrong").innerHTML = null;
		}
		if(stu_id.value != null && stu_id.value != "")
		{*/
		ajax({
			url : "/GuaranteeSystem/RegisterServlet?stu_id="+stu_id.value+"&action=check",
			type : "json",
			method : 'post',
			callback : function(data) {
				var span=document.getElementById("stu-Wrong");
				document.getElementById("stu-Wrong").innerHTML=data.msg;
			}
		}
		);

	};
	