
	$.ajaxSettings.async = false;
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
		var url = "http://localhost:8080/GuaranteeSystem/PersonalCenterServlet";
		var args = {"time":new Date(),"username":"123","pageNum":currentPage,"judgeState":-1,"userConfirm":0};
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
				
				str += "<tr><th id='orderNumber' name='orderNumber'>"+ data.data.dataList[k].orderNumber + "</th>"				
				+ "<th id='serType' name='serType'>" + serType + "</th>"
				+ "<th id='repair_state' name='repair_state'>" + judgeState + "</th>"
				+ "<th id='serTime' name='serTime'>" + data.data.dataList[k].serTime + "</th>"
				+ "<th id='serInform' name='serInform' class='serInform'><a href='#' id='show_bill' onclick='orderNumber=" + data.data.dataList[k].orderNumber + ";showPersonalRF()'>" + data.data.dataList[k].serInform + "</a></th></tr>";
			}
			//<a href='#' id='show_bill' onclick='showPersonalRF()'>" + data.data.dataList[k].serInform + "</a>
			$("#content").html(str);
			$("#currentPage").text(data.data.currentPage);
			$("#totalRecord").text(data.data.totalRecord);
			$("#totalPage").text(data.data.totalPage);
			
			
			
			
		});
		$(function(){
			//初始化地址
			initAddress();
			
			//更改区域后的操作
			$("select[name='area']").change(function(){
				var areaCode=$("select[name='area']").val();
				getBuild(areaCode);
			});
		});
		function initAddress(){
			var firstAreaCode;
			// ajax请求所有省份
			$.get("/GuaranteeSystem/GetDormitoryServlet", {
				method : "initArea"
			}, function(data) {
				for(var k in data.data){
					$("select[name='area']").append(
							"<option value='"+data.data[k].code+"'>" + data.data[k].name
									+ "</option>");
				}

				// 获取第一个区域的code
				firstAreaCode = data.data[0].code;
				// 根据第一个区域code获取对应城市列表
				getBuild(firstAreaCode);
			}, 'json');
		}

		//获取对应城市列表（里面包括获取第一个城市的区县列表）
		function getBuild(firstAreaCode) {
			

			// ajax请求所有市级单位
			$.get("/GuaranteeSystem/GetDormitoryServlet", {
				method : "getBuild",
				code : firstAreaCode
			}, function(data) {

				// 先清空城市下拉框
				$("select[name='build']").empty();
				for(var k in data.data){
					$("select[name='build']").append(
							"<option value='"+data.data[k].building+"'>" + data.data[k].building
									+ "</option>");
				}
				
				
			}, 'json');
		}
		var button = document.getElementById("Person_submit");
		var one=document.getElementById("one");
		var url = "/GuaranteeSystem/GetStuIdServlet";
		    $.post(url,  function(data) {
			if(data.code==-1){
				document.getElementById("stu_id").innerHTML="";
			}else{
				document.getElementById("stu_id").value=data.data;
			}
		}); 
		button.onclick=function(){
			var regu = /\d{3,4}$/;
//			var re = new RegExp(regu);
			if (($("#room").val() == "") || ($("#room").val() == null)||(document.getElementById("stu_id").value == "") || (document.getElementById("stu_id").value == null)) {
				swal("提示", "请填写完整信息!", "warning");
			}else if(!$("#room").val().match(regu)){
				swal("提示", "请填写正确的房间号!", "warning");
			}else{
				var url = "/GuaranteeSystem/PersonInformServlet";
				var args = {
					"room" : $("#room").val(),
					"sex":$('input[name="sex"]:checked').val(),
					"area":$("select[name='area']").val(),
					"build":$("select[name='build']").val(),
					"stu_id":document.getElementById("stu_id").value
				};
				$.post(url, args, function(data) {
						if(data.message=="添加成功"){
							alert("信息添加成功!");
							swal("提示", "信息添加成功!", "success");
							
							var select_Reset = document.getElementById('select_Reset');
						        select_Reset.style.display = "none"; 
							var modal2 = document.getElementById('change_person');
							 modal2.style.display = "none";
							var re_code=document.getElementById('change_Code');
						        re_code.style.display = "none";		
						}else{
							alert("信息添加失败!");
							swal("提示", "信息添加失败!", "warning");
						}

						});

		};
		};
		
		var password_submit=document.getElementById("password_submit");
		var two=document.getElementById("two");

		//点击提交按钮
		password_submit.onclick=function(){
		    if($("#new_password").val()==""||$("#new_password").val()==null||
			   $("#old_password").val()==""||$("#old_password").val()==null){
				swal("提示", "请填写完整信息", "warning");
			}else if($("#new_password").val()!=$("#repeatPassword").val()){
				swal("提示", "两次输入密码不一致", "warning");
			}else{
				var url = "/GuaranteeSystem/ModifyPasswordServlet";
				var args = {
					"old_password" : $("#old_password").val(),
					"new_password" : $("#new_password").val()
				};
				$.post(url, args, function(data) {
					if(data.msg=="修改密码失败"){
						swal("提示", "旧密码输入不正确", "warning");
					}
					if(data.msg=="密码更新成功"){
						swal("提示", "密码修改成功", "success");
					}

				});
			}
		};
		
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
			$("#r_ordernumber").text(data.data[k].orderNumber);
			$("#s_id").text(data.data[k].username);
			$("#s_phone").text(data.data[k].phone);
			$("#r_sertype").text(serType);
			$("#r_seradd").text(data.data[k].serAdd);  
			var time = data.data[k].serTime + "&nbsp;&nbsp;&nbsp;&nbsp;" + data.data[k].detailTime;
			$("#r_sertime").html(time);
			$("#r_serinform").text(data.data[k].serInform);
			$("#r_judgestate").text(judgeState);
			$("#a_id").text(data.data[k].repairMan);
			$("#img").attr("src","/file/" + data.data[k].file_path);
		}
		
	});
	
	var modal = document.getElementById('open_repair');
    // 打开弹窗的按钮对象
    var btn = document.getElementById("show_bill");
    // 获取 <span> 元素，用于关闭弹窗
    var span = document.getElementById("close");
    
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
	


