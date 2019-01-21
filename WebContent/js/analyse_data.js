function $(id){return document.getElementById(id);}
 window.onload = function() {
    var oA=$("layout_center").getElementsByClassName("tit");
    var oDiv=document.getElementById("box_con").getElementsByClassName("show_in");
    for(var i=0;i<oA.length;i++){
        oA[i].index=i;
        oA[i].onclick=function(){
            for(var n=0;n<oA.length;n++){
                oA[n].className="tit";
            }
            this.className="tit show";
            for(var j=0;j<oDiv.length;j++){
                oDiv[j].style.display="none";
            }
            oDiv[this.index].style.display="block";
        }
    }
    var $j = jQuery.noConflict(); //自定义一个比较短快捷方式
    var btn = document.getElementById("btn1");
    // 获取 <span> 元素，用于关闭弹窗
   // var span = document.getElementById("close_btn");
    //btn.onclick = function() {
		var url = "/GuaranteeSystem/GetAnalyzeServlet";
		var args = {
			"analyze" : 1
		};
		$j.post(url, args, function(data) {
				
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
var option = {
    title : {
        text: '报修类别饼图',
        subtext: '学生',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['水','木','电','其他']
    },
    series : [
        {
            name: '学生报修类别',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:data.data[0], name:'水'},
                {value:data.data[1], name:'木'},
                {value:data.data[2], name:'电'},
                {value:data.data[3], name:'其他'},
            ]
        }
    ]
};

// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
});
//    }
}