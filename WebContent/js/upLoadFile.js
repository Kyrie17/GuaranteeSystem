

function upLoad(){
		
//	document.getElementById("form").submit();
	var $j = jQuery.noConflict(); //自定义一个比较短快捷方式
	var form = new FormData(document.getElementById("form"));
	$j.ajax({
        url:"http://localhost:8080/GuaranteeSystem/UpLoadServlet",
        type:"post",
        data:form,
        processData:false,
        contentType:false,
        success:function(data){
            
        	swal("提示", "文件上傳成功!", "success");
        	
        },
        error:function(e){
        	swal("提示", "文件上傳成功!", "success");
        }
    });        
	
		
}