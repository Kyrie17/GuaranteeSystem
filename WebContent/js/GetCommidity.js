window.onload = function() {
	$.ajaxSettings.async = false;
	var url = "http://localhost:8080/GuaranteeSystem/GetCommidityServlet";
	var deleteChart=document.getElementById("delete");
	$.post(url,function(data){
		var str = "";
		for(var k in data.data){
			str += "<tr><th id='"+data.data[k].commidity_name+"' class='buy_Name'>" + data.data[k].commidity_name + "</th>"
			+ "<th id='"+data.data[k].commidity_name+"name'>" + data.data[k].quantity + "</th>"
			+ "<th id='"+data.data[k].commidity_name+"description'>" + data.data[k].quantity*data.data[k].price + "</th>"
			+ "<th><button id='" + data.data[k].id + "' onclick=deleteCommidity('"+data.data[k].commidity_name+"') >删除</button></th>"
			+  "</th></tr>";
		}
		$("#commidity").html(str);
		var url2 = "http://localhost:8080/GuaranteeSystem/DeleteChartServlet";
		deleteChart.onclick=function(){
			$.post(url2,function(data){
				if(data.msg=="删除成功"){
					swal("提示", "删除成功", "success");
					var url = "http://localhost:8080/GuaranteeSystem/GetCommidityServlet";
					$.post(url,function(data){
						var str = "";
						for(var k in data.data){
							str += "<tr><th id='"+data.data[k].commidity_name+"'  class='buy_Name'>" + data.data[k].commidity_name + "</th>"
							+ "<th id='"+data.data[k].commidity_name+"name'>" + data.data[k].quantity + "</th>"
							+ "<th id='"+data.data[k].commidity_name+"description'>" + data.data[k].quantity*data.data[k].price + "</th>"
							+ "<th><button id='" + data.data[k].id + "' onclick=deleteCommidity('"+data.data[k].commidity_name+"') >删除</button></th>"
							+  "</th></tr>";
						}
						$("#commidity").html(str);
					});
				}else{
					swal("提示", "删除失败", "warning");
				}
			});
		}
	});
	
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
function Setcookie (name, value){ 
    //设置名称为name,值为value的Cookie
    var expdate = new Date();   //初始化时间
    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);   //时间单位毫秒
    document.cookie = name+"="+value+";expires="+expdate.toGMTString()+";path=/";
 
   //即document.cookie= name+"="+value+";path=/";  时间默认为当前会话可以不要，但路径要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！
}
function deleteCommidity(name) {
	var commidity_name=name;
	var url = "http://localhost:8080/GuaranteeSystem/DeleteCommidityServlet";
	var arg={
			"commidity_name":commidity_name
	}
	$.post(url,arg,function(data){
		if(data.msg=="删除成功"){
			swal("提示", "删除成功", "success");
			var url = "http://localhost:8080/GuaranteeSystem/GetCommidityServlet";
			$.post(url,function(data){
				var str = "";
				for(var k in data.data){
					str += "<tr><th id='"+data.data[k].commidity_name+"' class='buy_Name'>" + data.data[k].commidity_name + "</th>"
					+ "<th id='"+data.data[k].commidity_name+"name'>" + data.data[k].quantity + "</th>"
					+ "<th id='"+data.data[k].commidity_name+"description'>" + data.data[k].quantity*data.data[k].price + "</th>"
					+ "<th><button id='" + data.data[k].id + "' onclick=deleteCommidity('"+data.data[k].commidity_name+"') >删除</button></th>"
					+  "</th></tr>";
				}
				$("#commidity").html(str);
			});
		}else{
			swal("提示", "删除失败", "warning");
		}
});
}

