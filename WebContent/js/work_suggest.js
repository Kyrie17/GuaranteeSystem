$.ajaxSettings.async = false;
var currentPage = 1;

$(document).ready(function () {
	
	Filter();
	
	getAvg();
});


function Filter(){
	
	var url = "http://localhost:8080/GuaranteeSystem/GetScoreByRm";
	var args = {"time":new Date(),"username":"卓泰","pageNum":currentPage};
	$.getJSON(url, args, function(data){
		
			if(currentPage > data.data.totalPage){
				document.getElementById("test1").innerHTML = "<div style='text-align:center;'>已经到底了~</div>";
				
			}else{
				for(var k in data.data.dataList){
				var str = "";
				var str2 = "";
				var score = data.data.dataList[k].score;
				var j = 0;
				while(j < score){
					str2 += "<li><img src='/GuaranteeSystem/imgs/xingxing.png'></li>";
					j++;
				}
				
				str += "<div class='stu_advice_box'>"
				+ "<img src='/GuaranteeSystem/imgs/repair_per.jpg' class='stu_top'>"
				+ "<div class='stu_use'>"
				+	"<span class='stuName'>" + data.data.dataList[k].username + "</span>"
				+	"<ul class='xingxing_tit'>"
				+	str2
				+	"</ul>"
				+"</div>"
				+"<span class='repair_data'>" + data.data.dataList[k].time + "</span>"
				+"<div class='suggest'>"
				+	"<span class='suggest_con'>" + data.data.dataList[k].suggest + "</span>"
				+"</div>"
			+"</div>";
				$("#test").append(str);
			}
		}
		
	});

}



$(window).scroll(function () {
	var scrollTop = $(window).scrollTop();             // 滚动条距离顶部的高度
	//scrollHeight,windowHeight,scrollHeight1三个height相同，都是这个页面的高度
    var scrollHeight = $(document).height();          // 当前页面的总高度
    var windowHeight = $(window).height();            // 当前可视的页面高度，jquery获取的不是屏幕的高度，而是整个文档的高度
    var scrollHeight1 = $("#test").get(0).scrollHeight;
    
    var windowidth = $(window).width();
	var documentwidtht =  $(document).width() ;
	
    var innerHeight =  window.innerHeight; //window的高度，即手机的高度
    var clientHeight = document.body.clientHeight; //window的高度
    var clientHeight1 = document.documentElement.clientHeight;//这个是body的整个高度，chrom测试
    
    
    if(scrollTop + innerHeight >= scrollHeight - 5){        // 距离顶部+当前高度 >=文档总高度，即代表滑动到底部
    	 loadMore();
    }
});


	function loadMore(){
		
		currentPage++;
		Filter();
		
	}	
	
	
	function getAvg(){
		
		var url = "http://localhost:8080/GuaranteeSystem/GetAvgByRmServlet";
		var args = {"time":new Date(),"username":"卓泰"};
		$.getJSON(url, args, function(data){
			
			document.getElementById("grade").innerHTML = data.code;
			
			var i = 0;
			var str3 = "";
			while(i < data.code - 1){
				str3 += "<li><img src='/GuaranteeSystem/imgs/xingxing.png'></li>";
				i++;
			}
			document.getElementById("xingxing_tit").innerHTML = str3;
		});
		
	}
	
	
