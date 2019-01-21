window.onload = function () {
    var oPf = document.getElementById('pingfen');
    var aLi = oPf.getElementsByTagName('li');
    var i = 0;
    for (i = 0; i < aLi.length; i++) {
        aLi[i].index = i;
        aLi[i].style.background = "url(/GuaranteeSystem/imgs/star.gif) no-repeat 0 0px";
        aLi[i].onmouseover = function () {
            for (i = 0; i < aLi.length; i++) {
                if (i <= this.index) {
                    aLi[i].style.background = "url(/GuaranteeSystem/imgs/star.gif) no-repeat 0 -29px";
                }
                else {
                    aLi[i].style.background = "url(/GuaranteeSystem/imgs/star.gif) no-repeat 0 0";
                }
            }
        };

        aLi[i].onmousedown = function () {
        	var addressID = $("input[name='radio']:checked").val();
        	var suggest=$("#suggest").val();
        	var r_ordernumber=$("#r_ordernumber").text();
        	var index=this.index + 1;
			var url = "/GuaranteeSystem/ScoreServlet";
			var args = {
			"addressID" : addressID,
			"suggest" : suggest,
			"r_ordernumber" : r_ordernumber,
			"index" : index
		};
		$.post(url, args, function(data) {
				if(data.msg=="添加成功"){
					swal("提示", "满意度提交成功!", "success");
				}else{
					swal("提示", "满意度提交失败!", "warning");
				}
				var modal = document.getElementById('repair_bill');
					modal.style.display = "none";
				});

        };
    }
};
