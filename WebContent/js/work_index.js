
	
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
	
	$(document).ready(function () {  
	    Filter();  
	});

	var orderNumber = "1";
	
	function Filter(){
		
		var currentPage = document.getElementById("currentPage").innerHTML;
		if(currentPage == "" || currentPage == "0"){
			currentPage = 1;
		}
		var url = "http://localhost:8080/GuaranteeSystem/GetRepairManRFServlet";
		var args = {"time":new Date(),"username":"卓泰","pageNum":currentPage,"judgeState":2,"userConfirm":0};
		$.getJSON(url, args, function(data){
			var str = "";

			for(var k in data.data.dataList){
				//将数据库中保存的报修类型参数，转换为文字
				var serType = "";
				switch(data.data.dataList[k].serType)
				{
				case 1:
				  serType = "水";
				  break;
				case 2:
				  serType = "木";
				  break;
				case 3:
				  serType = "电";
				  break;
				case 4:
				  serType = "其他";
				  break;
				}
				//将数据库中保存的报修类型参数，转换为文字
				var judgeState = "";
				switch(data.data.dataList[k].judgeState)
				{
				case -1:
				  judgeState = "未审核";
				  break;
				case 1:
					judgeState = "已审核";
				  break;
				case 2:
					judgeState = "已完成";
				  break;
				}
				
				str += "<tr><th id='serAdd' name='serAdd'>"+ data.data.dataList[k].serAdd + "</th>"				
				+ "<th id='serTime' name='serTime'>" + data.data.dataList[k].serTime + "</th>"
				+ "<th id='serInform' name='serInform'>" + data.data.dataList[k].serInform + "</th>"
				+ "<th class='change' title='点击改变状态'><button title='点击改变状态' id='" + data.data.dataList[k].orderNumber + "' onclick='orderNumber=" + data.data.dataList[k].orderNumber + ";changeJudgeState()'>" + judgeState + "</button></th>"
				+ "<th class='con_tit_scan' id='con_tit_scan'><a href='#' onclick='orderNumber=" + data.data.dataList[k].orderNumber + ";showPersonalRF()'>查看更多</a></th>";
			}
			$("#content").html(str);
			$("#currentPage").text(data.data.currentPage);
			$("#totalRecord").text(data.data.totalRecord);
			$("#totalPage").text(data.data.totalPage);
		}); 
	}
	
	
	
function showPersonalRF(){
	var url = "http://localhost:8080/GuaranteeSystem/ShowPerInforServlet";
	var args = {"time":new Date(),"orderNumber":orderNumber};
	$.getJSON(url, args, function(data){
		var str = "";
		
		for(var k in data.data){
			
			//将数据库中保存的报修类型参数，转换为文字
			var serType = "";
			switch(data.data[k].serType)
			{
			case 1:
			  serType = "水";
			  break;
			case 2:
			  serType = "木";
			  break;
			case 3:
			  serType = "电";
			  break;
			case 4:
			  serType = "其他";
			  break;
			}
			//将数据库中保存的报修类型参数，转换为文字
			var judgeState = "";
			switch(data.data[k].judgeState)
			{
			case -1:
			  judgeState = "未审核";
			  break;
			case 1:
				judgeState = "已审核";
			  break;
			case 2:
				judgeState = "已完成";
			  break;
			}
//			$("#r_ordernumber").text(data.data[k].orderNumber);
			$("#s_id").text(data.data[k].username);
			$("#s_phone").text(data.data[k].phone);
//			$("#r_sertype").text(serType);
			$("#r_seradd").text(data.data[k].serAdd);  
			var time = data.data[k].serTime + "&nbsp;&nbsp;&nbsp;&nbsp;" + data.data[k].detailTime;
			$("#r_sertime").html(time);
			$("#r_serinform").text(data.data[k].serInform);
			$("#change_repair").text(judgeState);
			$("#a_id").text(data.data[k].repairMan);
			$("#img").attr("src","/file/" + data.data[k].file_path);
		}
		
	});
	
	var modal = document.getElementById('open_repair');
    // 打开弹窗的按钮对象
    var btn = document.getElementById("show_bill");
    // 获取 <span> 元素，用于关闭弹窗
    var span = document.getElementById("comfirm");
    
    modal.style.display = "block";
    
     
    span.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}

