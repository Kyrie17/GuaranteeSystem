window.onload = function() {
	var url = "http://localhost:8080/GuaranteeSystem/ShowNoticeServlet";
	$.getJSON(url,function(data){
		var str = "";
		for(var k in data.data){
			str += "<tr><th>" + data.data[k].fileName + "</th>"
			+ "<th>" + data.data[k].upLoadDate_String + "</th>"
			+ "<th>" + data.data[k].downLoadNum + "</th>"
			+ "<td> <a href='/GuaranteeSystem/LoadFileServlet?fileName="+data.data[k].fileName+"'><img src='/GuaranteeSystem/imgs/1.jpg' width='20' height='20'></a>"
			+  "</th></tr>";
		}
		$("#repair_infor").html(str);
	});
}