
var currentPage = 1;

$(document).ready(function () {
	
	var url = "http://localhost:8080/GuaranteeSystem/SublistServlet";
	var args = {"time":new Date(),"username":"123","pageNum":currentPage,"judgeState":-1,"r_userconfirm":0};
	$.getJSON(url, args, function(data){
		
		var str = "";
		
		for(var k in data.data.dataList){
			
			var str = "<div>" + data.data.dataList[k].username + "</div>";
			$("#test").append(str);
			
		}
for(var k in data.data.dataList){
			
			var str = "<div>" + data.data.dataList[k].username + "</div>";
			$("#test").append(str);
			
		}
for(var k in data.data.dataList){
	
	var str = "<div>" + data.data.dataList[k].username + "</div>";
	$("#test").append(str);
	
}
for(var k in data.data.dataList){
	
	var str = "<div>" + data.data.dataList[k].username + "</div>";
	$("#test").append(str);
	
}
	});
});



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
    
    
    if(scrollTop + innerHeight >= scrollHeight){        // 距离顶部+当前高度 >=文档总高度，即代表滑动到底部
    	 loadMore();
    }
});

function loadMore(){
	var htmll="";
	for(var i=0;i<5;i++){
		htmll+="<tr ><td >"+(i+1)+"</td>"
    		+"<td >11</td>" 
    		+"<td >22</td>"
    		+"<td >33</td>"
    		+"<td >44</td>"
    		+"<td >55</td>"
    		+"<td  >66</td>"
    		+"<td  >77</td>"
    		+"<td  >88</td>"
    		+"<td  >99</td>"
    		+"<td  >22</td>"
    		+"<td  >33</td>"
    		+"<td  >44</td>"
    		+"<td  >55</td></tr>" +
    				"<tr ><td >"+(i+1)+"</td>"
    		+"<td >11</td>" 
    		+"<td >22</td>"
    		+"<td >33</td>"
    		+"<td >44</td>"
    		+"<td >55</td>"
    		+"<td  >66</td>"
    		+"<td  >77</td>"
    		+"<td  >88</td>"
    		+"<td  >99</td>"
    		+"<td  >22</td>"
    		+"<td  >33</td>"
    		+"<td  >44</td>"
    		+"<td  >55</td></tr>" +
    				"<tr ><td >"+(i+1)+"</td>"
    		+"<td >11</td>" 
    		+"<td >22</td>"
    		+"<td >33</td>"
    		+"<td >44</td>"
    		+"<td >55</td>"
    		+"<td  >66</td>"
    		+"<td  >77</td>"
    		+"<td  >88</td>"
    		+"<td  >99</td>"
    		+"<td  >22</td>"
    		+"<td  >33</td>"
    		+"<td  >44</td>"
    		+"<td  >55</td></tr>"
		}
	$("#test").append(htmll);

}