function ChangeUserConfirm(){

	var on = document.getElementById("r_ordernumber").innerHTML;
		
	var url = "http://localhost:8080/GuaranteeSystem/ChangeUserconfirmServlet";
	var args = {"time":new Date(),"orderNumber":on};
	$.getJSON(url, args, function(data){
		
		if(data.code == 1){
			swal({
				title : data.msg,
				text : "2秒后跳转回个人中心。",
				timer : 2000,
				showConfirmButton : false
			});
			setTimeout(function() {
				window.location.href = "/GuaranteeSystem/html/person_finish.html";
			}, 2000);
		}else{
			swal("提示", data.msg, "warning");
		}
	});
}


//点击改变审核状态的按钮
function changeJudgeState(){
	
	if(document.getElementById(orderNumber).innerHTML == "未审核"){
		var url = "http://localhost:8080/GuaranteeSystem/ChangeJudgeStateServlet";
		var args = {"time":new Date(),"orderNumber":orderNumber,"judgeState":-1};
		$.getJSON(url, args, function(data){
			
			if(data.code == 1){
				document.getElementById(orderNumber).innerHTML = "已审核";
				swal("成功", data.msg, "success");
			}else{
				swal("提示", data.msg, "warning");
			}
			
		});
	}else if(document.getElementById(orderNumber).innerHTML == "已审核"){
		var url = "http://localhost:8080/GuaranteeSystem/ChangeJudgeStateServlet";
		var args = {"time":new Date(),"orderNumber":orderNumber,"judgeState":1};
		$.getJSON(url, args, function(data){
			if(data.code == 1){
				document.getElementById(orderNumber).innerHTML = "已完成";
				swal("成功", data.msg, "success");
			}else{
				swal("提示", data.msg, "warning");
			}
		});
	}else{
		document.getElementById(orderNumber).disable = true;
		}	
}



//在报修单详情中点击改变审核状态的按钮
function changeJudgeState1(){
	
	if(document.getElementById(orderNumber).innerHTML == "未审核"){
		var url = "http://localhost:8080/GuaranteeSystem/ChangeJudgeStateServlet";
		var args = {"time":new Date(),"orderNumber":orderNumber,"judgeState":-1};
		$.getJSON(url, args, function(data){
			
			if(data.code == 1){
				document.getElementById("change_repair").innerHTML = "已审核";
				swal({
					title : "操作成功",
					text : "2秒后跳转回首页。",
					timer : 2000,
					showConfirmButton : false
				});
				setTimeout(function() {
					window.location.href = "/GuaranteeSystem/html/work_index.html";
				}, 2000);
			}else{
				swal("提示", data.msg, "warning");
			}
			
		});
	}else if(document.getElementById(orderNumber).innerHTML == "已审核"){
		var url = "http://localhost:8080/GuaranteeSystem/ChangeJudgeStateServlet";
		var args = {"time":new Date(),"orderNumber":orderNumber,"judgeState":1};
		$.getJSON(url, args, function(data){
			if(data.code == 1){
				document.getElementById("change_repair").innerHTML = "已完成";
				swal({
					title : "操作成功",
					text : "2秒后跳转回首页。",
					timer : 2000,
					showConfirmButton : false
				});
				setTimeout(function() {
					window.location.href = "/GuaranteeSystem/html/work_index.html";
				}, 2000);
			}else{
				swal("提示", data.msg, "warning");
			}
		});
	}else{
		document.getElementById(orderNumber).disable = true;
		}	

}

