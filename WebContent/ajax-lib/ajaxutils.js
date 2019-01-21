//创建request对象
function createXMLHttpRequest() {
	try {
		return new XMLHttpRequest();
	} catch (e) {
		try {
			return new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {

			try {
				return new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("老哥，你用的什么浏览器");
				throw (e);
			}
		}
	}
};
//option类有如下属性

/*请求方式*///method
/*请求的url*///url
/*是否异步*///asyn
/*请求体*///params
/*回调方法*///callBack
/*服务器响应数据转换成什么类型*///type
function ajax(option) {
	//得到xmlHttp
	var xmlHttp = createXMLHttpRequest();
	//打开连接
	if (!option.method)//默认为get
	{
		option.method = "get";
	}
	if (option.asyn == undefined)//默认为异步	
	{
		option.asyn = true;
	}
	xmlHttp.open(option.method, option.url, option.asyn);
	//判断是否为post
	if ("post" == option.method) {
		xmlHttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlHttp.setRequestHeader("token", localStorage.getItem("token"));

	}
	//发送请求
	xmlHttp.send(option.params);
	//注册监听器
	xmlHttp.onreadystatechange = function() {
		var data;
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {

			//获取服务器的响应数据，进行转换！
			if (option.type == "xml") {
				data = xmlHttp.responseXML;
			} else if (option.type == "text") {
				data = xmlHttp.responseText;
			} else if (option.type == "json") {
				var text = xmlHttp.responseText;
				data = eval("(" + text + ")");
			} else if (!type)//type不存在，则默认为纯文本
			{
				data = xmlHttp.responseText;
			}
			//调用回调方法
			option.callback(data);
		}
	};
};