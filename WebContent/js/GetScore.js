
	
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
			swal("提示", "已经是最后一页了哦!", "warning");
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
			swal("提示", "已经是第一页了哦!", "warning");
		}
	}
	
	// 尾页
	function lastPage(){
		document.getElementById("currentPage").innerHTML = document.getElementById("totalPage").innerHTML;
		Filter();
	}


	$().ready(function () {
		Filter();
	})
/**
 * 管理端获取所有报修评价
 */
	function Filter() { 
		
		var formElement = document.getElementById("########");
		var satisfied = formElement.satisfied.value;
		var classState = formElement.judgeState.value;
		var currentPage = document.getElementById("currentPage").innerHTML;
		if(currentPage == "" || currentPage == "0"){
			currentPage = 1;
		}
		var url = "http://localhost:8080/GuaranteeSystem/GetScoreServlet";
		var args = {"time":new Date(),"pageNum":currentPage,"satisfied":satisfied,"classState":classState};
		$.getJSON(url, args, function(data){
			var str = "";
			
			for(var k in data.data.dataList){
				//将数据库中保存的报修类型参数，转换为文字
				var score = "";
				
				
				
			}
			$("#content").html(str);
			$("#currentPage").text(data.data.currentPage);
			$("#totalRecord").text(data.data.totalRecord);
			$("#totalPage").text(data.data.totalPage);
		})	
	}