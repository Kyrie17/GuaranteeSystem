window.onload = function() {
	var url = "http://localhost:8080/GuaranteeSystem/ShowPerInformServlet";
	var args = {
		"token" : document.cookie.split(";")[0]
	};
	$.post(url, args, function(data) {
				var orderNumber = document.getElementById("r_ordernumber");
				var username = document.getElementById("s_id");
				var phone = document.getElementById("s_phone");
				var serType = document.getElementById("r_sertype");
				var serAdd = document.getElementById("r_seradd");
				var serInform = document.getElementById("r_serinform");
				var serTime = document.getElementById("r_sertime");
				var judgeState = document.getElementById("r_judgestate");
				var file_path = document.getElementById("r_filepath");
				var repairMan = document.getElementById("a_id");
				
				
				orderNumber.value=data.data[0].orderNumber;
				username.value=data.data[0].username;
				phone.value=data.data[0].phone;
				serType.value=data.data[0].serType;
				serAdd.value=data.data[0].serAdd;
				serInform.value=data.data[0].serInform;
				serTime.value=data.data[0].serTime;
				judgeState.value=data.data[0].judgeState;
				file_path.value=data.data[0].file_path;
				repairMan.value=data.data[0].repairMan;
			});
}