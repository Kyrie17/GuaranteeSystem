window.onload = function() {
	$.ajaxSettings.async = false;
	var url = "http://localhost:8080/GuaranteeSystem/GetAllCommidityServlet";
	$.post(url,function(data){
		var str = "";
		for(var k in data.data){
			str += "<tr><th id='"+data.data[k].id+"'>" + data.data[k].id + "</th>"
			+ "<th id='"+data.data[k].id+"name'>" + data.data[k].name + "</th>"
			+ "<th id='"+data.data[k].id+"description'>" + data.data[k].description + "</th>"
			+ "<th id='"+data.data[k].id+"price'>" + data.data[k].price + "</th>"
			+ "<th><button id='" + data.data[k].id + "' onclick=SubmitCommidity('"+data.data[k].name+"',"+data.data[k].id+",'"+data.data[k].description+"',"+data.data[k].price+",'"+"') >添加至购物车</button></th>"
			+  "</th></tr>";
		}
		$("#commidity").html(str);
		
		/*alert($("*[name='add']").attr('id'));
		add.onclick=function(){
			var commidity_id=add.id;
			alert(commidity_id);
			var name=document.getElementById(commidity_id+"name");
			var description=document.getElementById(commidity_id+"description");
			var price=document.getElementById(commidity_id+"price");
			var num=document.getElementById(commidity_id+"num");
		}*/
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
function SubmitCommidity(name,id,description,price) {
	var commidity_name=name;
	var commidity_id=id;
	var commidity_description=description;
	var commidity_price=price;
	
	var url = "http://localhost:8080/GuaranteeSystem/PurchaseCommidityServlet";
	var arg={
			"commidity_name":commidity_name,
			"commidity_id":commidity_id,
			"commidity_description":commidity_description,
			"commidity_price":commidity_price
	}
	$.post(url,arg,function(data){
		alert(data.msg);
		if(data.msg=="添加购物车成功"){
			swal("提示", "添加购物车成功", "success");
		}else{
			swal("提示", "添加购物车失败", "warning");
		}
});
}

