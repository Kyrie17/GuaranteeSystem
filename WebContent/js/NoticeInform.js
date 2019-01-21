
	
	// 第一页
	function firstPage(){
		document.getElementById("currentPage").innerHTML = "1";
		Filter();
	}
	
	// 下一页
	function nextPage(){
		var currentPage = document.getElementById("currentPage").innerHTML;
		var totalPage = document.getElementById("totalPage").innerHTML;
		if(currentPage < totalPage){
			var currentPage = parseInt(document.getElementById("currentPage").innerHTML) + 1;
			document.getElementById("currentPage").innerHTML = "" + currentPage;
			Filter();
		}else{
			swal("提示", "这是最后一页!", "warning");
		}
	}
	
	// 上一页
	function previousPage(){
		var currentPage = document.getElementById("currentPage").innerHTML;
		if(currentPage > "1"){
			var currentPage = parseInt(document.getElementById("currentPage").innerHTML) - 1;
			document.getElementById("currentPage").innerHTML = "" + currentPage;
			Filter();
		}else{
			swal("提示", "这是第一页!", "warning");
		}
	}
	
	// 尾页
	function lastPage(){
		document.getElementById("currentPage").innerHTML = document.getElementById("totalPage").innerHTML;
		Filter();
	}
	
	$(document).ready(function () {  
	    Filter();  
	});

	function Filter(){
		var currentPage = document.getElementById("currentPage").innerHTML;
		if(currentPage == ""){
			currentPage = 1;
		}
		var url = "http://localhost:8080/GuaranteeSystem/ShowNoticeServlet";
		var args = {"pageNum":currentPage};
		$.post(url, args, function(data){
				var str = "";
			for(var k in data.data.dataList){
				str += "<tr><td align='center'>" + data.data.dataList[k].fileName + "</th>"
				+ "<th>" + data.data.dataList[k].upLoadDate + "</th>"
				+ "<th>" + data.data.dataList[k].downLoadNum + "</th>"
				+ "<th> <a href='/GuaranteeSystem/LoadFileServlet?fileName="+data.data.dataList[k].fileName+"'><img src='/GuaranteeSystem/imgs/download.png' width='20' height='20'></a>"
			+  "</th></tr>";
			}
			$("#content").html(str);
			$("#currentPage").text(data.data.currentPage);
			$("#totalRecord").text(data.data.totalRecord);
			$("#totalPage").text(data.data.totalPage);
		}); 
	}
	
	