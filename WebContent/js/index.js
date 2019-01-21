/**
 * 图片回显
 * canvas
 */

		var photoW = 400;
        var photoH = 77;
        var photoW = 77;
        var photo;
        $(document).ready(function() {
 
 
		// show image preview after selection
        $("#fileChoosed").change(function picChange(evt) {
            //get files captured through input
            var fileInput = evt.target.files;
            if (fileInput.length > 0) {
                //window url
                var windowURL = window.URL || window.webkitURL;
                //picture url
                var picURL = windowURL.createObjectURL(fileInput[0]);
                //get canvas
                var photoCanvas = document.getElementById("canvasFile");
                var ctx = photoCanvas.getContext("2d");
 
 
                    //create image
                    photo = new Image();
 
 
                  
                    photo.onload = function() {
                        //draw photo into canvas when ready
                        ctx.drawImage(photo, 0, 0, photoW, photoH);
                    };
 
 
                    //load photo into canvas
                    photo.src = picURL;
 
 
                    var arr = picURL.split('/');
                    var imgUrl = arr[arr.length - 1];
                    windowURL.revokeObjectURL(imgUrl);
 
 
                }
            });
        });

// 判断图片是否为空
//判断图片是否为空
function check(a){
	document.getElementById("form").action = "http://localhost:8080/GuaranteeSystem/SubmitRFServlet";
	var x = "?username=" + this.username.value + "&phone=" + this.phone.value + "&serType=" + this.serType.value + "&serAdd=" + this.serAdd.value + "&serInform=" + this.serInform.value + "&serTime=" + this.serTime.value + "&detailTime=" + this.detailTime.value;
	var rp= x.replace('#','%23');
	var rp1= rp.replace('A','%41');
	var rp2= rp1.replace('B','%42');
	document.getElementById("form").action = document.getElementById("form").action + rp2;
//	alert(document.getElementById("form").action);
	
	
	
	// 获取对象并注册失去焦点监听
	var username = document.getElementById("username");
	var phone = document.getElementById("phone").value;
	var serType = document.getElementById("serType").value;
	var serAdd = document.getElementById("serAdd").value;
	var serInform = document.getElementById("serInform");
	var serTime = document.getElementById("serTime");
	if(document.getElementById("fileChoosed").value == ""){
		swal("提示", "请上传图片!", "warning");
		return false;
	}
	if(username.value == undefined || username.value == ""){
		swal("提示", "请填写报修人!", "warning");
		return false;
	}/*else if(phone.value == undefined || phone.value == ""){
		swal("提示", "请填写联系方式!", "warning");
		return false;
	}*/else if(serType == undefined || serType == ""){
		swal("提示", "请选择报修类型!", "warning");
		return false;
	}/*else if(serAdd.value == undefined || serAdd.value == ""){
		swal("提示", "请填写报修地址!", "warning");
		return false;
	}*/else if(serInform.value == undefined || serInform.value == ""){
		swal("提示", "请填写报修内容!", "warning");
		return false;
	}else if(serTime.value == undefined || serTime.value == ""){
		swal("提示", "请填写维修时间!", "warning");
		return false;
	}else{
		console.log("www = " + this.username.value);
		swal({
					title : "表单提交成功",
					//text : "1秒后跳转主界面。",
					timer : 10000,
					showConfirmButton : false
				});
		setTimeout(function() {
			
			}, 10000);
	}

}




